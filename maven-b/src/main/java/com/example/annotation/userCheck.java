package com.example.annotation;

import java.lang.annotation.*;

/**
 * @author kevin
 * @version 1.0
 * @project maven-parent
 * @description 用户校验
 * @date 2025/7/3 20:34:03
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface userCheck {

    String value() default ""; // 可以指定用户校验的类型或其他信息

}
