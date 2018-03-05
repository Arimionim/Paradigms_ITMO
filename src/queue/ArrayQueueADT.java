package queue;

import java.lang.reflect.InvocationTargetException;

public class ArrayQueueADT {

    private int head = 0;
    private int tail = -1;
    private int size = 0;
    private int length = 4;
    private Object[] body = new Object[4];

    public static boolean isEmpty(ArrayQueueADT queue){
        return queue.size != 0;
    }

    public static int size(ArrayQueueADT queue){
        return queue.size;
    }

    public static void clear(ArrayQueueADT queue){
        queue.size = 0;
        queue.head = 0;
        queue.tail = -1;
    }

    public static void enqueue(ArrayQueueADT queue, Object x){
        resize(queue, queue.size + 1);
        queue.tail = next(queue, queue.tail);
        queue.body[queue.tail] = x;
    }


    public static void push(ArrayQueueADT queue, Object x){
        resize(queue, queue.size + 1);
        queue.head = prev(queue, queue.head);
        if (queue.size == 1){
            queue.tail = queue.head;
        }
        queue.body[queue.head] = x;
    }

    private static void resize(ArrayQueueADT queue, int newSize){
        if (newSize <= queue.length){
            queue.size = newSize;
        }
        else{
            int newLength = queue.length;
            while(newLength < newSize * 2){
                newLength *= 2;
            }
            Object[] newBody = new Object[newLength];
            queue.tail = queue.size - 1;
            queue.size = newSize;
            for (int i = queue.head; i < queue.head + queue.size; i++){
                newBody[i - queue.head] = queue.body[i % queue.length];
            }
            queue.body = newBody;
            queue.length = newLength;
            queue.head = 0;
        }
    }

    public static Object element(ArrayQueueADT queue){
        return queue.body[queue.head];
    }

    public static Object peek(ArrayQueueADT queue){
        return queue.body[queue.tail];
    }


    public static Object dequeue(ArrayQueueADT queue){
        queue.size--;
        Object toReturn = queue.body[queue.head];
        queue.head = next(queue, queue.head);
        return toReturn;
    }

    public static Object remove(ArrayQueueADT queue){
        queue.size--;
        Object toReturn = queue.body[queue.tail];
        queue.tail = prev(queue, queue.tail);
        return toReturn;
    }

    private static int next(ArrayQueueADT queue, int index){
        return (index + 1) % queue.length;
    }

    private static int prev(ArrayQueueADT queue, int index){
      //  System.out.println("length: " + queue.length + " old: " + index + " prev: " + ((index - 1) + queue.length) % queue.length);
      //  System.out.println("size: " + queue.size + " head: " + queue.head + " tail: " + queue.tail);
        return (((index - 1) + queue.length) % queue.length);
    }
}
