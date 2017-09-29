package gambol.examples.password;

import java.util.Random;

/**
 * Created by zhenbao.zhou on 2017/9/1.
 */
public class Pass {

    private Random random = new Random();
    private static final char[] CHAR_SPECIAL_ALL = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM123456789".toCharArray();
    private static final int PASS_LENGTH = CHAR_SPECIAL_ALL.length;

    private String randomPass(int length) {
        String pass = "";
        for (int i = 0; i < length; i++) {
            int index =  random.nextInt(PASS_LENGTH);
            pass = pass +  CHAR_SPECIAL_ALL[index];
        }

        return pass;
    }

    public static void main(String[] args) {
        String pass = new Pass().randomPass(10);
        System.err.println("pss:" + pass);
        pass = new Pass().randomPass(10);
        System.err.println("pss:" + pass);

        pass = new Pass().randomPass(12);
        System.err.println("pss:" + pass);
        pass = new Pass().randomPass(10);
        System.err.println("pss:" + pass);
        pass = new Pass().randomPass(15);
        System.err.println("pss:" + pass);
        pass = new Pass().randomPass(10);
        System.err.println("pss:" + pass);
        pass = new Pass().randomPass(20);
        System.err.println("pss:" + pass);

        String info = "{"
                + "     \"portfolioId\" : \"234\","
                + "     \"portfolioName\" : \"养老基金\","
                + "     \"modelInfo\": {"
                + "          \"version\": \"124\","
                + "          \"modelList\": ["
                + "              {"
                + "                  \"type\": \"VAM\","
                + "                  \"symbolWeightList\": ["
                + "                   {"
                + "                       \"symbol\": \"VOO\","
                + "                       \"weight\": \"0.1383\""
                + "                   },"
                + "                   {"
                + "                       \"symbol\": \"VGK\","
                + "                       \"weight\": \"0.2809\""
                + "                   },"
                + "                   {"
                + "                       \"symbol\": \"EWJ\","
                + "                       \"weight\": \"0.0329\""
                + "                   },"
                + "                   {"
                + "                       \"symbol\": \"EWA\","
                + "                       \"weight\": \"0\""
                + "                   },"
                + "                   {"
                + "                       \"symbol\": \"ILF\","
                + "                       \"weight\": \"0\""
                + "                   },"
                + "                   {"
                + "                       \"symbol\": \"RSX\","
                + "                       \"weight\": \"0\""
                + "                   },"
                + "                   {"
                + "                       \"symbol\": \"AAXJ\","
                + "                       \"weight\": \"0.2329\""
                + "                   },"
                + "                   {"
                + "                       \"symbol\": \"ASHR\","
                + "                       \"weight\": \"0.182\""
                + "                   },"
                + "                   {"
                + "                       \"symbol\": \"DBC\","
                + "                       \"weight\": \"0\""
                + "                   },"
                + "                   {"
                + "                       \"symbol\": \"GLD\","
                + "                       \"weight\": \"0\""
                + "                   },"
                + "                   {"
                + "                       \"symbol\": \"VDE\","
                + "                       \"weight\": \"0\""
                + "                   },"
                + "                   {"
                + "                       \"symbol\": \"VNQ\","
                + "                       \"weight\": \"0\""
                + "                   },"
                + "                   {"
                + "                       \"symbol\": \"JNK\","
                + "                       \"weight\": \"0\""
                + "                   },"
                + "                   {"
                + "                       \"symbol\": \"EMB\","
                + "                       \"weight\": \"0\" "
                + "                   },"
                + "                   {"
                + "                       \"symbol\": \"LQD\", "
                + "                       \"weight\": \"0.0992\""
                + "                   }, "
                + "                   { "
                + "                       \"symbol\": \"IEF\",  "
                + "                       \"weight\": \"0.0338\""
                + "                   },"
                + "                   {"
                + "                       \"symbol\": \"----\", "
                + "                       \"weight\": \"0\"     "
                + "                   } "
                + "               ]"
                + "            } "
                + "       ]  "
                + "    } "
                + "} ";

        System.err.println(info);
    }
}
