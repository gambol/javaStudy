package gambol.examples.spring.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Method;

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
