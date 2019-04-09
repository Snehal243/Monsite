package com.montek.monsite;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.LinearLayout;
import com.montek.monsite.JsonConnection.RequestHandler;
import com.montek.monsite.extra.CheckForConnection;
import com.montek.monsite.extra.URLs;
import com.montek.monsite.extra.UserSessionManager;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import static com.montek.monsite.Activity_Login.ProgressD;
public class ActivitySplash extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    public static UserSessionManager session;
    Thread myThread;
    String currentAppVersion;
    public static ProgressDialog p;
    public static CheckForConnection cf = new CheckForConnection();;
    public static boolean mobile,wifi ;
    @SuppressLint("StaticFieldLeak")
    public static LinearLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getSupportActionBar().hide();
            setContentView(R.layout.activity_splash);
            coordinatorLayout = (LinearLayout) findViewById(R.id.parent_view);
            p=new ProgressDialog(this);
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0); //for version name
            int versionCode = packageInfo.versionCode;
            currentAppVersion = packageInfo.versionName;
            mobile = cf.getMobileDataStatus(this);
            wifi = cf.getWifiStatus(this);
            if(mobile||wifi) {
                new RetriveVersion().execute();
            }
            else
            {
                Snackbar snackbar =   Snackbar.make(coordinatorLayout, "Please Check Internet Connection!", Snackbar.LENGTH_LONG);
                snackbar.show();
           }
        } catch (Exception e) {e.printStackTrace();}
    }
    private void startActivity() throws Exception {
        myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000);
                    Intent intent = new Intent(getApplicationContext(),Activity_Login.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
    class RetriveVersion extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            return requestHandler.sendPostRequest(URLs.URL_RetriveVersion, params);
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
            String latestversion = null;
            String googleplaystorelink=null;
            try {
                JSONArray jArray = new JSONArray(s);
                for(int i=0; i < jArray.length(); i++) {
                    JSONObject jObject = jArray.getJSONObject(i);
                    String tbl_versionid = jObject.getString("tbl_versionid");
                    latestversion = jObject.getString("latestversion");
                    googleplaystorelink  = jObject.getString("googleplaystorelink");

                } // End Loop
                if(latestversion != null ? latestversion.equalsIgnoreCase(currentAppVersion) : false)
                {
                    startActivity();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySplash.this);
                    builder.setCancelable(false);
                    builder.setTitle("Monsite Update");
                    builder.setMessage("A new version is available.\nPlease visit play store to update.");
                    final String finalGoogleplaystorelink = googleplaystorelink;
                    builder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Intent version = new Intent(Intent.ACTION_VIEW);
                            version.setData(Uri.parse( finalGoogleplaystorelink));
                            startActivity(version);
                        }
                    });
                    builder.create();
                    builder.show();
                }
            } catch (Exception e) {}
        }
    }
}
