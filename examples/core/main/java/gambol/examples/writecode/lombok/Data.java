package gambol.examples.writecode.lombok;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by zhenbao.zhou on 16/5/12.
 */
@Getter
@Setter
@ToString
public class Data {

    String str1;
    String str2;
    String str3;

    public static void main(String[] args) {
        new Data().getStr1();
    }

}
