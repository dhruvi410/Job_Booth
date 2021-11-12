package com.example.dhruvi.job.jobseeker.subcategoryrecyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Url;
import com.example.dhruvi.job.jobseeker.postrecyclerview.JobseekerviewpostActivity;

import java.util.List;

public class AdapterSub extends RecyclerView.Adapter<AdapterSub.Holder> {

    private Context context;
    private LayoutInflater inflater;
    List<Beansub> beanList;


    public AdapterSub(Context context, List<Beansub> beanList) {
        this.context = context;
        inflater=LayoutInflater.from(context);
        this.beanList = beanList;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterSub.Holder(inflater.inflate(R.layout.jobseekersubcard,parent,false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.subtitle.setText(beanList.get(holder.getAdapterPosition()).title);
        Glide.with(context).load(Url.URL+"images/"+beanList.get(holder.getAdapterPosition()).image).into(holder.subimg);
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        ImageView subimg;
        TextView subtitle;
        public Holder(View itemView) {
            super(itemView);

            subimg=itemView.findViewById(R.id.subimg);
            subtitle=itemView.findViewById(R.id.subtitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, JobseekerviewpostActivity.class);
                    i.putExtra("subname",subtitle.getText().toString());
                    context.startActivity(i);
                }
            });
        }
    }
}
