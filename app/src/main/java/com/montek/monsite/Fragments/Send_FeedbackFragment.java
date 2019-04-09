//package com.montek.monsite.Fragments;
//import android.app.ProgressDialog;
//import android.graphics.Color;
//import android.graphics.PorterDuff;
//import android.graphics.drawable.Drawable;
//import android.os.*;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.text.*;
//import android.util.Log;
//import android.view.*;
//import android.widget.*;
//import com.montek.monsite.JsonConnection.RequestHandler;
//import com.montek.monsite.R;
//import com.montek.monsite.extra.*;
//import com.montek.monsite.extra.URLs;
//import org.json.JSONObject;
//import java.util.HashMap;
//import static com.montek.monsite.ActivitySplash.p;
//import static com.montek.monsite.Activity_Login.ProgressD;
//public class Send_FeedbackFragment extends Fragment implements View.OnClickListener  {
//    Button btn_send;
//    TextView txv_length;
//    EditText edtx_feedback;
//    String Feedback,ratingvalue;
//    View v;
//    RatingBar ratingBar;
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//         v = inflater.inflate(R.layout.acitivity_feedback, container, false);
//        iUI();
//        p=new ProgressDialog(getContext());
//        ratingBar.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    float touchPositionX = event.getX();
//                    float width = ratingBar.getWidth();
//                    float starsf = (touchPositionX / width) * 5.0f;
//                    int stars = (int)starsf + 1;
//                    ratingBar.setRating(stars);
//                    v.setPressed(false);
//                }
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    v.setPressed(true);
//                }
//                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
//                    v.setPressed(false);
//                }
//                return true;
//            }});
//        edtx_feedback.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void afterTextChanged(Editable s) {
//                txv_length.setText(String.valueOf(300 - edtx_feedback.length())+"/300");
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int st, int b, int c)
//            { }
//            @Override
//            public void beforeTextChanged(CharSequence s, int st, int c, int a)
//            { }
//        });
//        return v;
//    }
//    private void iUI() {
//        btn_send=(Button)v.findViewById(R.id.btn_send);
//        btn_send.setOnClickListener(this);
//        txv_length = (TextView)v.findViewById(R.id.txv_length);
//        txv_length = (TextView)v.findViewById(R.id.txv_length);
//        edtx_feedback = (EditText)v.findViewById(R.id.edtx_feedback);
//        ratingBar=(RatingBar)v.findViewById(R.id.rb_ratingBar1);
//        ratingBar.setNumStars(5);
//        Drawable drawable = ratingBar.getProgressDrawable();
//        drawable.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
//    }
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_send:
//                Feedback=edtx_feedback.getText().toString().trim();
//                if (TextUtils.isEmpty(Feedback)) {
//                    edtx_feedback.setError("Please enter Feedback");
//                    edtx_feedback.requestFocus();
//                    return;
//                }
//                ratingvalue=String.valueOf(ratingBar.getRating());
//                new Sendfeedback().execute();
//                break;
//        }
//    }
//    class Sendfeedback extends AsyncTask<Void, Void, String> {
//        @Override
//        protected String doInBackground(Void... voids) {
//            RequestHandler requestHandler = new RequestHandler();
//            HashMap<String, String> params = new HashMap<>();
//            params.put("email", GlobalVariable.Employer_email);
//            params.put("Feedback", Feedback);
//            params.put("hiredfreelancer_id", "0");
//            params.put("rating", ratingvalue);
//            return requestHandler.sendPostRequest(URLs.URL_sendfeedback, params);
//        }
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//           ProgressD();
//        }
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            p.dismiss();
//            try {
//                Log.d("Json response:", s.toString());
//                Log.d("id", s.toString());
//                JSONObject obj = new JSONObject(s);
//                if (!obj.getBoolean("error")) {
//                    Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//                    edtx_feedback.setText("");
//                }
//                else {
//                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
//                }
//            } catch (Exception e) {e.getMessage();}
//        }
//    }
//}