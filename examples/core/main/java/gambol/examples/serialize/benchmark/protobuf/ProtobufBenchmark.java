package gambol.examples.serialize.benchmark.protobuf;

import com.google.common.collect.Lists;
import gambol.examples.serialize.benchmark.Benchmark;
import gambol.examples.serialize.benchmark.BenchmarkSet;
import gambol.examples.serialize.benchmark.protobuf.model.Foo;
import gambol.examples.serialize.benchmark.protobuf.model.Type;

import java.util.List;

/**
 * Created by zhenbao.zhou on 16/3/12.
 */
public class ProtobufBenchmark implements BenchmarkSet {
    private  String name = "protobuf";

    private final int numChildren;

    public ProtobufBenchmark(int numChildren) {
        this.numChildren = numChildren;
        name = "protobuf[" + numChildren + "]";
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Benchmark serialize() {
        final Foo obj = buildObject("parent");
        return new Benchmark() {
            @Override
            public String name() {
                return name + " serialization";
            }

            @Override
            public void go() {
                obj.toByteArray();
            }
        };
    }

    @Override
    public Benchmark deserialize() {
        Foo obj = buildObject("parent");
        final byte[] bytes = obj.toByteArray();
        return new Benchmark() {
            @Override
            public String name() {
                return name + " deserialization";
            }

            @Override
            public void go() {
                try {
                    Foo.parseFrom(bytes);
                } catch (Exception e){
                    System.out.println("error in parse");
                }
            }
        };
    }

    @Override
    public int encodeSize() {
        Foo obj = buildObject("parent");
        byte[] bytes = obj.toByteArray();
        return bytes.length;
    }

    private Foo buildObject(String name) {
        return buildObject(name, true);
    }

    private Foo buildObject(String name, boolean children) {
        Foo.Builder builder = Foo.newBuilder()
                .setType(Type.FOO)
                .setFlag(true)
                .setNum32(Integer.MAX_VALUE)
                .setNum64(Long.MAX_VALUE)
                .setStr(name);


        if (children) {
            List<Foo> childList = Lists.newArrayList();
            for (int i = 0; i < numChildren; i++) {
                builder.addChildren(buildObject("child " + (i + 1), false));
            }
        }
        return builder.build();
    }
}
