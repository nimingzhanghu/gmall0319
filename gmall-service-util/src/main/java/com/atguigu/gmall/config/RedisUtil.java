package com.atguigu.gmall.config;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 取得redis的工具-->RedisUtil就是被注入的工具类以供其他模块调用
 */
public class RedisUtil {
    //1、声明一个连接池
    private JedisPool jedisPool;

    //2、连接池初始化--> 为连接池设置参数
    public void initJedisPool(String host,int port, int database){
        //new 一个连接池配制对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        //配置连接池参数
        jedisPoolConfig.setMaxTotal(200);

        //配制连接池等待的最大毫秒
        jedisPoolConfig.setMaxWaitMillis(10*1000);

        //配制最少剩余数
        jedisPoolConfig.setMinIdle(10);

        //如果达到连接上线，设置等待
        jedisPoolConfig.setBlockWhenExhausted(true);

        //设置等待时间
        jedisPoolConfig.setMaxWaitMillis(2000);

        //在连接时，检测是否有效
        jedisPoolConfig.setTestOnBorrow(true);

        //将jedisPoolConfig设置在jedisPool中
        jedisPool = new JedisPool(jedisPoolConfig,host,port,20*1000);
    }

    //取得jedis
    public Jedis getJedis(){
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

}
