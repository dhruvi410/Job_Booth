package com.example.dhruvi.job.company.acceptedemployee;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Url;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterAcceptedEmployee extends RecyclerView.Adapter<AdapterAcceptedEmployee.Holder>{

    Context context;
    LayoutInflater inflater;
    List<AcceptedBean> beanList;


    public AdapterAcceptedEmployee(Context context, List<AcceptedBean> beanList) {
        this.context = context;
        this.beanList = beanList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.companyaccepted,parent,false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.acceptcompname.setText(beanList.get(holder.getAdapterPosition()).name);
        holder.acceptcomptitle.setText(beanList.get(holder.getAdapterPosition()).title);
        Glide.with(context).load(Url.URL+"jobdp/"+beanList.get(holder.getAdapterPosition()).img).into(holder.acceptcompimg);
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CircleImageView acceptcompimg;
        TextView acceptcompname,acceptcomptitle;
        public Holder(View itemView) {
            super(itemView);
            acceptcompimg=itemView.findViewById(R.id.acceptcompimg);
            acceptcompname=itemView.findViewById(R.id.acceptcompname);
            acceptcomptitle=itemView.findViewById(R.id.acceptcomptitle);

        }
    }

}
