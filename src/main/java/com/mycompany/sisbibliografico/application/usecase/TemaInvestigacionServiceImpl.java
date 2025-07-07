package com.mycompany.sisbibliografico.application.usecase;

import com.mycompany.sisbibliografico.application.service.TemaInvestigacionService;
import com.mycompany.sisbibliografico.domain.entities.TemaInvestigacion;
import com.mycompany.sisbibliografico.domain.repository.TemaInvestigacionRepository;

import java.util.List;

public class TemaInvestigacionServiceImpl implements TemaInvestigacionService {

    private final TemaInvestigacionRepository temaRepository;

    public TemaInvestigacionServiceImpl(TemaInvestigacionRepository temaRepository) {
        this.temaRepository = temaRepository;
    }

    @Override 
    public void guardarTema(TemaInvestigacion tema) {
        temaRepository.guardar(tema);
    }

    @Override
    public TemaInvestigacion obtenerPorId(int id) {
        return temaRepository.buscarPorId(id);
    }

    @Override
    public List<TemaInvestigacion> listarTemas() {
        return temaRepository.listarTodos();
    }

    @Override
    public void actualizarTema(TemaInvestigacion tema) {
        temaRepository.actualizar(tema);
    }

    @Override
    public void eliminarTema(int id) {
        temaRepository.eliminar(id);
    }
} 

