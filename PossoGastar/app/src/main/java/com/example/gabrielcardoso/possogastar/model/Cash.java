package com.example.gabrielcardoso.possogastar.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dfavato on 24/11/16.
 */

public class Cash extends BasePaymentMethod {

    Cash() {
        this.paymentType = PaymentType.CASH;
    }
    public Cash(String name) {
        super(name);
        this.paymentType = PaymentType.CASH;
    }
    public Cash(BasePaymentMethod base) {
        this(base.getName());
        this.setId(base.getId());
    }

    public static Cash queryForId(Long id) throws SQLException {
        BasePaymentMethod b = BasePaymentMethod.queryForId(id);
        if(b != null) {
            if(b.getClass() == Cash.class) {
                return new Cash(b);
            }
        }
        return null;
    }

    public static List<Cash> queryAll() throws SQLException {
        List<Cash> list = new ArrayList<>();
        for(BasePaymentMethod b: (List<BasePaymentMethod>) BasePaymentMethod.queryForField("paymentType", PaymentType.CASH)) {
            list.add(new Cash(b));
        }
        return list;
    }
}
