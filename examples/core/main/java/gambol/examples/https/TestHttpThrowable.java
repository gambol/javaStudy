package gambol.examples.https;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zhenbao.zhou on 16/3/15.
 */
@Slf4j
public class TestHttpThrowable {

    public static void main(String[] args) {
        try {
            int i = 3;
            throw new Throwable("lalal");
        } catch (Throwable t) {
            log.info("kkk, ", t);
        }
    }
}
