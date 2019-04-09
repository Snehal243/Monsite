package com.montek.monsite;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.montek.monsite.Fragments.*;
import com.montek.monsite.extra.GlobalVariable;
import com.montek.monsite.extra.UserSessionManager;

import static com.montek.monsite.ActivitySplash.session;
public class DashBordActivity extends AppCompatActivity {
    public static  Fragment frag;
    private DrawerLayout dLayout;
    public static Fragment fragment;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);
        try {
            setNavigationDrawer();
            setToolBar();
            session = new UserSessionManager(getApplicationContext());
            if (GlobalVariable.Employer_id.equals(null) || GlobalVariable.Employer_id.isEmpty() || GlobalVariable.Employer_id.equals("null")) {
                FacebookSdk.sdkInitialize(getBaseContext());
                Log.d("hiiiii", "ghhhhh");
                Activity_Login.session();//session
                Log.d("Emailllll", GlobalVariable.Employer_email);
            }
            if (fragment == null) {
                fragment = new Employer_SearchFragment();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commit();
        }catch (Exception e){}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    private void setToolBar() throws Exception {
        ActionBar ab = getSupportActionBar();
        if( ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_view_list_black_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
    private void setNavigationDrawer() throws Exception {
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment frag = null;
                switch (menuItem.getItemId())
               {
                case  R.id.Profile:
                    getSupportActionBar().setTitle("Profile");
                    frag = new Employer_ProfileFragment();
                    break;
                case R.id.Job_Search:
                    getSupportActionBar().setTitle("DashBoard");
                    frag = new Employer_SearchFragment();
                    break;
                case R.id.Send_requirment:
                    getSupportActionBar().setTitle("Send Requirment");
                    frag = new SendRequirment_Fragment();
                    break;
                case R.id.action_editrequirment:
                    getSupportActionBar().setTitle("List of Requirements");
                    frag = new RequirmentList_Fragment();
                    break;
                case   R.id.Hired_Candidates:
                    getSupportActionBar().setTitle("Hired Candidates List");
                    frag = new Employer_HiredCandidatelistFragment();
                    break;
                case   R.id.action_about:
                    getSupportActionBar().setTitle("About Us");
                    frag = new Employer_AboutUs();
                    break;
                case   R.id.action_callReuest:
                    getSupportActionBar().setTitle("Conference call Request");
                    frag = new Employer_ConferenceCall();
                    break;
                case  R.id.action_logout:
                    try {
                        Logout();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.action_call:
                    String phone = "02041303300";
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                    break;
                default:
                    break;
            }
                if(frag!=null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, frag);
                    transaction.addToBackStack("DashBoard");
                    transaction.commit();
                    dLayout.closeDrawers();
                }
                return false;
            }
        });
    }
//    @Override
//    public void onBackPressed() {
//        dLayout.closeDrawers();
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
//    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int backstack = getSupportFragmentManager().getBackStackEntryCount();
        Log.d("backstack", String.valueOf(backstack));
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (backstack > 0) {
            super.onBackPressed();
            Log.d("gggggg","ggdgdggg");
        } else {
            if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
        }
    }
    public void Logout()  throws Exception{
        Log.d("hh","hhh");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to LogOut?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                session.logoutUser();//to clear all account details from session
                finish();
                Log.d("hh11","hhh111");
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case android.R.id.home: {
                if(dLayout.isDrawerOpen(GravityCompat.START)) {
                    dLayout.closeDrawers();
                }
                else
                {
                    dLayout.openDrawer(GravityCompat.START);
                }
                return true;
            }
        }
        return true;
    }
}