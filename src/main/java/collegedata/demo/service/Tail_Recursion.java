package collegedata.demo.service;

import java.math.BigInteger;

/**
 * author:panle
 * Date:2018/11/26
 * Time:14:28
 * 尾递归和递归的阶乘实现
 */
public class Tail_Recursion {

    //递归
    static BigInteger recursion(int L){
        if (L < 0) {
            return BigInteger.valueOf(0);
        }else if(L == 0 || L == 1){
            return BigInteger.valueOf(1);
        }else{
            return BigInteger.valueOf(L).multiply(recursion(L - 1))  ;
        }
    }

    //尾递归,再返回时直接调用函数,把每一次运算的结果放到参数中去运算
    static BigInteger tailRecursion(BigInteger i,BigInteger result){
        if (i.compareTo(BigInteger.valueOf(0)) < 0){
            return BigInteger.valueOf(0);
        }
        else if( i.equals(1)){
            return result;
        }else if( i.equals(0)){
            return BigInteger.valueOf(1);
        }else{
            return tailRecursion(i.subtract(BigInteger.valueOf(1)),result.multiply(BigInteger.valueOf(1)));
        }
    }

    public static void main(String[] args) {
//        BigInteger lo = Tail_Recursion.recursion(1000000);
//        System.out.println(lo);
        BigInteger ll = Tail_Recursion.tailRecursion(BigInteger.valueOf(100000),BigInteger.valueOf(1));
        System.out.println(ll);
    }

}
