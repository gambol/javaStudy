package gambol.examples.thread;

/**
 * Created by zhenbao.zhou on 15/4/11.
 */
public class Executors {

    enum QmonitorNameEnum {

        SPA_PRICE_ELONG_ROOM_ADAPTER_ERROR("aa", "bb", "艺龙报价适配错误");

        private QmonitorNameEnum(String level, String type, String desc) {
            this.level = level;
            this.type = type;
            this.desc = desc;
        }

        private String level;
        private String type;
        // 监控描述
        private String desc;

    }


    public static void main(String[] args) {
        int loop = 5000000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            for(int j = 0; j < loop; j++) {
                long k = i * j;
            }
        }
        long stop = System.currentTimeMillis();

        System.out.println("cost time : " + (stop - start));

        QmonitorNameEnum a = QmonitorNameEnum.SPA_PRICE_ELONG_ROOM_ADAPTER_ERROR;

        System.out.println("a name:" + a.name());

    }

}
