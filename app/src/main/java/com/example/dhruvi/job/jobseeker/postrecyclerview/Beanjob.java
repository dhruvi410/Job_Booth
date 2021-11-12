package com.example.dhruvi.job.jobseeker.postrecyclerview;

import android.content.Intent;

public class Beanjob {

    public String catid,seen;
    public String pid;
    public String title;
    public String salary;
    public String image;
    public String date;
    public String postby;
    public String email;
    Intent intent;

    public Beanjob() {
    }

    public Beanjob( String title, String image, String date, String postby,String email,String salary,String pid,String seen) {
        this.catid = catid;
        this.salary = salary;
        this.title = title;
        this.image = image;
        this.date = date;
        this.postby = postby;
        this.email = email;
        this.seen = seen;
        this.pid = pid;
    }
}
