package ru.job4j.thread;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Wget implements Runnable {
    private final String url;
    private final int speed;


    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String fileName = Paths.get(new URI(url).getPath())
                        .getFileName()
                        .toString();
                File file = new File("wget_" + fileName);
                try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                     FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                    byte[] dataBuffer = new byte[1024];
                    int bytesRead;
                    long time = System.currentTimeMillis();
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                        long rsl = System.currentTimeMillis() - time;
                        int speedTest = (int) (1024 / (rsl / 1000));
                        if (speedTest < speed) {
                            Thread.sleep((speed - speedTest) * 1000);
                            System.out.printf("Delay: %d%n", (speed - speedTest) * 1000);
                        }
                        time = System.currentTimeMillis();
                    }
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static boolean isValidURL(String url) {
        boolean result = true;
        try {
            new URL(url).toURI();
        } catch (URISyntaxException | MalformedURLException ex) {
            result = false;
        }
        return result;
    }

    private static boolean isValidSpeed(int speed) {
        return speed > 0;
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Arguments must be specified.");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        if (!isValidURL(url)) {
            throw new IllegalArgumentException("Incorrect URL.");
        }
        if (!isValidSpeed(speed)) {
            throw new IllegalArgumentException("Speed must be great than zero.");
        }
        try {
            Thread wget = new Thread(new Wget(url, speed));
            wget.start();
            wget.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

