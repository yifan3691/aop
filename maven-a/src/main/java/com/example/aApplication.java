package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author zhongzb
 * @date 2021/05/27
 */
@SpringBootApplication(scanBasePackages = {"com.example"})
@EnableAspectJAutoProxy(proxyTargetClass = true) // 显式启用AOP，并使用CGLIB代理
public class aApplication {

    public static void main(String[] args) {
        System.out.println("Java Version: " + System.getProperty("java.version"));
        SpringApplication.run(aApplication.class,args);
        System.out.println("已启动!!!!!!!!!!!!!!!");
    }

}
