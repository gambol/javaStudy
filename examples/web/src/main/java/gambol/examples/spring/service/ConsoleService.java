package gambol.examples.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 这个service 主要用来验证spring 的bean注解逻辑 Created by zhenbao.zhou on 16/6/22.
 */

@Service
public class ConsoleService extends AbstractConsole {

    // @Transactional
    public void test() {
        System.out.println(" test in consoleService");
       // console();
    }

    public int a() {
        return 1;
    }

}
