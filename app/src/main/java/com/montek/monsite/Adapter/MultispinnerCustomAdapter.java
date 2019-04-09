package com.montek.monsite.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.montek.monsite.R;
import com.montek.monsite.model.FilterFreelancer;

import java.util.ArrayList;
public class MultispinnerCustomAdapter extends ArrayAdapter<FilterFreelancer> {
    public ArrayList<FilterFreelancer> countryList;
    public MultispinnerCustomAdapter(Context context, int textViewResourceId,
                                     ArrayList<FilterFreelancer> countryList) {
        super(context, textViewResourceId, countryList);
        this.countryList = new ArrayList<FilterFreelancer>();
        this.countryList.addAll(countryList);
    }
    private class ViewHolder {
        TextView name;
        CheckBox chb;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        Log.v("ConvertView", String.valueOf(position));
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.activity_sortingelement, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.txv_title);
            holder.chb = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
            holder.chb.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    FilterFreelancer country = (FilterFreelancer) cb.getTag();
                    if(cb.isChecked())
                    {
                        country.setSelected(cb.isChecked());
                    }
                    else
                    {
                        country.setSelected(false);
                    }
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        FilterFreelancer country = countryList.get(position);
        holder.name.setText(country.getcategoires());
        holder.chb.setChecked(country.getSelected());
        holder.chb.setTag(country);
        return convertView;
    }
}
