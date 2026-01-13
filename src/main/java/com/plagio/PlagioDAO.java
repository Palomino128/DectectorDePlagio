package com.plagio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlagioDAO {

    // Método para insertar registros
    public void insertarTexto(String nombre, String texto) {
        String query = "INSERT INTO Alumnos (nombre, texto) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, texto);

            pstmt.executeUpdate();
            System.out.println("Texto insertado correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar registros
    public void actualizarTexto(int id, String nombre, String apellido, String texto, boolean esPlagio) {
        String query = "UPDATE Alumnos SET nombre = ?, apellido = ?, texto = ?, es_plagio = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setString(3, texto);
            pstmt.setBoolean(4, esPlagio);
            pstmt.setInt(5, id);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Registro actualizado correctamente.");
            } else {
                System.out.println("No se encontró un registro con el ID proporcionado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar registros
    public void eliminarTexto(int id) {
        String query = "DELETE FROM Alumnos WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Registro eliminado correctamente.");
            } else {
                System.out.println("No se encontró un registro con el ID proporcionado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para insertar registros con validación
    public void insertarTextoConValidacion(String nombre, String apellido, String texto, boolean esPlagio) {
        if (nombre == null || nombre.trim().isEmpty() || texto == null || texto.trim().isEmpty()) {
            System.out.println("Error: El nombre y el texto no pueden estar vacíos.");
            return;
        }

        String query = "INSERT INTO Alumnos (nombre, apellido, texto, es_plagio) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setString(3, texto);
            pstmt.setBoolean(4, esPlagio);

            pstmt.executeUpdate();
            System.out.println("Texto insertado correctamente con validación.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener textos de la base de datos
    public void obtenerTextos() {
        String query = "SELECT id, nombre, apellido, texto, es_plagio FROM Alumnos";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String texto = rs.getString("texto");
                Boolean esPlagio = rs.getBoolean("es_plagio");

                System.out.println("ID: " + id);
                System.out.println("Nombre: " + nombre);
                System.out.println("Apellido: " + (apellido != null ? apellido : "N/A"));
                System.out.println("Texto: " + texto);
                System.out.println("Es plagio: " + (esPlagio ? "Sí" : "No"));
                System.out.println("----------------------------------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


