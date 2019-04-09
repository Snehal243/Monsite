package com.montek.monsite.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.montek.monsite.R;
import com.montek.monsite.model.SortinglistFreelancer;

import java.util.ArrayList;
public class ListAdapterSortlistFreelancer extends ArrayAdapter<SortinglistFreelancer> {
    public ArrayList<SortinglistFreelancer> MainList;
    public ArrayList<SortinglistFreelancer> SortinglistFreelancerListTemp;
    public ListAdapterSortlistFreelancer.SortingListFreelancerDataFilter SortinglistFreelancerDataFilter ;
    public ListAdapterSortlistFreelancer(Context context, int id, ArrayList<SortinglistFreelancer> SortinglistFreelancerArrayList) {
        super(context, id, SortinglistFreelancerArrayList);
        this.SortinglistFreelancerListTemp = new ArrayList<SortinglistFreelancer>();
        this.SortinglistFreelancerListTemp.addAll(SortinglistFreelancerArrayList);
        this.MainList = new ArrayList<SortinglistFreelancer>();
        this.MainList.addAll(SortinglistFreelancerArrayList);
    }
    @NonNull
    @Override
    public Filter getFilter() {
        if (SortinglistFreelancerDataFilter == null){
            SortinglistFreelancerDataFilter  = new ListAdapterSortlistFreelancer.SortingListFreelancerDataFilter();
        }
        return SortinglistFreelancerDataFilter;
    }
    public class ViewHolder {
        TextView Name;
        ImageView image;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ListAdapterSortlistFreelancer.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView = vi.inflate(R.layout.activity_filtercategoryelementoffreelancer, null);
            holder = new ListAdapterSortlistFreelancer.ViewHolder();
            holder.Name = (TextView) convertView.findViewById(R.id.txv_sortby);
            holder.image = (ImageView) convertView.findViewById(R.id.img_sortby);
            convertView.setTag(holder);
        } else {
            holder = (ListAdapterSortlistFreelancer.ViewHolder) convertView.getTag();
        }
        //((holder) convertView.getTag()).checkbox.setTag(FilterListTemp.get(position));
        SortinglistFreelancer test = SortinglistFreelancerListTemp.get(position);
        holder.Name.setText(test.getsorting_title());
       // holder.image.setChecked(categoires.getimage());
        Glide.with(getContext()).load(test.getimage())
                .thumbnail(0.5f)
                .crossFade()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.image);
        return convertView;
    }
    private class SortingListFreelancerDataFilter extends Filter
    {
       @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            charSequence = charSequence.toString().toLowerCase();
            FilterResults filterResults = new FilterResults();
            if(charSequence.toString().length() > 0)
            {
                ArrayList<SortinglistFreelancer> arrayList1 = new ArrayList<SortinglistFreelancer>();
                for(int i = 0, l = MainList.size(); i < l; i++)
                {
                    SortinglistFreelancer subject = MainList.get(i);
                    if(subject.toString().toLowerCase().contains(charSequence))
                        arrayList1.add(subject);
                }
                filterResults.count = arrayList1.size();
                filterResults.values = arrayList1;
            }
            else
            {
                synchronized(this)
                {
                    filterResults.values = MainList;
                    filterResults.count = MainList.size();
                }
            }
            return filterResults;
        }
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            SortinglistFreelancerListTemp = (ArrayList<SortinglistFreelancer>)filterResults.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = SortinglistFreelancerListTemp.size(); i < l; i++)
                add(SortinglistFreelancerListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }
}