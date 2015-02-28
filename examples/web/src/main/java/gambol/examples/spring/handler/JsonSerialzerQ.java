package gambol.examples.spring.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.primitives.Ints;

/**
 * JSON接口序列化工具
 *  抄袭过来的
 *
 * @author zhongyuan.zhang
 */
final class JsonSerialzerQ {
/*
    private static final Logger logger = LoggerFactory.getLogger(JsonSerializer.class);

    static void write(Object value, Method method, HttpServletRequest request, HttpServletResponse response) {

        JsonBody meta = method.getAnnotation(JsonBody.class);
        Redirect rmeta = method.getAnnotation(Redirect.class);

        logger.debug("value={}, meta={}, redirect={}", value, meta, rmeta);

        MapperBuilder mb = MapperBuilder.create();

        String debug = Strings.emptyToNull(request.getParameter(meta.debugTag()));
        if (debug != null) {
            for (String feature : SPLIT_COMMA.split(debug)) {
                boolean state = true;
                if (feature.charAt(0) == '!') {
                    state = false;
                    feature = feature.substring(1);
                }

                try {
                    mb.configure(JsonFeature.valueOf(feature), state);
                } catch (Throwable t) {
                    value = new Exception("JsonFeature set error: " + feature, t);
                    break;
                }
            }
        }

        // always work
        mb.configure(meta.enable(), meta.disable()).disable(JsonFeature.AUTO_CLOSE_TARGET);
        JsonMapper om = mb.build();

        String callback = Strings.emptyToNull(request.getParameter(meta.callback()));

        try {
            if (rmeta == null) {
                response(callback, buildValue(value, meta.version()), om, response);
            } else if (callback == null) {
                response.getWriter().write("parameter '" + meta.callback() + "' required");
            } else if (!RequestUtil.match(rmeta.hosts(), callback)) {
                response.getWriter().write("forbidden to redirect to " + callback);
            } else {
                redirect(callback, buildValue(value, meta.version()), rmeta, om, response);
            }
        } catch (IOException e) {
            logger.warn("response write error", e);
        }
    }

    private static void response(String callback,  Object value, JsonMapper om, HttpServletResponse response) throws IOException {
        if (callback != null) {
            response.setContentType("application/javascript; charset=UTF-8");
            // call setContentType before getWriter(), or else UTF-8 doesn't work
            Writer writer = response.getWriter();
            writer.write(callback);
            writer.write('(');
            om.writeValue(writer, value );
            writer.write(')');
        } else {
            response.setContentType("application/json; charset=UTF-8");
            om.writeValue(response.getWriter(), value);
        }
    }

    private static void redirect(String callback, Object value, Redirect rmeta, JsonMapper om, HttpServletResponse response) throws IOException {

        String data = om.writeValueAsString(value);
        String token = RequestUtil.rtoken(data, rmeta.secret());
        logger.debug("data={}, secret={}", data, rmeta.secret());

        StringBuilder sb = new StringBuilder(callback);
        // ?r=$data
        sb.append(callback.contains("?") ? '&' : '?').append(rmeta.dataKey()).append('=').append(encode(data));
        // &cbt=$token
        sb.append('&').append(rmeta.tokenKey()).append('=').append(token);
        response.sendRedirect(sb.toString());
    }


    private static final Pattern encode = Pattern.compile("\\+");
    private static final String encode(String r) throws UnsupportedEncodingException {
        // + => %20
        return encode.matcher(URLEncoder.encode(r, Charsets.UTF_8.name())).replaceAll("%20");
    }

    private static Object buildValue(Object value, Version version) {

        if (value instanceof JsonV2 || value instanceof DataJsonV1 || value instanceof ErrorJsonV1) {
            return value;
        }

        switch (version) {
        case v1:
            return makeV1(value);
        case v2:
            return makeV2(value);
        default:
            throw new RuntimeException("Bad json version, " + version);
        }
    }

    private static Object makeV1(final Object value) {

        if (value instanceof CodeMessage) {
            final CodeMessage msg = (CodeMessage) value;

            if (msg.getStatus() == OK) {
                return new DataJsonV1<Object>(msg.getData());
            }
            return new ErrorJsonV1(msg.getStatus(), msg.getMessage());
        }

        if (value instanceof Throwable) {
            final Throwable e = (Throwable) value;

            return new ErrorJsonV1(SYSTEM_ERROR, e.getMessage());
        }

        return new DataJsonV1<Object>(value);
    }

    private static Object makeV2(final Object value) {

        if (value instanceof CodeMessage) {
            final CodeMessage msg = (CodeMessage) value;
            // avoid flushing cause/stackTrace/localizedMessage
            return new JsonV2<Object>(msg.getStatus(), msg.getMessage(), msg.getData());
        }

        if (value instanceof Throwable) {
            final Throwable e = (Throwable) value;
            return new JsonV2<Object>(SYSTEM_ERROR, e.getMessage(), null);
        }

        return new JsonV2<Object>(OK, null, value);
    }

    // convert string to message in the format: {status},{message}
    static CodeMessage parseConfig(String config, Exception ex) {

        Iterator<String> i = SPLIT_COMMA.split(config).iterator();
        Integer status = Ints.tryParse(i.next());
        if (status == null)
            status = CodeMessage.SYSTEM_ERROR;

        String message = i.hasNext() ? i.next() : ex.getMessage();

        return new RuntimeErrorMessage(status, message);
    }
    */
}