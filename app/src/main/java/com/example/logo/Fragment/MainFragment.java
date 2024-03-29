package com.example.logo.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.logo.Adapters.ExpandableRecyclerAdapter;
import com.example.logo.Adapters.ProgrammazioneTerapiaAdapter;
import com.example.logo.Entities.ProgrammazioneTerapia;
import com.example.logo.Entities.Svolgimento;
import com.example.logo.R;
import com.example.logo.util.InterationWithMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class MainFragment extends AbFrg implements SwipeRefreshLayout.OnRefreshListener{

    //SwipeRefreshLayout swipeLayout;
    RequestQueue request;
    private String url;
    InterationWithMain createNewRequest;
    public static String TAG="MAINFRG";
    ProgressDialog progress;
    int eserciziTot = 0;
    HashMap<String, Integer> hashMap = new HashMap<>();

    private ProgrammazioneTerapiaAdapter mAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        createNewRequest = (InterationWithMain) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        request =  Volley.newRequestQueue(getActivity().getApplicationContext());
    }


    @Override
    protected View viewOfFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getTerapie("1234");


        View v = inflater.inflate(R.layout.fragment_main, container, false);

        LineChartView lineChartView = v.findViewById(R.id.chart);

        String[] axisData = {"01/06", "08/06", "15/06", "22/06", "29/06"};
        int[] yAxisData = {50, 20, 25, 100, 20};

        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();
        Line line = new Line(yAxisValues);

        for(int i = 0; i < axisData.length; i++){
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++){
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        List lines = new ArrayList();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        lineChartView.setLineChartData(data);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);

        line.setColor(Color.parseColor("#0CBEBE"));

        axis.setTextSize(14);
        axis.setHasSeparationLine(true);
        axis.setTextColor(Color.parseColor("#217070"));
        yAxis.setTextColor(Color.parseColor("#217070"));
        yAxis.setTextSize(14);
        yAxis.setHasSeparationLine(true);
        yAxis.setName("Percentuale di successo");
        axis.setName("Data inizio terapia");
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());

        viewport.top =100;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
        lineChartView.animate();

        Svolgimento svolgimento11 = new Svolgimento("123456","12","si",3, "Riconoscimento parola: balena");
        Svolgimento svolgimento12 = new Svolgimento("1","12","si",3, "Riconoscimento parola: balena");
        Svolgimento svolgimento13 = new Svolgimento("1","12","si",3, "Riconoscimento parola: balena");
        Svolgimento svolgimento14 = new Svolgimento("1","12","si",3, "Riconoscimento parola: balena");

        Svolgimento svolgimento21 = new Svolgimento("1","12","si",3, "Riconoscimento parola: balena");
        Svolgimento svolgimento22 = new Svolgimento("1","12","si",3, "Riconoscimento parola: balena");
        Svolgimento svolgimento23 = new Svolgimento("1","12","si",3, "Riconoscimento parola: balena");
        Svolgimento svolgimento24 = new Svolgimento("1","12","si",3, "Riconoscimento parola: balena");

        //Recipe taco = new Recipe("taco", Arrays.asList(beef, cheese, salsa, tortilla));

        ProgrammazioneTerapia prog1 = new ProgrammazioneTerapia("15:08", 12, Arrays.asList(svolgimento11,svolgimento12,svolgimento13,svolgimento14));
        ProgrammazioneTerapia prog2 = new ProgrammazioneTerapia("15:09", 25, Arrays.asList(svolgimento21,svolgimento22,svolgimento23,svolgimento24));

        final List<ProgrammazioneTerapia> programmazioneTerapias = Arrays.asList(prog1, prog2);

        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        mAdapter = new ProgrammazioneTerapiaAdapter(getActivity(), programmazioneTerapias);
        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @UiThread
            @Override
            public void onParentExpanded(int parentPosition) {
                ProgrammazioneTerapia expandedProgrammazioneTerapias = programmazioneTerapias.get(parentPosition);

            }

            @UiThread
            @Override
            public void onParentCollapsed(int parentPosition) {
                ProgrammazioneTerapia collapsedProgrammazioneTerapia = programmazioneTerapias.get(parentPosition);

            }
        });

        recyclerView.setAdapter(mAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener(){

        });
        return v;
    }

    @Override
    protected void onResumeReload(View v) {

    }


    @Override
    public void onRefresh() {


        progress.show();
        //swipeLayout.setRefreshing(false);
    }


    private void getTerapie(String codicePaziente){
        String URL = "http://www.logopediapp.altervista.org/database/crud_terapia/read_condition.php?condition=paziente_codice='" +codicePaziente+"'+AND+month(data_inizio)+=+MONTH(current_date)+ORDER+BY+data_inizio";

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

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
                                String data_inizio = object.getString("data_inizio");
                                String id_terapia = object.getString("id");

                                System.out.println(data_inizio);
                                System.out.println(id_terapia);
                                getProgTerapie(id_terapia, data_inizio);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred

                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

    private void getProgTerapie(final String id_terapia, final String data_inizio){
        String URL = "http://www.logopediapp.altervista.org/database/crud_programmazione_terapia/read_condition.php?condition=terapia_id='" +id_terapia+"'+ORDER+BY+id";

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

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
                                String id_progr_terapia = object.getString("id");
                                String data_inizio_terapia = data_inizio;

                                System.out.println(id_progr_terapia);
                                System.out.println(data_inizio_terapia);


                                getSvolgimento(id_terapia, id_progr_terapia, data_inizio_terapia);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred

                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

    private void getSvolgimento(final String id_terapia, final String id_progr_terapia, String data_inizio_terapia){
        String URL = "http://www.logopediapp.altervista.org/database/crud_svolgimento/read_condition.php?condition=programmazione_terapia_id='" +id_progr_terapia+"'+ORDER+BY+id";

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

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
                            int contaEsercizi = jsonArray.length();
                            int contaEsCorretti = 0;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String corretto = object.getString("corretto");
                                if(corretto.equalsIgnoreCase("si")){
                                    contaEsCorretti++;
                                }
                            }
                            System.out.println(id_terapia + " - Prog " + id_progr_terapia + " - TOT " + contaEsercizi + " - corretti " + contaEsCorretti);

                            if(hashMap.isEmpty()){
                                hashMap.put(id_terapia,contaEsercizi);
                            }else{
                                if(hashMap.containsKey(id_terapia)){
                                    int numEserciziPrec = hashMap.get(id_terapia);
                                    int tot = numEserciziPrec + contaEsercizi;
                                    hashMap.remove(id_terapia);
                                    hashMap.put(id_terapia,tot);
                                }else{
                                    hashMap.put(id_terapia,contaEsercizi);
                                }
                            }

                            System.out.println(id_terapia+" ---- "+hashMap.get(id_terapia));

                            //System.out.println("SINGOLO - " + eserciziTot);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred

                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }
}
