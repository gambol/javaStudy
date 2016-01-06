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
    }
}
