package com.example.android.surveyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.ProximityExposure;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProximityDetailsAdapter extends RecyclerView.Adapter<ProximityViewHolder> {

    Context context;
    List<ProximityExposure> itemList;

    public ProximityDetailsAdapter(Context context, List<ProximityExposure> itemList) {
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
    public void onBindViewHolder(@NonNull ProximityViewHolder holder, int position) {

        ProximityExposure proximityExposure = itemList.get(position);

        String risk_level = "Low";

        /** If distance is less than 100 meters risk is high */
        if(proximityExposure.getDistance()<100) {
            holder.li_image.setImageResource(R.drawable.ic_radar_yellow);
            risk_level = "High";
        }
        else
            holder.li_image.setImageResource(R.drawable.ic_radar_red);

        String risk_level_string = "Risk Level: " + risk_level;
        holder.li_count.setText(risk_level);

        holder.li_address.setText(proximityExposure.getAddress());

        /** Convert string to long */
        Long unixSeconds = Long.parseLong(proximityExposure.getDateTime());
        Date date = new java.util.Date(unixSeconds*1000L);

        String fd = formatDate(date);
        String ft = formatTime(date);
        String dt = fd + " - " + ft ;

        holder.li_date_time.setText(dt);

        /** Set radius in meters */
        String li_radius_string = proximityExposure.getDistance() + " m radius" ;
        holder.li_radius.setText(li_radius_string);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /** Helper method to get formatted date*/
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /** Helper method to get formatted time*/
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

}