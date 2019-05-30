package com.example.ramirez.examen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);

        //Ejecuta lo que tenemos en LectorRSS
        LectorRSS lectorRSS=new LectorRSS(this,recyclerView);
        lectorRSS.execute();
    }
}
