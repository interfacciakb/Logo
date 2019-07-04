package com.example.logo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

    }

    @Override
    protected View viewOfFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_app_passati,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_app_passati);
        layoutManager = new LinearLayoutManager(getContext());
        //request =  Volley.newRequestQueue(getActivity().getApplicationContext());
        //loadRank(email,apikey)
        loadAppPassati();
        adapter = new AppuntamentiAdapter(ranks,getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        return v;

    }

    @Override
    protected void onResumeReload(View v) {

    }

    private void loadAppPassati(){
        ranks.clear();
        ranks.add(new Appuntamento("15 MAY", "Ospedaletto", "Dott.ssa Bruni", "16:00"));
        ranks.add(new Appuntamento("21 MAY", "Policlinico", "Dott.ssa Bruna", "17:00"));

    }

    /**
    private void loadRank(String email, String apikey){
        String url = "ulr?email="+email+"&apikey="+apikey;
        JsonArrayRequest soloRankRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            private ArrayList<Appuntamento> list = new ArrayList<>();
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject;
                for(int i=0; i<response.length(); i++){
                    try {

                        jsonObject = response.getJSONObject(i);
                        //list.add(new Appuntamento(jsonObject.getString("name"),jsonObject.getInt("points")));


                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
                adapter = new AppuntamentiAdapter(ranks,getContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }


        );
        request.add(soloRankRequest);
    }

            **/
}
