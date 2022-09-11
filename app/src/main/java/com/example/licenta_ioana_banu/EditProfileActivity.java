package com.example.licenta_ioana_banu;

import static com.example.licenta_ioana_banu.LoginActivity.getCurrentUser;

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

public class EditProfileActivity extends AppCompatActivity {
    private EditText numeEdt,prenumeEdt,userNameEdt,passwordEdt,passwordVerEdt;
    private Button registerButton;
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);

        // initializing all our variables.
        currentUser=getCurrentUser();
        numeEdt = findViewById(R.id.idNumeEdit);
        prenumeEdt = findViewById(R.id.idPrenumeEdit);
        passwordEdt = findViewById(R.id.idPasswordEdit);
        passwordVerEdt = findViewById(R.id.idPasswordVerEdit);
        registerButton = findViewById(R.id.idEditButton);
        numeEdt.setText(currentUser.getNume());
        prenumeEdt.setText(currentUser.getPrenume());


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nume = numeEdt.getText().toString();
                String prenume = prenumeEdt.getText().toString();
                //String username = userNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                String passwordVer = passwordVerEdt.getText().toString();




                if(!password.equals(passwordVer))
                {
                    Toast.makeText(EditProfileActivity.this, "The passwords are not matching", Toast.LENGTH_SHORT).show();
                    return;
                }



                if(!password.isEmpty())
                {
                    try {
                        password=AESUtils.encrypt(password);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    currentUser.setPassword(password);

                }
                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://licenta-ioana-banu-default-rtdb.firebaseio.com/");
                DatabaseReference myRef = database.getReference("user");
                //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChild(currentUser.getUsername())) {
                            currentUser.setPrenume(prenumeEdt.getText().toString());
                            currentUser.setNume(numeEdt.getText().toString());
                            myRef.child(currentUser.getUsername()).setValue(currentUser);
                            //Toast.makeText(EditProfileActivity.this, "Este ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditProfileActivity.this,  PlacePicker.class);// New activity
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            return;
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


                // myRef.child(username).setValue(user);
                //myRef.child("one").child("username").setValue("papa");




                /*Intent intent = new Intent(RegisterActivity.this, MainActivity.class);// New activity
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();*/

            }
        });
    }
}
