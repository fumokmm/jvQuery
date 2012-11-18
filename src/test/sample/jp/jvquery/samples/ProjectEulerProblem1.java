package sample.jp.jvquery.samples;

import static jp.jvquery.JvQuery.*;

import java.util.*;

/**
 * Project Euler Problem 1 のサンプルです。
 * @see http://projecteuler.net/index.php?section=problems&id=1
 * @see http://odz.sakura.ne.jp/projecteuler/index.php?Problem%201
 * @author fumokmm
 */
public class ProjectEulerProblem1 {
    /**
     * Problem 1<br/>
     * 10未満の自然数のうち、3 もしくは 5 の倍数になっているものは 3, 5, 6, 9 の4つがあり、<br/>
     * これらの合計は 23 になる。<br/>
     * 同じようにして、1,000 未満の 3 か 5 の倍数になっている数字の合計を求めよ。<br/>
     */
    public static void main(String[] args) {
	// 1〜1000未満のリストを生成
	List<Integer> nums = $.range(1, 999);
	
	// 答えを求める
	int answer = $(nums).filter(new FilterBlock<Integer>(){
	    @Override public Boolean call(Integer v1) {
		return v1 % 3 == 0 || v1 % 5 == 0;
	    }
	}).reduce(new ReduceBlock<Integer, Integer>() {
	    @Override public Integer call(Integer v1, Integer v2) {
		return v1 + v2;
	    }
	}).get();

	// 結果出力
	$.println(answer); // => 233168
    }
}
