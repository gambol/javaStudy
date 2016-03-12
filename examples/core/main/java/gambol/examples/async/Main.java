package gambol.examples.async;

/**
 * User: zhenbao.zhou
 * Date: 3/30/15
 * Time: 3:45 PM
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("main BEGIN");
        Host host = new Host();

        Data data1 = host.request(10, 'A');

        Data data2 = host.request(20, 'B');

        Data data3 = host.request(30, 'C');

        System.out.println("main otherJob BEGIN");

        // 去做别的事情
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {

        }

        System.out.println("main otherJob END");
        System.out.println("data1 = " + data1.getContent());

        System.out.println("data2 = " + data2.getContent());

        System.out.println("data3 = " + data3.getContent());

        System.out.println("main END");

    }


}
