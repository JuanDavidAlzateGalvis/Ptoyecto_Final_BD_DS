package com.mycompany.sisbibliografico.domain.entities;

public class Autor {
    private int idAutor;
    private String nombre;
    private String correo;
    private String centroTrabajo;
    private String paisOrigen;
    private String afiliacionUniversitaria;
    private String experienciaProfesional;
    private String gradoAcademico;
    private String colaboracionesPrevias;
    private String premiosAcademicos;
    private String asociacionesProfesionales;
    private String nivelColaboracionInternacional;

    public int getIdAutor() { return idAutor; }
    public void setIdAutor(int idAutor) { this.idAutor = idAutor; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getCentroTrabajo() { return centroTrabajo; }
    public void setCentroTrabajo(String centroTrabajo) { this.centroTrabajo = centroTrabajo; }

    public String getPaisOrigen() { return paisOrigen; }
    public void setPaisOrigen(String paisOrigen) { this.paisOrigen = paisOrigen; }

    public String getAfiliacionUniversitaria() { return afiliacionUniversitaria; }
    public void setAfiliacionUniversitaria(String afiliacionUniversitaria) { this.afiliacionUniversitaria = afiliacionUniversitaria; }

    public String getExperienciaProfesional() { return experienciaProfesional; }
    public void setExperienciaProfesional(String experienciaProfesional) { this.experienciaProfesional = experienciaProfesional; }

    public String getGradoAcademico() { return gradoAcademico; }
    public void setGradoAcademico(String gradoAcademico) { this.gradoAcademico = gradoAcademico; }

    public String getColaboracionesPrevias() { return colaboracionesPrevias; }
    public void setColaboracionesPrevias(String colaboracionesPrevias) { this.colaboracionesPrevias = colaboracionesPrevias; }

    public String getPremiosAcademicos() { return premiosAcademicos; }
    public void setPremiosAcademicos(String premiosAcademicos) { this.premiosAcademicos = premiosAcademicos; }

    public String getAsociacionesProfesionales() { return asociacionesProfesionales; }
    public void setAsociacionesProfesionales(String asociacionesProfesionales) { this.asociacionesProfesionales = asociacionesProfesionales; }

    public String getNivelColaboracionInternacional() { return nivelColaboracionInternacional; }
    public void setNivelColaboracionInternacional(String nivelColaboracionInternacional) { this.nivelColaboracionInternacional = nivelColaboracionInternacional; }
}

