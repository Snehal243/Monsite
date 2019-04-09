package com.montek.monsite.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.montek.monsite.FLprofileOnEmployerDashbord;
import com.montek.monsite.Fragments.Employer_HiredCandidatelistFragment;
import com.montek.monsite.JsonConnection.RequestHandler;
import com.montek.monsite.PayMentGateWay;
import com.montek.monsite.R;
import com.montek.monsite.extra.GlobalVariable;
import com.montek.monsite.extra.URLs;
import com.montek.monsite.model.Employer;
import com.montek.monsite.model.FilterFreelancer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.montek.monsite.ActivitySplash.p;
import static com.montek.monsite.Activity_Login.ProgressD;
import static com.montek.monsite.DashBordActivity.frag;
import static com.montek.monsite.Employer_EditProfile.jsonArray;
import static com.montek.monsite.FLprofileOnEmployerDashbord.dialog;
import static com.montek.monsite.Fragments.Employer_SearchFragment.Dlist;
import static com.montek.monsite.Fragments.Employer_SearchFragment.hiredlist;
public class ListAdapterEmployerSearch extends ArrayAdapter<Employer>  {
    public ArrayList<Employer> MainList;
    public ArrayList<Employer> EmployerListTemp;
    public static int feedbacklist=0;
    public ListAdapterEmployerSearch.EmployerDataFilter employerDataFilter ;
    String hiredfreelancer_id,id,Feedback,ratingvalue;
    int disable=0;
    ListView ListView;
    LinearLayout no_item;
    public static String Duration;
    Context mcon;
    List<String> myList = null;
    public ListAdapterEmployerSearch(Context context, int id, ArrayList<Employer> employertArrayList) {
        super(context, id, employertArrayList);
        mcon=context;
        this.EmployerListTemp = new ArrayList<Employer>();
        this.EmployerListTemp.addAll(employertArrayList);
        this.MainList = new ArrayList<Employer>();
        this.MainList.addAll(employertArrayList);
    }
    @NonNull
    @Override
    public Filter getFilter() {
        if (employerDataFilter == null){
            employerDataFilter  = new ListAdapterEmployerSearch.EmployerDataFilter();
        }
        return employerDataFilter;
    }
    public class ViewHolder {
        TextView txv_taptoopen,txv_Action,txv_payment,txv_client,txv_duration,txv_totalpaid,txv_feedback,btn_delete,txv_applied,posted,experience,education,status,Name,skill,contact,location;
        RatingBar ratingbar,clientratingbar;
        Spinner spin_duration;
        LinearLayout li_payment,li_Action,feedback,feedbacklayout,viewdlayout,li_duration;
    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        ListAdapterEmployerSearch.ViewHolder holder;
        if (convertView == null) {
           LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView = vi.inflate(R.layout.customlayout_freelancerlist, null);
            holder = new ListAdapterEmployerSearch.ViewHolder();
            p = new ProgressDialog(getContext());
            holder.Name = (TextView) convertView.findViewById(R.id.txv_name);
            holder.ratingbar = (RatingBar) convertView.findViewById(R.id.ratingbar);
            holder.skill = (TextView) convertView.findViewById(R.id.txv_skill);
            holder.status = (TextView) convertView.findViewById(R.id.txv_status);
            holder.contact = (TextView) convertView.findViewById(R.id.txv_contact);
            holder.location = (TextView) convertView.findViewById(R.id.txv_location);
            holder.education = (TextView) convertView.findViewById(R.id.txv_education);
            holder.experience = (TextView) convertView.findViewById(R.id.txv_experience);
            holder.clientratingbar = (RatingBar) convertView.findViewById(R.id.clientratingbar);
            holder.posted = (TextView) convertView.findViewById(R.id.txv_posted);
            holder.txv_applied = (TextView) convertView.findViewById(R.id.txv_appiled);
            holder.btn_delete = (Button) convertView.findViewById(R.id.btn_delete);
            holder.spin_duration = (Spinner) convertView.findViewById(R.id.spin_duartion);
            holder.txv_feedback = (TextView) convertView.findViewById(R.id.txv_feedback);
            holder.feedbacklayout = (LinearLayout) convertView.findViewById(R.id.feedbacklayout);
            holder.viewdlayout = (LinearLayout) convertView.findViewById(R.id.viewdlayout);
            holder.txv_totalpaid = (TextView) convertView.findViewById(R.id.txv_totalpaid);
            holder.txv_duration = (TextView) convertView.findViewById(R.id.txv_duration);
            holder.li_duration = (LinearLayout) convertView.findViewById(R.id.li_duration);
            holder.txv_client = (TextView) convertView.findViewById(R.id.txv_status11);
            holder.txv_feedback = (TextView) convertView.findViewById(R.id.txv_feedback);
            holder.feedback = (LinearLayout) convertView.findViewById(R.id.feedback);
            holder.li_Action = (LinearLayout) convertView.findViewById(R.id.li_Action);
            holder.txv_Action = (TextView) convertView.findViewById(R.id.txv_Action);
            holder.txv_payment = (TextView) convertView.findViewById(R.id.txv_payment);
            holder.txv_taptoopen = (TextView) convertView.findViewById(R.id.txv_taptoopen);
            holder.li_payment = (LinearLayout) convertView.findViewById(R.id.li_payment);
            LayerDrawable stars1 = (LayerDrawable) holder.ratingbar.getProgressDrawable();
            stars1.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            stars1.getDrawable(2).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
            stars1.getDrawable(1).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
            LayerDrawable stars = (LayerDrawable) holder.clientratingbar.getProgressDrawable();
            stars.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(2).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
            if (hiredlist.equalsIgnoreCase("0")) {
                ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(), R.layout.spinner_item, Dlist);
                // Drop down layout style - list view with radio button
                spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
                // attaching data adapter to spinner
                holder.spin_duration.setAdapter(spinnerAdapter);
                holder.li_Action.setVisibility(View.GONE);
            }
            convertView.setTag(holder);
        } else {
            holder = (ListAdapterEmployerSearch.ViewHolder) convertView.getTag();
        }
        final Employer employer = EmployerListTemp.get(position);
        if (hiredlist.equalsIgnoreCase("1")) {
            holder.txv_payment.setText("PayNow");
            holder.btn_delete.setVisibility(View.GONE);
            holder.li_Action.setVisibility(View.VISIBLE);
            if( employer.getapprovestatus().equalsIgnoreCase("0")) {
                holder.txv_Action.setText("Status : Pending");
                holder.li_payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Toast.makeText(getContext(),"After Admin Approvals, you can pay",Toast.LENGTH_LONG).show();
                    }
                });
            }
            else if( employer.getapprovestatus().equalsIgnoreCase("1"))
            {
                holder.txv_Action.setText("Status : Approved");
                if( employer.getpaymentstatus().equalsIgnoreCase("not paid"))
                {
                    holder.li_payment.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            try {
                                ActivityConfirmationdialog(employer.getevfb_id(),employer.getfreelancer_id(),employer.gettotalpaidrate(),employer.gettotalduration(),employer.getrateperduration(),employer.getonwhichdurationhired(),employer.getgstapplied());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else if(employer.getpaymentstatus().equalsIgnoreCase("paid"))
                {
                    holder.txv_payment.setText("Payment Paid");
                    Log.d("employer.get)",employer.getpaymentstatus());
                }
            }
            else  if( employer.getapprovestatus().equalsIgnoreCase("2"))
            {
                holder.txv_Action.setText("Status : Cancel");
                holder.li_payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"Sorry, this candidate has cancelled for more information check your mail",Toast.LENGTH_LONG).show();
                    }
                });
            }
            Log.d("startdate",employer.getstartdate());
            holder.feedbacklayout.setVisibility(View.VISIBLE);
            holder.viewdlayout.setVisibility(View.GONE);
            holder.spin_duration.setVisibility(View.GONE);
            holder.txv_totalpaid.setVisibility(View.VISIBLE);
            holder.txv_duration.setVisibility(View.VISIBLE);
            holder.li_duration.setVisibility(View.VISIBLE);
            holder.txv_totalpaid.setText(employer.gettotalpaidrate());
            holder.txv_duration.setText(employer.gettotalduration());
        } else {
            holder.btn_delete.setVisibility(View.GONE);
            holder.spin_duration.setVisibility(View.VISIBLE);
            holder.txv_totalpaid.setVisibility(View.GONE);
            holder.txv_duration.setVisibility(View.GONE);
            holder.li_duration.setVisibility(View.GONE);
        }
        holder.Name.setText(employer.getusername());
        if (employer.getskill().endsWith(",")) {
            holder.skill.setText(employer.getskill().substring(0, employer.getskill().length() - 1));
        } else {
            holder.skill.setText(employer.getskill());
        }
        holder.status.setText(employer.getstatus());
        holder.contact.setText(employer.getcontact());
        holder.location.setText(employer.getlocation());
        if (employer.getexperience().equalsIgnoreCase("")) {
            holder.experience.setText("Not Mention");
        } else {
            holder.experience.setText(employer.getexperience());
        }
        holder.education.setText(employer.geteducation());
        if (employer.getclientrating().equalsIgnoreCase("null")) {
           holder.txv_taptoopen.setVisibility(View.GONE);
           // holder.clientratingbar.setRating(1);
            holder.feedback.setVisibility(View.GONE);
        } else {
            holder.clientratingbar.setRating(Float.parseFloat(employer.getclientrating()));
            holder.feedback.setVisibility(View.VISIBLE);
            holder.txv_taptoopen.setVisibility(View.VISIBLE);
        }
        holder.clientratingbar.setIsIndicator(true);
        if (employer.getfreelancer_rating().equalsIgnoreCase("")) {
            holder.ratingbar.setRating(0);
        } else {
            holder.ratingbar.setRating(Float.parseFloat(employer.getfreelancer_rating()));
        }
        holder.ratingbar.setIsIndicator(true);
        if (employer.getemployer_email().equalsIgnoreCase(GlobalVariable.Employer_email)) {
            holder.posted.setText("Viewed");
        } else {
            holder.posted.setText(employer.getSystem_date());
        }
        if (employer.getemployer_hirefreelancer() != null) {
            if (employer.getemployer_hirefreelancer().equalsIgnoreCase("1")) {
                holder.txv_applied.setText("Hired");
                holder.txv_applied.setVisibility(View.VISIBLE);
            }
        }
        //  holder.spin_duration.setAdapter(spinnerAdapter);
        //spinner
        final ViewHolder finalHolder = holder;
        holder.spin_duration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1, int arg2, long arg3) {
                Duration = finalHolder.spin_duration.getSelectedItem().toString();
                if(Duration!="Select Type")
                {
                    Intent i = new Intent(mcon, FLprofileOnEmployerDashbord.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("freelancer_id",employer.getfreelancer_id());
                    i.putExtra("freelancer_username", employer.getusername());
                    i.putExtra("freelancer_email", employer.getemail());
                    i.putExtra("freelancer_contact", employer.getcontact());
                    i.putExtra("freelancer_summary", employer.getsummary());
                    i.putExtra("freelancer_skill", employer.getskill());
                    i.putExtra("freelancer_industryexp", employer.getindustryexp());
                    i.putExtra("freelancer_domainexp", employer.getdomainexp());
                    i.putExtra("freelancer_education", employer.geteducation());
                    i.putExtra("freelancer_experience", employer.getexperience());
                    i.putExtra("freelancer_image", employer.getimage());
                    i.putExtra("freelancer_status", employer.getstatus());
                    i.putExtra("freelancer_location", employer.getlocation());
                    i.putExtra("freelancer_Categoires", employer.getfreelancer_Categoires());
                    i.putExtra("freelancer_SubCategoires", employer.getfreelancer_SubCategoires());
                    i.putExtra("gender", employer.getgender());
                    i.putExtra("freelancer_address", employer.getfreelancer_address());
                    i.putExtra("System_date", employer.getSystem_date());
                    i.putExtra("employer_id", employer.getemployer_id());
                    i.putExtra("employer_email", employer.getemployer_email());
                    i.putExtra("employer_hirefreelancer", employer.getemployer_hirefreelancer());
                    i.putExtra("freelancer_rating", employer.getfreelancer_rating());
                    i.putExtra("status_reason", employer.getStatus_reason());
                    i.putExtra("rating", employer.getclientrating());
                    i.putExtra("certification", employer.getcertification());
                    i.putExtra("freelancer_designation", employer.getfreelancer_designation());
                    mcon.startActivity(i);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        final ViewHolder finalHolder2 = holder;
        holder.txv_taptoopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(employer.getclientrating().equalsIgnoreCase("null"))
                {
                    finalHolder2.txv_taptoopen.setVisibility(View.GONE);
                }
                else {
                    finalHolder2.txv_taptoopen.setVisibility(View.VISIBLE);
                    id = employer.getfreelancer_id();
                    //DiaglogBox
                    Log.d("hhii", "jjddd");
                    final Dialog dialog = new Dialog(view.getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                    dialog.setContentView(R.layout.listview);
                    TextView txv_title = (TextView) dialog.findViewById(R.id.txv_title);
                    txv_title.setVisibility(View.VISIBLE);
                    ((View) dialog.findViewById(R.id.lin1e)).setVisibility(View.VISIBLE);
                    ListView = (ListView) dialog.findViewById(R.id.listView1);
                    no_item = (LinearLayout) dialog.findViewById(R.id.no_item);
                    Button btn_submit = (Button) dialog.findViewById(R.id.btn_send);
                    btn_submit.setText("OK");
                    btn_submit.setVisibility(View.VISIBLE);
                    //  drawable.setColorFilter(Color.parseColor("#303F9F"), PorterDuff.Mode.SRC_ATOP);
//                    ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            FilterFreelancer ListViewClickData = (FilterFreelancer) parent.getItemAtPosition(position);
//                            // Toast.makeText(getBaseContext(), ListViewClickData.getcategoires(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    btn_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    new RetriveFeedback().execute();
                    dialog.show();
                }
            }
        });
        //final ViewHolder finalHolder1 = holder;
        holder.txv_feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(employer.getonwhichdurationhired()!=("Hourly")) {
                        myList = new ArrayList<String>(Arrays.asList(employer.getstartdate().split(", ")));
                        Log.d("myList.size();", String.valueOf(myList.size()));
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String currentDate = df.format(c.getTime());
                        Log.d("currentDate", currentDate + " " + myList.get(myList.size()-1));//need to later
                        try {
                            if (CheckDatesvalidation(myList.get(myList.size()-1), currentDate)) {
                                disable=0;
                            } else {
                                disable=1;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (disable==1 ) {
                        Toast toast = Toast.makeText(getContext(), "Give Feedback After Specified Date and time Over.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else if ((disable==0||disable==1) && employer.getapprovestatus().equalsIgnoreCase("2")) {
                        Toast toast = Toast.makeText(getContext(), "Sorry, This Transaction is cancelled", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    else  if (disable==0 && employer.getpaymentstatus().equalsIgnoreCase("paid"))
                    {
                        hiredfreelancer_id = employer.getfreelancer_id();
                        //DiaglogBox
                        final Dialog dialog = new Dialog(view.getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.acitivity_feedback);
                        // dialog.setTitle("Check availability and add to cart");
                        Button btn_send = (Button) dialog.findViewById(R.id.btn_send);
                        btn_send.setOnClickListener(this);
                        final TextView txv_length = (TextView) dialog.findViewById(R.id.txv_length);
                        final EditText edtx_feedback = (EditText) dialog.findViewById(R.id.edtx_feedback);
                        final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.rb_ratingBar1);
                        Drawable drawable = ratingBar.getProgressDrawable();
                        //  drawable.setColorFilter(Color.parseColor("#303F9F"), PorterDuff.Mode.SRC_ATOP);
                        btn_send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Feedback = edtx_feedback.getText().toString().trim();
                                if (TextUtils.isEmpty(Feedback)) {
                                    edtx_feedback.setError("Please enter Feedback");
                                    edtx_feedback.requestFocus();
                                    return;
                                }
                                ratingvalue = String.valueOf(ratingBar.getRating());
                                if (ratingvalue.equalsIgnoreCase("")) {
                                    ratingvalue = "0";
                                }
                                new Sendfeedback().execute();
                                dialog.dismiss();
                            }
                        });
                        //RatingBar Listener
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
                        //Edittext Listener
                        edtx_feedback.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void afterTextChanged(Editable s) {
                                txv_length.setText(String.valueOf(300 - edtx_feedback.length()) + "/300");
                            }
                            @Override
                            public void onTextChanged(CharSequence s, int st, int b, int c) {
                            }
                            @Override
                            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
                            }
                        });
                        dialog.show();
                        Window window = dialog.getWindow();
                        if (window != null)
                            window.setLayout(RecyclerView.LayoutParams.FILL_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
                    }
                }
            });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setMessage("Do you want to Remove this Hired Candidate?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user pressed "yes", then he is allowed to exit from application
                        // remove the item from the data list
                        id=employer.getfreelancer_id();
                        // Toast.makeText(getContext(),OpeningsListTemp.get(position).getid(),Toast.LENGTH_LONG).show();
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
        return convertView;
    }

    public static boolean CheckDatesvalidation(String startDate,String endDate) throws Exception {
        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
        boolean b = false;
        try {
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                b = true;//If start date is before end date
                Log.d("bbb","true");
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                b = false;//If two dates are equal
                Log.d("bbb","trueequals");
            } else {
                Log.d("bbb","false");
                b = false; //If start date is after the end date
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }
    public static boolean CheckDates(String startDate,String endDate) throws Exception {
        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean b = false;
        try {
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                b = true;//If start date is before end date
                Log.d("bbb","true");
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                b = false;//If two dates are equal
                Log.d("bbb","trueequals");
            } else {
                Log.d("bbb","false");
                b = false; //If start date is after the end date
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }
    private class EmployerDataFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            charSequence = charSequence.toString().toLowerCase();
            FilterResults filterResults = new FilterResults();
            if(charSequence != null && charSequence.toString().length() > 0)
            {
                ArrayList<Employer> arrayList1 = new ArrayList<Employer>();
                for(int i = 0, l = MainList.size(); i < l; i++)
                {
                    Employer subject = MainList.get(i);
                    if(subject.toString().toLowerCase().contains(charSequence))
                        arrayList1.add(subject);
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
            EmployerListTemp = (ArrayList<Employer>)filterResults.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = EmployerListTemp.size(); i < l; i++)
                add(EmployerListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }

    class DeletePost extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();
            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("id", id);
            params.put("email", GlobalVariable.Employer_email);
            Log.d("param updated data:",params.toString());
             return requestHandler.sendPostRequest(URLs.URL_RetriveListofFreelancer+"UpdatefreelancerhiredList", params);
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
                // GlobalVariable.JSONArrayFreelancer=s.toString();
                Log.d("id",s);
                //converting response to json object
                if(s.contains("Updated Successfully")) {

                    Toast.makeText(mcon, s, Toast.LENGTH_SHORT).show();
                    frag = new Employer_HiredCandidatelistFragment();
                    AppCompatActivity activity = (AppCompatActivity) getContext();

                    if (frag != null) {
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame, frag).commit();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }
        }
    }

    class Sendfeedback extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();
            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("email", GlobalVariable.Employer_email);
            params.put("Feedback", Feedback);
            params.put("hiredfreelancer_id", hiredfreelancer_id);
            params.put("rating", ratingvalue);
            //returing the response
            return requestHandler.sendPostRequest(URLs.URL_sendfeedback, params);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //displaying the progress bar while user registers on the server
          ProgressD();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //hiding the progressbar after completion
            p.dismiss();
            try {
                Log.d("Json response:", s);
                Log.d("id", s);
                JSONObject obj = new JSONObject(s);
                if (!obj.getBoolean("error")) {
                    Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();


            }
        }
    }
    class RetriveFeedback extends AsyncTask<Void, Void, String> {
        FeedbacklistAdapter SortlistAdapter;
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
               params.put("id", id);
            return requestHandler.sendPostRequest(URLs.URL_Retrivealltablevalue+"RetriveFeedback", params);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //displaying the progress bar while user registers on the server
          ProgressD();
        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            p.dismiss();
            Log.d("sorting item",s);
            if (s.equalsIgnoreCase("No Results Found")) {
                Log.d("error", s);
                ListView.setAdapter(null);
                no_item.setVisibility(View.VISIBLE);
                ListView.setVisibility(View.GONE);
            } else {
                no_item.setVisibility(View.GONE);
                ListView.setVisibility(View.VISIBLE);
                try {
                    jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    FilterFreelancer Sort;
                    ArrayList<FilterFreelancer> Sortinglist = new ArrayList<FilterFreelancer>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("fb_id");
                        String Feedback = jsonObject.getString("Feedback");
                        String rating = jsonObject.getString("rating");
                        String employer_companyname = jsonObject.getString("employer_companyname");
                        Sort = new FilterFreelancer(id,employer_companyname,Feedback,rating);
                        Sortinglist.add(Sort);
                    }
                    feedbacklist=1;
                    Log.d("feedbacklist", String.valueOf(feedbacklist));
                    SortlistAdapter = new FeedbacklistAdapter(getContext(), R.layout.activity_detailsofratingclient, Sortinglist);
                    ListView.setAdapter(SortlistAdapter);
                    ListView.setItemsCanFocus(false);
                    ListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void ActivityConfirmationdialog(final String evfb_id, final String freelancer_id, final String totalpaidrate,final String totalduration, final String rateperduration,final String durationtype,final String gstapplied) throws Exception {
        TextView txv_HiredOn,txv_hiredrate,txv_totalpayment,txv_email,txv_phone,txv_startdate,txv_enddate;
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_confirmationbooked);
        txv_totalpayment=(TextView)dialog.findViewById(R.id.txv_totalpayment);
        TextView txv_lablestartdate = (TextView) dialog.findViewById(R.id.txv_lablestartdate);
        LinearLayout li_enddate = (LinearLayout) dialog.findViewById(R.id.li_enddate);
        txv_email=(TextView)dialog.findViewById(R.id.txv_email);
        txv_startdate=(TextView)dialog.findViewById(R.id.txv_startdate);
       // txv_enddate=(TextView)dialog.findViewById(R.id.txv_enddate);
        txv_phone=(TextView)dialog.findViewById(R.id.txv_phone);
        txv_HiredOn=(TextView)dialog.findViewById(R.id.txv_HiredOn);
        txv_hiredrate=(TextView)dialog.findViewById(R.id.txv_hiredrate);
        Button btn_ok = (Button)dialog. findViewById(R.id.btn_paynow);
        Log.d("totalpaidrate",String.valueOf(totalpaidrate));
        txv_totalpayment.setText(String.valueOf(totalpaidrate));
        txv_email.setText(GlobalVariable.Employer_email);
            txv_startdate.setText(totalduration);
            txv_lablestartdate.setText("Total Duration Selected");
            li_enddate.setVisibility(View.GONE);

        txv_phone.setText(GlobalVariable.Employer_mobile);
        txv_HiredOn.setText(durationtype);
        txv_hiredrate.setText(Float.toString(Float.parseFloat(rateperduration)+(1+(Float.parseFloat(gstapplied)/100)))+" ( including GST "+ gstapplied+"%)");
        btn_ok.setText("PayNow");
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getContext(), PayMentGateWay.class);
                intent.putExtra("FIRST_NAME", "Dear User");
                intent.putExtra("PHONE_NUMBER", GlobalVariable.Employer_mobile);
                intent.putExtra("EMAIL_ADDRESS", GlobalVariable.Employer_email);
                intent.putExtra("evfb_id", evfb_id);
                intent.putExtra("freelancer_id", freelancer_id);
                intent.putExtra("RECHARGE_AMT", totalpaidrate);
                intent.putExtra("totalduration", totalduration);
                intent.putExtra("durationtype", durationtype);
                getContext().startActivity(intent);
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(RecyclerView.LayoutParams.FILL_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }
}