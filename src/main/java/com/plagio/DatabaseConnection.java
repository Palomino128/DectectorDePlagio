package com.plagio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection 
{

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=detector_plagio;trustServerCertificate=true";
    private static final String USER = "TestUser"; // Usuario nuevo
    private static final String PASSWORD = "Password123"; // Contraseña que configuraste

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}


