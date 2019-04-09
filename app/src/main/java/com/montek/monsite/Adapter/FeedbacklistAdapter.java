package com.montek.monsite.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.montek.monsite.R;
import com.montek.monsite.model.FilterFreelancer;

import java.util.ArrayList;
public class FeedbacklistAdapter extends ArrayAdapter<FilterFreelancer> {
    public ArrayList<FilterFreelancer> countryList;
    public FeedbacklistAdapter(Context context, int textViewResourceId,
                               ArrayList<FilterFreelancer> countryList) {
        super(context, textViewResourceId, countryList);
        this.countryList = new ArrayList<FilterFreelancer>();
        this.countryList.addAll(countryList);
    }
    private class ViewHolder {
        TextView name,txv_date,txv_feddback,txv_freename,txv_education,txv_location,txv_categories,txv_skill,txv_status;
        RatingBar clientratingbar,clientratingbar1,ratingbar;
        LinearLayout li_rating;
        CardView CardView,CardView2;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        Log.v("ConvertView", String.valueOf(position));
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.activity_detailsofratingclient, null);
            holder = new ViewHolder();
            holder.CardView = (CardView) convertView.findViewById(R.id.CardView);
            holder.CardView2 = (CardView) convertView.findViewById(R.id.CardView2);
            holder.txv_freename = (TextView) convertView.findViewById(R.id.txv_freename);
            holder.txv_education = (TextView) convertView.findViewById(R.id.txv_education);
            holder.txv_location = (TextView) convertView.findViewById(R.id.txv_location);
            holder.txv_categories = (TextView) convertView.findViewById(R.id.txv_categories);
            holder.txv_skill = (TextView) convertView.findViewById(R.id.txv_skill);
            holder.txv_status = (TextView) convertView.findViewById(R.id.txv_status);
            holder.txv_date = (TextView) convertView.findViewById(R.id.txv_date);
            holder.name = (TextView) convertView.findViewById(R.id.txv_companyname);
            holder.clientratingbar = (RatingBar) convertView.findViewById(R.id.clientratingbar);
            holder.txv_feddback = (TextView) convertView.findViewById(R.id.txv_feddback);
            holder.li_rating = (LinearLayout) convertView.findViewById(R.id.li_rating);
            LayerDrawable stars = (LayerDrawable) holder.clientratingbar.getProgressDrawable();
            stars.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(2).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

            holder.clientratingbar1 = (RatingBar) convertView.findViewById(R.id.clientratingbar1);
            holder.ratingbar = (RatingBar) convertView.findViewById(R.id.ratingbar);
            LayerDrawable a1 = (LayerDrawable) holder.clientratingbar1.getProgressDrawable();
            a1.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            a1.getDrawable(2).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
            a1.getDrawable(1).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

            LayerDrawable a2 = (LayerDrawable) holder.ratingbar.getProgressDrawable();
            a2.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            a2.getDrawable(2).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
            a2.getDrawable(1).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        FilterFreelancer country = countryList.get(position);

        if(ListAdapterEmployerSearch.feedbacklist==1) {
            holder.CardView.setVisibility(View.VISIBLE);
            holder.CardView2.setVisibility(View.GONE);
            holder.name.setText(country.getfbcompanyname());
            holder.txv_feddback.setText(country.getfb());
            if (country.getrating().equalsIgnoreCase("null") || country.getrating().equalsIgnoreCase("") || country.getrating().equalsIgnoreCase("0")) {
                holder.li_rating.setVisibility(View.GONE);
            } else {
                holder.li_rating.setVisibility(View.VISIBLE);
                holder.clientratingbar.setRating(Float.parseFloat(country.getrating()));
            }
        }
        else
        {
            holder.CardView.setVisibility(View.GONE);
            holder.CardView2.setVisibility(View.VISIBLE);
            holder.txv_freename.setText(country.getfbcompanyname());
            holder.txv_education.setText(country.getfreelancer_education());
            holder.txv_skill.setText(country.getsortelementid());
            holder.txv_categories.setText(country.getcategoires());
            holder.txv_location.setText(country.getid());
            if(country.getfb().equalsIgnoreCase("1")) {
                holder.txv_status.setText("Approved, Within 24 hour's Admin Will contact you");
            }
            else  if(country.getfb().equalsIgnoreCase("0")) {
                holder.txv_status.setText("Pending");
            }
            else   if(country.getfb().equalsIgnoreCase("2"))
            {
                holder.txv_status.setText("Cancel");
            }
            String[] parts = country.getduration().split(" ");
            holder.txv_date.setText( parts[0]);
            holder.ratingbar.setRating(Float.parseFloat(country.getfreelancer_rating()));
            Log.d("hhh","hhh");
            if(country.getrating().equalsIgnoreCase("null") || country.getrating().equalsIgnoreCase("") || country.getrating().equalsIgnoreCase("0"))
            {
                holder.clientratingbar1.setVisibility(View.GONE);
                ((TextView)convertView.findViewById(R.id.txv_status11)).setVisibility(View.GONE);
            }
            else
            {
                holder.clientratingbar1.setVisibility(View.VISIBLE);
                ((TextView)convertView.findViewById(R.id.txv_status11)).setVisibility(View.VISIBLE);
                holder.clientratingbar1.setRating(Float.parseFloat(country.getrating()));
            }
        }
        return convertView;
    }
}
