package test.jp.jvquery;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.*;

import static jp.jvquery.JvQuery.$;

import org.junit.*;

public class UtilityMethodsTest {

    @Test
    public void $listのテストその1() {
	List<String> list = $.list("あ", "い", "う");
	assertThat(list, is($.list("あ", "い", "う")));
    }
    @Test
    public void $listのテストその2() {
	List<Integer> list = $.list(0, 1, 2);
	assertThat($.size(list), is(3));
	assertThat(list.get(0), is(0));
	assertThat(list.get(1), is(1));
	assertThat(list.get(2), is(2));
    }
    
    @Test
    public void $listTest_Empty() {
	List<String> list = $.list();
	assertThat(list.isEmpty(), is(true));
    }

    @Test
    public void $nthのテスト_List() {
	List<Integer> list = $.range(1, 3);
	assertThat($.nth(list, -1), is(nullValue()));
	assertThat($.nth(list, 0), is(1));
	assertThat($.nth(list, 1), is(2));
	assertThat($.nth(list, 2), is(3));
	assertThat($.nth(list, 3), is(nullValue()));
    }
    
    @Test
    public void $nthのテスト_Array() {
	// TODO ↓ できれば$.array()の戻り値はプリミティブ型にしたい気もする・・・
	Integer[] array = $.array(1, 2, 3);
	assertThat($.nth(array, -1), is(nullValue()));
	assertThat($.nth(array, 0), is(1));
	assertThat($.nth(array, 1), is(2));
	assertThat($.nth(array, 2), is(3));
	assertThat($.nth(array, 3), is(nullValue()));
    }

    @Test
    public void $nthのテスト_String() {
	String str = "abc";
	assertThat($.nth(str, -1), is(""));
	assertThat($.nth(str, 0), is("a"));
	assertThat($.nth(str, 1), is("b"));
	assertThat($.nth(str, 2), is("c"));
	assertThat($.nth(str, 3), is(""));
    }

    @Test
    public void $sizeのテスト_List() {
	List<String> list = new ArrayList<String>();
	list.add("a");
	list.add("b");
	list.add("c");
	assertThat($.size(list), is(3));
    }

    @Test
    public void $sizeのテスト_List_Empty() {
	List<String> list = new ArrayList<String>();
	assertThat($.size(list), is(0));
    }

    @Test
    public void $sizeのテスト_List_Null() {
	List<String> list = null;
	assertThat($.size(list), is(0));
    }

    @Test
    public void $isEmptyTest() {
	List<String> list = new ArrayList<String>();
	list.add("a");
	list.add("b");
	list.add("c");
	assertThat($.isEmpty(list), is(false));
    }

    @Test
    public void $isEmptyTest_Empty() {
	List<String> list = new ArrayList<String>();
	assertThat($.isEmpty(list), is(true));
    }

    @Test
    public void $isEmptyTest_Null() {
	List<String> list = null;
	assertThat($.isEmpty(list), is(true));
    }
    
    @Test
    public void $optionのテスト_値あり_Some() {
	assertThat($.option(1).hasValue(),	is(true));
	assertThat($.option(1).get(),		is(1));
	assertThat($.option(1).getOrElse(2),	is(1));
    }

    @Test
    public void $optionのテスト_値なし_None() {
	assertThat($.option(null).hasValue(),			is(false));
	try {
	    $.option((Integer) null).get();
	    fail();
	} catch (UnsupportedOperationException e) {
	    assertThat(e.getMessage(), is("値がありません。"));
	}
	assertThat($.option((Integer) null).getOrElse(2),	is(2));
    }
    
    @Test
    public void $rangeのテスト() {
	// 0 始まり
	assertThat($.range(0, -3), is($.list(0, -1, -2, -3)));
	assertThat($.range(0, -2), is($.list(0, -1, -2)));
	assertThat($.range(0, -1), is($.list(0, -1)));
	assertThat($.range(0, 0), is($.list(0)));
	assertThat($.range(0, 1), is($.list(0, 1)));
	assertThat($.range(0, 2), is($.list(0, 1, 2)));
	assertThat($.range(0, 3), is($.list(0, 1, 2, 3)));

	// 正の数始まり
	assertThat($.range(2, -3), is($.list(2, 1, 0, -1, -2, -3)));
	assertThat($.range(2, -2), is($.list(2, 1, 0, -1, -2)));
	assertThat($.range(2, -1), is($.list(2, 1, 0, -1)));
	assertThat($.range(2, 0), is($.list(2, 1, 0)));
	assertThat($.range(2, 1), is($.list(2, 1)));
	assertThat($.range(2, 2), is($.list(2)));
	assertThat($.range(2, 3), is($.list(2, 3)));

	// 負の数始まり
	assertThat($.range(-2, -3), is($.list(-2, -3)));	
	assertThat($.range(-2, -2), is($.list(-2)));	
	assertThat($.range(-2, -1), is($.list(-2, -1)));	
	assertThat($.range(-2, 0), is($.list(-2, -1, 0)));	
	assertThat($.range(-2, 1), is($.list(-2, -1, 0, 1)));	
	assertThat($.range(-2, 2), is($.list(-2, -1, 0, 1, 2)));	
	assertThat($.range(-2, 3), is($.list(-2, -1, 0, 1, 2, 3)));
    }

    @Test
    public void $rangeBySizeのテスト() {
	List<Integer> emptyList = $.list();
	
	// 開始位置指定なし => 0はじまりとなる
	assertThat("サイズが0以下は空リスト", $.rangeBySize(-2), is(emptyList));
	assertThat("サイズが0以下は空リスト", $.rangeBySize(-1), is(emptyList));
	assertThat("サイズが0以下は空リスト", $.rangeBySize(0), is(emptyList));
	assertThat($.rangeBySize(1), is($.list(0)));
	assertThat($.rangeBySize(2), is($.list(0, 1)));
	assertThat($.rangeBySize(3), is($.list(0, 1, 2)));
	
	// 開始位置指定あり
	//   => 開始位置=0
	assertThat("サイズが0以下は空リスト", $.rangeBySize(0, -2), is(emptyList));
	assertThat("サイズが0以下は空リスト", $.rangeBySize(0, -1), is(emptyList));
	assertThat("サイズが0以下は空リスト", $.rangeBySize(0, 0), is(emptyList));
	assertThat($.rangeBySize(0, 1), is($.list(0)));
	assertThat($.rangeBySize(0, 2), is($.list(0, 1)));
	assertThat($.rangeBySize(0, 3), is($.list(0, 1, 2)));
	//   => 開始位置=正
	assertThat("サイズが0以下は空リスト", $.rangeBySize(2, -2), is(emptyList));
	assertThat("サイズが0以下は空リスト", $.rangeBySize(2, -1), is(emptyList));
	assertThat("サイズが0以下は空リスト", $.rangeBySize(2, 0), is(emptyList));
	assertThat($.rangeBySize(2, 1), is($.list(2)));
	assertThat($.rangeBySize(2, 2), is($.list(2, 3)));
	assertThat($.rangeBySize(2, 3), is($.list(2, 3, 4)));
	//   => 開始位置=負
	assertThat("サイズが0以下は空リスト", $.rangeBySize(-2, -2), is(emptyList));
	assertThat("サイズが0以下は空リスト", $.rangeBySize(-2, -1), is(emptyList));
	assertThat("サイズが0以下は空リスト", $.rangeBySize(-2, 0), is(emptyList));
	assertThat($.rangeBySize(-2, 1), is($.list(-2)));
	assertThat($.rangeBySize(-2, 2), is($.list(-2, -1)));
	assertThat($.rangeBySize(-2, 3), is($.list(-2, -1, 0)));
    }
}
