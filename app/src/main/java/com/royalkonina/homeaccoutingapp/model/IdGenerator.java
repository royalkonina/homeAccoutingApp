package com.royalkonina.homeaccoutingapp.model;

public class IdGenerator {

    private static long lastUsedId = 1;

    public static long generateId() {
        return lastUsedId++;
    }

}
