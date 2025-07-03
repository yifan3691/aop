package com.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author kevin
 * @version 1.0
 * @project maven-parent
 * @description 用户切面
 * @date 2025/7/3 20:41:25
 */
@Aspect
@Component
public class userAspect{

    @Pointcut("@annotation(com.example.annotation.userCheck)")
    public void userPointcut() {
        // 定义切点，匹配所有带有 @userCheck 注解的方法
    }

    @Around("userPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around 前置操作：" + joinPoint.getSignature().getName());
        Object result = joinPoint.proceed(); // 执行原方法
        System.out.println("Around 后置操作：" + joinPoint.getSignature().getName());
        return result;
    }

    @Before("userPointcut()")
    public void before(JoinPoint joinPoint) {
        // 在方法执行前进行用户校验
        System.out.println("用户校验切面：方法 " + joinPoint.getSignature().getName() + " 被调用");
        // 这里可以添加具体的用户校验逻辑
    }
}
