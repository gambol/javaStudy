package gambol.examples.spring.annotation;

import java.lang.annotation.*;

/**
 * User: zhenbao.zhou
 * Date: 10/15/14
 * Time: 5:44 PM
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonResponse {

    String callback() default "callback";
}
