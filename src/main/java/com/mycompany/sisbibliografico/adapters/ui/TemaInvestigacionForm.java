package com.mycompany.sisbibliografico.adapters.ui;

import com.mycompany.sisbibliografico.application.service.TemaInvestigacionService;
import com.mycompany.sisbibliografico.domain.entities.TemaInvestigacion;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TemaInvestigacionForm extends JPanel {

    private final TemaInvestigacionService temaService;
    private final CardLayout layout;
    private final JPanel contentPanel;

    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField txtId = new JTextField();
    private final JTextField txtNombre = new JTextField(30);

    public TemaInvestigacionForm(TemaInvestigacionService temaService, CardLayout layout, JPanel contentPanel) {
        this.temaService = temaService;
        this.layout = layout;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblTitulo = new JLabel("Gestión de Temas de Investigación", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new BorderLayout(20, 0));
        panelCentral.setOpaque(false);
        add(panelCentral, BorderLayout.CENTER);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(225, 220, 240));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(8, 5, 8, 5);
        
        gbc.gridx = 0; gbc.gridy = 0; panelFormulario.add(new JLabel("Nombre del Tema:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; panelFormulario.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weighty = 1.0; panelFormulario.add(new JLabel(), gbc);
        
        panelCentral.add(panelFormulario, BorderLayout.WEST);

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setOpaque(false);
        panelTabla.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Temas Registrados", TitledBorder.LEFT, TitledBorder.TOP));
        
        String[] columnNames = {"ID", "Nombre del Tema"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        panelCentral.add(panelTabla, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotones.setOpaque(false);
        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnMostrar = new JButton("Mostrar");
        JButton btnVolverInicio = new JButton("Volver a Inicio");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnMostrar);
        panelBotones.add(btnVolverInicio);
        add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarTema());
        btnActualizar.addActionListener(e -> actualizarTema());
        btnEliminar.addActionListener(e -> eliminarTema());
        btnMostrar.addActionListener(e -> actualizarTabla());
        btnVolverInicio.addActionListener(e -> layout.show(contentPanel, "inicio"));
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                poblarCamposDesdeTabla();
            }
        });
        
        actualizarTabla();
    }
    
    private void actualizarTabla() {
        tableModel.setRowCount(0);
        List<TemaInvestigacion> temas = temaService.obtenerTodosLosTemas();
        for (TemaInvestigacion t : temas) {
            tableModel.addRow(new Object[]{t.getIdTema(), t.getNombre()});
        }
    }

    private void poblarCamposDesdeTabla() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtNombre.setText(tableModel.getValueAt(selectedRow, 1).toString());
        }
    }
    
    private TemaInvestigacion leerDatosDeFormulario() {
        TemaInvestigacion t = new TemaInvestigacion();
        if (!txtId.getText().isEmpty()) {
            t.setIdTema(Integer.parseInt(txtId.getText()));
        }
        t.setNombre(txtNombre.getText());
        return t;
    }

    private void guardarTema() {
        try {
            TemaInvestigacion t = leerDatosDeFormulario();
            if(t.getNombre().trim().isEmpty()){
                JOptionPane.showMessageDialog(this, "El nombre del tema no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            temaService.crearTema(t);
            JOptionPane.showMessageDialog(this, "Tema guardado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCamposYActualizar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTema() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un tema para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            TemaInvestigacion t = leerDatosDeFormulario();
            if(t.getNombre().trim().isEmpty()){
                JOptionPane.showMessageDialog(this, "El nombre del tema no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            temaService.actualizarTema(t);
            JOptionPane.showMessageDialog(this, "Tema actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCamposYActualizar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarTema() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un tema para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este tema?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                temaService.eliminarTema(Integer.parseInt(txtId.getText()));
                JOptionPane.showMessageDialog(this, "Tema eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCamposYActualizar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCamposYActualizar(){
        txtId.setText("");
        txtNombre.setText("");
        table.clearSelection();
        actualizarTabla();
    }
}