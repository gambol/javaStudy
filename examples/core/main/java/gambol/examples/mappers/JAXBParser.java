package gambol.examples.mappers;

/**
 * copied from soa
 * Created by zhenbao.zhou on 16/1/6.
 */

import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.io.Closer;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 使用JAXB解析XML
 *
 * @author yong.chen
 * @since 2015-11-27 8:54 PM
 */
public class JAXBParser {

    // JAXB是线程安全的,缓存下来.
    private LoadingCache<Class, JAXBContext> cacheContext;
    private Map<String, String> namespacePrefixMapper;

    public JAXBParser(int cacheSize, Map<String, String> namespacePrefixMapper) {
        this.namespacePrefixMapper = namespacePrefixMapper;
        cacheContext = CacheBuilder.newBuilder().initialCapacity(cacheSize).expireAfterAccess(3, TimeUnit.DAYS)
                .build(new CacheLoader<Class, JAXBContext>() {
                    @Override
                    public JAXBContext load(Class clazz) throws Exception {
                        return JAXBContext.newInstance(clazz);
                    }
                });
    }

    public static final JAXBParser INSTANCE = new JAXBParser(128, Collections.<String, String> emptyMap());

    protected String nsPrefix(String namespaceUri, String suggestion) {
        String prefix = this.namespacePrefixMapper.get(namespaceUri);
        if (StringUtils.isNotEmpty(prefix)) {
            return prefix;
        }
        return suggestion;
    }

    public String toXML(Object object) {
        return toXML(object, false, false);
    }

    public String toXML(Object obj, boolean pretty, boolean withoutStartDoc) {
        Closer closer = Closer.create();
        try {
            JAXBContext context = cacheContext.get(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// 编码格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, pretty);// 是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, withoutStartDoc);// 是否省略xm头声明信息
            marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
                // 这地方为了解决 namespace=ns2的问题,如果有需要,自行添加映射
                @Override
                public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
                    return nsPrefix(namespaceUri, suggestion);
                }
            });

            StringWriter writer = new StringWriter();
            closer.register(writer);
            marshaller.marshal(obj, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closer.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T fromXML(String xml, Class<T> valueType) {
        Closer closer = Closer.create();
        try {
            JAXBContext context = cacheContext.get(valueType);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(xml);
            closer.register(reader);
            return (T) unmarshaller.unmarshal(reader);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        } finally {
            try {
                closer.close();
            } catch (IOException e) {
                Throwables.propagate(e);
            }
        }
    }

    public String formatXml(String prettyXml) {
        Closer closer = Closer.create();
        Reader in = new StringReader(prettyXml);
        closer.register(in);
        try {
            Document doc = (new SAXBuilder()).build(in);
            Format format = Format.getPrettyFormat();
            format.setEncoding("UTF-8");
            // format.setIndent(" ");
            format.setLineSeparator(System.lineSeparator());
            format.setOmitDeclaration(true);
            XMLOutputter outer = new XMLOutputter(format);
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            closer.register(bo);
            outer.output(doc, bo);
            return bo.toString();
        } catch (JDOMException | IOException e) {
            Throwables.propagate(e);
        } finally {
            try {
                closer.close();
            } catch (IOException e) {
                Throwables.propagate(e);
            }
        }
        return prettyXml;
    }
}