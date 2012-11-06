package jp.jvquery;

import java.util.*;

/**
 * jvQuery
 * @author fumokmm
 */
public class JvQuery {
    /** jvQueryインスタンス */
    public static final JvQuery jvQuery = Holder.instance;
    /** jvQueryの別名「$」 */
    public static final JvQuery $ = Holder.instance;

    private static class Holder {
	public static final JvQuery instance = new JvQuery();
    }
    private JvQuery() {}

    /**
     * リストを引数としてクエリを生成します。
     * @param list リスト
     * @return クエリ
     */
    public static <T> ListQuery<T> jvQuery(List<T> list) {
	return new ListQuery<T>(list);
    }

    /**
     * リストを引数としてクエリを生成します。
     * @param list リスト
     * @return クエリ
     */
    public static <T> ListQuery<T> $(List<T> list) {
	return JvQuery.jvQuery(list);
    }

    /**
     * 空リストを生成します。
     * @return 空リスト
     */
    public <T> List<T> list() {
	return new ArrayList<T>();
    }
    
    /**
     * 指定された内容でリストを生成します。
     * @param ts リストの内容
     * @return リスト
     */
    public <T> List<T> list(T t, @SuppressWarnings("unchecked") T... ts) {
	List<T> result = new ArrayList<T>();
	if (t == null && ts == null) {
	    return result;
	}
	for (T t1 : ts) {
	    result.add(t1);
	}
	return result;
    }

    /**
     * リストのサイズを取得します。
     * @param list リスト
     * @return リストのサイズ
     */
    public int size(List<?> list) {
	return list == null ? 0 : list.size();
    }
    
    /**
     * リストが空かどうか判定します。
     * @param list リスト
     * @return 空の場合true, でなければfalse
     */
    public boolean isEmpty(List<?> list) {
	return size(list) < 1;
    }

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
	public int length() {
	    return size();
	}

	/**
	 * 空かどうかチェックします
	 * @return 空かどうか
	 */
	@Override
	public boolean isEmpty() {
	    return size() < 1;
	}
    }

    /**
     * リストのQuery
     * @param <T> リストの内容の型
     * @author fumokmm
     */
    public static class ListQuery<T> extends AbstractQuery<List<T>, T> {
	private List<T> list;

	ListQuery(List<T> list) {
	    this.list = list;
	}

	public ListQuery<T> each(Act<T> act) {
	    if (jvQuery.isEmpty(list) || act == null) {
		return this;
	    }
	    for (T t : list) {
		act.run(t);
	    }
	    return this;
	}

	public <R> ListQuery<R> map(Conv<T, R> conv) {
	    List<R> result = jvQuery.list();
	    if (jvQuery.isEmpty(list) || conv == null) {
		return jvQuery(result);
	    }
	    for (T t : list) {
		result.add(conv.convert(t));
	    }
	    return jvQuery(result);
	}

	public ListQuery<T> filter(Pred<T> pred) {
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

	public List<T> get() {
	    return list;
	}

	public T get(int index) {
	    if (0 <= index && index < jvQuery.size(list)) {
		return list.get(index);
	    } else {
		// TODO あとでOption<T>型にする
		return null;
	    }
	}

	@Override
	public int size() {
	    return jvQuery.size(list);
	}
    }
    
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
}
