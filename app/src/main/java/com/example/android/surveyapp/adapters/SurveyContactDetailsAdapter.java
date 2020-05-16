package com.example.android.surveyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.SurveyContactDetails;

import java.util.List;

public class SurveyContactDetailsAdapter extends ArrayAdapter<SurveyContactDetails>{

    private List<SurveyContactDetails> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView nameView;
        TextView detailView;
        TextView addressView;
        TextView phoneView;
    }

    public SurveyContactDetailsAdapter(List<SurveyContactDetails> data, Context context) {
        super(context, R.layout.contact_details_list_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        SurveyContactDetails dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.contact_details_list_item, parent, false);

            viewHolder.nameView = convertView.findViewById(R.id.survey_contact_details_li_name);
            viewHolder.detailView = convertView.findViewById(R.id.survey_contact_details_li_details);
            viewHolder.addressView = convertView.findViewById(R.id.survey_contact_details_li_address);
            viewHolder.phoneView = convertView.findViewById(R.id.survey_contact_details_li_phone);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        String nameViewText = dataModel.getName() + " (" + dataModel.getRelationship() + ")";
        String detailViewText = dataModel.getDetailGender() + ", " + dataModel.getDetailAge() + " Years";
        String addressViewText = dataModel.getAddressLine1() + ", " + dataModel.getAddressLine2();

        viewHolder.nameView.setText(nameViewText);
        viewHolder.detailView.setText(detailViewText);
        viewHolder.addressView.setText(addressViewText);
        viewHolder.phoneView.setText(dataModel.getPhoneNo());

        return convertView;
    }
}
