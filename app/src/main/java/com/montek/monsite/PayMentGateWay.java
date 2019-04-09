package com.montek.monsite;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.webkit.JavascriptInterface;
import android.webkit.*;
import android.widget.Toast;
import com.montek.monsite.Fragments.Employer_HiredCandidatelistFragment;
import com.montek.monsite.JsonConnection.RequestHandler;
import com.montek.monsite.extra.GlobalVariable;
import com.montek.monsite.extra.URLs;
import org.json.JSONObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import static android.webkit.WebSettings.LOAD_DEFAULT;
import static com.montek.monsite.ActivitySplash.p;
import static com.montek.monsite.Activity_Login.ProgressD;
import static com.montek.monsite.DisplayList_Freelancer.freelancer_id;
public class PayMentGateWay extends Activity {
    private String paymentId,totalduration,durationtype;
    WebView webView ;
    final Activity activity = this;
    private String tag = "PayMentGateWay",evfb_id;
    private String hash;
    ProgressDialog progressDialog ;
//    String merchant_key="rjQUPktU"; // test
//    String salt="e5iIg1jwi8"; // test
    String merchant_key="gtKFFx"; // test
    String salt="eCwWELxi"; // test
    String action1 ="";
    String base_url="https://test.payu.in";
    int error=0;
    String hashString="";
    Map<String,String> params;
    String txnid ="";
    String SUCCESS_URL = "https://www.payumoney.com/mobileapp/payumoney/success.php" ; // failed
    String FAILED_URL = "https://www.payumoney.com/mobileapp/payumoney/failure.php" ;
    Handler mHandler = new Handler();
    static String getUpdatedRECHARGE_AMT,getFirstName, getNumber, getEmailAddress, getTotalAmt;
    @SuppressLint("JavascriptInterface") @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
     try {
         progressDialog = new ProgressDialog(activity);
         getWindow().requestFeature(Window.FEATURE_PROGRESS);
         webView = new WebView(this);
         setContentView(webView);
         Intent oIntent = getIntent();

             getFirstName = oIntent.getExtras().getString("FIRST_NAME");
             getNumber = oIntent.getExtras().getString("PHONE_NUMBER");
             getEmailAddress = oIntent.getExtras().getString("EMAIL_ADDRESS");
             evfb_id = oIntent.getExtras().getString("evfb_id");
             freelancer_id = oIntent.getExtras().getString("freelancer_id");
             getTotalAmt = oIntent.getExtras().getString("RECHARGE_AMT");
             totalduration = oIntent.getExtras().getString("totalduration");
             durationtype = oIntent.getExtras().getString("durationtype");

         params = new HashMap<String, String>();
         params.put("key", merchant_key);
         params.put("amount", getTotalAmt);
         params.put("firstname", getFirstName);
         params.put("email", getEmailAddress);
         params.put("phone", getNumber);
         params.put("productinfo", "Recharge Wallet");
         params.put("surl", SUCCESS_URL);
         params.put("furl", FAILED_URL);
         params.put("service_provider", "payu_paisa");
         params.put("lastname", "");
         params.put("address1", "");
         params.put("address2", "");
         params.put("city", "");
         params.put("state", "");
         params.put("country", "");
         params.put("zipcode", "");
         params.put("udf1", "");
         params.put("udf2", "");
         params.put("udf3", "");
         params.put("udf4", "");
         params.put("udf5", "");
         params.put("pg", "");
         if (empty(params.get("txnid"))) {
             Random rand = new Random();
             String rndm = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);
             txnid = hashCal("SHA-256", rndm).substring(0, 20);
             params.put("txnid", txnid);
         } else
             txnid = params.get("txnid");
         //String udf2 = txnid;
         String txn = "abcd";
         hash = "";
         String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";
         if (empty(params.get("hash")) && params.size() > 0) {
             if (empty(params.get("key"))
                     || empty(params.get("txnid"))
                     || empty(params.get("amount"))
                     || empty(params.get("firstname"))
                     || empty(params.get("email"))
                     || empty(params.get("phone"))
                     || empty(params.get("productinfo"))
                     || empty(params.get("surl"))
                     || empty(params.get("furl"))
                     || empty(params.get("service_provider"))
                     ) {
                 error = 1;
             } else {
                 String[] hashVarSeq = hashSequence.split("\\|");
                 for (String part : hashVarSeq) {
                     hashString = (empty(params.get(part))) ? hashString.concat("") : hashString.concat(params.get(part));
                     hashString = hashString.concat("|");
                 }
                 hashString = hashString.concat(salt);
                 hash = hashCal("SHA-512", hashString);
                 action1 = base_url.concat("/_payment");
             }
         } else if (!empty(params.get("hash"))) {
             hash = params.get("hash");
             action1 = base_url.concat("/_payment");
         }
         webView.setWebViewClient(new MyWebViewClient() {
             public void onPageFinished(WebView view, final String url) {
                 progressDialog.dismiss();
             }

             public void onPageStarted(WebView view, String url, Bitmap favicon) {
                 if (!progressDialog.isShowing()) {
                     progressDialog.show();
                 }
             }
			/*@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "SslError! " +  error, Toast.LENGTH_SHORT).show();
				 handler.proceed();
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "Page Started! " + url, Toast.LENGTH_SHORT).show();
				if(url.startsWith(SUCCESS_URL)){
					Toast

					.makeText(activity, "Payment Successful! " + url, Toast.LENGTH_SHORT).show();
					 Intent intent = new Intent(PayMentGateWay.this, MainActivity.class);
					    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_SINGLE_TOP);
					    startActivity(intent);
					    finish();
					    return false;
				}else if(url.startsWith(FAILED_URL)){
					Toast.makeText(activity, "Payment Failed! " + url, Toast.LENGTH_SHORT).show();
				    return false;
				}else if(url.startsWith("http")){
					return true;
				}
				//return super.shouldOverrideUrlLoading(view, url);
				return false;
			}*/
         });
         //webView.setVisibility(0);
         webView.setVisibility(View.VISIBLE);
         webView.getSettings().setBuiltInZoomControls(true);
         webView.getSettings().setCacheMode(LOAD_DEFAULT);//2
         webView.getSettings().setDomStorageEnabled(true);
         webView.clearHistory();
         webView.clearCache(true);
         webView.getSettings().setJavaScriptEnabled(true);
         webView.getSettings().setSupportZoom(true);
         webView.getSettings().setUseWideViewPort(false);
         webView.getSettings().setLoadWithOverviewMode(false);
         webView.addJavascriptInterface(new PayUJavaScriptInterface(), "PayUMoney");
         Map<String, String> mapParams = new HashMap<String, String>();
         mapParams.put("key", merchant_key);
         mapParams.put("hash", PayMentGateWay.this.hash);
         mapParams.put("txnid", (empty(PayMentGateWay.this.params.get("txnid"))) ? "" : PayMentGateWay.this.params.get("txnid"));
         Log.d(tag, "txnid: " + PayMentGateWay.this.params.get("txnid"));
         mapParams.put("service_provider", "payu_paisa");
         mapParams.put("amount", (empty(PayMentGateWay.this.params.get("amount"))) ? "" : PayMentGateWay.this.params.get("amount"));
         mapParams.put("firstname", (empty(PayMentGateWay.this.params.get("firstname"))) ? "" : PayMentGateWay.this.params.get("firstname"));
         mapParams.put("email", (empty(PayMentGateWay.this.params.get("email"))) ? "" : PayMentGateWay.this.params.get("email"));
         mapParams.put("phone", (empty(PayMentGateWay.this.params.get("phone"))) ? "" : PayMentGateWay.this.params.get("phone"));
         mapParams.put("productinfo", (empty(PayMentGateWay.this.params.get("productinfo"))) ? "" : PayMentGateWay.this.params.get("productinfo"));
         mapParams.put("surl", (empty(PayMentGateWay.this.params.get("surl"))) ? "" : PayMentGateWay.this.params.get("surl"));
         mapParams.put("furl", (empty(PayMentGateWay.this.params.get("furl"))) ? "" : PayMentGateWay.this.params.get("furl"));
         mapParams.put("lastname", (empty(PayMentGateWay.this.params.get("lastname"))) ? "" : PayMentGateWay.this.params.get("lastname"));
         mapParams.put("address1", (empty(PayMentGateWay.this.params.get("address1"))) ? "" : PayMentGateWay.this.params.get("address1"));
         mapParams.put("address2", (empty(PayMentGateWay.this.params.get("address2"))) ? "" : PayMentGateWay.this.params.get("address2"));
         mapParams.put("city", (empty(PayMentGateWay.this.params.get("city"))) ? "" : PayMentGateWay.this.params.get("city"));
         mapParams.put("state", (empty(PayMentGateWay.this.params.get("state"))) ? "" : PayMentGateWay.this.params.get("state"));
         mapParams.put("country", (empty(PayMentGateWay.this.params.get("country"))) ? "" : PayMentGateWay.this.params.get("country"));
         mapParams.put("zipcode", (empty(PayMentGateWay.this.params.get("zipcode"))) ? "" : PayMentGateWay.this.params.get("zipcode"));
         mapParams.put("udf1", (empty(PayMentGateWay.this.params.get("udf1"))) ? "" : PayMentGateWay.this.params.get("udf1"));
         mapParams.put("udf2", (empty(PayMentGateWay.this.params.get("udf2"))) ? "" : PayMentGateWay.this.params.get("udf2"));
         mapParams.put("udf3", (empty(PayMentGateWay.this.params.get("udf3"))) ? "" : PayMentGateWay.this.params.get("udf3"));
         mapParams.put("udf4", (empty(PayMentGateWay.this.params.get("udf4"))) ? "" : PayMentGateWay.this.params.get("udf4"));
         mapParams.put("udf5", (empty(PayMentGateWay.this.params.get("udf5"))) ? "" : PayMentGateWay.this.params.get("udf5"));
         mapParams.put("pg", (empty(PayMentGateWay.this.params.get("pg"))) ? "" : PayMentGateWay.this.params.get("pg"));
         webview_ClientPost(webView, action1, mapParams.entrySet());
     }catch (Exception e){}
    }
	 private final class PayUJavaScriptInterface {
        PayUJavaScriptInterface() {
        }
        @JavascriptInterface
        public void success(long id, final String paymentId1) {
            mHandler.post(new Runnable() {
                public void run() {
                    mHandler = null;
                    paymentId=params.get("txnid");
                        new PaymentforHiredFreelancer().execute();

                }
            });
        }
        @JavascriptInterface
        public void failure(final String id, String error) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"Cancel payment" ,Toast.LENGTH_LONG).show();
                     finish();
                }
            });
        }
        @JavascriptInterface
        public void failure() {
            failure("");
        }
        @JavascriptInterface
        public void failure(final String params) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"Failed payment" ,Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
    }
    public void webview_ClientPost(WebView webView, String url, Collection< Map.Entry<String, String>> postData){
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head>");
        sb.append("<body onload='form1.submit()'>");
        sb.append(String.format("<form id='form1' action='%s' method='%s'>", url, "post"));
        for (Map.Entry<String, String> item : postData) {
            sb.append(String.format("<input name='%s' type='hidden' value='%s' />", item.getKey(), item.getValue()));
        }
        sb.append("</form></body></html>");
        Log.d(tag, "webview_ClientPost called");
        //setup and load the progress bar
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading. Please wait...");
        progressDialog.setCancelable(false);
        webView.loadData(sb.toString(), "text/html", "utf-8");
    }
    public void success(long id, final String paymentId) {
        mHandler.post(new Runnable() {
            public void run() {
                mHandler = null;
              //  new PostRechargeData().execute();
                Toast.makeText(getApplicationContext(),"Successfully payment\n redirect from Success Function" ,Toast.LENGTH_LONG).show();
            }
        });
    }
    public boolean empty(String s) throws Exception
    {
        if(s== null || s.trim().equals(""))
            return true;
        else
            return false;
    }
    public String hashCal(String type,String str){
        byte[] hashseq=str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try{
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (int i=0;i<messageDigest.length;i++) {
                String hex=Integer.toHexString(0xFF & messageDigest[i]);
                if(hex.length()==1) hexString.append("0");
                hexString.append(hex);
            }
        }catch(NoSuchAlgorithmException nsae){ }
        return hexString.toString();
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
       if(url.startsWith("http")){
                progressDialog.show();
                view.loadUrl(url);
                System.out.println("myresult "+url);
                //return true;
            } else {
                return false;
            }
            return true;
        }
    }
    class PaymentforHiredFreelancer extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String, String> params = new HashMap<>();
            params.put("evfb_id", evfb_id);
            params.put("paymentId", paymentId);
            params.put("paymentstatus","paid");
            params.put("freelancer_id",freelancer_id);
            params.put("email", GlobalVariable.Employer_email);
            params.put("totalduration", totalduration);
            params.put("totalpaidrate", getTotalAmt);
            params.put("durationtype", durationtype);
            return requestHandler.sendPostRequest(URLs.URL_EmpVFLprofileOperation+"PaymentforHiredFreelancer", params);
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
                    Toast.makeText(getBaseContext(),"Successfully Received payment,For More information Check Mail", Toast.LENGTH_LONG).show();
                    DashBordActivity.fragment = new Employer_HiredCandidatelistFragment();
                    Intent i = new Intent(getBaseContext(), DashBordActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                  //  addNotification("Hired Successfully");
                }
                else {
                    Toast.makeText(getBaseContext(), "error", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) { e.getMessage();}

        }
    }
//       public void addNotification(String s) throws Exception {
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.app_logo)
//                        .setContentTitle("Hired Status")
//                        .setContentText(s);
//        DashBordActivity.fragment = new Employer_HiredCandidatelistFragment();
//        Intent notificationIntent = new Intent(getBaseContext(), DashBordActivity.class);
//       // notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(contentIntent);
//        // Add as notification
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());
//    }
}