package queue;

public class ArrayQueue {

    private int head = 0;
    private int tail = -1;
    private int size = 0;
    private int length = 4;
    private Object[] body = new Object[4];

    public boolean isEmpty(){
        return size != 0;
    }

    public int size(){
        return size;
    }

    public void clear(){
        size = 0;
        head = 0;
        tail = -1;
    }

    public void enqueue(Object x){
        resize(size + 1);
        tail = next(tail);
        if (size == 1) {
            head = tail;
        }
        body[tail] = x;
    }

    public void push(Object x){
        resize(size + 1);
        head = prev(head);
        if (size == 1){
            tail = head;
        }
        body[head] = x;
    }

    private void resize(int newSize){
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

    public Object element(){
        return body[head];
    }

    public Object peek(){
        return body[tail];
    }


    public Object dequeue(){
        size--;
        Object toReturn = body[head];
        head = next(head);
        return toReturn;
    }


    public Object remove(){
        size--;
        Object toReturn = body[tail];
        tail = prev(tail);
        return toReturn;
    }

    private int next(int index){
        return (index + 1) % length;
    }

    private int prev(int index){
        return (((index - 1) % length + length) % length);
    }
}
