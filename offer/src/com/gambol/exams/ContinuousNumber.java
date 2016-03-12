package com.gambol.exams;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 给一副扑克，判断是否是顺子
 * 其中王 可以 担任任何一个牌
 * User: zhenbao.zhou
 * Date: 9/28/14
 * Time: 8:47 PM
 */
public class ContinuousNumber {

    // 1 - 13 分别对应 A - K ,
    // 0  对应是王
    // 思路是，首先选出王的数量,
    // 然后判断 数组中最大和最小值之差 必须是 numbers.length - 王的数量
    // 最后数字里有没有重复的
    public boolean isContinuous(int[] cards) {
        checkNotNull(cards, "输入正确的数字");
        for (int card : cards) {
            checkArgument(card >= 0 && card < 14, "扑克必须正确");
        }

        int min = Integer.MAX_VALUE;
        int max = -1;
        int numberOfKing = 0;
        for (int card : cards) {
            if (card == 0) {
                numberOfKing++;
                continue;
            }

            if (card > max) {
                max = card;
            }

            if (card < min) {
                min = card;
            }
        }

        // 判断最大最小之差
        if (cards.length - 1 + numberOfKing < max - min) {
            return false;
        }

        int[] dupFlags = new int[max - min + 1];
        for (int card : cards) {
            if (card == 0) {
                continue;
            }

            dupFlags[card - min]++;
            if (dupFlags[card - min] > 1) {
                // 出现重复的，肯定不连续啦
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        ContinuousNumber cn = new ContinuousNumber();
        int[] cards = new int[] { 1, 3, 4, 2,6};
        boolean isContinuous = cn.isContinuous(cards);
        System.out.println(isContinuous);
    }

}
