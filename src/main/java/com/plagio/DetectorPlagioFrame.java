package com.plagio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.regex.*;

public class DetectorPlagioFrame extends JFrame {

    public DetectorPlagioFrame() {
        // Configuración de la ventana secundaria
        setTitle("Detector de Plagio");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana

        // Crear un panel con diseño de cuadrícula
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10)); // 5 filas, 2 columnas

        // Campos de texto para los nombres y textos de los alumnos
        JTextField[] nombreCampos = new JTextField[4];
        JTextArea[] textoCampos = new JTextArea[4];
        JScrollPane[] scrollTextos = new JScrollPane[4];

        // Añadir los componentes de texto al panel
        for (int i = 0; i < 4; i++) {
            panel.add(new JLabel("Alumno " + (i + 1) + " Nombre:"));
            nombreCampos[i] = new JTextField();
            panel.add(nombreCampos[i]);

            panel.add(new JLabel("Texto " + (i + 1) + ":"));
            textoCampos[i] = new JTextArea(5, 20);
            scrollTextos[i] = new JScrollPane(textoCampos[i]);
            panel.add(scrollTextos[i]);
        }

        // Botón para comparar los textos
        JButton compararButton = new JButton("Comparar Textos");
        panel.add(compararButton);

        // Área de resultados
        JTextArea resultadoArea = new JTextArea(10, 30);
        resultadoArea.setEditable(false);
        JScrollPane resultadoScroll = new JScrollPane(resultadoArea);
        panel.add(resultadoScroll);

        // Agregar el panel al frame
        add(panel);

        // Acción para el botón de comparación
        compararButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compararTextos(nombreCampos, textoCampos, resultadoArea);
            }
        });
    }

    // Método para comparar los textos y mostrar los resultados
    private void compararTextos(JTextField[] nombreCampos, JTextArea[] textoCampos, JTextArea resultadoArea) {
        String[] nombres = new String[4];
        String[] textos = new String[4];

        // Obtener los nombres y los textos
        for (int i = 0; i < 4; i++) {
            nombres[i] = nombreCampos[i].getText();
            textos[i] = textoCampos[i].getText();
        }

        // Lógica de comparación (aquí iría tu lógica de comparación)
        StringBuilder resultados = new StringBuilder();
        for (int i = 0; i < textos.length; i++) {
            for (int j = i + 1; j < textos.length; j++) {
                double similitud = calcularSimilitud(textos[i], textos[j]);
                String plagio = (similitud >= 30.0) ? "Sí" : "No";

                resultados.append(String.format("Comparando los textos de %s y %s\n", nombres[i], nombres[j]));
                resultados.append(String.format("Similitud: %.2f%%\n", similitud));
                resultados.append(String.format("Plagio: %s\n", plagio));
                resultados.append("\n");
            }
        }

        resultadoArea.setText(resultados.toString());
    }

    // Método para calcular la similitud entre los textos usando la similitud de coseno
    private double calcularSimilitud(String texto1, String texto2) {
        // Preprocesar los textos: convertir a minúsculas y eliminar puntuaciones
        texto1 = preprocesarTexto(texto1);
        texto2 = preprocesarTexto(texto2);

        // Crear los vectores de palabras para cada texto
        Set<String> vocabulario = new HashSet<>(Arrays.asList(texto1.split(" ")));
        vocabulario.addAll(Arrays.asList(texto2.split(" ")));

        // Contar las frecuencias de las palabras en cada texto
        Map<String, Integer> vector1 = contarPalabras(vocabulario, texto1);
        Map<String, Integer> vector2 = contarPalabras(vocabulario, texto2);

        // Calcular la similitud de coseno
        return calcularSimilitudCoseno(vector1, vector2);
    }

    // Preprocesar el texto: convertir a minúsculas y eliminar puntuaciones
    private String preprocesarTexto(String texto) {
        texto = texto.toLowerCase();
        texto = texto.replaceAll("[^a-záéíóúüñ0-9\\s]", "");  // Eliminar puntuación
        return texto;
    }

    // Contar la frecuencia de las palabras en el texto
    private Map<String, Integer> contarPalabras(Set<String> vocabulario, String texto) {
        Map<String, Integer> vector = new HashMap<>();
        String[] palabras = texto.split(" ");
        for (String palabra : palabras) {
            if (vocabulario.contains(palabra)) {
                vector.put(palabra, vector.getOrDefault(palabra, 0) + 1);
            }
        }
        return vector;
    }

    // Calcular la similitud de coseno entre dos vectores de palabras
    private double calcularSimilitudCoseno(Map<String, Integer> vector1, Map<String, Integer> vector2) {
        Set<String> palabrasComunes = new HashSet<>(vector1.keySet());
        palabrasComunes.retainAll(vector2.keySet());

        double productoPunto = 0.0;
        double modulo1 = 0.0;
        double modulo2 = 0.0;

        for (String palabra : palabrasComunes) {
            productoPunto += vector1.get(palabra) * vector2.get(palabra);
        }

        for (String palabra : vector1.keySet()) {
            modulo1 += Math.pow(vector1.get(palabra), 2);
        }

        for (String palabra : vector2.keySet()) {
            modulo2 += Math.pow(vector2.get(palabra), 2);
        }

        return (productoPunto) / (Math.sqrt(modulo1) * Math.sqrt(modulo2)) * 100;  // Convertir a porcentaje
    }
}

