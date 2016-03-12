package gambol.examples.stackoverflow;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhenbao.zhou on 15/5/16.
 */
public class Reflection {


    public void printAll(String className) throws  Exception{

        List<Object> list = new ArrayList<Object>();
        list.add(3);
        list.add(new HashMap<String, String>());
        list.add(2.2);
        list.add(new Reflection());

        for(Object o : list) {
            if (className.equals(o.getClass().getName())) {
                System.out.println(o);
            } else {
                System.out.println(" not match:" + o.getClass().getName());
            }

            if (o.getClass().isInstance(Class.forName(className).newInstance())) {
                System.out.println(" ahahha :" + o);
            } else {
                System.out.println(" ooo");
            }
        }

    }

    public static void main(String[] args) throws  Exception{
        new Reflection().printAll("gambol.examples.stackoverflow.Reflection");
    }
}
