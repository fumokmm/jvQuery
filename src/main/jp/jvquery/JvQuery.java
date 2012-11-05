package jp.jvquery;

import java.util.*;

public class JvQuery {

    public static <T> ListQuery<T> $(List<T> list) {
	return new ListQuery<T>(list);
    }
    
    public static class $ {
	@SafeVarargs
	public static <T> List<T> list(T... ts) {
	    return Arrays.asList(ts);
	}
	public static int size(List<?> list) {
	    return list == null ? 0 : list.size();
	}
	public static int length(List<?> list) {
	    return size(list);
	}
	public static boolean isEmpty(List<?> list) {
	    return size(list) == 0;
	}
    }
}
