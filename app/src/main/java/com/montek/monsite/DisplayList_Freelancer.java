package com.montek.monsite;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.montek.monsite.Adapter.ListAdapterEmployerSearch;
import com.montek.monsite.Fragments.Employer_SearchFragment;
import com.montek.monsite.JsonConnection.RequestHandler;
import com.montek.monsite.extra.GlobalVariable;
import com.montek.monsite.extra.URLs;
import com.montek.monsite.model.Employer;
import com.montek.monsite.model.SpinnerClass;
import com.montek.monsite.model.TempClassForFilterfreelancer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.montek.monsite.ActivityFilterEmployer.filterenddate;
import static com.montek.monsite.ActivityFilterEmployer.filterstartdate;
import static com.montek.monsite.ActivityFilterEmployer.freelancer_statusselected;
import static com.montek.monsite.ActivitySplash.p;
import static com.montek.monsite.Activity_Login.ProgressD;
import static com.montek.monsite.Adapter.ListAdapterFilterfreelancerSearch.FilterListstore;
import static com.montek.monsite.Adapter.ListAdapterFilterfreelancerSearch.finalvalues;
import static com.montek.monsite.Adapter.ListAdapterFilterfreelancerSearch.finalvaluesofjobsearch;
import static com.montek.monsite.Fragments.Employer_SearchFragment.categoires;
import static com.montek.monsite.Fragments.Employer_SearchFragment.freelancer_experience;
import static com.montek.monsite.Fragments.Employer_SearchFragment.jobsearch;
import static com.montek.monsite.Fragments.Employer_SearchFragment.selecteditem;
public class DisplayList_Freelancer extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ListView listView;
    EditText editText;
    ArrayList<Employer> EmployerList = new ArrayList<Employer>();
    ListAdapterEmployerSearch listAdapter;
    Button bt_clear,btn_search;
    FrameLayout li_edtxsearch;
    LinearLayout li_search,filter,sort;
    Spinner spinner_subcategory;
    String employer_email,employer_id;
    public static String freelancer_id,subCategoires,subCategoiresid;
    LinearLayout no_item;
    JSONArray jsonArray = null;
    TextView txv_filtervalues;
    String filterappliedvalues="";
    ArrayList<String> values=new ArrayList<>();
    AlertDialog alertDialog1;
    String[] values1 = {"Available","Not Available","All"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);//  getSupportActionBar().hide();
        try {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            values.clear();
            GlobalVariable.sortelement = "education";
            setContentView(R.layout.freelancer_list);
            iUI();
            p = new ProgressDialog(this);
            Employer_SearchFragment.hiredlist = "0";
            listView.setTextFilterEnabled(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Employer ListViewClickData = (Employer) parent.getItemAtPosition(position);
                    freelancer_id = ListViewClickData.getfreelancer_id();
                    Intent i = new Intent(getBaseContext(), FLprofileOnEmployerDashbord.class);
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
                    i.putExtra("rating", ListViewClickData.getclientrating());
                    i.putExtra("certification", ListViewClickData.getcertification());
                    i.putExtra("freelancer_designation", ListViewClickData.getfreelancer_designation());
                    startActivity(i);
                }
            });
            editText.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                public void onTextChanged(CharSequence stringVar, int start, int before, int count) {
                    if (listAdapter == null) {
                        Toast.makeText(getBaseContext(), "No Item Found", Toast.LENGTH_SHORT).show();
                    } else {
                        listAdapter.getFilter().filter(stringVar.toString());
                    }
                }
            });
            new RetriveSubCategory().execute();
        }catch (Exception e){}
    }
    private void iUI() throws Exception{
        filter = (LinearLayout)findViewById(R.id.filter);
        sort = (LinearLayout)findViewById(R.id.sort);
        sort.setOnClickListener(this);
        filter.setOnClickListener(this);
       // CardView subcategorylayout = (CardView)findViewById(R.id.subcategorylayout);
        spinner_subcategory = (Spinner) findViewById(R.id.spn_search);
        spinner_subcategory.setOnItemSelectedListener(this);
//        if(jobsearch.equalsIgnoreCase("1")) {
//            subcategorylayout.setVisibility(View.GONE);
//        }
        li_search = (LinearLayout)findViewById(R.id.li_search);
        li_edtxsearch = (FrameLayout)findViewById(R.id.li_edtxsearch);
        no_item = (LinearLayout)findViewById(R.id.no_item);
        li_search.setVisibility(View.GONE);
        no_item.setVisibility(View.GONE);
        li_edtxsearch.setVisibility(View.GONE);
        bt_clear = (Button)findViewById(R.id.btn_clear);
        bt_clear.setOnClickListener(this);
        btn_search = (Button)findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        editText = (EditText)findViewById(R.id.edtx_search);
        txv_filtervalues = (TextView)findViewById(R.id.txv_filtervalues);
        txv_filtervalues.setVisibility(View.GONE);
        listView = (ListView)findViewById(R.id.listView);
    }
    @Override
    public void onClick(View v)   {
        switch (v.getId())
        {
            case R.id.filter:
                Intent i = new Intent(getBaseContext(), ActivityFilterEmployer.class);
                startActivity(i);
                break;
            case R.id.sort:
                Sortdialog();
                break;
            case R.id.btn_clear:
                editText.setText("");
                break;
            case R.id.btn_search:
                li_edtxsearch.setVisibility(View.VISIBLE);
                li_search.setVisibility(View.GONE);
                break;
        }
    }
    public void Sortdialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort By");
        builder.setSingleChoiceItems(values1,Employer_SearchFragment.selecteditem, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item)
                {
                    case 0:
                        selecteditem=0;
                        freelancer_statusselected="Available";
                        new RetriveFreelancerlist().execute();
                        break;
                    case 1:
                        selecteditem=1;
                        freelancer_statusselected="Not Available";
                        new RetriveFreelancerlist().execute();
                        break;
                    case 2:
                        selecteditem=2;
                        freelancer_statusselected="";
                        new RetriveFreelancerlist().execute();
                        break;
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(!finalvalues.isEmpty()) {
                    finalvalues.clear();
                    FilterListstore.clear();
                }
                DashBordActivity.fragment = new Employer_SearchFragment();
                Intent i=new Intent(getBaseContext(),DashBordActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        if(!finalvalues.isEmpty()) {
            finalvalues.clear();
            FilterListstore.clear();
        }
       DashBordActivity.fragment = new Employer_SearchFragment();
        Intent i=new Intent(getBaseContext(),DashBordActivity.class);
        startActivity(i);
        finish();
    }
    class RetriveFreelancerlist extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            if(jobsearch.equalsIgnoreCase("1")) {
                finalvalues.addAll(finalvaluesofjobsearch);
            }
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("email", GlobalVariable.Employer_email);
            params.put("freelancer_SubCategoires",subCategoires);
            params.put("freelancer_Categoires", categoires);
            params.put("freelancer_experience",freelancer_experience);
            params.put("freelancer_status", freelancer_statusselected);
            params.put("enddate", filterenddate);
            params.put("startdate", filterstartdate);
            filterappliedvalues="";
            if(!finalvalues.isEmpty())
            {
                Multimap<String, String> multiMap = ArrayListMultimap.create();
                for (TempClassForFilterfreelancer b : finalvalues) {
                    multiMap.put(b.sortelement,"'"+b.value+"'");
                    values.add(b.sortelement);
                }
                HashSet<String> hashSet = new HashSet<String>();
                hashSet.addAll(values);
                values.clear();
                values.addAll(hashSet);
                hashSet.clear();
                for (int x=0; x<values.size(); x++){
                    if(x<values.size()-1){
                        filterappliedvalues= filterappliedvalues+" "+values.get(x)+",";}
                    else{
                        if(filterstartdate!=("0000-00-00 00:00:00")) {
                            filterappliedvalues = filterappliedvalues + " " + values.get(x) +", status, availabilty";
                        }else{ filterappliedvalues = filterappliedvalues + " " + values.get(x);}
                    }
                }
                Set<String> keys = multiMap.keySet();
                for (String key : keys) {
                    System.out.println("Key = " + key);
                    System.out.println("TempClassForFilterfreelancer = " + multiMap.get(key));
                    params.put(key, multiMap.get(key).toString());
                }
                return requestHandler.sendPostRequest(URLs.URL_RetriveListofFreelancer+"Selection", params);
            }
            else
            {
                return requestHandler.sendPostRequest(URLs.URL_RetriveListofFreelancer+"Selection", params);
            }
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
            Log.d("ssssss",s);
            p.dismiss();
            if(!finalvalues.isEmpty()) {
                txv_filtervalues.setText("You are Applied Filters on "+filterappliedvalues);
                txv_filtervalues.setVisibility(View.VISIBLE);
            }
            else
            {
                txv_filtervalues.setVisibility(View.GONE);
            }
            if(s.equalsIgnoreCase(" \"No Results Found\""))
            {
                listView.setAdapter(null);
                no_item.setVisibility(View.VISIBLE);
            }
            else {
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
                        String gender = jsonObject.getString("gender");
                        String certification = jsonObject.getString("certification");
                        String freelancer_designation = jsonObject.getString("freelancer_designation");
                        String System_date = jsonObject.getString("System_date");
                        String freelancer_address  = jsonObject.getString("freelancer_address");
                        employer_id = jsonObject.getString("employer_id");
                        employer_email = jsonObject.getString("employer_email");
                        String employer_hirefreelancer = jsonObject.getString("employer_hirefreelancer");
                        String clientrating  = jsonObject.getString("rating");
                        employer = new Employer(id, freelancer_username, freelancer_email, freelancer_contact, freelancer_summary, freelancer_skill, freelancer_industryexp, freelancer_domainexp, freelancer_education, freelancer_experience, freelancer_image, freelancer_status,status_reason, freelancer_location, freelancer_Categoires, freelancer_SubCategoires,freelancer_rating, gender, System_date,freelancer_address , employer_id, employer_email,employer_hirefreelancer,"0","0","0","0","0",clientrating,"0","0","0","0","0",freelancer_designation,certification,"0");
                        EmployerList.add(employer);
                    }
                    listAdapter = new ListAdapterEmployerSearch(getBaseContext(), R.layout.customlayout_freelancerlist, EmployerList);
                    listView.setAdapter(listAdapter);
              } catch (Exception e) { e.getMessage();}
            }
        }
    }
    class RetriveSubCategory extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("categories_id", Employer_SearchFragment.categoires_id);
            return requestHandler.sendPostRequest(URLs.URL_Retrivealltablevalue+"subcategories", params);
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
            try {
               jsonArray = new JSONArray(s);
                String subCategories;
                ArrayList<SpinnerClass> itemList = new ArrayList<SpinnerClass>();
               // itemList.add(new SpinnerClass("0", "Select_SubCategory"));
                JSONObject obj;
                for (int J = 0; J < jsonArray.length(); J++) {
                    obj = jsonArray.getJSONObject(J);
                    String subCategories_id = obj.getString("subcategories_id");
                    subCategories = obj.getString("subcategories").toString();
                    itemList.add(new SpinnerClass(subCategories_id, subCategories));
                }
                ArrayAdapter<SpinnerClass> spinnerAdapter = new ArrayAdapter<SpinnerClass>(getApplication(), R.layout.spinner_itemnew, itemList);
                spinnerAdapter.setDropDownViewResource( R.layout.spinner_item);
                spinner_subcategory.setAdapter(spinnerAdapter);
                spinner_subcategory.setSelection(getIndex(spinner_subcategory,subCategoires));
            } catch (Exception e) { e.getMessage();}
        }
    }
    @Override//spinner choice
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try{
        SpinnerClass i = (SpinnerClass) parent.getSelectedItem();
        subCategoiresid= i.getid();
        subCategoires= i.getStr();
        if(subCategoires.equalsIgnoreCase("Select_SubCategory"))
        {
            subCategoires="";
        }
            new RetriveFreelancerlist().execute();
        }catch (Exception e){}
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(getBaseContext(),"Their nothing to show", Toast.LENGTH_LONG).show();
    }
    public static int getIndex(Spinner spinner, String myString)//set spinner values
    {
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            Log.d("spinner.",spinner.getItemAtPosition(i).toString()+myString);
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
}