package gambol.examples.writecode.mapper;

import gambol.examples.writecode.money.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dozer.DozerBeanMapper;

import java.math.BigDecimal;

/**
 * 无法正确的转换money
 * Created by zhenbao.zhou on 16/11/1.
 */
public class DozerMapperTest {


    @Getter
    @Setter
    @AllArgsConstructor
    public static class PersonSource {
        String name;
        int age;

        BigDecimal amount;

        public BigDecimal getAmount() {
            System.out.println("fuck amount");
            return amount;
        }
    }

    @Getter @Setter @ToString
    public static class PersonDest {

        String name;
        String _age;
        Money amount;
    }

    public void test() {
        PersonSource personSource = new PersonSource("gb", 20, new BigDecimal(15));

        /**
         * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
         */
        DozerBeanMapper dozer = new DozerBeanMapper();
        PersonDest p = dozer.map(personSource, PersonDest.class);
        System.out.print("dest:" + p);

    }

    public static void main(String[] args) {
        new DozerMapperTest().test();
    }
}
