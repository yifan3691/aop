package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author kevin
 * @version 1.0
 * @project maven-parent
 * @description 事务分析
 * @date 2025/7/2 22:25:39
 */
@Component
public class shiwu {



    @Transactional
    public void shiwu1() {
        System.out.println("开启事务");
        // 执行事务内业务逻辑
        // ...
        
        
        

        // 事务将在方法结束时自动提交
    }

    /**
     * 简单的事务提交后执行的方法
     */
    public void afterTransactionComplete() {
        System.out.println("事务已完成，执行后续操作");
        // 在这里添加事务完成后的逻辑
        updateCache();
        sendNotification();
    }


    @Transactional
    public void executeWithTransaction() {
        System.out.println("开始执行事务内的业务逻辑");

        // 事务内的业务逻辑
        // ...

        // 注册一个事务同步，在事务提交后执行
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                System.out.println("事务已提交，开始执行后续操作");
                // 在这里执行事务提交后的操作

            }
        });
    }

    // 更新缓存的示例方法
    private void updateCache() {
        System.out.println("更新缓存数据");
    }

    // 发送通知的示例方法
    private void sendNotification() {
        System.out.println("发送完成通知");
    }
}
