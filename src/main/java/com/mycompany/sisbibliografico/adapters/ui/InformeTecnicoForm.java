package com.mycompany.sisbibliografico.adapters.ui;

import com.mycompany.sisbibliografico.application.service.InformeTecnicoService;
import com.mycompany.sisbibliografico.domain.entities.InformeTecnico;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InformeTecnicoForm extends JPanel {

    private final InformeTecnicoService informeService;
    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    private JTextField txtCentro, txtMes, txtAnio;
    private JTable tabla;
    private DefaultTableModel tableModel;

    public InformeTecnicoForm(InformeTecnicoService informeService, CardLayout cardLayout, JPanel contentPanel) {
        this.informeService = informeService;
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        inicializarUI();
    }

    private void inicializarUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 255));
        setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel lblTitulo = new JLabel("Gestión de Informes Técnicos");
        lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 24));
        lblTitulo.setForeground(new Color(60, 60, 120));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setOpaque(false);

        txtCentro = new JTextField();
        txtMes = new JTextField();
        txtAnio = new JTextField();

        formPanel.add(new JLabel("Centro de publicación:")); formPanel.add(txtCentro);
        formPanel.add(new JLabel("Mes de publicación:")); formPanel.add(txtMes);
        formPanel.add(new JLabel("Año de publicación:")); formPanel.add(txtAnio);

        add(formPanel, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.setOpaque(false);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnCargar = new JButton("Cargar");
        JButton btnVolver = new JButton("Volver");

        btnGuardar.addActionListener(e -> guardarInforme());
        btnActualizar.addActionListener(e -> actualizarInforme());
        btnEliminar.addActionListener(e -> eliminarInforme());
        btnCargar.addActionListener(e -> cargarInformes());
        btnVolver.addActionListener(e -> cardLayout.show(contentPanel, "inicio"));

        botones.add(btnGuardar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        botones.add(btnCargar);
        botones.add(btnVolver);

        add(botones, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Centro", "Mes", "Año"}, 0);
        tabla = new JTable(tableModel);
        tabla.setRowHeight(22);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                txtCentro.setText(tableModel.getValueAt(fila, 1).toString());
                txtMes.setText(tableModel.getValueAt(fila, 2).toString());
                txtAnio.setText(tableModel.getValueAt(fila, 3).toString());
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Informes Técnicos registrados"));
        scroll.setPreferredSize(new Dimension(600, 200));
        add(scroll, BorderLayout.EAST);
    }

    private void guardarInforme() {
        try {
            InformeTecnico i = new InformeTecnico();
            i.setCentroPublicacion(txtCentro.getText());
            i.setMesPublicacion(txtMes.getText());
            i.setAnioPublicacion(Integer.parseInt(txtAnio.getText()));
            informeService.guardarInforme(i);
            cargarInformes();
            JOptionPane.showMessageDialog(this, "Informe técnico guardado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
        }
    }

    private void actualizarInforme() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            try {
                InformeTecnico i = new InformeTecnico();
                i.setIdInforme((int) tableModel.getValueAt(fila, 0));
                i.setCentroPublicacion(txtCentro.getText());
                i.setMesPublicacion(txtMes.getText());
                i.setAnioPublicacion(Integer.parseInt(txtAnio.getText()));
                informeService.actualizarInforme(i);
                cargarInformes();
                JOptionPane.showMessageDialog(this, "Informe actualizado");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un informe primero");
        }
    }

    private void eliminarInforme() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            int id = (int) tableModel.getValueAt(fila, 0);
            informeService.eliminarInforme(id);
            cargarInformes();
            JOptionPane.showMessageDialog(this, "Informe eliminado");
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un informe para eliminar");
        }
    }

    private void cargarInformes() {
        tableModel.setRowCount(0);
        List<InformeTecnico> informes = informeService.listarInformes();
        for (InformeTecnico i : informes) {
            tableModel.addRow(new Object[]{i.getIdInforme(), i.getCentroPublicacion(), i.getMesPublicacion(), i.getAnioPublicacion()});
        }
    }
} 
 
