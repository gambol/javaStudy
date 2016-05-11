package gambol.examples.serialize.benchmark.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import gambol.examples.serialize.benchmark.Benchmark;
import gambol.examples.serialize.benchmark.BenchmarkSet;
import gambol.examples.serialize.benchmark.model.Foo;
import gambol.examples.serialize.benchmark.model.FooBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by zhenbao.zhou on 16/3/12.
 */
public class KryoBenchmark implements BenchmarkSet {
    private final String name;

    private final int numChildren;

    public KryoBenchmark(int numChildren) {
        this.numChildren = numChildren;
        name = "kryo[" + numChildren + "]";
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Benchmark serialize() throws Exception {
        final Foo foo = FooBuilder.buildObject("parent", numChildren);
        Kryo kryo = new Kryo();


        return new Benchmark() {
            @Override
            public String name() {
                return name + " serialize";
            }

            @Override
            public void go() throws Exception {
                Output output = new Output(new ByteArrayOutputStream());
                kryo.writeObject(output, foo);
                output.close();
            }
        };
    }

    @Override
    public Benchmark deserialize() throws Exception {
        final Foo foo = FooBuilder.buildObject("parent", numChildren);
        Kryo kryo = new Kryo();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Output output = new Output(bos);
        kryo.writeObject(output, foo);
        output.close();
        byte[] bytes = bos.toByteArray();

        return new Benchmark() {
            @Override
            public String name() {
                return name + " deserialize";
            }

            @Override
            public void go() throws Exception {
                Input input = new Input(new ByteArrayInputStream(bytes));
                Foo foo2 = kryo.readObject(input, Foo.class);
                input.close();
            }
        };
    }

    @Override
    public int encodeSize() throws Exception {
        final Foo foo = FooBuilder.buildObject("parent", numChildren);
        Kryo kryo = new Kryo();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Output output = new Output(bos);
        kryo.writeObject(output, foo);
        output.close();
        byte[] bytes = bos.toByteArray();
        return bytes.length;
    }

    public static void main(String[] args) throws Exception {
        KryoBenchmark benchmark = new KryoBenchmark(1);

        Benchmark b = benchmark.deserialize();

        b.go();
    }

}
