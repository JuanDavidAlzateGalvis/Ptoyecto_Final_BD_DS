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
    public void crearTema(TemaInvestigacion tema) {
        temaRepository.guardar(tema);
    }

    @Override
    public TemaInvestigacion obtenerTemaPorId(int id) {
        return temaRepository.buscarPorId(id);
    }

    @Override
    public List<TemaInvestigacion> obtenerTodosLosTemas() {
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
    
    @Override
    public List<TemaInvestigacion> buscarPorNombre(String nombre) {
        return temaRepository.buscarPorNombre(nombre);
    }
}