package com.example.dhruvi.job.jobseeker.categoryrecyclerview;

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
import com.example.dhruvi.job.jobseeker.subcategoryrecyclerview.Subcategory;

import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.Holder>{

    private Context context;
    private LayoutInflater inflater;
    List<BeanCat> beanList;


    public AdapterCategory(Context context, List<BeanCat> beanList) {
        this.context = context;
        inflater=LayoutInflater.from(context);
        this.beanList = beanList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterCategory.Holder(inflater.inflate(R.layout.jobseekercategorycard,parent,false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.categorytitle.setText(beanList.get(holder.getAdapterPosition()).title);
        Glide.with(context).load(Url.URL+"images/"+beanList.get(holder.getAdapterPosition()).image).into(holder.categoryimg);
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        ImageView categoryimg;
        TextView categorytitle;
        public Holder(View itemView) {
            super(itemView);

            categoryimg=itemView.findViewById(R.id.categoryimg);
            categorytitle=itemView.findViewById(R.id.categorytitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, Subcategory.class);
                    i.putExtra("catname",categorytitle.getText().toString());
                    context.startActivity(i);
                }
            });

        }
    }
}
