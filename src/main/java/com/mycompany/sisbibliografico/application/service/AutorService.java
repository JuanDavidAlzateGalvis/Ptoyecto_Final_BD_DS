package com.mycompany.sisbibliografico.application.service;

import com.mycompany.sisbibliografico.domain.entities.Autor;
import java.util.List;

public interface AutorService {
    void crearAutor(Autor autor);
    Autor obtenerAutorPorId(int id);
    List<Autor> obtenerTodosLosAutores();
    void actualizarAutor(Autor autor);
    void eliminarAutor(int id);
    List<Autor> buscarAutores(String nombre, String pais, String afiliacion, String grado); 
}