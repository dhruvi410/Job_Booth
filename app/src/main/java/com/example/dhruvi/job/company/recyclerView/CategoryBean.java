package com.example.dhruvi.job.company.recyclerView;

/**
 * Created by Krupali on 06-03-2019.
 */

public class CategoryBean {
    public String catid;
    public String title;
    public String image;
    public String date;
    public String postby;
    public String salary;
    public String email;


    public CategoryBean() {
    }

    public CategoryBean(String catid, String title, String image, String date, String postby,String salary,String email) {
        this.catid = catid;
        this.title = title;
        this.image = image;
        this.date = date;
        this.postby = postby;
        this.salary = salary;
        this.email = email;
    }



}

