package com.mycompany.sisbibliografico.application.service;

import com.mycompany.sisbibliografico.domain.entities.Autor;
import java.util.List;

public interface AutorService {
    void guardarAutor(Autor autor);
    Autor obtenerPorId(int id);
    List<Autor> listarAutores();
    void actualizarAutor(Autor autor);
    void eliminarAutor(int id);
}

