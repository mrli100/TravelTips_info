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
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
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
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

/**
 * 数据权限
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Slf4j
public class DataScopeInnerInterceptor implements InnerInterceptor {

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
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //已经拿到session,就可以拿到session中保存的用户信息了。
        Enumeration<String> headerNames = request.getHeaderNames();
        UserDetail user = SecurityUser.getUser();
        System.out.println(headerNames.toString());

        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();
        final BoundSql boundSql = mpSh.boundSql();
        SqlCommandType sct = ms.getSqlCommandType();
        if (sct == SqlCommandType.INSERT || sct == SqlCommandType.UPDATE || sct == SqlCommandType.DELETE) {
            PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
            DataChangeRecorderInnerInterceptor.OperationResult operationResult;
            long startTs = System.currentTimeMillis();
            try {
                Statement statement = JsqlParserGlobal.parse(mpBs.sql());
                if (statement instanceof Insert) {
                } else if (statement instanceof Update) {
                    Update updateStmt = (Update) statement;
                    //添加更新时间
                    UpdateSet updateSet = new UpdateSet(new Column("update_time"), new StringValue(DateUtils.format(new Date())));
                    //updateStmt.getUpdateSets().add(updateSet);
                    //PluginUtils.mpBoundSql(boundSql).sql(updateStmt.toString());
                } else if (statement instanceof Delete) {
                } else {
                    log.info("other operation sql={}", mpBs.sql());
                    return;
                }
            } catch (Exception e) {
                log.error("Unexpected error for mappedStatement={}, sql={}", ms.getId(), mpBs.sql(), e);
                return;
            }
            long costThis = System.currentTimeMillis() - startTs;
        }
    }
}
