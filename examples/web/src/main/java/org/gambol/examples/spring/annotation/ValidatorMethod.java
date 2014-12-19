package org.gambol.examples.spring.annotation;

import java.lang.annotation.*;

/**
 * User: zhenbao.zhou
 * Date: 12/19/14
 * Time: 7:21 PM
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatorMethod {

    String value();
}
