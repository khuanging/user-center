-- auto-generated definition
create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    username     varchar(256)                        null comment '用户名',
    userAccount  varchar(256)                        null comment '用户账号',
    avatarUrl    varchar(1024)                       null comment '用户头像',
    gender       tinyint                             null comment '性别',
    userPassword varchar(256)                        not null comment '用户密码',
    phone        varchar(128)                        null comment '电话号码',
    email        varchar(512)                        null comment '邮箱',
    userStatus   int       default 0                 null comment '用户状态 1-用户过期 0-用户有效',
    createTime   datetime  default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   timestamp default CURRENT_TIMESTAMP null comment '更新时间',
    isDelete     tinyint   default 0                 not null comment '是否删除',
    userRole     int       default 0                 null comment '用户角色 0-普通用户 1-管理员 2-会员用户',
    planetCode   varchar(512)                        null comment '编号'
)
    comment '用户';

