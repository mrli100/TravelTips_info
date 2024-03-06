<#assign dbTime = "now()">
<#if dbType=="SQLServer">
    <#assign dbTime = "getDate()">
</#if>

-- 初始化菜单
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (1, '${tableComment!}', '${moduleName}/${functionName}/index', NULL, 0, 0, 'icon-menu', 0, 0, 0, 10000, ${dbTime}, 10000, ${dbTime});

INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) SELECT temp.max_id, '查看', '', '${moduleName}:${functionName}:page', 1, 0, '', 0, 0, 0, 10000, ${dbTime}, 10000, ${dbTime} FROM (SELECT max(id) as max_id from sys_menu where name = '${tableComment!}') temp;
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) SELECT temp.max_id, '新增', '', '${moduleName}:${functionName}:save', 1, 0, '', 1, 0, 0, 10000, ${dbTime}, 10000, ${dbTime} FROM (SELECT max(id) as max_id from sys_menu where name = '${tableComment!}') temp;
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) SELECT temp.max_id, '修改', '', '${moduleName}:${functionName}:update,${moduleName}:${functionName}:info', 1, 0, '', 2, 0, 0, 10000, ${dbTime}, 10000, ${dbTime} FROM (SELECT max(id) as max_id from sys_menu where name = '${tableComment!}') temp;
INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) SELECT temp.max_id, '删除', '', '${moduleName}:${functionName}:delete', 1, 0, '', 3, 0, 0, 10000, ${dbTime}, 10000, ${dbTime} FROM (SELECT max(id) as max_id from sys_menu where name = '${tableComment!}') temp;

