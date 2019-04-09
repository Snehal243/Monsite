package com.montek.monsite;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.*;
import android.widget.*;
import com.facebook.*;
import com.facebook.login.*;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.montek.monsite.JsonConnection.RequestHandler;
import com.montek.monsite.extra.URLs;
import com.montek.monsite.extra.UserSessionManager;
import org.json.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.montek.monsite.ActivitySplash.p;
import static com.montek.monsite.ActivitySplash.session;
import static com.montek.monsite.extra.GlobalVariable.Employer_email;
import static com.montek.monsite.extra.GlobalVariable.Employer_id;
import static com.montek.monsite.extra.GlobalVariable.Employer_mobile;
import static java.lang.Thread.sleep;
public class Activity_Login extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    AppCompatButton login,btnSignIn;
    EditText edtx_email,edtx_password ;
    TextView link_signup,txv_forgotpassword;
    String password;
    private static final int RC_SIGN_IN = 420;
    public static String Flag="",email="";
    TextInputLayout passwordinputlayout,emailinputlayout ;
    //private SignInButton btnSignIn;
    private GoogleApiClient mGoogleApiClient;
    LoginButton btnFacebookLogin;
    private CallbackManager callbackManager;
    JSONArray jsonArray=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Flag="";
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//for layout adjust when keybord
            FacebookSdk.sdkInitialize(getApplicationContext());
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getSupportActionBar().hide();
            setContentView(R.layout.activity_login);
            iUI();
            p = new ProgressDialog(this);
            session = new UserSessionManager(getApplicationContext()); // get user data from session
            session();//session
            if (Employer_id != null && !Employer_id.isEmpty() && !Employer_id.equals("null")) {
                startActivity(new Intent(getApplicationContext(), DashBordActivity.class));
                finish();
            }
        }catch (Exception e){}
    }
    public static void session() throws Exception
    {
        HashMap<String, String> user = session.getUserDetails();
        Employer_id = user.get(UserSessionManager.KEY_Userid);
        Employer_mobile = user.get(UserSessionManager.key_mobile);
        Employer_email = user.get(UserSessionManager.key_email);
    }
    private void iUI() throws Exception{
        edtx_email= (EditText) findViewById(R.id.edtx_email);
        edtx_password = (EditText) findViewById(R.id.edtx_password);
        passwordinputlayout = (TextInputLayout) findViewById(R.id.passwordinputlayout);
        emailinputlayout = (TextInputLayout) findViewById(R.id.emailinputlayout);
        link_signup = (TextView) findViewById(R.id.link_signup);
        txv_forgotpassword = (TextView) findViewById(R.id.txv_forgotpassword);
        txv_forgotpassword.setOnClickListener(this);
        login = (AppCompatButton) findViewById(R.id.btn_login);
        btnSignIn = (AppCompatButton) findViewById(R.id.btn_googlesignin);
        btnFacebookLogin = (LoginButton)findViewById(R.id.login_facebookbutton);
        btnFacebookLogin.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        login.setOnClickListener(this);
        link_signup.setOnClickListener(this);
    }
    private void initializeGPlusSettings() throws Exception{
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Log.d("running",mGoogleApiClient.toString());
        // Customizing G+ button
        //btnSignIn.setSize(SignInButton.SIZE_WIDE);
       // btnSignIn.setScopes(gso.getScopeArray());
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }
    private void signIn() throws Exception {//for google login
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.d("RC_SIGN_IN",Integer.toString(RC_SIGN_IN));
    }
    public void signOut() throws Exception {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                    }
                });
    }
    private void handleGPlusSignInResult  (GoogleSignInResult result){
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            //Fetch values
            if (acct != null) {
               // firstname = acct.getDisplayName();
                //String lastname=acct.getServerAuthCode();
                // String personGivenName = acct.getGivenName();
               // lastname = acct.getFamilyName();
                email = acct.getEmail();
              //  oauth_uid = acct.getId();
               // Log.d("personid",oauth_uid);
                Flag="google";
                new CheckSocialMUserexist().execute();
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(Flag.equalsIgnoreCase("google")) {
            mGoogleApiClient.disconnect();
            Log.d("hh11","11");
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if(Flag.equalsIgnoreCase("facebook")) { //for facebook
             callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        else if(Flag.equalsIgnoreCase("google")) //for google
        {
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleGPlusSignInResult(result);
            }
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        if( Flag.equalsIgnoreCase("google")) {
            Log.d("hh11","11111");
            try {
                mGoogleApiClient.connect();
                OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
                if (opr.isDone()) {
                    GoogleSignInResult result = opr.get();
                    handleGPlusSignInResult(result);
                } else {
                    opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                        @Override
                        public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                            handleGPlusSignInResult(googleSignInResult);
                        }
                    });
                }
            }catch (Exception e){}
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("tag", "onConnectionFailed:" + connectionResult);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txv_forgotpassword:
                finish();
                Intent forgotpassword=new Intent(getApplicationContext(),ResetActivity.class);
                startActivity(forgotpassword);
                break;
            case R.id.btn_login:
                ActivitySplash.mobile = ActivitySplash.cf.getMobileDataStatus(this);
                ActivitySplash.wifi = ActivitySplash.cf.getWifiStatus(this);
                if(ActivitySplash.mobile||ActivitySplash.wifi) {
                    email = edtx_email.getText().toString().trim();
                    password = edtx_password.getText().toString().trim();
                    try {
                        signUp();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Please Check Internet Connection!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.link_signup:
                finish();
                Intent i=new Intent(this,Employer_Registration.class);
                startActivity(i);
                break;
            case R.id.btn_googlesignin:
                try {
                    initializeGPlusSettings();
                    sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Flag="google";
                try {
                    signIn();
                } catch (Exception e) { e.printStackTrace();}
                break;

            case R.id.login_facebookbutton:
                try {
                    Flag = "facebook";
                    btnFacebookLogin.setReadPermissions(Arrays.asList("public_profile, email, user_birthday"));
                    callbackManager = CallbackManager.Factory.create();
                    btnFacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() { // Callback registration
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            GraphRequest request = GraphRequest.newMeRequest(
                                    loginResult.getAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {

                                        @Override
                                        public void onCompleted(JSONObject object, GraphResponse response) {
                                            Log.v("Main", response.toString());
                                            setProfileToView(object);
                                        }
                                    });
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id,name,email,gender, birthday");
                            request.setParameters(parameters);
                            request.executeAsync();
                        }
                        @Override
                        public void onCancel() {
                        }
                        @Override
                        public void onError(FacebookException exception) {
                            Toast.makeText(Activity_Login.this, "error to Login Facebook", Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch (Exception e){}
                break;
        }
    }

     public static void ProgressD()
     {
         try{
         p.setMessage("Loading.....");
         p.setIndeterminate(false);
         p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
         p.setCancelable(false);
         p.show();}
         catch (Exception e){}
     }
       private void setProfileToView(JSONObject jsonObject) { //for facebook login
        try {
            email = jsonObject.getString("email");
           // String gender = jsonObject.getString("gender");
          //  oauth_uid = jsonObject.getString("id");
          //  Log.d("oauth_uid",oauth_uid);
            Flag="facebook";
           new CheckSocialMUserexist().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void regsocialmedia()
    {
        if(Flag.equalsIgnoreCase("facebook"))
        {
            LoginManager.getInstance().logOut();
        }
        else if(Flag.equalsIgnoreCase("google"))
        {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                        }
                    });
            Log.d("hh","Logout");
        }
    }
    private void signUp() throws Exception{
        boolean isValid = true;
        if (email.isEmpty()) {
            emailinputlayout.setError("Please enter valid email");
            isValid = false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailinputlayout.setError("Please enter valid email.");
            isValid = false;
        } else {
            emailinputlayout.setErrorEnabled(false);
        }
        if (password.isEmpty()) {
            passwordinputlayout.setError("Please enter valid password.");
            isValid = false;
        } else  if (!isValidPassword(password)) {
            passwordinputlayout.setError("Please enter valid password. (for e.g Example@123)");
            isValid = false;
        } else {
            passwordinputlayout.setErrorEnabled(false);
        }
        if (isValid) {
            new  RegisterLogin().execute();
        }
    }
    public boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
    void clear()
    {
        edtx_email.setText("");
        edtx_password.setText("");
    }
    class RegisterLogin extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("password", password);
            return requestHandler.sendPostRequest(URLs.URL_EmployerREGISTER+"login", params);
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
            JSONArray jsonArray = null;
            try {
                if (s.contains("employer_id"))
                {
                    jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        Employer_id = jsonObject.getString("employer_id");
                        Employer_mobile = jsonObject.getString("employer_contact");
                        Employer_email = jsonObject.getString("employer_email");
                    }
                        UserSessionManager.createUserLoginSession(Employer_id, Employer_mobile, Employer_email);
                        if (Employer_id != null && !Employer_id.isEmpty() && !Employer_id.equals("null")) {
                            Toast.makeText(getBaseContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getBaseContext(), DashBordActivity.class);
                            startActivity(i);
                            finish();
                            DashBordActivity.fragment=null;
                            Flag="";
                         }
                         else
                         { Toast.makeText(getBaseContext(),"Invalid Username and Password " ,Toast.LENGTH_SHORT).show(); }
                }
                else
                { Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show(); }
            }
            catch (Exception e) {  e.getMessage();}
        }
    }
    class CheckSocialMUserexist extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("employer_email", email);
            //returing the response
            return requestHandler.sendPostRequest(URLs.URL_EmployerREGISTER+"CheckSocialMUserexist", params);
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
               regsocialmedia();
               if (s.contains("employer_id"))
                {
                    jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        Employer_id = jsonObject.getString("employer_id");
                        Employer_mobile  = jsonObject.getString("employer_contact");
                        Employer_email  = jsonObject.getString("employer_email");
                    }
                    Log.d("Employer_id",Employer_id+Employer_mobile+Employer_email);
                    UserSessionManager.createUserLoginSession(Employer_id, Employer_mobile, Employer_email);
                    if (Employer_id != null && !Employer_id.isEmpty() && !Employer_id.equals("null")) {
                        Toast.makeText(getBaseContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getBaseContext(), DashBordActivity.class);
                        startActivity(i);
                        finish();
                        DashBordActivity.fragment=null;
                    }
                }
                else if(s.contains("This email is not registered with us."))
                {
                    finish();
                    startActivity(new Intent(getApplicationContext(), Employer_Registration.class));
                }
                else {
                   Toast.makeText(getBaseContext(),s,Toast.LENGTH_LONG).show();
               }
            }
            catch (Exception e) { e.getMessage();}
        }
    }
}
