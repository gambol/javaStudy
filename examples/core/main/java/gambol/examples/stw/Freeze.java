package gambol.examples.stw;

import java.math.BigInteger;

/**
 * Created by zhenbao.zhou on 16/1/27.
 */
public class Freeze {

    public static void main(String[] args) {
        new Thread(){ @Override public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
            }
            System.out.println("fuck, power start");
            System.out.println("pow result:" + BigInteger.valueOf(5).pow(100000000));
        }}.start();

// Loop, allocating memory and periodically logging progress, so illustrate GC pause times.
        byte[] b;
        for (int outer = 0; ; outer++) {
            long startMs = System.currentTimeMillis();
            for (int inner = 0; inner < 100000; inner++) {
                b = new byte[1000];
            }

            long t = System.currentTimeMillis() - startMs;

                System.out.println("Iteration " + outer + " took " + (System.currentTimeMillis() - startMs) + " ms");
        }
    }
}
