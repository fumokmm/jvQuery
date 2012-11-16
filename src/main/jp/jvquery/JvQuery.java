package jp.jvquery;

import java.util.*;

/**
 * jvQuery
 * @author fumokmm
 */
public final class JvQuery {
    /** jvQueryインスタンス */
    public static final JvQuery jvQuery = Holder.instance;
    /** jvQueryの別名「$」 */
    public static final JvQuery $ = Holder.instance;

    private static final class Holder {
	public static final JvQuery instance = new JvQuery();
    }
    private JvQuery() {}

    // ---------------------------------------------------

    /**
     * リストを引数としてクエリを生成します。
     * @param list リスト
     * @return クエリ
     */
    public static final <T> ListQuery<T> jvQuery(List<T> list) {
	return new ListQuery<T>(list);
    }

    /**
     * リストを引数としてクエリを生成します。
     * @param list リスト
     * @return クエリ
     */
    public static final <T> ListQuery<T> $(List<T> list) {
	return JvQuery.jvQuery(list);
    }

    // ---------------------------------------------------

    /**
     * 空リストを生成します。(ArrayList)
     * @return 空リスト
     */
    public final <T> List<T> list() {
	return arrayList();
    }
    
    /**
     * 指定された内容でリストを生成します。
     * @param ts リストの内容
     * @return リスト
     */
    @SafeVarargs
    public final <T> List<T> list(T t, T... ts) {
        List<T> result = list();
        if (t == null && ts == null) {
            return result;
        }
        result.add(t);
        for (T t1 : ts) {
            result.add(t1);
        }
        return result;
    }

    /**
     * ArrayListの空リストを生成します。
     * @return 空リスト
     */
    public final <T> List<T> arrayList() {
	return new ArrayList<T>();
    }
    
    /**
     * LinkedListの空リストを生成します。
     * @return 空リスト
     */
    public final <T> List<T> linkedList() {
	return new LinkedList<T>();
    }
    
    public final <T> T[] array(@SuppressWarnings("unchecked") T... ts) {
        return ts;
    }

    public final <T> T nth(List<T> list, int index) {
	return list.get(index);
    }

    public final <T> T nth(T[] array, int index) {
	return array[index];
    }
    
    /**
     * リストのサイズを取得します。
     * @param list リスト
     * @return リストのサイズ
     */
    public final int size(List<?> list) {
	return list == null ? 0 : list.size();
    }
    
    /**
     * リストが空かどうか判定します。
     * @param list リスト
     * @return 空の場合true, でなければfalse
     */
    public final boolean isEmpty(List<?> list) {
	return size(list) < 1;
    }

    /**
     * 指定した値のラッピングし、オプション型を返却します。
     * @param t 値
     * @return オプション型
     */
    public final <T> Option<T> option(T t) {
	return t == null ? new None<T>() : new Some<T>(t);
    }

    /**
     * 範囲の整数リスト(start 〜 end)を生成して返却します。
     * <ul>
     * <li>終了値が開始値より小さい場合、逆順のリストを返却します。
     * </ul>
     * @param start 開始値
     * @param end 終了値
     * @return 整数リスト(start 〜 end)
     */
    public final List<Integer> range(int start, int end) {
	List<Integer> result = list();
	if (start <= end) {
	    // 正順
	    for (int i = start; i <= end; i++) {
		result.add(i);
	    }
	} else {
	    // 逆順
	    for (int i = start; i >= end; i--) {
		result.add(i);
	    }
	}
	return result;
    }

    /**
     * 範囲の整数リスト(0から始まる指定の要素サイズ分)を生成して返却します。
     * <ul>
     * <li>開始値は0からとなります。
     * <li>要素サイズが0以下の場合、空リストを返却します。
     * </ul>
     * @param size 要素サイズ
     * @return 整数リスト(0 〜 size - 1)
     */
    public final List<Integer> rangeBySize(int size) {
	return rangeBySize(0, size);
    }

    /**
     * 範囲の整数リスト(startから始まる指定の要素サイズ分)を生成して返却します。
     * <ul>
     * <li>指定の要素サイズが0以下の場合、空リストを返却します。
     * </ul>
     * @param start 開始値
     * @param size 要素サイズ
     * @return 整数リスト(start 〜 start + size - 1)
     */
    public final List<Integer> rangeBySize(int start, int size) {
	if (size <= 0) {
	    return list();
	}
	return range(start, start + size - 1);
    }

    // ---------------------------------------------------
    
    /**
     * Queryインタフェース
     * @author fumokmm
     */
    public static interface Query {
	/**
	 * 長さを取得します
	 * @return 長さ
	 */
	int size();

	/**
	 * 長さを取得します (#sizeの別名)
	 * @return 長さ
	 */
	int length();

	/**
	 * 空かどうかチェックします
	 * @return 空かどうか
	 */
	boolean isEmpty();
    }
    
    public static abstract class AbstractQuery<COLL, ITEM> implements Query {
	/**
	 * 長さを取得します
	 * @return 長さ
	 */
	public abstract int size();

	/**
	 * 長さを取得します (#sizeの別名)
	 * @return 長さ
	 */
	@Override
	public final int length() {
	    return size();
	}

	/**
	 * 空かどうかチェックします
	 * @return 空かどうか
	 */
	@Override
	public final boolean isEmpty() {
	    return size() < 1;
	}
	
	/**
	 * 指定したインデックスの要素のみを抽出します。
	 * インデックスが範囲外の場合、空となります。
	 * @param index インデックス
	 * @return クエリオブジェクト
	 */
	public abstract ListQuery<ITEM> eq(int index);

	/**
	 * 指定したインデックスよりも大きな要素を抽出します。
	 * @param index インデックス
	 * @return クエリオブジェクト
	 */
	public abstract ListQuery<ITEM> lt(int index);

	/**
	 * 指定したインデックスよりも小さな要素を抽出します。
	 * @param index インデックス
	 * @return クエリオブジェクト
	 */
	public abstract ListQuery<ITEM> gt(int index);
	
	/**
	 * 最初の要素を抽出します。
	 * @return クエリオブジェクト
	 */
	public final ListQuery<ITEM> first() {
	    return eq(0);
	}

	/**
	 * 最後の要素を抽出します。
	 * @return クエリオブジェクト
	 */
	public final ListQuery<ITEM> last() {
	    return eq(size() - 1);
	}
    }

    /**
     * リストのQuery
     * @param <T> リストの内容の型
     * @author fumokmm
     */
    public static final class ListQuery<T> extends AbstractQuery<List<T>, T> {
	private List<T> list;

	private ListQuery(List<T> list) {
	    this.list = list;
	}

	public final ListQuery<T> each(EachBlock<T> block) {
	    if (jvQuery.isEmpty(list) || block == null) {
		return this;
	    }
	    for (T t : list) {
		block.call(t);
	    }
	    return this;
	}

	public final ListQuery<T> each(EachWithIndexBlock<T> block) {
	    if (jvQuery.isEmpty(list) || block == null) {
		return this;
	    }
	    for (int index = 0; index < list.size(); index++) {
		T t = jvQuery.nth(list, index);
		block.call(t, index);
	    }
	    return this;
	}

	public final <R> ListQuery<R> map(Conv<T, R> conv) {
	    List<R> result = jvQuery.list();
	    if (jvQuery.isEmpty(list) || conv == null) {
		return jvQuery(result);
	    }
	    for (T t : list) {
		result.add(conv.convert(t));
	    }
	    return jvQuery(result);
	}

	public final <R> ListQuery<R> map(Conv2<T, Integer, R> conv) {
	    List<R> result = jvQuery.list();
	    if (jvQuery.isEmpty(list) || conv == null) {
		return jvQuery(result);
	    }
	    for (int index = 0; index < list.size(); index++) {
		T t = jvQuery.nth(list, index);
		result.add(conv.convert(t, index));
	    }
	    return jvQuery(result);
	}

	public final ListQuery<T> filter(Pred<T> pred) {
	    if (jvQuery.isEmpty(list) || pred == null) {
		return this;
	    }
	    List<T> result = jvQuery.list();
	    for (T t : list) {
		if (pred.is(t)) {
		    result.add(t);
		}
	    }
	    return jvQuery(result);
	}

	public final ListQuery<T> filter(Pred2<T, Integer> pred) {
	    if (jvQuery.isEmpty(list) || pred == null) {
		return this;
	    }
	    List<T> result = jvQuery.list();
	    for (int index = 0; index < list.size(); index++) {
		T t = jvQuery.nth(list, index);
		if (pred.is(t, index)) {
		    result.add(t);
		}
	    }
	    return jvQuery(result);
	}

	public final ListQuery<T> filter(Filter filter) {
	    if (jvQuery.isEmpty(list) || filter == null || filter.getFilter() == null) {
		return this;
	    }
	    Pred2<T, Integer> fil = filter.getFilter();
	    return filter(fil);
	}
	
	public final <R> ListQuery<R> foldLeft(R init, Func2<R, T, R> fold) {
	    R result = init;
	    for (T t : list) {
		result = fold.call(result, t);
	    }
	    return jvQuery(jvQuery.list(result));
	}

	public final List<T> get() {
	    if (jvQuery.isEmpty(list)) {
		return jvQuery.list();
	    }
	    return list;
	}

	public final T get(int index) {
	    return 0 <= index && index < jvQuery.size(list) ? list.get(index) : null;
	}

	public final Option<T> getOption(int index) {
	    return jvQuery.option(get(index));
	}

	@Override
	public final int size() {
	    return jvQuery.size(list);
	}

	@Override
	public final ListQuery<T> eq(int index) {
	    Option<T> resultItem = getOption(index);
	    if (resultItem.hasValue()) {
		T t = resultItem.get();
		List<T> result = jvQuery.list(t);
		return jvQuery(result);
	    } else {
		List<T> result = jvQuery.list();
		return jvQuery(result);
	    }
	}

	@Override
	public final ListQuery<T> lt(int index) {
	    final int idx = index;
	    return filter(new Pred2<T, Integer>(){
		@Override
		public boolean is(T t, Integer tIdx) {
		    return tIdx < idx;
		}
	    });
	}

	@Override
	public final ListQuery<T> gt(int index) {
	    final int idx = index;
	    return filter(new Pred2<T, Integer>(){
		@Override
		public boolean is(T t, Integer tIdx) {
		    return tIdx > idx;
		}
	    });
	}
    }
    
    // ---------------------------------------------------

    public static interface FunctionVoid {
	void call();
    }
    public static interface FunctionVoid1<V1> {
	void call(V1 v1);
    }
    public static interface FunctionVoid2<V1, V2> {
	void call(V1 v1, V2 v2);
    }
    public static interface FunctionVoid3<V1, V2, V3> {
	void call(V1 v1, V2 v2, V3 v3);
    }

    public static interface Function<R> {
	R call();
    }
    public static interface Function1<V1, R> {
	R call(V1 v1);
    }
    public static interface Function2<V1, V2, R> {
	R call(V1 v1, V2 v2);
    }
    public static interface Function3<V1, V2, V3, R> {
	R call(V1 v1, V2 v2, V3 v3);
    }

    public static interface EachBlock<T> extends FunctionVoid1<T> {}
    public static interface EachWithIndexBlock<T> extends FunctionVoid2<T, Integer> {}
    
    public static interface Act<A> extends Act1<A> {
    }

    public static interface Act1<A1> {
	public void run(A1 a1);
    }

    public static interface Act2<A1, A2> {
	public void run(A1 a1, A2 a2);
    }

    public static interface Act3<A1, A2, A3> {
	public void run(A1 a1, A2 a2, A3 a3);
    }

    public static interface Func<A, R> extends Func1<A, R> {
    }

    public static interface Func1<A1, R> {
	public R call(A1 a1);
    }

    public static interface Func2<A1, A2, R> {
	public R call(A1 a1, A2 a2);
    }

    public static interface Func3<A1, A2, A3, R> {
	public R call(A1 a1, A2 a2, A3 a3);
    }

    public static interface Pred<A> extends Pred1<A> {
    }

    public static interface Pred1<A1> {
	public boolean is(A1 a1);
    }

    public static interface Pred2<A1, A2> {
	public boolean is(A1 a1, A2 a2);
    }

    public static interface Pred3<A1, A2, A3> {
	public boolean is(A1 a1, A2 a2, A3 a3);
    }

    public static interface Conv<A, R> extends Conv1<A, R> {
    }

    public static interface Conv1<A1, R> {
	public R convert(A1 a1);
    }

    public static interface Conv2<A1, A2, R> {
	public R convert(A1 a1, A2 a2);
    }

    public static interface Conv3<A1, A2, A3, R> {
	public R convert(A1 a1, A2 a2, A3 a3);
    }
    
    public static interface Filter {
	public <T> Pred2<T, Integer> getFilter();
    }
    
    public static interface Fold {
	public <T, R> Func2<R, T, R> getFunc();
    }

    public static enum IndexIs implements Filter {
	/** 偶数 */
	even {
	    @Override
	    public <T> Pred2<T, Integer> getFilter() {
		return new Pred2<T, Integer>() {
		    @Override
		    public boolean is(T t, Integer idx) {
			return idx % 2 == 0;
		    }
		};
	    }
	},

	/** 奇数 */
	odd {
	    @Override
	    public <T> Pred2<T, Integer> getFilter() {
		return new Pred2<T, Integer>() {
		    @Override
		    public boolean is(T t, Integer idx) {
			return idx % 2 == 1;
		    }
		};
	    }
	},
	;
    }
    
    // ---------------------------------------------------

    /**
     * 選択的な値を表すクラス。
     * インスタンスは以下の二種類のみ。
     *  - Some<T> 値が存在する
     *  - None<T> 値が存在しない
     * @param <T> 値の型
     * @author fumokmm
     */
    public static abstract class Option<T> {
	/** パッケージプライベートコンストラクタ */
	private Option(){}

	/** @return 値があるかどうか */
	public abstract boolean hasValue();
	
	/**
	 * 値を取得します。
	 * もし、値がない場合(#hasValueの値がfalseの場合)は、IllegalStateExceptionが発生します。
	 * @return 値
	 * @throws IllegalStateException
	 */
	public abstract T get();
	
	/**
	 * 値を取得します。
	 * もし、値がない場合(#hasValueの値がfalseの場合)は、代替の値が返却されます。
	 * @param alt 代替の値
	 * @return 値
	 */
	public T getOrElse(T alt) {
	    return hasValue() ? get() : alt;
	}
    }
    
    private static final class Some<T> extends Option<T> {
	private T t;
	private Some(T t) {
	    this.t = t;
	}
	@Override
	public boolean hasValue() {
	    return true;
	}
	@Override
	public T get() {
	    return t;
	}
    }
    
    private static final class None<T> extends Option<T> {
	private None() {}
	@Override
	public boolean hasValue() {
	    return false;
	}
	@Override
	public T get() {
	    throw new UnsupportedOperationException("値がありません。");
	}
    }

    // ---------------------------------------------------
    
}
