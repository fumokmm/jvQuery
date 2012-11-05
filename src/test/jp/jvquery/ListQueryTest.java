package jp.jvquery;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.*;

import jp.jvquery.Functions.Act;
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

}
