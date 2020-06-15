package com.example.androidphpmysql;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidphpmysql.adapters.FilterUsersAdapter;
import com.example.androidphpmysql.adapters.TransactionLogAdapter;
import com.example.androidphpmysql.models.LogEntryModel;
import com.example.androidphpmysql.models.TransactionModel;
import com.example.androidphpmysql.models.UserDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TransactionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView usernameTextView;
    private Button saveButton;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference transactionDatabaseReference, userDatabaseReference;
    private EditText quantityEditText, receiverEditText;
    private ListView userListView;
    private AlertDialog alertDialog;
    private FrameLayout frameLayout;
    private FirebaseUser user;
    private List<UserDetails> usersList;
    private List<String> usersUID;
    private String receiverUID;
    private FilterUsersAdapter adapter;
    private UserDetails sender, receiver;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TransactionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionFragment newInstance(String param1, String param2) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        firebaseAuth = FirebaseAuth.getInstance();
        transactionDatabaseReference = FirebaseDatabase.getInstance().getReference("transactions");
        userDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
        user = firebaseAuth.getCurrentUser();
        usersList = new ArrayList<>();
        usersUID = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        usernameTextView = (TextView) view.findViewById(R.id.userEmailAddress);
        quantityEditText = (EditText) view.findViewById(R.id.quantityEditText);
        receiverEditText = (EditText) view.findViewById(R.id.receiverEditText);
        saveButton = (Button) view.findViewById(R.id.saveInfoButton);
        frameLayout = (FrameLayout) view.findViewById(R.id.frameLayout_transaction);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = quantityEditText.getText().toString();
                int quadCoins = 0;
                if (!"".equals(temp)){
                    quadCoins = Integer.parseInt(temp);

                    Log.d(TAG, "Amount: "+ quadCoins);

                    if(makeTransaction(quadCoins)){
                        String transactionId = transactionDatabaseReference.push().getKey();
                        TransactionModel transferCall = new TransactionModel(quadCoins, sender.getName(), receiver.getName());
                        transactionDatabaseReference.child(transactionId).setValue(transferCall);

                        Toast.makeText(getActivity(), "Transaction Successful!", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Please input an amount", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        setupUsersDialog(usersList);
        return view;
    }

    public void setupUsersDialog(final List<UserDetails> usersList){
        LayoutInflater layoutInflater = getLayoutInflater();
        View dialog = layoutInflater.inflate(R.layout.users_alert_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        userListView = (ListView) dialog.findViewById(R.id.usersListView);
        builder.setView(dialog);
        builder.setTitle("Please select a user");
        alertDialog = builder.create();

        receiverEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
                frameLayout.requestFocus();
            }
        });

        receiverEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                receiverEditText.performClick();
            }
        });

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                receiver = usersList.get(i);
                receiverUID = usersUID.get(i);
                Log.d(TAG, "Receiver UID: " + receiverUID);
                alertDialog.dismiss();
                receiverEditText.setText(receiver.getName());
            }
        });
    }

    public boolean makeTransaction(Integer amount){
        if(!sender.subtractQuadCoins(amount)){
            Toast.makeText(getActivity(), "You have insufficient amount of money to make this transaction. \n Please try again.", Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            Log.d(TAG, "nowp" + amount);
            Log.d(TAG, "quadCoins: " + receiver.getQuadCoins());
            receiver.addQuadCoins(amount);
        }

        userDatabaseReference.child(user.getUid()).setValue(sender);
        userDatabaseReference.child(receiverUID).setValue(receiver);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                usersUID.clear();
                for (DataSnapshot transactionSnapshot : dataSnapshot.getChildren()){
                    UserDetails userEntry = transactionSnapshot.getValue(UserDetails.class);
                    if(user.getUid().equals(transactionSnapshot.getKey())){
                        sender = userEntry;
                        Log.d(TAG, "NAME: " + sender.getName());
                        continue;
                    }
                    usersList.add(userEntry);
                    String userUID = transactionSnapshot.getKey();
                    usersUID.add(userUID);
                }

                if (getActivity() != null){
                    adapter = new FilterUsersAdapter(getActivity(), usersList);
                    userListView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
