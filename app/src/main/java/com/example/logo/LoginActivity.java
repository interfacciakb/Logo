package com.example.logo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
public class LoginActivity extends AppCompatActivity {

    Button accedi;
    Button registrati;
    EditText email;
    EditText password;
    RequestQueue requestQueue;
    ConstraintLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accedi = (Button) findViewById(R.id.login);
        registrati = (Button) findViewById(R.id.signup);
        email = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        container = (ConstraintLayout) findViewById(R.id.container);
        requestQueue = Volley.newRequestQueue(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String mailSaved = prefs.getString("USERNAME", null);
        String passSaved = prefs.getString("PASSWORD", null);


        if (mailSaved != null && passSaved != null  ) {
            email.setText(mailSaved);
            password.setText(passSaved);
            accedi();
        }

        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrati();
            }
        });

        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(getApplicationContext(), accedi.getWindowToken());
                accedi();
            }
        });

    }

    private void accedi() {
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        if(emailText.equalsIgnoreCase("") ||
                passwordText.equalsIgnoreCase("") ){
                //Snackbar.make(container,"Completare correttamente tutti i campi!",Snackbar.LENGTH_LONG).show();
                Snackbar.make(findViewById(android.R.id.content), "Completare correttamente tutti i campi!", Snackbar.LENGTH_LONG).show();
        } else{
            login(emailText,passwordText);
        }
    }
    private void login(String emailLogin,String passwordLogin){
        String URL = "http://www.logopediapp.altervista.org/database/crud_caregiver/read_condition.php?email="+emailLogin+"&password="+passwordLogin;

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
                            //String messageServer = response.getString("message");
                            //if(messageServer.isEmpty()){
                                JSONArray jsonArray = response.getJSONArray("records");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String email = object.getString("email");
                                    System.out.println(email);
                                    if(email!=null){
                                        Intent intentLogin = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intentLogin);
                                    }
                                }
                            //}else{
                            //    System.out.println(messageServer);
                            //}
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


    private void registrati() {
        Intent intentRegistrati = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intentRegistrati);
    }

    private void snackbarError(){
        Snackbar.make(findViewById(android.R.id.content), "Email o Password errate!", Snackbar.LENGTH_LONG).show();
    }

    private String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }
    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }

}
