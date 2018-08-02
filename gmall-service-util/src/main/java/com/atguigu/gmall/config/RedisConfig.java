package com.atguigu.gmall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RedisConfig负责在spring容器启动时自动注入
 */
@Configuration
public class RedisConfig {

    // host ,port ,database 配置文件：哪个项目中使用redis，则就将host,port,database,放到哪个项目中
    // 如果host没有值的时候，则默认为disable。
    @Value("${spring.redis.host:disable}")
    private String host;

    @Value("${spring.redis.port:0}")
    private int port;

    @Value("${spring.redis.database:0}")
    private int database;


    // <bean id="redisUtil" class="com.atguigu.gmall0319.config.RedisUtil" />
    @Bean
    public RedisUtil getRedisUtil(){
        if ("disable".equals(host)){
            return null;
        }
        RedisUtil redisUtil = new RedisUtil();
//        Jedis jedis = redisUtil.getJedis();
        redisUtil.initJedisPool(host,port,database);

        return redisUtil;
    }

}
