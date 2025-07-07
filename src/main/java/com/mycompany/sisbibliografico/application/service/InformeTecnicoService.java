package com.mycompany.sisbibliografico.application.service;

import com.mycompany.sisbibliografico.domain.entities.InformeTecnico;
import java.util.List;

public interface InformeTecnicoService {
    void guardarInforme(InformeTecnico informe);
    InformeTecnico obtenerPorId(int id);
    List<InformeTecnico> listarInformes();
    void actualizarInforme(InformeTecnico informe);
    void eliminarInforme(int id);
}

