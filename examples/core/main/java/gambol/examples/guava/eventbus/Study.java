package gambol.examples.guava.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executors;

class JDK {

    class Event {
        int type;
        Event(int type) {
            this.type = type;
        }
    }

    class MyObserver implements Observer {

        @Override
        public void update(Observable o, Object arg) {
            if (arg instanceof Event) {
                Event event = (Event) arg;
                switch (event.type) {
                    case 1: //handle
                    case 2: //hande
                }
            }
        }
    }

    class MyObservable extends Observable {
        private void do1() {
            this.notifyObservers(new Event(1));
        }

        private void do2() {
            this.notifyObservers(new Event(2));
        }
    }

    public static void main(String[] args) {
    }

}

class Guava {
    class Event {
        int type;
        Event(int type) {
            this.type = type;
        }
    }

    class MyObserver {
        @Subscribe
        public void handleEvent1(Event event1) {
            //handle
        }

        @Subscribe
        public void handleEvent2(Event event2) {
            //handle
        }
    }

    class MyObservable {
        EventBus myBus = new EventBus("my event bud");
        AsyncEventBus asyncEventBus = new AsyncEventBus(Executors.newSingleThreadExecutor());

        private void do1() {
            myBus.post(new Event(1));
        }

        private void do2() {
            myBus.post(new Event(2));
        }

        public void register(Object observer) {
            myBus.register(observer);
        }

        private void other() {
            asyncEventBus.post(new Event(3));
        }
    }

}

public class Study {
    public static void main(String[] args) {
        /** 先说为什么会有这个东西 */
        /**
         *  传统的Java实现中，监听者使用方法很少的接口——通常只有一个方法。这样做有一些缺点:
         *  1, 监听者类对给定事件类型，只能有单一处理逻辑；
         *  2, 监听者接口方法可能冲突；update函数名很通用的。
         *  3, 方法命名只和事件相关（handleChangeEvent），不能表达意图（recordChangeInJournal）；update是啥意思？表达什么事件？
         *  4, 继承方式，很不好，如果被监听者已经继承了其他类，则无法再次扩展
         *
         *  更加详细的参考：
         *   http://code.google.com/p/guava-libraries/wiki/EventBusExplained
         */



    }
}
