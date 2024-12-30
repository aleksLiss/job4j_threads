package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public final class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        accounts.put(account.id(), account);
        return accounts.containsKey(account.id());
    }

    public synchronized boolean update(Account account) {
        return accounts.containsKey(account.id())
                && accounts.computeIfPresent(account.id(),
                (k, v) -> new Account(account.id(), account.amount())) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Account from = getById(fromId).orElseThrow(()
                -> new IllegalArgumentException("Not found account by id = " + fromId));
        Account to = getById(toId).orElseThrow(()
                -> new IllegalArgumentException("Not found account by id = " + toId));
        if (from.amount() < amount) {
            throw new IllegalArgumentException("There is not enough money in the account");
        }
        return accounts.computeIfPresent(fromId, (k, v)
                -> new Account(fromId, v.amount() - amount)) != null
                && accounts.computeIfPresent(toId, (k, v)
                -> new Account(toId, v.amount() + amount)) != null;
    }
}