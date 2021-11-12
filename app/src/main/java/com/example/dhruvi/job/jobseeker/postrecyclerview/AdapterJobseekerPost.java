package com.example.dhruvi.job.jobseeker.postrecyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dhruvi.job.CallServices;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Url;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterJobseekerPost extends RecyclerView.Adapter<AdapterJobseekerPost.Holder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Beanjob> beanList;
    String pid;
    ArrayList<String> key,value;
    CallServices cs;


    public AdapterJobseekerPost(Context context, List<Beanjob> beanList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.beanList = beanList;
        key=new ArrayList<>();
        value=new ArrayList<>();
        cs=new CallServices();
    }


    public AdapterJobseekerPost() {
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterJobseekerPost.Holder(inflater.inflate(R.layout.jobseekerpostrecylerview, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.posttitle.setText(beanList.get(holder.getAdapterPosition()).title);
        holder.postby.append(beanList.get(holder.getAdapterPosition()).postby);
        holder.postdate.setText(beanList.get(holder.getAdapterPosition()).date);
        holder.jemail.append(beanList.get(holder.getAdapterPosition()).email);
        pid=beanList.get(holder.getAdapterPosition()).pid;
        if (beanList.get(holder.getAdapterPosition()).seen.equals("true"))
        {
            holder.jpostseen.setText("Seen");
        }
        else
        {
            holder.jpostseen.setText("Unseen");
        }
        holder.jpostsalary.append(beanList.get(holder.getAdapterPosition()).salary);
        Log.e("iiiii",beanList.get(holder.getAdapterPosition()).image);
        Glide.with(context).load(Url.URL + "companydb/" + beanList.get(holder.getAdapterPosition()).image).into(holder.circleimg);
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        CircleImageView circleimg;
        TextView posttitle, postdate, postby, jemail,jpostsalary,jpostseen;

        Holder(View itemView) {
            super(itemView);

            circleimg = itemView.findViewById(R.id.jcircleimg);
            posttitle = itemView.findViewById(R.id.jposttitle);
            jemail = itemView.findViewById(R.id.jemail);
            jpostseen = itemView.findViewById(R.id.jpostseen);
            postdate = itemView.findViewById(R.id.jpostdate);
            jpostsalary = itemView.findViewById(R.id.jpostsalary);
            postby = itemView.findViewById(R.id.jpostby);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    key.add("pid");
                    value.add(pid);
                    cs.CallServices(context,Url.URL+"chnageseenjobpost.php",Url.METHOD,key,value);
                    Intent i = new Intent(context, JobseekerpostInfo.class);
                    i.putExtra("title", posttitle.getText().toString());
                    context.startActivity(i);
                }
            });

        }
    }
}
