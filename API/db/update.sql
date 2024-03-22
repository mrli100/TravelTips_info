create table traveltips.t_app_sns_circle
(
    id          bigint unsigned auto_increment
        primary key,
    user_id     int                                        null comment '用户ID',
    name        varchar(50)  default ''                    not null comment '名称',
    avatar      varchar(255) default ''                    not null comment '头像',
    intro       varchar(255) default ''                    null comment '简介',
    ip          varchar(128) default ''                    null comment 'ip',
    address_lat varchar(50)  default ''                    null comment '纬度',
    address_lng varchar(50)  default ''                    null comment '经度',
    country     varchar(50)  default ''                    null comment '国家',
    province    varchar(50)  default ''                    null comment '省',
    city        varchar(50)  default ''                    null comment '市',
    district    varchar(50)  default ''                    null comment '区',
    highlight   tinyint(1)   default 1                     not null comment '高亮',
    role_type   tinyint(1) COMMENT '权限级别',
    status      tinyint(1)   default 1                     not null comment '状态',
    class_type  tinyint(1)                                     NOT NULL DEFAULT 1 COMMENT '类型',
    weigh       int          default 0                     not null comment '权重',
    tenant_id    bigint       null comment '租户ID',
    version      int          null comment '版本号',
    deleted      tinyint      null comment '删除标识  0：正常   1：已删除',
    creator      bigint       null comment '创建者',
    create_time  datetime     null comment '创建时间',
    updater      bigint       null comment '更新者',
    update_time  datetime     null comment '更新时间'
)
    comment '圈子表' collate = utf8mb4_unicode_ci;



create table traveltips.sys_dict_data
(
    id           bigint auto_increment comment 'id'
        primary key,
    dict_type_id bigint       not null comment '字典类型ID',
    dict_label   varchar(255) not null comment '字典标签',
    dict_value   varchar(255) null comment '字典值',
    label_class  varchar(100) null comment '标签样式',
    remark       varchar(255) null comment '备注',
    sort         int          null comment '排序',
    tenant_id    bigint       null comment '租户ID',
    version      int          null comment '版本号',
    deleted      tinyint      null comment '删除标识  0：正常   1：已删除',
    creator      bigint       null comment '创建者',
    create_time  datetime     null comment '创建时间',
    updater      bigint       null comment '更新者',
    update_time  datetime     null comment '更新时间'
)
    comment '字典数据' collate = utf8mb4_general_ci;

