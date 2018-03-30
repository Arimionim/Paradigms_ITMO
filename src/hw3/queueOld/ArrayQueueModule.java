package hw3.queueOld;

public class ArrayQueueModule {

    // Inv: (n >= 0) && (a[i] != null for i = 1..n - 1)

    private static int head = 0;
    private static int size = 0;
    private static int length = 4;
    private static Object[] body = new Object[4];

    public static boolean isEmpty() {
        return size != 0;
    }

    // Pre: true
    public static int size() {
        return size;
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == n)

    // Pre: true
    public static void clear() {
        size = 0;
        head = 0;
    }
    // Post: n == 0

    // Pre: (x != null)
    public static void enqueue(Object x) {
        resize(size + 1);
        body[(head + size - 1) % length] = x;
    }
    // (n' == n + 1) && (a'[i] == a[i] for i = 0..n - 1) && (a'[n] == x)

    // Pre: (x != null)
    public static void push(Object x) {
        resize(size + 1);
        head = prev(head);
        body[head] = x;
    }
    // (n' == n + 1) && (a'[i + 1] == a[i] for i = 0..n - 1) && (a'[0] == x)

    //Pre: size >= 0
    private static void resize(int newSize) {
        if (newSize <= length) {
            size = newSize;
        } else {
            int newLength = length;
            while (newLength < newSize * 2) {
                newLength *= 2;
            }
            Object[] newBody = new Object[newLength];
            size = newSize;

            System.arraycopy(body, head, newBody, 0, length - head);
            System.arraycopy(body, 0, newBody, length - head, (head + size - 1) % length);


            body = newBody;
            length = newLength;
            head = 0;
        }
    }
    // Post: (size < body'.length <= sz * 4) && (n' == n) && (a'[i] == a[i] for i = 0...n - 1)

    // Pre: n > 0
    public static Object element() {
        return body[head];
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == a[0])

    // Pre: n > 0
    public static Object peek() {
        return body[(head + size - 1) % length];
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == a[n - 1])

    // Pre: n > 0
    public static Object dequeue() {
        size--;
        Object toReturn = body[head];
        head = next(head);
        return toReturn;
    }
    // Post: (n' == n - 1) && (a'[i - 1] == a[i] for i = 1...n - 1) && (Result == a[0])

    // Pre: n > 0
    public static Object remove() {
        Object toReturn = body[(head + size - 1) % length];
        size--;
        return toReturn;
    }
    // Post: (n' == n - 1) && (a'[i] == a[i] for i = 0...n - 2) && (Result == a[n - 1])

    // Pre: (body.length != 0) && (0 <= x < body.length)
    private static int next(int index) {
        return (index + 1) % length;
    }
    // Result == (x + 1) % body.length

    // Pre: (body.length != 0) && (0 <= x < body.length)
    private static int prev(int index) {
        return ((index - 1) % length + length) % length;
    }
    // Post: (Result == x - 1 && x > 0) || (Result == body.length && x == 0)
}
