package com.example.logo.Entities;

public class Appuntamento {

    private String data;
    private String luogo;
    private String medico;
    private String orario;


    public Appuntamento(String data, String luogo, String medico, String orario) {
        this.data = data;
        this.luogo = luogo;
        this.medico = medico;
        this.orario = orario;
    }

    public String getData() {
        return data;
    }

    public String getLuogo() {
        return luogo;
    }

    public String getMedico() {
        return medico;
    }

    public String getOrario() {
        return orario;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public void setOrario(String orario) {
        this.orario = orario;
    }
}
