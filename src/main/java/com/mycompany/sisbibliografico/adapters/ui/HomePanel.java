package com.mycompany.sisbibliografico.adapters.ui;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    public HomePanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblBienvenido = new JLabel("Bienvenido al Sistema Bibliográfico");
        lblBienvenido.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblBienvenido.setForeground(new Color(60, 60, 60));
        
        JLabel lblInstrucciones = new JLabel("Utilice el menú de la izquierda para navegar entre las diferentes gestiones.");
        lblInstrucciones.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblInstrucciones.setForeground(new Color(100, 100, 100));

        add(lblBienvenido, gbc);
        add(lblInstrucciones, gbc);
    }
}
