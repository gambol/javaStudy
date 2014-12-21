package org.gambol.examples.spring.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.gambol.examples.spring.service.TestValidateService;
import org.springframework.stereotype.Service;

/**
 * 测试利用Aop进行validate校验
 * User: zhenbao.zhou
 * Date: 12/19/14
 * Time: 7:41 PM
 */

@Slf4j
@Service("testValidateService")
public class TestValidateServiceImpl implements TestValidateService{


    public void serviceA(int bigOne, int smallOne) {

        //log.info("big one :{}, small one: {}", bigOne, smallOne);

    }

}

