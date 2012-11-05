package jp.jvquery;

import java.util.*;

import jp.jvquery.Functions.Act;
import jp.jvquery.JvQuery.*;

public class ListQuery<T> implements Query {

    private List<T> list;
    
    ListQuery(List<T> list) {
	this.list = list;
    }
    
    public ListQuery<T> each(Act<T> act) {
	if ($.isEmpty(list)) {
	    return this;
	}
	for (T t : list) {
	    act.run(t);
	}
	return this;
    }

    @Override
    public int length() {
	return $.length(list);
    }

    @Override
    public int size() {
	return $.size(list);
    }
}
