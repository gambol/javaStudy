package gambol.examples.stackoverflow;

import java.util.LinkedList;

/**
 * Created by zhenbao.zhou on 15/9/24.
 */
public class Test {

    public static void main(String[] args) {

        LinkedList<Integer> list = new LinkedList<>();
        list.push(1);
        list.push(2);
        list.push(3);

        System.out.println(list.getLast());
        System.out.println(list.getFirst());


        for(int i = 1; i < 20000; i = i+2) {
            if ((i % 3 == 0) & (i % 4 == 1) && (i % 5 == 4) && (i % 6 == 3) && (i % 7 == 5) && (i % 8 == 1) && (i % 9 ==0)) {
                System.out.println("i = " + i);
            }
        }
    }
}
