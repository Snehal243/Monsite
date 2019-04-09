package com.montek.monsite.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.montek.monsite.Adapter.MultispinnerCustomAdapter;
import com.montek.monsite.Adapter.RecyclerAdapter;
import com.montek.monsite.DisplayList_Freelancer;
import com.montek.monsite.JsonConnection.RequestHandler;
import com.montek.monsite.R;
import com.montek.monsite.extra.URLs;
import com.montek.monsite.model.Categoires;
import com.montek.monsite.model.FilterFreelancer;
import com.montek.monsite.model.SpinnerClass;
import com.montek.monsite.model.TempClassForFilterfreelancer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.montek.monsite.ActivityFilterEmployer.filterenddate;
import static com.montek.monsite.ActivityFilterEmployer.filterstartdate;
import static com.montek.monsite.ActivitySplash.p;
import static com.montek.monsite.Activity_Login.ProgressD;
import static com.montek.monsite.Adapter.ListAdapterFilterfreelancerSearch.FilterListstore;
import static com.montek.monsite.Adapter.ListAdapterFilterfreelancerSearch.finalvalues;
import static com.montek.monsite.Adapter.ListAdapterFilterfreelancerSearch.finalvaluesofjobsearch;
import static com.montek.monsite.DisplayList_Freelancer.subCategoiresid;
import static com.montek.monsite.Employer_EditProfile.jsonArray;
import static com.montek.monsite.R.id.spin_state1;
public class Employer_SearchFragment extends Fragment implements View.OnClickListener,Spinner.OnItemSelectedListener {
    public static int selecteditem =2;
    ListView listView;
    Spinner spin_country,spin_state;
    MultispinnerCustomAdapter SortlistAdapter;
    List arrayskill=new ArrayList();
    List arrayskillid=new ArrayList();
    List arraylocationid=new ArrayList();
    List arraylocation=new ArrayList();
    String Countryid,Stateid="";
    public static String categoires_id,categoires;
    View view;
    TextInputLayout  skillinputlayout,locationinputlayout;
    public static String freelancer_experience ="",skill,location,jobsearch="0",diglogskill="";
    TextView  edtx_skill,edtx_location,edtx_locationdialog;
    Spinner spin_yearxperience;
    String[] yearexperienceArray={"Work_Experience","0", "1", "2", "3","4","5","6","7","8","9","10+"};
    Button btn_search;
    public static  String[] Dlist={"Select Type","Hourly","Daily","Monthly"};
    String [] Categories_id=null,Categories=null,category_image=null;
    public static ArrayList<SpinnerClass> itemListCountry = new ArrayList<SpinnerClass>();
    public static ArrayList<SpinnerClass> itemListCategory = new ArrayList<SpinnerClass>();
    RecyclerView recyclerView;
    public static String  hiredlist="0";
    public static Employer_SearchFragment newInstance() {
        Employer_SearchFragment fragment = new Employer_SearchFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //  getActivity().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.activity_employersearch, container, false);
        try{
        p=new ProgressDialog(getContext());
        iUI();
      //  new employer_Duration().execute();
       // GlobalVariable.Employer_id != null && !GlobalVariable.Employer_id.isEmpty() && !GlobalVariable.Employer_id.equals("null")
            selecteditem=2;
            filterstartdate="0000-00-00 00:00:00";
            filterenddate="0000-00-00 00:00:00";
            DisplayList_Freelancer.subCategoires="";
            categoires="";subCategoiresid="";
            finalvaluesofjobsearch.clear();
            finalvalues.clear();
            itemListCountry.clear();
            itemListCategory.clear();
          new RetriveCategories().execute();
        ArrayAdapter yearexp = new ArrayAdapter(getContext(), R.layout.spinner_item, yearexperienceArray);
        // Drop down layout style - list view with radio button
        yearexp.setDropDownViewResource(R.layout.spinner_item);
        // attaching data adapter to spinner
        spin_yearxperience.setAdapter(yearexp);
        new RetriveCountry().execute();
        }catch (Exception e){}
      return view;
    }
    void iUI() throws Exception
    {
        recyclerView = (RecyclerView)view. findViewById(R.id.recyclerviewscroll);
        edtx_location = (TextView)view. findViewById(R.id.edtx_location);
        edtx_location.setOnClickListener(this);
        edtx_skill = (TextView)view. findViewById(R.id.edtx_skill);
        edtx_skill.setOnClickListener(this);
        spin_yearxperience = (Spinner)view. findViewById(R.id.spin_yearxperience);
        spin_yearxperience.setOnItemSelectedListener(this);
        btn_search = (Button)view. findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        skillinputlayout = (TextInputLayout)view. findViewById(R.id.skillinputlayout);
        locationinputlayout = (TextInputLayout)view. findViewById(R.id.locationinputlayout);
    }
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btn_search:
                    skill = edtx_skill.getText().toString().trim();
                    location = edtx_location.getText().toString().trim();
                    validationbtn();
                    break;
                case R.id.edtx_location:
                    diglogskill="0";
                    Activity activity1 = getActivity();
                    if (activity1 != null && isAdded()) {
                        ActivityAdvancelocationdialog();
                    }
                    break;
                case R.id.edtx_skill:
                    diglogskill="1";
                    Log.d("hii111","hiii");
                    Activity activity2 = getActivity();
                    if (activity2 != null && isAdded()) {
                        ActivityAdvanceskilldialog();
                    }
                    break;
            }
        }catch (Exception e){}
    }
    void spinnercountry() throws Exception
    {  ArrayAdapter<SpinnerClass> spinnerAdapter = null;
        Log.d("hii222","hiii");
        if(diglogskill.equalsIgnoreCase("1")) {
            spinnerAdapter = new ArrayAdapter<SpinnerClass>(getContext(), R.layout.spinner_item, itemListCategory);
        }else  if(diglogskill.equalsIgnoreCase("0"))  {
            spinnerAdapter = new ArrayAdapter<SpinnerClass>(getContext(), R.layout.spinner_item, itemListCountry);
        }
        Log.d("hii333","hiii");
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        spin_country.setAdapter(spinnerAdapter);
    }
    public void ActivityAdvanceskilldialog() throws Exception{
        Stateid="0";
        final Dialog dialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        dialog.setContentView(R.layout.activity_citydiaglog);
        spin_country = (Spinner)dialog. findViewById(R.id.spin_country1);
        spin_country.setOnItemSelectedListener(this);
        spinnercountry();
        spin_state = (Spinner)dialog. findViewById(spin_state1);
        spin_state.setOnItemSelectedListener(this);
        spin_state.setVisibility(View.GONE);
        edtx_locationdialog = (TextView)dialog. findViewById(R.id.edtx_location);
        edtx_locationdialog.setHint("Skill");
       // edtx_locationdialog.setEnabled(false);
        Button btn_ok = (Button)dialog. findViewById(R.id.btn_ok);
        Button btn_cancel = (Button)dialog. findViewById(R.id.btn_cancel);
        edtx_locationdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Stateid.equalsIgnoreCase("") || (Stateid.equalsIgnoreCase("0")))
                {
                    Toast.makeText(getContext(),"please select subcategory",Toast.LENGTH_SHORT).show();
                }
                else {
                    Activity activity = getActivity();
                    if (activity != null && isAdded()) {
                        try {
                            AlertDialogSkill();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void ActivityAdvancelocationdialog() throws Exception{
        Stateid="0";
       final Dialog dialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        dialog.setContentView(R.layout.activity_citydiaglog);
        spin_country = (Spinner)dialog. findViewById(R.id.spin_country1);
        spin_country.setOnItemSelectedListener(this);
        spinnercountry();
        spin_state = (Spinner)dialog. findViewById(spin_state1);
        spin_state.setOnItemSelectedListener(this);
        spin_state.setVisibility(View.GONE);
        edtx_locationdialog = (TextView)dialog. findViewById(R.id.edtx_location);
        edtx_locationdialog.setHint("Location");
      //  edtx_locationdialog.setEnabled(false);
        Button btn_ok = (Button)dialog. findViewById(R.id.btn_ok);
        Button btn_cancel = (Button)dialog. findViewById(R.id.btn_cancel);
            edtx_locationdialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Stateid.equalsIgnoreCase("")|| Stateid.equalsIgnoreCase("0"))
                    {
                        Toast.makeText(getContext(),"please select state",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Activity activity = getActivity();
                        if (activity != null && isAdded()) {
                            try {
                                AlertDialogLocation();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
  void validationbtn() throws Exception{
    boolean isValid = true;
    if (skill.isEmpty()) {
        skillinputlayout.setError("Skill is mandatory");
        isValid = false;
    } else {
        skillinputlayout.setErrorEnabled(false);
    }
//    if (location.isEmpty()) {
//        locationinputlayout.setError("Location is mandatory");
//        isValid = false;
//    } else {
//        locationinputlayout.setErrorEnabled(false);
//    }
    if (isValid) {
       Intent i=new Intent(getContext(),DisplayList_Freelancer.class);
        startActivity(i);
        jobsearch="1";
        edtx_skill.setText("");
        edtx_location.setText("");
        for (int j=0;j<arraylocation.size();j++)
        {
            finalvaluesofjobsearch.add(new TempClassForFilterfreelancer("location",arraylocation.get(j).toString()));
        }
        for (int j=0;j<arrayskill.size();j++)
        {
            finalvaluesofjobsearch.add(new TempClassForFilterfreelancer("skill",arrayskill.get(j).toString()));
        }
    }
}
    private void initViews() throws Exception { //Gridview using recyclerview
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Categoires> item = prepareData();
        RecyclerAdapter adapter = new RecyclerAdapter(getContext(), item);
        recyclerView.setAdapter(adapter);
    }
    private ArrayList<Categoires> prepareData() throws Exception {
        ArrayList<Categoires> item = new ArrayList<>();
        for (int i = 0; i < Categories_id.length; i++) {
            Categoires items = new Categoires();
            items.setid(Categories_id[i]);
            items.setcategoires(Categories[i]);
            items.setcategoires_image( category_image[i]);
            item.add(items);
        }
        return item;
    }
    class RetriveCategories extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            return requestHandler.sendPostRequest(URLs.URL_Retrivealltablevalue + "categories", params);
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
            Log.d("s",s);
            if (s.contains("No Results Found")) {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            } else {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(s);
                    Categories_id = new String[jsonArray.length()];
                    Categories = new String[jsonArray.length()];
                    category_image = new String[jsonArray.length()];
                    JSONObject jsonObject;
                        itemListCategory.add(new SpinnerClass("0", "Select_Category"));
                        for (int J = 0; J < jsonArray.length(); J++) {

                            jsonObject = jsonArray.getJSONObject(J);
                            String location_id = jsonObject.getString("categories_id");
                            String recivedlist = jsonObject.getString("categories").trim();
                            itemListCategory.add(new SpinnerClass(location_id, recivedlist));
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            Categories_id[i] = jsonObject.getString("categories_id");
                            Categories[i] = jsonObject.getString("categories").trim();
                            category_image[i] = "http://monsite.montekservices.com/"+jsonObject.getString("category_image");
                        }
                    initViews();
                } catch (Exception e) { e.getMessage(); }
            }
        }
    }
    public void AlertDialogLocation() throws Exception {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.activity_multispinner, null);
        // etUsernameF = (EditText) alertLayout.findViewById(R.id.et_username);
        listView = (ListView) alertLayout.findViewById(R.id.listView1);
        new Retrivelocation().execute();
        //  final RadioGroup Radio_dialog_Group = (RadioGroup) alertLayout.findViewById(R.id.dialogrdGroup);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        //for title color
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        String titleText="Select Multiple Values of Location";
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                titleText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        alert.setTitle(ssBuilder);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    StringBuffer responseText = new StringBuffer();
                    ArrayList<FilterFreelancer> countryList = SortlistAdapter.countryList;
                    arraylocation.clear();
                    arraylocationid.clear();
                    for (int i = 0; i < countryList.size(); i++) {
                        FilterFreelancer country = countryList.get(i);
                        if (country.getSelected()) {
                            responseText.append(country.getcategoires() + ",");

                            if (!arraylocationid.contains(country.getsortelementid())) {
                                arraylocationid.add(country.getsortelementid());
                                arraylocation.add(country.getcategoires());
                                // finalvalues.add(new TempClassForFilterfreelancer("location",country.getcategoires()));
                                FilterListstore.add(country.getcategoires());
                            }
                            // arrayindustryExp.add(country.getsortelementid());
                            Log.d("arrayskill", arraylocationid.toString());
                        }
                    }
                    //   Toast.makeText(getContext(), responseText, Toast.LENGTH_LONG).show();
                    edtx_location.setText(responseText);
                    edtx_locationdialog.setText(responseText);
                }catch (Exception e){}
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
        //for btn text color
        Button sign_out = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams neutralButtonLL = (LinearLayout.LayoutParams) sign_out.getLayoutParams();
        neutralButtonLL.gravity = Gravity.CENTER;
        sign_out.setLayoutParams(neutralButtonLL);
        sign_out.setTextColor(getResources().getColor(R.color.colorPrimary));
        Button btn_continue = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        LinearLayout.LayoutParams btn_continue1 = (LinearLayout.LayoutParams) btn_continue.getLayoutParams();
        btn_continue1.gravity = Gravity.CENTER;
        btn_continue.setLayoutParams(neutralButtonLL);
        btn_continue.setTextColor(getResources().getColor(R.color.colorPrimary));
    }
    class Retrivelocation extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("state_id",Stateid);
            return requestHandler.sendPostRequest(URLs.URL_Retrivealltablevalue+"location", params);
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
                Log.d("error", s);
            }
            else {
                try {
                    Log.d("Json response:", s);
                     jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    FilterFreelancer Sort;
                    boolean chk;
                    ArrayList<FilterFreelancer> Sortinglist = new ArrayList<FilterFreelancer>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("location_id");
                        String list1 = jsonObject.getString("location").trim();
                        chk = false;
                        for (int j = 0; j < arraylocation.size(); j++) {
                            Log.d("arraylocation", arraylocation.get(j).toString() + "" + id);
                            if (arraylocation.get(j).toString().equalsIgnoreCase(list1)) {
                                chk = true;
                                break;
                            }
                        }
                        Sort = new FilterFreelancer(Integer.toString(id), list1, chk);
                        Sortinglist.add(Sort);
                    }
                    SortlistAdapter = new MultispinnerCustomAdapter(getContext(), R.layout.activity_sortingelement, Sortinglist);
                    listView.setAdapter(SortlistAdapter);
                    listView.setItemsCanFocus(false);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void AlertDialogSkill() throws Exception{
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.activity_multispinner, null);
        // etUsernameF = (EditText) alertLayout.findViewById(R.id.et_username);
        listView = (ListView) alertLayout.findViewById(R.id.listView1);
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            new Retriveskill().execute();
        }
        //  final RadioGroup Radio_dialog_Group = (RadioGroup) alertLayout.findViewById(R.id.dialogrdGroup);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        //for title color
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        String titleText="Select Multiple Values";
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                titleText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        alert.setTitle(ssBuilder);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
               try {
                   StringBuffer responseText = new StringBuffer();
                   ArrayList<FilterFreelancer> countryList = SortlistAdapter.countryList;
                   arrayskill.clear();
                   arrayskillid.clear();
                   edtx_locationdialog.setText("");
                   edtx_location.setText("");
                   for (int i = 0; i < countryList.size(); i++) {
                       FilterFreelancer country = countryList.get(i);
                       if (country.getSelected()) {
                           responseText.append(country.getcategoires() + ",");

                           if (!arrayskillid.contains(country.getsortelementid())) {
                               arrayskillid.add(country.getsortelementid());
                               arrayskill.add(country.getcategoires());
                               //  finalvalues.add(new TempClassForFilterfreelancer("skill",country.getcategoires()));
                               FilterListstore.add(country.getcategoires());
                           }
                           // arrayindustryExp.add(country.getsortelementid());
                           Log.d("arrayskill", arrayskillid.toString());
                       }
                   }
                   edtx_skill.setText(responseText);
                   edtx_locationdialog.setText(responseText);
               }catch (Exception e) {}
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
        //for btn text color
        Button sign_out = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams neutralButtonLL = (LinearLayout.LayoutParams) sign_out.getLayoutParams();
        neutralButtonLL.gravity = Gravity.CENTER;
        sign_out.setLayoutParams(neutralButtonLL);
        sign_out.setTextColor(getResources().getColor(R.color.colorPrimary));
        Button btn_continue = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        LinearLayout.LayoutParams btn_continue1 = (LinearLayout.LayoutParams) btn_continue.getLayoutParams();
        btn_continue1.gravity = Gravity.CENTER;
        btn_continue.setLayoutParams(neutralButtonLL);
        btn_continue.setTextColor(getResources().getColor(R.color.colorPrimary));
    }
    class Retriveskill extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("subcategories_id",Stateid);
            //returing the response
            return requestHandler.sendPostRequest(URLs.URL_Retrivealltablevalue+"skill", params);
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
            if (s.contains("No Results Found")) {
                Log.d("error", s);
                Toast toast= Toast.makeText(getContext(),"Skills under this subcategory not available",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            else {
                //hiding the progressbar after completion
                try {
                    Log.d("Json response:", s);
                    JSONArray  jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    FilterFreelancer Sort;
                    boolean chk;
                    ArrayList<FilterFreelancer> Sortinglist = new ArrayList<FilterFreelancer>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("skill_id");
                        String list1 = jsonObject.getString("skill").trim();
                        chk = false;
                        for (int j = 0; j < arrayskill.size(); j++) {
                            Log.d("arrayskill", arrayskill.get(j).toString() + "" + id);
                            if (arrayskill.get(j).toString().equalsIgnoreCase(list1)) {
                                chk = true;
                                break;
                            }
                        }
                        Sort = new FilterFreelancer(Integer.toString(id), list1, chk);
                        Sortinglist.add(Sort);
                    }
                    SortlistAdapter = new MultispinnerCustomAdapter(getContext(), R.layout.activity_sortingelement, Sortinglist);
                    listView.setAdapter(SortlistAdapter);
                    listView.setItemsCanFocus(false);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

                } catch (Exception e) {
                    e.printStackTrace();
                    e.getMessage();
                }
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
            Log.d("Locatio",s);
            if (s.equalsIgnoreCase(" \"No Results Found\"")) {
                Log.d("error", s);
            }
            else {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    String recivedlist;
                    itemListCountry.add(new SpinnerClass("0", "Select_Country"));
                    JSONObject obj;
                    for (int J = 0; J < jsonArray.length(); J++) {
                        obj = jsonArray.getJSONObject(J);
                        String location_id = obj.getString("id");
                        recivedlist = obj.getString("name").trim();
//                        recivedlist = obj.getString("sortname").toString().trim();
//                        recivedlist = obj.getString("phonecode ").toString().trim();
                        itemListCountry.add(new SpinnerClass(location_id, recivedlist));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getMessage();
                    //Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    class RetriveState extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            //creating request parameters
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
            Log.d("Locatio",s);
            if (s.equalsIgnoreCase(" \"No Results Found\"")) {
                Log.d("error", s);
            }
            else {
                try {
                    JSONArray   jsonArray = new JSONArray(s);
                    String recivedlist;
                    ArrayList<SpinnerClass> itemList = new ArrayList<SpinnerClass>();
                    itemList.add(new SpinnerClass("0", "Select_State"));
                    JSONObject obj;
                    for (int J = 0; J < jsonArray.length(); J++) {
                        obj = jsonArray.getJSONObject(J);
                        String location_id = obj.getString("id");
                        recivedlist = obj.getString("name").trim();
//                        recivedlist = obj.getString("sortname").toString().trim();
//                        recivedlist = obj.getString("phonecode ").toString().trim();
                        itemList.add(new SpinnerClass(location_id, recivedlist));
                    }
                    ArrayAdapter<SpinnerClass> spinnerAdapter = new ArrayAdapter<SpinnerClass>(getContext(),R.layout.spinner_item, itemList);
                    spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
                    spin_state.setAdapter(spinnerAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class RetriveSubCategory extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("categories_id", categoires_id);
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
                itemList.add(new SpinnerClass("0", "Select_SubCategory"));
                JSONObject obj;
                for (int J = 0; J < jsonArray.length(); J++) {
                    obj = jsonArray.getJSONObject(J);
                    String subCategories_id = obj.getString("subcategories_id");
                    subCategories = obj.getString("subcategories");
                    itemList.add(new SpinnerClass(subCategories_id, subCategories));
                }
                ArrayAdapter<SpinnerClass> spinnerAdapter = new ArrayAdapter<SpinnerClass>(getContext(), R.layout.spinner_item, itemList);
                spinnerAdapter.setDropDownViewResource( R.layout.spinner_item);
                spin_state.setAdapter(spinnerAdapter);
            } catch (Exception e) { e.getMessage();}
        }
    }

    //spinner choice
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spin_country1)
        {
            Log.d("hiii11","hiii");
            SpinnerClass ii = (SpinnerClass) parent.getItemAtPosition(position);
            if( diglogskill.equalsIgnoreCase("0")) {
                Countryid=ii.getid();
                if("0" != Countryid.intern()){
                     new RetriveState().execute();
                    spin_state.setVisibility(View.VISIBLE);
                }
            }else if( diglogskill.equalsIgnoreCase("1")) {
                arrayskill.clear();
                arrayskillid.clear();
                edtx_locationdialog.setText("");
                edtx_location.setText("");
                categoires=ii.getStr();
                categoires_id=ii.getid();
                if("0" != categoires_id.intern()){
                spin_state.setVisibility(View.VISIBLE);
                new RetriveSubCategory().execute();}

            }
        }
        if(spinner.getId() == R.id.spin_state1)
        {
            SpinnerClass ii = (SpinnerClass) parent.getItemAtPosition(position);

            if(diglogskill.equalsIgnoreCase("1")){
                DisplayList_Freelancer.subCategoires=ii.getStr();
                subCategoiresid=ii.getid();
                Stateid=ii.getid();}
            if(diglogskill.equalsIgnoreCase("0")) {
                Stateid=ii.getid();
            }
        }
        if(spinner.getId() == R.id.spin_yearxperience)
        {
            freelancer_experience = parent.getItemAtPosition(position).toString()+".";
            if(freelancer_experience.equalsIgnoreCase("Work_Experience."))
            {
                freelancer_experience="";
            }
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(getContext(),"Their nothing to show", Toast.LENGTH_LONG).show();
    }
}