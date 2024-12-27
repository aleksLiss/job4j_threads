package ru.job4j.io;

import java.util.function.Predicate;

public interface Parser {

    public String content(Predicate<Character> filter);
}
