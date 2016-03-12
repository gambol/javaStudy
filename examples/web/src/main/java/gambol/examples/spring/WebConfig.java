package gambol.examples.spring;

import gambol.examples.spring.handler.JsonResponseMethodProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.lang.reflect.Method;
import java.util.List;

/*
*
*  这个配置貌似没有用，只有在spring-mvc.xml里的配置，才有效果
*
*  <mvc:return-value-handlers>
            <bean  class="gambol.examples.spring.handler.JsonResponseMethodProcessor"></bean>
        </mvc:return-value-handlers>
*
* */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurationSupport {

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {

        RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter();

        // FIXME: set JsonBodyMethodProcessor as the first
        List<HandlerMethodReturnValueHandler> returnValueHandlers = getDefaultReturnValueHandlers(adapter);
        returnValueHandlers.add(0, new JsonResponseMethodProcessor());
        adapter.setReturnValueHandlers(returnValueHandlers);

        return adapter;
    }

    @SuppressWarnings("unchecked")
    private List<HandlerMethodReturnValueHandler> getDefaultReturnValueHandlers(RequestMappingHandlerAdapter adapter) {
        try {
            Method method = RequestMappingHandlerAdapter.class.getDeclaredMethod("getDefaultReturnValueHandlers");
            method.setAccessible(true);
            return (List<HandlerMethodReturnValueHandler>) method.invoke(adapter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}