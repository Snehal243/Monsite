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
import com.montek.monsite.Adapter.FeedbacklistAdapter;
import com.montek.monsite.Adapter.ListAdapterEmployerSearch;
import com.montek.monsite.JsonConnection.RequestHandler;
import com.montek.monsite.R;
import com.montek.monsite.extra.GlobalVariable;
import com.montek.monsite.extra.URLs;
import com.montek.monsite.model.FilterFreelancer;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import static com.montek.monsite.ActivitySplash.p;
import static com.montek.monsite.Activity_Login.ProgressD;
import static com.montek.monsite.Employer_EditProfile.jsonArray;
public class Employer_ConferenceCall extends Fragment {
    ListView listView;
    LinearLayout no_item;
    FeedbacklistAdapter listAdapter;
    ArrayList<FilterFreelancer> List = new ArrayList<FilterFreelancer>();
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.listview, container, false);
        try{ iUI();
            p=new ProgressDialog(getContext());
            listView.setTextFilterEnabled(true);
            new RetriveConferenceCall().execute();
        }catch (Exception e){}
        return v;
    }
    private void iUI() throws Exception{
        listView = (ListView)v.findViewById(R.id.listView1);
        no_item = (LinearLayout)v.findViewById(R.id.no_item);
        no_item.setVisibility(View.GONE);
    }
    class RetriveConferenceCall extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("employer_id", GlobalVariable.Employer_id);
            return requestHandler.sendPostRequest(URLs.URL_Retrivealltablevalue+"retrive_conference_call", params);
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
            Log.d("rsult", s.toString());
            if (s.contains("No Results Found")) {
                listView.setAdapter(null);
                no_item.setVisibility(View.VISIBLE);
            } else {
                no_item.setVisibility(View.GONE);
                jsonArray = null;
                try {
                    jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String freelancer_username  = jsonObject.getString("freelancer_username");
                        String status = jsonObject.getString("status");
                        String datetime  = jsonObject.getString("datetime");
                        String freelancer_skill = jsonObject.getString("freelancer_skill");
                        String freelancer_location  = jsonObject.getString("freelancer_location");
                        String freelancer_education = jsonObject.getString("freelancer_education");
                        String freelancer_Categoires = jsonObject.getString("freelancer_Categoires");
                        String freelancer_rating = jsonObject.getString("freelancer_rating");
                        String rating = jsonObject.getString("rating");
                        List.add(new FilterFreelancer(freelancer_username, status,datetime,freelancer_skill,freelancer_location,freelancer_education,freelancer_Categoires,freelancer_rating,rating));
                    }
                    ListAdapterEmployerSearch.feedbacklist=0;
                    listAdapter = new FeedbacklistAdapter(getContext(), R.layout.activity_detailsofratingclient, List);
                    listView.setAdapter(listAdapter);
                } catch (Exception e) { e.getMessage();}
            }
        }
    }
}
