package gambol.examples.extendtest;

import gambol.examples.mappers.JacksonMapper;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by zhenbao.zhou on 17/5/10.
 */
public class MapTest {

    @Getter @Setter
    static class A {
        BigDecimal a ;
        BigDecimal b;


    }

    @Getter @Setter
    static class B {
        BigDecimal a;
        BigDecimal d;
    }

    public static void main(String[] args) {
        A a = new A();
        a.setA(new BigDecimal(4));
        a.setB(new BigDecimal(3));

        B b = new B();
        b.setD(new BigDecimal(33));

        Map result = JacksonMapper.string2Obj(JacksonMapper.obj2String(a),Map.class);

        result.putAll(JacksonMapper.string2Obj(JacksonMapper.obj2String(b), Map.class));

        System.out.println("haha:" + JacksonMapper.obj2String(result));
    }

}
