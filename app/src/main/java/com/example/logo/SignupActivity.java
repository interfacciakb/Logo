package com.example.logo;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.logo.LoginActivity.closeKeyboard;

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
                closeKeyboard(getApplicationContext(), next.getWindowToken());
                controllaCodice();
            }
        });
    }

    private void controllaCodice() {
        String codicePaziente = doctorCode.getText().toString();
        if(codicePaziente.equalsIgnoreCase("") ){
            Snackbar.make(findViewById(android.R.id.content), "Inserire il codice paziente fornito dal dottore!", Snackbar.LENGTH_LONG).show();
        } else{
            procedi(codicePaziente);
        }
    }

    private void procedi(String codicePaziente) {
        String URL = "http://www.logopediapp.altervista.org/database/crud_paziente/read_condition.php?condition=codice=\""+codicePaziente+"\"";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Initialize a new JsonArrayRequest instance
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray jsonArray = response.getJSONArray("records");

                            Intent intentProcedi = new Intent(getApplicationContext(), SignupActivity2.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("doctorcode", doctorCode.getText().toString());
                            intentProcedi.putExtras(bundle);
                            startActivity(intentProcedi);
                        }catch (JSONException e){
                            e.printStackTrace();
                            snackbarError();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

    private void snackbarError(){
        Snackbar.make(findViewById(android.R.id.content), "Codice paziente errato!", Snackbar.LENGTH_LONG).show();
    }
}
