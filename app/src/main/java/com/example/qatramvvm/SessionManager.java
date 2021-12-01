package com.example.qatramvvm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    public static final String SESSION_USER_SESSION = "userLoginSession";
    public static final String SESSION_REMEMBER_ME = "rememberMe";

    /* user session variables*/
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD= "password";
    public static final String KEY_EMAIL= "email";
    public static final String KEY_ADDRESS= "address";
    public static final String KEY_GROUP= "group";

    /* remember me session variables*/
    private static final String IS_REMEMBERME = "IsRememberMe";
    public static final String KEY_REMEMBERME_PHONE = "phone";
    public static final String KEY_REMEMBERME_PASSWORD= "password";

    public SessionManager(Context context, String sessionName){
        this.context = context;
        pref = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    /* user session */
    public void createLoginSession(String phone, String password){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }

    public void createUserDataSession(String user_id, String name, String phone, String email, String address, String group){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER_ID, user_id);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_GROUP, group);
        editor.commit();
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        return user;
    }


    public HashMap<String, String> getUserData(){
        HashMap<String, String> userdata = new HashMap<String, String>();
        userdata.put(KEY_USER_ID, pref.getString(KEY_USER_ID, null));
        userdata.put(KEY_NAME, pref.getString(KEY_NAME, null));
        userdata.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        userdata.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));
        userdata.put(KEY_GROUP, pref.getString(KEY_GROUP, null));
        return userdata;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }


    /* remember me session */
    public void createRememberMeSession(String phone, String password){
        editor.putBoolean(IS_REMEMBERME, true);
        editor.putString(KEY_REMEMBERME_PHONE, phone);
        editor.putString(KEY_REMEMBERME_PASSWORD, password);
        editor.commit();
    }


    public HashMap<String, String> getRememberMeDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_REMEMBERME_PHONE, pref.getString(KEY_REMEMBERME_PHONE, null));
        user.put(KEY_REMEMBERME_PASSWORD, pref.getString(KEY_REMEMBERME_PASSWORD, null));
        return user;
    }

    public boolean checkRememberMe(){
        if(pref.getBoolean(IS_REMEMBERME, false)){
            return true;
        }
        else {
            return false;
        }
    }

    public void removeRememberMeSession(){
        editor.clear();
        editor.commit();
    }

}
