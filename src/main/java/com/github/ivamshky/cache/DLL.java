package com.github.ivamshky.cache;

public class DLL {
    private int size;
    private Node head;
    private Node tail;

    DLL() {
        size = 0;
        head = null;
        tail = null;
    }

    public Node head() {
        return this.head;
    }

    public Node tail() {
        return this.tail;
    }

    public int size() {
        return this.size;
    }

    public void addToLast(Node n) {
        if (head == null) {
            head = n;
            tail = n;
            return;
        }
        tail.next = n;
        n.prev = tail;
        tail = n;
        size++;
    }

    public void remove(Node n) {
        if (n.next == null) {
            tail = n.prev;
        } else {
            n.next.prev = n.prev;
        }

        if (n.prev == null) {
            head = n.next;
        } else {
            n.prev.next = n.next;
        }
        size--;
    }

    public void moveToFront(Node node) {
        if (node == head) {
            return;
        }
        if (node == tail) {
            tail = node.prev;
        }
        if (node.prev != null)
            node.prev.next = node.next;
        node.prev = null;
        head.prev = node;
        node.next = head;
        head = node;
    }

    public void addToFront(Node node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            node.prev = null;
            head.prev = node;
            head = node;
        }
        size++;
    }

    public Node removeLast() {
        if (tail == null || head == null) {
            return null;
        }
        Node removed = null;
        if (tail == head) {
            removed = tail;
            tail = null;
            head = null;
        } else {
            removed = tail;
            Node secondLast = tail.prev;
            secondLast.next = null;
            tail.prev = null;
            tail = secondLast;
        }
        size--;

        return removed;

    }
}
