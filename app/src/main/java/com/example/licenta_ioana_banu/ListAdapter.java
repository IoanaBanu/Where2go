package com.example.licenta_ioana_banu;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.maps.model.LatLng;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.Viewholder> implements SwipeFlingAdapterView.OnItemClickListener {

    private final Context context;
    public static LatLng org,dest;

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
        holder.courseNameTV.setText(model.getDestName());
        holder.courseRatingTV.setText("" + model.getDestName());
        holder.courseIV.setText(model.getDestName());

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

    }

    @Override
    public int getItemCount() {

        return courseModelArrayList.size();
    }

    @Override
    public void onItemClicked(int i, Object o) {

    }


    public class Viewholder extends RecyclerView.ViewHolder {

        private final TextView courseNameTV;
        private final TextView courseRatingTV;
        private final TextView courseIV;
        private final Button notInterestedAnymore;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            courseIV = itemView.findViewById(R.id.idIVCourseImage);
            courseNameTV = itemView.findViewById(R.id.idTVCourseName);
            courseRatingTV = itemView.findViewById(R.id.idCardviewTiltle);
            notInterestedAnymore=itemView.findViewById(R.id.notInterestedAnymoreButton);

        }

    }
}