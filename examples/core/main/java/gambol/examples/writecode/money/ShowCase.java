package gambol.examples.writecode.money;


import java.math.BigDecimal;

/**
 * 演示数字运算的工具 Created by zhenbao.zhou on 16/5/23.
 */
public class ShowCase {


    // 转换, 适配各种场景
    void transfer() {

        Money money = Money.of(3);
        System.out.println("money:" + money + " money string:" + money.toMoneyString()); // 3.0 3.00

        Money a = money.plus(Money.of(4));
        money.plus(4);
        a = money.plus(new BigDecimal(4.41234462));

        System.out.println("a:" + a    // 7.4123446200000
                + "\n a string:" + a.toMoneyString()    // 7.41
                + "\n  a store value:" + a.toStoreValue()  // 7412345
                + "\n a to string decimal:" + a.toStoreDecimal());  // 7.412345

    }

    //
    void calculate() {

        Money money = Money.of(4.1);
        money.minus(new BigDecimal(4.4));
        money.minus(4.4);
        money.minus(Money.of(4.4));
        System.out.println("major:" + money.minusMajor(12));
        System.out.println("minor:" + money.minusMinor(12L, 4));


        money.plus(new BigDecimal(123));
        money.plus(Money.of(44));

        money.divide(4.1);
    }



    public static void main(String[] args) {
        new ShowCase().transfer();
        new ShowCase().calculate();
    }
}
