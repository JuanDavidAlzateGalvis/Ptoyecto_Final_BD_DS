package com.mycompany.sisbibliografico.infrastructure.postgresql;

import com.mycompany.sisbibliografico.domain.entities.Articulo;
import com.mycompany.sisbibliografico.domain.repository.ArticuloRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArticuloRepositoryPostgres implements ArticuloRepository {

    private final Connection conn = ConexionBD.getConnection();

    @Override
    public void guardar(Articulo articulo) {
        String sql = "INSERT INTO articulo (titulo, palabras_clave, correo_contacto, disponibilidad, ubicacion, tipo_publicacion, fecha_publicacion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, articulo.getTitulo());
            stmt.setString(2, articulo.getPalabrasClave());
            stmt.setString(3, articulo.getCorreoContacto());
            stmt.setBoolean(4, articulo.isDisponibilidad());
            stmt.setString(5, articulo.getUbicacion());
            stmt.setString(6, articulo.getTipoPublicacion());
            stmt.setDate(7, Date.valueOf(articulo.getFechaPublicacion()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al guardar artículo: " + e.getMessage());
        }
    }

    @Override
    public Articulo buscarPorId(int id) {
        String sql = "SELECT * FROM articulo WHERE id_articulo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return construirDesdeResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar artículo por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Articulo> listarTodos() {
        List<Articulo> lista = new ArrayList<>();
        String sql = "SELECT * FROM articulo ORDER BY id_articulo";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(construirDesdeResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar artículos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void actualizar(Articulo articulo) {
        String sql = "UPDATE articulo SET titulo = ?, palabras_clave = ?, correo_contacto = ?, disponibilidad = ?, " +
                     "ubicacion = ?, tipo_publicacion = ?, fecha_publicacion = ? WHERE id_articulo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, articulo.getTitulo());
            stmt.setString(2, articulo.getPalabrasClave());
            stmt.setString(3, articulo.getCorreoContacto());
            stmt.setBoolean(4, articulo.isDisponibilidad());
            stmt.setString(5, articulo.getUbicacion());
            stmt.setString(6, articulo.getTipoPublicacion());
            stmt.setDate(7, Date.valueOf(articulo.getFechaPublicacion()));
            stmt.setInt(8, articulo.getIdArticulo());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar artículo: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM articulo WHERE id_articulo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar artículo: " + e.getMessage());
        }
    }

    private Articulo construirDesdeResultSet(ResultSet rs) throws SQLException {
        Articulo a = new Articulo();
        a.setIdArticulo(rs.getInt("id_articulo"));
        a.setTitulo(rs.getString("titulo"));
        a.setPalabrasClave(rs.getString("palabras_clave"));
        a.setCorreoContacto(rs.getString("correo_contacto"));
        a.setDisponibilidad(rs.getBoolean("disponibilidad"));
        a.setUbicacion(rs.getString("ubicacion"));
        a.setTipoPublicacion(rs.getString("tipo_publicacion"));

        Date fecha = rs.getDate("fecha_publicacion");
        a.setFechaPublicacion(fecha != null ? fecha.toLocalDate() : LocalDate.now());

        return a;
    }
}

