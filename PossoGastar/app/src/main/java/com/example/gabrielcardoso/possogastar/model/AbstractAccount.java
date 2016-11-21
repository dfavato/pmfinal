package com.example.gabrielcardoso.possogastar.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by dfavato on 19/11/16.
 */


public abstract class AbstractAccount {
    @DatabaseField(generatedId = true)
    private Long accountId;

    @DatabaseField(unique = true, canBeNull = false)
    private String accountName;

    void setName(String name) {
        this.accountName = name;
    }

    Long getId() {
        return this.accountId;
    }

    String getName() {
        return this.accountName;
    }

    @Override
    public String toString() {
        return this.getId() + " - " + this.getName();
    }
}
