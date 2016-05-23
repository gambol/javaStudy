package gambol.examples.writecode.guava;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

/**
 * 介绍guava base包里的内容
 * Created by zhenbao.zhou on 16/5/10.
 */
public class Bases {

    public void testPreconditions() {

        // NullPointerException
        Preconditions.checkNotNull(null, "msg");

        /* IllegalArgumentException */
        Preconditions.checkArgument(false,"msg");
    }

    public void testOptional() {
        Optional<String> optional = Optional.of(new String(""));
        Optional.fromNullable(null/** or object */);

        Optional.absent();

        if(optional.isPresent()){
        }


        optional.get();/* get object */
    }
}
