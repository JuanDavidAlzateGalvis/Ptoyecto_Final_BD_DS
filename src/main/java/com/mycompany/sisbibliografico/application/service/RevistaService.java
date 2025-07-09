package com.mycompany.sisbibliografico.application.service;

import com.mycompany.sisbibliografico.domain.entities.Revista;
import java.util.List;

public interface RevistaService {
    void crearRevista(Revista revista);
    Revista obtenerRevistaPorId(int id);
    List<Revista> obtenerTodasLasRevistas();
    void actualizarRevista(Revista revista);
    void eliminarRevista(int id);
    List<Revista> buscarRevistasPorNombre(String nombre);
    List<Revista> buscarRevistasPorEditor(String editor);
    List<Revista> buscarRevistasPorFrecuencia(String frecuencia);
    List<Revista> buscarRevistasPorAnioFundacion(int anio);
}