package gambol.examples.guava.base.throwable;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

/**
 * @author kris.zhang
 */
@Slf4j
public class Study {

    public static void throwsException1() {
        throw new NullPointerException("root");
    }

    public static void throwsException2() throws SQLException {
        try {
            throwsException1();
        } catch(Throwable throwable){
            throw new SQLException("exception",throwable);
        }
    }

    public static void main(String[] args) throws SQLException {
        try {
            /** NullPointerException -> SQLException */
            throwsException2();
        } catch(Throwable throwable) {
            /** 比较常用的是这些方法，获得根异常，遍历 */
            Throwable cause = Throwables.getRootCause(throwable);

            /** 获得异常栈信息 */
            String stackTrace = Throwables.getStackTraceAsString(throwable);

            /** 简化异常链的遍历 */
            for (Throwable t : Throwables.getCausalChain(throwable)) {  log.error("",t); }

            /** 以下的用法我没有使用过，最后一个是包装成RuntimeException，最好别用*/
            Throwables.propagateIfInstanceOf(throwable,NullPointerException.class);
            Throwables.propagateIfInstanceOf(throwable,SQLException.class);
            Throwables.propagateIfPossible(throwable); //error or RuntimeException
            throw Throwables.propagate(throwable);//最好使用 throw new RuntimeException()，意义明确
        }
    }
}
