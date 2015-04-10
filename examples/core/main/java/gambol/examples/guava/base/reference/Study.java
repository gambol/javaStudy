package gambol.examples.guava.base.reference;

import com.google.common.base.FinalizablePhantomReference;
import com.google.common.base.FinalizableReferenceQueue;
import com.google.common.base.FinalizableSoftReference;

import java.lang.ref.*;

/**
 * @author kris.zhang
 */
public class Study {

    static final ReferenceQueue referenceQueue = new ReferenceQueue();

    public static void main(String[] args) {
        Object hard = new Object();
        SoftReference<Object> soft = new SoftReference<>(new Object());/*软引用可用来实现内存敏感的高速缓存*/
        WeakReference<Object> weak = new WeakReference<>(new Object());/*强引用中断感知*/
        PhantomReference<Object> phantom = new PhantomReference<>(new Object(),referenceQueue);/*虚引用主要用来跟踪对象被垃圾回收器回收的活动*/

        //while
        Reference queue;
        while ((queue = referenceQueue.poll()) != null) {
            System.out.println("normal:" + queue);
        };

        //call back here

        /**
         * 可以看到原始使用jdk无法回调，即当引用被回收的时候我们只能手动判断
         */

        /** guava 提供了更好的办法 */
        FinalizableReferenceQueue frq = new FinalizableReferenceQueue();

        Reference<Object> reference = new FinalizablePhantomReference<Object>(new Object(), frq) {
            @Override
            public void finalizeReferent() {
                Object object = get();//object == null
            }
        };

    }

}
