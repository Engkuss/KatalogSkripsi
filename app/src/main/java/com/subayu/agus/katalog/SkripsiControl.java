package com.subayu.agus.katalog;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by black4v on 15/05/2017.
 */

public class SkripsiControl extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<DataSkripsi> data= Collections.emptyList(),filterList;
    DataSkripsi current;
    int currentPos=0;

    public SkripsiControl(Context context, List<DataSkripsi> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
        this.filterList = new ArrayList<DataSkripsi>();
        this.filterList.addAll(this.data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_skripsi, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder= (MyHolder) holder;
        DataSkripsi current=filterList.get(position);
        myHolder.nimmhs.setText(current.NIM);
        myHolder.namamhs.setText(current.NAMAMHS);
        myHolder.judulmhs.setText(current.judul);
        myHolder.judulmhs.setTextColor(ContextCompat.getColor(context, R.color.org));

        Glide.with(context).load(R.drawable.katalogskrip)
                .placeholder(R.drawable.error)
                .error(R.drawable.error)
                .into(myHolder.imgmhs);
    }

    @Override
    public int getItemCount() {
        return (null != filterList ? filterList.size() : 0);
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
    // Do Search...
    public void filter(final String text) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {

                // Clear the filter list
                filterList.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {

                    filterList.addAll(data);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (DataSkripsi item : data) {
                        if (item.NAMAMHS.toLowerCase().contains(text.toLowerCase()) ||
                                item.NIM.toLowerCase().contains(text.toLowerCase()) ||
                                item.judul.toLowerCase().contains(text.toLowerCase())) {
                            // Adding Matched items
                            filterList.add(item);
                        }
                    }
                }

                // Set on UI Thread
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }

}
