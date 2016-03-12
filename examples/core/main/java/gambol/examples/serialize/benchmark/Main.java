package gambol.examples.serialize.benchmark;

import com.google.common.base.Stopwatch;
import gambol.examples.serialize.benchmark.hessian.HessianBenchmark;
import gambol.examples.serialize.benchmark.jackson.JacksonBenchmark;
import gambol.examples.serialize.benchmark.javaSerialize.JavaBenchmark;
import gambol.examples.serialize.benchmark.kryo.KryoBenchmark;
import gambol.examples.serialize.benchmark.protobuf.ProtobufBenchmark;
import gambol.examples.serialize.benchmark.protostuff.ProtostuffBenchmark;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhenbao.zhou on 16/3/12.
 */
public class Main {

    final static int LOOP = 15;
    final static int ITERATION = 1000;

    public static void main(String[] args) throws Exception {
        System.out.printf("%30s %8s %8s %8s  (MILLISECONDS)\n", "test", "min", "max", "avg");
        System.out.println("-----------------------------------------------------------------");

        for (int numChildren : new int[]{0, 32, 64, 1024}) {
            System.out.println("\nwith " + numChildren + " children");

            BenchmarkSet[] sets = new BenchmarkSet[]{
                    new JacksonBenchmark(numChildren),
                    new ProtobufBenchmark(numChildren),
                    new JavaBenchmark(numChildren),
                    new KryoBenchmark(numChildren),
                    new HessianBenchmark(numChildren),
                    new ProtostuffBenchmark(numChildren)
            };

            for (BenchmarkSet set : sets) {
                run(set);
            }

            System.out.println("\nencoded sizes:");
            for (BenchmarkSet set : sets) {
                System.out.printf("%30s   %s\n", set.name(), set.encodeSize());
            }
            System.out.println();
        }
    }


    static void run(BenchmarkSet set) throws Exception {
        bench(set.serialize());
        bench(set.deserialize());
    }

    static void bench(Benchmark b) throws Exception {

        long min = Long.MAX_VALUE;
        long max = 0;
        long avg;

        // 预热jvm
        for(int i = 0; i < 10; i++) {
            b.go();
        }

        Stopwatch watch = Stopwatch.createStarted();

        for (int i = 0; i < LOOP; i++) {

            Stopwatch loopWatch = Stopwatch.createStarted();
            for (int j = 0; j < ITERATION; j++) {
                b.go();
            }
            long elapsed = loopWatch.stop().elapsed(TimeUnit.MILLISECONDS);

            max = Math.max(elapsed, max);
            min = Math.min(elapsed, min);

        }

        avg = watch.stop().elapsed(TimeUnit.MILLISECONDS) / LOOP;
        display(b, min, max, avg);

    }

    static void display(Benchmark benchmark, long min, long max, long avg) {
        System.out.printf("%30s %8d %8d %8d\n", benchmark.name(), min, max, avg);
    }


}
