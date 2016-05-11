package gambol.examples.serialize.benchmark.hessian;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import gambol.examples.serialize.benchmark.Benchmark;
import gambol.examples.serialize.benchmark.BenchmarkSet;
import gambol.examples.serialize.benchmark.model.Foo;
import gambol.examples.serialize.benchmark.model.FooBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by zhenbao.zhou on 16/3/12.
 */
public class HessianBenchmark implements BenchmarkSet {
    private final String name;

    private final int numChildren;

    public HessianBenchmark(int numChildren) {
        this.numChildren = numChildren;
        name = "hessian[" + numChildren + "]";
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
                Hessian2Output output = new Hessian2Output(new ByteArrayOutputStream());
                output.writeObject(foo);
                //output.close();
            }
        };
    }

    @Override
    public Benchmark deserialize() throws Exception {
        final Foo foo = FooBuilder.buildObject("parent", numChildren);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(bos);
        output.writeObject(foo);
        output.close();
        byte[] bytes = bos.toByteArray();

        return new Benchmark() {
            @Override
            public String name() {
                return name + " deserialize";
            }

            @Override
            public void go() throws Exception {
                Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(bytes));
                Foo foo2 = (Foo) input.readObject(Foo.class);
                //input.close();
            }
        };
    }

    @Override
    public int encodeSize() throws Exception {
        final Foo foo = FooBuilder.buildObject("parent", numChildren);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(bos);
        output.writeObject(foo);
        output.close();
        byte[] bytes = bos.toByteArray();
        return bytes.length;
    }

    public static void main(String[] args) throws Exception {
        HessianBenchmark benchmark = new HessianBenchmark(1);

        Benchmark b = benchmark.deserialize();

        b.go();
    }

}
