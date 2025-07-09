package com.mycompany.sisbibliografico.adapters.ui;

import com.mycompany.sisbibliografico.application.service.TemaInvestigacionService;
import com.mycompany.sisbibliografico.domain.entities.TemaInvestigacion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TemaInvestigacionQueryDialog extends JDialog {

    private final TemaInvestigacionService temaService;
    private final DefaultTableModel tableModel;
    private final JTextField txtNombre = new JTextField(20);

    public TemaInvestigacionQueryDialog(Frame owner, TemaInvestigacionService temaService) {
        super(owner, "Panel de Consulta de Temas", true);
        this.temaService = temaService;

        setSize(600, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        // Panel de Filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelFiltros.add(new JLabel("Nombre del Tema:"));
        panelFiltros.add(txtNombre);
        JButton btnBuscar = new JButton("Buscar");
        panelFiltros.add(btnBuscar);
        add(panelFiltros, BorderLayout.NORTH);

        // Tabla de Resultados
        String[] columnNames = {"ID", "Nombre del Tema"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> buscar());

        buscar(); // Carga inicial
    }

    private void buscar() {
        List<TemaInvestigacion> temas = temaService.buscarPorNombre(txtNombre.getText());
        tableModel.setRowCount(0);
        for (TemaInvestigacion t : temas) {
            tableModel.addRow(new Object[]{t.getIdTema(), t.getNombre()});
        }
    }
}