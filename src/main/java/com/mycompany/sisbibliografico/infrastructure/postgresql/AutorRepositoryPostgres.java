package com.mycompany.sisbibliografico.infrastructure.postgresql;

import com.mycompany.sisbibliografico.domain.entities.Autor;
import com.mycompany.sisbibliografico.domain.repository.AutorRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorRepositoryPostgres implements AutorRepository {

    private final Connection conn = ConexionBD.getConnection();

    @Override
    public void guardar(Autor autor) {
        String sql = "INSERT INTO autor (nombre, correo, centro_trabajo, pais_origen, afiliacion_universitaria, experiencia_profesional, grado_academico, colaboraciones_previas, premios_academicos, asociaciones_profesionales, nivel_colaboracion_internacional) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            setAutorParams(stmt, autor);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al guardar autor: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Autor autor) {
        String sql = "UPDATE autor SET nombre = ?, correo = ?, centro_trabajo = ?, pais_origen = ?, afiliacion_universitaria = ?, experiencia_profesional = ?, grado_academico = ?, colaboraciones_previas = ?, premios_academicos = ?, asociaciones_profesionales = ?, nivel_colaboracion_internacional = ? WHERE id_autor = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            setAutorParams(stmt, autor);
            stmt.setInt(12, autor.getIdAutor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar autor: " + e.getMessage());
        }
    }

    @Override
    public Autor buscarPorId(int id) {
        String sql = "SELECT * FROM autor WHERE id_autor = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirDesdeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar autor por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Autor> listarTodos() {
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT * FROM autor ORDER BY nombre";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(construirDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar autores: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM autor WHERE id_autor = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar autor: " + e.getMessage());
        }
    }
    
    // Método de ayuda para no repetir código en guardar y actualizar
    private void setAutorParams(PreparedStatement stmt, Autor autor) throws SQLException {
        stmt.setString(1, autor.getNombre());
        stmt.setString(2, autor.getCorreo());
        stmt.setString(3, autor.getCentroTrabajo());
        stmt.setString(4, autor.getPaisOrigen());
        stmt.setString(5, autor.getAfiliacionUniversitaria());
        stmt.setString(6, autor.getExperienciaProfesional());
        stmt.setString(7, autor.getGradoAcademico());
        stmt.setString(8, autor.getColaboracionesPrevias());
        stmt.setString(9, autor.getPremiosAcademicos());
        stmt.setString(10, autor.getAsociacionesProfesionales());
        stmt.setString(11, autor.getNivelColaboracionInternacional());
    }

    private Autor construirDesdeResultSet(ResultSet rs) throws SQLException {
        Autor a = new Autor();
        a.setIdAutor(rs.getInt("id_autor"));
        a.setNombre(rs.getString("nombre"));
        a.setCorreo(rs.getString("correo"));
        a.setCentroTrabajo(rs.getString("centro_trabajo"));
        a.setPaisOrigen(rs.getString("pais_origen"));
        a.setAfiliacionUniversitaria(rs.getString("afiliacion_universitaria"));
        a.setExperienciaProfesional(rs.getString("experiencia_profesional"));
        a.setGradoAcademico(rs.getString("grado_academico"));
        a.setColaboracionesPrevias(rs.getString("colaboraciones_previas"));
        a.setPremiosAcademicos(rs.getString("premios_academicos"));
        a.setAsociacionesProfesionales(rs.getString("asociaciones_profesionales"));
        a.setNivelColaboracionInternacional(rs.getString("nivel_colaboracion_internacional"));
        return a;
    }
}