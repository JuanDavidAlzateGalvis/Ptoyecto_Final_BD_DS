package com.mycompany.sisbibliografico.adapters.ui;

import com.mycompany.sisbibliografico.application.service.AutorService;
import com.mycompany.sisbibliografico.domain.entities.Autor;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AutorForm extends JPanel {

    private final AutorService autorService;
    private final CardLayout layout;
    private final JPanel contentPanel;

    // Componentes de la UI
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField txtId = new JTextField();
    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtCorreo = new JTextField(20);
    private final JTextField txtCentroTrabajo = new JTextField(20);
    private final JTextField txtPaisOrigen = new JTextField(20);
    private final JTextField txtAfiliacion = new JTextField(20);
    private final JTextField txtGrado = new JTextField(20);
    private final JTextArea areaExperiencia = new JTextArea(2, 20); // Tamaño reducido
    private final JTextArea areaColaboraciones = new JTextArea(2, 20); // Tamaño reducido
    private final JTextArea areaPremios = new JTextArea(2, 20); // Tamaño reducido
    private final JTextArea areaAsociaciones = new JTextArea(2, 20); // Tamaño reducido
    private final JTextArea areaNivelColaboracion = new JTextArea(2, 20); // Tamaño reducido

    public AutorForm(AutorService autorService, CardLayout layout, JPanel contentPanel) {
        this.autorService = autorService;
        this.layout = layout;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblTitulo = new JLabel("Gestión de Autores", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);

        // --- Panel de Formulario (Izquierda) ---
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(225, 220, 240));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 15);
        
        // Fila 0
        gbc.gridx = 0; gbc.gridy = 0; panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 0.5; panelFormulario.add(txtNombre, gbc);
        gbc.gridx = 2; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 3; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 0.5; panelFormulario.add(txtCorreo, gbc);
        
        // Fila 1
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("Centro de Trabajo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; panelFormulario.add(txtCentroTrabajo, gbc);
        gbc.gridx = 2; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("País de Origen:"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; panelFormulario.add(txtPaisOrigen, gbc);
        
        // Fila 2
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("Afiliación Universitaria:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; panelFormulario.add(txtAfiliacion, gbc);
        gbc.gridx = 2; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; panelFormulario.add(new JLabel("Grado Académico:"), gbc);
        gbc.gridx = 3; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; panelFormulario.add(txtGrado, gbc);
        
        // Fila 3 - Text Areas
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.gridx = 0; gbc.gridy = 3; panelFormulario.add(new JLabel("Experiencia:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0; panelFormulario.add(new JScrollPane(areaExperiencia), gbc);
        gbc.gridx = 2; gbc.gridy = 3; panelFormulario.add(new JLabel("Colaboraciones:"), gbc);
        gbc.gridx = 3; gbc.gridy = 3; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0; panelFormulario.add(new JScrollPane(areaColaboraciones), gbc);
        
        // Fila 4 - Text Areas
        gbc.gridx = 0; gbc.gridy = 4; panelFormulario.add(new JLabel("Premios:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0; panelFormulario.add(new JScrollPane(areaPremios), gbc);
        gbc.gridx = 2; gbc.gridy = 4; panelFormulario.add(new JLabel("Asociaciones:"), gbc);
        gbc.gridx = 3; gbc.gridy = 4; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0; panelFormulario.add(new JScrollPane(areaAsociaciones), gbc);
        
        // Fila 5 - Text Area
        gbc.gridx = 0; gbc.gridy = 5; panelFormulario.add(new JLabel("Colab. Intl.:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0; panelFormulario.add(new JScrollPane(areaNivelColaboracion), gbc);
        gbc.gridwidth = 1;
        
        // --- Panel de Tabla (Derecha) ---
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setOpaque(false);
        panelTabla.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Autores Registrados", TitledBorder.LEFT, TitledBorder.TOP));
        String[] columnNames = {"ID", "Nombre", "Correo", "País"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        panelTabla.add(new JScrollPane(table), BorderLayout.CENTER);

        // --- JSplitPane para balancear el tamaño ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelFormulario, panelTabla);
        splitPane.setResizeWeight(0.6); // Da el 60% del espacio inicial al formulario
        splitPane.setOpaque(false);
        add(splitPane, BorderLayout.CENTER);

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

        btnGuardar.addActionListener(e -> guardarAutor());
        btnActualizar.addActionListener(e -> actualizarAutor());
        btnEliminar.addActionListener(e -> eliminarAutor());
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
        List<Autor> autores = autorService.obtenerTodosLosAutores();
        for (Autor a : autores) {
            tableModel.addRow(new Object[]{a.getIdAutor(), a.getNombre(), a.getCorreo(), a.getPaisOrigen()});
        }
    }

    private void poblarCamposDesdeTabla() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int autorId = (int) tableModel.getValueAt(selectedRow, 0);
            Autor a = autorService.obtenerAutorPorId(autorId);
            if (a != null) {
                txtId.setText(String.valueOf(a.getIdAutor()));
                txtNombre.setText(a.getNombre());
                txtCorreo.setText(a.getCorreo());
                txtCentroTrabajo.setText(a.getCentroTrabajo());
                txtPaisOrigen.setText(a.getPaisOrigen());
                txtAfiliacion.setText(a.getAfiliacionUniversitaria());
                areaExperiencia.setText(a.getExperienciaProfesional());
                txtGrado.setText(a.getGradoAcademico());
                areaColaboraciones.setText(a.getColaboracionesPrevias());
                areaPremios.setText(a.getPremiosAcademicos());
                areaAsociaciones.setText(a.getAsociacionesProfesionales());
                areaNivelColaboracion.setText(a.getNivelColaboracionInternacional());
            }
        }
    }
    
    private Autor leerDatosDeFormulario() {
        Autor a = new Autor();
        if (!txtId.getText().isEmpty()) {
            a.setIdAutor(Integer.parseInt(txtId.getText()));
        }
        a.setNombre(txtNombre.getText());
        a.setCorreo(txtCorreo.getText());
        a.setCentroTrabajo(txtCentroTrabajo.getText());
        a.setPaisOrigen(txtPaisOrigen.getText());
        a.setAfiliacionUniversitaria(txtAfiliacion.getText());
        a.setExperienciaProfesional(areaExperiencia.getText());
        a.setGradoAcademico(txtGrado.getText());
        a.setColaboracionesPrevias(areaColaboraciones.getText());
        a.setPremiosAcademicos(areaPremios.getText());
        a.setAsociacionesProfesionales(areaAsociaciones.getText());
        a.setNivelColaboracionInternacional(areaNivelColaboracion.getText());
        return a;
    }

    private void guardarAutor() {
        try {
            Autor a = leerDatosDeFormulario();
            autorService.crearAutor(a);
            JOptionPane.showMessageDialog(this, "Autor guardado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarAutor() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un autor para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Autor a = leerDatosDeFormulario();
            autorService.actualizarAutor(a);
            JOptionPane.showMessageDialog(this, "Autor actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarAutor() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un autor para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este autor?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                autorService.eliminarAutor(Integer.parseInt(txtId.getText()));
                JOptionPane.showMessageDialog(this, "Autor eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}