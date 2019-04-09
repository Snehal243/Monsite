package com.montek.monsite.Fragments;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.*;
import android.widget.TextView;
import com.montek.monsite.R;
import static com.montek.monsite.ActivitySplash.p;
public class Employer_AboutUs extends Fragment {
        View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_aboutus, container, false);
        try{
            p=new ProgressDialog(getContext());
            TextView txv_aboutus = (TextView)v.findViewById(R.id.txv_aboutus);
            txv_aboutus.setText(Html.fromHtml(getString(R.string.aboutus)));
            txv_aboutus.setTextAppearance(getContext(), R.style.SimpleStyle);

        }catch (Exception e){}
        return v;
    }

}
