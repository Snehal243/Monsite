package com.montek.monsite.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;

import com.montek.monsite.R;
import com.montek.monsite.extra.GlobalVariable;
import com.montek.monsite.model.FilterFreelancer;
import com.montek.monsite.model.TempClassForFilterfreelancer;

import java.util.ArrayList;
public class ListAdapterFilterfreelancerSearch extends ArrayAdapter<FilterFreelancer> {
    public static  ArrayList FilterListstore=new ArrayList();
    public ArrayList<FilterFreelancer> MainList;
    public ArrayList<FilterFreelancer> FilterListTemp;
    public ListAdapterFilterfreelancerSearch.FilterDataFilter filterDataFilter ;
      public static ArrayList<TempClassForFilterfreelancer> finalvalues = new ArrayList<TempClassForFilterfreelancer>();
    public static ArrayList<TempClassForFilterfreelancer> finalvaluesofjobsearch = new ArrayList<TempClassForFilterfreelancer>();
    public ListAdapterFilterfreelancerSearch(Context context, int id, ArrayList<FilterFreelancer> FilterArrayList) {
        super(context, id, FilterArrayList);
        this.FilterListTemp = new ArrayList<FilterFreelancer>();
        this.FilterListTemp.addAll(FilterArrayList);
        this.MainList = new ArrayList<FilterFreelancer>();
        this.MainList.addAll(FilterArrayList);
    }
    @NonNull
    @Override
    public Filter getFilter() {
        if (filterDataFilter == null){
            filterDataFilter  = new ListAdapterFilterfreelancerSearch.FilterDataFilter();
        }
        return filterDataFilter;
    }
    public class ViewHolder {
        TextView Name;
      CheckBox checkbox ;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ListAdapterFilterfreelancerSearch.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.activity_sortingelement, null);
            holder = new ListAdapterFilterfreelancerSearch.ViewHolder();
            holder.Name = (TextView) convertView.findViewById(R.id.txv_title);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ListAdapterFilterfreelancerSearch.ViewHolder) convertView.getTag();
        }
        //((holder) convertView.getTag()).checkbox.setTag(FilterListTemp.get(position));
        final FilterFreelancer categoires = FilterListTemp.get(position);
        holder.Name.setText(categoires.getcategoires());
        holder.checkbox.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v ;
                FilterFreelancer FilterFreelancer = (com.montek.monsite.model.FilterFreelancer) cb.getTag();
                FilterFreelancer.setSelected(cb.isChecked());
               // Toast.makeText(getContext(),"Clicked on Checkbox: " + cb.getText() +" is " + cb.isChecked(),Toast.LENGTH_LONG).show();
                if(cb.isChecked()){
                    FilterListstore.add(categoires.getcategoires());
                    finalvalues.add(new TempClassForFilterfreelancer(GlobalVariable.sortelement, categoires.getcategoires()));
                }
                else
                {
                    FilterListstore.remove(categoires.getcategoires());
                    for(int j = 0; j < finalvalues.size(); j++)
                    {
                        TempClassForFilterfreelancer obj = finalvalues.get(j);
                        if(obj.value.equals(categoires.getcategoires())){
                            //found, delete.
                            finalvalues.remove(j);
                            break;
                        }
                    }
                }
            }
        });
        holder.checkbox.setChecked(categoires.getSelected());
        holder.checkbox.setTag(categoires);
        return convertView;
    }
    private class FilterDataFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            charSequence = charSequence.toString().toLowerCase();
            FilterResults filterResults = new FilterResults();
            if(charSequence.toString().length() > 0)
            {
                ArrayList<FilterFreelancer> arrayList1 = new ArrayList<FilterFreelancer>();
                for(int i = 0, l = MainList.size(); i < l; i++)
                {
                    FilterFreelancer subject = MainList.get(i);
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
            FilterListTemp = (ArrayList<FilterFreelancer>)filterResults.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = FilterListTemp.size(); i < l; i++)
                add(FilterListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }
}