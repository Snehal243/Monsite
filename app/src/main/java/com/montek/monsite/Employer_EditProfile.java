package com.montek.monsite;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.montek.monsite.Adapter.MultispinnerCustomAdapter;
import com.montek.monsite.Fragments.Employer_ProfileFragment;
import com.montek.monsite.JsonConnection.RequestHandler;
import com.montek.monsite.extra.GlobalVariable;
import com.montek.monsite.extra.URLs;
import com.montek.monsite.model.FilterFreelancer;

import net.gotev.uploadservice.MultipartUploadRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.montek.monsite.ActivitySplash.p;
import static com.montek.monsite.ActivitySplash.session;
import static com.montek.monsite.Activity_Login.ProgressD;
import static com.montek.monsite.Fragments.Employer_ProfileFragment.employer_image;
public class Employer_EditProfile extends AppCompatActivity implements View.OnClickListener {
    Button signup ;
    ImageView uploadimage;
    EditText edtx_confpassword,editsummary,editTextOfficeAddress,editTextdesignation,editTextcontactperson,editTextcompanyname,editTextcontact,editTextemail,editTextpassword;
    String eimage,imageName="",employer_confpassword,employersummary,employer_contact,employer_email,employer_OfficeAddress,employer_designation,employer_password,employer_companyname,employer_industrytype,employer_contactperson;;
    LinearLayout li_password,li_password1;
    MultispinnerCustomAdapter SortlistAdapter;
    List arrayindustrytypeid=new ArrayList();
    List arrayindustrytype=new ArrayList();
    ListView listView;
    TextView editTextindustrytype;
    LinearLayout li_chk;
    private Uri filePath;
    public static JSONArray jsonArray = null;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    TextInputLayout cnfpasswordinputlayout,passwordinputlayout,OfficeAddressinputlayout,designationinputlayout,summaryinputlayout,contactinputlayout,emailinputlayout,companynameinputlayout,industrytypeinputlayout,contactpnminputlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      try {
          getWindow().requestFeature(Window.FEATURE_NO_TITLE);
          setContentView(R.layout.activity_employer_reg);
          ActionBar actionBar = getSupportActionBar();
          if (actionBar != null) {
              actionBar.setHomeButtonEnabled(true);
              actionBar.setDisplayHomeAsUpEnabled(true);
          }
          iUI();
          requestStoragePermission();
          p = new ProgressDialog(this);
          Bundle extras = getIntent().getExtras();
          if (extras != null) {
              String summary = extras.getString("employersummary");
              editsummary.setText(summary);
              String eemail = extras.getString("employer_email");
              editTextemail.setText(eemail);
              String econtact = extras.getString("employer_contact");
              editTextcontact.setText(econtact);
              String officeaddress = extras.getString("employer_officeaddress");
              editTextOfficeAddress.setText(officeaddress);
              String designation = extras.getString("employer_designation");
              editTextdesignation.setText(designation);
              String password = extras.getString("employer_password");
              editTextpassword.setText(password);
              edtx_confpassword.setText(password);
              String companyname = extras.getString("employer_companyname");
              editTextcompanyname.setText(companyname);
              String industrytype = extras.getString("employer_industrytype");
              if (industrytype.equalsIgnoreCase("Not Mentioned")) {
                  industrytype = "";
              }
              ArrayList<String> strings = new ArrayList<>(Arrays.asList(industrytype.split(",")));
              arrayindustrytype = strings;
              editTextindustrytype.setText(industrytype);
              String cpersonsname = extras.getString("employer_cpersonsname");
              editTextcontactperson.setText(cpersonsname);
              String image = extras.getString("employer_image");
              Log.d("eimage", image);
              //for display image
              Glide.with(getBaseContext()).load(image)
                      .thumbnail(0.5f)
                      .crossFade()
                      .skipMemoryCache(true)
                      .error(R.drawable.profile)
                      .diskCacheStrategy(DiskCacheStrategy.NONE)
                      .into(uploadimage);

              File f = new File(employer_image);
              eimage = f.getName();
              Log.d("image path", eimage);
          }
      }catch (Exception e){}
    }
    @Override
    public void onBackPressed() {
        DashBordActivity.fragment = new Employer_ProfileFragment();
        Intent i = new Intent(getBaseContext(), DashBordActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
    private void iUI()  throws Exception {
        signup=(Button)findViewById(R.id.btn_signup1);
        signup.setOnClickListener(this);
        signup.setText("Update Profile");
        li_chk=(LinearLayout)findViewById(R.id.li_chk);
        li_chk.setVisibility(View.GONE);
        li_password = (LinearLayout) findViewById(R.id.li_password);
        li_password1 = (LinearLayout) findViewById(R.id.li_password1);
        li_password.setVisibility(View.GONE);
        li_password1.setVisibility(View.GONE);
        uploadimage=(ImageView)findViewById(R.id.imageView);
        uploadimage.setOnClickListener(this);
        editsummary=(EditText)findViewById(R.id.edtx_summary);
        editTextcontact=(EditText)findViewById(R.id.edtx_contact);
        editTextemail=(EditText)findViewById(R.id.edtx_email);
        editTextemail.setEnabled(false);
        editTextemail.setTextColor(getResources().getColor(android.R.color.black));
        editTextcompanyname=(EditText)findViewById(R.id.edtx_companyname);
        editTextindustrytype=(TextView)findViewById(R.id.edtx_industrytype);
        editTextindustrytype.setOnClickListener(this);
        editTextcontactperson=(EditText)findViewById(R.id.edtx_contactperson);
        editTextdesignation=(EditText)findViewById(R.id.edtx_designation);
        editTextOfficeAddress=(EditText)findViewById(R.id.edtx_OfficeAddress);
        editTextpassword=(EditText)findViewById(R.id.edtx_password);
        edtx_confpassword=(EditText)findViewById(R.id.edtx_confpassword);
        summaryinputlayout = (TextInputLayout) findViewById(R.id.summaryinputlayout);
        contactinputlayout = (TextInputLayout) findViewById(R.id.contactinputlayout);
        emailinputlayout = (TextInputLayout) findViewById(R.id.emailinputlayout);
        companynameinputlayout = (TextInputLayout) findViewById(R.id.companynameinputlayout);
        industrytypeinputlayout = (TextInputLayout) findViewById(R.id.industrytypeinputlayout);
        contactpnminputlayout = (TextInputLayout) findViewById(R.id.contactpnminputlayout);
        designationinputlayout = (TextInputLayout) findViewById(R.id.designationinputlayout);
        OfficeAddressinputlayout = (TextInputLayout) findViewById(R.id.OfficeAddressinputlayout);
        passwordinputlayout = (TextInputLayout) findViewById(R.id.passwordinputlayout);
        cnfpasswordinputlayout = (TextInputLayout) findViewById(R.id.cnfpasswordinputlayout);
        //validation
        editsummary.addTextChangedListener(new MyTextWatcher(editsummary));
        editTextcontact.addTextChangedListener(new MyTextWatcher(editTextcontact));
        editTextemail.addTextChangedListener(new MyTextWatcher(editTextemail));
        editTextcompanyname.addTextChangedListener(new MyTextWatcher(editTextcompanyname));
        editTextindustrytype.addTextChangedListener(new MyTextWatcher(editTextindustrytype));
        editTextcontactperson.addTextChangedListener(new MyTextWatcher(editTextcontactperson));
        editTextdesignation.addTextChangedListener(new MyTextWatcher(editTextdesignation));
        editTextOfficeAddress.addTextChangedListener(new MyTextWatcher(editTextOfficeAddress));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                DashBordActivity.fragment = new Employer_ProfileFragment();
                Intent i = new Intent(getBaseContext(), DashBordActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edtx_industrytype:
                try {
                    AlertDialogindustrytype();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.imageView:
                try {
                    showFileChooser();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_signup1:
                employersummary = editsummary.getText().toString().trim();
                employer_contact = editTextcontact.getText().toString().trim();
                employer_email = editTextemail.getText().toString().trim();
                employer_password = editTextpassword.getText().toString().trim();
                employer_confpassword = edtx_confpassword.getText().toString().trim();
                employer_companyname = editTextcompanyname.getText().toString().trim();
                employer_industrytype = editTextindustrytype.getText().toString().trim();
                employer_contactperson = editTextcontactperson.getText().toString().trim();
                employer_designation = editTextdesignation.getText().toString().trim();
                employer_OfficeAddress = editTextOfficeAddress.getText().toString().trim();
                try {
                    signUp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    private void requestFocus(View view)  throws Exception {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private boolean validatesummary()  throws Exception {
        if (editsummary.getText().toString().trim().isEmpty()) {
            summaryinputlayout.setError("Summary is mandatory");
            requestFocus(editsummary);
            return false;
        } else {
            summaryinputlayout.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateContact()  throws Exception {
        if (editTextcontact.getText().toString().trim().isEmpty()) {
            contactinputlayout.setError("Contact is mandatory");
            requestFocus(editTextcontact);
            return false;
        } else if (editTextcontact.getText().toString().trim().length() < 10) {
            contactinputlayout.setError("Contact should be valid");
            requestFocus(editTextcontact);
            return false;
        }
        else
        {
            contactinputlayout.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateEmail()  throws Exception {
        if (editTextemail.getText().toString().trim().isEmpty()) {
            emailinputlayout.setError("Email is mandatory");
            requestFocus(editTextemail);
            return false;
        } else  if (!Patterns.EMAIL_ADDRESS.matcher(editTextemail.getText().toString().trim()).matches()) {
            emailinputlayout.setError("email should be valid");
            requestFocus(editTextemail);
            return false;
        }
        else {
            emailinputlayout.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateCompanyname()  throws Exception {
        if (editTextcompanyname.getText().toString().trim().isEmpty()) {
            companynameinputlayout.setError("Company Name is mandatory");
            requestFocus(editTextcompanyname);
            return false;
        } else {
            companynameinputlayout.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateContactperson()  throws Exception {
        if (editTextcontactperson.getText().toString().trim().isEmpty()) {
            contactpnminputlayout.setError("ContactPerson Name is mandatory");
            requestFocus(editTextcontactperson);
            return false;
        } else {
            contactpnminputlayout.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateDesignation() throws Exception{
        if (editTextdesignation.getText().toString().trim().isEmpty()) {
            designationinputlayout.setError("Designation is mandatory (for e.g HR,Software etc)");
            requestFocus(editTextdesignation);
            return false;
        } else {
            designationinputlayout.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateOfficeAddress() throws Exception {
        if (editTextOfficeAddress.getText().toString().trim().isEmpty()) {
            OfficeAddressinputlayout.setError("OfficeAddress is mandatory");
            requestFocus(editTextOfficeAddress);
            return false;
        } else {
            OfficeAddressinputlayout.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateIndustrytype() throws Exception {
        if (editTextindustrytype.getText().toString().trim().isEmpty()) {
            industrytypeinputlayout.setError("Industry_Type is mandatory");
            requestFocus(editTextindustrytype);
            return false;
        } else {
            industrytypeinputlayout.setErrorEnabled(false);
        }
        return true;
    }
    private class MyTextWatcher implements TextWatcher  {
        private View view;
        private MyTextWatcher(View view) {
            this.view = view;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void afterTextChanged(Editable editable)  {
            switch (view.getId()) {
                case R.id.edtx_summary:
                    try {
                        validatesummary();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.edtx_contact:
                    try {
                        validateContact();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.edtx_email:
                    try {
                        validateEmail();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.edtx_companyname:
                    try {
                        validateCompanyname();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.edtx_industrytype:
                    try {
                        validateIndustrytype();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.edtx_contactperson:
                    try {
                        validateContactperson();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.edtx_designation:
                    try {
                        validateDesignation();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.edtx_OfficeAddress:
                    try {
                        validateOfficeAddress();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
    private void signUp()  throws Exception {
        if (!validateCompanyname()) {
            return;
        }
        if (!validateContact()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
        if (!validateContactperson()) {
            return;
        }
        if (!validateDesignation()) {
            return;
        }
        if (!validateIndustrytype()) {
            return;
        }
        if (!validateOfficeAddress()) {
            return;
        }
        if (!validatesummary()) {
            return;
        }
        new UpdateUser().execute();
    }
    void clear()  throws Exception
    {
        editsummary.setText("");
        editTextcontact.setText("");
        editTextemail.setText("");
        editTextpassword.setText("");
        edtx_confpassword.setText("");
        editTextindustrytype.setText("");
        editTextcontactperson.setText("");
        editTextdesignation.setText("");
        editTextOfficeAddress.setText("");
        editTextcompanyname.setText("");
        uploadimage.setImageResource(R.drawable.profile);
        emailinputlayout.setErrorEnabled(false);
        designationinputlayout.setErrorEnabled(false);
        OfficeAddressinputlayout.setErrorEnabled(false);
        industrytypeinputlayout.setErrorEnabled(false);
        summaryinputlayout.setErrorEnabled(false);
        contactinputlayout.setErrorEnabled(false);
        contactpnminputlayout.setErrorEnabled(false);
        companynameinputlayout.setErrorEnabled(false);
    }
    public void AlertDialogindustrytype()  throws Exception {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.activity_multispinner, null);
        // etUsernameF = (EditText) alertLayout.findViewById(R.id.et_username);
        listView = (ListView) alertLayout.findViewById(R.id.listView1);
        new RetriveIndustrytype().execute();
        //  final RadioGroup Radio_dialog_Group = (RadioGroup) alertLayout.findViewById(R.id.dialogrdGroup);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
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
                    arrayindustrytype.clear();
                    arrayindustrytypeid.clear();
                    for (int i = 0; i < countryList.size(); i++) {
                        FilterFreelancer country = countryList.get(i);
                        if (country.getSelected()) {
                            responseText.append(country.getcategoires() + ",");
                            if (!arrayindustrytypeid.contains(country.getsortelementid())) {
                                arrayindustrytype.add(country.getcategoires());
                                arrayindustrytypeid.add(country.getsortelementid());
                            }
                            // arrayindustryExp.add(country.getsortelementid());
                            Log.d("arraydomainExp", arrayindustrytype.toString());
                        }
                    }
                    Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG).show();
                    editTextindustrytype.setText(responseText);
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
    class RetriveIndustrytype extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            return requestHandler.sendPostRequest(URLs.URL_Retrivealltablevalue+"industrytype", params);
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
                        int id = jsonObject.getInt("industrytype_id");
                        String list1 = jsonObject.getString("industrytype");
                        chk = false;
                        Log.d("arraydomainExp", arrayindustrytype.toString());
                        for (int j = 0; j < arrayindustrytype.size(); j++) {
                            if (arrayindustrytype.get(j).toString().equalsIgnoreCase(list1)) {
                                Log.d("true", "true");
                                chk = true;
                                break;
                            }
                        }
                        Sort = new FilterFreelancer(Integer.toString(id), list1, chk);
                        Sortinglist.add(Sort);
                    }
                    SortlistAdapter = new MultispinnerCustomAdapter(getBaseContext(), R.layout.activity_sortingelement, Sortinglist);
                    listView.setAdapter(SortlistAdapter);
                    listView.setItemsCanFocus(false);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                } catch (Exception e) { e.printStackTrace();  }
            }
        }
    }
    /*
* This is the method responsible for image upload
* We need the full image path and the name for the image in this method
* */
    public void uploadMultipart() throws Exception {
        try {
            String  imgpath = getPath(filePath);
            File f = new File(imgpath);
            imageName = f.getName();
            Log.d("imageName",imageName);
            //Uploading code
            String uploadId = UUID.randomUUID().toString();
            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, URLs.UPLOAD_URLEmployer)
                    .addFileToUpload(imgpath, "image") //Adding file
                    .addParameter("name",imageName) //Adding text parameter to the request
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload
        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    //method to show file chooser
    private void showFileChooser() throws Exception{
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                uploadimage.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //method to get the file path from uri
    public String getPath(Uri uri) throws Exception {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }
    //Requesting permission
    private void requestStoragePermission() throws Exception {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
    class UpdateUser extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            if (filePath != null) {
                try {
                    uploadMultipart();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if( imageName.isEmpty() || imageName.equalsIgnoreCase("null") || imageName.equalsIgnoreCase("") )
            {
                imageName=eimage;
            }
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("employer_id", GlobalVariable.Employer_id);
            params.put("employersummary", employersummary);
            params.put("employer_contact", employer_contact);
            params.put("employer_email", employer_email);
            params.put("employer_companyname", employer_companyname);
            params.put("employer_industrytype", TextUtils.join(",", arrayindustrytype));
            params.put("employer_cpersonsname", employer_contactperson);
            params.put("employer_designation", employer_designation);
            params.put("employer_officeaddress", employer_OfficeAddress);
            params.put("employer_image", "http://monsite.montekservices.com/services/uploadsemployer/"+imageName);
            params.put("employer_password", employer_password);
            Log.d("param updated data:", params.toString());
            //returing the response
            return requestHandler.sendPostRequest(URLs.URL_EmployerREGISTER+"Update", params);
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
                if (s.contains("employer_id")) {
                    if (filePath != null && imageName!=("profile.jpg")) {
                        new Deleteimage().execute();
                    }
                    jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        GlobalVariable.Employer_id = jsonObject.getString("employer_id");
                        GlobalVariable.Employer_mobile = jsonObject.getString("employer_contact");
                        GlobalVariable.Employer_email = jsonObject.getString("employer_email");
                    }
                    session.createUserLoginSession(GlobalVariable.Employer_id, GlobalVariable.Employer_mobile, GlobalVariable.Employer_email);
                    if (GlobalVariable.Employer_id != null && !GlobalVariable.Employer_id.isEmpty() && !GlobalVariable.Employer_id.equals("null")) {
                        clear();
                        DashBordActivity.fragment = new Employer_ProfileFragment();
                        Intent i = new Intent(getBaseContext(), DashBordActivity.class);
                       // i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                        Employer_ProfileFragment.uploadimagestring="true";
                        Toast.makeText(getBaseContext(), " Profile Updated Successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), " Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }
        }
    }
    class Deleteimage extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("filePath",eimage);
            return requestHandler.sendPostRequest(URLs.URL_EmployerviewedFLprofileinsertion+"deletepriviousimage", params);
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
        }
    }
}

