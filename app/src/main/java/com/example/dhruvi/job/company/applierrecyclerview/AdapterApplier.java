package com.example.dhruvi.job.company.applierrecyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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


public class AdapterApplier extends RecyclerView.Adapter<AdapterApplier.Holder> {

    private Context context;
    private LayoutInflater inflater;
    private List<BeanAppliers> beanAppliers;
    private ArrayList<String> key, value;
    private CallServices cs;

    AdapterApplier(Context context, List<BeanAppliers> beanAppliers) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.beanAppliers = beanAppliers;
        key = new ArrayList<>();
        value = new ArrayList<>();
        cs = new CallServices();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterApplier.Holder(inflater.inflate(R.layout.appliers_recyclerview, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(Holder holder, int position) {


        if (beanAppliers.get(holder.getAdapterPosition()).seen.equals("true"))
            holder.applierseen.setText("Seen");
        else {
            holder.applierseen.setText("Unseen");
        }
        holder.appliername.setText(beanAppliers.get(holder.getAdapterPosition()).name);
        holder.applieremail.setText(beanAppliers.get(holder.getAdapterPosition()).email);
        Glide.with(context).load(Url.URL + "jobdp/" + beanAppliers.get(holder.getAdapterPosition()).img).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return beanAppliers.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView applierseen;
        CardView applier_recyclerview_cv;
        TextView appliername, applieremail;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        Holder(View itemView) {
            super(itemView);

            appliername = itemView.findViewById(R.id.appliername);
            applieremail = itemView.findViewById(R.id.applieremail);
            applierseen = itemView.findViewById(R.id.applierseen);
            applier_recyclerview_cv = itemView.findViewById(R.id.applier_recyclerview_cv);
            img = itemView.findViewById(R.id.applierimage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AppliersInfoActivity.class);
                    intent.putExtra("appliername", appliername.getText().toString());
                    key.add("id");
                    value.add(beanAppliers.get(getAdapterPosition()).id);
                    cs.CallServices(context, Url.URL + "changeseenpot.php", Url.METHOD, key, value);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            });

        }
    }
}
