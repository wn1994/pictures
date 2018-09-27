drop database if exists pictures;
create database pictures;
use pictures;
-- 创建用户表
create table `user` (
  `id`            bigint(20)  not null auto_increment
  comment '用户ID',
  `phone_num`     varchar(20) not null unique
  comment '手机号，注册检验唯一性',
  `username`      varchar(20) not null
  comment '用户名',
  `password`      varchar(20) not null,
  `register_time` timestamp   not null default current_timestamp,
  primary key (`id`)
  #   key `phone_num`(`phone_num`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='用户表';

-- 创建图片表
create table `picture` (
  `id`            bigint(20)   not null auto_increment,
  `user_id`       bigint(20)   not null,
  `name`          varchar(20)           default '未定义'
  comment '自定义照片名',
  `path`          varchar(300) not null,
  `guest_visible` bool                  default false
  comment '访客可见性，默认不可见',
  `upload_time`   timestamp    not null default current_timestamp,
  primary key (`id`),
  key `user_id`(`user_id`),
  constraint `FK_1` foreign key (`user_id`) references user (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='图片表';

INSERT INTO
  user (phone_num, username, password)
VALUES
  ('18810000000', '张三', 'wn123'),
  ('18810000001', '李四', 'wn');

INSERT INTO
  picture (user_id, name, path, guest_visible)
VALUES
  (1, '钱钱钱', '/img/render/3fb0fb124fdb48819ceaeab8d3968eb7.jpeg', false),
  (2, 'ww ', '/img/render/3744db0175c94b04b1e015326893e0d2.jpeg', false),
  (2, 'wws ', '/img/render/7563c480faf94ab788619322e6fcd7cc.jpeg', true),
  (1, '钱', '/img/render/39723e0448ac40db95dd808d12f8ed9f.jpeg', true),
  (1, '钱钱钱钱钱', '/img/render/67682cf4b11942769e2606ee8ee79685.jpeg', true);