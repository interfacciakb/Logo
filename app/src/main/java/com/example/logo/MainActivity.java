package com.example.logo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.logo.Fragment.AppuntamentiFragment;
import com.example.logo.Fragment.MainFragment;
import com.example.logo.util.Bitmap;
import com.example.logo.util.CircleImageView;
import com.example.logo.util.InterationWithMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, InterationWithMain {

    private String codicePaziente = "a1a4c3c4-9b04-472e-b1c0";

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private String email;
    private MainActivity activity;
    private SharedPreferences prefs;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        changeContainer(new MainFragment(), getResources().getString(R.string.nav_home), R.id.nav_home);
        requestQueue = Volley.newRequestQueue(this);

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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //email = prefs.getString("USERNAME", null);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        setHeader(codicePaziente,headerView);

        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            changeContainer(new MainFragment(), getResources().getString(R.string.nav_home), id);
        } else if (id == R.id.nav_appuntamenti) {
            changeContainer(new AppuntamentiFragment(), getResources().getString(R.string.nav_appuntamenti), id);
        } else if (id == R.id.nav_ricompense) {
            //changeContainer(new familyGoalFrg(), getResources().getString(R.string.nav_obiettivi), id);
        } else if (id == R.id.nav_scenario) {
            //changeContainer(new familyGoalFrg(), getResources().getString(R.string.nav_obiettivi), id);
        } else if (id == R.id.nav_settings) {
            //changeContainer(new familyGoalFrg(), getResources().getString(R.string.nav_obiettivi), id);
        } else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeContainer(Fragment frg, String title, int id_navItem) {
        String cripted = title + ";" + id_navItem;
        getSupportFragmentManager().beginTransaction().setCustomAnimations(
                android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fragment_container, frg, "setFragment").addToBackStack(cripted).commit();
        setTitle(title);
    }



    private void setHeader(String codice, View headerView){
        final ImageView userImage;
        final TextView patientName;
        final TextView caregiverEmail;

        String URL = "http://www.logopediapp.altervista.org/database/crud_paziente/read_condition.php?condition=codice='" + codice + "'";
        userImage = (ImageView ) headerView.findViewById(R.id.imageView);
        patientName = (TextView) headerView.findViewById(R.id.patientName);
        caregiverEmail = (TextView) headerView.findViewById(R.id.caregiverEmail);


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
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);

                                String nome = object.getString("nome");
                                String cognome = object.getString("cognome");
                                String emailCaregiver = object.getString("caregiver_email");
                                //todo prendere la vera immagine dal db (in pazienti non è presente l'immagine come stringa)

                                /*
                                String immagineProfilo = object.getString("immagine_profilo");
                                String imgDecoded = immagineProfilo.replace("data:image/jpeg;base64,","");
                                userImage.setImageBitmap(Bitmap.getBitmapFromString(imgDecoded));
                                */
                                String img = "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";
                                patientName.setText(nome + " " + cognome);
                                caregiverEmail.setText(emailCaregiver);
                                userImage.setImageBitmap(Bitmap.getBitmapFromString(img));
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                       //todo
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void changeFragment(String tag, Bundle... bundle) {
        /*if(tag.compareTo(familyGoalFrg.TAG)==0){
            changeContainer(new addGoalFrg(), "Aggiungi nuovi obbiettivi",13);
        } else if(tag.compareTo(rulesFrg.TAG)==0) {
            changeContainer(new TimePickerFragment(), "Seleziona orario", 12);
        }*/
    }

    public void logout(){
        Intent intentLogout = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intentLogout);
    }
}
