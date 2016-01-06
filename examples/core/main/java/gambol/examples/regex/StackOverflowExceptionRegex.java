package gambol.examples.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhenbao.zhou on 15/7/23.
 */
public class StackOverflowExceptionRegex {
    public static final String REGEX_A = "/search/(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*+)-(.*)-(.*)-(.*)-(.*)[/]?(\\d+?)";
    public static final String REGEX_B = "/search/(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)-(.*)/?([0-9]+)?";

    public static final String REGEX_C = "/gangaotai/(\\d+)_(\\d+)_(\\d+)_(.*).html";

    static Pattern patternA = Pattern.compile(REGEX_A);
    static Pattern patternB = Pattern.compile(REGEX_B);
    static Pattern patternC = Pattern.compile(REGEX_C);

    private void match(Pattern pattern ,String s, String patternName) {
        Matcher m = pattern.matcher(s);
        System.out.println("------------------PatternName:" + patternName + "  string:" + s + " ----START----");
        if (m.find( )) {
            for(int i = 0;i < 15; i++) {
                System.out.println("Found value: " + m.group(i));

            }
        } else {
            System.out.println("no found");
        }

        System.out.println("----------------b--PatternName:" + patternName + "  string:" + s + " ----END----");
    }


    public static boolean checkSpecialChars(String inputstr, String regex)
    {
        if (inputstr == null || "".equals(inputstr))
        {
            return false;
        }
        return Pattern.compile(regex).matcher(inputstr).matches(); //注意是此处matches()方法抛的异常
    }


    public static void main(String[] args) {
        //String str =  "/search/groupasdf-a-a-asdf%E9%87%8Dadsf%E5%BA%86--asdf---%E5%9C%A3%E6%89%98%E9%87%8C%E5%B0%BC-17500----20150816-4400";

        String k = "3";
        for(int i = 0; i < 10; i++) {
            k = k+k;
        }


        //String l =   "/search/a\\-bc-a\\-bc-a\\-bc-a\\-bc-a\\-bc-a\\-bc-a\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\" +
        //        "-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bc"+"-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bc"+"-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bc"+"-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bc"+"-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bc"+"-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bc"+"-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bc"+"-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bc"+"-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bc"+"-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bc"+"-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bc"+"-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bc"+"-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bc"+"-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bc";


        String str =  "/search/a\\-bc-a\\-bc-a\\-bc-a\\-bc-a\\-bc-a\\-bc-a\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\" +
                "-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\-bca\\-bc-a\\/-bc";

        String strb = "/gangaotai/444444444444444_444444_444444444_444444444_444444_444444444444444.html";
        String strB = "/gangaotai/" + k + "_" + k + "_"+k+"_" + k + ".html";

        String strc = "/search/" + k + "-" + k + "-" + k + "-" + k +"-" + k + "-" + k + "-" + k + "-" + k + "-" + k + "-" + k + "-" + k +"-" + k + "-" + k + "-" + k + "-" + k;

        StackOverflowExceptionRegex example = new StackOverflowExceptionRegex();
       // example.match(patternA, str, REGEX_A);
        //example.match(patternA, strc, REGEX_A);
        // example.match(patternB, str, REGEX_B);
        // example.match(patternB, strc, REGEX_B);


        //example.match(patternC,strb, REGEX_C);
       // example.match(patternC,strB, REGEX_C);

        String regex = "([a-z]|//d)*";
        String inputStr = "";
        for (int i = 0; i < 1; i++) //此处的值为>=400则会马上抛异常
        {
          //  example.match(patternB, str, REGEX_B);
        }
        example.match(patternA, str, REGEX_A);
        // System.out.println("字符串长度为："+inputStr.length());
       // boolean flag = checkSpecialChars(inputStr, regex);


    }
}
