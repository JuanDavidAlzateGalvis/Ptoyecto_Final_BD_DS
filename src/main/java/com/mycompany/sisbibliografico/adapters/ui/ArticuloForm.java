package com.mycompany.sisbibliografico.adapters.ui;

import com.mycompany.sisbibliografico.application.service.ArticuloService;
import com.mycompany.sisbibliografico.domain.entities.Articulo;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ArticuloForm extends JPanel {

    private final ArticuloService articuloService;
    private final CardLayout layout;
    private final JPanel contentPanel;

    // Componentes de la UI
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField txtId = new JTextField();
    private final JTextField txtTitulo = new JTextField(20);
    private final JTextArea areaPalabrasClave = new JTextArea(2, 20);
    private final JTextField txtCorreo = new JTextField(20);
    private final JCheckBox chkDisponibilidad = new JCheckBox("Disponible");
    private final JTextField txtUbicacion = new JTextField(15);
    private final JTextField txtTipoPublicacion = new JTextField(15);
    private final JTextField txtFechaPublicacion = new JTextField(10);

    public ArticuloForm(ArticuloService articuloService, CardLayout layout, JPanel contentPanel) {
        this.articuloService = articuloService;
        this.layout = layout;
        this.contentPanel = contentPanel;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblTitulo = new JLabel("Gestión de Artículos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(225, 220, 240));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(6, 5, 6, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelFormulario.add(txtTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        panelFormulario.add(new JLabel("Palabras Clave:"), gbc);
        gbc.anchor = GridBagConstraints.EAST;
        areaPalabrasClave.setLineWrap(true);
        areaPalabrasClave.setWrapStyleWord(true);
        JScrollPane scrollPalabras = new JScrollPane(areaPalabrasClave);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.5;
        panelFormulario.add(scrollPalabras, gbc);

        gbc.gridwidth = 1;
        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        panelFormulario.add(new JLabel("Correo Contacto:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(txtCorreo, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        panelFormulario.add(new JLabel("Fecha Publicación:"), gbc);
        txtFechaPublicacion.setToolTipText("Formato: AAAA-MM-DD");
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(txtFechaPublicacion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        panelFormulario.add(new JLabel("Ubicación:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(txtUbicacion, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        panelFormulario.add(new JLabel("Tipo Publicación:"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(txtTipoPublicacion, gbc);

        chkDisponibilidad.setOpaque(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(chkDisponibilidad, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weighty = 1.0;
        panelFormulario.add(new JLabel(), gbc);

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setOpaque(false);
        panelTabla.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Artículos Registrados", TitledBorder.LEFT, TitledBorder.TOP));
        String[] columnNames = {"ID", "Título", "Tipo", "Fecha", "Palabras Clave", "Correo", "Disponibilidad", "Ubicación"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        panelTabla.add(new JScrollPane(table), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelFormulario, panelTabla);
        splitPane.setResizeWeight(0.6);
        splitPane.setOpaque(false);
        add(splitPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotones.setOpaque(false);
        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnMostrar = new JButton("Mostrar");
        JButton btnConsultas = new JButton("Consultas");
        JButton btnVolverInicio = new JButton("Volver a Inicio");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnMostrar);
        panelBotones.add(btnConsultas);
        panelBotones.add(btnVolverInicio);
        add(panelBotones, BorderLayout.SOUTH);

        // --- Listeners ---
        btnGuardar.addActionListener(e -> guardarArticulo());
        btnActualizar.addActionListener(e -> actualizarArticulo());
        btnEliminar.addActionListener(e -> eliminarArticulo());
        btnMostrar.addActionListener(e -> actualizarTabla());
        btnVolverInicio.addActionListener(e -> layout.show(contentPanel, "inicio"));

        // --- ACCIÓN MODIFICADA PARA EL BOTÓN DE CONSULTAS ---
        btnConsultas.addActionListener(e -> {
            // Obtiene la ventana principal (JFrame) que contiene este panel
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
            // Crea y muestra el diálogo de consulta
            ArticuloQueryDialog queryDialog = new ArticuloQueryDialog(owner, articuloService);
            queryDialog.setVisible(true);
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                poblarCamposDesdeTabla();
            }
        });

        actualizarTabla();
    }

    // El resto de los métodos (actualizarTabla, poblarCampos, etc.) no cambian
    private void actualizarTabla() {
        tableModel.setRowCount(0);
        List<Articulo> articulos = articuloService.obtenerTodosLosArticulos();
        for (Articulo a : articulos) {
            Object fecha = a.getFechaPublicacion() != null ? a.getFechaPublicacion() : "N/D";

            tableModel.addRow(new Object[]{
                a.getIdArticulo(),
                a.getTitulo(),
                a.getTipoPublicacion(),
                fecha,
                a.getPalabrasClave(),
                a.getCorreoContacto(),
                a.isDisponibilidad(),
                a.getUbicacion()
            });
        }
    }

    private void poblarCamposDesdeTabla() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int articuloId = (int) tableModel.getValueAt(selectedRow, 0);
            Articulo a = articuloService.obtenerArticuloPorId(articuloId);
            if (a != null) {
                txtId.setText(String.valueOf(a.getIdArticulo()));
                txtTitulo.setText(a.getTitulo());
                areaPalabrasClave.setText(a.getPalabrasClave());
                txtCorreo.setText(a.getCorreoContacto());
                chkDisponibilidad.setSelected(a.isDisponibilidad());
                txtUbicacion.setText(a.getUbicacion());
                txtTipoPublicacion.setText(a.getTipoPublicacion());
                if (a.getFechaPublicacion() != null) {
                    txtFechaPublicacion.setText(a.getFechaPublicacion().toString());
                } else {
                    txtFechaPublicacion.setText("");
                }
            }
        }
    }

    private Articulo leerDatosDeFormulario() throws DateTimeParseException {
        Articulo a = new Articulo();
        if (!txtId.getText().isEmpty()) {
            a.setIdArticulo(Integer.parseInt(txtId.getText()));
        }
        a.setTitulo(txtTitulo.getText());
        a.setPalabrasClave(areaPalabrasClave.getText());
        a.setCorreoContacto(txtCorreo.getText());
        a.setDisponibilidad(chkDisponibilidad.isSelected());
        a.setUbicacion(txtUbicacion.getText());
        a.setTipoPublicacion(txtTipoPublicacion.getText());
        if (!txtFechaPublicacion.getText().trim().isEmpty()) {
            a.setFechaPublicacion(LocalDate.parse(txtFechaPublicacion.getText()));
        } else {
            a.setFechaPublicacion(null);
        }
        return a;
    }

    private void guardarArticulo() {
        try {
            Articulo a = leerDatosDeFormulario();
            articuloService.crearArticulo(a);
            JOptionPane.showMessageDialog(this, "Artículo guardado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarTabla();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Error en el formato de fecha. Use AAAA-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarArticulo() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un artículo para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Articulo a = leerDatosDeFormulario();
            articuloService.actualizarArticulo(a);
            JOptionPane.showMessageDialog(this, "Artículo actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarTabla();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Error en el formato de fecha. Use AAAA-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarArticulo() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un artículo para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este artículo?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                articuloService.eliminarArticulo(Integer.parseInt(txtId.getText()));
                JOptionPane.showMessageDialog(this, "Artículo eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
