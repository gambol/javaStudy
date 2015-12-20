package gambol.examples.spring.handler;

import com.google.common.base.Strings;
import gambol.examples.mappers.JacksonMapper;
import gambol.examples.spring.annotation.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;

/**
 * User: zhenbao.zhou
 * Date: 2/10/15
 * Time: 7:42 PM
 */
public final class JsonSerializer {

    private static final Logger logger = LoggerFactory.getLogger(JsonSerializer.class);

    static void write(Object value, Method method, HttpServletRequest request, HttpServletResponse response) {

        JsonResponse meta = method.getAnnotation(JsonResponse.class);

        logger.debug("value={}, meta={}", value, meta);

        String callback = Strings.emptyToNull(request.getParameter(meta.callback()));

        try {
            response(callback, value, response);
        } catch (IOException e) {
            logger.error("error in response request");
        } catch (Exception e) {
            logger.info("error", e);
        }Ø
    }


    private static void response(String callback,  Object value, HttpServletResponse response) throws IOException {
        String str = null;
        if (callback != null) {
            response.setContentType("application/javascript; charset=UTF-8");
            // call setContentType before getWriter(), or else UTF-8 doesn't work
            str = JacksonMapper.obj2JsonP(callback, value);
        } else {
            response.setContentType("application/json; charset=UTF-8");
            str = JacksonMapper.obj2String(value);
        }
        response.getWriter().write(str);
    }

}
