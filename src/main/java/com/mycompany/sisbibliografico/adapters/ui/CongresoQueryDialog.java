package com.mycompany.sisbibliografico.adapters.ui;

import com.mycompany.sisbibliografico.application.service.CongresoService;
import com.mycompany.sisbibliografico.domain.entities.Congreso;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CongresoQueryDialog extends JDialog {

    private final CongresoService congresoService;
    private final DefaultTableModel tableModel;

    private final JTextField txtNombre = new JTextField(15);
    private final JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Todos", "nacional", "internacional"});
    private final JTextField txtPais = new JTextField(12);
    private final JTextField txtCiudad = new JTextField(12);

    public CongresoQueryDialog(Frame owner, CongresoService congresoService) {
        super(owner, "Panel de Consulta de Congresos", true);
        this.congresoService = congresoService;

        setSize(800, 500);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));
        
        // --- INICIO DE LA CORRECCIÓN ---

        // Panel de Filtros con GridBagLayout para mejor control
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; panelFiltros.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panelFiltros.add(txtNombre, gbc);

        gbc.gridx = 2; gbc.gridy = 0; panelFiltros.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 3; gbc.gridy = 0; panelFiltros.add(comboTipo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelFiltros.add(new JLabel("País:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panelFiltros.add(txtPais, gbc);

        gbc.gridx = 2; gbc.gridy = 1; panelFiltros.add(new JLabel("Ciudad:"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; panelFiltros.add(txtCiudad, gbc);

        JButton btnBuscar = new JButton("Buscar");
        gbc.gridx = 4; gbc.gridy = 0; gbc.gridheight = 2; // Ocupa dos filas de alto
        gbc.fill = GridBagConstraints.VERTICAL;
        panelFiltros.add(btnBuscar, gbc);

        add(panelFiltros, BorderLayout.NORTH);
        
        // --- FIN DE LA CORRECCIÓN ---

        // Tabla de Resultados
        String[] columnNames = {"ID", "Nombre", "Tipo", "País", "Ciudad", "Fecha Inicio"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> buscar());
        
        buscar(); // Carga inicial
    }

    private void buscar() {
        String nombre = txtNombre.getText();
        String tipo = (String) comboTipo.getSelectedItem();
        String pais = txtPais.getText();
        String ciudad = txtCiudad.getText();

        List<Congreso> congresos = congresoService.buscarCongresos(nombre, tipo, pais, ciudad);

        tableModel.setRowCount(0); // Limpiar tabla
        for (Congreso c : congresos) {
            tableModel.addRow(new Object[]{
                c.getIdCongreso(),
                c.getNombre(),
                c.getTipo(),
                c.getPais(),
                c.getCiudad(),
                c.getFechaInicio()
            });
        }
    }
}