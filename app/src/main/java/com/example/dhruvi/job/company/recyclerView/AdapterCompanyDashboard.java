package com.example.dhruvi.job.company.recyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dhruvi.job.CallServices;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Session;
import com.example.dhruvi.job.Url;
import com.example.dhruvi.job.company.CompanydashboardActivity;
import com.example.dhruvi.job.company.applierrecyclerview.AppliersActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Krupali on 06-03-2019.
 */

public class AdapterCompanyDashboard extends RecyclerView.Adapter<AdapterCompanyDashboard.Holder>{

    private Context context;
    private LayoutInflater inflater;
    List<CategoryBean> beanList;
    CategoryBean cb;
    String img;
    ArrayList<String> key,value;
    Session s;
    CallServices cs;
    public AdapterCompanyDashboard(Context context, List<CategoryBean> beanList) {
        this.context = context;
        inflater=LayoutInflater.from(context);
        this.beanList = beanList;
        s=new Session(context);
        cs=new CallServices();
        key=new ArrayList<>();
        value=new ArrayList<>();
        key.add("email");
        value.add(s.checkLogin());
        img=cs.CallServices(context,Url.URL+"getimagecompany.php",Url.METHOD,key,value);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.activity_recyclerview_companydashboard,parent,false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.posttitle.setText(beanList.get(holder.getAdapterPosition()).title);
        holder.postby.append(beanList.get(holder.getAdapterPosition()).postby);
        holder.postdate.setText(beanList.get(holder.getAdapterPosition()).date);
        holder.email.append(beanList.get(holder.getAdapterPosition()).email);
        holder.salarpost.append(beanList.get(holder.getAdapterPosition()).salary);
        Glide.with(context).load(Url.URL+"companydb/"+img).into(holder.circleimg);

    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        CircleImageView circleimg;
        ImageView posteditbtn,postdeletebtn;
        TextView posttitle,postdate,postby,email,salarpost;


        public Holder(final View itemView) {
            super(itemView);
            circleimg=itemView.findViewById(R.id.circleimg);
            posttitle=itemView.findViewById(R.id.posttitle);
            postdate=itemView.findViewById(R.id.postdate);
            salarpost=itemView.findViewById(R.id.salarpost);
            posteditbtn=itemView.findViewById(R.id.posteditbtn);
            postdeletebtn=itemView.findViewById(R.id.postdeletebtn);
            postby=itemView.findViewById(R.id.postby);
            email=itemView.findViewById(R.id.email);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent intent=new Intent(context, AppliersActivity.class);
                   intent.putExtra("title",posttitle.getText().toString());
                   context.startActivity(intent);
                }
            });
            posteditbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context,Editpost.class);
                    i.putExtra("title",posttitle.getText().toString());
                    context.startActivity(i);
                    ((Activity)context).finish();
                }
            });
            postdeletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    key.clear();
                    value.clear();
                    key.add("title");
                    value.add(posttitle.getText().toString());
                    String resp=cs.CallServices(context,Url.URL+"deletepost.php",Url.METHOD,key,value);
                    if(resp.equals("1"))
                    {
                        Toast.makeText(context, "Deleted Succesfully", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context,CompanydashboardActivity.class));
                        ((Activity)context).finish();
                    }
                    else
                    {
                        Toast.makeText(context, "Not deleted", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
