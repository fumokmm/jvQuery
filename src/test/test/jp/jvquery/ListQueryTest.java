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
	$(nums).each(new EachBlock<Integer>(){
	    @Override
	    public void call(Integer a1) {
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
	$(nums).each(new EachBlock<Integer>(){
	    @Override
	    public void call(Integer a1) {
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
	$(nums).each(new EachBlock<Integer>(){
	    @Override
	    public void call(Integer a1) {
		num[0] += a1;
	    }
	});
	assertThat(num[0], is(0));
    }
    
    @Test
    public void filterのテスト() {
	List<String> strList = $.list("a", "b", "c");
	List<String> expected = $.list("a", "c");
	assertThat($(strList).filter(new FilterBlock<String>(){
	    @Override
	    public Boolean call(String a1) {
		return ! a1.equals("b");
	    }
	}).get(), is(expected));
    }
    
    @Test
    public void filterのテスト_IndexIs() {
	List<String> strList = $.list("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
	assertThat("偶数(even)",
	    $(strList).filter(IndexIs.even).get(), is(
	        $.list("a", "c", "e", "g", "i")
	    )
	);
	assertThat("奇数(odd)",
	    $(strList).filter(IndexIs.odd).get(), is(
		$.list("b", "d", "f", "h", "j")
	    )
	);
    }
    
    @Test
    public void foldLeftのテスト() {
	List<Integer> nums = $.range(1, 10);
	assertThat($(nums).foldLeft(0, new FoldBlock<Integer, Integer>(){
	    @Override
	    public Integer call(Integer result, Integer num) {
		return result + num;
	    }
	}).get(0), is(55));
    }
    
    @Test
    public void mapTest() {
	List<Integer> nums = $.list(1, 2, 3);
	assertThat($(nums).map(new MapBlock<Integer, String>(){
	    @Override
	    public String call(Integer a1) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a1; i++) {
		    sb.append(String.valueOf(a1));
		}
		return sb.toString();
	    }
	}).get(), is($.list("1", "22", "333")));
    }
    
    @Test
    public void eqのテスト() {
	List<Integer> nums = $.list(100, 200, 300, 400, 500);
	assertThat($(nums).eq(-1).isEmpty(), is(true));
	assertThat($(nums).eq(0).getOption(0).get(), is(100));
	assertThat($(nums).eq(1).getOption(0).get(), is(200));
	assertThat($(nums).eq(2).getOption(0).get(), is(300));
	assertThat($(nums).eq(3).getOption(0).get(), is(400));	
	assertThat($(nums).eq(4).getOption(0).get(), is(500));
	assertThat($(nums).eq(5).isEmpty(), is(true));
	assertThat($(nums).eq(6).isEmpty(), is(true));
    }

    @Test
    public void ltのテスト() {
	List<Integer> nums = $.list(100, 200, 300, 400, 500);
	assertThat($(nums).lt(-1).isEmpty(), is(true));
	assertThat($(nums).lt(0).isEmpty(), is(true));
	assertThat($(nums).lt(1).get(), is($.list(100)));
	assertThat($(nums).lt(2).get(), is($.list(100, 200)));
	assertThat($(nums).lt(3).get(), is($.list(100, 200, 300)));
	assertThat($(nums).lt(4).get(), is($.list(100, 200, 300, 400)));
	assertThat($(nums).lt(5).get(), is($.list(100, 200, 300, 400, 500)));
	assertThat($(nums).lt(6).get(), is($.list(100, 200, 300, 400, 500)));
    }

    @Test
    public void gtのテスト() {
	List<Integer> nums = $.list(100, 200, 300, 400, 500);
	assertThat($(nums).gt(-1).get(), is($.list(100, 200, 300, 400, 500)));
	assertThat($(nums).gt(0).get(), is($.list(200, 300, 400, 500)));
	assertThat($(nums).gt(1).get(), is($.list(300, 400, 500)));
	assertThat($(nums).gt(2).get(), is($.list(400, 500)));
	assertThat($(nums).gt(3).get(), is($.list(500)));
	assertThat($(nums).gt(4).isEmpty(), is(true));
	assertThat($(nums).gt(5).isEmpty(), is(true));
	assertThat($(nums).gt(6).isEmpty(), is(true));
    }

    @Test
    public void firstのテスト() {
	List<Integer> num = $.list(10);
	List<Integer> nums5 = $.list(100, 200, 300, 400, 500);
	List<Integer> 空リスト = $.list();
	assertThat($(num).first().getOption(0).get(), is(10));
	assertThat($(nums5).first().getOption(0).get(), is(100));
	assertThat($(空リスト).first().getOption(0).hasValue(), is(false));
    }

    @Test
    public void lastのテスト() {
	List<Integer> num = $.list(10);
	List<Integer> nums5 = $.list(100, 200, 300, 400, 500);
	List<Integer> 空リスト = $.list();
	assertThat($(num).last().getOption(0).get(), is(10));
	assertThat($(nums5).last().getOption(0).get(), is(500));
	assertThat($(空リスト).last().getOption(0).hasValue(), is(false));
    }
    
    @Test
    public void getのテスト() {
	assertThat($(null).get(), is($.list()));
	assertThat($($.list()).get(), is($.list()));
	assertThat($($.list(1)).get(), is($.list(1)));
	assertThat($($.list(1, 2)).get(), is($.list(1, 2)));
	assertThat($($.list(1, 2, 3)).get(), is($.list(1, 2, 3)));
    }

    @Test
    public void getのテスト_index() {
	assertThat($(null).get(-1), is(nullValue()));
	assertThat($(null).get(0), is(nullValue()));
	assertThat($(null).get(1), is(nullValue()));

	assertThat($($.list()).get(-1), is(nullValue()));
	assertThat($($.list()).get(0), is(nullValue()));
	assertThat($($.list()).get(1), is(nullValue()));
	
	assertThat($($.list(1)).get(-1), is(nullValue()));
	assertThat($($.list(1)).get(0), is(1));
	assertThat($($.list(1)).get(1), is(nullValue()));

	assertThat($($.list(1, 2)).get(-1), is(nullValue()));
	assertThat($($.list(1, 2)).get(0), is(1));
	assertThat($($.list(1, 2)).get(1), is(2));
	assertThat($($.list(1, 2)).get(2), is(nullValue()));

	assertThat($($.list(1, 2, 3)).get(-1), is(nullValue()));
	assertThat($($.list(1, 2, 3)).get(0), is(1));
	assertThat($($.list(1, 2, 3)).get(1), is(2));
	assertThat($($.list(1, 2, 3)).get(2), is(3));
	assertThat($($.list(1, 2, 3)).get(3), is(nullValue()));
    }

    @Test
    public void getOptionのテスト() {
	assertThat($(null).getOption(-1).hasValue(), is(false));
	assertThat($(null).getOption(0).hasValue(), is(false));
	assertThat($(null).getOption(1).hasValue(), is(false));

	assertThat($($.list()).getOption(-1).hasValue(), is(false));
	assertThat($($.list()).getOption(0).hasValue(), is(false));
	assertThat($($.list()).getOption(1).hasValue(), is(false));
	
	assertThat($($.list(1)).getOption(-1).hasValue(), is(false));
	assertThat($($.list(1)).getOption(0).hasValue(), is(true));
	assertThat($($.list(1)).getOption(0).get(), is(1));
	assertThat($($.list(1)).getOption(1).hasValue(), is(false));

	assertThat($($.list(1, 2)).getOption(-1).hasValue(), is(false));
	assertThat($($.list(1, 2)).getOption(0).hasValue(), is(true));
	assertThat($($.list(1, 2)).getOption(0).get(), is(1));
	assertThat($($.list(1, 2)).getOption(1).hasValue(), is(true));
	assertThat($($.list(1, 2)).getOption(1).get(), is(2));
	assertThat($($.list(1, 2)).getOption(2).hasValue(), is(false));

	assertThat($($.list(1, 2, 3)).getOption(-1).hasValue(), is(false));
	assertThat($($.list(1, 2, 3)).getOption(0).hasValue(), is(true));
	assertThat($($.list(1, 2, 3)).getOption(0).get(), is(1));
	assertThat($($.list(1, 2, 3)).getOption(1).hasValue(), is(true));
	assertThat($($.list(1, 2, 3)).getOption(1).get(), is(2));
	assertThat($($.list(1, 2, 3)).getOption(2).hasValue(), is(true));
	assertThat($($.list(1, 2, 3)).getOption(2).get(), is(3));
	assertThat($($.list(1, 2, 3)).getOption(3).hasValue(), is(false));
    }
}
