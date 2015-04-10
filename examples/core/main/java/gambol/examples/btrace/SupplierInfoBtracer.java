package gambol.examples.btrace;
import com.sun.btrace.*;
import com.sun.btrace.annotations.*;


import java.util.concurrent.atomic.AtomicInteger;

import static com.sun.btrace.BTraceUtils.*;

import java.util.Map;
/**
 * User: zhenbao.zhou
 * Date: 1/20/15
 * Time: 3:03 PM
 */
public class SupplierInfoBtracer {

        private static final String KLASS_NAME = "com.qunar.vacation.b2c.dao.OrderDao";
        private static final String METHOD_NAME = "countOrderByMap";

        private static AtomicInteger ai = Atomic.newAtomicInteger(0);
        private static Map<Long, AnyType[]> argsMap = Collections.newHashMap();

        @OnMethod(clazz = KLASS_NAME, method = METHOD_NAME)
        public static void startMethod(AnyType[] args) {
            Atomic.incrementAndGet(ai);
            Collections.put(argsMap, threadId(currentThread()), args);
        }

        @OnMethod(clazz = KLASS_NAME, method = METHOD_NAME, location = @Location(Kind.RETURN))
        public static void endMethod(@ProbeClassName String pcn, @ProbeMethodName String pmn, @Duration long duration) {
            long ms = duration / 1000000L;
            print("Time: ");
            print(BTraceUtils.timestamp("yyyy-MM-dd HH:mm:ss"));
            print(", count: ");
            print(str(Atomic.get(ai)));
            print(", cost time: ");
            print(str(ms));
            print(", caller: ");
            String caller = BTraceUtils.jstackStr(1);
            print(BTraceUtils.substr(caller, 0, BTraceUtils.length(caller) - 1));
            print(",");
            print(ms > 3000 ? "++" : "  ");
            print(" args: ");
            printArray(Collections.get(argsMap, threadId(currentThread())));
        }


}
