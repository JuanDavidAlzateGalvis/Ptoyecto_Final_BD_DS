package com.mycompany.sisbibliografico.domain.repository;

import com.mycompany.sisbibliografico.domain.entities.TemaInvestigacion;
import java.util.List;

public interface TemaInvestigacionRepository {
    void guardar(TemaInvestigacion tema);
    TemaInvestigacion buscarPorId(int id);
    List<TemaInvestigacion> listarTodos();
    void actualizar(TemaInvestigacion tema);
    void eliminar(int id);
    List<TemaInvestigacion> buscarPorNombre(String nombre);
}