/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sisbibliografico;

import com.mycompany.sisbibliografico.infrastructure.postgresql.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author juana
 */
public class SisBibliografico {

    public static void main(String[] args) {
        try {
            Connection conn = ConexionBD.getConnection();

            if (conn != null) {
                System.out.println("Conexión exitosa a PostgreSQL");

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM articulo");

                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id_articulo") + " | Título: " + rs.getString("titulo"));
                }

                conn.close();
            } else {
                System.out.println("Conexión fallida");
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
