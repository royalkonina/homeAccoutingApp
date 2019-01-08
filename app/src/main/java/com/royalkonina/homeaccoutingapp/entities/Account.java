package com.royalkonina.homeaccoutingapp.entities;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class Account {

    private long id;
    @NonNull
    private String name;
    private String description;
    private long balance;
    private List<Operation> operations = new ArrayList<>();

    public Account(long id, @NonNull String name, String description, long balance) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.balance = balance;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getBalance() {
        return balance;
    }

    public long getId() {
        return id;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
    }

}
