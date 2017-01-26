package gambol.examples.web;

import gambol.examples.spring.service.AbstractConsole;
import gambol.examples.spring.service.ConsoleService;
import gambol.examples.spring.service.ContainerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by zhenbao.zhou on 16/6/22.
 */
@RequestMapping("/a/")
@Controller
public class AController {

    @Resource
    ConsoleService consoleService;

    @Resource
    ContainerService containerService;

    @RequestMapping("/a.json")
    public @ResponseBody  String a() {
        try {
            AbstractConsole a = consoleService;

            System.out.println("hahah");
            containerService.setAbstractConsole(consoleService);
            // a.test();

            containerService.callConsole();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "kkk";
    }


    @RequestMapping("/b.html")
    public  String b() {

        return "helloWorld";
    }
}
