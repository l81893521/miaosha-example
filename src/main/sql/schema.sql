-- 创建数据库脚本

--创建数据库
create database seckill;
--使用数据库
use seckill;
--创建秒杀库存表
create table seckill(
	seckill_id bigint unsigned not null auto_increment comment '商品库存id',
	name varchar(120) not null comment '商品名称',
	number int unsigned not null comment '库存数量',
	start_time timestamp not null comment '描述开始时间',
	end_time timestamp not null comment '秒杀结束时间',
	create_time timestamp not null default current_timestamp comment '创建时间'
)engine=InnoDB auto_increment=1000 default charset=utf8 comment='秒杀库存表'