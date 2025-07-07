package com.mycompany.sisbibliografico.domain.repository;

import com.mycompany.sisbibliografico.domain.entities.Congreso;
import java.util.List;

public interface CongresoRepository {
    void guardar(Congreso congreso);
    Congreso buscarPorId(int id);
    List<Congreso> listarTodos();
    void actualizar(Congreso congreso);
    void eliminar(int id);
}

