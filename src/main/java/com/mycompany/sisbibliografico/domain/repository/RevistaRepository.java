package com.mycompany.sisbibliografico.domain.repository;

import com.mycompany.sisbibliografico.domain.entities.Revista;
import java.util.List;

public interface RevistaRepository {
    void guardar(Revista revista);
    Revista buscarPorId(int id);
    List<Revista> listarTodas();
    void actualizar(Revista revista);
    void eliminar(int id);
    List<Revista> buscarPorNombre(String nombre);
    List<Revista> buscarPorEditor(String editor);
    List<Revista> buscarPorFrecuencia(String frecuencia);
    List<Revista> buscarPorAnioFundacion(int anio);
}