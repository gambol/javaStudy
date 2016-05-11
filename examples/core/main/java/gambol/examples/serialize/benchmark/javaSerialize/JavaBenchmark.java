package gambol.examples.serialize.benchmark.javaSerialize;

import gambol.examples.serialize.benchmark.Benchmark;
import gambol.examples.serialize.benchmark.BenchmarkSet;
import gambol.examples.serialize.benchmark.model.Foo;
import gambol.examples.serialize.benchmark.model.FooBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by zhenbao.zhou on 16/3/12.
 */
public class JavaBenchmark implements BenchmarkSet {

    private final String name;

    private final int numChildren;

    public JavaBenchmark(int numChildren) {
        this.numChildren = numChildren;
        name = "java[" + numChildren + "]";
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
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(bos);
                os.writeObject(foo);
                os.flush();
                os.close();
                bos.close();

            }
        };
    }

    @Override
    public Benchmark deserialize() throws Exception {
        byte[] bytes;

        final Foo foo = FooBuilder.buildObject("parent", numChildren);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(foo);
        bytes = bos.toByteArray();
        return new Benchmark() {
            @Override
            public String name() {
                return name + " deserialize";
            }

            @Override
            public void go() throws Exception {
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bis);
                Foo foo2 = (Foo) ois.readObject();
                ois.close();
                bis.close();
            }
        };


    }

    @Override
    public int encodeSize() throws Exception {
        final Foo foo = FooBuilder.buildObject("parent", numChildren);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(foo);
        byte[] bytes = bos.toByteArray();
        return bytes.length;
    }
}
