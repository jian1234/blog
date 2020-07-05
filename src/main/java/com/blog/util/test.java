package com.blog.util;

import redis.clients.jedis.Jedis;

public class test {
    public static void main(String[] args){
        Jedis jedis = new Jedis("134.175.236.63",6379);
        jedis.set("name","连接成功");
        System.out.println(jedis.get("name"));
        jedis.close();
    }
}
