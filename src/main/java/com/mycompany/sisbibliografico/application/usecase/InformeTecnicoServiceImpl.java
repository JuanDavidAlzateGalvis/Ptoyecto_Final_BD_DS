package com.mycompany.sisbibliografico.application.usecase;

import com.mycompany.sisbibliografico.application.service.InformeTecnicoService;
import com.mycompany.sisbibliografico.domain.entities.InformeTecnico;
import com.mycompany.sisbibliografico.domain.repository.InformeTecnicoRepository;

import java.util.List;

public class InformeTecnicoServiceImpl implements InformeTecnicoService {

    private final InformeTecnicoRepository informeRepository;

    public InformeTecnicoServiceImpl(InformeTecnicoRepository informeRepository) {
        this.informeRepository = informeRepository;
    }

    @Override
    public void guardarInforme(InformeTecnico informe) {
        informeRepository.guardar(informe);
    }

    @Override
    public InformeTecnico obtenerPorId(int id) {
        return informeRepository.buscarPorId(id);
    }

    @Override
    public List<InformeTecnico> listarInformes() {
        return informeRepository.listarTodos();
    }

    @Override
    public void actualizarInforme(InformeTecnico informe) {
        informeRepository.actualizar(informe);
    }

    @Override
    public void eliminarInforme(int id) {
        informeRepository.eliminar(id);
    }
} 
 
