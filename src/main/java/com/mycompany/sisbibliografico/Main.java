package com.mycompany.sisbibliografico;

// ... (todas las importaciones se mantienen igual)
import com.mycompany.sisbibliografico.adapters.ui.ArticuloForm;
import com.mycompany.sisbibliografico.adapters.ui.AutorForm;
import com.mycompany.sisbibliografico.adapters.ui.CongresoForm;
import com.mycompany.sisbibliografico.adapters.ui.HomePanel;
import com.mycompany.sisbibliografico.adapters.ui.InformeTecnicoForm;
import com.mycompany.sisbibliografico.adapters.ui.RevistaForm;
import com.mycompany.sisbibliografico.adapters.ui.TemaInvestigacionForm;
import com.mycompany.sisbibliografico.application.service.ArticuloService;
import com.mycompany.sisbibliografico.application.service.AutorService;
import com.mycompany.sisbibliografico.application.service.CongresoService;
import com.mycompany.sisbibliografico.application.service.InformeTecnicoService;
import com.mycompany.sisbibliografico.application.service.RevistaService;
import com.mycompany.sisbibliografico.application.service.TemaInvestigacionService;
import com.mycompany.sisbibliografico.application.usecase.ArticuloServiceImpl;
import com.mycompany.sisbibliografico.application.usecase.AutorServiceImpl;
import com.mycompany.sisbibliografico.application.usecase.CongresoServiceImpl;
import com.mycompany.sisbibliografico.application.usecase.InformeTecnicoServiceImpl;
import com.mycompany.sisbibliografico.application.usecase.RevistaServiceImpl;
import com.mycompany.sisbibliografico.application.usecase.TemaInvestigacionServiceImpl;
import com.mycompany.sisbibliografico.infrastructure.postgresql.ArticuloRepositoryPostgres;
import com.mycompany.sisbibliografico.infrastructure.postgresql.AutorRepositoryPostgres;
import com.mycompany.sisbibliografico.infrastructure.postgresql.CongresoRepositoryPostgres;
import com.mycompany.sisbibliografico.infrastructure.postgresql.InformeTecnicoRepositoryPostgres;
import com.mycompany.sisbibliografico.infrastructure.postgresql.RevistaRepositoryPostgres;
import com.mycompany.sisbibliografico.infrastructure.postgresql.TemaInvestigacionRepositoryPostgres;
import javax.swing.*;
import java.awt.*;


public class Main extends JFrame {

    private final CardLayout layout = new CardLayout();
    private final JPanel contentPanel = new JPanel(layout);

    public Main() {
        setTitle("SisBibliografico - Sistema Bibliográfico");
        setSize(1200, 800);
        setMinimumSize(new Dimension(1100, 750));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarUI();

        setVisible(true);
    }

    private void inicializarUI() {
        // --- Panel del Menú Lateral ---
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(new Color(90, 100, 180));
        menu.setPreferredSize(new Dimension(220, getHeight()));

        // --- Botones del Menú ---
        // Se elimina el botón "Inicio" del menú
        JButton btnArticulos = crearBotonMenu("Artículos");
        JButton btnAutores = crearBotonMenu("Autores");
        JButton btnRevistas = crearBotonMenu("Revistas");
        JButton btnCongresos = crearBotonMenu("Congresos");
        JButton btnInformes = crearBotonMenu("Informes Técnicos");
        JButton btnTemas = crearBotonMenu("Temas de Investigación");
        JButton btnSalir = crearBotonMenu("Salir");

        // --- Acciones de los Botones ---
        btnArticulos.addActionListener(e -> layout.show(contentPanel, "articulos"));
        btnAutores.addActionListener(e -> layout.show(contentPanel, "autores"));
        btnRevistas.addActionListener(e -> layout.show(contentPanel, "revistas"));
        btnCongresos.addActionListener(e -> layout.show(contentPanel, "congresos"));
        btnInformes.addActionListener(e -> layout.show(contentPanel, "informes"));
        btnTemas.addActionListener(e -> layout.show(contentPanel, "temas"));
        btnSalir.addActionListener(e -> System.exit(0));

        // --- Añadir Botones al Menú ---
        menu.add(Box.createVerticalStrut(30));
        // No se añade el botón "Inicio"
        menu.add(btnArticulos);
        menu.add(Box.createVerticalStrut(10));
        menu.add(btnAutores);
        menu.add(Box.createVerticalStrut(10));
        menu.add(btnRevistas);
        menu.add(Box.createVerticalStrut(10));
        menu.add(btnCongresos);
        menu.add(Box.createVerticalStrut(10));
        menu.add(btnInformes);
        menu.add(Box.createVerticalStrut(10));
        menu.add(btnTemas);
        menu.add(Box.createVerticalGlue());
        menu.add(btnSalir);
        menu.add(Box.createVerticalStrut(20));

        // El resto del método se mantiene igual
        // --- Instanciación de Capas (Inyección de Dependencias) ---
        ArticuloRepositoryPostgres articuloRepo = new ArticuloRepositoryPostgres();
        ArticuloService articuloService = new ArticuloServiceImpl(articuloRepo);

        AutorRepositoryPostgres autorRepo = new AutorRepositoryPostgres();
        AutorService autorService = new AutorServiceImpl(autorRepo);
        
        RevistaRepositoryPostgres revistaRepo = new RevistaRepositoryPostgres();
        RevistaService revistaService = new RevistaServiceImpl(revistaRepo);

        CongresoRepositoryPostgres congresoRepo = new CongresoRepositoryPostgres();
        CongresoService congresoService = new CongresoServiceImpl(congresoRepo);
        
        InformeTecnicoRepositoryPostgres informeRepo = new InformeTecnicoRepositoryPostgres();
        InformeTecnicoService informeService = new InformeTecnicoServiceImpl(informeRepo);

        TemaInvestigacionRepositoryPostgres temaRepo = new TemaInvestigacionRepositoryPostgres();
        TemaInvestigacionService temaService = new TemaInvestigacionServiceImpl(temaRepo);

        // --- Creación de Formularios (Paneles de la UI) ---
        HomePanel homePanel = new HomePanel();
        ArticuloForm articuloForm = new ArticuloForm(articuloService, layout, contentPanel);
        AutorForm autorForm = new AutorForm(autorService, layout, contentPanel);
        RevistaForm revistaForm = new RevistaForm(revistaService, layout, contentPanel);
        CongresoForm congresoForm = new CongresoForm(congresoService, layout, contentPanel);
        InformeTecnicoForm informeForm = new InformeTecnicoForm(informeService, layout, contentPanel);
        TemaInvestigacionForm temaForm = new TemaInvestigacionForm(temaService, layout, contentPanel);

        // --- Añadir Paneles al CardLayout ---
        contentPanel.add(homePanel, "inicio");
        contentPanel.add(articuloForm, "articulos");
        contentPanel.add(autorForm, "autores");
        contentPanel.add(revistaForm, "revistas");
        contentPanel.add(congresoForm, "congresos");
        contentPanel.add(informeForm, "informes");
        contentPanel.add(temaForm, "temas");

        // --- Ensamblaje Final ---
        add(menu, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // La aplicación sigue iniciando en la pantalla de bienvenida
        layout.show(contentPanel, "inicio");
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(200, 45));
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