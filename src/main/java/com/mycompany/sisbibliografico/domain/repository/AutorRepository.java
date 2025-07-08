package com.mycompany.sisbibliografico.domain.repository;

import com.mycompany.sisbibliografico.domain.entities.Autor;
import java.util.List;

public interface AutorRepository {
    void guardar(Autor autor);
    Autor buscarPorId(int id);
    List<Autor> listarTodos();
    void actualizar(Autor autor);
    void eliminar(int id);
}