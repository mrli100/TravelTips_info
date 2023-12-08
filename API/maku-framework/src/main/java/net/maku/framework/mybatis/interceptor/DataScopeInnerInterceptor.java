package net.maku.framework.mybatis.interceptor;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserGlobal;
import com.baomidou.mybatisplus.extension.plugins.inner.DataChangeRecorderInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.maku.framework.common.utils.DateUtils;
import net.maku.framework.security.user.SecurityUser;
import net.maku.framework.security.user.UserDetail;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.Connection;
import java.util.*;

/**
 * 数据权限
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Slf4j
public class DataScopeInnerInterceptor implements InnerInterceptor {

    //系统初始化的表不需要过滤，例如：工作流自带的表
    protected static List<String> noInitFilterTables = new ArrayList<>();

    static {
        //去掉这些系统表，不更改
        noInitFilterTables.add("QRTZ_CALENDARS");
        noInitFilterTables.add("QRTZ_FIRED_TRIGGERS");
        noInitFilterTables.add("QRTZ_JOB_DETAILS");
        noInitFilterTables.add("QRTZ_LOCKS");
        noInitFilterTables.add("QRTZ_PAUSED_TRIGGER_GRPS");
        noInitFilterTables.add("QRTZ_SCHEDULER_STATE");
        noInitFilterTables.add("QRTZ_TRIGGERS");
        noInitFilterTables.add("QRTZ_BLOB_TRIGGERS");
        noInitFilterTables.add("QRTZ_CRON_TRIGGERS");
        noInitFilterTables.add("QRTZ_SIMPLE_TRIGGERS");
        noInitFilterTables.add("QRTZ_SIMPROP_TRIGGERS");
        noInitFilterTables.add("GEN_BASE_CLASS");
        noInitFilterTables.add("GEN_DATASOURCE");
        noInitFilterTables.add("GEN_FIELD_TYPE");
        noInitFilterTables.add("GEN_PROJECT_MODIFY");
        noInitFilterTables.add("GEN_TABLE");
        noInitFilterTables.add("GEN_TABLE_FIELD");
        noInitFilterTables.add("GEN_TEST_STUDENT");
        noInitFilterTables.add("SCHEDULE_JOB");
        noInitFilterTables.add("SCHEDULE_JOB_LOG");
        noInitFilterTables.add("SYS_ATTACHMENT");
        noInitFilterTables.add("SYS_DICT_DATA");
        noInitFilterTables.add("SYS_DICT_TYPE");
        noInitFilterTables.add("SYS_LOG_LOGIN");
        noInitFilterTables.add("SYS_LOG_OPERATE");
        noInitFilterTables.add("SYS_MENU");
        noInitFilterTables.add("SYS_ORG");
        noInitFilterTables.add("SYS_PARAMS");
        noInitFilterTables.add("SYS_POST");
        noInitFilterTables.add("SYS_ROLE");
        noInitFilterTables.add("SYS_ROLE_DATA_SCOPE");
        noInitFilterTables.add("SYS_ROLE_MENU");
        noInitFilterTables.add("SYS_USER");
        noInitFilterTables.add("SYS_USER_POST");
        noInitFilterTables.add("SYS_USER_ROLE");
        noInitFilterTables.add("SYS_USER_TOKEN");
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        DataScope scope = getDataScope(parameter);
        // 不进行数据过滤
        if (scope == null || StrUtil.isBlank(scope.getSqlFilter())) {
            return;
        }

        // 拼接新SQL
        String buildSql = getSelect(boundSql.getSql(), scope);

        // 重写SQL
        PluginUtils.mpBoundSql(boundSql).sql(buildSql);
    }


    private DataScope getDataScope(Object parameter) {
        if (parameter == null) {
            return null;
        }

        // 判断参数里是否有DataScope对象
        if (parameter instanceof Map<?, ?> parameterMap) {
            for (Map.Entry<?, ?> entry : parameterMap.entrySet()) {
                if (entry.getValue() != null && entry.getValue() instanceof DataScope) {
                    return (DataScope) entry.getValue();
                }
            }
        } else if (parameter instanceof DataScope) {
            return (DataScope) parameter;
        }

        return null;
    }

    private String getSelect(String buildSql, DataScope scope) {
        try {
            Select select = (Select) CCJSqlParserUtil.parse(buildSql);
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

            Expression expression = plainSelect.getWhere();
            if (expression == null) {
                plainSelect.setWhere(new StringValue(scope.getSqlFilter()));
            } else {
                AndExpression andExpression = new AndExpression(expression, new StringValue(scope.getSqlFilter()));
                plainSelect.setWhere(andExpression);
            }

            return select.toString().replaceAll("'", "");
        } catch (JSQLParserException e) {
            return buildSql;
        }
    }


    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        //获取到当前线程绑定的请求对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == requestAttributes) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        //已经拿到session,就可以拿到session中保存的用户信息了。
        Enumeration<String> headerNames = request.getHeaderNames();
        UserDetail user = SecurityUser.getUser();

        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();
        final BoundSql boundSql = mpSh.boundSql();
        SqlCommandType sct = ms.getSqlCommandType();
        if (sct == SqlCommandType.INSERT || sct == SqlCommandType.UPDATE || sct == SqlCommandType.DELETE) {
            PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
            DataChangeRecorderInnerInterceptor.OperationResult operationResult;
            try {
                Statement statement = JsqlParserGlobal.parse(mpBs.sql());
                if (statement instanceof Insert) {
                    Insert insertStmt = (Insert) statement;
                    if (!noInitFilterTables.contains(insertStmt.getTable().getName().toUpperCase())) {
                        if (null != user) {
                            List<Column> columns = insertStmt.getColumns();
                            ExpressionList items = (ExpressionList) insertStmt.getItemsList();
                            List<Expression> exprs = items.getExpressions();
                            //创建时间
                            columns.add(new Column("create_time"));
                            items.addExpressions(new StringValue(DateUtils.format(new Date())));
                            //创建人
                            columns.add(new Column("creator"));
                            items.addExpressions(new LongValue(user.getId()));
                            //设置版本号
                            columns.add(new Column("version"));
                            items.addExpressions(new LongValue(1));

                            insertStmt.setColumns(columns);
                            insertStmt.setSetExpressionList(exprs);
                            PluginUtils.mpBoundSql(boundSql).sql(insertStmt.toString());
                        }
                    }
                } else if (statement instanceof Update) {
                    Update updateStmt = (Update) statement;
                    if (!noInitFilterTables.contains(updateStmt.getTable().getName().toUpperCase())) {
                        if (null != user) {
                            //添加更新时间
                            UpdateSet updateTimeSet = new UpdateSet(new Column("update_time"), new StringValue(DateUtils.format(new Date())));
                            UpdateSet updater = new UpdateSet(new Column("updater"), new LongValue(user.getId()));
                            //更新时候对版本号+1
                            Addition expr = new Addition();
                            expr.setLeftExpression(new Column(updateStmt.getTable(), "version"));
                            expr.setRightExpression(new LongValue(1));
                            UpdateSet version = new UpdateSet(new Column("version"), expr);
                            updateStmt.getUpdateSets().add(updateTimeSet);
                            updateStmt.getUpdateSets().add(updater);
                            updateStmt.getUpdateSets().add(version);
                            PluginUtils.mpBoundSql(boundSql).sql(updateStmt.toString());
                        }
                    }
                } else if (statement instanceof Delete) {
                } else {
                    log.info("other operation sql={}", mpBs.sql());
                    return;
                }
            } catch (Exception e) {
                log.error("Unexpected error for mappedStatement={}, sql={}", ms.getId(), mpBs.sql(), e);
                return;
            }
        }
    }
}
