package ru.job4j.io;

import java.io.*;
import java.util.Scanner;
import java.util.function.Predicate;

public final class ParseFile implements Parser {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    @Override
    public String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.next();
                char[] chars = line.toCharArray();
                for (char character : chars) {
                    if (filter.test(character)) {
                        output.append(character);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return output.toString();
    }
}


