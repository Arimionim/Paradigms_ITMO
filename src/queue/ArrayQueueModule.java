package queue;

public class ArrayQueueModule {

    private static int head = 0;
    private static int tail = -1;
    private static int size = 0;
    private static int length = 4;
    private static Object[] body = new Object[4];

    public static boolean isEmpty(){
        return size != 0;
    }

    public static int size(){
        return size;
    }

    public static void clear(){
        size = 0;
        head = 0;
        tail = -1;
    }

    public static void enqueue(Object x){
        resize(size + 1);
        tail = next(tail);
        body[tail] = x;
    }

    public static void push(Object x){
        resize(size + 1);
        head = prev(head);
        if (size == 1){
            tail = head;
        }
        body[head] = x;
    }

    private static void resize(int newSize){
        if (newSize <= length){
            size = newSize;
        }
        else{
            int newLength = length;
            while(newLength < newSize * 2){
                newLength *= 2;
            }
            Object[] newBody = new Object[newLength];
            tail = size - 1;
            size = newSize;
            for (int i = head; i < head + size; i++){
                newBody[i - head] = body[i % length];
            }
            body = newBody;
            length = newLength;
            head = 0;
        }
    }

    public static Object element(){
        return body[head];
    }

    public static Object peek(){
        return body[tail];
    }

    public static Object dequeue(){
        size--;
        Object toReturn = body[head];
        head = next(head);
        return toReturn;
    }

    public static Object remove(){
        size--;
        Object toReturn = body[tail];
        tail = prev(tail);
        return toReturn;
    }

    private static int next(int index){
        return (index + 1) % length;
    }

    private static int prev(int index){
        return ((index - 1) % length + length) % length;
    }
}
