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
            System.err.println("Error al guardar informe técnico: " + e.getMessage());
        }
    }

    @Override
    public InformeTecnico buscarPorId(int id) {
        String sql = "SELECT * FROM informe_tecnico WHERE id_informe = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return construir(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar informe: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<InformeTecnico> listarTodos() {
        List<InformeTecnico> lista = new ArrayList<>();
        String sql = "SELECT * FROM informe_tecnico ORDER BY id_informe";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(construir(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar informes: " + e.getMessage());
        }
        return lista;
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
            System.err.println("Error al actualizar informe: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM informe_tecnico WHERE id_informe = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar informe: " + e.getMessage());
        }
    }

    private InformeTecnico construir(ResultSet rs) throws SQLException {
        InformeTecnico i = new InformeTecnico();
        i.setIdInforme(rs.getInt("id_informe"));
        i.setCentroPublicacion(rs.getString("centro_publicacion"));
        i.setMesPublicacion(rs.getString("mes_publicacion"));
        i.setAnioPublicacion(rs.getInt("anio_publicacion"));
        return i;
    }
} 

