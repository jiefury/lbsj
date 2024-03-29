package com.lbsj.config.redis;

import org.springframework.stereotype.Component;

@Component
public class RedisMessageReceiver {
    public void receiveMessage(String message,String channel){
        System.out.println("RedisMessageReceiver channel---: " + channel);
        System.out.println("RedisMessageReceiver message---: " + message);
    }
}