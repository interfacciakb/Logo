package com.example.logo.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.logo.R;


public abstract class AbFrg extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!isInternetAvailable()){
            Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            return null;
        }else{
            return viewOfFragment(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        View v = getView();
        if(v != null){
            onResumeReload(v);
        }
    }

    protected abstract View viewOfFragment(LayoutInflater inflater, ViewGroup container,
                                           Bundle savedInstanceState);

    protected abstract void onResumeReload(View v);

    protected boolean isInternetAvailable() {
        NetworkInfo info = ((ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}