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
	create_time timestamp not null default current_timestamp comment '����ʱ��'
)engine=InnoDB auto_increment=1000 default charset=utf8 comment='��ɱ����'