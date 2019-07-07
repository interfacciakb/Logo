package com.example.logo.Entities;

public class Svolgimento {


    private String id;
    private String esercizioCodice;
    private String corretto;
    private int numeroStelle;
    private String nomeEsercizio;

    public Svolgimento(String id, String esercizioCodice, String corretto, int numeroStelle, String nomeEsercizio) {
        this.id = id;
        this.esercizioCodice = esercizioCodice;
        this.corretto = corretto;
        this.numeroStelle = numeroStelle;
        this.nomeEsercizio = nomeEsercizio;
    }

    public Svolgimento(String id, String esercizioCodice, String corretto, int numeroStelle) {
        this.id = id;
        this.esercizioCodice = esercizioCodice;
        this.corretto = corretto;
        this.numeroStelle = numeroStelle;
    }

    public String getNomeEsercizio() {
        return nomeEsercizio;
    }

    public void setNomeEsercizio(String nomeEsercizio) {
        this.nomeEsercizio = nomeEsercizio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEsercizioCodice() {
        return esercizioCodice;
    }

    public void setEsercizioCodice(String esercizioCodice) {
        this.esercizioCodice = esercizioCodice;
    }

    public String getCorretto() {
        return corretto;
    }

    public void setCorretto(String corretto) {
        this.corretto = corretto;
    }

    public int getNumeroStelle() {
        return numeroStelle;
    }

    public void setNumeroStelle(int numeroStelle) {
        this.numeroStelle = numeroStelle;
    }
}
