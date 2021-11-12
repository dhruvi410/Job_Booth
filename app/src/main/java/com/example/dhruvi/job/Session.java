package com.example.dhruvi.job;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {


        Context context;
        SharedPreferences preferences;
        SharedPreferences.Editor editor;


        public Session(Context context) {
            this.context = context;
            preferences=context.getSharedPreferences("jobboothsessionfile",Context.MODE_PRIVATE);
            editor=preferences.edit();

        }

        public void setLogin(String unm)
        {
            editor.putString("unm",unm).commit();
        }
        public void setPwd(String pwd)
        {
            editor.putString("pwd",pwd).commit();
        }
        public String getPwd(){

            String pwdd=preferences.getString("pwd","");
            return pwdd;
        }

        public void setType(String type) {
            editor.putString("type",type).commit();
        }

        public String getType()
        {
            String type=preferences.getString("type","");
            return type;
        }

        public String checkLogin()
        {
            String res=preferences.getString("unm","");
            return res;
        }

        public void logout()
        {
            editor.clear().commit();
        }
    }


