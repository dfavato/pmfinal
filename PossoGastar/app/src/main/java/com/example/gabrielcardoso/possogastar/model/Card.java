package com.example.gabrielcardoso.possogastar.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by dfavato on 20/11/16.
 */

@DatabaseTable
public class Card extends AbstractPaymentMethod {
    @DatabaseField
    private float limit;

    @DatabaseField
    private byte dueDate; //vencimento

    @DatabaseField
    private byte closeDate; //data de fechamento da fatura

    Card() {
        this.limit = 0;
    }
    public Card(Float limit) {
        this();
        setLimit(limit);
    }

    public void setLimit(Float limit) {
        this.limit = limit;
    }

    public Calendar paymentDate() {
        Calendar today = Calendar.getInstance();
        if(today.get(Calendar.DAY_OF_MONTH) > this.closeDate) {
            //fatura fechada pagamento só no próximo vencimento
            return new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH)+1, this.dueDate);
        } else {
            return new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), this.dueDate);
        }
    }
}
