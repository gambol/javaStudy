package com.gambol.exams;

/**
 * Created by zhenbao.zhou on 15/9/8.
 */
public class ContinousNum {

    /*
     * 假设正整数 n 能表示为 i 个连续正整数之和且其第一个数为 x，则 n = x * i + (i - 1) * i/2，其中 n, x, i 都为正整数，
     * 所以如果 x = (n - (i-1)*i/2) / i 为正整数(即分子对i取模等于0)，则 n 就能表示为i个连续正整数之和。
     * i 的取值范围为[2,y](y=1+sqrt(1+8n)/2,可通过一元二次不等式求得)
     * 或者简单地认为i的取值范围为[2,n/2+1]
     */
    public static void bestPrintContinuousNum(int target){
        int n=target;
        for(int i=2;(2*i-1)*(2*i-1)-1<8*n;i++){//将求根转化为平方。例如 i<sqrt(x)-->i*i<n
            if((n-i*(i-1)/2)%i==0){
                int x=(n-i*(i-1)/2)/i;
                int j=0;
                while(j<i){
                    System.out.print(x+" ");
                    x++;
                    j++;
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        bestPrintContinuousNum(10);
    }
}
