package com.plagio;

import java.util.HashSet;
import java.util.Set;

public class PlagioDetector {

    // Método para calcular la similitud entre dos textos usando el índice de Jaccard
    public double calcularSimilitud(String texto1, String texto2) {
        // Obtener las palabras de cada texto
        Set<String> palabrasTexto1 = obtenerPalabras(texto1);
        Set<String> palabrasTexto2 = obtenerPalabras(texto2);

        // Calcular la intersección y la unión de las palabras
        Set<String> interseccion = new HashSet<>(palabrasTexto1);
        interseccion.retainAll(palabrasTexto2);  // Intersección

        Set<String> union = new HashSet<>(palabrasTexto1);
        union.addAll(palabrasTexto2);  // Unión

        // Calcular la similitud (índice de Jaccard)
        return (double) interseccion.size() / union.size() * 100;
    }

    // Método para obtener las palabras de un texto, ignorando mayúsculas y puntuación
    private Set<String> obtenerPalabras(String texto) {
        // Convertir el texto a minúsculas y dividir en palabras por los espacios y puntuación
        String[] palabras = texto.toLowerCase().split("\\W+");

        // Usar un Set para almacenar las palabras únicas
        Set<String> palabrasSet = new HashSet<>();
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                palabrasSet.add(palabra);  // Añadir solo palabras no vacías
            }
        }

        return palabrasSet;
    }

    // Método de prueba
    public static void main(String[] args) {
        PlagioDetector detector = new PlagioDetector();

        // Ejemplo de dos textos para comparar
        String texto1 = "El sistema de detección de plagio compara textos y detecta similitudes";
        String texto2 = "La comparación de textos permite detectar similitudes y plagio en el contenido";

        // Calcular la similitud
        double similitud = detector.calcularSimilitud(texto1, texto2);
        System.out.println("Similitud: " + similitud + "%");

        // Resultado esperado: Similaridad entre 50-80% dependiendo de las palabras en común
    }
}