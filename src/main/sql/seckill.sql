-- 秒杀执行存储过程
delimiter $$
CREATE PROCEDURE seckill.execute_seckill (IN v_seckill_id BIGINT, IN v_phone BIGINT, IN v_kill_time TIMESTAMP, OUT r_result INT)
    BEGIN
        -- 记录影响行数
        DECLARE insert_count INT DEFAULT 0;
        START TRANSACTION;
        insert ignore into success_killed
        (seckill_id, user_phone, create_time) values (v_seckill_id, v_phone, v_kill_time);
        -- row_count()获取影响行数
        select row_count() into insert_count;
        if(insert_count = 0) then
            ROLLBACK ;
            set r_result = -1;
        ELSEIF (insert_count < 0) THEN
            ROLLBACK ;
            set r_result = -2;
        ELSE
            UPDATE seckill set number = number - 1
            where seckill_id = v_seckill_id
            and end_time > v_kill_time
            and start_time < v_kill_time
            and number > 0;
            select row_count() into insert_count;
            if(insert_count = 0) THEN
                ROLLBACK ;
                set r_result = 0;
            ELSEIF (insert_count < 0) THEN
                ROLLBACK ;
                set r_result = -2;
            ELSE
                COMMIT ;
                set r_result = 1;
            END IF;
        END IF;

    END;
$$

