package com.example.androidphpmysql.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidphpmysql.R;
import com.example.androidphpmysql.models.LogEntryModel;
import com.example.androidphpmysql.models.UserDetails;

import java.util.List;

public class FilterUsersAdapter extends ArrayAdapter<UserDetails> {
    private Activity context;
    private List<UserDetails> usersList;

    public FilterUsersAdapter(Activity context, List<UserDetails> usersList){
        super(context, R.layout.users_list_layout, usersList);
        this.context = context;
        this.usersList = usersList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.users_list_layout, null, true);
        TextView emailAdress = (TextView) view.findViewById(R.id.receiverEmailTextView);
        TextView name = (TextView) view.findViewById(R.id.receiverNameTextView);
        TextView quadCoins = (TextView) view.findViewById(R.id.receiverQuadcoinsTextView);

        UserDetails userDetails = usersList.get(position);

        emailAdress.setText("(" + userDetails.getEmailAddress() + ")");
        name.setText(userDetails.getName());
        quadCoins.setText("QuadCoins: " + userDetails.getQuadCoins().toString());

        return view;
    }
}
