package gambol.examples.serialize.benchmark;

/**
 * Created by zhenbao.zhou on 16/3/12.
 */
public interface BenchmarkSet extends Nameable {

    /**
     * 序列化
     * @return
     */
    Benchmark serialize() throws Exception;

    /**
     * 反序列化
     * @return
     */
    Benchmark deserialize() throws Exception;


    int encodeSize() throws Exception;

}
