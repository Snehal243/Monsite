package com.montek.monsite.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.montek.monsite.Adapter.ListAdapterEmployerSearch;
import com.montek.monsite.DashBordActivity;
import com.montek.monsite.FLprofileOnEmployerDashbord;
import com.montek.monsite.JsonConnection.RequestHandler;
import com.montek.monsite.R;
import com.montek.monsite.extra.GlobalVariable;
import com.montek.monsite.extra.URLs;
import com.montek.monsite.model.Employer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.montek.monsite.ActivitySplash.p;
import static com.montek.monsite.Activity_Login.ProgressD;
import static com.montek.monsite.Employer_EditProfile.jsonArray;
public class Employer_HiredCandidatelistFragment extends Fragment  {
    View v;
    ListView listView;
     ListAdapterEmployerSearch listAdapter;
    LinearLayout no_item;
    String freelancer_id;
    String  employer_id;
    ArrayList<Employer> EmployerList = new ArrayList<Employer>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.listview, container, false);
       try {
           ((DashBordActivity) getActivity()).setTitle("Hired CandidatesList");
           Employer_SearchFragment.hiredlist = "1";
           EmployerList.clear();
           iUI();
           p = new ProgressDialog(getContext());
           listView.setTextFilterEnabled(true);
           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   Employer ListViewClickData = (Employer) parent.getItemAtPosition(position);
                   freelancer_id = ListViewClickData.getfreelancer_id();
                   Intent i = new Intent(getContext(), FLprofileOnEmployerDashbord.class);
                   i.putExtra("freelancer_id", ListViewClickData.getfreelancer_id());
                   i.putExtra("freelancer_username", ListViewClickData.getusername());
                   i.putExtra("freelancer_email", ListViewClickData.getemail());
                   i.putExtra("freelancer_contact", ListViewClickData.getcontact());
                   i.putExtra("freelancer_summary", ListViewClickData.getsummary());
                   i.putExtra("freelancer_skill", ListViewClickData.getskill());
                   i.putExtra("freelancer_industryexp", ListViewClickData.getindustryexp());
                   i.putExtra("freelancer_domainexp", ListViewClickData.getdomainexp());
                   i.putExtra("freelancer_education", ListViewClickData.geteducation());
                   i.putExtra("freelancer_experience", ListViewClickData.getexperience());
                   i.putExtra("freelancer_image", ListViewClickData.getimage());
                   i.putExtra("freelancer_status", ListViewClickData.getstatus());
                   i.putExtra("freelancer_location", ListViewClickData.getlocation());
                   i.putExtra("freelancer_Categoires", ListViewClickData.getfreelancer_Categoires());
                   i.putExtra("freelancer_SubCategoires", ListViewClickData.getfreelancer_SubCategoires());
                   i.putExtra("gender", ListViewClickData.getgender());
                   i.putExtra("freelancer_address", ListViewClickData.getfreelancer_address());
                   i.putExtra("System_date", ListViewClickData.getSystem_date());
                   i.putExtra("employer_id", ListViewClickData.getemployer_id());
                   i.putExtra("employer_email", ListViewClickData.getemployer_email());
                   i.putExtra("employer_hirefreelancer", ListViewClickData.getemployer_hirefreelancer());
                   i.putExtra("freelancer_rating", ListViewClickData.getfreelancer_rating());
                   i.putExtra("status_reason", ListViewClickData.getStatus_reason());
                   i.putExtra("rateperduration", ListViewClickData.getrateperduration());
                   i.putExtra("onwhichdurationhired", ListViewClickData.getonwhichdurationhired());
                   i.putExtra("totalduration", ListViewClickData.gettotalduration());
                   i.putExtra("totalpaidrate", ListViewClickData.gettotalpaidrate());
                   i.putExtra("startdate", ListViewClickData.getstartdate());
                  // i.putExtra("enddate", ListViewClickData.getenddate());
                   i.putExtra("rating", ListViewClickData.getclientrating());
                   i.putExtra("employerviewedfreelancerid", ListViewClickData.getemployerviewedfreelancerid());
                   i.putExtra("evfb_id", ListViewClickData.getevfb_id());
                   i.putExtra("certification", ListViewClickData.getcertification());
                   i.putExtra("freelancer_designation", ListViewClickData.getfreelancer_designation());
                   i.putExtra("gstapplied", ListViewClickData.getgstapplied());
                   startActivity(i);
                   getActivity().finish();
               }
           });
           new RetriveFreelancerlist().execute();
       }catch(Exception e){}
        return v;
    }
    private void iUI() throws Exception{
        no_item = (LinearLayout)v. findViewById(R.id.no_item);
        no_item.setVisibility(View.GONE);
        listView = (ListView)v. findViewById(R.id.listView1);
    }
    class RetriveFreelancerlist extends AsyncTask<Void, Void, String> {
      @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("email", GlobalVariable.Employer_email);
            return requestHandler.sendPostRequest(URLs.URL_RetriveListofFreelancer + "EmptySelection", params);
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
            Log.d("ssssss",s);
            if (s.equalsIgnoreCase(" \"No Results Found\"")) {
                Log.d("error", s);
                listView.setAdapter(null);
                no_item.setVisibility(View.VISIBLE);
            } else {
                no_item.setVisibility(View.GONE);
                try {
                    jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    Employer employer;
                    EmployerList = new ArrayList<Employer>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("freelancer_id");
                        String freelancer_username = jsonObject.getString("freelancer_username");
                        String freelancer_email = jsonObject.getString("freelancer_email");
                        String freelancer_contact = jsonObject.getString("freelancer_contact");
                        String freelancer_summary = jsonObject.getString("freelancer_summary");
                        String freelancer_skill = jsonObject.getString("freelancer_skill");
                        String freelancer_industryexp = jsonObject.getString("freelancer_industryexp");
                        String freelancer_domainexp = jsonObject.getString("freelancer_domainexp");
                        String freelancer_education = jsonObject.getString("freelancer_education");
                        String freelancer_experience = jsonObject.getString("freelancer_experience");
                        String freelancer_image = "http://monsite.montekservices.com/"+jsonObject.getString("freelancer_image");
                        String freelancer_status = jsonObject.getString("freelancer_status");
                        String status_reason = jsonObject.getString("status_reason");
                        String freelancer_location = jsonObject.getString("freelancer_location");
                        String freelancer_Categoires = jsonObject.getString("freelancer_Categoires");
                        String freelancer_SubCategoires = jsonObject.getString("freelancer_SubCategoires");
                        String freelancer_rating = jsonObject.getString("freelancer_rating");
                        String DOB = jsonObject.getString("DOB");
                        String freelancer_designation = jsonObject.getString("freelancer_designation");
                        String gender = jsonObject.getString("gender");
                        String certification = jsonObject.getString("certification");
                        String System_date = jsonObject.getString("System_date");
                        String freelancer_address = jsonObject.getString("freelancer_address");
                        employer_id = jsonObject.getString("employer_id");
                        String employer_email = jsonObject.getString("employer_email");
                        String employer_hirefreelancer = jsonObject.getString("freelancer_id");
                        String onwhichdurationhired = jsonObject.getString("onwhichdurationhired");
                        String rateperduration = jsonObject.getString("rateperduration");
                        String totalpaidrate = jsonObject.getString("totalpaidrate");
                        String totalduration = jsonObject.getString("totalduration");
                        String startdate = jsonObject.getString("bookeddates");
                        String clientrating = jsonObject.getString("rating");
                        String employerviewedfreelancerid = jsonObject.getString("evf_id");
                        String evfb_id = jsonObject.getString("evfb_id");
                        String paymentId = jsonObject.getString("paymentId");
                        String gstapplied = jsonObject.getString("gstapplied");
                        String approvestatus = jsonObject.getString("approvestatus");
                        String paymentstatus  = jsonObject.getString("paymentstatus");
                        employer = new Employer(id, freelancer_username, freelancer_email, freelancer_contact, freelancer_summary, freelancer_skill, freelancer_industryexp, freelancer_domainexp, freelancer_education, freelancer_experience, freelancer_image, freelancer_status, status_reason, freelancer_location, freelancer_Categoires, freelancer_SubCategoires, freelancer_rating, gender, System_date, freelancer_address, employer_id, employer_email, employer_hirefreelancer, onwhichdurationhired, rateperduration, totalpaidrate, totalduration, startdate, clientrating, employerviewedfreelancerid,evfb_id,paymentId,approvestatus,paymentstatus,freelancer_designation,certification,gstapplied);
                        EmployerList.add(employer);
                    }
                    listAdapter = new ListAdapterEmployerSearch(getContext(), R.layout.customlayout_freelancerlist, EmployerList);
                    listView.setAdapter(listAdapter);
                } catch (Exception e) {e.getMessage();}
            }
        }
    }
}
