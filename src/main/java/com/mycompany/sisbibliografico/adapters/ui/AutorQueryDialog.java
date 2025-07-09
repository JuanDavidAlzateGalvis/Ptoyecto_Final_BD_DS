package com.mycompany.sisbibliografico.adapters.ui;

import com.mycompany.sisbibliografico.application.service.AutorService;
import com.mycompany.sisbibliografico.domain.entities.Autor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AutorQueryDialog extends JDialog {

    private final AutorService autorService;
    private final DefaultTableModel tableModel;
    
    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtPais = new JTextField(15);
    private final JTextField txtAfiliacion = new JTextField(15);
    private final JTextField txtGrado = new JTextField(15);

    public AutorQueryDialog(Frame owner, AutorService autorService) {
        super(owner, "Panel de Consulta de Autores", true);
        this.autorService = autorService;

        setSize(900, 600);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        // Panel de Filtros
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; panelFiltros.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; panelFiltros.add(txtNombre, gbc);

        gbc.gridx = 2; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; panelFiltros.add(new JLabel("País de Origen:"), gbc);
        gbc.gridx = 3; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; panelFiltros.add(txtPais, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; panelFiltros.add(new JLabel("Afiliación Univ.:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; panelFiltros.add(txtAfiliacion, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; panelFiltros.add(new JLabel("Grado Académico:"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; panelFiltros.add(txtGrado, gbc);

        JButton btnBuscar = new JButton("Buscar");
        gbc.gridx = 4; gbc.gridy = 0; gbc.gridheight = 2; gbc.fill = GridBagConstraints.VERTICAL; panelFiltros.add(btnBuscar, gbc);
        
        add(panelFiltros, BorderLayout.NORTH);

        // Tabla de Resultados
        String[] columnNames = {"ID", "Nombre", "Correo", "País", "Grado Académico", "Afiliación"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Acción del botón
        btnBuscar.addActionListener(e -> buscar());

        buscar();
    }

    private void buscar() {
        String nombre = txtNombre.getText();
        String pais = txtPais.getText();
        String afiliacion = txtAfiliacion.getText();
        String grado = txtGrado.getText();

        List<Autor> autores = autorService.buscarAutores(nombre, pais, afiliacion, grado);
        
        tableModel.setRowCount(0); // Limpiar tabla
        for (Autor a : autores) {
            tableModel.addRow(new Object[]{
                a.getIdAutor(), a.getNombre(), a.getCorreo(), a.getPaisOrigen(),
                a.getGradoAcademico(), a.getAfiliacionUniversitaria()
            });
        }
    }
}
