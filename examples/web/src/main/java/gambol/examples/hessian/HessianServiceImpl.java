package gambol.examples.hessian;

/**
 * User: zhenbao.zhou
 * Date: 11/18/14
 * Time: 8:07 PM
 */
public class HessianServiceImpl implements HessianService {
    public String sayHello(String str) {
        return "hello:" + str;
    }
}
