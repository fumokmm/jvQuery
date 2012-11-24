package test.jp.jvquery;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.*;

import jp.jvquery.JvQuery.EachBlock;
import jp.jvquery.JvQuery.FilterBlock;
import jp.jvquery.JvQuery.FoldBlock;
import jp.jvquery.JvQuery.MapBlock;
import jp.jvquery.JvQuery.*;

import static jp.jvquery.JvQuery.*;
import org.junit.*;

public class ListQueryTest {

    @Test
    public void eachのテスト() {
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
    public void eachのテスト_Empty() {
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
    public void eachのテスト_Null() {
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
    public void eachのテスト_NullBlock() {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        final int[] num = new int[]{ 0 };
        EachBlock<Integer> block = null;
        $(nums).each(block);
        assertThat(num[0], is(0));
    }

    @Test
    public void mapのテスト() {
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
        }).getAll(), is($.list("1", "22", "333")));
    }

    @Test
    public void mapのテスト_Empty() {
        List<Integer> nums = $.list();
        assertThat($(nums).map(new MapBlock<Integer, String>(){
            @Override
            public String call(Integer a1) {
        	StringBuilder sb = new StringBuilder();
        	for (int i = 0; i < a1; i++) {
        	    sb.append(String.valueOf(a1));
        	}
        	return sb.toString();
            }
        }).isEmpty(), is(true));
    }

    @Test
    public void mapのテスト_Null() {
        List<Integer> nums = null;
        assertThat($(nums).map(new MapBlock<Integer, String>(){
            @Override
            public String call(Integer a1) {
        	StringBuilder sb = new StringBuilder();
        	for (int i = 0; i < a1; i++) {
        	    sb.append(String.valueOf(a1));
        	}
        	return sb.toString();
            }
        }).isEmpty(), is(true));
    }

    @Test
    public void mapのテスト_NullBlock() {
        List<Integer> nums = $.list(1, 2, 3);
        MapBlock<Integer, String> block = null;
        assertThat($(nums).map(block).isEmpty(), is(true));
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
	}).getAll(), is(expected));
    }
    
    @Test
    public void filterのテスト_Empty() {
	List<String> strList = $.list();
	assertThat($(strList).filter(new FilterBlock<String>(){
	    @Override
	    public Boolean call(String a1) {
		return ! a1.equals("b");
	    }
	}).isEmpty(), is(true));
    }

    @Test
    public void filterのテスト_Null() {
	List<String> strList = null;
	assertThat($(strList).filter(new FilterBlock<String>(){
	    @Override
	    public Boolean call(String a1) {
		return ! a1.equals("b");
	    }
	}).isEmpty(), is(true));
    }

    @Test
    public void filterのテスト_NullBlock() {
        List<String> strList = $.list("a", "b", "c");
        FilterBlock<String> block = null;
        assertThat("フィルタされない", $(strList).filter(block).getAll(), is(strList));
    }

    @Test
    public void filterのテスト_IndexIs() {
	List<String> strList = $.list("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
	assertThat("偶数(even)",
	    $(strList).filter(IndexIs.even).getAll(), is(
	        $.list("a", "c", "e", "g", "i")
	    )
	);
	assertThat("奇数(odd)",
	    $(strList).filter(IndexIs.odd).getAll(), is(
		$.list("b", "d", "f", "h", "j")
	    )
	);
    }

    @Test
    public void filterのテスト_FilterNull() {
	List<String> strList = $.list("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
	Filter fil = null;
	assertThat("nullはフィルタされず最初と同じ", $(strList).filter(fil).getAll(), is(strList));
    }

    @Test
    public void filterのテスト_FilterGetNull() {
	List<String> strList = $.list("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
	Filter fil = new Filter() {
	    @SuppressWarnings("unchecked")
	    @Override
	    public FilterWithIndexBlock<String> getFilter() {
		FilterWithIndexBlock<String> fil = null;
		return fil;
	    }
	};
	assertThat("#getFilter結果がnullはフィルタされず最初と同じ", $(strList).filter(fil).getAll(), is(strList));
    }

    @Test
    public void foldLeftのテスト_初期値なし_Empty() {
	List<Integer> nums = $.list();
	assertThat($(nums).foldLeft(new FoldBlock<Integer, Integer>() {
	    @Override
	    public Integer call(Integer v1, Integer v2) {
		return null;
	    }
	}).isEmpty(), is(true));
    }
    
    @Test
    public void foldLeftのテスト_初期値なし_Null() {
	List<Integer> nums = null;
	assertThat($(nums).foldLeft(new FoldBlock<Integer, Integer>() {
	    @Override
	    public Integer call(Integer v1, Integer v2) {
		return null;
	    }
	}).isEmpty(), is(true));
    }

    @Test
    public void foldLeftのテスト_初期値なし_NullBlock() {
	List<Integer> nums = $.list(1, 2, 3);
	assertThat($(nums).foldLeft(null).isEmpty(), is(true));
    }

    @Test
    public void foldLeftのテスト_初期値なし_要素数1() {
	List<Integer> nums = $.list(1);
	assertThat($(nums).foldLeft(new FoldBlock<Integer, Integer>() {
	    @Override
	    public Integer call(Integer v1, Integer v2) {
		return v1 + v2;
	    }
	}).get(0), is(1));
    }

    @Test
    public void foldLeftのテスト_初期値なし_要素数2() {
	List<Integer> nums = $.list(1, 2);
	assertThat($(nums).foldLeft(new FoldBlock<Integer, Integer>() {
	    @Override
	    public Integer call(Integer v1, Integer v2) {
		return v1 + v2;
	    }
	}).get(0), is(3));
    }
    
    @Test
    public void foldLeftのテスト_初期値なし_要素数3() {
	List<Integer> nums = $.list(1, 2, 3);
	assertThat($(nums).foldLeft(new FoldBlock<Integer, Integer>() {
	    @Override
	    public Integer call(Integer v1, Integer v2) {
		return v1 + v2;
	    }
	}).get(0), is(6));
    }

    @Test
    public void foldLeftのテスト_Integer() {
	List<Integer> nums = $.range(1, 10);
	assertThat($(nums).foldLeft(0, new FoldBlock<Integer, Integer>(){
	    @Override
	    public Integer call(Integer result, Integer num) {
		return result + num;
	    }
	}).get(0), is(55));
    }

    @Test
    public void foldLeftのテスト_Empty() {
	List<Integer> nums = $.list();
	assertThat($(nums).foldLeft(0, new FoldBlock<Integer, Integer>(){
	    @Override
	    public Integer call(Integer result, Integer num) {
		return result + num;
	    }
	}).isEmpty(), is(true));
    }
    
    @Test
    public void foldLeftのテスト_Null() {
	List<Integer> nums = null;
	assertThat($(nums).foldLeft(0, new FoldBlock<Integer, Integer>(){
	    @Override
	    public Integer call(Integer result, Integer num) {
		return result + num;
	    }
	}).isEmpty(), is(true));
    }

    @Test
    public void foldLeftのテスト_NullBlock() {
        List<Integer> nums = $.range(1, 10);
        FoldBlock<Integer, Integer> block = null;
        assertThat($(nums).foldLeft(0, block).isEmpty(), is(true));
    }

    @Test
    public void foldLeftのテスト_String() {
        List<Integer> nums = $.range(1, 3);
        assertThat($(nums).foldLeft("0", new FoldBlock<Integer, String>(){
            @Override
            public String call(String result, Integer num) {
        	return "(" + result + " + " + num + ")";
            }
        }).get(0), is("(((0 + 1) + 2) + 3)"));
    }


    @Test
    public void foldRightのテスト_初期値なし_Empty() {
	List<Integer> nums = $.list();
	assertThat($(nums).foldRight(new FoldBlock<Integer, Integer>() {
	    @Override
	    public Integer call(Integer v1, Integer v2) {
		return null;
	    }
	}).isEmpty(), is(true));
    }
    
    @Test
    public void foldRightのテスト_初期値なし_Null() {
	List<Integer> nums = null;
	assertThat($(nums).foldRight(new FoldBlock<Integer, Integer>() {
	    @Override
	    public Integer call(Integer v1, Integer v2) {
		return null;
	    }
	}).isEmpty(), is(true));
    }

    @Test
    public void foldRightのテスト_初期値なし_NullBlock() {
	List<Integer> nums = $.list(1, 2, 3);
	assertThat($(nums).foldRight(null).isEmpty(), is(true));
    }

    @Test
    public void foldRightのテスト_初期値なし_要素数1() {
	List<Integer> nums = $.list(1);
	assertThat($(nums).foldRight(new FoldBlock<Integer, Integer>() {
	    @Override
	    public Integer call(Integer result, Integer num) {
		return num - result;
	    }
	}).get(0), is(1));
    }

    @Test
    public void foldRightのテスト_初期値なし_要素数2() {
	List<Integer> nums = $.list(1, 2);
	// (1 - 2) = -1
	assertThat($(nums).foldRight(new FoldBlock<Integer, Integer>() {
	    @Override
	    public Integer call(Integer result, Integer num) {
		return num - result;
	    }
	}).get(0), is(-1));
    }
    
    @Test
    public void foldRightのテスト_初期値なし_要素数3() {
	List<Integer> nums = $.list(1, 2, 3);
	// (1 - (2 - 3)) = 2
	assertThat($(nums).foldRight(new FoldBlock<Integer, Integer>() {
	    @Override
	    public Integer call(Integer result, Integer num) {
		return num - result;
	    }
	}).get(0), is(2));
    }
    
    @Test
    public void foldRightのテスト_Integer() {
	List<Integer> nums = $.range(1, 10);
	assertThat($(nums).foldRight(0, new FoldBlock<Integer, Integer>(){
	    @Override
	    public Integer call(Integer result, Integer num) {
		return num + result;
	    }
	}).get(0), is(55));
    }

    @Test
    public void foldRightのテスト_Empty() {
	List<Integer> nums = $.list();
	assertThat($(nums).foldRight(0, new FoldBlock<Integer, Integer>(){
	    @Override
	    public Integer call(Integer result, Integer num) {
		return num + result;
	    }
	}).isEmpty(), is(true));
    }

    @Test
    public void foldRightのテスト_Null() {
	List<Integer> nums = null;
	assertThat($(nums).foldRight(0, new FoldBlock<Integer, Integer>(){
	    @Override
	    public Integer call(Integer result, Integer num) {
		return num + result;
	    }
	}).isEmpty(), is(true));
    }

    @Test
    public void foldRightのテスト_NullBlock() {
        List<Integer> nums = $.range(1, 10);
        FoldBlock<Integer, Integer> block = null;
        assertThat($(nums).foldRight(0, block).isEmpty(), is(true));
    }

    @Test
    public void foldRightのテスト_String() {
	List<Integer> nums = $.range(1, 3);
	assertThat($(nums).foldRight("0", new FoldBlock<Integer, String>(){
	    @Override
	    public String call(String result, Integer num) {
		return "(" + num + " + " + result + ")";
	    }
	}).get(0), is("(1 + (2 + (3 + 0)))"));
    }

    @Test
    public void injectのテスト_初期値なし() {
	List<Integer> nums = $.range(1, 10);
	assertThat($(nums).inject(new InjectBlock<Integer, Integer>() {
	    @Override
	    public Integer call(Integer v1, Integer v2) {
		return v1 + v2;
	    }
	}).get(0), is(55));
    }
    
    @Test
    public void injectのテスト_String() {
	List<Integer> nums = $.range(1, 3);
	assertThat($(nums).inject("0", new InjectBlock<Integer, String>(){
	    @Override
	    public String call(String result, Integer num) {
		return "(" + result + " + " + num + ")";
	    }
	}).get(0), is("(((0 + 1) + 2) + 3)"));
    }

    @Test
    public void reduceのテスト_初期値なし() {
	List<Integer> nums = $.range(1, 10);
	assertThat($(nums).reduce(new ReduceBlock<Integer, Integer>() {
	    @Override
	    public Integer call(Integer v1, Integer v2) {
		return v1 + v2;
	    }
	}).get(0), is(55));
    }
    
    @Test
    public void reduceのテスト_String() {
	List<Integer> nums = $.range(1, 3);
	assertThat($(nums).reduce("0", new ReduceBlock<Integer, String>(){
	    @Override
	    public String call(String result, Integer num) {
		return "(" + result + " + " + num + ")";
	    }
	}).get(0), is("(((0 + 1) + 2) + 3)"));
    }
    
    @Test
    public void reverseのテスト() {
	List<Integer> nums = $.range(1, 5);
	assertThat($(nums).reverse().getAll(), is($.list(5, 4, 3, 2, 1)));
    }
    
    @Test
    public void sortのテスト() {
	List<Integer> nums = $.list(3, 5, 4, 2, 1);
	assertThat($(nums).sort(new Comparator<Integer>(){
	    @Override
	    public int compare(Integer o1, Integer o2) {
		return o1 - o2;
	    }
	}).getAll(), is($.list(1, 2, 3, 4, 5)));
    }
    
    @Test
    public void sortのテスト_NullComparator() {
	List<Integer> nums = $.list(3, 5, 4, 2, 1);
	Comparator<Integer> cmp = null;
	assertThat("ソートされない", $(nums).sort(cmp).getAll(), is($.list(3, 5, 4, 2, 1)));
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
	assertThat($(nums).lt(1).getAll(), is($.list(100)));
	assertThat($(nums).lt(2).getAll(), is($.list(100, 200)));
	assertThat($(nums).lt(3).getAll(), is($.list(100, 200, 300)));
	assertThat($(nums).lt(4).getAll(), is($.list(100, 200, 300, 400)));
	assertThat($(nums).lt(5).getAll(), is($.list(100, 200, 300, 400, 500)));
	assertThat($(nums).lt(6).getAll(), is($.list(100, 200, 300, 400, 500)));
    }

    @Test
    public void gtのテスト() {
	List<Integer> nums = $.list(100, 200, 300, 400, 500);
	assertThat($(nums).gt(-1).getAll(), is($.list(100, 200, 300, 400, 500)));
	assertThat($(nums).gt(0).getAll(), is($.list(200, 300, 400, 500)));
	assertThat($(nums).gt(1).getAll(), is($.list(300, 400, 500)));
	assertThat($(nums).gt(2).getAll(), is($.list(400, 500)));
	assertThat($(nums).gt(3).getAll(), is($.list(500)));
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
    public void getAllのテスト() {
	List<Object> nullList = null;
	assertThat($(nullList).getAll(), is($.list()));
	assertThat($($.list()).getAll(), is($.list()));
	assertThat($($.list(1)).getAll(), is($.list(1)));
	assertThat($($.list(1, 2)).getAll(), is($.list(1, 2)));
	assertThat($($.list(1, 2, 3)).getAll(), is($.list(1, 2, 3)));
    }

    @Test
    public void getのテスト() {
	List<Object> nullList = null;
	assertThat($(nullList).get(-1), is(nullValue()));
	assertThat($(nullList).get(0), is(nullValue()));
	assertThat("#get(0)と同じ", $(nullList).get(), is(nullValue()));
	assertThat($(nullList).get(1), is(nullValue()));

	assertThat($($.list()).get(-1), is(nullValue()));
	assertThat($($.list()).get(0), is(nullValue()));
	assertThat("#get(0)と同じ", $($.list()).get(), is(nullValue()));
	assertThat($($.list()).get(1), is(nullValue()));
	
	assertThat($($.list(1)).get(-1), is(nullValue()));
	assertThat($($.list(1)).get(0), is(1));
	assertThat("#get(0)と同じ", $($.list(1)).get(), is(1));
	assertThat($($.list(1)).get(1), is(nullValue()));

	assertThat($($.list(1, 2)).get(-1), is(nullValue()));
	assertThat($($.list(1, 2)).get(0), is(1));
	assertThat("#get(0)と同じ", $($.list(1, 2)).get(), is(1));
	assertThat($($.list(1, 2)).get(1), is(2));
	assertThat($($.list(1, 2)).get(2), is(nullValue()));

	assertThat($($.list(1, 2, 3)).get(-1), is(nullValue()));
	assertThat($($.list(1, 2, 3)).get(0), is(1));
	assertThat("#get(0)と同じ", $($.list(1, 2, 3)).get(), is(1));
	assertThat($($.list(1, 2, 3)).get(1), is(2));
	assertThat($($.list(1, 2, 3)).get(2), is(3));
	assertThat($($.list(1, 2, 3)).get(3), is(nullValue()));
    }

    @Test
    public void getOptionのテスト() {
	List<Object> nullList = null;
	assertThat($(nullList).getOption(-1).hasValue(), is(false));
	assertThat($(nullList).getOption(0).hasValue(), is(false));
	assertThat("#getOption(0)と同じ", $(nullList).getOption().hasValue(), is(false));
	assertThat($(nullList).getOption(1).hasValue(), is(false));

	assertThat($($.list()).getOption(-1).hasValue(), is(false));
	assertThat($($.list()).getOption(0).hasValue(), is(false));
	assertThat("#getOption(0)と同じ", $($.list()).getOption().hasValue(), is(false));
	assertThat($($.list()).getOption(1).hasValue(), is(false));
	
	assertThat($($.list(1)).getOption(-1).hasValue(), is(false));
	assertThat($($.list(1)).getOption(0).hasValue(), is(true));
	assertThat("#getOption(0)と同じ", $($.list(1)).getOption().hasValue(), is(true));
	assertThat($($.list(1)).getOption(0).get(), is(1));
	assertThat("#getOption(0)と同じ", $($.list(1)).getOption().get(), is(1));
	assertThat($($.list(1)).getOption(1).hasValue(), is(false));

	assertThat($($.list(1, 2)).getOption(-1).hasValue(), is(false));
	assertThat($($.list(1, 2)).getOption(0).hasValue(), is(true));
	assertThat("#getOption(0)と同じ", $($.list(1, 2)).getOption().hasValue(), is(true));
	assertThat($($.list(1, 2)).getOption(0).get(), is(1));
	assertThat("#getOption(0)と同じ", $($.list(1, 2)).getOption().get(), is(1));
	assertThat($($.list(1, 2)).getOption(1).hasValue(), is(true));
	assertThat($($.list(1, 2)).getOption(1).get(), is(2));
	assertThat($($.list(1, 2)).getOption(2).hasValue(), is(false));

	assertThat($($.list(1, 2, 3)).getOption(-1).hasValue(), is(false));
	assertThat($($.list(1, 2, 3)).getOption(0).hasValue(), is(true));
	assertThat("#getOption(0)と同じ", $($.list(1, 2, 3)).getOption().hasValue(), is(true));
	assertThat($($.list(1, 2, 3)).getOption(0).get(), is(1));
	assertThat("#getOption(0)と同じ", $($.list(1, 2, 3)).getOption().get(), is(1));
	assertThat($($.list(1, 2, 3)).getOption(1).hasValue(), is(true));
	assertThat($($.list(1, 2, 3)).getOption(1).get(), is(2));
	assertThat($($.list(1, 2, 3)).getOption(2).hasValue(), is(true));
	assertThat($($.list(1, 2, 3)).getOption(2).get(), is(3));
	assertThat($($.list(1, 2, 3)).getOption(3).hasValue(), is(false));
    }
}
