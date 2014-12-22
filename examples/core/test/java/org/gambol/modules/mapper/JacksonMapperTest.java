package org.gambol.modules.mapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import junit.framework.TestCase;
import gambol.examples.mappers.JacksonMapper;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

/**
 * User: zhenbao.zhou
 * Date: 9/25/14
 * Time: 4:10 PM
 */
public class JacksonMapperTest extends TestCase {

    @Test
    public void testToJson() throws Exception {
        // Bean
        TestBean bean = new TestBean("A");
        String beanString = JacksonMapper.obj2String(bean);
        System.out.println("Bean:" + beanString);
        assertThat(beanString).isEqualTo("{\"name\":\"A\"}");

        // Map
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("name", "A");
        map.put("age", 2);
        String mapString = JacksonMapper.obj2String(map);
        System.out.println("Map:" + mapString);
        assertThat(mapString).isEqualTo("{\"name\":\"A\",\"age\":2}");

        // List<String>
        List<String> stringList = Lists.newArrayList("A", "B", "C");
        String listString = JacksonMapper.obj2String(stringList);
        System.out.println("String List:" + listString);
        assertThat(listString).isEqualTo("[\"A\",\"B\",\"C\"]");

        // List<Bean>
        List<TestBean> beanList = Lists.newArrayList(new TestBean("A"), new TestBean("B"));
        String beanListString = JacksonMapper.obj2String(beanList);
        System.out.println("Bean List:" + beanListString);
        assertThat(beanListString).isEqualTo("[{\"name\":\"A\"},{\"name\":\"B\"}]");

        // Bean[]
        TestBean[] beanArray = new TestBean[] { new TestBean("A"), new TestBean("B") };
        String beanArrayString = JacksonMapper.obj2String(beanArray);
        System.out.println("Array List:" + beanArrayString);
        assertThat(beanArrayString).isEqualTo("[{\"name\":\"A\"},{\"name\":\"B\"}]");
    }


    public static class TestBean {

        private String name;
        private String defaultValue = "";
        private String nullValue = null;

        public TestBean() {
        }

        public TestBean(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getNullValue() {
            return nullValue;
        }

        public void setNullValue(String nullValue) {
            this.nullValue = nullValue;
        }

        @Override
        public String toString() {
            return "TestBean [defaultValue=" + defaultValue + ", name=" + name + ", nullValue=" + nullValue + "]";
        }
    }

}
