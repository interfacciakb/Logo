package com.example.logo.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.logo.Adapters.ExpandableRecyclerAdapter;
import com.example.logo.Adapters.ProgrammazioneTerapiaAdapter;
import com.example.logo.Entities.ProgrammazioneTerapia;
import com.example.logo.Entities.Svolgimento;
import com.example.logo.R;
import com.example.logo.util.InterationWithMain;

import java.util.Arrays;
import java.util.List;

public class MainFragment extends AbFrg implements SwipeRefreshLayout.OnRefreshListener{

    //SwipeRefreshLayout swipeLayout;
    RequestQueue request;
    private String url;
    InterationWithMain createNewRequest;
    public static String TAG="MAINFRG";
    ProgressDialog progress;

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
        View v = inflater.inflate(R.layout.fragment_main, container, false);
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

}
