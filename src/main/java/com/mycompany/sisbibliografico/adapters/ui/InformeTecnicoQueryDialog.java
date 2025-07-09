package com.mycompany.sisbibliografico.adapters.ui;

import com.mycompany.sisbibliografico.application.service.InformeTecnicoService;
import com.mycompany.sisbibliografico.domain.entities.InformeTecnico;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InformeTecnicoQueryDialog extends JDialog {
    private final InformeTecnicoService informeService;
    private final JComboBox<String> comboConsultas;
    private final JTextField txtCriterio = new JTextField(30);
    private final JButton btnEjecutar = new JButton("Ejecutar Consulta");
    private final DefaultTableModel tableModel;

    public InformeTecnicoQueryDialog(Frame owner, InformeTecnicoService informeService) {
        super(owner, "Ventana de Consultas de Informes Técnicos", false);
        this.informeService = informeService;

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));
        ((JPanel) contentPane).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        String[] consultas = {"Buscar por ID", "Buscar por Centro de Publicación", "Buscar por Mes", "Buscar por Año"};
        comboConsultas = new JComboBox<>(consultas);
        panelControles.add(new JLabel("Tipo de Consulta:"));
        panelControles.add(comboConsultas);
        panelControles.add(new JLabel("Criterio:"));
        panelControles.add(txtCriterio);
        panelControles.add(btnEjecutar);
        contentPane.add(panelControles, BorderLayout.NORTH);

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Resultados de la Consulta"));
        String[] columnNames = {"ID", "Centro de Publicación", "Mes", "Año"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable tablaResultados = new JTable(tableModel);
        panelTabla.add(new JScrollPane(tablaResultados), BorderLayout.CENTER);
        contentPane.add(panelTabla, BorderLayout.CENTER);

        btnEjecutar.addActionListener(e -> ejecutarConsulta());

        setSize(800, 500);
        setLocationRelativeTo(owner);
    }

    private void ejecutarConsulta() {
        String tipoConsulta = (String) comboConsultas.getSelectedItem();
        String criterio = txtCriterio.getText();
        if (criterio.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un criterio de búsqueda.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<InformeTecnico> resultados = new ArrayList<>();
        try {
            switch (tipoConsulta) {
                case "Buscar por ID":
                    InformeTecnico informe = informeService.obtenerInformeTecnicoPorId(Integer.parseInt(criterio));
                    if (informe != null) {
                        resultados.add(informe);
                    }
                    break;
                case "Buscar por Centro de Publicación":
                    resultados = informeService.buscarInformesPorCentro(criterio);
                    break;
                case "Buscar por Mes":
                    resultados = informeService.buscarInformesPorMes(criterio);
                    break;
                case "Buscar por Año":
                    resultados = informeService.buscarInformesPorAnio(Integer.parseInt(criterio));
                    break;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Para búsquedas por ID o Año, por favor ingrese un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
        actualizarTabla(resultados);
    }

    private void actualizarTabla(List<InformeTecnico> informes) {
        tableModel.setRowCount(0);
        if (informes != null && !informes.isEmpty()) {
            for (InformeTecnico i : informes) {
                tableModel.addRow(new Object[]{i.getIdInforme(), i.getCentroPublicacion(), i.getMesPublicacion(), i.getAnioPublicacion()});
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron resultados para su búsqueda.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}