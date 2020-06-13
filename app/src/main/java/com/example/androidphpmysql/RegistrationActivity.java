package com.example.androidphpmysql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidphpmysql.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextPassword, editTextEmail, quantityEditText, editTextName;
    private Button buttonRegister;
    private TextView progressDialog, loginText;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        sharedPreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);

        editTextName = (EditText) findViewById(R.id.editName);
        editTextEmail = (EditText) findViewById(R.id.editEmail);
        editTextPassword = (EditText) findViewById(R.id.editPassword);
        quantityEditText = (EditText) findViewById(R.id.startingQuantityEditText);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        progressDialog = (TextView) findViewById(R.id.progressDialog);
        loginText = (TextView) findViewById(R.id.switchLogin);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("yowp");
                registerUser();
            }
        });


        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    private void registerUser(){
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String name = editTextName.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_LONG).show();
            return;
        }

        String temp = quantityEditText.getText().toString();
        Integer quadCoins = 0;
        if (!"".equals(temp)){
            quadCoins = Integer.parseInt(temp);
        }
        else{
            Toast.makeText(getApplicationContext(), "Please Enter QuadCoins", Toast.LENGTH_LONG).show();
            return;
        }

        final UserModel user = new UserModel(name, email, password, quadCoins);

        progressDialog.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Registered Successfully!!", Toast.LENGTH_LONG).show();
                    final String userUid = firebaseAuth.getUid();
//                  String userUid = databaseReference.push().getKey();
                    databaseReference.child(userUid).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            editor = sharedPreferences.edit();
                            editor.putString("uid", userUid);
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "Registered Unsuccessfully :((", Toast.LENGTH_LONG).show();
                    Log.w(RegistrationActivity.class.getSimpleName(), "createUserWithEmail:failure", task.getException());
                }
                progressDialog.setVisibility(View.INVISIBLE);
            }
        });
    }
}
