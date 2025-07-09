package com.mycompany.sisbibliografico.domain.repository;

import com.mycompany.sisbibliografico.domain.entities.Articulo;
import java.time.LocalDate;
import java.util.List;

public interface ArticuloRepository {
    // Métodos CRUD
    void guardar(Articulo articulo);
    Articulo buscarPorId(int id);
    List<Articulo> listarTodos();
    void actualizar(Articulo articulo);
    void eliminar(int id);

    // Métodos para Consultas
    List<Articulo> buscarPorTitulo(String titulo);
    List<Articulo> buscarPorAnioPublicacion(int anio);
    List<Articulo> buscarPorPalabrasClave(String palabrasClave);
    List<Articulo> buscarPorResumen(String termino);
    List<Articulo> buscarPorMetodologia(String metodologia);
    List<Articulo> buscarPorRangoPaginas(String paginas);
    List<Articulo> buscarPorNombreRevista(String nombreRevista);
    List<Articulo> buscarPorEditorRevista(String editor);
    List<Articulo> buscarPorAreaTematicaRevista(String areaTematica);
    List<Articulo> buscarPorPaisCongreso(String pais);
    List<Articulo> buscarPorCiudadCongreso(String ciudad);
    List<Articulo> buscarPorTipoCongreso(String tipo);
    List<Articulo> buscarPorGrupoInvestigacion(String nombreGrupo);
    List<Articulo> buscarPorProyectoInvestigacion(String nombreProyecto);
     List<Articulo> buscarPorTemaInvestigacion(String tema);
    List<Articulo> buscarPorEdicionCongreso(String edicion);
    List<Articulo> buscarPorFechaPublicacion(LocalDate fecha);
    List<Articulo> buscarPorFrecuenciaRevista(String frecuencia);
    List<Articulo> buscarPorIdRevista(int idRevista);
}