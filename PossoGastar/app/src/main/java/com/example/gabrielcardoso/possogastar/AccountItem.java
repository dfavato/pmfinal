package com.example.gabrielcardoso.possogastar;

import android.view.View;

import java.util.Date;

/**
 * Created by Gabriel Cardoso on 26/10/2016.
 */

public class AccountItem {
        private String mTitle;
        private double mAmount = 0.0;
        private Date mLastUse;
        private int mVisibility = View.VISIBLE;

        public String getmTitle(){
            return this.mTitle;
        }
        public double getmAmount(){
             return this.mAmount;
        }
        public Date getmLastUse(){
        return this.mLastUse;
    }
        public int getmVisibility(){
        return this.mVisibility;
    }
        public Date getmLastUset(){
            return this.mLastUse;
        }
        AccountItem(String title, double amount, Date lastUse){
            this.mTitle = title;
            this.mAmount = amount;
            this.mLastUse = lastUse;
        }
        AccountItem(String title, double amount, Date lastUse, int visibility){
            this(title,amount,lastUse);
            this.mVisibility = visibility;
        }
}
