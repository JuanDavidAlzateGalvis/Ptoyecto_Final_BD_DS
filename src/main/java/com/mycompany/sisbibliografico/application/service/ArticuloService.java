package com.mycompany.sisbibliografico.application.service;

import com.mycompany.sisbibliografico.domain.entities.Articulo;
import java.util.List;

public interface ArticuloService {
    void guardarArticulo(Articulo articulo);
    Articulo obtenerPorId(int id);
    List<Articulo> listarArticulos();
    void actualizarArticulo(Articulo articulo);
    void eliminarArticulo(int id);
}

