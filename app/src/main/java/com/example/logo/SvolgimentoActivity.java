package com.example.logo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SvolgimentoActivity extends AppCompatActivity {

    private String idsvolgimento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svolgimento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent svolgimento = getIntent();
        Bundle svolgimentoBundle = svolgimento.getExtras();
        if(!svolgimentoBundle.isEmpty()){

            idsvolgimento = svolgimentoBundle.getString("idsvolgimento");
            System.out.println(idsvolgimento);
        }
    }

}
