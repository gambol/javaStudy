package gambol.examples.serialize.benchmark.protostuff;

import gambol.examples.serialize.benchmark.Benchmark;
import gambol.examples.serialize.benchmark.BenchmarkSet;
import gambol.examples.serialize.benchmark.model.Foo;
import gambol.examples.serialize.benchmark.model.FooBuilder;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhenbao.zhou on 16/3/12.
 */
public class ProtostuffBenchmark implements BenchmarkSet {

    private final String name;

    private final int numChildren;

    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    public ProtostuffBenchmark(int numChildren) {
        this.numChildren = numChildren;
        name = "protostuff[" + numChildren + "]";
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
            public void go() throws Exception {
                LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
                try {
                    Schema<Foo> schema = getSchema(Foo.class);
                    ProtostuffIOUtil.toByteArray(foo, schema, buffer);
                } catch (Exception e) {
                    throw new IllegalStateException(e.getMessage(), e);
                } finally {
                    buffer.clear();
                }
            }
        };
    }

    @Override
    public Benchmark deserialize() throws Exception {
        final Foo foo = FooBuilder.buildObject("parent", numChildren);

        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        Schema<Foo> schema = getSchema(Foo.class);
        byte[] bytes = ProtostuffIOUtil.toByteArray(foo, schema, buffer);
        buffer.clear();

        return new Benchmark() {
            @Override
            public String name() {
                return name + " deserialize";
            }

            @Override
            public void go() throws Exception {
                try {
                    Foo foo2 = new Foo();
                    Schema<Foo> schema = getSchema(Foo.class);
                    ProtostuffIOUtil.mergeFrom(bytes, foo2, schema);

                } catch (Exception e) {
                    throw new IllegalStateException(e.getMessage(), e);
                }
            }
        };


    }

    @Override
    public int encodeSize() throws Exception {
        final Foo foo = FooBuilder.buildObject("parent", numChildren);

        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        Schema<Foo> schema = getSchema(Foo.class);
        byte[] bytes = ProtostuffIOUtil.toByteArray(foo, schema, buffer);
        buffer.clear();

        return bytes.length;
    }


    private static <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            if (schema != null) {
                cachedSchema.put(cls, schema);
            }
        }
        return schema;
    }

    public static void main(String[] args) throws Exception {
        ProtostuffBenchmark benchmark = new ProtostuffBenchmark(1);

        Benchmark b = benchmark.deserialize();

        b.go();
    }
}
