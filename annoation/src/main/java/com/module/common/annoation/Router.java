package com.module.common.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuxia
 * @version 3.7
 * @date 16-8-31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Router {

//    String target();
//
//    String action();
    String[] path();

    String[] paramAlias() default {};

    String[] paramName() default {};

    String[] paramType() default {};

    boolean needLogined() default false;

    boolean needClickable() default false;
}
