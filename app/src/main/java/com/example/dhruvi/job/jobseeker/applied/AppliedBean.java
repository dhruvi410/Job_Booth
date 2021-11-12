package com.example.dhruvi.job.jobseeker.applied;

public class AppliedBean {
    String img,title,name;

    public AppliedBean(String img, String title, String name) {
        this.img = img;
        this.title = title;
        this.name = name;
    }

    public AppliedBean() {
    }

    public AppliedBean(String title, String name) {
        this.title = title;
        this.name = name;
    }
}
