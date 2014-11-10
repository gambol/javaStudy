package org.gambol.modules.jackson;

import junit.framework.TestCase;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * 主要来测试 objectMapper.configure(SerializationConfig.Feature.REQUIRE_SETTERS_FOR_GETTERS, true);
 * User: zhenbao.zhou
 * Date: 10/25/14
 * Time: 4:39 PM
 */
public class JacksonTest extends TestCase{

    private static ObjectMapper objectMapper;

    Foo foo = new Foo();

    @Test
    public void testOriginMapper() throws  Exception{
        objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValueAsString(foo);
            failBecauseExceptionWasNotThrown(NullPointerException.class);
        } catch (Exception e) {
             assertThat(e).hasMessageContaining("NullPointerException");
        }
    }

    @Test
    public void testNoGetter() throws  Exception{
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationConfig.Feature.REQUIRE_SETTERS_FOR_GETTERS, true);

        assertThat(objectMapper.writeValueAsString(foo)).isEqualTo("{\"name\":null,\"age\":null}");
    }

    @Test
    public void testJsonIgnore() throws  Exception{
        objectMapper = new ObjectMapper();
        Foo2 foo2 = new Foo2();

        System.out.println(objectMapper.writeValueAsString(foo2));
        assertThat(objectMapper.writeValueAsString(foo2)).isEqualTo("{\"name\":null,\"age\":null}");
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

    public String getTrimName() {
        return name.trim();
    }
}

class Foo2 {
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

    @JsonIgnore
    public String getTrimName() {
        return name.trim();
    }
}
