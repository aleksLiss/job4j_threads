package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                }
        );
        first.start();
        Thread second = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                }
        );
        second.start();
        while (second.getState() != Thread.State.TERMINATED
                && first.getState() != Thread.State.TERMINATED) {
        }
        System.out.println("All threads have completed their work");
    }
}
