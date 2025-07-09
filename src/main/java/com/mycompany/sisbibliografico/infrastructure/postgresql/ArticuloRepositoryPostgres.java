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
        String sql = "INSERT INTO articulo (titulo, palabras_clave, correo_contacto, disponibilidad, ubicacion, tipo_publicacion, fecha_publicacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
    public void actualizar(Articulo articulo) {
        String sql = "UPDATE articulo SET titulo = ?, palabras_clave = ?, correo_contacto = ?, disponibilidad = ?, ubicacion = ?, tipo_publicacion = ?, fecha_publicacion = ? WHERE id_articulo = ?";
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
    public Articulo buscarPorId(int id) {
        String sql = "SELECT * FROM articulo WHERE id_articulo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirDesdeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar artículo por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Articulo> listarTodos() {
        List<Articulo> lista = new ArrayList<>();
        String sql = "SELECT * FROM articulo ORDER BY fecha_publicacion DESC, titulo";
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

        // --- INICIO DE LA CORRECCIÓN ---
        // Se obtiene la fecha de la base de datos
        Date fechaSql = rs.getDate("fecha_publicacion");

        // Se comprueba si la fecha no es nula antes de convertirla
        if (fechaSql != null) {
            a.setFechaPublicacion(fechaSql.toLocalDate());
        } else {
            a.setFechaPublicacion(null); // Si es nula, se asigna null al objeto
        }
        // --- FIN DE LA CORRECCIÓN ---

        return a;
    }
      @Override
    public List<Articulo> buscarPorTitulo(String titulo) {
        List<Articulo> articulos = new ArrayList<>();
        // Usamos ILIKE para una búsqueda insensible a mayúsculas/minúsculas
        String sql = "SELECT * FROM articulo WHERE titulo ILIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + titulo + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                articulos.add(construirDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar artículos por título: " + e.getMessage());
        }
        return articulos;
    }

    @Override
    public List<Articulo> buscarPorAnioPublicacion(int anio) {
        List<Articulo> articulos = new ArrayList<>();
        // Usamos EXTRACT para obtener el año de la fecha
        String sql = "SELECT * FROM articulo WHERE EXTRACT(YEAR FROM fecha_publicacion) = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, anio);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                articulos.add(construirDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar artículos por año: " + e.getMessage());
        }
        return articulos;
    }

    @Override
    public List<Articulo> buscarPorNombreRevista(String nombreRevista) {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT a.* FROM articulo a " +
                     "JOIN revista_articulo ra ON a.id_articulo = ra.id_articulo " +
                     "JOIN revista r ON ra.id_revista = r.id_revista " +
                     "WHERE r.nombre ILIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombreRevista + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                articulos.add(construirDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar artículos por revista: " + e.getMessage());
        }
        return articulos;
    }
    @Override
    public List<Articulo> buscarPorPalabrasClave(String palabrasClave) {
        return buscarPorCampoDeTexto("palabras_clave", palabrasClave);
    }

    @Override
    public List<Articulo> buscarPorResumen(String termino) {
        return buscarPorCampoDeTexto("resumen", termino);
    }

    @Override
    public List<Articulo> buscarPorMetodologia(String metodologia) {
        return buscarPorCampoDeTexto("metodologia", metodologia);
    }
    
    @Override
    public List<Articulo> buscarPorRangoPaginas(String paginas) {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT a.* FROM articulo a " +
                     "JOIN revista_articulo ra ON a.id_articulo = ra.id_articulo " +
                     "WHERE ra.paginas = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paginas);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                articulos.add(construirDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar por páginas: " + e.getMessage());
        }
        return articulos;
    }

    @Override
    public List<Articulo> buscarPorEditorRevista(String editor) {
        return buscarArticuloPorRelacion("revista_articulo", "revista", "id_revista", "editor", editor);
    }

    @Override
    public List<Articulo> buscarPorAreaTematicaRevista(String areaTematica) {
        return buscarArticuloPorRelacion("revista_articulo", "revista", "id_revista", "area_tematica", areaTematica);
    }

    @Override
    public List<Articulo> buscarPorPaisCongreso(String pais) {
        return buscarArticuloPorRelacion("congreso_articulo", "congreso", "id_congreso", "pais", pais);
    }

    @Override
    public List<Articulo> buscarPorCiudadCongreso(String ciudad) {
        return buscarArticuloPorRelacion("congreso_articulo", "congreso", "id_congreso", "ciudad", ciudad);
    }
    
    @Override
    public List<Articulo> buscarPorTipoCongreso(String tipo) {
        return buscarArticuloPorRelacion("congreso_articulo", "congreso", "id_congreso", "tipo", tipo);
    }
    
    @Override
    public List<Articulo> buscarPorGrupoInvestigacion(String nombreGrupo) {
        return buscarArticuloPorRelacion("articulo_grupo", "grupo_investigacion", "id_grupo", "nombre_grupo", nombreGrupo);
    }

    @Override
    public List<Articulo> buscarPorProyectoInvestigacion(String nombreProyecto) {
        return buscarArticuloPorRelacion("articulo_proyecto", "proyecto_investigacion", "id_proyecto", "nombre_proyecto", nombreProyecto);
    }

    // --- MÉTODOS PRIVADOS DE AYUDA PARA EVITAR REPETIR CÓDIGO ---

    private List<Articulo> buscarPorCampoDeTexto(String nombreColumna, String criterio) {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM articulo WHERE LOWER(" + nombreColumna + ") LIKE LOWER(?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + criterio + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                articulos.add(construirDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error en búsqueda genérica de texto: " + e.getMessage());
        }
        return articulos;
    }

    private List<Articulo> buscarArticuloPorRelacion(String tablaUnion, String tablaPrincipal, String idPrincipal, String columnaBusqueda, String criterio) {
        List<Articulo> articulos = new ArrayList<>();
        String sql = String.format(
            "SELECT DISTINCT a.* FROM articulo a " +
            "JOIN %s tj ON a.id_articulo = tj.id_articulo " +
            "JOIN %s tp ON tj.%s = tp.%s " +
            "WHERE LOWER(tp.%s) LIKE LOWER(?)",
            tablaUnion, tablaPrincipal, idPrincipal, idPrincipal, columnaBusqueda
        );
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + criterio + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                articulos.add(construirDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error en búsqueda por relación: " + e.getMessage());
        }
        return articulos;
    }
    @Override
    public List<Articulo> buscarPorTemaInvestigacion(String tema) {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT DISTINCT a.* FROM articulo a " +
                     "JOIN articulo_autor aa ON a.id_articulo = aa.id_articulo " +
                     "JOIN autor_tema at ON aa.id_autor = at.id_autor " +
                     "JOIN tema_investigacion ti ON at.id_tema = ti.id_tema " +
                     "WHERE LOWER(ti.nombre) LIKE LOWER(?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + tema + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                articulos.add(construirDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar por tema de investigación: " + e.getMessage());
        }
        return articulos;
    }

    @Override
    public List<Articulo> buscarPorEdicionCongreso(String edicion) {
        return buscarArticuloPorRelacion("congreso_articulo", "congreso", "id_congreso", "edicion", edicion);
    }
    
    @Override
    public List<Articulo> buscarPorFechaPublicacion(LocalDate fecha) {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM articulo WHERE fecha_publicacion = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(fecha));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                articulos.add(construirDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar por fecha de publicación: " + e.getMessage());
        }
        return articulos;
    }

    @Override
    public List<Articulo> buscarPorFrecuenciaRevista(String frecuencia) {
        return buscarArticuloPorRelacion("revista_articulo", "revista", "id_revista", "frecuencia", frecuencia);
    }

    @Override
    public List<Articulo> buscarPorIdRevista(int idRevista) {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT a.* FROM articulo a " +
                     "JOIN revista_articulo ra ON a.id_articulo = ra.id_articulo " +
                     "WHERE ra.id_revista = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRevista);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                articulos.add(construirDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar por ID de revista: " + e.getMessage());
        }
        return articulos;
    }
}