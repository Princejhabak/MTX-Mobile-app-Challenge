package com.example.android.surveyapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.ProximityExposure;

import com.example.android.surveyapp.ui.ProximityExposureDetailsActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProximityAdapter extends RecyclerView.Adapter<ProximityViewHolder> {

    Context context;
    List<List<ProximityExposure>> itemList;

    public ProximityAdapter(Context context, List<List<ProximityExposure>> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ProximityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.proximity_exposure_list_item, parent, false);
        return new ProximityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProximityViewHolder holder, int position) {

        final List<ProximityExposure> proximityExposureList = itemList.get(position);

        String li_count_string = proximityExposureList.size() + " Confirmed Exposures";
        holder.li_count.setText(li_count_string);

        String risk_level = "Low";

        /** Risk is considered high if a positive case is found within 100 m*/
        for(ProximityExposure pe: proximityExposureList){
            if(pe.getDistance()<100) {
                risk_level = "High";
                break;
            }
        }

        /** If risk level is low show red radar otherwise yellow radar*/
        if(risk_level.equals("Low"))
            holder.li_image.setImageResource(R.drawable.ic_radar_yellow);
        else
            holder.li_image.setImageResource(R.drawable.ic_radar_red);

        String risk_level_string = "Risk Level: " + risk_level;
        holder.li_address.setText(risk_level_string);

        /** Parse string to long*/
        Long unixSeconds = Long.parseLong(proximityExposureList.get(0).getDateTime());
        Date date = new java.util.Date(unixSeconds*1000L);

        String fd = formatDate(date);
        String ft = formatTime(date);
        String dt = fd ;

        holder.li_date_time.setText(dt);

        String li_radius_string = "500 m radius" ;
        holder.li_radius.setText(li_radius_string);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProximityExposureDetailsActivity.class);
                intent.putExtra("ExposureList",(Serializable) proximityExposureList);
                context.startActivity(intent);

            }
        });

    }

    /** Returns size of list*/
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /** Helper method to get formatted date */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /** Helper method to get formatted time */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }


}