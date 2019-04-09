package com.montek.monsite.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.montek.monsite.Fragments.RequirmentList_Fragment;
import com.montek.monsite.JsonConnection.RequestHandler;
import com.montek.monsite.R;
import com.montek.monsite.extra.URLs;
import com.montek.monsite.model.OpeningsPost;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.montek.monsite.ActivitySplash.p;
import static com.montek.monsite.Activity_Login.ProgressD;
import static com.montek.monsite.DashBordActivity.frag;
public class ListAdapterRequirmentlist extends ArrayAdapter<OpeningsPost> {
    public ArrayList<OpeningsPost> MainList;
    private Context mcon;
    String id;
    public ArrayList<OpeningsPost> OpeningsListTemp;
    public ListAdapterRequirmentlist.OpeningsDataFilter openingsDataFilter ;
    public ListAdapterRequirmentlist(Context context, int id, ArrayList<OpeningsPost> OpeningsArrayList) {
        super(context, id, OpeningsArrayList);
        this.mcon = context;
        this.OpeningsListTemp = new ArrayList<OpeningsPost>();
        this.OpeningsListTemp.addAll(OpeningsArrayList);
        this.MainList = new ArrayList<OpeningsPost>();
        this.MainList.addAll(OpeningsArrayList);
    }
    @NonNull
    @Override
    public Filter getFilter() {
        if (openingsDataFilter == null){
            openingsDataFilter  = new ListAdapterRequirmentlist.OpeningsDataFilter();
        }
        return openingsDataFilter;
    }
    public class ViewHolder {
        TextView txv_status,txv_doh,companyname,posted,titleofrequirment,experience,location,skill,duration;
        RatingBar ratingbar;
        Button btn_delete,btn_edit;
    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        ListAdapterRequirmentlist.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.activity_openingelement, null);
            holder = new ListAdapterRequirmentlist.ViewHolder();
            p = new ProgressDialog(getContext());
            holder.titleofrequirment = (TextView) convertView.findViewById(R.id.txv_titleofrequirment);
            holder.experience = (TextView) convertView.findViewById(R.id.txv_experience);
            holder.location = (TextView) convertView.findViewById(R.id.txv_location);
            holder.skill = (TextView) convertView.findViewById(R.id.txv_skill);
            holder.duration = (TextView) convertView.findViewById(R.id.txv_duration);
            holder.posted = (TextView) convertView.findViewById(R.id.txv_posted);
            holder.companyname = (TextView) convertView.findViewById(R.id.txv_companyname);
            holder.btn_delete = (Button) convertView.findViewById(R.id.btn_delete);
            holder.ratingbar = (RatingBar) convertView.findViewById(R.id.ratingbar);
            holder.btn_edit = (Button) convertView.findViewById(R.id.btn_edit);
            holder.txv_doh = (TextView) convertView.findViewById(R.id.txv_doh);
            holder.txv_status = (TextView) convertView.findViewById(R.id.txv_status);
            holder.btn_delete.setVisibility(View.VISIBLE);
            convertView.setTag(holder);
        } else {
            holder = (ListAdapterRequirmentlist.ViewHolder) convertView.getTag();
        }
        final OpeningsPost openings = OpeningsListTemp.get(position);
        holder.titleofrequirment.setText(openings.getTitle_Of_Requirment());
        holder.experience.setText(openings.getexperience());
        holder.location.setText(openings.getlocation());
        holder.skill.setText(openings.getskill());
        holder.duration.setText(openings.getduration());
        holder.txv_doh.setText(openings.getdateofhiring());
        Drawable drawable =  holder.ratingbar.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#83AAE5"), PorterDuff.Mode.SRC_ATOP);
        holder.ratingbar.setRating(Float.parseFloat(openings.getskillrating()));
        holder.companyname.setText(openings.getcompanyName());
        holder.posted.setText(openings.getCurrentTimeStamp());
        if(openings.getfreelancer_id().equalsIgnoreCase("1")) {
            holder.txv_status.setText("Approved");
            holder.btn_delete.setVisibility(View.GONE);
        }
        else if(openings.getfreelancer_id().equalsIgnoreCase("0"))
        {
            holder.txv_status.setText("Pending");
            holder.btn_delete.setVisibility(View.VISIBLE);
        }
        else if(openings.getfreelancer_id().equalsIgnoreCase("2"))
        {
            holder.txv_status.setText("Cancel");
            holder.btn_delete.setVisibility(View.GONE);
        }

            if(openings.getfreelancer_id().equalsIgnoreCase("1")) {
                holder.txv_status.setText("Approved");
                holder.btn_delete.setVisibility(View.GONE);
            }
            else if(openings.getfreelancer_id().equalsIgnoreCase("0"))
            {
                holder.txv_status.setText("Pending");
                holder.btn_delete.setVisibility(View.VISIBLE);

                if(openings.getfreelancer_email().equalsIgnoreCase("0"))
                {
                    holder.btn_delete.setVisibility(View.VISIBLE);
                }
                else  if(openings.getfreelancer_email().equalsIgnoreCase("1"))
                {
                    holder.txv_status.setText("post Cancelled by you");
                    holder.btn_delete.setVisibility(View.GONE);
                }
            }
            else if(openings.getfreelancer_id().equalsIgnoreCase("2"))
            {
                holder.txv_status.setText("Cancel");
                holder.btn_delete.setVisibility(View.GONE);
            }


        holder.btn_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setMessage("Do you want to cancel this requirment?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user pressed "yes", then he is allowed to exit from application
                        // remove the item from the data list
                        id=OpeningsListTemp.get(position).getid();
                        new DeletePost().execute();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user select "No", just cancel this dialog and continue with app
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  list.remove(position); // remove the item from the data list
                Log.d("id",OpeningsListTemp.get(position).getid());
                Toast.makeText(getContext(),OpeningsListTemp.get(position).getid(),Toast.LENGTH_LONG).show();
                // notifyDataSetChanged();
                id=OpeningsListTemp.get(position).getid();
//                Intent i=new Intent(mcon,EditSendRequirment.class);
//                i.putExtra( "sendrequirment_id", id);
//                i.putExtra( "Title_Of_Requirment", openings.getTitle_Of_Requirment() );
//                i.putExtra( "skill", openings.getskill() );
//                i.putExtra( "experience", openings.getexperience() );
//                i.putExtra( "location", openings.getlocation() );
//                i.putExtra( "skillrating", openings.getskillrating() );
//                i.putExtra( "duration", openings.getduration() );
//                i.putExtra( "Education_Required", openings.getEducation_Required() );
//                i.putExtra( "Description", openings.getDescription() );
//                i.putExtra( "companyName", openings.getcompanyName() );
//                i.putExtra( "AboutCompany", openings.getAboutCompany() );
//                i.putExtra( "contactpersonname", openings.getcontactpersonname() );
//                i.putExtra( "email", openings.getemail() );
//                i.putExtra( "contact",openings.getcontact());
//                i.putExtra( "CurrentTimeStamp",openings.getCurrentTimeStamp());
//                i.putExtra( "employer_id",openings.getemployer_id());
//                mcon.getApplicationContext().startActivity(i);
            }
        });
        return convertView;
    }
    private class OpeningsDataFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            charSequence = charSequence.toString().toLowerCase();
            FilterResults filterResults = new FilterResults();
            if(charSequence.toString().length() > 0)
            {
                ArrayList<OpeningsPost> arrayList1 = new ArrayList<OpeningsPost>();
                for(int i = 0, l = MainList.size(); i < l; i++)
                {
                    OpeningsPost openings = MainList.get(i);
                    if(openings.toString().toLowerCase().contains(charSequence))
                        arrayList1.add(openings);
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
            OpeningsListTemp = (ArrayList<OpeningsPost>)filterResults.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = OpeningsListTemp.size(); i < l; i++)
                add(OpeningsListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }
    class DeletePost extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("id", id);
            Log.d("param updated data:",params.toString());
            return requestHandler.sendPostRequest(URLs.URL_DeleteRequirmentPost, params);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressD();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            p.dismiss();
            try {
                Log.d("Json response:",s);
                Log.d("id",s);
                JSONObject obj = new JSONObject(s);
                if (!obj.getBoolean("error")) {
                    Toast.makeText(getContext(),obj.getString("message"), Toast.LENGTH_LONG).show();
                    frag = new RequirmentList_Fragment();
                    AppCompatActivity activity = (AppCompatActivity) getContext();
                    if (frag != null) {
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame, frag).commit();
                    }
                } else {
                    Toast.makeText(getContext(), "error", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                e.getMessage();
            }
        }
    }
}