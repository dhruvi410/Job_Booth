package com.example.dhruvi.job;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Url {

    //http://192.168.56.1/job_project/
    //192.168.56.
    public static String URL="http://192.168.43.131/job_project/";
    public static String METHOD="add";

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
