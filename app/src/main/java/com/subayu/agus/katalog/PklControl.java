package com.subayu.agus.katalog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

/**
 * Created by black4v on 29/05/2017.
 */

public class PklControl extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<DataPKL> data= Collections.emptyList();
    DataPKL current;
    int currentPos=0;

    public PklControl(Context context, List<DataPKL> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_skripsi, parent,false);
        PklControl.MyHolder holder=new PklControl.MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PklControl.MyHolder myHolder= (PklControl.MyHolder) holder;
        DataPKL current = data.get(position);
        myHolder.nimmhs.setText(current.NIM);
        myHolder.namamhs.setText(current.NAMAMHS);
        myHolder.judulmhs.setText(current.jdl_pkl);
        myHolder.judulmhs.setTextColor(ContextCompat.getColor(context, R.color.org));

        Glide.with(context).load(R.drawable.katalogpkl)
              .placeholder(R.drawable.error)
            .error(R.drawable.error)
          .into(myHolder.imgmhs);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView namamhs;
        ImageView imgmhs;
        TextView nimmhs;
        TextView judulmhs;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            namamhs= (TextView) itemView.findViewById(R.id.namamhs);
            imgmhs= (ImageView) itemView.findViewById(R.id.imgskripsi);
            nimmhs = (TextView) itemView.findViewById(R.id.nimmhs);
            judulmhs = (TextView) itemView.findViewById(R.id.jdlmhs);
        }

    }
}
