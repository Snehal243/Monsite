package com.montek.monsite.extra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.montek.monsite.Activity_Login;

import java.util.HashMap;
public class UserSessionManager {
    // Shared Preferences reference
    SharedPreferences pref;
    // Editor reference for Shared preferences
   public static Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    public static final String KEY_Userid = "user_id";
    // Email address (make variable public to access from outside)
 //   public static final String key_last_name = "last_name";
    public static final String key_mobile = "mobile";
    public static final String key_email = "email";
    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences("AndroidExamplePref", PRIVATE_MODE);
        editor = pref.edit();
    }
    //Create login session
    public static void createUserLoginSession(String user_id,String mobile,String email){//},String address_line_1,String address_line_2,String registration_status ){
        // Storing login value as TRUE
        editor.putBoolean("IsUserLoggedIn", true);
        // Storing name in pref
        editor.putString(KEY_Userid, user_id);
       // editor.putString(key_last_name, last_name);
        editor.putString(key_mobile, mobile);
        editor.putString(key_email, email);
        editor.apply();
        editor.commit();
    }
    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Activity_Login.class);
            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);
            return true;
        }
        return false;
    }
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_Userid, pref.getString(KEY_Userid, null));
      //  user.put(key_last_name, pref.getString(key_last_name, null));
        user.put(key_mobile, pref.getString(key_mobile, null));
        user.put(key_email, pref.getString(key_email, null));
      //  user.put(key_add1, pref.getString(key_add1, null));
      //  user.put(key_add2, pref.getString(key_add2, null));
      //  user.put(key_registration_status, pref.getString(key_registration_status, null));
        // return user
        return user;
    }
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.apply();
        editor.commit();
        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, Activity_Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        _context.startActivity(i);
    }
    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean("IS_USER_LOGIN", false);
    }
}