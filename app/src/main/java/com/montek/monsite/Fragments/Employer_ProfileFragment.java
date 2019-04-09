package com.montek.monsite.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.montek.monsite.DashBordActivity;
import com.montek.monsite.Employer_EditProfile;
import com.montek.monsite.JsonConnection.RequestHandler;
import com.montek.monsite.R;
import com.montek.monsite.extra.GlobalVariable;
import com.montek.monsite.extra.URLs;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static com.montek.monsite.ActivitySplash.p;
import static com.montek.monsite.Activity_Login.ProgressD;
public class Employer_ProfileFragment extends Fragment implements View.OnClickListener {
    TextView eindustrytype, designation, officeAddress, ContactPersonsName, ecompanyname, summary, email, contact;
    View view;
    ImageView profile;
    String employer_password, employer_officeaddress, employer_designation, employersummary, employer_contact, employer_email, employer_companyname, employer_industrytype, employer_cpersonsname;
    Button btn_editprofile;
    public static String employer_image = "",image,uploadimagestring="";
    public static Employer_ProfileFragment newInstance() {
        Employer_ProfileFragment fragment = new Employer_ProfileFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_employerprofile, container, false);
        try {
            iUI();
            p = new ProgressDialog(getContext());
            if(getActivity() != null && !getActivity().isFinishing()) {
               new RetriveEmployerUser().execute();
            }
        }catch (Exception e){}
              return view;
    }
    void iUI() throws Exception {
        profile = (ImageView) view.findViewById(R.id.img_profile);
        summary = (TextView) view.findViewById(R.id.txv_summary);
        email = (TextView) view.findViewById(R.id.txv_email);
        contact = (TextView) view.findViewById(R.id.txv_contact);
        ecompanyname = (TextView) view.findViewById(R.id.txv_ecompanyname);
        ContactPersonsName = (TextView) view.findViewById(R.id.txv_ContactPersonsName);
        eindustrytype = (TextView) view.findViewById(R.id.txv_eindustrytype);
        designation = (TextView) view.findViewById(R.id.txv_designation);
        officeAddress = (TextView) view.findViewById(R.id.txv_officeAddress);
        email.setOnClickListener(this);
        contact.setOnClickListener(this);
        btn_editprofile = (Button) view.findViewById(R.id.btn_editprofile);
        btn_editprofile.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_editprofile:
                Glide.get(getActivity()).clearMemory();
                Intent intent = new Intent(getContext(), Employer_EditProfile.class);
                intent.putExtra("employersummary", employersummary);
                intent.putExtra("employer_contact", employer_contact);
                intent.putExtra("employer_email", employer_email);
                intent.putExtra("employer_officeaddress", employer_officeaddress);
                intent.putExtra("employer_designation", employer_designation);
                intent.putExtra("employer_password", employer_password);
                intent.putExtra("employer_companyname", employer_companyname);
                intent.putExtra("employer_industrytype", employer_industrytype);
                intent.putExtra("employer_cpersonsname", employer_cpersonsname);
                intent.putExtra("employer_password", employer_password);
                intent.putExtra("employer_image", employer_image);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.txv_email:
                Intent testIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject=" + "subject" + "&body=" + "body" + "&to=" + employer_email);
                testIntent.setData(data);
                startActivity(testIntent);
                break;
            case R.id.txv_contact:
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", employer_contact, null));
                startActivity(callIntent);
                break;
        }
    }
    class RetriveEmployerUser extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("employer_id", GlobalVariable.Employer_id);
            return requestHandler.sendPostRequest(URLs.URL_RetriveUserEmployer, params);
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
            Log.d("ssss",s);
            p.dismiss();
            try {
                JSONArray jsonArray = new JSONArray(s);
                JSONObject obj;
                for (int J = 0; J < jsonArray.length(); J++) {
                    obj = jsonArray.getJSONObject(J);
                    employer_image = obj.getString("employer_image");
                    Picasso.with(getContext()).load(employer_image).skipMemoryCache()
                            .into(profile);
                    GlobalVariable.Employer_id = obj.getString("employer_id");
                    employersummary = obj.getString("employersummary");
                    summary.setText(employersummary);
                    Log.d("employersummary", employersummary);
                    employer_contact = obj.getString("employer_contact");
                    contact.setText(employer_contact);
                    employer_email = obj.getString("employer_email");
                    email.setText(employer_email);
                    employer_companyname = obj.getString("employer_companyname");
                    ecompanyname.setText(employer_companyname);
                    employer_industrytype = obj.getString("employer_industrytype");
                    eindustrytype.setText(employer_industrytype);
                    employer_cpersonsname = obj.getString("employer_cpersonsname");
                    ContactPersonsName.setText(employer_cpersonsname);
                    employer_designation = obj.getString("employer_designation");
                    designation.setText(employer_designation);
                    employer_officeaddress = obj.getString("employer_officeaddress");
                    officeAddress.setText(employer_officeaddress);
                   // String password = obj.getString("employer_password");
                   // Log.d("employersummary11", employersummary);
//                    byte[] data = Base64.decode(password, Base64.DEFAULT); //for decode password
//                    employer_password = new String(data, StandardCharsets.UTF_8);
                }
            } catch (Exception e) {
                e.getMessage();
            }
            finally {
                if( uploadimagestring.equalsIgnoreCase("true"))
                {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    DashBordActivity.fragment = new Employer_ProfileFragment();
                    Intent i = new Intent(getContext(), DashBordActivity.class);
                  //  i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    uploadimagestring="false";
                    getActivity().finish();
                    Log.d("uploadimagestring",uploadimagestring);
                }
            }
        }
    }
}
