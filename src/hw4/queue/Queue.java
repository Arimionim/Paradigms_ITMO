package hw4.queue;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Queue {
    // Inv: (n >= 0) && (a[i] != null for i = 0...n - 1)

    //Pre: size > 0
    Object element();
    /* Post size` == size &&
    * for hw4.queue` == hw4.queue &&
        R == a[0]
     */

    /*Pre: object != null*/
    void enqueue(Object object);
    /*Pre: size` = size + 1*/

    // Pre: n > 0
    Object dequeue();
    // Post: (n' == n - 1) && (a'[i - 1] == a[i] for i = 1...n - 1) && (R == a[0])

    //Pre: true
    int size();
    //Post: R == n && a[i]' == a[i] for i = 0...n - 1

    //Pre: true
    boolean isEmpty();
    //Post: R == (n == 0) &&
    // a[i]' == a[i] for i = 0...n - 1

    //Pre: true
    void clear();
    //Post: n' == 0

    //Pre: predicate != null
    Queue filter(Predicate<Object> predicate);
    //Post: i[j] - sequence of indexes a[i] : predicate(a[i]) for i = 0...n - 1 of size k,
    //      for j = 0...k - 1 b[j] = a[i[j]] && R.seq == b && R.size == k &&
    //      n' == n && a[i]' == a[i] for i == 0...n - 1

    //Pre: function != null
    Queue map(Function<Object, Object> func);
    // for i = 0...n - 1 b[i] = f(a[i]) &&
    // && R.seq == b && R.size == n && n' == n &&
    // && for i = 0... n - 1 a[i]' == a[i]
}
