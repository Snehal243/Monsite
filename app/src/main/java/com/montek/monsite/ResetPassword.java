package com.montek.monsite;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.montek.monsite.JsonConnection.RequestHandler;
import com.montek.monsite.extra.URLs;
import java.util.*;
import java.util.regex.*;
import static com.montek.monsite.ActivitySplash.coordinatorLayout;
import static com.montek.monsite.Activity_Login.ProgressD;
import static com.montek.monsite.ResetActivity.email;
public class ResetPassword extends AppCompatActivity implements View.OnClickListener {
    private EditText edtx_cpassword, edtx_password;
    private Button btn_go, btn_submit;
    TextInputLayout passwordinputlayout,cnfpasswordinputlayout;
    String cpassword,password;
    TextView link_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        coordinatorLayout = (LinearLayout) findViewById(R.id.parent_view);
        edtx_password = (EditText) findViewById(R.id.edtx_password);
        edtx_cpassword = (EditText) findViewById(R.id.edtx_cpassword);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        passwordinputlayout = (TextInputLayout) findViewById(R.id.passwordinputlayout);
        cnfpasswordinputlayout = (TextInputLayout) findViewById(R.id.cnfpasswordinputlayout);
        link_signup = (TextView) findViewById(R.id.link_signup);
    }
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.link_signup:
                    Intent i = new Intent(this, Employer_Registration.class);
                    startActivity(i);
                    finish();
                    break;
                case R.id.btn_submit:
                    password = edtx_password.getText().toString().trim();
                    cpassword = edtx_cpassword.getText().toString().trim();
                    boolean isValid = true;
                    if (password.isEmpty()) {
                        passwordinputlayout.setError("Please enter valid password.");
                        isValid = false;
                    } else if (!isValidPassword(password)) {
                        passwordinputlayout.setError("Please enter valid password.(for e.g Example@123)");
                        isValid = false;
                    } else {
                        passwordinputlayout.setErrorEnabled(false);
                    }
                    if (cpassword.isEmpty()) {
                        cnfpasswordinputlayout.setError("Please enter valid confirm password.");
                        isValid = false;
                    } else if (!isValidPassword(cpassword)) {
                        cnfpasswordinputlayout.setError("Please enter valid confirm password.(for e.g Example@123)");
                        isValid = false;
                    } else {
                        cnfpasswordinputlayout.setErrorEnabled(false);
                    }
                    if (isValid) {
                        if (password.equalsIgnoreCase(cpassword)) {
                            new Reset_Password().execute();
                        } else {
                            Toast.makeText(getBaseContext(), "passwords not matching.please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        } catch (Exception e) { }
    }
    public boolean isValidPassword(final String password)  throws Exception {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
    class Reset_Password extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("Group", "Employer");
            params.put("OTP",String.valueOf(ResetActivity.random_no));
            params.put("password",password);
            return requestHandler.sendPostRequest(URLs.Url_ResetPassword+"UpdatePassword", params);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressD();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
                try {
                    if (s.contains("Reset Password Successfully")) {
                        Intent i = new Intent(getBaseContext(), Activity_Login.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        addNotification();
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Password Successfully Updated", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }catch (Exception e){}
        }
    }
    public void addNotification()  throws Exception{
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.mlogo)
                        .setContentTitle("Reset Password")
                        .setContentText("Successfully Reset Password");
        Intent notificationIntent = new Intent(this, Activity_Login.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}

