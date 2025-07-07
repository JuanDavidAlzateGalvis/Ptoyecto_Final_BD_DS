package com.mycompany.sisbibliografico.domain.entities;

import java.time.LocalDate;

public class Congreso {
    private int idCongreso;
    private String nombre;
    private int edicion;
    private String ciudad;
    private String pais;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String tipo;
    private String frecuencia;
    private int anioPrimeraEdicion;

    public int getIdCongreso() { return idCongreso; }
    public void setIdCongreso(int idCongreso) { this.idCongreso = idCongreso; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdicion() { return edicion; }
    public void setEdicion(int edicion) { this.edicion = edicion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }

    public int getAnioPrimeraEdicion() { return anioPrimeraEdicion; }
    public void setAnioPrimeraEdicion(int anioPrimeraEdicion) { this.anioPrimeraEdicion = anioPrimeraEdicion; }
}

