package ru.job4j.linked;

public final class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() throws CloneNotSupportedException {
        return (Node<T>) next.clone();
    }

    public T getValue() {
        return value;
    }
}