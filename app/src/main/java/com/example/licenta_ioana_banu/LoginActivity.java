package com.example.licenta_ioana_banu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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

public class LoginActivity extends AppCompatActivity {
    private EditText userNameEdt,passwordEdt,passwordVerEdt;
    private Button loginButton,guestLoginButton,registerButton;
    public static User currentUser;
    private User currentUserAug;
    public static void setCurrentUser(User currentUser) {
        LoginActivity.currentUser = currentUser;
    }




    public static User getCurrentUser()
    {
        return currentUser;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // initializing all our variables.
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        userNameEdt = findViewById(R.id.idUsernameLogin);
        passwordEdt = findViewById(R.id.idPasswordLogin);

        loginButton = findViewById(R.id.idLoginButton);
        guestLoginButton = findViewById(R.id.idGuestLoginButton);
        registerButton=findViewById(R.id.idRegisterButtonLogin);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,  RegisterActivity.class);// New activity
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        guestLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUserAug=new User("guest","guest","guest","guest");
                setCurrentUser(currentUserAug);
                Intent intent = new Intent(LoginActivity.this,  PlacePicker.class);// New activity
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
         });




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String username = userNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                //String passwordVer = passwordVerEdt.getText().toString();


                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }


                try {
                    password=AESUtils.encrypt(password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
               // User user = new User(username,password,nume,prenume,new ArrayList<Integer>());
                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://licenta-ioana-banu-default-rtdb.firebaseio.com/");
                DatabaseReference myRef = database.getReference("user");
                //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                String finalPassword = password;
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChild(username)&&snapshot.child(username).child("password").getValue().equals(finalPassword)) {
                            Toast.makeText(LoginActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                            currentUserAug=new User(username,
                                    snapshot.child(username).child("password").getValue().toString(),
                                    snapshot.child(username).child("prenume").getValue().toString(),
                                    snapshot.child(username).child("nume").getValue().toString());

                            setCurrentUser(currentUserAug);
                            Intent intent = new Intent(LoginActivity.this,  PlacePicker.class);// New activity
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            return;
                        }
                        else {
                           // myRef.child(username).setValue(user);
                            Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
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
