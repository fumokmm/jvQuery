package sample.jp.jvquery.samples;

import static jp.jvquery.JvQuery.*;

/**
 * 1から100までのFizzBuzzのサンプルです。
 */
public class FizzBuzz {
    public static void main(String[] args) {
	$($.range(1, 100)).map(new MapBlock<Integer, String>(){
	    @Override public String call(Integer a1) {
		return a1 % 15 == 0 ? "FizzBuzz" :
		       a1 % 5  == 0 ? "Buzz"     :
		       a1 % 3  == 0 ? "Fizz"     :
		       String.valueOf(a1);
	    }
	}).each(new EachBlock<String>(){
	    @Override public void call(String a1) {
		System.out.println(a1);
	    }
	});
    }
}
