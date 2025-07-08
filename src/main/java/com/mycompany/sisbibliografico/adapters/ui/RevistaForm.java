package com.mycompany.sisbibliografico.adapters.ui;

import com.mycompany.sisbibliografico.application.service.RevistaService;
import com.mycompany.sisbibliografico.domain.entities.Revista;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RevistaForm extends JPanel {

    private final RevistaService revistaService;
    private final CardLayout layout;
    private final JPanel contentPanel;

    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField txtId = new JTextField();
    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtEditor = new JTextField(20);
    private final JTextField txtAnioFundacion = new JTextField(10);
    private final JTextField txtFrecuencia = new JTextField(15);
    private final JTextArea areaTemasAbordados = new JTextArea(2, 20); // Tamaño reducido

    public RevistaForm(RevistaService revistaService, CardLayout layout, JPanel contentPanel) {
        this.revistaService = revistaService;
        this.layout = layout;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblTitulo = new JLabel("Gestión de Revistas", SwingConstants.CENTER);
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
        gbc.insets = new Insets(6, 5, 6, 5);
        
        gbc.gridx = 0; gbc.gridy = 0; panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; panelFormulario.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("Editor:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; panelFormulario.add(txtEditor, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("Año Fundación:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; panelFormulario.add(txtAnioFundacion, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("Frecuencia:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; panelFormulario.add(txtFrecuencia, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.NORTHEAST; panelFormulario.add(new JLabel("Temas Abordados:"), gbc);
        areaTemasAbordados.setLineWrap(true);
        areaTemasAbordados.setWrapStyleWord(true);
        JScrollPane scrollTemas = new JScrollPane(areaTemasAbordados);
        gbc.gridx = 1; gbc.gridy = 4; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0; panelFormulario.add(scrollTemas, gbc);

        panelCentral.add(panelFormulario, BorderLayout.WEST);

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setOpaque(false);
        panelTabla.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Revistas Registradas", TitledBorder.LEFT, TitledBorder.TOP));
        String[] columnNames = {"ID", "Nombre", "Editor", "Año Fundación"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        panelCentral.add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Panel de Botones ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        // ... (resto de la lógica de botones y listeners se mantiene igual)
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

        btnGuardar.addActionListener(e -> guardarRevista());
        btnActualizar.addActionListener(e -> actualizarRevista());
        btnEliminar.addActionListener(e -> eliminarRevista());
        btnMostrar.addActionListener(e -> actualizarTabla());
        btnVolverInicio.addActionListener(e -> layout.show(contentPanel, "inicio"));
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                poblarCamposDesdeTabla();
            }
        });
        
        actualizarTabla();
    }
    
    // El resto de los métodos (actualizarTabla, poblarCampos, etc.) no necesitan cambios
    private void actualizarTabla() {
        tableModel.setRowCount(0);
        List<Revista> revistas = revistaService.obtenerTodasLasRevistas();
        for (Revista r : revistas) {
            tableModel.addRow(new Object[]{r.getIdRevista(), r.getNombre(), r.getEditor(), r.getAnioFundacion()});
        }
    }

    private void poblarCamposDesdeTabla() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int revistaId = (int) tableModel.getValueAt(selectedRow, 0);
            Revista r = revistaService.obtenerRevistaPorId(revistaId);
            if (r != null) {
                txtId.setText(String.valueOf(r.getIdRevista()));
                txtNombre.setText(r.getNombre());
                txtEditor.setText(r.getEditor());
                txtAnioFundacion.setText(String.valueOf(r.getAnioFundacion()));
                txtFrecuencia.setText(r.getFrecuencia());
                areaTemasAbordados.setText(r.getTemasAbordados());
            }
        }
    }
    
    private Revista leerDatosDeFormulario() throws NumberFormatException {
        Revista r = new Revista();
        if (!txtId.getText().isEmpty()) {
            r.setIdRevista(Integer.parseInt(txtId.getText()));
        }
        r.setNombre(txtNombre.getText());
        r.setEditor(txtEditor.getText());
        r.setAnioFundacion(Integer.parseInt(txtAnioFundacion.getText()));
        r.setFrecuencia(txtFrecuencia.getText());
        r.setTemasAbordados(areaTemasAbordados.getText());
        return r;
    }

    private void guardarRevista() {
        try {
            Revista r = leerDatosDeFormulario();
            revistaService.crearRevista(r);
            JOptionPane.showMessageDialog(this, "Revista guardada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El año de fundación debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarRevista() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una revista para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Revista r = leerDatosDeFormulario();
            revistaService.actualizarRevista(r);
            JOptionPane.showMessageDialog(this, "Revista actualizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El año de fundación debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarRevista() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una revista para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar esta revista?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                revistaService.eliminarRevista(Integer.parseInt(txtId.getText()));
                JOptionPane.showMessageDialog(this, "Revista eliminada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}