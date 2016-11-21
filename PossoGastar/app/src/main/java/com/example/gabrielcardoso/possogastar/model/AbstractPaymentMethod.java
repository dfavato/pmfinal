package com.example.gabrielcardoso.possogastar.model;


import com.j256.ormlite.field.DatabaseField;

import java.util.Calendar;


/**
 * Created by dfavato on 20/11/16.
 */

public abstract class AbstractPaymentMethod {
    @DatabaseField(id = true)
    private Long id;

    @DatabaseField
    private String paymentMethodName;

    public abstract Calendar paymentDate();
}
