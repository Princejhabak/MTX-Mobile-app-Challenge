package com.example.android.surveyapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.surveyapp.R;

public class ProximityViewHolder extends RecyclerView.ViewHolder {

    ImageView li_image;
    TextView li_count, li_address, li_date_time, li_radius;

    public ProximityViewHolder(@NonNull View itemView) {
        super(itemView);

        li_count = itemView.findViewById(R.id.proximity_exposure_li_no_of_exposures);
        li_address = itemView.findViewById(R.id.proximity_exposure_li_address);
        li_date_time = itemView.findViewById(R.id.proximity_exposure_li_date_time);
        li_radius = itemView.findViewById(R.id.proximity_exposure_li_radius);
        li_image = itemView.findViewById(R.id.proximity_exposure_li_image_view);

    }


}
