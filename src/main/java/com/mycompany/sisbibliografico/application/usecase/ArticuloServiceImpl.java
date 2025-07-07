package com.mycompany.sisbibliografico.application.usecase;

import com.mycompany.sisbibliografico.application.service.ArticuloService;
import com.mycompany.sisbibliografico.domain.entities.Articulo;
import com.mycompany.sisbibliografico.domain.repository.ArticuloRepository;

import java.util.List; 

public class ArticuloServiceImpl implements ArticuloService {

    private final ArticuloRepository articuloRepository;

    public ArticuloServiceImpl(ArticuloRepository articuloRepository) {
        this.articuloRepository = articuloRepository;
    }

    @Override
    public void guardarArticulo(Articulo articulo) {
        articuloRepository.guardar(articulo);
    }

    @Override
    public Articulo obtenerPorId(int id) {
        return articuloRepository.buscarPorId(id);
    }

    @Override
    public List<Articulo> listarArticulos() {
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
}

