package com.montek.monsite.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.montek.monsite.Adapter.ListAdapterRequirmentlist;
import com.montek.monsite.JsonConnection.RequestHandler;
import com.montek.monsite.R;
import com.montek.monsite.extra.GlobalVariable;
import com.montek.monsite.extra.URLs;
import com.montek.monsite.model.OpeningsPost;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.montek.monsite.ActivitySplash.p;
import static com.montek.monsite.Activity_Login.ProgressD;
import static com.montek.monsite.Employer_EditProfile.jsonArray;
public class RequirmentList_Fragment extends Fragment  {
    ListView listView;
    LinearLayout no_item;
    ListAdapterRequirmentlist listAdapter;
    ArrayList<OpeningsPost> OpeningsList = new ArrayList<OpeningsPost>();
    String dateofhiring,contact,CurrentTimeStamp,email,contactpersonname,AboutCompany,companyName,Title_Of_Requirment,skill,experience,location,skillrating,duration,Education_Required,Description;
    int employer_id;
        View v;
@Nullable
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.listview, container, false);
       try{ iUI();
       p=new ProgressDialog(getContext());
       listView.setTextFilterEnabled(true);
       new RetriveOpenings().execute();}catch (Exception e){}
      return v;
   }
    private void iUI() throws Exception{
        listView = (ListView)v.findViewById(R.id.listView1);
        no_item = (LinearLayout)v.findViewById(R.id.no_item);
        no_item.setVisibility(View.GONE);
    }
    class RetriveOpenings extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("email_id", GlobalVariable.Employer_email);
            return requestHandler.sendPostRequest(URLs.URL_RetriveSendRequirment, params);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          ProgressD();
        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            p.dismiss();
            Log.d("rsult", s);
            if (s.contains("No Results Found")) {
                listView.setAdapter(null);
                no_item.setVisibility(View.VISIBLE);
            } else {
                no_item.setVisibility(View.GONE);
                 jsonArray = null;
                try {
                    jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    OpeningsPost openings;
                    OpeningsList = new ArrayList<OpeningsPost>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String sendrequirment_id = jsonObject.getString("sendrequirment_id");
                        Title_Of_Requirment = jsonObject.getString("Title_Of_Requirment");
                        skill = jsonObject.getString("skill");
                        experience = jsonObject.getString("experience");
                        location = jsonObject.getString("location");
                        skillrating = jsonObject.getString("skillrating");
                        duration = jsonObject.getString("duration");
                        Education_Required = jsonObject.getString("Education_Required");
                        Description = jsonObject.getString("Description");
                        companyName = jsonObject.getString("employer_companyname");
                        AboutCompany = jsonObject.getString("employersummary");
                        contactpersonname = jsonObject.getString("employer_cpersonsname");
                        email = jsonObject.getString("employer_email");
                        contact = jsonObject.getString("employer_contact");
                        CurrentTimeStamp = jsonObject.getString("System_date");
                        employer_id = jsonObject.getInt("employer_id");
                        dateofhiring = jsonObject.getString("dateofhiring");
                        String Approve_Post = jsonObject.getString("Approve_Post");
                        String userstatus = jsonObject.getString("userstatus");
                        openings = new OpeningsPost(sendrequirment_id, Title_Of_Requirment, skill, experience,dateofhiring, location, skillrating, duration, Education_Required, Description, companyName, AboutCompany, contactpersonname, email, contact, CurrentTimeStamp, employer_id, Approve_Post,userstatus, "");
                        OpeningsList.add(openings);
                    }
                    listAdapter = new ListAdapterRequirmentlist(getContext(), R.layout.activity_openingelement, OpeningsList);
                    listView.setAdapter(listAdapter);

                } catch (Exception e) { e.getMessage();}
            }
        }
    }
}