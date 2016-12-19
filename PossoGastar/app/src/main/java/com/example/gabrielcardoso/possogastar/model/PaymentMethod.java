package com.example.gabrielcardoso.possogastar.model;


public class PaymentMethod extends BasePaymentMethod {
    //Construtores
    public PaymentMethod(String name, String paymentType, float limit, byte dueDate, byte closeDate){
        super(name);
        setPaymentType(PaymentType.valueOf(paymentType));
        setLimit(limit);
        setDueDate(dueDate);
        setCloseDate(closeDate);
    }

    //Getters e Setters
    private void setPaymentType(PaymentType paymentType){
        this.paymentType = paymentType;
    }

    public PaymentType getPaymentType(){
        return this.paymentType;
    }

    private void setLimit(float limit){
        this.limit = limit;
    }

    public float getLimit(){
        return this.limit;
    }

    private void setDueDate(byte dueDate){
        this.dueDate = dueDate;
    }

    public byte getDueDate(){
        return this.dueDate;
    }

    private void setCloseDate(byte closeDate){
        this.closeDate = closeDate;
    }

    public byte getCloseDate(){
        return this.closeDate;
    }
}
