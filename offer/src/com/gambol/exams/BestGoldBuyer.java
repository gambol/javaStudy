package com.gambol.exams;


import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 题目：
 * 知道每一天黄金的价格， 怎么出售能够获取最好的收益
 * User: zhenbao.zhou
 * Date: 9/28/14
 * Time: 8:32 PM
 */
public class BestGoldBuyer {

    public long goldRush(int[] goldPrices, int money) {
        checkNotNull(goldPrices, "输入正确的价格");
        for (int i = 0; i < goldPrices.length; i++) {
            checkArgument(goldPrices[i] > 0, "价格不能为负数");
        }
        checkArgument(money > 0, "拥有的金钱不能为负数");

        int minPrice = Integer.MAX_VALUE;
        long maxProfit = 0;

        for (int goldPrice : goldPrices) {
            if (goldPrice < minPrice) {
                minPrice = goldPrice;
            }

            long nowProfit = money * goldPrice / minPrice;
            if (nowProfit > maxProfit) {
                maxProfit = nowProfit;
            }
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        BestGoldBuyer bgb = new BestGoldBuyer();
        int[] goldPrices = new int[] {3, 4,6,8,2,7};
        int money = 100;

        long profit = bgb.goldRush(goldPrices, money);
        System.out.println("profit is :" + profit);
    }

}
