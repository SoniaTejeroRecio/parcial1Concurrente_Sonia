package org.example;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;
import javafx.scene.text.Text;

public class GaltonBoard extends Application {

    private static final int NUM_CONTENEDORES = 10;
    private static final int ANCHO_CONTENEDOR = 50;
    private static final int ALTO_TABLERO = 400;
    private static final int RADIO_BOLA = 10;
    private static final int NUM_CLAVOS = 7;

    private VBox root;
    private Pane tablero;
    private int[] contadorBolas = new int[NUM_CONTENEDORES];
    private HBox contenedores;
    private Text[] labels;

    @Override
    public void start(Stage stage) {
        root = new VBox();

        // Crear los contenedores inferiores
        contenedores = new HBox();
        labels = new Text[NUM_CONTENEDORES];
        for (int i = 0; i < NUM_CONTENEDORES; i++) {
            Rectangle contenedor = new Rectangle(ANCHO_CONTENEDOR, 50, Color.LIGHTGRAY);
            labels[i] = new Text("0");
            VBox contenedorBox = new VBox(contenedor, labels[i]);
            contenedores.getChildren().add(contenedorBox);
        }

        tablero = new Pane();
        root.getChildren().addAll(tablero, contenedores);

        Scene scene = new Scene(root, NUM_CONTENEDORES * ANCHO_CONTENEDOR, ALTO_TABLERO + 100);
        stage.setScene(scene);
        stage.setTitle("Simulación Tablero de Galton");
        stage.show();

        agregarClavos(tablero);
    }

    // Método para agregar los clavos al tablero
    private void agregarClavos(Pane tablero) {
        double spacingX = ANCHO_CONTENEDOR;
        double spacingY = 50;

        // Crear filas de clavos en forma triangular
        for (int fila = 0; fila < NUM_CLAVOS; fila++) {
            for (int i = 0; i <= fila; i++) {
                Circle clavo = new Circle(5, Color.BLACK);
                double xPos = (NUM_CLAVOS - fila) * (spacingX / 2) + i * spacingX;
                double yPos = fila * spacingY + 50;
                clavo.setCenterX(xPos);
                clavo.setCenterY(yPos);
                tablero.getChildren().add(clavo);
            }
        }
    }

    // Método para lanzar una bola desde el centro
    public void lanzarBola() {
        Circle bola = new Circle(RADIO_BOLA, Color.BLUE);
        bola.setCenterX(ANCHO_CONTENEDOR * NUM_CONTENEDORES / 2);
        bola.setCenterY(RADIO_BOLA);

        tablero.getChildren().add(bola);

        // Crear la animación de la bola que baja
        TranslateTransition animacion = new TranslateTransition(Duration.seconds(3), bola);
        Random random = new Random();

        // Calcular los rebotes en cada fila de clavos
        double xPos = ANCHO_CONTENEDOR * NUM_CONTENEDORES / 2;
        for (int fila = 0; fila < NUM_CLAVOS; fila++) {
            // Simular rebote a izquierda o derecha
            xPos += (random.nextBoolean() ? ANCHO_CONTENEDOR / 2 : -ANCHO_CONTENEDOR / 2);
        }

        int contenedorFinal = (int) (xPos / ANCHO_CONTENEDOR);
        if (contenedorFinal < 0) contenedorFinal = 0;
        if (contenedorFinal >= NUM_CONTENEDORES) contenedorFinal = NUM_CONTENEDORES - 1;

        animacion.setToY(ALTO_TABLERO - RADIO_BOLA);
        animacion.setToX(xPos);
        int finalContenedorFinal = contenedorFinal;
        animacion.setOnFinished(e -> {
            final int contenedorAsignado = finalContenedorFinal;  // Crear una variable final para usar dentro de la lambda
            actualizarContador(contenedorAsignado);
        });

        animacion.play();
    }

    // Método para actualizar el contador de bolas en los contenedores
    private void actualizarContador(int contenedorFinal) {
        contadorBolas[contenedorFinal]++;
        labels[contenedorFinal].setText(String.valueOf(contadorBolas[contenedorFinal]));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
