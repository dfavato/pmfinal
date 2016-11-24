package com.example.gabrielcardoso.possogastar.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dfavato on 20/11/16.
 */


public class RealAccount extends AbstractAccount {
    public enum TIPO {
        CHECKING_ACCOUNT,
        SAVINGS,
        FUND,
        MONEY
    }

    private TIPO type;

    RealAccount () {
    }

    public RealAccount(TIPO type) {
        setType(type);
    }

    private void setType(TIPO type) {
        this.type = type;
    }
}
