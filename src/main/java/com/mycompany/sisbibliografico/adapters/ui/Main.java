package com.mycompany.sisbibliografico.adapters.ui;

import com.mycompany.sisbibliografico.application.service.ArticuloService;
import com.mycompany.sisbibliografico.application.service.AutorService;
import com.mycompany.sisbibliografico.application.service.InformeTecnicoService;
import com.mycompany.sisbibliografico.application.usecase.ArticuloServiceImpl;
import com.mycompany.sisbibliografico.application.usecase.AutorServiceImpl;
import com.mycompany.sisbibliografico.application.usecase.InformeTecnicoServiceImpl;
import com.mycompany.sisbibliografico.domain.repository.ArticuloRepository;
import com.mycompany.sisbibliografico.infrastructure.postgresql.ArticuloRepositoryPostgres;
import com.mycompany.sisbibliografico.infrastructure.postgresql.AutorRepositoryPostgres;
import com.mycompany.sisbibliografico.infrastructure.postgresql.InformeTecnicoRepositoryPostgres;

import javax.swing.*;
import java.awt.*; 

public class Main extends JFrame {

    private final CardLayout layout = new CardLayout();
    private final JPanel contentPanel = new JPanel(layout);

    public Main() {
        setTitle("SisBibliografico - Sistema Bibliográfico");
        setSize(1000, 600);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarUI();

        setVisible(true);
    }

    private void inicializarUI() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(new Color(90, 100, 180));
        menu.setPreferredSize(new Dimension(200, getHeight()));

        JButton btnArticulos = crearBotonMenu("Artículos");
        JButton btnAutores = crearBotonMenu("Autores");
        JButton btnInformes = crearBotonMenu("Informes");
        JButton btnSalir = crearBotonMenu("Salir");

        btnArticulos.addActionListener(e -> layout.show(contentPanel, "articulos"));
        btnAutores.addActionListener(e -> layout.show(contentPanel, "autores"));
        btnInformes.addActionListener(e -> layout.show(contentPanel, "informes"));
        btnSalir.addActionListener(e -> System.exit(0));

        menu.add(Box.createVerticalStrut(30));
        menu.add(btnArticulos);
        menu.add(Box.createVerticalStrut(10));
        menu.add(btnAutores);
        menu.add(Box.createVerticalStrut(10));
        menu.add(btnInformes);
        menu.add(Box.createVerticalGlue());
        menu.add(btnSalir);
        
        ArticuloRepository articuloRepo = new ArticuloRepositoryPostgres();
        AutorRepositoryPostgres autorRepo = new AutorRepositoryPostgres();
        InformeTecnicoRepositoryPostgres informeRepo = new InformeTecnicoRepositoryPostgres();
        ArticuloService articuloService = new ArticuloServiceImpl(articuloRepo);
        AutorService autorService = new AutorServiceImpl(autorRepo);
        InformeTecnicoService informeService = new InformeTecnicoServiceImpl(informeRepo);

        ArticuloForm articuloForm = new ArticuloForm(articuloService, layout, contentPanel);
        AutorForm autorForm = new AutorForm(autorService, layout, contentPanel);
        InformeTecnicoForm informeForm = new InformeTecnicoForm(informeService, layout, contentPanel);
        contentPanel.add(articuloForm, "articulos");
        contentPanel.add(autorForm, "autores");
        contentPanel.add(informeForm, "informes");

        add(menu, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        layout.show(contentPanel, "articulos");
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(50, 60, 150));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
} 

