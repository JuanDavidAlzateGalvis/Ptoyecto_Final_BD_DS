package com.mycompany.sisbibliografico.application.usecase;

import com.mycompany.sisbibliografico.application.service.RevistaService;
import com.mycompany.sisbibliografico.domain.entities.Revista;
import com.mycompany.sisbibliografico.domain.repository.RevistaRepository;
import java.util.List;

public class RevistaServiceImpl implements RevistaService {

    private final RevistaRepository revistaRepository;

    public RevistaServiceImpl(RevistaRepository revistaRepository) {
        this.revistaRepository = revistaRepository;
    }

    @Override
    public void crearRevista(Revista revista) {
        revistaRepository.guardar(revista);
    }

    @Override
    public Revista obtenerRevistaPorId(int id) {
        return revistaRepository.buscarPorId(id);
    }

    @Override
    public List<Revista> obtenerTodasLasRevistas() {
        return revistaRepository.listarTodas();
    }

    @Override
    public void actualizarRevista(Revista revista) {
        revistaRepository.actualizar(revista);
    }

    @Override
    public void eliminarRevista(int id) {
        revistaRepository.eliminar(id);
    }
}