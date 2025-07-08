package com.mycompany.sisbibliografico.domain.repository;

import com.mycompany.sisbibliografico.domain.entities.InformeTecnico;
import java.util.List;

public interface InformeTecnicoRepository {
    void guardar(InformeTecnico informe);
    InformeTecnico buscarPorId(int id);
    List<InformeTecnico> listarTodos();
    void actualizar(InformeTecnico informe);
    void eliminar(int id);
}