package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                }
        );
        Thread second = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                }
        );
        first.start();
        second.start();
        while (second.getState() != Thread.State.TERMINATED) {
            while (first.getState() != Thread.State.TERMINATED) {
                continue;
            }
        }
        System.out.println("All threads have completed their work");
    }
}
