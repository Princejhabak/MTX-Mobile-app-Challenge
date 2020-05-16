package com.example.android.surveyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.Survey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SurveyHistoryAdapter extends ArrayAdapter<Survey>{

    private List<Survey> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        ImageView imageViewMood;
        TextView textViewDate;
        TextView textViewTime;

    }

    public SurveyHistoryAdapter(List<Survey> data, Context context) {
        super(context, R.layout.contact_details_list_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Survey dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.survey_history_list_item, parent, false);

            viewHolder.imageViewMood = convertView.findViewById(R.id.survey_history_li_image);
            viewHolder.textViewDate = convertView.findViewById(R.id.survey_history_li_date);
            viewHolder.textViewTime = convertView.findViewById(R.id.survey_history_li_time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        Long unixSeconds = Long.parseLong(dataModel.getDateTime());
        Date date = new java.util.Date(unixSeconds*1000L);

        String feeling = dataModel.getFeeling();

        String fd = formatDate(date);
        String ft = formatTime(date);

        if(feeling.equals("Well"))
            viewHolder.imageViewMood.setImageResource(R.drawable.ic_smile);
        else
            viewHolder.imageViewMood.setImageResource(R.drawable.ic_sad);

        viewHolder.textViewDate.setText(fd);
        viewHolder.textViewTime.setText(ft);


        return convertView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

}

