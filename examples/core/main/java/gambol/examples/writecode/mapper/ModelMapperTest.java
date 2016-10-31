package gambol.examples.writecode.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;


/**
 * 无法正确的转化money
 * Created by zhenbao.zhou on 16/10/31.
 */
public class ModelMapperTest {


    @Getter
    @Setter
    @AllArgsConstructor
    public static class PersonSource {
        String name;
        int age;

        int amount;

        public int getAmount() {
            System.out.println("fuck amount. amount:" + amount);
            return amount;
        }
    }

    @Getter @Setter @ToString
    public static class PersonDest {

        String name;
        String _age;
        K amount;
    }

    @AllArgsConstructor
    public static class K{
        long v;
    }

    public void test() {
        PersonSource personSource = new PersonSource("gb", 20, 15);
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<PersonSource, PersonDest> personMap = new PropertyMap<PersonSource, PersonDest>() {
            protected void configure() {
                map().set_age(String.valueOf(source.getAge()));
                map().setAmount(new K(source.getAmount()));
            }
        };
        modelMapper.addMappings(personMap);
        PersonDest p = modelMapper.map(personSource, PersonDest.class);

        System.out.print("dest:" + p);

    }

    public static void main(String[] args) {
        new ModelMapperTest().test();
    }
}
