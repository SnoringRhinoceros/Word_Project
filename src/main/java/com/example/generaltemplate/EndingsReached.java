package com.example.generaltemplate;

import java.io.Serializable;
import java.util.ArrayList;

public class EndingsReached implements Serializable {
    private final ArrayList<PossibleEndings> all;

    EndingsReached() {
        all = new ArrayList<>();
    }

    public ArrayList<PossibleEndings> getAll() {
        return all;
    }
}
