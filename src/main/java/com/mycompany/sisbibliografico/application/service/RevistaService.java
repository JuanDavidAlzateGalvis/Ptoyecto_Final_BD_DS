package com.mycompany.sisbibliografico.application.service;

import com.mycompany.sisbibliografico.domain.entities.Revista;
import java.util.List;

public interface RevistaService {
    void guardarRevista(Revista revista);
    Revista obtenerPorId(int id);
    List<Revista> listarRevistas();
    void actualizarRevista(Revista revista);
    void eliminarRevista(int id);
}

