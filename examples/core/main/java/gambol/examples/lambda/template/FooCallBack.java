package gambol.examples.lambda.template;

/**
 * Created by zhenbao.zhou on 15/10/29.
 */
public interface FooCallBack<T> {

    /**
     * 执行
     * @param t
     */
    void execute(T t) ;

}
