package com.example;

import com.example.annotation.userCheck;
import com.example.config.RedisConfig;
import com.example.config.RedisUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Hello world!
 *
 */
@RestController
public class App
{

    @GetMapping("/add")
    public String add () {


        return "Hello World!";
    }

    @GetMapping("/test1")
    public String test1 () {
        String scan = RedisUtils.get("ip", String.class);
        System.out.println(scan);
        return "test";
    }

    @Autowired
    private RedisConfig redisConfig;

    @GetMapping("/test2")
    public String test2 () {

        //从配置文件中获取redis配置ip
        String redisInfo = redisConfig.getRedisInfo();
        System.out.println("当前Redis配置: " + redisInfo);


        RedisUtils.set("123", "456");
        return "test";
    }


    @Autowired
    private RedissonClient redissonClient;
    @GetMapping("/test3")
    public String test3 () {
        RLock lock = redissonClient.getLock("111");
        boolean b = lock.tryLock();
        if (!b) {
            System.out.println("获取锁失败");
            return "获取锁失败";
        }

        System.out.println("锁定");
        lock.unlock();
        System.out.println("解锁");
        return "test";
    }




    @GetMapping("/payment")
    @userCheck("支付用户校验")
    public void sendPamentNotification() {
        // 发送支付通知的逻辑
        System.out.println("发送支付通知");
    }

    @GetMapping("/registration")
    @userCheck("注册用户校验")
    public void sendRegistrationNotification() {
        // 发送注册通知的逻辑
        System.out.println("发送注册通知");
    }



    @GetMapping("/transaction")
    @userCheck("事务用户校验")
    public void sendNotification() {
        System.out.println("发送通知");
    }


}
