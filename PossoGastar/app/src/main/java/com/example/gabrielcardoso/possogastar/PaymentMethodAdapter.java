package com.example.gabrielcardoso.possogastar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gabrielcardoso.possogastar.model.PaymentMethod;

import java.util.ArrayList;

public class PaymentMethodAdapter extends ArrayAdapter {
    private int mNumElements;
    public PaymentMethodAdapter(Context context, ArrayList<PaymentMethod> methods){
        super(context, 0, methods);
        this.mNumElements = methods.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listMethodView = convertView;
        if (listMethodView == null) {
            listMethodView = LayoutInflater.from(getContext()).inflate(R.layout.account_list_item, parent, false);
        }
        AccountItem item = (AccountItem) getItem(position);
        TextView titleView = (TextView) listMethodView.findViewById(R.id.nomeMetodoPagamento);
        titleView.setText(item.getmTitle());

        //se for o ultimo item, ele deve ficar invisivel. Serve pra poder scrollar o ultimo item da lista
        //pra cima do botao flutuante, q de outra maneira o esconderia. (é um item em branco adicionado para este fim)
        if(item.getmVisibility()==View.INVISIBLE)
            listMethodView.setVisibility(View.INVISIBLE);
        else
            listMethodView.setVisibility(View.VISIBLE);
        return listMethodView;
    }

    public static void setPaymentMethodList(Integer listId, ArrayList<PaymentMethod> methods, Context context, Activity activity){
        PaymentMethodAdapter paymentMethodAdapter = new PaymentMethodAdapter(context, methods);
        ListView methodList = (ListView)activity.findViewById(listId);
        methodList.setAdapter(paymentMethodAdapter);

    }
}
