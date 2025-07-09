package com.mycompany.sisbibliografico.infrastructure.postgresql;

import com.mycompany.sisbibliografico.domain.entities.InformeTecnico;
import com.mycompany.sisbibliografico.domain.repository.InformeTecnicoRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InformeTecnicoRepositoryPostgres implements InformeTecnicoRepository {
    
    private final Connection conn = ConexionBD.getConnection();

    @Override
    public void guardar(InformeTecnico informe) {
        String sql = "INSERT INTO informe_tecnico (centro_publicacion, mes_publicacion, anio_publicacion) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, informe.getCentroPublicacion());
            stmt.setString(2, informe.getMesPublicacion());
            stmt.setInt(3, informe.getAnioPublicacion());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al guardar informe técnico: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(InformeTecnico informe) {
        String sql = "UPDATE informe_tecnico SET centro_publicacion = ?, mes_publicacion = ?, anio_publicacion = ? WHERE id_informe = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, informe.getCentroPublicacion());
            stmt.setString(2, informe.getMesPublicacion());
            stmt.setInt(3, informe.getAnioPublicacion());
            stmt.setInt(4, informe.getIdInforme());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar informe técnico: " + e.getMessage());
        }
    }

    @Override
    public InformeTecnico buscarPorId(int id) {
        String sql = "SELECT * FROM informe_tecnico WHERE id_informe = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirDesdeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar informe técnico por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<InformeTecnico> listarTodos() {
        List<InformeTecnico> lista = new ArrayList<>();
        String sql = "SELECT * FROM informe_tecnico ORDER BY anio_publicacion DESC, mes_publicacion";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(construirDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar informes técnicos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM informe_tecnico WHERE id_informe = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar informe técnico: " + e.getMessage());
        }
    }

    private InformeTecnico construirDesdeResultSet(ResultSet rs) throws SQLException {
        InformeTecnico informe = new InformeTecnico();
        informe.setIdInforme(rs.getInt("id_informe"));
        informe.setCentroPublicacion(rs.getString("centro_publicacion"));
        informe.setMesPublicacion(rs.getString("mes_publicacion"));
        informe.setAnioPublicacion(rs.getInt("anio_publicacion"));
        return informe;
    }
     @Override
    public List<InformeTecnico> buscarPorCentro(String centro) {
        return buscarPorCampoDeTexto("centro_publicacion", centro);
    }

    @Override
    public List<InformeTecnico> buscarPorMes(String mes) {
        return buscarPorCampoDeTexto("mes_publicacion", mes);
    }

    @Override
    public List<InformeTecnico> buscarPorAnio(int anio) {
        List<InformeTecnico> informes = new ArrayList<>();
        String sql = "SELECT * FROM informe_tecnico WHERE anio_publicacion = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, anio);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                informes.add(construirDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar por año: " + e.getMessage());
        }
        return informes;
    }

    // Método de ayuda para no repetir código
    private List<InformeTecnico> buscarPorCampoDeTexto(String columna, String criterio) {
        List<InformeTecnico> informes = new ArrayList<>();
        String sql = "SELECT * FROM informe_tecnico WHERE LOWER(" + columna + ") LIKE LOWER(?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + criterio + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                informes.add(construirDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error en búsqueda genérica: " + e.getMessage());
        }
        return informes;
    }
}