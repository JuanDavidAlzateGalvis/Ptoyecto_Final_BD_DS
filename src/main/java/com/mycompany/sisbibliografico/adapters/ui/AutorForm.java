package com.mycompany.sisbibliografico.adapters.ui;

import com.mycompany.sisbibliografico.application.service.AutorService;
import com.mycompany.sisbibliografico.domain.entities.Autor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AutorForm extends JPanel {

    private final AutorService autorService;
    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    private JTextField txtNombre, txtCorreo, txtCentro, txtPais, txtAfiliacion, txtExperiencia,
            txtGrado, txtColab, txtPremios, txtAsociaciones, txtNivel;

    private JTable tabla;
    private DefaultTableModel tableModel;

    public AutorForm(AutorService autorService, CardLayout cardLayout, JPanel contentPanel) {
        this.autorService = autorService;
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        inicializarUI();
    }

    private void inicializarUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 255));
        setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel lblTitulo = new JLabel("Gestión de Autores");
        lblTitulo.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 24));
        lblTitulo.setForeground(new Color(60, 60, 120));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 4, 10, 10));
        formPanel.setOpaque(false);

        txtNombre = new JTextField();
        txtCorreo = new JTextField();
        txtCentro = new JTextField();
        txtPais = new JTextField();
        txtAfiliacion = new JTextField();
        txtExperiencia = new JTextField();
        txtGrado = new JTextField();
        txtColab = new JTextField();
        txtPremios = new JTextField();
        txtAsociaciones = new JTextField();
        txtNivel = new JTextField();

        formPanel.add(new JLabel("Nombre:")); formPanel.add(txtNombre);
        formPanel.add(new JLabel("Correo:")); formPanel.add(txtCorreo);
        formPanel.add(new JLabel("Centro trabajo:")); formPanel.add(txtCentro);
        formPanel.add(new JLabel("País origen:")); formPanel.add(txtPais);
        formPanel.add(new JLabel("Afiliación universitaria:")); formPanel.add(txtAfiliacion);
        formPanel.add(new JLabel("Experiencia profesional:")); formPanel.add(txtExperiencia);
        formPanel.add(new JLabel("Grado académico:")); formPanel.add(txtGrado);
        formPanel.add(new JLabel("Colaboraciones previas:")); formPanel.add(txtColab);
        formPanel.add(new JLabel("Premios académicos:")); formPanel.add(txtPremios);
        formPanel.add(new JLabel("Asociaciones profesionales:")); formPanel.add(txtAsociaciones);
        formPanel.add(new JLabel("Nivel colaboración internacional:")); formPanel.add(txtNivel);

        add(formPanel, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.setOpaque(false);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnCargar = new JButton("Cargar");
        JButton btnVolver = new JButton("Volver");

        btnGuardar.addActionListener(e -> guardarAutor());
        btnActualizar.addActionListener(e -> actualizarAutor());
        btnEliminar.addActionListener(e -> eliminarAutor());
        btnCargar.addActionListener(e -> cargarAutores());
        btnVolver.addActionListener(e -> cardLayout.show(contentPanel, "inicio"));

        botones.add(btnGuardar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        botones.add(btnCargar);
        botones.add(btnVolver);

        add(botones, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Correo"}, 0);
        tabla = new JTable(tableModel);
        tabla.setRowHeight(22);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                txtNombre.setText(tableModel.getValueAt(fila, 1).toString());
                txtCorreo.setText(tableModel.getValueAt(fila, 2).toString());
                // Puedes seguir completando los demás campos si lo deseas
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Autores registrados"));
        scroll.setPreferredSize(new Dimension(600, 200));
        add(scroll, BorderLayout.EAST);
    }

    private void guardarAutor() {
        Autor a = obtenerDesdeFormulario();
        autorService.guardarAutor(a);
        cargarAutores();
        JOptionPane.showMessageDialog(this, "Autor guardado");
    }

    private void actualizarAutor() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            Autor a = obtenerDesdeFormulario();
            a.setIdAutor((int) tableModel.getValueAt(fila, 0));
            autorService.actualizarAutor(a);
            cargarAutores();
            JOptionPane.showMessageDialog(this, "Autor actualizado");
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un autor primero");
        }
    }

    private void eliminarAutor() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            int id = (int) tableModel.getValueAt(fila, 0);
            autorService.eliminarAutor(id);
            cargarAutores();
            JOptionPane.showMessageDialog(this, "Autor eliminado");
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un autor para eliminar");
        }
    }

    private void cargarAutores() {
        tableModel.setRowCount(0);
        List<Autor> autores = autorService.listarAutores();
        for (Autor a : autores) {
            tableModel.addRow(new Object[]{a.getIdAutor(), a.getNombre(), a.getCorreo()});
        }
    }

    private Autor obtenerDesdeFormulario() {
        Autor a = new Autor();
        a.setNombre(txtNombre.getText());
        a.setCorreo(txtCorreo.getText());
        a.setCentroTrabajo(txtCentro.getText());
        a.setPaisOrigen(txtPais.getText());
        a.setAfiliacionUniversitaria(txtAfiliacion.getText());
        a.setExperienciaProfesional(txtExperiencia.getText());
        a.setGradoAcademico(txtGrado.getText());
        a.setColaboracionesPrevias(txtColab.getText());
        a.setPremiosAcademicos(txtPremios.getText());
        a.setAsociacionesProfesionales(txtAsociaciones.getText());
        a.setNivelColaboracionInternacional(txtNivel.getText());
        return a;
    }
}


