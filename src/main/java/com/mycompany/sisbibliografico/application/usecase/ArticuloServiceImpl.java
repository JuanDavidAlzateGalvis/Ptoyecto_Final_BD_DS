package com.mycompany.sisbibliografico.application.usecase;

import com.mycompany.sisbibliografico.application.service.ArticuloService;
import com.mycompany.sisbibliografico.domain.entities.Articulo;
import com.mycompany.sisbibliografico.domain.repository.ArticuloRepository;
import java.time.LocalDate;
import java.util.List;

public class ArticuloServiceImpl implements ArticuloService {

    private final ArticuloRepository articuloRepository;

    public ArticuloServiceImpl(ArticuloRepository articuloRepository) {
        this.articuloRepository = articuloRepository;
    }

    @Override
    public void crearArticulo(Articulo articulo) {
        articuloRepository.guardar(articulo);
    }

    @Override
    public Articulo obtenerArticuloPorId(int id) {
        return articuloRepository.buscarPorId(id);
    }

    @Override
    public List<Articulo> obtenerTodosLosArticulos() {
        return articuloRepository.listarTodos();
    }

    @Override
    public void actualizarArticulo(Articulo articulo) {
        articuloRepository.actualizar(articulo);
    }

    @Override
    public void eliminarArticulo(int id) {
        articuloRepository.eliminar(id);
    }

    @Override
    public List<Articulo> buscarArticulosPorTitulo(String titulo) {
        return articuloRepository.buscarPorTitulo(titulo);
    }

    @Override
    public List<Articulo> buscarArticulosPorAnio(int anio) {
        return articuloRepository.buscarPorAnioPublicacion(anio);
    }

    @Override
    public List<Articulo> buscarArticulosPorPalabrasClave(String p) {
        return articuloRepository.buscarPorPalabrasClave(p);
    }

    @Override
    public List<Articulo> buscarArticulosPorResumen(String t) {
        return articuloRepository.buscarPorResumen(t);
    }

    @Override
    public List<Articulo> buscarArticulosPorMetodologia(String m) {
        return articuloRepository.buscarPorMetodologia(m);
    }

    @Override
    public List<Articulo> buscarArticulosPorRangoPaginas(String p) {
        return articuloRepository.buscarPorRangoPaginas(p);
    }

    @Override
    public List<Articulo> buscarArticulosPorNombreRevista(String n) {
        return articuloRepository.buscarPorNombreRevista(n);
    }

    @Override
    public List<Articulo> buscarArticulosPorEditorRevista(String e) {
        return articuloRepository.buscarPorEditorRevista(e);
    }

    @Override
    public List<Articulo> buscarArticulosPorAreaTematica(String a) {
        return articuloRepository.buscarPorAreaTematicaRevista(a);
    }

    @Override
    public List<Articulo> buscarArticulosPorPaisCongreso(String p) {
        return articuloRepository.buscarPorPaisCongreso(p);
    }

    @Override
    public List<Articulo> buscarArticulosPorCiudadCongreso(String c) {
        return articuloRepository.buscarPorCiudadCongreso(c);
    }

    @Override
    public List<Articulo> buscarArticulosPorTipoCongreso(String t) {
        return articuloRepository.buscarPorTipoCongreso(t);
    }

    @Override
    public List<Articulo> buscarArticulosPorGrupo(String n) {
        return articuloRepository.buscarPorGrupoInvestigacion(n);
    }

    @Override
    public List<Articulo> buscarArticulosPorProyecto(String n) {
        return articuloRepository.buscarPorProyectoInvestigacion(n);
    }

    @Override
    public List<Articulo> buscarArticulosPorTema(String tema) {
        return articuloRepository.buscarPorTemaInvestigacion(tema);
    }

    @Override
    public List<Articulo> buscarArticulosPorEdicionCongreso(String edicion) {
        return articuloRepository.buscarPorEdicionCongreso(edicion);
    }

    @Override
    public List<Articulo> buscarArticulosPorFecha(LocalDate fecha) {
        return articuloRepository.buscarPorFechaPublicacion(fecha);
    }

    @Override
    public List<Articulo> buscarArticulosPorFrecuenciaRevista(String f) {
        return articuloRepository.buscarPorFrecuenciaRevista(f);
    }

    @Override
    public List<Articulo> buscarArticulosPorIdRevista(int id) {
        return articuloRepository.buscarPorIdRevista(id);
    }

}
