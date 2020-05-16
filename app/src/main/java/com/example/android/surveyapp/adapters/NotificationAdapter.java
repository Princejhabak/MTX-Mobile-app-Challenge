package com.example.android.surveyapp.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.Notification;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    Context context;
    List<Notification> itemList;

    public NotificationAdapter(Context context, List<Notification> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {

        /** If notification isRead then remove bold style*/
        if(itemList.get(position).isNotificationRead()){
            holder.li_heading.setTypeface(null, Typeface.NORMAL);
            holder.li_body.setTypeface(null, Typeface.NORMAL);
            holder.li_date_time.setTypeface(null, Typeface.NORMAL);
        }

        /** Parse string to long*/
        Long unixSeconds = Long.parseLong(itemList.get(position).getNotificationDateTime());
        Date date = new java.util.Date(unixSeconds*1000L);

        String fd = formatDate(date);
        String ft = formatTime(date);
        String dt = fd + " - " + ft;


        /** Set text in holder list items*/
        holder.li_heading.setText(itemList.get(position).getNotificationHeading());
        holder.li_body.setText(itemList.get(position).getNotificationBody());
        holder.li_date_time.setText(dt);


    }

    /** return size of NotificationsList*/
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
