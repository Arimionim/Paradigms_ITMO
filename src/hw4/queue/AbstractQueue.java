package hw4.queue;

import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
    int size;

    public boolean isEmpty() {
        return size != 0;
    }


    public int size() {
        return size;
    }

    public Object dequeue() {
        size--;
        Object toReturn = element();
        dequeueImp();
        return toReturn;
    }

    protected abstract void dequeueImp();

    public void clear() {
        size = 0;
        clearImp();
    }

    protected abstract void clearImp();

    public Queue filter(Predicate<Object> predicate) {
        AbstractQueue result = createCopy();
        for (int i = 0; i < size; i++) {
            Object temp = result.dequeue();
            if (predicate.test(temp)) {
                result.enqueue(temp);
            }
        }
        return result;
    }

    protected abstract AbstractQueue createCopy();

    public Queue map(Function<Object, Object> func) {
        AbstractQueue result = createCopy();
        for (int i = 0; i < size; i++) {
            result.enqueue(func.apply(result.dequeue()));
        }
        return result;
    }
}
