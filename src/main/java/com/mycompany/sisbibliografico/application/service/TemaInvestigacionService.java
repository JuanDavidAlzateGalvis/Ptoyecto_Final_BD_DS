package com.mycompany.sisbibliografico.application.service;

import com.mycompany.sisbibliografico.domain.entities.TemaInvestigacion;
import java.util.List;

public interface TemaInvestigacionService {
    void crearTema(TemaInvestigacion tema);
    TemaInvestigacion obtenerTemaPorId(int id);
    List<TemaInvestigacion> obtenerTodosLosTemas();
    void actualizarTema(TemaInvestigacion tema);
    void eliminarTema(int id);
    List<TemaInvestigacion> buscarPorNombre(String nombre);
}