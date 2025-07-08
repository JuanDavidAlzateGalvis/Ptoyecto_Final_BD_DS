package com.mycompany.sisbibliografico.infrastructure.postgresql;

import com.mycompany.sisbibliografico.domain.entities.TemaInvestigacion;
import com.mycompany.sisbibliografico.domain.repository.TemaInvestigacionRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TemaInvestigacionRepositoryPostgres implements TemaInvestigacionRepository {
    
    private final Connection conn = ConexionBD.getConnection();

    @Override
    public void guardar(TemaInvestigacion tema) {
        String sql = "INSERT INTO tema_investigacion (nombre) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tema.getNombre());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al guardar tema de investigación: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(TemaInvestigacion tema) {
        String sql = "UPDATE tema_investigacion SET nombre = ? WHERE id_tema = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tema.getNombre());
            stmt.setInt(2, tema.getIdTema());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar tema de investigación: " + e.getMessage());
        }
    }

    @Override
    public TemaInvestigacion buscarPorId(int id) {
        String sql = "SELECT * FROM tema_investigacion WHERE id_tema = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirDesdeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar tema de investigación por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<TemaInvestigacion> listarTodos() {
        List<TemaInvestigacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM tema_investigacion ORDER BY nombre";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(construirDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar temas de investigación: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM tema_investigacion WHERE id_tema = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar tema de investigación: " + e.getMessage());
        }
    }

    private TemaInvestigacion construirDesdeResultSet(ResultSet rs) throws SQLException {
        TemaInvestigacion tema = new TemaInvestigacion();
        tema.setIdTema(rs.getInt("id_tema"));
        tema.setNombre(rs.getString("nombre"));
        return tema;
    }
}
