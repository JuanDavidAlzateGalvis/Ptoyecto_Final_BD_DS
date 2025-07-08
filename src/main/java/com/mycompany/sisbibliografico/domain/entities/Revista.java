package com.mycompany.sisbibliografico.domain.entities;

public class Revista {
    private int idRevista;
    private String nombre;
    private String editor;
    private int anioFundacion;
    private String frecuencia;
    private String temasAbordados;

    public int getIdRevista() { return idRevista; }
    public void setIdRevista(int idRevista) { this.idRevista = idRevista; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEditor() { return editor; }
    public void setEditor(String editor) { this.editor = editor; }

    public int getAnioFundacion() { return anioFundacion; }
    public void setAnioFundacion(int anioFundacion) { this.anioFundacion = anioFundacion; }

    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }

    public String getTemasAbordados() { return temasAbordados; }
    public void setTemasAbordados(String temasAbordados) { this.temasAbordados = temasAbordados; }
}