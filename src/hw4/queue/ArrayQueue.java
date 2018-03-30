package hw4.queue;

public class ArrayQueue extends AbstractQueue {

    private int head = 0;
    private int tail = -1;
    private int length = 4;
    private Object[] body = new Object[4];

    public ArrayQueue(int size) {
        head = 0;
        tail = -1;
        length = size;
        body = new Object[length];
    }

    public ArrayQueue() {
        this(4);
    }

    public void clearImp() {
        head = 0;
        tail = -1;
    }

    public void enqueue(Object x) {
        resize(size + 1);
        tail = next(tail);
        if (size == 1) {
            head = tail;
        }
        body[tail] = x;
    }

    public void push(Object x) {
        resize(size + 1);
        head = prev(head);
        if (size == 1) {
            tail = head;
        }
        body[head] = x;
    }

    private void resize(int newSize) {
        if (newSize <= length) {
            size = newSize;
        } else {
            int newLength = length;
            while (newLength < newSize * 2) {
                newLength *= 2;
            }
            Object[] newBody = new Object[newLength];
            tail = size - 1;
            size = newSize;

            System.arraycopy(body, head, newBody, 0, length - head);
            System.arraycopy(body, 0, newBody, length - head, (head + size - 1) % length);

            body = newBody;
            length = newLength;
            head = 0;
        }
    }

    public Object element() {
        return body[head];
    }

    public Object peek() {
        return body[tail];
    }


    protected void dequeueImp() {
        Object toReturn = body[head];
        head = next(head);
    }


    public void removeImp() {
        tail = prev(tail);
    }

    private int next(int index) {
        return (index + 1) % length;
    }

    private int prev(int index) {
        return (((index - 1) % length + length) % length);
    }

    protected ArrayQueue createCopy() {
        ArrayQueue result = new ArrayQueue(body.length);
        result.head = head;
        result.tail = tail;
        result.size = size;
        System.arraycopy(body, 0, result.body, 0, body.length);
        return result;
    }
}
