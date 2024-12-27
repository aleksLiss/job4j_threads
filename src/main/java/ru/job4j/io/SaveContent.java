package ru.job4j.io;

import java.io.*;

public final class SaveContent implements Saver {

    private final File file;

    public SaveContent(File file) {
        this.file = file;
    }

    @Override
    public void saveContent(String content) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
