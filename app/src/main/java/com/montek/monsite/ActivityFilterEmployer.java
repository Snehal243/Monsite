package com.montek.monsite;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.montek.monsite.Adapter.ListAdapterFilterfreelancerSearch;
import com.montek.monsite.Adapter.ListAdapterSortlistFreelancer;
import com.montek.monsite.Fragments.Employer_SearchFragment;
import com.montek.monsite.JsonConnection.RequestHandler;
import com.montek.monsite.extra.GlobalVariable;
import com.montek.monsite.extra.URLs;
import com.montek.monsite.model.FilterFreelancer;
import com.montek.monsite.model.SortinglistFreelancer;
import com.montek.monsite.model.SpinnerClass;
import com.montek.monsite.model.TempClassForFilterfreelancer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

import static com.montek.monsite.ActivitySplash.p;
import static com.montek.monsite.Activity_Login.ProgressD;
import static com.montek.monsite.Adapter.ListAdapterFilterfreelancerSearch.FilterListstore;
import static com.montek.monsite.Adapter.ListAdapterFilterfreelancerSearch.finalvalues;
import static com.montek.monsite.Adapter.ListAdapterFilterfreelancerSearch.finalvaluesofjobsearch;
import static com.montek.monsite.DisplayList_Freelancer.subCategoiresid;
import static com.montek.monsite.FLprofileOnEmployerDashbord.dayend;
import static com.montek.monsite.FLprofileOnEmployerDashbord.daystart;
import static com.montek.monsite.FLprofileOnEmployerDashbord.monthend;
import static com.montek.monsite.FLprofileOnEmployerDashbord.monthstart;
import static com.montek.monsite.FLprofileOnEmployerDashbord.yearend;
import static com.montek.monsite.FLprofileOnEmployerDashbord.yearstart;
import static com.montek.monsite.Fragments.Employer_SearchFragment.jobsearch;
import static com.montek.monsite.extra.GlobalVariable.sortelement;
public class ActivityFilterEmployer extends AppCompatActivity implements Spinner.OnItemSelectedListener, View.OnClickListener {
    ListView listView1,listView2;
    TextView edtx_enddate,edtx_startdate,txv_citylist;
    ArrayList<String> Sortfeild=new ArrayList<>();
    ArrayList<FilterFreelancer> FilterList = new ArrayList<FilterFreelancer>();
    ListAdapterFilterfreelancerSearch listAdapter;
    ListAdapterSortlistFreelancer SortlistAdapter;
    ArrayList<SortinglistFreelancer> Sortinglist=new ArrayList<SortinglistFreelancer>();
    Button btn_clearFilters,btn_ApplyFilters;
    FilterFreelancer filter;
    String sorting_title,Countryid,Stateid;
    LinearLayout layoutdatepicker,datetimelayout,no_item,spinnercountrylayout;
    Spinner spin_country,spin_state;
    JSONArray jsonArray=null;

    public static String freelancer_statusselected="",filterstartdate,filterenddate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutfilterfreelancer);
        try {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
                actionBar.setTitle("Filters");
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            UI();
            p = new ProgressDialog(this);
            new RetriveSortinglist().execute();
            listView1.setTextFilterEnabled(true);
            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    FilterFreelancer ListViewClickData = (FilterFreelancer) parent.getItemAtPosition(position);
                }
            });
            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SortinglistFreelancer ListViewClickData = (SortinglistFreelancer) parent.getItemAtPosition(position);
                    sortelement = ListViewClickData.getsorting_title();
                    spinnercountrylayout.setVisibility(View.GONE);
                    datetimelayout.setVisibility(View.GONE);
                    layoutdatepicker.setVisibility(View.GONE);
                    if (sortelement.equalsIgnoreCase("location")) {
                        spinnercountrylayout.setVisibility(View.VISIBLE);
                        no_item.setVisibility(View.GONE);
                        new RetriveCountry().execute();
                    } else if (sortelement.equalsIgnoreCase("status")) {
                        datetimelayout.setVisibility(View.VISIBLE);
                        no_item.setVisibility(View.GONE);
                    } else if (sortelement.equalsIgnoreCase("availability")) {
                        layoutdatepicker.setVisibility(View.VISIBLE);
                        no_item.setVisibility(View.GONE);
                    }
                    new RetriveSelectedSortvalue().execute();
                }
            });
        }catch (Exception e){}
    }
    void UI()  throws Exception
    {   layoutdatepicker = (LinearLayout)findViewById(R.id.layoutdatepicker);
        layoutdatepicker.setVisibility(View.GONE);
        edtx_enddate=(TextView)findViewById(R.id.txv_enddate);
        edtx_enddate.setOnClickListener(this);
        edtx_startdate=(TextView)findViewById(R.id.txv_startdate);
        edtx_startdate.setOnClickListener(this);
        txv_citylist=(TextView)findViewById(R.id.citylist);
        spin_country = (Spinner)findViewById(R.id.spin_country1);
        spin_country.setOnItemSelectedListener(this);
        spin_state = (Spinner)findViewById(R.id.spin_state1);
        spin_state.setOnItemSelectedListener(this);
        spin_state.setVisibility(View.GONE);
        spinnercountrylayout = (LinearLayout)findViewById(R.id.spinnercountrylayout);
        spinnercountrylayout.setVisibility(View.GONE);
        datetimelayout = (LinearLayout)findViewById(R.id.datetimelayout);
        datetimelayout.setVisibility(View.GONE);
        listView1 = (ListView)findViewById(R.id.list1);
        listView2 = (ListView)findViewById(R.id.list2);
        btn_ApplyFilters = (Button) findViewById(R.id.btn_ApplyFilters);
        btn_ApplyFilters.setOnClickListener(this);
        btn_clearFilters = (Button) findViewById(R.id.btn_clearFilters);
        btn_clearFilters.setOnClickListener(this);
        no_item = (LinearLayout)findViewById(R.id.no_item);
        no_item.setVisibility(View.GONE);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                clear();
                if(!finalvalues.isEmpty()) {
                    finalvalues.clear();
                }
                Intent i = new Intent(getBaseContext(), DisplayList_Freelancer.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        clear();
        if(!finalvalues.isEmpty()) {
            finalvalues.clear();
        }
         Intent i = new Intent(getBaseContext(), DisplayList_Freelancer.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        this.finish();
    }
    void clear()
    {
        filterstartdate="0000-00-00 00:00:00";
        filterenddate="0000-00-00 00:00:00";
        freelancer_statusselected="";
        GlobalVariable.sortelement="education";
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v)  {
        switch (v.getId()) {
            case R.id.btn_clearFilters:
                clear();
                Employer_SearchFragment.selecteditem=2;
                if(!finalvalues.isEmpty()) {
                    FilterListstore.clear();
                    finalvalues.clear();
                }
                if(jobsearch.equalsIgnoreCase("1")) {
                    finalvaluesofjobsearch.clear();
                }
                Log.d("size",String.valueOf(FilterListstore.size())+String.valueOf(finalvaluesofjobsearch));
                Intent i=new Intent(this,ActivityFilterEmployer.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                break;
            case R.id.btn_ApplyFilters:
                Log.d("hhh","hhh");
                if(jobsearch.equalsIgnoreCase("1")) {
                    finalvaluesofjobsearch.clear();
                }
                try {
                    Log.d("hhh","hhh1");
                    filterstartdate = edtx_startdate.getText().toString().trim();
                    filterenddate = edtx_enddate.getText().toString().trim();
                    if (filterstartdate != null && !filterstartdate.isEmpty() && !filterstartdate.equals("null")) {
                    } else {
                        filterstartdate = "0000-00-00 00:00:00";
                    }
                    if (filterenddate != null && !filterenddate.isEmpty() && !filterenddate.equals("null")) {
                    } else {
                        filterenddate = "0000-00-00 00:00:00";
                    }
                    RadioGroup  rbt_stausGroup = (RadioGroup) findViewById(R.id.rbt_stausGroup);
                    int selectedId = rbt_stausGroup.getCheckedRadioButtonId();
                    RadioButton radioSexButton = (RadioButton)findViewById(selectedId);
                    freelancer_statusselected = radioSexButton.getText().toString();
                    if (freelancer_statusselected.equalsIgnoreCase("All")) {
                        freelancer_statusselected = "";
                    }
                    Multimap<String, String> multiMap = ArrayListMultimap.create();
                    for (TempClassForFilterfreelancer b : finalvalues) {
                        multiMap.put(b.sortelement, b.value);
                    }
                    Set<String> keys = multiMap.keySet();
                    for (String key : keys) {
                        System.out.println("Key = " + key);
                        System.out.println("TempClassForFilterfreelancer = " + multiMap.get(key));
                    }
                    Intent i1 = new Intent(this, DisplayList_Freelancer.class);
                    i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i1);
                    finish();
                }catch (Exception e){}
                break;

            case R.id.txv_startdate:
                try {
                    startdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.txv_enddate:
                if(edtx_startdate.getText().toString()!="")
                {
                    try {
                        enddate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getBaseContext(), "Please Select StartDate", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    void enddate() throws Exception
    {
       final Calendar c = Calendar.getInstance();// calender class's instance and get current date , month and year from calender
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        filterenddate=(year + "-"+ (monthOfYear + 1) + "-" +dayOfMonth );
                        yearend = (year);
                        monthend = (monthOfYear + 1);
                        dayend = (dayOfMonth);
                    }
                }, yearstart, monthstart-1, daystart);
        timeend();
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }
    void startdate() throws Exception
    {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                        filterstartdate=(year + "-"
                                + (monthOfYear + 1) + "-" +dayOfMonth);
                        yearstart=(year);
                        monthstart=(monthOfYear + 1);
                        daystart=(dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        timestart();
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }
    void timestart() throws Exception {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                edtx_startdate.setText(filterstartdate+" "+selectedHour + ":" + selectedMinute+":00");
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
    void timeend() throws Exception {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                edtx_enddate.setText(filterenddate+" "+selectedHour + ":" + selectedMinute+":00");
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
    class RetriveSortinglist extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            return requestHandler.sendPostRequest(URLs.URL_RetriveSortOption+"employer", params);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressD();// calender class's instance and get current date , month and year from calender
        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            p.dismiss();
            Log.d("sorting item",s);
            try {
                jsonArray = new JSONArray(s);
                JSONObject jsonObject;
                SortinglistFreelancer Sort;
                Sortinglist = new ArrayList<SortinglistFreelancer>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    sorting_title = jsonObject.getString("sorting_title");
                    String image = jsonObject.getString("image");
                    Sort = new SortinglistFreelancer(id,sorting_title,image);
                    Sortfeild.add(sorting_title);
                    Log.d("Sortfeild",Sortfeild.toString());
                    Sortinglist.add(Sort);
                }
                SortlistAdapter = new ListAdapterSortlistFreelancer(getBaseContext(), R.layout.activity_filtercategoryelementoffreelancer, Sortinglist);
                listView2.setAdapter(SortlistAdapter);
                listView2.setItemsCanFocus(false);
                listView2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                new RetriveSelectedSortvalue().execute();
            } catch (Exception e) { e.getMessage();}
        }
    }
    class RetriveSelectedSortvalue extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            if(sortelement.equalsIgnoreCase("location")&& Stateid!=null &&(""!= Stateid.intern()))
            {
                params.put("state_id", Stateid);
            }
            if((sortelement.equalsIgnoreCase("skill")|| sortelement.equalsIgnoreCase("domainexperties") || sortelement.equalsIgnoreCase("industryexperties"))&& subCategoiresid!=null &&(""!= subCategoiresid.intern()))
            {
                params.put("subcategories_id", subCategoiresid);
            }
            return requestHandler.sendPostRequest(URLs.URL_Retrivealltablevalue + sortelement, params);
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
            Log.d("sssss",s);
            p.dismiss();
            if (s.equalsIgnoreCase("No Results Found")) {
                if(sortelement.equalsIgnoreCase("skill")) {
                    Toast toast = Toast.makeText(getBaseContext(),"Skills not available", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                listView1.setAdapter(null);
                no_item.setVisibility(View.VISIBLE);
                listView1.setVisibility(View.GONE);
                if(sortelement.contains("status") || sortelement.contains("availability") || sortelement.contains("location")) {
                    no_item.setVisibility(View.GONE);
                }
            } else {
                no_item.setVisibility(View.GONE);
                listView1.setVisibility(View.VISIBLE);
                try {
                    if (sortelement.contains("location")) {
                        txv_citylist.setVisibility(View.VISIBLE);
                    } else
                    {
                        txv_citylist.setVisibility(View.GONE);
                    }
                    Log.d("sortelement", sortelement);
                    jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    boolean chekbox = false;
                    int j;
                    FilterList = new ArrayList<FilterFreelancer>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String Filter = jsonObject.getString(sortelement);
                        if (!FilterListstore.isEmpty()) {
                            Log.d("vales2", Filter);
                            for (j = 0; j < FilterListstore.size(); j++) {
                                if (FilterListstore.get(j).equals(Filter)) {
                                    chekbox = true;
                                    break;
                                } else {
                                    chekbox =false;
                                }
                            }
                        } else {
                            chekbox = false;
                        }
                        filter = new FilterFreelancer(sortelement, Filter, chekbox);
                        FilterList.add(filter);
                    }
                    listAdapter = new ListAdapterFilterfreelancerSearch(getBaseContext(), R.layout.activity_sortingelement, FilterList);
                    listView1.setAdapter(listAdapter);
                    listView1.setItemsCanFocus(false);
                    listView1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                } catch (Exception e) { e.getMessage();}
            }
        }
    }
    class RetriveCountry extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            return requestHandler.sendPostRequest(URLs.URL_Retrivealltablevalue+"countries", params);
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
            if (s.equalsIgnoreCase(" \"No Results Found\"")) {
            }
            else {
                try {
                    jsonArray = new JSONArray(s);
                    String recivedlist;
                    ArrayList<SpinnerClass> itemList = new ArrayList<SpinnerClass>();
                    itemList.add(new SpinnerClass("0", "Select_Country"));
                    JSONObject obj;
                    for (int J = 0; J < jsonArray.length(); J++) {
                        obj = jsonArray.getJSONObject(J);
                        String location_id = obj.getString("id");
                        recivedlist = obj.getString("name").trim();
                        itemList.add(new SpinnerClass(location_id, recivedlist));
                    }
                    ArrayAdapter<SpinnerClass> spinnerAdapter = new ArrayAdapter<SpinnerClass>(getBaseContext(),R.layout.spinner_item, itemList);
                    spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
                    spin_country.setAdapter(spinnerAdapter);
                } catch (Exception e) {e.getMessage();}
            }
        }
    }
    class RetriveState extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("country_id",Countryid);
            return requestHandler.sendPostRequest(URLs.URL_Retrivealltablevalue+"states", params);
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
            if (s.equalsIgnoreCase(" \"No Results Found\"")) {
            }
            else {
                try {
                    jsonArray = new JSONArray(s);
                    String recivedlist;
                    ArrayList<SpinnerClass> itemList = new ArrayList<SpinnerClass>();
                    itemList.add(new SpinnerClass("0", "Select_State"));
                    JSONObject obj;
                    for (int J = 0; J < jsonArray.length(); J++) {
                        obj = jsonArray.getJSONObject(J);
                        String location_id = obj.getString("id");
                        recivedlist = obj.getString("name").trim();
                        itemList.add(new SpinnerClass(location_id, recivedlist));
                    }
                    ArrayAdapter<SpinnerClass> spinnerAdapter = new ArrayAdapter<SpinnerClass>(getBaseContext(),R.layout.spinner_item, itemList);
                    // Drop down layout style - list view with radio button
                    spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
                    // attaching data adapter to spinner
                    spin_state.setAdapter(spinnerAdapter);
                } catch (Exception e) { e.getMessage();}
            }
        }
    }
    //spinner choice
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Toast.makeText(getBaseContext(),parent.getItemAtPosition(position).toString() + " Selected" ,Toast.LENGTH_LONG).show();
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spin_country1)
        {
            SpinnerClass ii = (SpinnerClass) parent.getItemAtPosition(position);
            Countryid=ii.getid();
            Log.d("Countryid",Countryid);
            //  Toast.makeText(getBaseContext(),Countryid, Toast.LENGTH_LONG).show();
            if("0" != Countryid.intern()) {
                spin_state.setVisibility(View.VISIBLE);
                no_item.setVisibility(View.GONE);
                new RetriveState().execute();
            }
        }
        if(spinner.getId() == R.id.spin_state1)
        {
            SpinnerClass ii = (SpinnerClass) parent.getItemAtPosition(position);
            Stateid=ii.getid();
            new RetriveSelectedSortvalue().execute();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(getBaseContext(),"Their nothing to show", Toast.LENGTH_LONG).show();
    }
}
