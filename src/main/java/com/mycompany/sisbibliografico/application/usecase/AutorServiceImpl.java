package com.mycompany.sisbibliografico.application.usecase;

import com.mycompany.sisbibliografico.application.service.AutorService;
import com.mycompany.sisbibliografico.domain.entities.Autor;
import com.mycompany.sisbibliografico.domain.repository.AutorRepository;

import java.util.List;

public class AutorServiceImpl implements AutorService {

    private final AutorRepository autorRepository;

    public AutorServiceImpl(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Override
    public void guardarAutor(Autor autor) {
        autorRepository.guardar(autor);
    }

    @Override
    public Autor obtenerPorId(int id) {
        return autorRepository.buscarPorId(id);
    }

    @Override
    public List<Autor> listarAutores() {
        return autorRepository.listarTodos();
    }

    @Override
    public void actualizarAutor(Autor autor) {
        autorRepository.actualizar(autor);
    }

    @Override
    public void eliminarAutor(int id) {
        autorRepository.eliminar(id);
    }
} 

