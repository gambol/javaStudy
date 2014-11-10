package org.gambol.examples.web;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * User: zhenbao.zhou
 * Date: 10/11/14
 * Time: 5:28 PM
 */
@RequestMapping("/test/")
@Controller
public class TestController {

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("helloWorld.sp")
    public String helloWorld() {
        return "helloWorld";
    }

    @RequestMapping("old.json")
    public @ResponseBody Map old() {
        Map map = new HashMap<String, Object>();
        map.put("key1", "测试值1");
        map.put("key2", 2);

        return map;
    }

    //
    @RequestMapping("json_ignore.json")
    public @ResponseBody Foo testJsonIgnore() {
        Foo foo = new Foo();

        return foo;
    }


    @RequestMapping("test_binding.json")
    public @ResponseBody Param testBindResult(@Valid Param param, BindingResult result) {

        logger.info("result:" + result.toString());
        return param;
    }
}


class Foo {
    String name;

    Integer age;

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    // 在这个代码(Spring)里，json ignore不起作用
    @JsonIgnore
    public String getTrimName() {
        return name.trim();
    }
}


class Param {
    @NotEmpty(message = "name不能为空")
    String name;

    @NotNull
    Integer age;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}