package com.subayu.agus.katalog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Menuu} interface
 * to handle interaction events.
 * Use the {@link Menuu} factory method to
 * create an instance of this fragment.
 */
public class Menuu extends Fragment implements View.OnClickListener{
    ImageView im1,im2,im3,im4;
    CardView ln1,ln2,ln3,ln4;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_menuu, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        ln1 = (CardView) view.findViewById(R.id.cv_main);
        ln2 = (CardView) view.findViewById(R.id.cv_main2);
        ln3 = (CardView) view.findViewById(R.id.cv_main3);
        ln4 = (CardView) view.findViewById(R.id.cv_main4);

        ln1.setOnClickListener(this);
        ln2.setOnClickListener(this);
        ln3.setOnClickListener(this);
        ln4.setOnClickListener(this);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cv_main:
                Intent intent = new Intent(getActivity(), Kategori.class);
                intent.putExtra("dt","1");
                getActivity().startActivity(intent);
                break;
            case R.id.cv_main2:
                Intent intent2 = new Intent(getActivity(), Kategori.class);
                intent2.putExtra("dt","2");
                getActivity().startActivity(intent2);
                break;
            case R.id.cv_main3:
                Intent intent21 = new Intent(getActivity(), Upload.class);
                getActivity().startActivity(intent21);
                break;
            case R.id.cv_main4:
                Intent intent211 = new Intent(getActivity(), About.class);
                getActivity().startActivity(intent211);
                break;

        }


    }
}
