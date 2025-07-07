package com.mycompany.sisbibliografico.domain.entities;

public class InformeTecnico {
    private int idInforme;
    private String centroPublicacion;
    private String mesPublicacion;
    private int anioPublicacion;

    public int getIdInforme() { return idInforme; }
    public void setIdInforme(int idInforme) { this.idInforme = idInforme; }

    public String getCentroPublicacion() { return centroPublicacion; }
    public void setCentroPublicacion(String centroPublicacion) { this.centroPublicacion = centroPublicacion; }

    public String getMesPublicacion() { return mesPublicacion; }
    public void setMesPublicacion(String mesPublicacion) { this.mesPublicacion = mesPublicacion; }

    public int getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }
}

