# miaosha-example(高并发秒杀系统列子)

### 技术架构
* 展示层 : jquery, jquery.cookie, jquery.countdown, bootstrap, springMVC
* 持久层 : mybatis
* 数据库 : mysql
* 缓存 : redis
* IOC : spring


### 数据库
* 案例使用的数据库版本为5.7.16
* 数据库一共包含了2个表，一个为秒杀库存表(seckill)和秒杀成功信息表(success_killed)
* 数据库建表脚本[查看](https://github.com/l81893521/miaosha-example/blob/master/src/main/sql/schema.sql)

### 配置
* mybatis配置[查看](https://github.com/l81893521/miaosha-example/blob/master/src/main/resources/mybatis-config.xml)
* dao相关配置[查看](https://github.com/l81893521/miaosha-example/blob/master/src/main/resources/spring/spring-dao.xml)
* service相关配置[查看](https://github.com/l81893521/miaosha-example/blob/master/src/main/resources/spring/spring-service.xml)

### 代码
* 待添加

### 单元测试
* 待添加