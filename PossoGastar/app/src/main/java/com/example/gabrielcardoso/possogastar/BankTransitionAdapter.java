package com.example.gabrielcardoso.possogastar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gabriel Cardoso on 21/11/2016.
 */

public class BankTransitionAdapter extends ArrayAdapter{
    public BankTransitionAdapter(Context context, ArrayList<BankTransition> transitions){
        super(context, 0, transitions);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.bank_transition_list_item, parent, false);
        }
        BankTransition item = (BankTransition) getItem(position);
        //setando quantia
        TextView amount = (TextView)listItemView.findViewById(R.id.amount);
        amount.setTextColor(getContext().getResources().getColor(item.getmAmountTextColor()));
        amount.setText(String.valueOf(item.getmAmount()));
        //setando data
        TextView date = (TextView)listItemView.findViewById(R.id.date);
        date.setText(String.valueOf(item.getmDate()));
        //setando hora
        TextView hour = (TextView)listItemView.findViewById(R.id.hour);
        hour.setText(String.valueOf(item.getmTime()));
        //setando participantes
        //sender
        ((TextView)listItemView.findViewById(R.id.sender_text_view)).setText(item.getmSenderTitle());
        if(item.getmSenderVisibility()==View.GONE)
            listItemView.findViewById(R.id.sender_linear_layout).setVisibility(View.GONE);
        //receiver
        ((TextView)listItemView.findViewById(R.id.receiver_text_view)).setText(item.getmSenderTitle());
        if(item.getmReceiverVisibility()==View.GONE)
            listItemView.findViewById(R.id.receiver_linear_layout).setVisibility(View.GONE);
        return listItemView;
    }
}
