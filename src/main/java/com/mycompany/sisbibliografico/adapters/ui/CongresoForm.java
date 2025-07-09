package com.mycompany.sisbibliografico.adapters.ui;

import com.mycompany.sisbibliografico.application.service.CongresoService;
import com.mycompany.sisbibliografico.domain.entities.Congreso;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CongresoForm extends JPanel {

    private final CongresoService congresoService;
    private final CardLayout layout;
    private final JPanel contentPanel;

    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField txtId = new JTextField();
    private final JTextField txtNombre = new JTextField(20);
    private final JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Nacional", "Internacional"});
    private final JTextField txtEdicion = new JTextField(15);
    private final JTextField txtCiudad = new JTextField(15);
    private final JTextField txtPais = new JTextField(15);
    private final JTextField txtFechaInicio = new JTextField(10);
    private final JTextField txtFechaFin = new JTextField(10);
    private final JTextField txtFrecuencia = new JTextField(15);
    private final JTextField txtAnioPrimeraEdicion = new JTextField(10);

    public CongresoForm(CongresoService congresoService, CardLayout layout, JPanel contentPanel) {
        this.congresoService = congresoService;
        this.layout = layout;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblTitulo = new JLabel("Gestión de Congresos", SwingConstants.CENTER);
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
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; panelFormulario.add(txtNombre, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("Edición:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; panelFormulario.add(txtEdicion, gbc);
        
        gbc.gridx = 2; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; panelFormulario.add(comboTipo, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("Ciudad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; panelFormulario.add(txtCiudad, gbc);
        
        gbc.gridx = 2; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("País:"), gbc);
        gbc.gridx = 3; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; panelFormulario.add(txtPais, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("Frecuencia:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; panelFormulario.add(txtFrecuencia, gbc);
        
        gbc.gridx = 2; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("Año 1ra Edición:"), gbc);
        gbc.gridx = 3; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; panelFormulario.add(txtAnioPrimeraEdicion, gbc);
        
        txtFechaInicio.setToolTipText("Formato: AAAA-MM-DD");
        txtFechaFin.setToolTipText("Formato: AAAA-MM-DD");

        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("Fecha Inicio:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.fill = GridBagConstraints.HORIZONTAL; panelFormulario.add(txtFechaInicio, gbc);

        gbc.gridx = 2; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("Fecha Fin:"), gbc);
        gbc.gridx = 3; gbc.gridy = 4; gbc.fill = GridBagConstraints.HORIZONTAL; panelFormulario.add(txtFechaFin, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.weighty = 1.0; panelFormulario.add(new JLabel(), gbc);
        
        panelCentral.add(panelFormulario, BorderLayout.WEST);

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setOpaque(false);
        panelTabla.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Congresos Registrados", TitledBorder.LEFT, TitledBorder.TOP));
        
        String[] columnNames = {"ID", "Nombre", "Tipo", "País", "Fecha Inicio"};
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
        JButton btnConsultar = new JButton("Consultar");
        JButton btnVolverInicio = new JButton("Volver a Inicio");
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnMostrar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnVolverInicio);
        add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarCongreso());
        btnActualizar.addActionListener(e -> actualizarCongreso());
        btnEliminar.addActionListener(e -> eliminarCongreso());
        btnMostrar.addActionListener(e -> actualizarTabla());
        btnConsultar.addActionListener(e -> {
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
            CongresoQueryDialog dialog = new CongresoQueryDialog(owner, congresoService);
            dialog.setVisible(true); });
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
        List<Congreso> congresos = congresoService.obtenerTodosLosCongresos();
        for (Congreso c : congresos) {
            tableModel.addRow(new Object[]{c.getIdCongreso(), c.getNombre(), c.getTipo(), c.getPais(), c.getFechaInicio()});
        }
    }

    private void poblarCamposDesdeTabla() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int congresoId = (int) tableModel.getValueAt(selectedRow, 0);
            Congreso c = congresoService.obtenerCongresoPorId(congresoId);
            if (c != null) {
                txtId.setText(String.valueOf(c.getIdCongreso()));
                txtNombre.setText(c.getNombre());
                comboTipo.setSelectedItem(c.getTipo());
                txtEdicion.setText(c.getEdicion());
                txtCiudad.setText(c.getCiudad());
                txtPais.setText(c.getPais());
                txtFrecuencia.setText(c.getFrecuencia());
                txtAnioPrimeraEdicion.setText(String.valueOf(c.getAnioPrimeraEdicion()));
                txtFechaInicio.setText(c.getFechaInicio().toString());
                txtFechaFin.setText(c.getFechaFin().toString());
            }
        }
    }
    
    private Congreso leerDatosDeFormulario() throws NumberFormatException, DateTimeParseException {
        Congreso c = new Congreso();
        if (!txtId.getText().isEmpty()) {
            c.setIdCongreso(Integer.parseInt(txtId.getText()));
        }
        c.setNombre(txtNombre.getText());
        c.setTipo((String) comboTipo.getSelectedItem());
        c.setEdicion(txtEdicion.getText());
        c.setCiudad(txtCiudad.getText());
        c.setPais(txtPais.getText());
        c.setFrecuencia(txtFrecuencia.getText());
        c.setAnioPrimeraEdicion(Integer.parseInt(txtAnioPrimeraEdicion.getText()));
        c.setFechaInicio(LocalDate.parse(txtFechaInicio.getText()));
        c.setFechaFin(LocalDate.parse(txtFechaFin.getText()));
        return c;
    }

    private void guardarCongreso() {
        try {
            Congreso c = leerDatosDeFormulario();
            congresoService.crearCongreso(c);
            JOptionPane.showMessageDialog(this, "Congreso guardado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarTabla();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Error en el formato de fecha. Use AAAA-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El año debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarCongreso() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un congreso para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Congreso c = leerDatosDeFormulario();
            congresoService.actualizarCongreso(c);
            JOptionPane.showMessageDialog(this, "Congreso actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarTabla();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Error en el formato de fecha. Use AAAA-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El año debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCongreso() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un congreso para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este congreso?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                congresoService.eliminarCongreso(Integer.parseInt(txtId.getText()));
                JOptionPane.showMessageDialog(this, "Congreso eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}