package com.mycompany.sisbibliografico.infrastructure.postgresql;

import com.mycompany.sisbibliografico.domain.entities.Revista;
import com.mycompany.sisbibliografico.domain.repository.RevistaRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RevistaRepositoryPostgres implements RevistaRepository {
    
    private final Connection conn = ConexionBD.getConnection();

    @Override
    public void guardar(Revista revista) {
        String sql = "INSERT INTO revista (nombre, editor, anio_fundacion, frecuencia, temas_abordados) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, revista.getNombre());
            stmt.setString(2, revista.getEditor());
            stmt.setInt(3, revista.getAnioFundacion());
            stmt.setString(4, revista.getFrecuencia());
            stmt.setString(5, revista.getTemasAbordados());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al guardar revista: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Revista revista) {
        String sql = "UPDATE revista SET nombre = ?, editor = ?, anio_fundacion = ?, frecuencia = ?, temas_abordados = ? WHERE id_revista = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, revista.getNombre());
            stmt.setString(2, revista.getEditor());
            stmt.setInt(3, revista.getAnioFundacion());
            stmt.setString(4, revista.getFrecuencia());
            stmt.setString(5, revista.getTemasAbordados());
            stmt.setInt(6, revista.getIdRevista());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar revista: " + e.getMessage());
        }
    }

    @Override
    public Revista buscarPorId(int id) {
        String sql = "SELECT * FROM revista WHERE id_revista = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirDesdeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar revista por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Revista> listarTodas() {
        List<Revista> lista = new ArrayList<>();
        String sql = "SELECT * FROM revista ORDER BY nombre";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(construirDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar revistas: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM revista WHERE id_revista = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar revista: " + e.getMessage());
        }
    }

    private Revista construirDesdeResultSet(ResultSet rs) throws SQLException {
        Revista r = new Revista();
        r.setIdRevista(rs.getInt("id_revista"));
        r.setNombre(rs.getString("nombre"));
        r.setEditor(rs.getString("editor"));
        r.setAnioFundacion(rs.getInt("anio_fundacion"));
        r.setFrecuencia(rs.getString("frecuencia"));
        r.setTemasAbordados(rs.getString("temas_abordados"));
        return r;
    }
}