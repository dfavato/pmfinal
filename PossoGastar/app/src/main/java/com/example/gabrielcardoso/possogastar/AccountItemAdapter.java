package com.example.gabrielcardoso.possogastar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Gabriel Cardoso on 26/10/2016.
 */

public class AccountItemAdapter extends ArrayAdapter {
    public AccountItemAdapter(Context context, ArrayList<AccountItem> accounts){
        super(context, 0, accounts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.account_list_item, parent, false);
        }
        //alterar valores do item
        return listItemView;
    }
}
