package ru.job4j.ref;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class UserCache {
    private volatile ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    public User findById(int id) {
        return User.of(users.get(id).getName());
    }

    public List<User> findAll() {
        List<User> usersList = new CopyOnWriteArrayList<>();
        for (Map.Entry<Integer, User> entry : users.entrySet()) {
            usersList.add(User.of(entry.getValue().getName()));
        }
        return usersList;
    }
}
