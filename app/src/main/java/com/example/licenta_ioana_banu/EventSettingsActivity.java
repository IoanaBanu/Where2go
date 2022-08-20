package com.example.licenta_ioana_banu;



import static com.example.licenta_ioana_banu.LoginActivity.getCurrentUser;
import static com.example.licenta_ioana_banu.utils.FetchAddressIntentService.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventSettingsActivity extends Activity
{
    private RecyclerView eventRV;


    // Arraylist for storing data
    private ArrayList<ListModel> eventModelArrayList;
    //private DBEventHandler eventHandler;
    private ArrayList<Route> array;
    private TextView event;
    private Button notInterestedButton;
    private User curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_settings);
        eventRV = findViewById(R.id.idRVEvent);
        event=findViewById(R.id.textView14);

       // eventHandler= new DBEventHandler(EventSettingsActivity.this);
        //array = eventHandler.readEvent();
        array =new ArrayList<Route>();
        curUser=getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://licenta-ioana-banu-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("routes");
        //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {

                    Route route_aux=new Route(child.child("orgName").getValue().toString(),
                                        child.child("destName").getValue().toString(),
                                        child.child("owner").getValue().toString(),
                                        Double.parseDouble(child.child("orgLong").getValue().toString()),
                                        Double.parseDouble(child.child("orgLat").getValue().toString()),
                                        Double.parseDouble(child.child("destLong").getValue().toString()),
                                        Double.parseDouble(child.child("destLat").getValue().toString())
                                        );
                    //Log.i(TAG, route_aux.getDestName());
                    //Log.i(TAG,"Asta e cu x"+ route_aux.getDestName());
                    if(route_aux.getOwner().equals(curUser.getUsername())) {
                        array.add(route_aux);
                    }
                }
                //Log.i(TAG,"Zide da"+ array.size());
                eventModelArrayList = new ArrayList<>();
                for (int i = 0; i < array.size(); i++) {
                    Route route_aug=array.get(i);

                    //Log.i(TAG,"Asta e"+ route_aug.getDestName());
                    eventModelArrayList.add(new ListModel( route_aug.getOrgName(),route_aug.getDestName(), route_aug.getOrgLong(), route_aug.getOrgLat(),route_aug.getDestLong(),route_aug.getDestLat()));

                }

                ListAdapter courseAdapter = new ListAdapter(EventSettingsActivity.this, eventModelArrayList);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EventSettingsActivity.this, LinearLayoutManager.VERTICAL, false);

                eventRV.setLayoutManager(linearLayoutManager);
                eventRV.setAdapter(courseAdapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EventSettingsActivity.this, PlacePicker.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

    }

    }
