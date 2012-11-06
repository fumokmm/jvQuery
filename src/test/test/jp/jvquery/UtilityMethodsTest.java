package test.jp.jvquery;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.*;

import static jp.jvquery.JvQuery.$;

import org.junit.*;

public class UtilityMethodsTest {

    @Test
    public void $listTest() {
	List<String> list = $.list("あ", "い", "う");
	assertThat(list, is($.list("あ", "い", "う")));
    }
    
    @Test
    public void $listTest_Empty() {
	List<String> list = $.list();
	assertThat(list.isEmpty(), is(true));
    }

    @Test
    public void $sizeTest() {
	List<String> list = new ArrayList<String>();
	list.add("a");
	list.add("b");
	list.add("c");
	assertThat($.size(list), is(3));
    }

    @Test
    public void $sizeTest_Empty() {
	List<String> list = new ArrayList<String>();
	assertThat($.size(list), is(0));
    }

    @Test
    public void $sizeTest_Null() {
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
}
