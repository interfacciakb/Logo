package com.example.logo.Entities;

import com.example.logo.Adapters.Parent;

import java.util.List;

public class ProgrammazioneTerapia implements Parent<Svolgimento> {
    private String dataProgrammazione;
    private int progressPercentage;
    private List<Svolgimento> svolgimenti;

    public ProgrammazioneTerapia(String dataProgrammazione, int progressPercentage, List<Svolgimento> svolgimenti) {
        this.dataProgrammazione = dataProgrammazione;
        this.progressPercentage = progressPercentage;
        this.svolgimenti = svolgimenti;
    }

    public String getDataProgrammazione() {
        return dataProgrammazione;
    }

    public void setDataProgrammazione(String dataProgrammazione) {
        this.dataProgrammazione = dataProgrammazione;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public List<Svolgimento> getSvolgimenti() {
        return svolgimenti;
    }

    public void setSvolgimenti(List<Svolgimento> svolgimenti) {
        this.svolgimenti = svolgimenti;
    }

    @Override
    public List<Svolgimento> getChildList() {
        return svolgimenti;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }


    public Svolgimento getSvolgimento(int position) {
        return svolgimenti.get(position);
    }

}


