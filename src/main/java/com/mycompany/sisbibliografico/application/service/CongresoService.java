package com.mycompany.sisbibliografico.application.service;

import com.mycompany.sisbibliografico.domain.entities.Congreso;
import java.util.List;

public interface CongresoService {
    void crearCongreso(Congreso congreso);
    Congreso obtenerCongresoPorId(int id);
    List<Congreso> obtenerTodosLosCongresos();
    void actualizarCongreso(Congreso congreso);
    void eliminarCongreso(int id);
}