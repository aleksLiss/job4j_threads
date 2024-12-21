package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

public class Wget implements Runnable {
    private final String url;
    private final int speed;


    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try {
            long startAt = System.currentTimeMillis();
            File file = new File("tmp.xml");
            try (InputStream input = new URL(url).openStream();
                 FileOutputStream output = new FileOutputStream(file)) {
                System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
                byte[] dataBuffer = new byte[512];
                int bytesRead;
                while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                    long downloadAt = System.nanoTime();
                    output.write(dataBuffer, 0, bytesRead);
                    System.out.print("Read 512 bytes : " + (System.nanoTime() - downloadAt) + " nano. ");
                    long t = System.nanoTime() - downloadAt;
                    long ms = t / speed;
                    if (speed < t) {
                        System.out.printf("Delay: %s ms.%n", ms);
                        Thread.sleep(ms);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(Files.size(file.toPath()) + "bytes");
        } catch (Exception ex) {
            ex.printStackTrace();
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

