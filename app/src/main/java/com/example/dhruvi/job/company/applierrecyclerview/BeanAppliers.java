package com.example.dhruvi.job.company.applierrecyclerview;



public class BeanAppliers {

    public String name;
    public String email;
    public String title;
    public String img;
    public String id,seen;

    public BeanAppliers(String name, String img,String email,String title,String id,String seen) {
        this.name = name;
        this.img = img;
        this.email = email;
        this.title= title;
        this.id= id;
        this.seen=seen;
    }

    public BeanAppliers() {
    }
}
