package gambol.examples.writecode.mapper;

import gambol.examples.writecode.money.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.ObjectFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;

import java.math.BigDecimal;

/**
 * OK的
 * Created by zhenbao.zhou on 16/5/10.
 */
public class OrikaMapper {

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    @Getter @Setter @AllArgsConstructor
    public static class PersonSource {
        String name;
        int age;

        Money amount;
        BigDecimal fee;

        String x;
    }

    @Getter @Setter @ToString
    public static class PersonDest {

        String _name;
        String _age;
        Money amount;
        Money fee;

        Wallet x;
    }

    @AllArgsConstructor @ToString
    public static class Wallet {
        String k;

        String v;

        public Wallet(String k) {
            this.k = k;
        }
    }

    void original() {

        //Dozer
        PersonSource personSource = new PersonSource("gb", 20, Money.of(15), new BigDecimal(2), "5");
        PersonDest personDest = new PersonDest();
        personDest.set_age(String.valueOf(personSource.getAge()));
        personDest.set_name(personSource.getName());
    }

    public class MyConverter extends CustomConverter<BigDecimal,Money> {
        public Money convert(BigDecimal source, Type<? extends Money> destinationType) {
            // return a new instance of destinationType with all properties filled
            return Money.of(source);
        }
    }

//    public class WalletConvert extends CustomConverter<String, Wallet> {
//        public Wallet convert(String source, Type<? extends Wallet> destinationType) {
//            // return a new instance of destinationType with all properties filled
//            return new Wallet(source);
//        }
//    }

    public class MoneyConvert extends CustomConverter<Money,Money> {
        public Money convert(Money source, Type<? extends Money> destinationType) {
            // return a new instance of destinationType with all properties filled
            return source;
        }
    }


    public void test() {
        mapperFactory.classMap(PersonSource.class, PersonDest.class)
                .field("name", "_name")
                .field("age", "_age")
                .byDefault()
                .register();
        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new MyConverter());
        converterFactory.registerConverter(new MoneyConvert());

        PersonSource personSource = new PersonSource("gb", 20, Money.of(15), new BigDecimal(2), "wall");
        PersonDest p = mapperFactory.getMapperFacade().map(personSource, PersonDest.class);

        System.out.print("dest:" + p);

    }

    public static void main(String[] args) {
        new OrikaMapper().test();
    }
}
