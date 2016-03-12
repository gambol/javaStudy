package gambol.examples.dubbo.provider;

import java.util.List;

/**
 * User: zhenbao.zhou
 * Date: 11/17/14
 * Time: 7:32 PM
 */
public interface DubboService {

    String sayHello(String name);

    List getUsers();

}
