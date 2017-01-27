package com.quizapp.quizbox.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.quizapp.quizbox.MainActivity;

import java.util.HashMap;

/**
 * Created by Lenovo on 16-01-2017.
 */

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;


    int PRIVATE_MODE=0;
    private static final String PREF_NAME="LoginPref";
    private static final String IS_LOGIN="IsLoggedIn";
    public static final String KEY_NAME="name";
    //public static final String KEY_EMAIL="email";
    public static final String KEY_USERNAME="username";


    public SessionManager(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=sharedPreferences.edit();
    }

    public void createLoginSession(String name,String username)
    {
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(KEY_NAME,name);
        // editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_USERNAME,username);
        editor.commit();
    }

    public void checkLogin()
    {
        if(!this.isLoggedIn())
        {
            Intent intent=new Intent(context,Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, sharedPreferences.getString(KEY_NAME, null));

        //user.put(KEY_EMAIL, sharedPreferences.getString(KEY_EMAIL, null));
        user.put(KEY_USERNAME,sharedPreferences.getString(KEY_USERNAME,null));
        return user;
    }



    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }




    public boolean isLoggedIn()
    {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }
}
