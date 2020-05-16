package com.example.android.surveyapp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.surveyapp.R;

class NotificationViewHolder extends RecyclerView.ViewHolder {

    TextView li_heading, li_body, li_date_time;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);

        li_heading = itemView.findViewById(R.id.notification_li_heading);
        li_body = itemView.findViewById(R.id.notification_li_body);
        li_date_time = itemView.findViewById(R.id.notification_li_date_time);

    }


}
