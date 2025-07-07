package com.mycompany.sisbibliografico.application.service;

import com.mycompany.sisbibliografico.domain.entities.TemaInvestigacion;
import java.util.List;

public interface TemaInvestigacionService {
    void guardarTema(TemaInvestigacion tema);
    TemaInvestigacion obtenerPorId(int id);
    List<TemaInvestigacion> listarTemas();
    void actualizarTema(TemaInvestigacion tema);
    void eliminarTema(int id);
}

