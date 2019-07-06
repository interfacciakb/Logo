package com.example.logo.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.logo.Adapters.AppuntamentiAdapter;
import com.example.logo.Entities.Appuntamento;
import com.example.logo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AppuntamentiPassatiFrg extends AbFrg {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    RequestQueue request;
    private ArrayList<Appuntamento> ranks = new ArrayList<Appuntamento>();
    //String email;
    //String apikey;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //request =  Volley.newRequestQueue(getActivity().getApplicationContext());
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        // email = preferences.getString("USERNAME", null);
        //apikey = preferences.getString("APIKEY", null);

        loadRank("a1a4c3c4-9b04-472e-b1c0");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);


    }

    @Override
    protected View viewOfFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_app_passati,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_app_passati);
        layoutManager = new LinearLayoutManager(getContext());
        //request =  Volley.newRequestQueue(getActivity().getApplicationContext());

        //loadAppPassati();
        return v;

    }

    @Override
    protected void onResumeReload(View v) {

    }

    private void loadAppPassati(){
        ranks.clear();
        ranks.add(new Appuntamento("15 MAY", "Ospedaletto", "Dott.ssa Bruni", "16:00"));
        ranks.add(new Appuntamento("21 MAY", "Policlinico", "Dott.ssa Bruna", "17:00"));
        ranks.add(new Appuntamento("15 MAY", "Ospedaletto", "Dott.ssa Bruni", "16:00"));
        ranks.add(new Appuntamento("21 MAY", "Policlinico", "Dott.ssa Bruna", "17:00"));
        ranks.add(new Appuntamento("15 MAY", "Ospedaletto", "Dott.ssa Bruni", "16:00"));
        ranks.add(new Appuntamento("21 MAY", "Policlinico", "Dott.ssa Bruna", "17:00"));
        ranks.add(new Appuntamento("15 MAY", "Ospedaletto", "Dott.ssa Bruni", "16:00"));
        ranks.add(new Appuntamento("21 MAY", "Policlinico", "Dott.ssa Bruna", "17:00"));
        ranks.add(new Appuntamento("15 MAY", "Ospedaletto", "Dott.ssa Bruni", "16:00"));
        ranks.add(new Appuntamento("21 MAY", "Policlinico", "Dott.ssa Bruna", "17:00"));
        ranks.add(new Appuntamento("15 MAY", "Ospedaletto", "Dott.ssa Bruni", "16:00"));
        ranks.add(new Appuntamento("21 MAY", "Policlinico", "Dott.ssa Bruna", "17:00"));
        ranks.add(new Appuntamento("15 MAY", "Ospedaletto", "Dott.ssa Bruni", "16:00"));
        ranks.add(new Appuntamento("21 MAY", "Policlinico", "Dott.ssa Bruna", "17:00"));
        ranks.add(new Appuntamento("15 MAY", "Ospedaletto", "Dott.ssa Bruni", "16:00"));
        ranks.add(new Appuntamento("21 MAY", "Policlinico", "Dott.ssa Bruna", "17:00"));
        ranks.add(new Appuntamento("15 MAY", "Ospedaletto", "Dott.ssa Bruni", "16:00"));
        ranks.add(new Appuntamento("21 MAY", "Policlinico", "Dott.ssa Bruna", "17:00"));
        ranks.add(new Appuntamento("15 MAY", "Ospedaletto", "Dott.ssa Bruni", "16:00"));
        ranks.add(new Appuntamento("21 MAY", "Policlinico", "Dott.ssa Bruna", "17:00"));
        ranks.add(new Appuntamento("15 MAY", "Ospedaletto", "Dott.ssa Bruni", "16:00"));
        ranks.add(new Appuntamento("21 MAY", "Policlinico", "Dott.ssa Bruna", "17:00"));
        ranks.add(new Appuntamento("15 MAY", "Ospedaletto", "Dott.ssa Bruni", "16:00"));
        ranks.add(new Appuntamento("21 MAY", "Policlinico", "Dott.ssa Bruna", "17:00"));
        ranks.add(new Appuntamento("15 MAY", "Ospedaletto", "Dott.ssa Bruni", "16:00"));
        ranks.add(new Appuntamento("21 MAY", "Policlinico", "Dott.ssa Bruna", "17:00"));
        ranks.add(new Appuntamento("15 MAY", "Ospedaletto", "Dott.ssa Bruni", "16:00"));
        ranks.add(new Appuntamento("21 MAY", "Policlinico", "Dott.ssa Bruna", "17:00"));
    }

    private void loadRank(String codice_paziente){
        String URL = "http://www.logopediapp.altervista.org/database/crud_terapia/read_condition.php?condition=paziente_codice=\""+codice_paziente+"\"+AND+data_fine<CURRENT_DATE+ORDER+BY+data_fine";
        //System.out.println(URL);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

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
                            ranks.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String data_fine = object.getString("data_fine");
                                String luogo_appuntamento = object.getString("luogo_appuntamento");
                                String medico = object.getString("medico_email");
                                System.out.println(data_fine);
                                System.out.println(luogo_appuntamento);
                                System.out.println(medico);

                                ranks.add(new Appuntamento(data_fine, luogo_appuntamento, medico, data_fine));
                            }

                            adapter = new AppuntamentiAdapter(ranks,getContext());
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(layoutManager);
                        }catch (JSONException e){
                            e.printStackTrace();
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
}