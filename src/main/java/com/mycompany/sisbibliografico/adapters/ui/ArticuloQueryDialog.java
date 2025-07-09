package com.mycompany.sisbibliografico.adapters.ui;

import com.mycompany.sisbibliografico.application.service.ArticuloService;
import com.mycompany.sisbibliografico.domain.entities.Articulo;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ArticuloQueryDialog extends JDialog {

    private final ArticuloService articuloService;
    private final JComboBox<String> comboConsultas;
    private final JTextField txtCriterio = new JTextField(25);
    private final JButton btnEjecutar = new JButton("Ejecutar Consulta");
    private final DefaultTableModel tableModel;

    public ArticuloQueryDialog(Frame owner, ArticuloService articuloService) {
        super(owner, "Ventana de Consultas de Artículos", false);
        this.articuloService = articuloService;

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));
        ((JPanel) contentPane).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        // --- LISTA COMPLETA DE CONSULTAS ---
        String[] consultas = {
            "Buscar por Título", 
            "Buscar por Palabras Clave",
            "Buscar por Año de Publicación",
            "Buscar por Fecha de Publicación (AAAA-MM-DD)",
            "Buscar por Rango de Páginas",
            "Buscar por Nombre de Revista",
            "Buscar por ID de Revista",
            "Buscar por Editor de Revista",
            "Buscar por Área Temática de Revista",
            "Buscar por Frecuencia de Revista",
            "Buscar por País del Congreso",
            "Buscar por Ciudad del Congreso",
            "Buscar por Edición del Congreso",
            "Buscar por Tipo de Congreso",
            "Buscar por Grupo de Investigación",
            "Buscar por Proyecto de Investigación",
            "Buscar por Tema de Investigación"
        };
        comboConsultas = new JComboBox<>(consultas);
        panelControles.add(new JLabel("Tipo de Consulta:"));
        panelControles.add(comboConsultas);
        panelControles.add(new JLabel("Criterio:"));
        panelControles.add(txtCriterio);
        panelControles.add(btnEjecutar);
        
        contentPane.add(panelControles, BorderLayout.NORTH);

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Resultados de la Consulta"));
        String[] columnNames = {"ID", "Título", "Tipo", "Fecha", "Palabras Clave", "Correo", "Disponibilidad", "Ubicación"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable tablaResultados = new JTable(tableModel);
        panelTabla.add(new JScrollPane(tablaResultados), BorderLayout.CENTER);
        
        contentPane.add(panelTabla, BorderLayout.CENTER);

        btnEjecutar.addActionListener(e -> ejecutarConsulta());
        
        setSize(950, 600);
        setLocationRelativeTo(owner);
    }

    private void ejecutarConsulta() {
        String tipoConsulta = (String) comboConsultas.getSelectedItem();
        String criterio = txtCriterio.getText();
        if (criterio.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un criterio de búsqueda.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Articulo> resultados = null;
        try {
            switch (tipoConsulta) {
                case "Buscar por Título":
                    resultados = articuloService.buscarArticulosPorTitulo(criterio);
                    break;
                case "Buscar por Palabras Clave":
                    resultados = articuloService.buscarArticulosPorPalabrasClave(criterio);
                    break;
                case "Buscar por Año de Publicación":
                    resultados = articuloService.buscarArticulosPorAnio(Integer.parseInt(criterio));
                    break;
                case "Buscar por Fecha de Publicación (AAAA-MM-DD)":
                    resultados = articuloService.buscarArticulosPorFecha(LocalDate.parse(criterio));
                    break;
                case "Buscar por Rango de Páginas":
                    resultados = articuloService.buscarArticulosPorRangoPaginas(criterio);
                    break;
                case "Buscar por Nombre de Revista":
                    resultados = articuloService.buscarArticulosPorNombreRevista(criterio);
                    break;
                case "Buscar por ID de Revista":
                     resultados = articuloService.buscarArticulosPorIdRevista(Integer.parseInt(criterio));
                    break;
                case "Buscar por Editor de Revista":
                    resultados = articuloService.buscarArticulosPorEditorRevista(criterio);
                    break;
                case "Buscar por Área Temática de Revista":
                    resultados = articuloService.buscarArticulosPorAreaTematica(criterio);
                    break;
                case "Buscar por Frecuencia de Revista":
                    resultados = articuloService.buscarArticulosPorFrecuenciaRevista(criterio);
                    break;
                case "Buscar por País del Congreso":
                    resultados = articuloService.buscarArticulosPorPaisCongreso(criterio);
                    break;
                case "Buscar por Ciudad del Congreso":
                    resultados = articuloService.buscarArticulosPorCiudadCongreso(criterio);
                    break;
                case "Buscar por Edición del Congreso":
                    resultados = articuloService.buscarArticulosPorEdicionCongreso(criterio);
                    break;
                case "Buscar por Tipo de Congreso":
                    resultados = articuloService.buscarArticulosPorTipoCongreso(criterio);
                    break;
                case "Buscar por Grupo de Investigación":
                    resultados = articuloService.buscarArticulosPorGrupo(criterio);
                    break;
                case "Buscar por Proyecto de Investigación":
                    resultados = articuloService.buscarArticulosPorProyecto(criterio);
                    break;
                case "Buscar por Tema de Investigación":
                    resultados = articuloService.buscarArticulosPorTema(criterio);
                    break;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Para búsquedas por Año o ID, por favor ingrese un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Para la búsqueda por fecha, use el formato AAAA-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }

        actualizarTabla(resultados);
    }

    private void actualizarTabla(List<Articulo> articulos) {
        tableModel.setRowCount(0);
        if (articulos != null && !articulos.isEmpty()) {
            for (Articulo a : articulos) {
                Object fecha = a.getFechaPublicacion() != null ? a.getFechaPublicacion().toString() : "N/D";
                tableModel.addRow(new Object[]{
                    a.getIdArticulo(), a.getTitulo(), a.getTipoPublicacion(), fecha,
                    a.getPalabrasClave(), a.getCorreoContacto(), a.isDisponibilidad(),
                    a.getUbicacion()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron resultados para su búsqueda.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}