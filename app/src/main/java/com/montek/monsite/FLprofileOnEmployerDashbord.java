package com.montek.monsite;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.montek.monsite.Adapter.CalendarAdapter;
import com.montek.monsite.Fragments.Employer_HiredCandidatelistFragment;
import com.montek.monsite.Fragments.Employer_SearchFragment;
import com.montek.monsite.JsonConnection.RequestHandler;
import com.montek.monsite.extra.GlobalVariable;
import com.montek.monsite.extra.URLs;
import com.montek.monsite.model.CalendarCollection;
import org.json.*;
import java.text.*;
import java.util.*;
import static com.montek.monsite.ActivitySplash.coordinatorLayout;
import static com.montek.monsite.ActivitySplash.p;
import static com.montek.monsite.Activity_Login.ProgressD;
import static com.montek.monsite.Adapter.CalendarAdapter.*;
import static com.montek.monsite.Adapter.ListAdapterEmployerSearch.*;
import static com.montek.monsite.DisplayList_Freelancer.freelancer_id;
import static com.montek.monsite.Fragments.Employer_SearchFragment.*;
import static com.montek.monsite.model.CalendarCollection.*;
public class FLprofileOnEmployerDashbord extends AppCompatActivity implements View.OnClickListener {
    public static GregorianCalendar cal_month, cal_month_copy;
    @SuppressLint("StaticFieldLeak")
    public static CalendarAdapter cal_adapter;
    @SuppressLint("StaticFieldLeak")
    public static TextView txv_clear,tv_month,txv_selecteddate;
    RatingBar ratingBar,clientratingbar;
    public static Dialog dialog;
    String evf_id,endtime;
    ArrayAdapter monthexp = null;
    public static Calendar c;
    String[] totalmonthselect={ "Select Month","1", "2", "3","4","5","6","7","8","9","10","11","12"};
    String[] totalhourselect={"Select Hours","1","2","3", "4", "5","6","7","8","9","10","11","12"};
    public static long elapsedMonth,elapsedDays,elapsedHours;
    TextView txv_gst, txv_certification,txv_designation,txv_findustryexp,txv_fdomainexp,txv_fskill,txv_durationtxt,txv_enddate,txv_startdate,txv_duration,txv_totalpaid,edtx_startdate, edtx_enddate, statusreason, txv_rate, txv_ratedur, fdob, faddress, fcategories, fsubcategories, flocation, fname, femail, fcontact, fstatus, fsummary, fskill, findustryexp, fdomainexp, feducation, fexperience;
    public static int yearstart,yearend,monthstart,monthend,daystart,dayend;
    ImageView profile;
    LinearLayout  li_clientratingbar,HiredDetails,ratinglayout,layoutdatepicker, layoutlocation, reasonlayout, li_skill, buttonlayout, li_experience, li_industryexp, li_domainexp;
    public static String  gst="",datediaglog="",evfb_id="",employerviewedfreelancerid,totalduration,startdate="",enddate="", rate, gsttatalrate;
    String selectetotaldurationvalue,status_reason, employer_hirefreelancer, freelancer_address, freelancer_SubCategoires, freelancer_Categoires, gender, freelancer_password, freelancer_status, freelancer_location, freelancer_username, freelancer_education, freelancer_experience, freelancer_image, freelancer_email, freelancer_contact, freelancer_summary, freelancer_skill, freelancer_industryexp, freelancer_domainexp;
    Button btn_signup;
    Spinner spin_duration,spin_perduration;
    TextInputLayout spinnerinputlayout;
    public static Float totalpaidrate,totalextendedpaidrate;
    ScrollView scrollview;
    int previousSelectedPosition=-1;
    public static Multimap<String, String> multiMap = ArrayListMultimap.create();
    public static Multimap<String,String> map = ArrayListMultimap.create();
    String validationoftime="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_freelancer_profile);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Job Seeker Profile");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            iUI();
            p = new ProgressDialog(this);
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                freelancer_id = extras.getString("freelancer_id");
                if (hiredlist.equalsIgnoreCase("1")) {
                    String gstapplied = extras.getString("gstapplied");
                    String rateperduration = extras.getString("rateperduration");
                    String onwhichdurationhired = extras.getString("onwhichdurationhired");
                    String totalduration = extras.getString("totalduration");
                    String totalpaidrate = extras.getString("totalpaidrate");
                    startdate = extras.getString("startdate");
                    employerviewedfreelancerid = extras.getString("employerviewedfreelancerid");
                    evfb_id = extras.getString("evfb_id");
                    Log.d("totalduration", totalduration);
                    if(onwhichdurationhired.equalsIgnoreCase("Hourly")) {
                        String[] parts = startdate.split(" ");
                        txv_totalpaid.setText(totalpaidrate + "\n" +"Booked date "+parts[0]+" & time from " + parts[1] + " To " + parts[2]);
                    }
                    else {
                        txv_totalpaid.setText(totalpaidrate);
                    }
                    txv_duration.setText(totalduration);
                   // txv_ratedur.setText("hired on " + onwhichdurationhired + " basis " + rateperduration + " per rate ()");
                    txv_ratedur.setText("hired on " + onwhichdurationhired + " basis " + Float.toString(Float.parseFloat(rateperduration)+(1+(Float.parseFloat(gstapplied)/100))) + " per rate  (including GST "+gstapplied+"%)");
                    txv_rate.setVisibility(View.GONE);
                    spinnerinputlayout.setVisibility(View.GONE);
                    HiredDetails.setVisibility(View.VISIBLE);
                    rate = rateperduration;
                    Duration = onwhichdurationhired;
                    totalextendedpaidrate = Float.parseFloat(totalpaidrate);
                } else {
                    HiredDetails.setVisibility(View.GONE);
                    duration();//spinner adapter
                    spin_duration.setSelection(getIndex(spin_duration, Duration));
                    if (Duration.equalsIgnoreCase("Select Type")) {
                        ratinglayout.setVisibility(View.GONE);
                        txv_gst.setVisibility(View.GONE);
                    } else {
                        new employer_RequestRate().execute();
                    }
                }
                String freelancer_designation = extras.getString("freelancer_designation");
                String certification = extras.getString("certification");
                if (freelancer_designation.equalsIgnoreCase("")) {
                    freelancer_designation = "Not Mentioned";
                }
                if (certification.equalsIgnoreCase("")) {
                    certification = "Not Mentioned";
                }
                txv_designation.setText(freelancer_designation);
                txv_certification.setText(certification);
                freelancer_username = extras.getString("freelancer_username");
                fname.setText(freelancer_username);
                freelancer_email = extras.getString("freelancer_email");
                freelancer_contact = extras.getString("freelancer_contact");
                Log.d("freelancer_contact", freelancer_contact);
                freelancer_summary = extras.getString("freelancer_summary");
                fsummary.setText(freelancer_summary);
                Log.d("freelancer_summary", freelancer_summary);
                freelancer_skill = extras.getString("freelancer_skill");
                if (freelancer_skill.equalsIgnoreCase("")) {
                    freelancer_skill = "Not Mentioned";
                }else
                {
                    txv_fskill.setText(freelancer_skill);
                }

                freelancer_industryexp = extras.getString("freelancer_industryexp");
                if (freelancer_industryexp.equalsIgnoreCase("")) {
                    freelancer_industryexp = "Not Mentioned";
                }
                else
                {
                    txv_findustryexp.setText(freelancer_industryexp);
                }

                freelancer_domainexp = extras.getString("freelancer_domainexp");
                if (freelancer_domainexp.equalsIgnoreCase("")) {
                    freelancer_domainexp = "Not Mentioned";
                }
                else
                {
                    txv_fdomainexp.setText(freelancer_domainexp);
                }

                freelancer_education = extras.getString("freelancer_education");
                if (freelancer_education.equalsIgnoreCase("")) {
                    freelancer_education = "Not Mentioned";
                }
                feducation.setText(freelancer_education);
                freelancer_experience = extras.getString("freelancer_experience");
                if (freelancer_experience.equalsIgnoreCase("")) {
                    freelancer_experience = "Not Mentioned";
                }
                fexperience.setText(freelancer_experience);
                freelancer_image = extras.getString("freelancer_image");
                Log.d("freelancer_image", freelancer_image);
                Glide.with(getBaseContext()).load(freelancer_image)
                        .thumbnail(0.5f)
                        .crossFade()
                        .skipMemoryCache(true)
                        .error(R.drawable.profile)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(profile);
                freelancer_status = extras.getString("freelancer_status");
                fstatus.setText(freelancer_status);
                status_reason = extras.getString("status_reason");
                if (freelancer_status.equalsIgnoreCase("Not Available")) {
                    if (freelancer_status != null && "" != status_reason.intern()) {
                        statusreason.setText(status_reason);
                    } else {
                        statusreason.setText("not available");
                    }
                } else {
                    reasonlayout.setVisibility(View.GONE);
                }
                freelancer_location = extras.getString("freelancer_location");
                flocation.setText(freelancer_location);
                freelancer_address = extras.getString("freelancer_address");
                if (freelancer_address.equalsIgnoreCase("")) {
                    freelancer_address = "Not Mentioned";
                }
                faddress.setText(freelancer_address);
                gender = extras.getString("gender");
                if (gender.equalsIgnoreCase("") || gender.equalsIgnoreCase(null)) {
                    gender = "Not Mentioned";
                }
                fdob.setText(gender);
                freelancer_Categoires = extras.getString("freelancer_Categoires");
                fcategories.setText(freelancer_Categoires);
                freelancer_SubCategoires = extras.getString("freelancer_SubCategoires");
                fsubcategories.setText(freelancer_SubCategoires);
                employer_hirefreelancer = extras.getString("employer_hirefreelancer");
                String freelancer_rating = extras.getString("freelancer_rating");
                ratingBar.setRating(Float.parseFloat(freelancer_rating));
                if (freelancer_status.equalsIgnoreCase("Not Available") && hiredlist.equalsIgnoreCase("0")) {
                    buttonlayout.setVisibility(View.GONE);
                } else {
                    buttonlayout.setVisibility(View.VISIBLE);
                }
                if (hiredlist.equalsIgnoreCase("1")) {
                    btn_signup.setVisibility(View.GONE);
                }
                String clientrating = extras.getString("rating");
                Log.d("clientrating",clientrating);
                if (clientrating.equalsIgnoreCase("null") || clientrating.equalsIgnoreCase("") || clientrating.equalsIgnoreCase(null) ||clientrating.equals(null) || clientrating.isEmpty() ) {
                    li_clientratingbar.setVisibility(View.GONE);
                } else {
                    li_clientratingbar.setVisibility(View.VISIBLE);
                    clientratingbar.setRating(Float.parseFloat(clientrating));
                }
            }
            Button txv_calenderlink=(Button)findViewById(R.id.txv_calenderlink);
            txv_calenderlink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ActivityCalender();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            if(Employer_SearchFragment.hiredlist.equalsIgnoreCase("0")) {
                new RetrivebookedDTfreelancer().execute();
                new employerviewedfreelancerlist().execute();
            }
            else
            {
                new RetrivebookedDTofEmployee().execute();
            }
        }catch (Exception e){}
    }
    void duration()throws Exception
    {
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getBaseContext(), R.layout.spinner_itempfofile, Dlist);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_itempfofile);
        spin_duration.setAdapter(spinnerAdapter);
        spin_duration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1, int arg2, long arg3) {
                Duration= spin_duration.getSelectedItem().toString();
                if(Duration!="Select Type") {
                    spinnerinputlayout.setErrorEnabled(false);
                    btn_signup.setText("Hire");
                    txv_gst.setVisibility(View.GONE);
                    new employer_RequestRate().execute();
                }
                else
                {
                    ratinglayout.setVisibility(View.GONE);
                    txv_gst.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(hiredlist.equalsIgnoreCase("0")) {
                    Intent i = new Intent(getBaseContext(), DisplayList_Freelancer.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
                else
                {
                    DashBordActivity.fragment = new Employer_HiredCandidatelistFragment();
                    Intent i = new Intent(getBaseContext(), DashBordActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        if(hiredlist.equalsIgnoreCase("0")) {
            Intent i = new Intent(getBaseContext(), DisplayList_Freelancer.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        else
        {
            DashBordActivity.fragment = new Employer_HiredCandidatelistFragment();
            Intent i = new Intent(getBaseContext(), DashBordActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }
    void iUI() throws Exception
    {
        scrollview = ((ScrollView) findViewById(R.id.scrollView));
        coordinatorLayout = (LinearLayout) findViewById(R.id.parent_view);
        HiredDetails = (LinearLayout) findViewById(R.id.HiredDetails);
        txv_designation = (TextView) findViewById(R.id.txv_designation);
        txv_certification = (TextView) findViewById(R.id.txv_certification);
        txv_duration = (TextView) findViewById(R.id.txv_duration);
        txv_totalpaid = (TextView) findViewById(R.id.txv_totalpaid);
        ratinglayout=(LinearLayout)findViewById(R.id.ratinglayout);
        spinnerinputlayout=(TextInputLayout)findViewById(R.id.spinnerinputlayout);
        spinnerinputlayout.setVisibility(View.VISIBLE);
        li_skill=(LinearLayout)findViewById(R.id.li_skill);
        spin_duration=(Spinner)findViewById(R.id.spin_duartion);
        txv_gst=(TextView)findViewById(R.id.txv_gst);
        txv_gst.setVisibility(View.GONE);
        li_industryexp=(LinearLayout)findViewById(R.id.li_industryexp);
        li_domainexp=(LinearLayout)findViewById(R.id.li_domainexp);
        reasonlayout=(LinearLayout)findViewById(R.id.reasonlayout);
        buttonlayout=(LinearLayout) findViewById(R.id.buttonlayout);
        buttonlayout.setVisibility(View.GONE);
        profile=(ImageView)findViewById(R.id.img_profile);
        fname=(TextView)findViewById(R.id.txv_fname);
        fname=(TextView)findViewById(R.id.txv_fname);
        txv_ratedur=(TextView)findViewById(R.id.txv_ratedur);
        txv_rate=(TextView)findViewById(R.id.txv_rate);
        li_clientratingbar = (LinearLayout) findViewById(R.id.li_clientratingbar);
        clientratingbar=(RatingBar)findViewById(R.id.clientratingbar);
        clientratingbar.setNumStars(5);
        LayerDrawable stars = (LayerDrawable) clientratingbar.getProgressDrawable();
        stars.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        //stars.getDrawable(1).setColorFilter(Color.parseColor("#3F51B5"), PorterDuff.Mode.SRC_ATOP);
        flocation=(TextView)findViewById(R.id.txv_flocation);
        feducation=(TextView)findViewById(R.id.txv_feducation);
        fexperience=(TextView)findViewById(R.id.txv_fexperience);
        ratingBar=(RatingBar)findViewById(R.id.rb_ratingBar1);
        ratingBar.setNumStars(5);
        LayerDrawable stars1 = (LayerDrawable) ratingBar.getProgressDrawable();
        stars1.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        stars1.getDrawable(2).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        stars1.getDrawable(1).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        fstatus=(TextView)findViewById(R.id.txv_fstatus);
        fsummary=(TextView)findViewById(R.id.txv_fsummary);
        btn_signup=(Button)findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);
        btn_signup.setText("Hire ME");
        fdob=(TextView)findViewById(R.id.txv_dob);
        faddress=(TextView)findViewById(R.id.txv_address);
        fcategories=(TextView)findViewById(R.id.txv_categories);
        txv_findustryexp=(TextView)findViewById(R.id.txv_findustryexp);
        txv_fdomainexp=(TextView)findViewById(R.id.txv_fdomainexp);
        txv_fskill=(TextView)findViewById(R.id.txv_fskill);
        fsubcategories=(TextView)findViewById(R.id.txv_subcategories);
        fsubcategories=(TextView)findViewById(R.id.txv_subcategories);
        statusreason=(TextView)findViewById(R.id.txv_reason);
    }
    public void ActivityCalender() throws Exception {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_calenderdiglog);
        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        cal_adapter = new CalendarAdapter(this, cal_month, CalendarCollection.date_collection_arr, date_selected_arr);
        tv_month = (TextView)dialog. findViewById(R.id.tv_month);
        txv_selecteddate=(TextView)dialog. findViewById(R.id.txv_selecteddate);
        txv_clear=(TextView)dialog. findViewById(R.id.txv_clear);
        Button btn_ok= (Button)dialog.findViewById(R.id.btn_ok);
        btn_ok.setVisibility(View.GONE);
        if(hiredlist.equalsIgnoreCase("0")) {
            btn_ok.setVisibility(View.VISIBLE);
        }
        txv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalselectedays.clear();
                txv_selecteddate.setText(" ");
            }
        });
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
        ImageButton previous = (ImageButton)dialog.findViewById(R.id.ib_prev);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setPreviousMonth();
                    refreshCalendar();
                }catch (Exception e){}
            }
        });
        ImageButton next = (ImageButton)dialog. findViewById(R.id.Ib_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {  setNextMonth();
                    refreshCalendar();  }catch (Exception e){}
            }
        });
        final GridView gridview = (GridView)dialog.findViewById(R.id.gv_calendar);
        gridview.setAdapter(cal_adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                try{
                    ((CalendarAdapter) parent.getAdapter()).setSelected(v,position);
                    String selectedGridDate = CalendarAdapter.day_string.get(position);
                    if(CalendarCollection.date_collection_arr.size()==0) {
                        if (datediaglog.equalsIgnoreCase("1")) {
                            printDifference(curentDateString+" 00:00:00",selectedGridDate+" 00:00:00");
                            if(!CheckDates(selectedGridDate+" 00:00:00",curentDateString+" 00:00:00") && (elapsedDays>=1 ||  elapsedMonth>=1 || elapsedHours>=24))
                             {
                                    if(Duration.equalsIgnoreCase("Daily"))
                                    {
                                        startdate=edtx_startdate.getText().toString().trim();
                                        txv_clear.setVisibility(View.VISIBLE);
                                        txv_selecteddate.setVisibility(View.VISIBLE);
                                        totalselectedays.add(selectedGridDate.trim());
                                        txv_selecteddate.setText("You are selected date are: " + totalselectedays.toString().trim());
                                    }
                                    else {
                                        txv_clear.setVisibility(View.GONE);
                                        Log.d("datedate", selectedGridDate);
                                        startdate = selectedGridDate.trim();
                                        txv_selecteddate.setVisibility(View.VISIBLE);
                                        txv_selecteddate.setText("You are selected date is: " + selectedGridDate);
                                    }
                                }
                                else{toast("Candidates Will be available after 24hour's of current date.");
                                txv_selecteddate.setVisibility(View.GONE);}
                        } else if (datediaglog.equalsIgnoreCase("0"))
                        {
                            if(Duration.equalsIgnoreCase("Monthly") || Duration.equalsIgnoreCase("Daily") || Duration.equalsIgnoreCase("Hourly")) {
                                new android.app.AlertDialog.Builder(getBaseContext())
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("Date: " + selectedGridDate)
                                        .setMessage("You Cannot Change Enddate!!.")
                                        .setPositiveButton("OK", new OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                            }
                        }
                    }
                    String[] separatedTime = selectedGridDate.split("-");
                    String gridvalueString = separatedTime[2].replaceFirst("^0*","");
                    int gridvalue = Integer.parseInt(gridvalueString);
                    if ((gridvalue > 10) && (position < 8)) {
                        setPreviousMonth();
                        refreshCalendar();
                    } else if ((gridvalue < 7) && (position > 28)) {
                        setNextMonth();
                        refreshCalendar();
                    }
                    ((CalendarAdapter) parent.getAdapter()).setSelected(v,position);
                    ((CalendarAdapter) parent.getAdapter()).getPositionList(selectedGridDate, FLprofileOnEmployerDashbord.this);
                }catch (Exception e){}
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    enddate=edtx_enddate.getText().toString().trim();
                    if(Duration.equalsIgnoreCase("Daily"))
                    {   date_selected_arr.clear();
                        edtx_startdate.setText("Total days "+totalselectedays.size());
                        startdate=edtx_startdate.getText().toString().trim();
                        edtx_enddate.setText("Total days "+totalselectedays.size());
                        for (String s:totalselectedays) {
                            date_selected_arr.add(new CalendarCollection(s.trim(),"0"));
                        }
                    }
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    if (datediaglog.equalsIgnoreCase("1") && startdate != "" && startdate != null && !CheckDates(startdate+" 00:00:00", curentDateString+" 00:00:00")) {
                        if(Duration.equalsIgnoreCase("Hourly")|| Duration.equalsIgnoreCase("Monthly")){timestart();}
                        dialog.dismiss();
                    } else if (datediaglog.equalsIgnoreCase("0") && enddate != "" && enddate != null) {
                        dialog.dismiss();
                    } else {
                        toast("Please Select Proper Date");
                    }
                }catch (Exception e){}
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(RecyclerView.LayoutParams.FILL_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }
    protected void setNextMonth() throws Exception {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1),
                    cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }
    }
    protected void setPreviousMonth() throws Exception  {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1),
                    cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) - 1);
        }
    }
    public static void refreshCalendar() throws Exception  {
        cal_adapter.refreshDays();
        cal_adapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }
    public void Activitydatedialog() throws Exception {
        totalpaidrate= Float.valueOf("0.0");
        totalselectedays.clear();
        final Dialog dialog = new Dialog(FLprofileOnEmployerDashbord.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        dialog.setContentView(R.layout.activity_citydiaglog);
        layoutlocation=(LinearLayout)dialog.findViewById(R.id.layoutlocation);
        layoutlocation.setVisibility(View.GONE);
        layoutdatepicker=(LinearLayout)dialog.findViewById(R.id.layoutdatepicker);
        layoutdatepicker.setVisibility(View.VISIBLE);
        TextView txv_enddatetext = (TextView) dialog.findViewById(R.id.txv_enddatetext);
        if(Duration.equalsIgnoreCase("Daily"))
        {
            txv_enddatetext.setText("Selected Date");
        }
        else {
            txv_enddatetext.setText("End DateTime");
        }
        txv_durationtxt=(TextView)dialog.findViewById(R.id.txv_durationtxt);
        LinearLayout li_spinner = (LinearLayout) dialog.findViewById(R.id.li_spinner);
        spin_perduration=(Spinner)dialog.findViewById(R.id.spin_perduration);
        if(Duration.equalsIgnoreCase("Monthly")) {
            li_spinner.setVisibility(View.VISIBLE);
            txv_durationtxt.setText("No.of Months");
            spinner(totalmonthselect);
        }else  if(Duration.equalsIgnoreCase("Hourly")) {
            li_spinner.setVisibility(View.VISIBLE);
            txv_durationtxt.setText("No.of Hours");
            spinner(totalhourselect);
        }else {
            li_spinner.setVisibility(View.GONE);
        }
        edtx_enddate=(TextView)dialog.findViewById(R.id.txv_enddate);
        edtx_enddate.setOnClickListener(this);
        edtx_startdate=(TextView)dialog.findViewById(R.id.txv_startdate);
        edtx_startdate.setOnClickListener(this);
        final Button btn_ok = (Button)dialog. findViewById(R.id.btn_ok);
        btn_ok.setText("Hire");
        Button btn_cancel = (Button)dialog. findViewById(R.id.btn_cancel);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s="";
                startdate=edtx_startdate.getText().toString().trim();
                enddate=edtx_enddate.getText().toString().trim();
                if(enddate!=null) {
                    try {
                        if((Duration.equalsIgnoreCase("Monthly") ||Duration.equalsIgnoreCase("Hourly")) && !CheckDates(startdate,enddate))
                        {
                            toast("Please Check Start & End date !");
                        }
                        else{
                            dialog.dismiss();
                            if(Duration.equalsIgnoreCase("Hourly") || Duration.equalsIgnoreCase("Monthly")){printDifference(startdate,enddate);}
                             if(Duration.equalsIgnoreCase("Hourly")){
                                if(elapsedHours>=1 || elapsedDays>0 || elapsedMonth>0) {
                                    date_selected_arr.clear();
                                    //totalpaidrate = (Float.parseFloat(rate) * Float.parseFloat(String.valueOf(second))) / Float.parseFloat("3600");
                                    date_selected_arr.add(new CalendarCollection(startdate.trim()+" "+endtime,"0"));
                                   // date_selected_arr.add(new CalendarCollection(enddate, "0"));
                                    totalpaidrate= Float.parseFloat(selectetotaldurationvalue)*Float.parseFloat(gsttatalrate);
                                    Log.d("Calculate", String.valueOf(totalpaidrate));
                                }
                                else
                                {
                                    totalpaidrate= Float.valueOf("0");
                                    s="Please Select end datetime greater than 1hour";
                                }
                            }
                            else if(Duration.equalsIgnoreCase("Daily")){
                                    //totalpaidrate = (Float.parseFloat(rate) * Float.parseFloat(String.valueOf(second))) / Float.parseFloat("86400");
                                    totalpaidrate= Float.parseFloat(Integer.toString(totalselectedays.size()))*Float.parseFloat(gsttatalrate);
                                    Log.d("Calculate", String.valueOf(totalpaidrate));
                                    s="Please Select atleast one day";
                            }
                            else if(Duration.equalsIgnoreCase("Monthly")){
                                if(elapsedMonth+1>=1) {
                                    // totalpaidrate = (Float.parseFloat(rate) * Float.parseFloat(String.valueOf(second))) / Float.parseFloat("2592000");
                                    totalpaidrate= Float.parseFloat(selectetotaldurationvalue)*Float.parseFloat(gsttatalrate);
                                    Log.d("Calculate", String.valueOf(totalpaidrate));
                                }
                                else
                                {
                                    totalpaidrate= Float.valueOf("0");
                                    s="Please Select end datetime greater than one Month";
                                }
                            }
                        Log.d("Float.toString",Float.toString(totalpaidrate));
                        if(Float.toString(totalpaidrate).equalsIgnoreCase("0.0")) {
                            toast(s);
                        }else{
                            btn_signup.setText("Hire");
                            if(Duration.equalsIgnoreCase("Monthly")){
                                totalduration=selectetotaldurationvalue+"Month";
                                ActivityConfirmationdialog();
                            }
                            else if(Duration.equalsIgnoreCase("Hourly"))
                            {
                                totalduration=selectetotaldurationvalue+"Hours";
                                ActivityConfirmationdialog();
                            }
                            else if(Duration.equalsIgnoreCase("Daily")){
                                if(totalselectedays.size()>=30)
                                {
                                    toast("Please Select different duration type instead of daily");
                                }else {
                                    totalduration = totalselectedays.size() + "Days";
                                    ActivityConfirmationdialog();
                                }
                            }
                        }
                    } } catch (Exception e) {e.printStackTrace();}
                }
                else
                {
                    toast("Please Select Proper Start & End Date");
                }
            }
        });
        btn_cancel.setText("Cancel");
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }
    void spinner(String[] value)
    {
        monthexp = new ArrayAdapter(getBaseContext(), R.layout.spinner_item, value);
        monthexp.setDropDownViewResource(R.layout.spinner_item);
        spin_perduration.setAdapter(monthexp);
        spin_perduration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1, int arg2, long arg3) {
                selectetotaldurationvalue= spin_perduration.getSelectedItem().toString();
                edtx_startdate.setText("");
                edtx_enddate.setText("");
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }
    public static void printDifference(String startDate, String endDate) throws Exception {
        String outputPattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date  date1 = outputFormat.parse(startDate);
        Log.d("date1",date1.toString());
        Date date2 = outputFormat.parse(endDate);
        Log.d("date2",date2.toString());
        long different = date2.getTime() - date1.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long MonthInMilli = daysInMilli * 30;
        elapsedMonth = different / MonthInMilli;
        different = different % MonthInMilli;
        elapsedDays = different / daysInMilli;
        different = different % daysInMilli;
        elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;
        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;
       // long elapsedSeconds = different / secondsInMilli;
        String cal = elapsedMonth+"M "+elapsedDays + "d " + elapsedHours + "h " + elapsedMinutes + "m ";
        Log.d("cal",cal);
        if(String.valueOf(elapsedMonth).equalsIgnoreCase("0"))
        {
            totalduration="";
        }
        else
        {
            totalduration=elapsedMonth+"Months";
        }
        if(String.valueOf(elapsedDays).equalsIgnoreCase("0"))
        {
            totalduration=totalduration+"";
        }
        else
        {
            totalduration=elapsedDays+"days ";
        }

        if(String.valueOf(elapsedHours).equalsIgnoreCase("0"))
        {
            totalduration=totalduration+"";
        }
        else
        {
            totalduration=totalduration+elapsedHours+"hours ";
        }
        if(String.valueOf(elapsedMinutes).equalsIgnoreCase("0"))
        {
            totalduration=totalduration+"";
        }
        else
        {
            totalduration=totalduration+elapsedMinutes+"minutes ";
        }
        Log.d(" dayhourminuteseconds",totalduration);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {   case R.id.btn_signup:
            try {
                boolean isValid = true;
                if (Duration.equalsIgnoreCase("Select Type")) {
                    scrollview.post(new Runnable() {
                        public void run() {
                            scrollview.fullScroll(ScrollView.FOCUS_UP);
                        }
                    });
                    spinnerinputlayout.setError("Please Select Type");
                    toast("Please Select Duration Type");
                    isValid = false;
                } else {
                    spinnerinputlayout.setErrorEnabled(false);
                }
                if (isValid) {
                    if(txv_ratedur.getText().toString().equalsIgnoreCase("Rate Not Given by admin"))
                    {
                        toast("Please Try later,rate are not specified by admin");
                    }else {
                        Confirmation();
                    }
                }
            }catch (Exception e){}
            break;
            case R.id.txv_startdate:
                datediaglog="1";
                try {
                    if ((Duration.equalsIgnoreCase("Monthly") && selectetotaldurationvalue.equalsIgnoreCase("Select Month")) || (Duration.equalsIgnoreCase("Hourly")&& selectetotaldurationvalue.equalsIgnoreCase("Select Hours")))
                    {
                        totalselectedays.clear();
                        String s="";
                        if(selectetotaldurationvalue.equalsIgnoreCase("Select Month")){s="Month";}
                        else if(selectetotaldurationvalue.equalsIgnoreCase("Select Hours")){s="Hours";}
                        toast("Please Select No.Of "+s);
                    }else { ActivityCalender(); }
                } catch (Exception e) { e.printStackTrace(); }
                break;
            case R.id.txv_enddate:
                if(edtx_startdate.getText().toString()!="")
                {
                    datediaglog="0";
                    try {
                        ActivityCalender();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    toast("Please Select StartDate");
                }
                break;
        }
    }
    void timestart() throws Exception {
        validationoftime="0";
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(FLprofileOnEmployerDashbord.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                try {
                    edtx_startdate.setText(startdate+" "+selectedHour + ":" + selectedMinute+":00");
                    String s=edtx_startdate.getText().toString().trim();
                    Date d=new Date();
                    SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
                    printDifference(curentDateString+" "+sdf.format(d),s);
                    if(!CheckDates(s,curentDateString+" "+sdf.format(d)) && (elapsedDays>=1 ||  elapsedMonth>=1 || elapsedHours>=24))
                    {
                            String outputPattern = "yyyy-MM-dd HH:mm:ss";  //for difference
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                            Date date = outputFormat.parse(edtx_startdate.getText().toString().trim());
                            c = Calendar.getInstance();
                            c.setTime(date);
                            //To find last date from startdate
                            if (Duration.equalsIgnoreCase("Monthly"))
                            {
                                c.add(Calendar.MONTH, Integer.parseInt(selectetotaldurationvalue));
                                c.add(Calendar.DAY_OF_MONTH, -1);
                                for (int i = 0; i < CalendarCollection.date_collection_arr.size(); i++)
                                {
                                    Log.d("outputFormat.format", outputFormat.format(c.getTime()).toString());
                                    List<Date> dates = getDates(splitdate(edtx_startdate.getText().toString().trim()), splitdate(outputFormat.format(c.getTime())));
                                    Log.d("dateiiiiiiiadded", String.valueOf(dates.size()));
                                    for (int j = 0; j < dates.size(); j++)
                                    {
                                        Log.d("checked", String.valueOf(CalendarCollection.date_collection_arr.get(i).toString() + dateFormat.format((dates).get(j))));
                                        if (String.valueOf(CalendarCollection.date_collection_arr.get(i).toString()).equalsIgnoreCase((dateFormat.format((dates).get(j))))) {
                                            c.add(Calendar.DATE, 1);
                                            Log.d("added", String.valueOf(CalendarCollection.date_collection_arr.get(i).toString() + dateFormat.format((dates).get(j))));
                                            break;
                                        }
                                    }
                                }
                                date_selected_arr.clear();
                                edtx_enddate.setText(outputFormat.format(c.getTime()));
                                List<Date> datesnew = getDates(splitdate(edtx_startdate.getText().toString().trim()), splitdate(edtx_enddate.getText().toString().trim()));
                                for (Date date1 : datesnew) {
                                    Log.d("date", dateFormat.format(date1));
                                    date_selected_arr.add(new CalendarCollection(dateFormat.format(date1), "0"));
                                    Log.d("date", String.valueOf(date_selected_arr.size()));
                                }
                                date_selected_arr.removeAll(date_collection_arr);
                                Log.d("size", String.valueOf(date_selected_arr.size()));
                            }
                            else if (Duration.equalsIgnoreCase("Hourly"))
                            {
                                c.add(Calendar.HOUR, Integer.parseInt(selectetotaldurationvalue));
                                SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
                                String[] parts1 = String.valueOf(outputFormat.format(c.getTime())).split(" ");
                                Date caltime = parser.parse(parts1[1]);
                                Log.d("caltime", String.valueOf(caltime));

                                if(timearray!="" || !timearray.isEmpty() )
                                {
                                     String a = timearray.replace("[","");
                                     String  listString = a.replace("]","");
                                    List<String> myList = new ArrayList<String>(Arrays.asList(listString.split(", ")));
                                    for (int i = 0; i < myList.size(); i++) {
                                        Log.d("myList.size()", myList.get(i));
                                        String[] parts = myList.get(i).split(" ");
                                        Date t1 = parser.parse(parts[0]);
                                        Date t2 = parser.parse(parts[1]);
                                        Date userDate = parser.parse(selectedHour + ":" + selectedMinute + ":00");
                                        if (userDate.after(t1) && userDate.before(t2) || userDate.before(t1) && userDate.after(t2)) {
                                            validationoftime = "1";
                                        } else if (caltime.after(t1) && caltime.before(t2) || caltime.before(t1) && caltime.after(t2)) {
                                            validationoftime = "1";
                                        } else if (t1.after(userDate) && t1.before(caltime) || t1.after(userDate) && t1.before(caltime)) {
                                            validationoftime = "1";
                                        } else if (t2.after(userDate) && t2.before(caltime) || t2.after(userDate) && t2.before(caltime)) {
                                            validationoftime = "1";
                                        }
                                    }
                                }
                                Log.d("hiii","hiiii");
                                 if (validationoftime.equalsIgnoreCase("0")) {
                                    edtx_enddate.setText(outputFormat.format(c.getTime()));
                                    String[] parts = String.valueOf(outputFormat.format(c.getTime())).split(" ");
                                    endtime = parts[1];
                                    Log.d("endtime", endtime);
                                    date_selected_arr.add(new CalendarCollection(dateFormat.format(c.getTime()), "0"));
                                }
                                else { toast("Please Select different time !!");                        }
                        }
                    }
                    else
                    {
                       toast("Candidates Will be available after 24hour's of current date.");
                    }
                } catch (Exception e)
                {e.printStackTrace();}}
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
    public void Confirmation() throws Exception  {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Are you Sure want to hire ?");
        builder.setPositiveButton("YES", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Activitydatedialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NO", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.dismiss();
                try {
                    ConfirmationCall();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void ConfirmationCall() throws Exception  {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Request For conference call to admin?");
        builder.setPositiveButton("YES", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new employer_Requestconferencecall().execute();
            }
        });
        builder.setNegativeButton("NO", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    class RetrivebookedDTfreelancer extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("freelancer_id", freelancer_id);
            return requestHandler.sendPostRequest(URLs.URL_EmpVFLprofileOperation+"RetrivebookedDTfreelancer", params);
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
            Log.d("pp",s);
            CalendarCollection.date_collection_arr.clear();
            date_selected_arr.clear();
            multiMap.clear();
            map.clear();
            if(s.contains("No Results Found"))
            {  }
            else {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject obj;
                    String bookeddates="",hourdatetime="";
                    for (int j = 0; j < jsonArray.length(); j++) {
                        obj = jsonArray.getJSONObject(j);
                        String onwhichdurationhired = obj.getString("onwhichdurationhired");
                        if (onwhichdurationhired.equalsIgnoreCase("Hourly")) {
                            if (j == jsonArray.length() - 1) {
                                hourdatetime = hourdatetime + obj.getString("bookeddates");
                            } else {
                                hourdatetime = hourdatetime + obj.getString("bookeddates") + ", ";
                            }
                        } else {
                            if (j == jsonArray.length() - 1) {
                                bookeddates = bookeddates + obj.getString("bookeddates");
                            } else {
                                bookeddates = bookeddates + obj.getString("bookeddates") + ", ";
                            }
                        }
                    }
                    List<String> myList1 = new ArrayList<String>(Arrays.asList(bookeddates.split(", ")));
                    Log.d("myList.size()", String.valueOf(myList1.size()));
                    for(int i=0;i<myList1.size();i++) {
                        Log.d("myList.size(iiii)", myList1.get(i));
                        CalendarCollection.date_collection_arr.add(new CalendarCollection(myList1.get(i),"0"));
                    }
                    List<String> myList = new ArrayList<String>(Arrays.asList(hourdatetime.split(", ")));
                    Log.d("myList.size", String.valueOf(myList.size()));
                    for(int i=0;i<myList.size();i++) {
                        //   Log.d("myList.size(iiii)", splitdatetime(myList.get(i)));
                        splitdatetime(myList.get(i));
                    }
//                    List<CalendarCollection> uniques = new ArrayList<CalendarCollection>();
//                    for (CalendarCollection element : date_collection_arr) {
//                        if (!uniques.contains(element)) {
//                            uniques.add(element);
//                        }
//                    }
                    //                        List<Date> dates = getDates(startdate,enddate);
//                        for(Date date:dates){
//                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                            Log.d("date",dateFormat.format(date));
//
//                        }
//                    CalendarCollection.date_collection_arr.size(
//                    for(int i=0;i<CalendarCollection.date_collection_arr.size();i++) {
//                        multiMap.put(date_collection_arr.get(i).date,date_collection_arr.get(i).event_message);
//                        Log.d("date_collection_arr.get",date_collection_arr.get(i).date.toString());
//                    }

//                    // Getting values
//                    Collection<String> fruits = myMultimap.get(" Fruits");
//                    System.out.println(fruits); // [Bannana, Apple, Pear]
//                    Collection<String> fruits1 = multiMap.get("2018-05-05");
//                    System.out.println(fruits1);
//                    Log.d("fruits",fruits1.toString());
                } catch (Exception e) {  e.getMessage();}
            }
        }
    }

    class RetrivebookedDTofEmployee extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("evfb_id", evfb_id);
            return requestHandler.sendPostRequest(URLs.URL_EmpVFLprofileOperation+"RetrivebookedDTofEmployee", params);
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
            Log.d("ppppppp",s);
            CalendarCollection.date_collection_arr.clear();
            date_selected_arr.clear();
            multiMap.clear();
            map.clear();
            if(s.contains("No Results Found"))
            {  }
            else {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject obj;
                    String bookeddates="",hourdatetime="";
                    for (int j = 0; j < jsonArray.length(); j++) {
                        obj = jsonArray.getJSONObject(j);
                        String onwhichdurationhired = obj.getString("onwhichdurationhired");
                        if (onwhichdurationhired.equalsIgnoreCase("Hourly")) {
                            if (j == jsonArray.length() - 1) {
                                hourdatetime = hourdatetime + obj.getString("bookeddates");
                            } else {
                                hourdatetime = hourdatetime + obj.getString("bookeddates") + ", ";
                            }
                        } else {
                            if (j == jsonArray.length() - 1) {
                                bookeddates = bookeddates + obj.getString("bookeddates");
                            } else {
                                bookeddates = bookeddates + obj.getString("bookeddates") + ", ";
                            }
                        }
                    }
                    List<String> myList1 = new ArrayList<String>(Arrays.asList(bookeddates.split(", ")));
                    Log.d("myList.size()", String.valueOf(myList1.size()));
                    for(int i=0;i<myList1.size();i++) {
                        Log.d("myList.size(iiii)", myList1.get(i));
                        CalendarCollection.date_collection_arr.add(new CalendarCollection(myList1.get(i),"0"));
                    }
                    List<String> myList = new ArrayList<String>(Arrays.asList(hourdatetime.split(", ")));
                    Log.d("myList.size", String.valueOf(myList.size()));
                    for(int i=0;i<myList.size();i++) {
                        //   Log.d("myList.size(iiii)", splitdatetime(myList.get(i)));
                        splitdatetime(myList.get(i));
                    }
                } catch (Exception e) {  e.getMessage();}
            }
        }
    }
    String splitdate(String date) throws Exception
    {
        String[] parts = date.split(" ");
        return parts[0];
    }
    void splitdatetime(String date) throws Exception
    {
        String[] parts = date.split(" ");
        String hourlydates = parts[0];
        String event="From "+parts[1]+" To "+parts[2];
        multiMap.put(hourlydates,event);
        map.put(hourlydates,parts[1].toString()+" "+parts[2].toString());
        CalendarCollection.date_collection_arr.add(new CalendarCollection(hourlydates,event));
    }
    private static List<Date> getDates(String dateString1, String dateString2) throws Exception
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }
    class employerviewedfreelancerlist extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("freelancer_id", freelancer_id);
            params.put("email", GlobalVariable.Employer_email);
            Log.d("url", URLs.URL_EmployerviewedFLprofileinsertion) ;
            return requestHandler.sendPostRequest(URLs.URL_EmpVFLprofileOperation+"viewedprofileinsertion", params);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressD();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("sss",s);
            p.dismiss();
            try {
                JSONArray jsonArray = new JSONArray(s);
                JSONObject obj;
                for (int j = 0; j < jsonArray.length(); j++) {
                    obj = jsonArray.getJSONObject(j);
                    //    String rateid = obj.getString("id");
                    evf_id = obj.getString("id");
                }
            } catch (Exception e) { e.getMessage();}
        }
    }
    class employer_Requestconferencecall extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("freelancer_id", freelancer_id);
            params.put("email", GlobalVariable.Employer_email);
            return requestHandler.sendPostRequest(URLs.URL_EmployerviewedFLprofileinsertion+"RequestforConfirenceCall", params);
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
                    toast(obj.getString("message"));
                }
                else {
                    Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) { e.getMessage();}
        }
    }
    class employer_RequestRate extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("freelancer_id", freelancer_id);
            params.put("duration",Duration );
            return requestHandler.sendPostRequest(URLs.URL_Retrivealltablevalue+"RetriveRate", params);
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
            if(s.contains("No Results Found"))
            {
                ratinglayout.setVisibility(View.VISIBLE);
                txv_ratedur.setText("Rate Not Given by admin");
                txv_rate.setVisibility(View.GONE);
                txv_gst.setVisibility(View.GONE);
            }
            else {
                try {
                    ratinglayout.setVisibility(View.VISIBLE);
                    txv_rate.setVisibility(View.VISIBLE);
                    txv_gst.setVisibility(View.VISIBLE);
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject obj;
                    for (int j = 0; j < jsonArray.length(); j++) {
                        obj = jsonArray.getJSONObject(j);
                        //    String rateid = obj.getString("id");
                        rate = obj.getString("rate");
                        String duration = obj.getString("duration");
                        gst = obj.getString("gst");
                        gsttatalrate=Float.toString(Float.parseFloat(rate)+(1+(Float.parseFloat(gst)/100)));
                        txv_ratedur.setText("Rate by "+duration);
                        txv_rate.setText(gsttatalrate);
                        txv_gst.setText("(including GST "+gst+"%)");
                    }
                } catch (Exception e) {  e.getMessage();}
            }
        }
    }
    public void ActivityConfirmationdialog() throws Exception {
        TextView txv_HiredOn,txv_hiredrate,txv_totalpayment,txv_email,txv_phone,txv_startdate,txv_enddate;
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_confirmationbooked);
        txv_totalpayment=(TextView)dialog.findViewById(R.id.txv_totalpayment);
        TextView txv_lablestartdate = (TextView) dialog.findViewById(R.id.txv_lablestartdate);
        LinearLayout li_enddate = (LinearLayout) dialog.findViewById(R.id.li_enddate);
        txv_email=(TextView)dialog.findViewById(R.id.txv_email);
        txv_startdate=(TextView)dialog.findViewById(R.id.txv_startdate);
        txv_enddate=(TextView)dialog.findViewById(R.id.txv_enddate);
        txv_phone=(TextView)dialog.findViewById(R.id.txv_phone);
        txv_HiredOn=(TextView)dialog.findViewById(R.id.txv_HiredOn);
        txv_hiredrate=(TextView)dialog.findViewById(R.id.txv_hiredrate);
        Button btn_ok = (Button)dialog. findViewById(R.id.btn_paynow);
        Log.d("totalpaidrate",String.valueOf(totalpaidrate));
        txv_totalpayment.setText(String.valueOf(totalpaidrate));
        txv_email.setText(GlobalVariable.Employer_email);
        if(Duration.equalsIgnoreCase("Monthly") || Duration.equalsIgnoreCase("Daily"))
        {
            txv_startdate.setText(Integer.toString(date_selected_arr.size()));
            txv_lablestartdate.setText("Selected Total Days");
            li_enddate.setVisibility(View.GONE);
        }
        else {
            li_enddate.setVisibility(View.VISIBLE);
            txv_enddate.setText(enddate);
            txv_startdate.setText(startdate);
        }
        txv_phone.setText(GlobalVariable.Employer_mobile);
        txv_HiredOn.setText(Duration);
        txv_hiredrate.setText(gsttatalrate +" ( including GST "+ gst+"%)");
        btn_ok.setText("Hire");
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new employer_hirefreelancer().execute();
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(RecyclerView.LayoutParams.FILL_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    class employer_hirefreelancer extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("freelancer_id", freelancer_id);
            params.put("email", GlobalVariable.Employer_email);
            params.put("onwhichdurationhired", Duration);
            params.put("rateperduration", rate);
            params.put("bookeddates",date_selected_arr.toString());
            params.put("gstapplied", gst);
            params.put("evf_id", evf_id);
            params.put("paymentId", "0");
            params.put("totalpaidrate",String.valueOf(totalpaidrate));
            params.put("totalduration",totalduration);
            Log.d("params",params.toString());
            return requestHandler.sendPostRequest(URLs.URL_EmpVFLprofileOperation+"HiredFreelancer", params);
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
            Log.d("pppp",s);
            try {
                Log.d("Json response:", s.toString());
                JSONObject obj = new JSONObject(s);
                if (!obj.getBoolean("error")) {
                    toast("Candidate Hired Request Sent Successfully ");
                    DashBordActivity.fragment = new Employer_HiredCandidatelistFragment();
                    Intent i = new Intent(getBaseContext(), DashBordActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                    date_selected_arr.clear();
                }
                else {
                    toast("error");
                }
            } catch (Exception e) { e.getMessage();}
        }
    }

    void toast(String s)
    {
        Toast toast= Toast.makeText(getBaseContext(),s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
}
