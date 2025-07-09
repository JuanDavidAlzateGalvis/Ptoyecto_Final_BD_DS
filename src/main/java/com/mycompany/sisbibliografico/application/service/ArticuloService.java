package com.mycompany.sisbibliografico.application.service;

import com.mycompany.sisbibliografico.domain.entities.Articulo;
import java.time.LocalDate;
import java.util.List;

public interface ArticuloService {
    void crearArticulo(Articulo articulo);
    Articulo obtenerArticuloPorId(int id);
    List<Articulo> obtenerTodosLosArticulos();
    void actualizarArticulo(Articulo articulo);
    void eliminarArticulo(int id);

    List<Articulo> buscarArticulosPorTitulo(String titulo);
    List<Articulo> buscarArticulosPorAnio(int anio);
    List<Articulo> buscarArticulosPorPalabrasClave(String palabrasClave);
    List<Articulo> buscarArticulosPorResumen(String termino);
    List<Articulo> buscarArticulosPorMetodologia(String metodologia);
    List<Articulo> buscarArticulosPorRangoPaginas(String paginas);
    List<Articulo> buscarArticulosPorNombreRevista(String nombreRevista);
    List<Articulo> buscarArticulosPorEditorRevista(String editor);
    List<Articulo> buscarArticulosPorAreaTematica(String areaTematica);
    List<Articulo> buscarArticulosPorPaisCongreso(String pais);
    List<Articulo> buscarArticulosPorCiudadCongreso(String ciudad);
    List<Articulo> buscarArticulosPorTipoCongreso(String tipo);
    List<Articulo> buscarArticulosPorGrupo(String nombreGrupo);
    List<Articulo> buscarArticulosPorProyecto(String nombreProyecto);
     List<Articulo> buscarArticulosPorTema(String tema);
    List<Articulo> buscarArticulosPorEdicionCongreso(String edicion);
    List<Articulo> buscarArticulosPorFecha(LocalDate fecha);
    List<Articulo> buscarArticulosPorFrecuenciaRevista(String frecuencia);
    List<Articulo> buscarArticulosPorIdRevista(int idRevista);
}