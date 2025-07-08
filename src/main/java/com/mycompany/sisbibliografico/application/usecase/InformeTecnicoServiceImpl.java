package com.mycompany.sisbibliografico.application.usecase;

import com.mycompany.sisbibliografico.application.service.InformeTecnicoService;
import com.mycompany.sisbibliografico.domain.entities.InformeTecnico;
import com.mycompany.sisbibliografico.domain.repository.InformeTecnicoRepository;
import java.util.List;

public class InformeTecnicoServiceImpl implements InformeTecnicoService {

    private final InformeTecnicoRepository informeTecnicoRepository;

    public InformeTecnicoServiceImpl(InformeTecnicoRepository informeTecnicoRepository) {
        this.informeTecnicoRepository = informeTecnicoRepository;
    }

    @Override
    public void crearInformeTecnico(InformeTecnico informe) {
        informeTecnicoRepository.guardar(informe);
    }

    @Override
    public InformeTecnico obtenerInformeTecnicoPorId(int id) {
        return informeTecnicoRepository.buscarPorId(id);
    }

    @Override
    public List<InformeTecnico> obtenerTodosLosInformesTecnicos() {
        return informeTecnicoRepository.listarTodos();
    }

    @Override
    public void actualizarInformeTecnico(InformeTecnico informe) {
        informeTecnicoRepository.actualizar(informe);
    }

    @Override
    public void eliminarInformeTecnico(int id) {
        informeTecnicoRepository.eliminar(id);
    }
}