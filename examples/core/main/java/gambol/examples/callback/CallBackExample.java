package gambol.examples.callback;

/**
 * 演示Callback的使用
 * User: zhenbao.zhou
 * Date: 2/27/15
 * Time: 11:44 AM
 */
public class CallBackExample {

    interface CallbackInterface {
        void doSth();
    }

    static class Callee implements CallbackInterface {

        @Override
        public void doSth() {
            System.out.println(" callee do sth. I am done");
        }
    }

    static class Caller {

        CallbackInterface callbackInterface;

        public CallbackInterface getCallbackInterface() {
            return callbackInterface;
        }

        public void setCallbackInterface(CallbackInterface callbackInterface) {
            this.callbackInterface = callbackInterface;
        }

        public void comeIn() {
            System.out.println (" haha I am coming. I am caller");

            callbackInterface.doSth();
        }
    }


    public static void main(String[] args) {
        Caller caller = new Caller();
        caller.setCallbackInterface(new Callee());
        caller.comeIn();


        caller.setCallbackInterface(new CallbackInterface() {
            @Override
            public void doSth() {
                System.out.println(" wakaka , I am anynoumous callee. Hello");
            }
        });
        caller.comeIn();

    }

}



