package com.mycompany.sisbibliografico.adapters.ui;

import com.mycompany.sisbibliografico.application.service.RevistaService;
import com.mycompany.sisbibliografico.domain.entities.Revista;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RevistaQueryDialog extends JDialog {

    private final RevistaService revistaService;
    private final JComboBox<String> comboConsultas;
    private final JTextField txtCriterio = new JTextField(30);
    private final JButton btnEjecutar = new JButton("Ejecutar Consulta");
    private final DefaultTableModel tableModel;

    public RevistaQueryDialog(Frame owner, RevistaService revistaService) {
        super(owner, "Ventana de Consultas de Revistas", false);
        this.revistaService = revistaService;

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));
        ((JPanel) contentPane).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Panel Superior: Controles de Consulta ---
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        String[] consultas = {"Buscar por Nombre", "Buscar por Editor", "Buscar por Frecuencia", "Buscar por Año de Fundación"};
        comboConsultas = new JComboBox<>(consultas);
        panelControles.add(new JLabel("Tipo de Consulta:"));
        panelControles.add(comboConsultas);
        panelControles.add(new JLabel("Criterio:"));
        panelControles.add(txtCriterio);
        panelControles.add(btnEjecutar);
        contentPane.add(panelControles, BorderLayout.NORTH);

        // --- Panel Central: Tabla de Resultados ---
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Resultados de la Consulta"));
        String[] columnNames = {"ID", "Nombre", "Editor", "Año Fundación", "Frecuencia", "Temas Abordados"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable tablaResultados = new JTable(tableModel);
        panelTabla.add(new JScrollPane(tablaResultados), BorderLayout.CENTER);
        contentPane.add(panelTabla, BorderLayout.CENTER);

        // Listener
        btnEjecutar.addActionListener(e -> ejecutarConsulta());
        
        setSize(950, 500);
        setLocationRelativeTo(owner);
    }

    private void ejecutarConsulta() {
        String tipoConsulta = (String) comboConsultas.getSelectedItem();
        String criterio = txtCriterio.getText();
        if (criterio.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un criterio de búsqueda.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Revista> resultados = null;
        try {
            switch (tipoConsulta) {
                case "Buscar por Nombre":
                    resultados = revistaService.buscarRevistasPorNombre(criterio);
                    break;
                case "Buscar por Editor":
                    resultados = revistaService.buscarRevistasPorEditor(criterio);
                    break;
                case "Buscar por Frecuencia":
                    resultados = revistaService.buscarRevistasPorFrecuencia(criterio);
                    break;
                case "Buscar por Año de Fundación":
                    int anio = Integer.parseInt(criterio);
                    resultados = revistaService.buscarRevistasPorAnioFundacion(anio);
                    break;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Para la búsqueda por año, por favor ingrese un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
        
        actualizarTabla(resultados);
    }

    private void actualizarTabla(List<Revista> revistas) {
        tableModel.setRowCount(0);
        if (revistas != null && !revistas.isEmpty()) {
            for (Revista r : revistas) {
                tableModel.addRow(new Object[]{
                    r.getIdRevista(),
                    r.getNombre(),
                    r.getEditor(),
                    r.getAnioFundacion(),
                    r.getFrecuencia(),
                    r.getTemasAbordados()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron resultados para su búsqueda.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}