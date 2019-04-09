package com.montek.monsite.Fragments;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.montek.monsite.Adapter.MultispinnerCustomAdapter;
import com.montek.monsite.DisplayList_Freelancer;
import com.montek.monsite.JsonConnection.RequestHandler;
import com.montek.monsite.R;
import com.montek.monsite.extra.GlobalVariable;
import com.montek.monsite.extra.URLs;
import com.montek.monsite.model.FilterFreelancer;
import com.montek.monsite.model.SpinnerClass;
import com.montek.monsite.model.TempClassForFilterfreelancer;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import static com.montek.monsite.ActivitySplash.p;
import static com.montek.monsite.Activity_Login.ProgressD;
import static com.montek.monsite.Adapter.ListAdapterFilterfreelancerSearch.FilterListstore;
import static com.montek.monsite.Adapter.ListAdapterFilterfreelancerSearch.finalvalues;
import static com.montek.monsite.DisplayList_Freelancer.subCategoiresid;
import static com.montek.monsite.Employer_EditProfile.jsonArray;
import static com.montek.monsite.Fragments.Employer_SearchFragment.Dlist;
import static com.montek.monsite.Fragments.Employer_SearchFragment.categoires;
import static com.montek.monsite.Fragments.Employer_SearchFragment.categoires_id;
import static com.montek.monsite.Fragments.Employer_SearchFragment.diglogskill;
import static com.montek.monsite.Fragments.Employer_SearchFragment.itemListCategory;
import static com.montek.monsite.Fragments.Employer_SearchFragment.itemListCountry;
import static com.montek.monsite.R.id.spin_state1;
public class SendRequirment_Fragment  extends Fragment implements Spinner.OnItemSelectedListener,View.OnClickListener  {
    Spinner spin_country,spin_state;
    List arraylocationid=new ArrayList();
    List arraylocation=new ArrayList();
    String Countryid,Stateid="";
    View v;
    Button btn_send;
    EditText edtx_titleofrequirment,edtx_description;
    String System_date,education,skill,titleofrequirment,description,location,ratingvalue,Durationselected;
    RatingBar ratingBar;
    List arrayskill=new ArrayList();
    List arrayeducation=new ArrayList();
    List arrayeducationid=new ArrayList();
    List arrayskillid=new ArrayList();
    TextView txv_date,edtx_education,edtx_location,edtx_locationdialog,edtx_skill;
    MultispinnerCustomAdapter SortlistAdapter;
    ListView listView;
    Spinner spn_duration,spin_yearxperience,spin_monthxperience;
    String expmonth=null,expyear = null,fromdateofhiring;
    String[] yearexperienceArray={"Select_Year","0-1", "1-2", "2-3", "3-4","4-5","5-6","6-7","7-8","8-9","9-10","10+"};
    String[] monthexperienceArray={"Select_Month", "1", "2", "3","4","5","6","7","8","9","10","11","12"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_sendrequirement, container, false);
       try {
           iUI();
           p = new ProgressDialog(getContext());
           new RetriveEducation().execute();
           Calendar c = Calendar.getInstance();
           SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
           System_date = df1.format(c.getTime());
           ratingBar.setOnTouchListener(new View.OnTouchListener() {
               @Override
               public boolean onTouch(View v, MotionEvent event) {
                   if (event.getAction() == MotionEvent.ACTION_UP) {
                       float touchPositionX = event.getX();
                       float width = ratingBar.getWidth();
                       float starsf = (touchPositionX / width) * 5.0f;
                       int stars = (int) starsf + 1;
                       ratingBar.setRating(stars);
                       v.setPressed(false);
                   }
                   if (event.getAction() == MotionEvent.ACTION_DOWN) {
                       v.setPressed(true);
                   }
                   if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                       v.setPressed(false);
                   }
                   return true;
               }
           });
           Spinner();
       }catch (Exception e){}
    return v;
    }
    private void iUI() throws Exception {
        btn_send=(Button)v.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        edtx_skill = (TextView)v.findViewById(R.id.edtx_skill);
        edtx_skill.setOnClickListener(this);
        spin_monthxperience=(Spinner)v.findViewById(R.id.spin_monthxperience);
        spin_monthxperience.setOnItemSelectedListener(this);
        spin_yearxperience=(Spinner)v.findViewById(R.id.spin_yearxperience);
        spin_yearxperience.setOnItemSelectedListener(this);
        edtx_titleofrequirment = (EditText)v.findViewById(R.id.edtx_titleofrequirment);
        edtx_description = (EditText)v.findViewById(R.id.edtx_description);
        ratingBar = (RatingBar)v.findViewById(R.id.ratingbar);
        Drawable drawable = ratingBar.getProgressDrawable();
        // drawable.setColorFilter(Color.parseColor("#83AAE5"), PorterDuff.Mode.SRC_ATOP);
        spn_duration = (Spinner)v.findViewById(R.id.spn_duration);
        spn_duration.setOnItemSelectedListener(this);
        edtx_education = (TextView)v.findViewById(R.id.edtx_education);
        edtx_education.setOnClickListener(this);
        edtx_location = (TextView)v.findViewById(R.id.edtx_location);
        edtx_location.setOnClickListener(this);
        txv_date = (TextView)v.findViewById(R.id.txv_date);
        txv_date.setOnClickListener(this);
    }
    public void AlertDialogEducation() throws Exception {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.activity_multispinner, null);
        listView = (ListView) alertLayout.findViewById(R.id.listView1);
        new RetriveEducation().execute();
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        String titleText="Select Multiple Values";
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                titleText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        alert.setTitle(ssBuilder);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuffer responseText = new StringBuffer();
                ArrayList<FilterFreelancer> countryList = SortlistAdapter.countryList;
                arrayeducation.clear();
                arrayeducationid.clear();
                for(int i=0;i<countryList.size();i++){
                    FilterFreelancer country = countryList.get(i);
                    if(country.getSelected()){
                        responseText.append(country.getcategoires()+",");
                        if(!arrayeducationid.contains(country.getsortelementid())) {
                            arrayeducationid.add(country.getsortelementid());
                            arrayeducation.add(country.getcategoires());
                        }
                    }
                }
                edtx_education.setText(responseText);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
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
    public void AlertDialogSkill() throws Exception{
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.activity_multispinner, null);
        listView = (ListView) alertLayout.findViewById(R.id.listView1);
        new Retriveskill().execute();
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        //for title color
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        String titleText="Select Multiple Values";
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                titleText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        alert.setTitle(ssBuilder);
        alert.setView(alertLayout);
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
                    for (int i = 0; i < countryList.size(); i++) {
                        FilterFreelancer country = countryList.get(i);
                        if (country.getSelected()) {
                            responseText.append(country.getcategoires() + ",");
                            if (!arrayskillid.contains(country.getsortelementid())) {
                                arrayskillid.add(country.getsortelementid());
                                arrayskill.add(country.getcategoires());
                            }
                        }
                    }
                    edtx_skill.setText(responseText);
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
    class Retriveskill extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("subcategories_id",Stateid);
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
            if (s.toString().contains("No Results Found")) {
                Log.d("error", s.toString());
                Toast toast= Toast.makeText(getContext(),"Skills under this subcategory not available",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            else {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    FilterFreelancer Sort;
                    boolean chk;
                    ArrayList<FilterFreelancer> Sortinglist = new ArrayList<FilterFreelancer>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("skill_id");
                        String list1 = jsonObject.getString("skill").toString().trim();
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

                } catch (Exception e) { e.printStackTrace();  }
            }
        }
    }
    class RetriveEducation extends AsyncTask<Void, Void, String> {
    @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            return requestHandler.sendPostRequest(URLs.URL_Retrivealltablevalue+"education", params);
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
            if (s.toString().equalsIgnoreCase(" \"No Results Found\"")) {
            }
            else {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    FilterFreelancer Sort;
                    boolean chk;
                    ArrayList<FilterFreelancer> Sortinglist = new ArrayList<FilterFreelancer>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("education_id");
                        String list1 = jsonObject.getString("education").toString().trim();
                        chk = false;
                        for (int j = 0; j < arrayeducation.size(); j++) {
                            Log.d("arrayskill", arrayeducation.get(j).toString() + "" + id);
                            if (arrayeducation.get(j).toString().equalsIgnoreCase(list1)) {
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
                } catch (Exception e) {e.getMessage();}
            }
        }
    }
    void Spinner() throws Exception
    {
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(),R.layout.spinner_item, Dlist);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_duration.setAdapter(spinnerAdapter);
        ArrayAdapter yearexp = new ArrayAdapter(getContext(), R.layout.spinner_item, yearexperienceArray);
        yearexp.setDropDownViewResource(R.layout.spinner_item);
        spin_yearxperience.setAdapter(yearexp);
        ArrayAdapter monthexp = new ArrayAdapter(getContext(), R.layout.spinner_item, monthexperienceArray);
        monthexp.setDropDownViewResource(R.layout.spinner_item);
        spin_monthxperience.setAdapter(monthexp);
    }
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.txv_date:
                    dateofhiring();
                    break;
                case R.id.edtx_location:
                    diglogskill="0";
                    Activity activity = getActivity();
                    if (activity != null && isAdded()) {
                        ActivityAdvanceFilterdialog();
                    }
                    break;
                case R.id.edtx_skill:
                    diglogskill="1";
                    Activity activity1 = getActivity();
                    if (activity1 != null && isAdded()) {
                        ActivityAdvanceskilldialog();
                    }
                    break;
                case R.id.edtx_education:
                    AlertDialogEducation();
                    break;
                case R.id.btn_send:
                    skill = edtx_skill.getText().toString().trim();
                    titleofrequirment = edtx_titleofrequirment.getText().toString().trim();
                    description = edtx_description.getText().toString().trim();
                    location = edtx_location.getText().toString().trim();
                    education = edtx_education.getText().toString().trim();
                    fromdateofhiring = txv_date.getText().toString().trim();
                    if (fromdateofhiring.equalsIgnoreCase("")) {
                        fromdateofhiring = "Not Mention";
                    }
                    ratingvalue = String.valueOf(ratingBar.getRating());
                    if (ratingvalue.equalsIgnoreCase("")) {
                        ratingvalue = "0";
                    }
                    if (TextUtils.isEmpty(titleofrequirment)) {
                        edtx_titleofrequirment.setError("Please enter Title of Requirment");
                        edtx_titleofrequirment.requestFocus();
                        return;
                    } else if (skill.isEmpty()) {
                        Toast.makeText(getContext(), "Please enter skill", Toast.LENGTH_LONG).show();
                    } else if (location.isEmpty()) {
                        Toast.makeText(getContext(), "Please Enter Location", Toast.LENGTH_LONG).show();
                    } else if (Durationselected.equalsIgnoreCase("Select Type")) {
                        Toast.makeText(getContext(), "Please Duration Type", Toast.LENGTH_LONG).show();
                    } else if (education.isEmpty()) {
                        Toast.makeText(getContext(), "Please Select Education", Toast.LENGTH_LONG).show();
                    } else {
                        new SendRequirmentask().execute();
                    }
                    break;
            }
        }catch (Exception e){}
    }
    void dateofhiring() throws Exception
    {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        String a = (year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        txv_date.setText(a);
                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }

    public void ActivityAdvanceskilldialog() throws Exception{
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
                if(Stateid.equalsIgnoreCase(""))
                {
                    Toast.makeText(getContext(),"please select skill",Toast.LENGTH_SHORT).show();
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
    void spinnercountry() throws Exception
    {     ArrayAdapter<SpinnerClass> spinnerAdapter = null;
        if(diglogskill.equalsIgnoreCase("1")) {
            spinnerAdapter = new ArrayAdapter<SpinnerClass>(getContext(), R.layout.spinner_item, itemListCategory);
        }else  if(diglogskill.equalsIgnoreCase("0"))  {
            spinnerAdapter = new ArrayAdapter<SpinnerClass>(getContext(), R.layout.spinner_item, itemListCountry);
        }
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        spin_country.setAdapter(spinnerAdapter);
    }
    public void ActivityAdvanceFilterdialog() throws Exception{
        final Dialog dialog = new Dialog(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        dialog.setContentView(R.layout.activity_citydiaglog);
        spin_country = (Spinner)dialog. findViewById(R.id.spin_country1);
        spin_country.setOnItemSelectedListener(this);
        spinnercountry();
        spin_state = (Spinner)dialog. findViewById(spin_state1);
        spin_state.setOnItemSelectedListener(this);
        spin_state.setVisibility(View.GONE);
        edtx_locationdialog = (TextView)dialog. findViewById(R.id.edtx_location);
        Button btn_ok = (Button)dialog. findViewById(R.id.btn_ok);
        Button btn_cancel = (Button)dialog. findViewById(R.id.btn_cancel);
            edtx_locationdialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Countryid.equalsIgnoreCase("") || Countryid.equalsIgnoreCase("0") )
                    {
                        Toast.makeText(getContext(),"please select Country",Toast.LENGTH_LONG).show();
                    }
                    else if(Stateid.equalsIgnoreCase("") || Stateid.equalsIgnoreCase("0"))
                    {
                        Toast.makeText(getContext(),"please select state",Toast.LENGTH_LONG).show();
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
    public void AlertDialogLocation() throws Exception {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.activity_multispinner, null);
        listView = (ListView) alertLayout.findViewById(R.id.listView1);
        new Retrivelocation().execute();
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
                                finalvalues.add(new TempClassForFilterfreelancer("location", country.getcategoires()));
                                FilterListstore.add(country.getcategoires());
                            }
                            // arrayindustryExp.add(country.getsortelementid());
                        }
                    }
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
            if (s.toString().equalsIgnoreCase(" \"No Results Found\"")) {
            }
            else {
                try {
                    Log.d("Json response:", s.toString());
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    FilterFreelancer Sort;
                    boolean chk;
                    ArrayList<FilterFreelancer> Sortinglist = new ArrayList<FilterFreelancer>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("location_id");
                        String list1 = jsonObject.getString("location").toString().trim();
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

                } catch (Exception e) {  e.getMessage();}
            }
        }
    }
//    class RetriveCountry extends AsyncTask<Void, Void, String> {
//        @Override
//        protected String doInBackground(Void... voids) {
//            RequestHandler requestHandler = new RequestHandler();
//            HashMap<String, String> params = new HashMap<>();
//            return requestHandler.sendPostRequest(URLs.URL_Retrivealltablevalue+"countries", params);
//        }
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//           ProgressD();
//        }
//        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            p.dismiss();
//            if (s.toString().equalsIgnoreCase(" \"No Results Found\"")) {
//                Log.d("error", s.toString());
//            }
//            else {
//                try {
//                    JSONArray jsonArray = new JSONArray(s);
//                    String recivedlist;
//                    itemListCountry.add(new SpinnerClass("0", "Select_Country"));
//                    JSONObject obj;
//                    for (int J = 0; J < jsonArray.length(); J++) {
//                        obj = jsonArray.getJSONObject(J);
//                        String location_id = obj.getString("id");
//                        recivedlist = obj.getString("name").toString().trim();
////                        recivedlist = obj.getString("sortname").toString().trim();
////                        recivedlist = obj.getString("phonecode ").toString().trim();
//                        itemListCountry.add(new SpinnerClass(location_id, recivedlist));
//                    }
//                } catch (Exception e) { e.getMessage();}
//            }
//        }
//    }
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
            if (s.toString().equalsIgnoreCase(" \"No Results Found\"")) {
                Log.d("error", s.toString());
            }
            else {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    String recivedlist;
                    ArrayList<SpinnerClass> itemList = new ArrayList<SpinnerClass>();
                    itemList.add(new SpinnerClass("0", "Select_State"));
                    JSONObject obj;
                    for (int J = 0; J < jsonArray.length(); J++) {
                        obj = jsonArray.getJSONObject(J);
                        String location_id = obj.getString("id");
                        recivedlist = obj.getString("name").toString().trim();
//                        recivedlist = obj.getString("sortname").toString().trim();
//                        recivedlist = obj.getString("phonecode ").toString().trim();
                        itemList.add(new SpinnerClass(location_id, recivedlist));
                    }
                    ArrayAdapter<SpinnerClass> spinnerAdapter = new ArrayAdapter<SpinnerClass>(getContext(),R.layout.spinner_item, itemList);
                    spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
                    spin_state.setAdapter(spinnerAdapter);
                } catch (Exception e) {e.getMessage();}
            }
        }
    }
    void clear() throws Exception
    {
        edtx_skill.setText("");
        edtx_titleofrequirment.setText("");
        edtx_description.setText("");
        ratingBar = (RatingBar)v.findViewById(R.id.ratingbar);
        Drawable drawable = ratingBar.getProgressDrawable();
        ratingBar.setRating(0);
        arrayskill.clear();
        arraylocation.clear();
        arrayeducation.clear();
        edtx_education.setText("");
        edtx_location.setText("");
        txv_date.setText("");
        spin_yearxperience.setSelection(0);
        spn_duration.setSelection(0);
        spin_monthxperience.setSelection(0);
        // drawable.setColorFilter(Color.parseColor("#83AAE5"), PorterDuff.Mode.SRC_ATOP);
    }
    class SendRequirmentask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("Title_Of_Requirment", titleofrequirment);
            params.put("skill",TextUtils.join(",", arrayskill));
            params.put("experience",expyear);
            params.put("dateofhiring",fromdateofhiring);
            params.put("location", TextUtils.join(",", arraylocation));
            params.put("skillrating",ratingvalue );
            params.put("duration",Durationselected);
            params.put("Education_Required",TextUtils.join(",", arrayeducation));
            params.put("Description",description);
            params.put("System_date",System_date);
            params.put("email_id", GlobalVariable.Employer_email);
            return requestHandler.sendPostRequest(URLs.URL_SendRequirment, params);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           ProgressD();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ss",s);
            p.dismiss();
            try {
                JSONObject obj = new JSONObject(s);
                if (!obj.getBoolean("error")) {
                    clear();
                    Toast.makeText(getContext(), "Your requirement send successfully we will get back to you as early as possible", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "error", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) { e.getMessage();}
        }
    }

    class RetriveSubCategory extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("categories_id", Countryid);
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
                    subCategories = obj.getString("subcategories").toString();
                    itemList.add(new SpinnerClass(subCategories_id, subCategories));
                }
                ArrayAdapter<SpinnerClass> spinnerAdapter = new ArrayAdapter<SpinnerClass>(getContext(), R.layout.spinner_item, itemList);
                spinnerAdapter.setDropDownViewResource( R.layout.spinner_item);
                spin_state.setAdapter(spinnerAdapter);
            } catch (Exception e) { e.getMessage();}
        }
    }
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
                Countryid=ii.getid();
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
        else if(spinner.getId() == R.id.edtx_education)
        {
            SpinnerClass ii = (SpinnerClass) parent.getSelectedItem();
            //  educationid=ii.getid();
            education=ii.getStr();
        }
        else if(spinner.getId() == R.id.spn_duration)
        {
           // FilterFreelancer ii = (FilterFreelancer) parent.getItemAtPosition(position);
            Durationselected = parent.getItemAtPosition(position).toString();
        }
        else if(spinner.getId() == R.id.spin_yearxperience)
        {
            expyear = parent.getItemAtPosition(position).toString();
            if(expyear.equalsIgnoreCase("Select_Year"))
            {
                expyear="0";
            }
        }
        else if(spinner.getId() == R.id.spin_monthxperience)
        {
            expmonth = parent.getItemAtPosition(position).toString();
            if(expmonth.equalsIgnoreCase("Select_Month"))
            {
                expmonth="0";
            }
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(getContext(),"Their nothing to show", Toast.LENGTH_LONG).show();
    }
}
