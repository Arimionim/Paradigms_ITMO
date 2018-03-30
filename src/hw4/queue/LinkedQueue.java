package hw4.queue;

public class LinkedQueue extends AbstractQueue {

    private Node head;
    private Node tail;

    public Object element() {
        return head.value;
    }

    protected void dequeueImp() {
        head = head.next;
    }

    public void enqueue(Object object) {
        Node newNode = new Node(null, object);
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (size == 0) {
            head = tail;
        }
        size += 1;
    }

    protected void clearImp() {
        head = null;
        tail = null;
    }

    private class Node {
        private Node next;
        private Object value;

        private Node(Node newNext, Object newValue) {
            next = newNext;
            value = newValue;
        }
    }

    protected LinkedQueue createCopy() {
        LinkedQueue result = new LinkedQueue();
        Node temp = head;
        while (temp != null) {
            result.enqueue(temp.value);
            temp = temp.next;
        }
        return result;
    }
}
