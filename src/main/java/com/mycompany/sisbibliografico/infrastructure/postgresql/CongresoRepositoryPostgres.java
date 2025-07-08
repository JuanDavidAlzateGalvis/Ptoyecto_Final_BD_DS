package com.mycompany.sisbibliografico.infrastructure.postgresql;

import com.mycompany.sisbibliografico.domain.entities.Congreso;
import com.mycompany.sisbibliografico.domain.repository.CongresoRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CongresoRepositoryPostgres implements CongresoRepository {
    
    private final Connection conn = ConexionBD.getConnection();

    @Override
    public void guardar(Congreso congreso) {
        String sql = "INSERT INTO congreso (nombre, edicion, ciudad, pais, fecha_inicio, fecha_fin, tipo, frecuencia, anio_primera_edicion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, congreso.getNombre());
            stmt.setString(2, congreso.getEdicion());
            stmt.setString(3, congreso.getCiudad());
            stmt.setString(4, congreso.getPais());
            stmt.setDate(5, Date.valueOf(congreso.getFechaInicio()));
            stmt.setDate(6, Date.valueOf(congreso.getFechaFin()));
            stmt.setString(7, congreso.getTipo());
            stmt.setString(8, congreso.getFrecuencia());
            stmt.setInt(9, congreso.getAnioPrimeraEdicion());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al guardar congreso: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Congreso congreso) {
        String sql = "UPDATE congreso SET nombre = ?, edicion = ?, ciudad = ?, pais = ?, fecha_inicio = ?, fecha_fin = ?, tipo = ?, frecuencia = ?, anio_primera_edicion = ? WHERE id_congreso = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, congreso.getNombre());
            stmt.setString(2, congreso.getEdicion());
            stmt.setString(3, congreso.getCiudad());
            stmt.setString(4, congreso.getPais());
            stmt.setDate(5, Date.valueOf(congreso.getFechaInicio()));
            stmt.setDate(6, Date.valueOf(congreso.getFechaFin()));
            stmt.setString(7, congreso.getTipo());
            stmt.setString(8, congreso.getFrecuencia());
            stmt.setInt(9, congreso.getAnioPrimeraEdicion());
            stmt.setInt(10, congreso.getIdCongreso());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar congreso: " + e.getMessage());
        }
    }

    @Override
    public Congreso buscarPorId(int id) {
        String sql = "SELECT * FROM congreso WHERE id_congreso = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirDesdeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar congreso por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Congreso> listarTodos() {
        List<Congreso> lista = new ArrayList<>();
        String sql = "SELECT * FROM congreso ORDER BY fecha_inicio DESC, nombre";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(construirDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar congresos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM congreso WHERE id_congreso = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar congreso: " + e.getMessage());
        }
    }

    private Congreso construirDesdeResultSet(ResultSet rs) throws SQLException {
        Congreso c = new Congreso();
        c.setIdCongreso(rs.getInt("id_congreso"));
        c.setNombre(rs.getString("nombre"));
        c.setEdicion(rs.getString("edicion"));
        c.setCiudad(rs.getString("ciudad"));
        c.setPais(rs.getString("pais"));
        c.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
        c.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
        c.setTipo(rs.getString("tipo"));
        c.setFrecuencia(rs.getString("frecuencia"));
        c.setAnioPrimeraEdicion(rs.getInt("anio_primera_edicion"));
        return c;
    }
}