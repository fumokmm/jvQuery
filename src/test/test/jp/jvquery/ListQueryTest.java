package test.jp.jvquery;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.*;

import static jp.jvquery.JvQuery.*;
import org.junit.*;

public class ListQueryTest {

    @Test
    public void eachTest() {
	List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	final int[] num = new int[]{ 0 };
	$(nums).each(new Act<Integer>(){
	    @Override
	    public void run(Integer a1) {
		num[0] += a1;
	    }
	});
	assertThat(num[0], is(55));
    }
    
    @Test
    public void eachTest_Empty() {
	List<Integer> nums = $.list();
	final int[] num = new int[]{ 0 };
	// ループしない
	$(nums).each(new Act<Integer>(){
	    @Override
	    public void run(Integer a1) {
		num[0] += a1;
	    }
	});
	assertThat(num[0], is(0));
    }

    @Test
    public void eachTest_Null() {
	List<Integer> nums = null;
	final int[] num = new int[]{ 0 };
	// ループしない
	$(nums).each(new Act<Integer>(){
	    @Override
	    public void run(Integer a1) {
		num[0] += a1;
	    }
	});
	assertThat(num[0], is(0));
    }
    
    @Test
    public void mapTest() {
	List<Integer> nums = $.list(1, 2, 3);
	assertThat($(nums).map(new Conv<Integer, String>(){
	    @Override
	    public String convert(Integer a1) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a1; i++) {
		    sb.append(String.valueOf(a1));
		}
		return sb.toString();
	    }
	}).get(), is($.list("1", "22", "333")));
    }
}
