package com.example.logo.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.logo.R;
import com.example.logo.util.InterationWithMain;

public class AppuntamentiFragment extends AbFrg implements SwipeRefreshLayout.OnRefreshListener{

    //SwipeRefreshLayout swipeLayout;
    RequestQueue request;
    private String url;
    InterationWithMain createNewRequest;
    public static String TAG="APPUNTFRG";
    ProgressDialog progress;


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
        View v = inflater.inflate(R.layout.fragment_appuntamenti, container, false);
        //txt_name = (TextView) v.findViewById(R.id.frg_fav_name);
        //list_my_requests = (ListView) v.findViewById(R.id.frg_my_requests);
        //swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);
        //swipeLayout.setOnRefreshListener(this);
        progress = new ProgressDialog(getActivity());
        progress.setMessage(getString(R.string.loading));

        /**FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.addProposalButtonMain);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CreaRichiestaActivity.class);
                startActivity(intent);
            }
        });
        */
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
