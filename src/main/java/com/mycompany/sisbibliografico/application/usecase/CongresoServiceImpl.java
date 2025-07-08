package com.mycompany.sisbibliografico.application.usecase;

import com.mycompany.sisbibliografico.application.service.CongresoService;
import com.mycompany.sisbibliografico.domain.entities.Congreso;
import com.mycompany.sisbibliografico.domain.repository.CongresoRepository;
import java.util.List;

public class CongresoServiceImpl implements CongresoService {

    private final CongresoRepository congresoRepository;

    public CongresoServiceImpl(CongresoRepository congresoRepository) {
        this.congresoRepository = congresoRepository;
    }

    @Override
    public void crearCongreso(Congreso congreso) {
        congresoRepository.guardar(congreso);
    }

    @Override
    public Congreso obtenerCongresoPorId(int id) {
        return congresoRepository.buscarPorId(id);
    }

    @Override
    public List<Congreso> obtenerTodosLosCongresos() {
        return congresoRepository.listarTodos();
    }

    @Override
    public void actualizarCongreso(Congreso congreso) {
        congresoRepository.actualizar(congreso);
    }

    @Override
    public void eliminarCongreso(int id) {
        congresoRepository.eliminar(id);
    }
}