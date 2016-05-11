package gambol.examples.spring.handler;

import gambol.examples.spring.annotation.JsonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: zhenbao.zhou
 * Date: 10/15/14
 * Time: 6:10 PM
 */
public class JsonResponseMethodProcessor implements HandlerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        boolean t=  returnType.getMethodAnnotation(JsonResponse.class) != null;
        return t;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType,
         ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        //JsonSerializer.write(returnValue, returnType.getMethod(), request, response);
    }
}
