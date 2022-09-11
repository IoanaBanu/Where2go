package com.example.licenta_ioana_banu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import java.util.ArrayList;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private EditText numeEdt,prenumeEdt,userNameEdt,passwordEdt,passwordVerEdt;
    private Button registerButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        // initializing all our variables.
        numeEdt = findViewById(R.id.idNumeRegister);
        prenumeEdt = findViewById(R.id.idPrenumeRegister);
        userNameEdt = findViewById(R.id.idUsernameRegister);
        passwordEdt = findViewById(R.id.idPasswordRegister);
        passwordVerEdt = findViewById(R.id.idPasswordVerRegister);
        registerButton = findViewById(R.id.idRegisterButton);



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nume = numeEdt.getText().toString();
                String prenume = prenumeEdt.getText().toString();
                String username = userNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                String passwordVer = passwordVerEdt.getText().toString();
               


                if (nume.isEmpty() || prenume.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(passwordVer))
                {
                    Toast.makeText(RegisterActivity.this, "The passwords are not matching", Toast.LENGTH_SHORT).show();
                    return;
                }


                try {
                    password=AESUtils.encrypt(password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                User user = new User(username,password,nume,prenume);
                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://licenta-ioana-banu-default-rtdb.firebaseio.com/");
                DatabaseReference myRef = database.getReference("user");
                //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChild(username)) {
                            Toast.makeText(RegisterActivity.this, "This user already exists!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            myRef.child(username).setValue(user);
                            Toast.makeText(RegisterActivity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this,  LoginActivity.class);// New activity
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });



            }
        });
    }
}
