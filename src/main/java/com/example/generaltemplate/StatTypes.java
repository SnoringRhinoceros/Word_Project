package com.example.generaltemplate;

public enum StatTypes {
    STAMINA, MAX_STAMINA, MONEY;

    public static StatTypes[] getAll() {
        return values();
    }
}
