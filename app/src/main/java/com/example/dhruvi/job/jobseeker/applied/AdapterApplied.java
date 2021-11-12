package com.example.dhruvi.job.jobseeker.applied;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dhruvi.job.CallServices;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Session;
import com.example.dhruvi.job.Url;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterApplied extends RecyclerView.Adapter<AdapterApplied.Holder> {

    Context context;
    LayoutInflater inflater;
    List<AppliedBean> beanList;
    ArrayList<String> key,value;
    String res,email,title;
    CallServices cs;
    Session s;

    public AdapterApplied(Context context, List<AppliedBean> beanList) {
        this.context = context;
        this.beanList = beanList;
        inflater=LayoutInflater.from(context);
        key=new ArrayList<>();
        value=new ArrayList<>();
        cs=new CallServices();
        s=new Session(context);
        email=s.checkLogin();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.appliedrv,parent,false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.appliedrvtitle.setText(beanList.get(holder.getAdapterPosition()).title);
        title=beanList.get(holder.getAdapterPosition()).name;
        key.add("email");
        key.add("title");
        //Log.e("titlel",title);
        Log.e("emmmil",email);

        value.add(email);
        value.add(beanList.get(holder.getAdapterPosition()).title);
        res=cs.CallServices(context,Url.URL+"getstatus.php",Url.METHOD,key,value);
        Log.e("stay",res);
        if(res.equals("true"))
        {
            holder.appliedrvstatus.setTextColor(Color.parseColor("#FF19BF07"));
            holder.appliedrvstatus.setText("Congratulation you are selected");
        }
        else if(res.equals("false"))
        {
            holder.appliedrvstatus.setTextColor(Color.parseColor("#FFE20404"));
            holder.appliedrvstatus.setText("Sorry, apply next time");
        }
        else
            holder.appliedrvstatus.setText("Pending......");
        holder.appliedrvname.setText(title);
        Glide.with(context).load(Url.URL+"companydb/"+beanList.get(holder.getAdapterPosition()).img).into(holder.appliedrvimg);
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        CircleImageView appliedrvimg;
        TextView appliedrvtitle,appliedrvname,appliedrvstatus;
        public Holder(View itemView) {
            super(itemView);
            appliedrvimg=itemView.findViewById(R.id.appliedrvimg);
            appliedrvtitle=itemView.findViewById(R.id.appliedrvtitle);
            appliedrvname=itemView.findViewById(R.id.appliedrvname);
            appliedrvstatus=itemView.findViewById(R.id.appliedrvstatus);
            Log.d("titleallo",appliedrvtitle.getText().toString());


        }
    }
}
