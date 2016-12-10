package com.example.gabrielcardoso.possogastar;

import android.view.View;

/**
 * Created by Gabriel Cardoso on 21/11/2016.
 */

public class BankTransition {
    //constantes
    private static final Integer UNSETTED = -1;
    //Sobre quem recebe a quantia
    private Integer mReceiverId = UNSETTED;
    private String mReceiverTitle = "Conta rebendo quantia";
    private Integer mReceiverVisibility = UNSETTED;
    //Sobre quem envia a quantia
    private Integer mSenderId = UNSETTED;
    private String mSenderTitle = "Conta enviando quantia";
    private Integer mSenderVisibility = UNSETTED;
    //outras informações da transação
    private String mDate = "00/00/00";
    private String mTime = "00:00";
    private double mAmount = 0.0;
    //sobre a exibição
    private Integer mIdToFocus = UNSETTED;
    //se setado, essa varivel informa em qual participante da transação é importante focar. Se não for setada, nenhum ganhará foco
    private Integer mAmountTextColor = UNSETTED;

    public BankTransition(String Date, String Time, Integer ReceiverId, String ReceiverTitle,
                          Integer SenderId, String SenderTitle, double Amount, Integer IdToFocus){
        this.mDate = Date;
        this.mTime = Time;
        this.mReceiverId = ReceiverId;
        this.mReceiverTitle = ReceiverTitle;
        this.mSenderId = SenderId;
        this.mSenderTitle = SenderTitle;
        this.mAmount = Amount;
        this.mIdToFocus = IdToFocus;
        treatAccountFocus();

    }
    public BankTransition(String Date, String Time, Integer ReceiverId, String ReceiverTitle,
                          Integer SenderId, String SenderTitle, double Amount ){
        this(Date,Time,ReceiverId,ReceiverTitle,SenderId,SenderTitle,Amount,UNSETTED);
    }

    public void treatAccountFocus(){
        //trata se o saldo deve ser mostrado negativo, positivo e tambem a cor do texto
        if(this.mIdToFocus == UNSETTED){
            this.mAmountTextColor = R.color.colorPrimaryText;
            this.mReceiverVisibility = View.VISIBLE;
            this.mSenderVisibility = View.VISIBLE;
        }else if(this.mIdToFocus == this.mReceiverId){
            this.mAmountTextColor = R.color.colorPrimary;
            this.mReceiverVisibility = View.GONE;
            this.mSenderVisibility = View.VISIBLE;
        }else if(this.mIdToFocus == this.mSenderId){
            this.mAmountTextColor = R.color.colorAccent;
            this.mAmount = this.mAmount*(-1);
            this.mReceiverVisibility = View.VISIBLE;
            this.mSenderVisibility = View.GONE;
        }
    }

    //metodos get necessarios
    public String getmReceiverTitle(){
        return this.mReceiverTitle;
    }
    public Integer getmReceiverVisibility(){
        return this.mReceiverVisibility;
    }
    public String getmSenderTitle(){
        return this.mSenderTitle;
    }
    public Integer getmSenderVisibility(){
        return this.mSenderVisibility;
    }
    public String getmDate(){
        return this.mDate;
    }
    public String getmTime(){
        return this.mTime;
    }
    public double getmAmount(){
        return this.mAmount;
    }
    public Integer getmAmountTextColor(){
        return this.mAmountTextColor;
    }
}