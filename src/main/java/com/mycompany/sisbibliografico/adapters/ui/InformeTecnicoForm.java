package com.mycompany.sisbibliografico.adapters.ui;

import com.mycompany.sisbibliografico.application.service.InformeTecnicoService;
import com.mycompany.sisbibliografico.domain.entities.InformeTecnico;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InformeTecnicoForm extends JPanel {

    private final InformeTecnicoService informeTecnicoService;
    private final CardLayout layout;
    private final JPanel contentPanel;

    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField txtId = new JTextField();
    private final JTextField txtCentroPublicacion = new JTextField(20);
    private final JComboBox<String> comboMes;
    private final JTextField txtAnioPublicacion = new JTextField(10);

    public InformeTecnicoForm(InformeTecnicoService informeTecnicoService, CardLayout layout, JPanel contentPanel) {
        this.informeTecnicoService = informeTecnicoService;
        this.layout = layout;
        this.contentPanel = contentPanel;

        // Inicializar JComboBox con los meses
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        comboMes = new JComboBox<>(meses);

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblTitulo = new JLabel("Gestión de Informes Técnicos", SwingConstants.CENTER);
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
        
        gbc.gridx = 0; gbc.gridy = 0; panelFormulario.add(new JLabel("Centro de Publicación:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; panelFormulario.add(txtCentroPublicacion, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("Mes de Publicación:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; panelFormulario.add(comboMes, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("Año de Publicación:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; panelFormulario.add(txtAnioPublicacion, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.weighty = 1.0; panelFormulario.add(new JLabel(), gbc);
        
        panelCentral.add(panelFormulario, BorderLayout.WEST);

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setOpaque(false);
        panelTabla.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Informes Registrados", TitledBorder.LEFT, TitledBorder.TOP));
        
        String[] columnNames = {"ID", "Centro de Publicación", "Mes", "Año"};
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

        btnGuardar.addActionListener(e -> guardarInforme());
        btnActualizar.addActionListener(e -> actualizarInforme());
        btnEliminar.addActionListener(e -> eliminarInforme());
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
        List<InformeTecnico> informes = informeTecnicoService.obtenerTodosLosInformesTecnicos();
        for (InformeTecnico i : informes) {
            tableModel.addRow(new Object[]{i.getIdInforme(), i.getCentroPublicacion(), i.getMesPublicacion(), i.getAnioPublicacion()});
        }
    }

    private void poblarCamposDesdeTabla() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int informeId = (int) tableModel.getValueAt(selectedRow, 0);
            InformeTecnico i = informeTecnicoService.obtenerInformeTecnicoPorId(informeId);
            if (i != null) {
                txtId.setText(String.valueOf(i.getIdInforme()));
                txtCentroPublicacion.setText(i.getCentroPublicacion());
                comboMes.setSelectedItem(i.getMesPublicacion());
                txtAnioPublicacion.setText(String.valueOf(i.getAnioPublicacion()));
            }
        }
    }
    
    private InformeTecnico leerDatosDeFormulario() throws NumberFormatException {
        InformeTecnico i = new InformeTecnico();
        if (!txtId.getText().isEmpty()) {
            i.setIdInforme(Integer.parseInt(txtId.getText()));
        }
        i.setCentroPublicacion(txtCentroPublicacion.getText());
        i.setMesPublicacion((String) comboMes.getSelectedItem());
        i.setAnioPublicacion(Integer.parseInt(txtAnioPublicacion.getText()));
        return i;
    }

    private void guardarInforme() {
        try {
            InformeTecnico i = leerDatosDeFormulario();
            informeTecnicoService.crearInformeTecnico(i);
            JOptionPane.showMessageDialog(this, "Informe Técnico guardado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El año de publicación debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarInforme() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un informe para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            InformeTecnico i = leerDatosDeFormulario();
            informeTecnicoService.actualizarInformeTecnico(i);
            JOptionPane.showMessageDialog(this, "Informe Técnico actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El año de publicación debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarInforme() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un informe para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este informe?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                informeTecnicoService.eliminarInformeTecnico(Integer.parseInt(txtId.getText()));
                JOptionPane.showMessageDialog(this, "Informe Técnico eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}