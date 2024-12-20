package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread loading = new Thread(
                () -> {
                    try {
                        int index = 0;
                        while (index < 101) {
                            index++;
                            System.out.print("\rLoading : " + index + "%");
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
        );
        loading.start();
    }
}
