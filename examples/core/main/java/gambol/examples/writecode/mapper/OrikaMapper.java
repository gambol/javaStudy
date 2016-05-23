package gambol.examples.writecode.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * Created by zhenbao.zhou on 16/5/10.
 */
public class OrikaMapper {

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    @Getter @Setter @AllArgsConstructor
    public static class PersonSource {
        String name;
        int age;
    }

    @Getter @Setter @ToString
    public static class PersonDest {

        String _name;
        String _age;
    }

    void original() {

        //Dozer
        PersonSource personSource = new PersonSource("gb", 20);
        PersonDest personDest = new PersonDest();
        personDest.set_age(String.valueOf(personSource.getAge()));
        personDest.set_name(personSource.getName());
    }

    public void test() {
        mapperFactory.classMap(PersonSource.class, PersonDest.class)
                .field("name", "_name")
                .field("age", "_age")
                .byDefault()
                .register();
        PersonSource personSource = new PersonSource("gb", 20);
        PersonDest p = mapperFactory.getMapperFacade().map(personSource, PersonDest.class);

        System.out.print("dest:" + p);

    }

    public static void main(String[] args) {
        new OrikaMapper().test();
    }
}
