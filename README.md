# miaosha-example
一个高并发优化的秒杀系统例子

### 技术架构
* 待添加


### 数据库
* 案例使用的数据库版本为5.7.16
* 数据库一共包含了2个表，一个为秒杀库存表(seckill)和秒杀成功信息表(success_killed)
* 数据库建表脚本[查看](https://github.com/l81893521/miaosha-example/blob/master/src/main/sql/schema.sql)

### 配置
* mybatis配置[查看](https://github.com/l81893521/miaosha-example/blob/master/src/main/resources/mybatis-config.xml)
* 数据源+mybatis整合spring配置[查看](https://github.com/l81893521/miaosha-example/blob/master/src/main/resources/spring/spring-dao.xml)