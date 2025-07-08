package com.mycompany.sisbibliografico.application.service;

import com.mycompany.sisbibliografico.domain.entities.Articulo;
import java.util.List;

public interface ArticuloService {
    void crearArticulo(Articulo articulo);
    Articulo obtenerArticuloPorId(int id);
    List<Articulo> obtenerTodosLosArticulos();
    void actualizarArticulo(Articulo articulo);
    void eliminarArticulo(int id);
}