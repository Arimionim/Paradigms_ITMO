package hw3.queueOld;

public class ArrayQueueADT {
    // Inv: (n >= 0) && (a[i] != null for i = 1..n - 1)

    private int head = 0;
    private int size = 0;
    private int length = 4;
    private Object[] body = new Object[4];

    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size != 0;
    }

    // Pre: true
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == n)

    // Pre: true
    public static void clear(ArrayQueueADT queue) {
        queue.size = 0;
        queue.head = 0;
    }
    // Post: n == 0

    // Pre: (x != null)
    public static void enqueue(ArrayQueueADT queue, Object x) {
        resize(queue, queue.size + 1);
        queue.body[(queue.head + queue.size - 1) % queue.length] = x;
    }
    // (n' == n + 1) && (a'[i] == a[i] for i = 0..n - 1) && (a'[n] == x)

    // Pre: (x != null)
    public static void push(ArrayQueueADT queue, Object x) {
        resize(queue, queue.size + 1);
        queue.head = prev(queue, queue.head);
        queue.body[queue.head] = x;
    }
    // (n' == n + 1) && (a'[i + 1] == a[i] for i = 0..n - 1) && (a'[0] == x)

    //Pre: size >= 0
    private static void resize(ArrayQueueADT queue, int newSize) {
        if (newSize <= queue.length) {
            queue.size = newSize;
        } else {
            int newLength = queue.length;
            while (newLength < newSize * 2) {
                newLength *= 2;
            }
            Object[] newBody = new Object[newLength];
            queue.size = newSize;

            System.arraycopy(queue.body, queue.head, newBody, 0, queue.length - queue.head);
            System.arraycopy(queue.body, 0, newBody, queue.length - queue.head, (queue.head + queue.size - 1) % queue.length);

            queue.body = newBody;
            queue.length = newLength;
            queue.head = 0;
        }
    }
    // Post: (size < body'.length <= size * 4) && (n' == n) && (a'[i] == a[i] for i = 0...n - 1)

    // Pre: n > 0
    public static Object element(ArrayQueueADT queue) {
        return queue.body[queue.head];
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == a[0])

    // Pre: n > 0
    public static Object peek(ArrayQueueADT queue) {
        return queue.body[(queue.head + queue.size - 1) % queue.length];
    }
    // Post: (n' == n) && (a'[i] == a[i] for i = 0...n - 1) && (Result == a[n - 1])

    // Pre: n > 0
    public static Object dequeue(ArrayQueueADT queue) {
        queue.size--;
        Object toReturn = queue.body[queue.head];
        queue.head = next(queue, queue.head);
        return toReturn;
    }
    // Post: (n' == n - 1) && (a'[i - 1] == a[i] for i = 1...n - 1) && (Result == a[0])

    // Pre: n > 0
    public static Object remove(ArrayQueueADT queue) {
        Object toReturn = peek(queue);
        queue.size--;
        return toReturn;
    }
    // Post: (n' == n - 1) && (a'[i] == a[i] for i = 0...n - 2) && (Result == a[n - 1])

    // Pre: (body.length != 0) && (0 <= x < body.length)
    private static int next(ArrayQueueADT queue, int index) {
        return (index + 1) % queue.length;
    }
    // Result == (x + 1) % body.length

    // Pre: (body.length != 0) && (0 <= x < body.length)
    private static int prev(ArrayQueueADT queue, int index) {
        return (((index - 1) + queue.length) % queue.length);
    }
    // Post: (Result == x - 1 && x > 0) || (Result == body.length && x == 0)
}
