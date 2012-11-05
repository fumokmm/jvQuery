package jp.jvquery;

public final class Functions {
  public static interface Act<A> extends Act1<A>{} 
  public static interface Act1<A1> {
    public void run(A1 a1);
  }
  public static interface Act2<A1, A2> {
    public void run(A1 a1, A2 a2);
  }
  public static interface Act3<A1, A2, A3> {
    public void run(A1 a1, A2 a2, A3 a3);
  }

  public static interface Func<A, R> extends Func1<A, R>{}
  public static interface Func1<A1, R> {
    public R call(A1 a1);
  }
  public static interface Func2<A1, A2, R> {
    public R call(A1 a1, A2 a2);
  }
  public static interface Func3<A1, A2, A3, R> {
    public R call(A1 a1, A2 a2, A3 a3);
  }

  public static interface Pred<A> extends Pred1<A>{}
  public static interface Pred1<A1> {
    public boolean is(A1 a1);
  }
  public static interface Pred2<A1, A2> {
    public boolean is(A1 a1, A2 a2);
  }
  public static interface Pred3<A1, A2, A3> {
    public boolean is(A1 a1, A2 a2, A3 a3);
  }

  public static interface Conv<A, R> extends Conv1<A, R>{}
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
