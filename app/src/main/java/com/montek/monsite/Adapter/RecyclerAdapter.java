package com.montek.monsite.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.montek.monsite.DisplayList_Freelancer;
import com.montek.monsite.Fragments.Employer_SearchFragment;
import com.montek.monsite.R;
import com.montek.monsite.interfaces.ItemClickListener;
import com.montek.monsite.model.Categoires;

import java.util.ArrayList;

import static com.montek.monsite.ActivityFilterEmployer.freelancer_statusselected;
import static com.montek.monsite.Adapter.ListAdapterFilterfreelancerSearch.FilterListstore;
import static com.montek.monsite.Adapter.ListAdapterFilterfreelancerSearch.finalvalues;
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<Categoires> item;
    private Context context;
    private ItemClickListener clickListener;
    public RecyclerAdapter(Context context, ArrayList<Categoires> item) {
        this.item = item;
        this.context = context;
    }
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
             view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_view_category, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, final int i) {
            viewHolder.tv_name1.setText(item.get(i).getcategoires());
            Glide.with(context).load(item.get(i).getcategoires_image())
                    .thumbnail(0.5f)
                    .crossFade()
                    .skipMemoryCache(true)
                    .into(viewHolder.img1);
            viewHolder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View v, int position, boolean isLongClick) {
                    Employer_SearchFragment.jobsearch="0";
                    Employer_SearchFragment.freelancer_experience="";
                    freelancer_statusselected="";
                    Intent i1=new Intent(v.getContext(),DisplayList_Freelancer.class);
                    v.getContext().startActivity(i1);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.finish();
                    Employer_SearchFragment.categoires_id=item.get(i).getid();
                    Employer_SearchFragment.categoires=item.get(i).getcategoires();
                    if(!finalvalues.isEmpty()) {
                        finalvalues.clear();
                        FilterListstore.clear();
                    }
                }
            });
    }
    @Override
    public int getItemCount() {
        return item.size();
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView tv_name1;
        private ImageView img1;
        private ItemClickListener clickListener;
        public ViewHolder(View view) {
            super(view);
            tv_name1 = (TextView)view.findViewById(R.id.textviewName);
            img1 = (ImageView) view.findViewById(R.id.imageView);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }
        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getPosition(), false);
        }
        @Override
        public boolean onLongClick(View v) {
            clickListener.onClick(v, getPosition(), true);
            return true;
        }
    }
}