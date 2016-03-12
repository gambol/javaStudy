package gambol.examples.serialize.benchmark.jackson;

import gambol.examples.mappers.JacksonMapper;
import gambol.examples.serialize.benchmark.Benchmark;
import gambol.examples.serialize.benchmark.BenchmarkSet;
import gambol.examples.serialize.benchmark.model.Foo;
import gambol.examples.serialize.benchmark.model.FooBuilder;
import org.codehaus.jackson.type.TypeReference;

/**
 * Created by zhenbao.zhou on 16/3/12.
 */
public class JacksonBenchmark implements BenchmarkSet {

    private final String name;

    private final int numChildren;

    public JacksonBenchmark(int numChildren) {
        this.numChildren = numChildren;
        name = "jackson[" + numChildren + "]";
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Benchmark serialize() throws Exception {
        final Foo foo = FooBuilder.buildObject("parent", numChildren);
        return new Benchmark() {
            @Override
            public String name() {
                return name + " serialize";
            }

            @Override
            public void go() {
                JacksonMapper.obj2String(foo);
            }
        };
    }

    @Override
    public Benchmark deserialize() throws Exception {
        final Foo obj = FooBuilder.buildObject("parent", numChildren);
        String json = JacksonMapper.obj2String(obj);
        return new Benchmark() {
            @Override
            public String name() {
                return name + " deserialize";
            }

            @Override
            public void go() {
                JacksonMapper.string2Obj(json, new TypeReference<Foo>() {
                });
            }
        };
    }

    @Override
    public int encodeSize() throws Exception {
        Foo obj = FooBuilder.buildObject("parent", numChildren);
        String json = JacksonMapper.obj2String(obj);
        return json.length();
    }

}
