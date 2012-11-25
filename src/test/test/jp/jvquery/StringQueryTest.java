package test.jp.jvquery;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.*;

import jp.jvquery.JvQuery.*;

import static jp.jvquery.JvQuery.*;
import org.junit.*;

public class StringQueryTest {

    @Test
    public void sizeのテスト() {
	String nullStr = null;
	assertThat($(nullStr).size(), is(0));
	assertThat($("").size(), is(0));
	assertThat($("a").size(), is(1));
	assertThat($(" ").size(), is(1));
	assertThat($("abc").size(), is(3));
	assertThat($("１２３").size(), is(3));
    }

    @Test
    public void getAllのテスト() {
	String nullStr = null;
	assertThat("nullは空文字", $(nullStr).getAll(), is(""));
	assertThat($("").getAll(), is(""));
	assertThat($("a").getAll(), is("a"));
	assertThat($(" ").getAll(), is(" "));
	assertThat($("abc").getAll(), is("abc"));
	assertThat($("１２３").getAll(), is("１２３"));
    }

    @Test
    public void getのテスト() {
	String nullStr = null;
	assertThat($(nullStr).get(-1), is(""));
	assertThat($(nullStr).get(0), is(""));
	assertThat("#get(0)と同じ", $(nullStr).get(), is(""));
	assertThat($(nullStr).get(1), is(""));

	assertThat($("").get(-1), is(""));
	assertThat($("").get(0), is(""));
	assertThat("#get(0)と同じ", $("").get(), is(""));
	assertThat($("").get(1), is(""));
	
	assertThat($("a").get(-1), is(""));
	assertThat($("a").get(0), is("a"));
	assertThat("#get(0)と同じ", $("a").get(), is("a"));
	assertThat($("a").get(1), is(""));

	assertThat($("ab").get(-1), is(""));
	assertThat($("ab").get(0), is("a"));
	assertThat("#get(0)と同じ", $("ab").get(), is("a"));
	assertThat($("ab").get(1), is("b"));
	assertThat($("ab").get(2), is(""));

	assertThat($("abc").get(-1), is(""));
	assertThat($("abc").get(0), is("a"));
	assertThat("#get(0)と同じ", $("abc").get(), is("a"));
	assertThat($("abc").get(1), is("b"));
	assertThat($("abc").get(2), is("c"));
	assertThat($("abc").get(3), is(""));
    }

    @Test
    public void getOptionのテスト() {
	String nullStr = null;
	assertThat($(nullStr).getOption(-1).hasValue(), is(false));
	assertThat($(nullStr).getOption(0).hasValue(), is(false));
	assertThat("#getOption(0)と同じ", $(nullStr).getOption().hasValue(), is(false));
	assertThat($(nullStr).getOption(1).hasValue(), is(false));

	assertThat($($.list()).getOption(-1).hasValue(), is(false));
	assertThat($($.list()).getOption(0).hasValue(), is(false));
	assertThat("#getOption(0)と同じ", $($.list()).getOption().hasValue(), is(false));
	assertThat($($.list()).getOption(1).hasValue(), is(false));
	
	assertThat($("a").getOption(-1).hasValue(), is(false));
	assertThat($("a").getOption(0).hasValue(), is(true));
	assertThat("#getOption(0)と同じ", $("a").getOption().hasValue(), is(true));
	assertThat($("a").getOption(0).get(), is("a"));
	assertThat("#getOption(0)と同じ", $("a").getOption().get(), is("a"));
	assertThat($("a").getOption(1).hasValue(), is(false));

	assertThat($("ab").getOption(-1).hasValue(), is(false));
	assertThat($("ab").getOption(0).hasValue(), is(true));
	assertThat("#getOption(0)と同じ", $("ab").getOption().hasValue(), is(true));
	assertThat($("ab").getOption(0).get(), is("a"));
	assertThat("#getOption(0)と同じ", $("ab").getOption().get(), is("a"));
	assertThat($("ab").getOption(1).hasValue(), is(true));
	assertThat($("ab").getOption(1).get(), is("b"));
	assertThat($("ab").getOption(2).hasValue(), is(false));

	assertThat($("abc").getOption(-1).hasValue(), is(false));
	assertThat($("abc").getOption(0).hasValue(), is(true));
	assertThat("#getOption(0)と同じ", $("abc").getOption().hasValue(), is(true));
	assertThat($("abc").getOption(0).get(), is("a"));
	assertThat("#getOption(0)と同じ", $("abc").getOption().get(), is("a"));
	assertThat($("abc").getOption(1).hasValue(), is(true));
	assertThat($("abc").getOption(1).get(), is("b"));
	assertThat($("abc").getOption(2).hasValue(), is(true));
	assertThat($("abc").getOption(2).get(), is("c"));
	assertThat($("abc").getOption(3).hasValue(), is(false));
    }

    @Test
    public void eachのテスト() {
	String strs = "12345";
	final int[] num = new int[]{ 0 };
	$(strs).each(new EachBlock<String>(){
	    @Override
	    public void call(String a1) {
		num[0] += Integer.parseInt(a1);
	    }
	});
	assertThat(num[0], is(15));
    }
    
    @Test
    public void eachのテスト_Empty() {
	String strs = "";
	final int[] num = new int[]{ 0 };
	// ループしない
	$(strs).each(new EachBlock<String>(){
	    @Override
	    public void call(String a1) {
		num[0] += Integer.parseInt(a1);
	    }
	});
	assertThat(num[0], is(0));
    }

    @Test
    public void eachのテスト_Null() {
	String strs = null;
	final int[] num = new int[]{ 0 };
	// ループしない
	$(strs).each(new EachBlock<String>(){
	    @Override
	    public void call(String a1) {
		num[0] += Integer.parseInt(a1);
	    }
	});
	assertThat(num[0], is(0));
    }
    
    @Test
    public void eachのテスト_NullBlock() {
	String strs = "12345";
        final int[] num = new int[]{ 0 };
        EachBlock<String> block = null;
        $(strs).each(block);
        assertThat(num[0], is(0));
    }

}
