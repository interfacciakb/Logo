package com.example.logo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    EditText doctorCode;
    Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        doctorCode = (EditText) findViewById(R.id.doctor_code);
        next = (Button) findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procedi();
            }
        });
    }

    private void procedi() {
        Intent intentProcedi = new Intent(getApplicationContext(), SignupActivity2.class);
        Bundle bundle = new Bundle();
        bundle.putString("doctorcode", doctorCode.getText().toString());
        intentProcedi.putExtras(bundle);
        startActivity(intentProcedi);
    }

}
