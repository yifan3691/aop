package com.example;

import com.example.annotation.userCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.*;

/**
 * Hello world!
 *
 */
@RestController
public class App
{

    @Autowired
    private shiwu shiwu;

    @GetMapping("/add")
    public String add () {



        return "Hello World!";
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
