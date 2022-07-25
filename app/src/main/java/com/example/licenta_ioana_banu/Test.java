package com.example.licenta_ioana_banu;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Date;

public class Test extends AppCompatActivity {
    private EditText numeEdt,prenumeEdt,userNameEdt,passwordEdt,passwordVerEdt;
    private Button registerButton;
    private MySQL connection = null;
    private Integer k=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        // initializing all our variables.
        numeEdt = findViewById(R.id.idNumeRegister);
        prenumeEdt = findViewById(R.id.idPrenumeRegister);
        userNameEdt = findViewById(R.id.idUsernameRegister);
        passwordEdt = findViewById(R.id.idPasswordVerRegister);
        passwordVerEdt = findViewById(R.id.idPasswordVerRegister);
        registerButton = findViewById(R.id.idRegisterButton);
        connection = new MySQL("192.168.1.1", "users", 3306, "root", "");

        System.out.println(connection.connection());
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://licenta-ioana-banu-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("user");
        //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
       /* myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(username)) {


                    Toast.makeText(RegisterActivity.this, "da  "+snapshot.child(username).child("nume").getValue().equals("numelel"), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "nu", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        User user = new User(username,password,nume,prenume,new ArrayList<Integer>());
*/
        // myRef.child(username).setValue(user);
        //myRef.child("one").child("username").setValue("papa");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

// Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://licenta-ioana-banu-default-rtdb.firebaseio.com/");
                DatabaseReference myRef = database.getReference("users");

                //User user = new User("test","test","test","test",new ArrayList<Integer>());

                myRef.child("one").setValue("papa");
                //myRef.child("one").child("username").setValue("papa");





            }
        });
    }
}
