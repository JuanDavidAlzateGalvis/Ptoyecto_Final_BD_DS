package com.mycompany.sisbibliografico.application.service;

import com.mycompany.sisbibliografico.domain.entities.InformeTecnico;
import java.util.List;

public interface InformeTecnicoService {
    void crearInformeTecnico(InformeTecnico informe);
    InformeTecnico obtenerInformeTecnicoPorId(int id);
    List<InformeTecnico> obtenerTodosLosInformesTecnicos();
    void actualizarInformeTecnico(InformeTecnico informe);
    void eliminarInformeTecnico(int id);
    List<InformeTecnico> buscarInformesPorCentro(String centro);
    List<InformeTecnico> buscarInformesPorMes(String mes);
    List<InformeTecnico> buscarInformesPorAnio(int anio);
}