-- �������ݿ�ű�

--�������ݿ�
create database seckill;
--ʹ�����ݿ�
use seckill;
--������ɱ����
create table seckill(
	seckill_id bigint unsigned not null auto_increment comment '��Ʒ���id',
	name varchar(120) not null comment '��Ʒ����',
	number int unsigned not null comment '�������',
	start_time timestamp not null comment '������ʼʱ��',
	end_time timestamp not null comment '��ɱ����ʱ��',
	create_time timestamp not null default current_timestamp comment '����ʱ��',
	primary key (seckill_id),
	key idx_start_time(start_time),
	key idx_end_time(end_time),
	key idx_create_time(create_time)
)engine=InnoDB auto_increment=1000 default charset=utf8 comment='��ɱ����';

-- ��ʼ������
insert into seckill(name,number,start_time,end_time)
values
('1000Ԫ��ɱiphone7',100,'2016-10-01 00:00:00','2016-10-07 00:00:00'),
('400Ԫ��ɱipad mini',200,'2016-10-01 00:00:00','2016-10-07 00:00:00'),
('200Ԫ��ɱmac book pro',300,'2016-10-01 00:00:00','2016-10-07 00:00:00'),
('1Ԫ��ɱiphone7 plus',400,'2016-10-01 00:00:00','2016-10-07 00:00:00');

--��ɱ�ɹ���ϸ��
--�û���¼��֤��ص���Ϣ
create table success_killed(
seckill_id bigint unsigned not null comment '��ɱ��Ʒid',
user_phone bigint unsigned not null comment '�û��ֻ���',
state tinyint unsigned not null comment '״̬��ʶ��-1����Ч 0���ɹ� 1���Ѹ��� ',
create_time timestamp not null comment '����ʱ��',
primary key(seckill_id,user_phone),/*��������*/
key idx_create_time(create_time)
)engine=InnoDB default charset=utf8 comment='��ɱ�ɹ���ϸ��';











