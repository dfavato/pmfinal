package com.example.gabrielcardoso.possogastar;

/**
 * Created by Gabriel Cardoso on 26/10/2016.
 */

public class AccountItem {
        private String mTitle;
        private double mAmount;

        public String getmTitle(){
            return this.mTitle;
        }
        public double getmAmount(){
             return this.mAmount;
        }
        AccountItem(String title, double amount){
            this.mTitle = title;
            this.mAmount = amount;
        }
}
