package gambol.examples.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * User: zhenbao.zhou
 * Date: 4/7/15
 * Time: 4:56 PM
 */
public class ReferenceExample {
    public static void main(String[] args) {

        Object strongRef = new Object();  // 强引用，在strongRef被引用期间，永远都不会被jvm gc回收掉

        // 软引用，softReference在oom之前 被jvm回收
        ReferenceQueue<Object> queue = new ReferenceQueue<Object>();
        SoftReference<Object> softReference = new SoftReference<Object>(strongRef, queue);

        // 弱引用, 有可能会被jvm回收
        Object o = new Object();
        ReferenceQueue<Object> weakQueue = new ReferenceQueue<Object>();
        WeakReference<Object> ref = new WeakReference<Object>(o, weakQueue);
        o = null;
        System.out.println(ref.get());  // 输出obj的地址
        System.gc();
        System.out.println(ref.get());  // 输出null


        //
        WeakHashMap<Object, String> map = new WeakHashMap<Object, String>();
        map.put(new Object(), "test");
        System.out.println(map);
        System.gc();
        System.out.println(map);
    }
}
