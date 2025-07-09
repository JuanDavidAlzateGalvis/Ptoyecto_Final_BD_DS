package com.mycompany.sisbibliografico.infrastructure.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/Sis_bibliografia";
    private static final String USER = "postgres";
    private static final String PASSWORD = "rey1234";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión exitosa a la base de datos PostgreSQL");
            } catch (SQLException e) {
                System.err.println("Error de conexión a la base de datos: " + e.getMessage());
            }
        }
        return connection;
    }
}
