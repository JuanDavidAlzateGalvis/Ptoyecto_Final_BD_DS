package com.mycompany.sisbibliografico.application.service;

import com.mycompany.sisbibliografico.domain.entities.Congreso;
import java.util.List;

public interface CongresoService {
    void guardarCongreso(Congreso congreso);
    Congreso obtenerPorId(int id);
    List<Congreso> listarCongresos();
    void actualizarCongreso(Congreso congreso);
    void eliminarCongreso(int id);
}

