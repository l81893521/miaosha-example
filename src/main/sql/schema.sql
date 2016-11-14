-- 创建数据库脚本

-- 创建数据库
create database seckill;
-- 使用数据库
use seckill;
-- 创建秒杀库存表
create table seckill(
	seckill_id bigint unsigned not null auto_increment comment '商品库存id',
	name varchar(120) not null comment '商品名称',
	number int unsigned not null comment '库存数量',
	start_time timestamp null comment '描述开始时间',
	end_time timestamp null comment '秒杀结束时间',
	create_time timestamp not null default current_timestamp comment '创建时间',
	primary key (seckill_id),
	key idx_start_time(start_time),
	key idx_end_time(end_time),
	key idx_create_time(create_time)
)engine=InnoDB auto_increment=1000 default charset=utf8 comment='秒杀库存表';

-- 初始化数据
insert into seckill(name,number,start_time,end_time)
values
	('1000元秒杀iphone7',100,'2016-10-01 00:00:00','2016-10-07 00:00:00'),
	('400元秒杀ipad mini',200,'2016-10-01 00:00:00','2016-10-07 00:00:00'),
	('200元秒杀mac book pro',300,'2016-10-01 00:00:00','2016-10-07 00:00:00'),
	('1元秒杀iphone7 plus',400,'2016-10-01 00:00:00','2016-10-07 00:00:00');

-- 秒杀成功明细表
-- 用户登录认证相关的信息
create table success_killed(
	seckill_id bigint unsigned not null comment '秒杀商品id',
	user_phone bigint unsigned not null comment '用户手机号',
	state tinyint unsigned not null comment '状态标识：-1：无效 1：成功',
	create_time timestamp not null comment '创建时间',
	primary key(seckill_id,user_phone),/*联合主键*/
	key idx_create_time(create_time)
)engine=InnoDB default charset=utf8 comment='秒杀成功明细表';











