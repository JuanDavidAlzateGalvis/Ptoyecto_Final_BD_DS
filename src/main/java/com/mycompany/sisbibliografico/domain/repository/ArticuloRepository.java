package com.mycompany.sisbibliografico.domain.repository;

import com.mycompany.sisbibliografico.domain.entities.Articulo;
import java.util.List;

public interface ArticuloRepository {
    void guardar(Articulo articulo);
    Articulo buscarPorId(int id);
    List<Articulo> listarTodos();
    void actualizar(Articulo articulo);
    void eliminar(int id);
}

