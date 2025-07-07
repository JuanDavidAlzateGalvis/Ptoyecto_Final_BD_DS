package com.mycompany.sisbibliografico.adapters.ui;

import com.mycompany.sisbibliografico.application.service.ArticuloService;
import com.mycompany.sisbibliografico.domain.entities.Articulo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List; 

public class ArticuloForm extends JPanel {

    private final ArticuloService articuloService;
    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    private JTextField txtTitulo, txtPalabrasClave, txtCorreo, txtUbicacion, txtTipo, txtFecha;
    private JCheckBox chkDisponible;
    private JTable tabla;
    private DefaultTableModel tableModel;

    public ArticuloForm(ArticuloService articuloService, CardLayout cardLayout, JPanel contentPanel) {
        this.articuloService = articuloService;
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        inicializarUI();
    }

    private void inicializarUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 255)); 
        setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel lblTitulo = new JLabel("Gestión de Artículos");
        lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 24));
        lblTitulo.setForeground(new Color(60, 60, 120));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        formPanel.setOpaque(false);

        txtTitulo = new JTextField();
        txtPalabrasClave = new JTextField();
        txtCorreo = new JTextField();
        txtUbicacion = new JTextField();
        txtTipo = new JTextField();
        txtFecha = new JTextField();
        chkDisponible = new JCheckBox("Disponible");

        formPanel.add(new JLabel("Título:"));
        formPanel.add(txtTitulo);
        formPanel.add(new JLabel("Palabras clave:"));
        formPanel.add(txtPalabrasClave);
        formPanel.add(new JLabel("Correo contacto:"));
        formPanel.add(txtCorreo);
        formPanel.add(new JLabel("Ubicación:"));
        formPanel.add(txtUbicacion);
        formPanel.add(new JLabel("Tipo de publicación:"));
        formPanel.add(txtTipo);
        formPanel.add(new JLabel("Fecha publicación (yyyy-MM-dd):"));
        formPanel.add(txtFecha);
        formPanel.add(new JLabel("¿Disponible?:"));
        formPanel.add(chkDisponible);

        add(formPanel, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.setOpaque(false);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnCargar = new JButton("Cargar");
        JButton btnVolver = new JButton("Volver");

        btnGuardar.addActionListener(e -> guardarArticulo());
        btnActualizar.addActionListener(e -> actualizarArticulo());
        btnEliminar.addActionListener(e -> eliminarArticulo());
        btnCargar.addActionListener(e -> cargarArticulos());
        btnVolver.addActionListener(e -> cardLayout.show(contentPanel, "inicio"));

        botones.add(btnGuardar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        botones.add(btnCargar);
        botones.add(btnVolver);

        add(botones, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Título", "Palabras clave", "Correo"}, 0);
        tabla = new JTable(tableModel);
        tabla.setRowHeight(22);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                txtTitulo.setText(tableModel.getValueAt(fila, 1).toString());
                txtPalabrasClave.setText(tableModel.getValueAt(fila, 2).toString());
                txtCorreo.setText(tableModel.getValueAt(fila, 3).toString());
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Artículos registrados"));
        scroll.setPreferredSize(new Dimension(600, 200));

        add(scroll, BorderLayout.EAST);
    }

    private void guardarArticulo() {
        try {
            Articulo a = new Articulo();
            a.setTitulo(txtTitulo.getText());
            a.setPalabrasClave(txtPalabrasClave.getText());
            a.setCorreoContacto(txtCorreo.getText());
            a.setUbicacion(txtUbicacion.getText());
            a.setTipoPublicacion(txtTipo.getText());
            a.setDisponibilidad(chkDisponible.isSelected());
            a.setFechaPublicacion(LocalDate.parse(txtFecha.getText()));

            articuloService.guardarArticulo(a);
            cargarArticulos();
            JOptionPane.showMessageDialog(this, "Artículo guardado correctamente");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarArticulo() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            try {
                Articulo a = new Articulo();
                a.setIdArticulo((int) tableModel.getValueAt(fila, 0));
                a.setTitulo(txtTitulo.getText());
                a.setPalabrasClave(txtPalabrasClave.getText());
                a.setCorreoContacto(txtCorreo.getText());
                a.setUbicacion(txtUbicacion.getText());
                a.setTipoPublicacion(txtTipo.getText());
                a.setDisponibilidad(chkDisponible.isSelected());
                a.setFechaPublicacion(LocalDate.parse(txtFecha.getText()));

                articuloService.actualizarArticulo(a);
                cargarArticulos();
                JOptionPane.showMessageDialog(this, "Artículo actualizado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un artículo primero");
        }
    }

    private void eliminarArticulo() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            int id = (int) tableModel.getValueAt(fila, 0);
            articuloService.eliminarArticulo(id);
            cargarArticulos();
            JOptionPane.showMessageDialog(this, "Artículo eliminado");
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un artículo para eliminar");
        }
    }

    private void cargarArticulos() {
        tableModel.setRowCount(0);
        List<Articulo> articulos = articuloService.listarArticulos();
        for (Articulo a : articulos) {
            tableModel.addRow(new Object[]{
                a.getIdArticulo(),
                a.getTitulo(),
                a.getPalabrasClave(),
                a.getCorreoContacto()
            });
        }
    }
} 

