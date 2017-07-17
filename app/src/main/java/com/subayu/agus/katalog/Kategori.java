package com.subayu.agus.katalog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Kategori extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView ls;
    String [] fakultas = {"Fakultas Teknik","Fakultas Ekonomi","FKIP","Fakultas Peternakan","Fakultas Perikanan","Fakultas Hukum","Fakultas Kebidanan","Fakultas Agama Islam"};
    String [] nama = {"TEKNIK","EKONOMI","FKIP","PETERNAKAN","PERIKANAN","HUKUM","KEBIDANAN","FAI"};
    String dt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);
        getSupportActionBar().setTitle(R.string.kl);

        ls = (ListView)findViewById(R.id.ls);

        Intent in = getIntent();
        dt = in.getStringExtra("dt");
        ls.setAdapter(new ArrayAdapter<>(this, R.layout.item, R.id.text,fakultas));
        ls.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String key = nama[position];
        switch (dt){
            case "1":
                Intent intent = new Intent(Kategori.this,ListSkripsi.class);
                intent.putExtra("key",key);
                startActivity(intent);
                break;
            case "2":
                Intent intent2 = new Intent(Kategori.this,ListPKL.class);
                intent2.putExtra("key",key);
                startActivity(intent2);
                break;
        }

    }
}
