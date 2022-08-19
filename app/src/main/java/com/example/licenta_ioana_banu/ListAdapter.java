package com.example.licenta_ioana_banu;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.Viewholder> implements SwipeFlingAdapterView.OnItemClickListener {

    private final Context context;
    public static LatLng org,dest;
    public int i;

    public static LatLng getOrg() {
        return org;
    }

    public void setOrg(LatLng org) {
        this.org = org;
    }

    public static LatLng getDest() {
        return dest;
    }

    public void setDest(LatLng dest) {
        this.dest = dest;
    }

    private final ArrayList<ListModel> courseModelArrayList;
   // private DBEventHandler dbEventHandler;
    // Constructor
    public ListAdapter(Context context, ArrayList<ListModel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        ListModel model = courseModelArrayList.get(position);
        holder.destinatie.setText(model.getDestName());
        holder.origine.setText("" + model.getOrgName());


        holder.notInterestedAnymore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOrg(new LatLng(model.getOrgLat(),model.getOrgLong()));
                setDest(new LatLng(model.getDestLat(),model.getDestLong()));
                Intent intent = new Intent( ((Activity)context), PlacePicker.class);// New activity
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ((Activity)context).startActivity(intent);
               // ((Activity)context).finish();


            }


        });
        holder.deleteRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://licenta-ioana-banu-default-rtdb.firebaseio.com/");
                DatabaseReference myRef = database.getReference("routes");

                //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            //Log.i(TAG, String.valueOf(child.getKey())+String.valueOf(i));



                           if(child.child("orgName").getValue().toString().equals(model.getOrgName()))
                            {
                                 myRef.child(child.getKey()).removeValue();
                            }

                        }
                           Intent intent = new Intent( ((Activity)context), EventSettingsActivity.class);// New activity
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ((Activity)context).startActivity(intent);
                            ((Activity)context).finish();
                            return;
                        }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


            }


        });


    }

    @Override
    public int getItemCount() {

        return courseModelArrayList.size();
    }

    @Override
    public void onItemClicked(int i, Object o) {

    }


    public class Viewholder extends RecyclerView.ViewHolder {

        private final TextView destinatie;
        private final TextView origine;

        private final Button notInterestedAnymore;
        private final Button deleteRoute;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            destinatie = itemView.findViewById(R.id.idDestinatie);
            origine = itemView.findViewById(R.id.idOrigin);
            notInterestedAnymore=itemView.findViewById(R.id.notInterestedAnymoreButton);
            deleteRoute=itemView.findViewById(R.id.idDeleteRouteButton);

        }

    }
}