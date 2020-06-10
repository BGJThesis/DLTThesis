package com.example.androidphpmysql.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidphpmysql.R;
import com.example.androidphpmysql.models.LogEntryModel;

import java.util.ArrayList;
import java.util.List;

public class TransactionLog extends ArrayAdapter<LogEntryModel> {
    private Activity context;
    private List<LogEntryModel> logList;

    public TransactionLog(Activity context, List<LogEntryModel> logList){
        super(context, R.layout.list_layout, logList);
        this.context = context;
        this.logList = logList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
        TextView senderViewName = (TextView) listViewItem.findViewById(R.id.senderTextView);
        TextView receiverViewName = (TextView) listViewItem.findViewById(R.id.receiverTextView);
        TextView quantityQuadCoins = (TextView) listViewItem.findViewById(R.id.transferAmountTextView);

        LogEntryModel logEntryModel = logList.get(position);

        senderViewName.setText(logEntryModel.getUser1());
        receiverViewName.setText(logEntryModel.getUser2());
        quantityQuadCoins.setText(logEntryModel.getQuadCoins().toString());

        return listViewItem;
    }
}
