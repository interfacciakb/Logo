package com.example.logo.Fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logo.R;


public class AppuntamentiFragment extends AbFrg {

    private FragmentTabHost tabHost;

    @Override
    protected View viewOfFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_appuntamenti,container,false);

        tabHost = (FragmentTabHost) root.findViewById(android.R.id.tabhost);
        tabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec("fragmentPassati").setIndicator("Passati"),
                AppuntamentiPassatiFrg.class, null);

        tabHost.addTab(tabHost.newTabSpec("fragmentFuturi").setIndicator("Futuri"),
                AppuntamentiFuturiFrg.class, null);




        return root;
    }

    @Override
    protected void onResumeReload(View v) {

    }
}
