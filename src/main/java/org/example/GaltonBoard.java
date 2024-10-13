package org.example;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class GaltonBoard extends Application {

    private static final int NUM_CONTENEDORES = 10; // Número de contenedores
    private static final int NUM_CLAVOS = 7; // Número de filas de clavos
    private static final int ANCHO_CONTENEDOR = 50; // Ancho de cada contenedor
    private static final int ALTO_TABLERO = 400; // Altura del tablero
    private static final int RADIO_BOLA = 10; // Radio de la bola
    private static final double REBOTE_IZQUIERDA = -ANCHO_CONTENEDOR / 4;
    private static final double REBOTE_DERECHA = ANCHO_CONTENEDOR / 4;

    private VBox root;
    private Pane tablero;
    private Label[] contenedoresLabels = new Label[NUM_CONTENEDORES];
    private int[] contadorBolas = new int[NUM_CONTENEDORES]; // Contadores de bolas

    @Override
    public void start(Stage stage) {
        root = new VBox();

        // Crear los contenedores inferiores
        HBox contenedores = new HBox();
        for (int i = 0; i < NUM_CONTENEDORES; i++) {
            VBox contenedorBox = new VBox();
            Rectangle contenedor = new Rectangle(ANCHO_CONTENEDOR, 50, Color.LIGHTGRAY);
            contenedoresLabels[i] = new Label("0");
            contenedorBox.getChildren().addAll(contenedor, contenedoresLabels[i]);
            contenedores.getChildren().add(contenedorBox);
        }

        tablero = new Pane();
        root.getChildren().addAll(tablero, contenedores);

        Scene scene = new Scene(root, NUM_CONTENEDORES * ANCHO_CONTENEDOR, ALTO_TABLERO + 100);
        stage.setScene(scene);
        stage.setTitle("Simulación Tablero de Galton");
        stage.show();

        // Agregar clavos
        agregarClavos(tablero);

        // Lanzar múltiples bolas desde el clavo más alto
        lanzarBolas();
    }

    // Método para agregar clavos al tablero
    private void agregarClavos(Pane tablero) {
        double spacingX = ANCHO_CONTENEDOR;
        double spacingY = 50; // Distancia vertical entre filas de clavos

        // Crear filas de clavos en forma triangular
        for (int fila = 0; fila < NUM_CLAVOS; fila++) {
            for (int i = 0; i <= fila; i++) {
                Circle clavo = new Circle(5, Color.BLACK);
                double xPos = (NUM_CLAVOS - fila) * (spacingX / 2) + i * spacingX;
                double yPos = (fila + 1) * spacingY;
                clavo.setCenterX(xPos);
                clavo.setCenterY(yPos);
                tablero.getChildren().add(clavo);
            }
        }
    }

    // Método para lanzar múltiples bolas
    public void lanzarBolas() {
        for (int i = 0; i < 50; i++) { // Lanzar 50 bolas
            int delay = i * 500; // Intervalo de tiempo entre lanzamientos
            new Thread(() -> {
                try {
                    Thread.sleep(delay);
                    javafx.application.Platform.runLater(this::lanzarBola); // Lanza la bola en el hilo de la interfaz
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    // Método para lanzar una bola desde el clavo más alto y hacer que rebote en los clavos
    public void lanzarBola() {
        Circle bola = new Circle(RADIO_BOLA, Color.BLUE);
        bola.setCenterX((NUM_CONTENEDORES * ANCHO_CONTENEDOR) / 2); // Lanzar desde el centro superior
        bola.setCenterY(50); // Posición inicial justo encima del primer clavo

        tablero.getChildren().add(bola);

        // Simular la caída de la bola con rebotes
        simularCaida(bola);
    }

    // Simulación de la caída de la bola rebotando en los clavos
    private void simularCaida(Circle bola) {
        Random random = new Random();
        double xPos = bola.getCenterX();
        double yPos = bola.getCenterY();

        for (int fila = 0; fila < NUM_CLAVOS; fila++) {
            double desviacion = random.nextBoolean() ? REBOTE_DERECHA : REBOTE_IZQUIERDA; // Rebote a izquierda o derecha
            double nuevoX = xPos + desviacion;
            double nuevoY = yPos + 50; // Bajamos a la siguiente fila de clavos

            int finalFila = fila; // Variable final para uso en lambda
            TranslateTransition transition = new TranslateTransition(Duration.millis(500), bola);
            transition.setToX(nuevoX);
            transition.setToY(nuevoY);

            transition.setOnFinished(event -> {
                if (finalFila == NUM_CLAVOS - 1) {
                    // Cuando la bola alcanza el final, asignarla al contenedor correspondiente
                    int contenedorIndex = (int) Math.min(Math.max(nuevoX / ANCHO_CONTENEDOR, 0), NUM_CONTENEDORES - 1);
                    incrementarContenedor(contenedorIndex);
                }
            });

            transition.play();
            xPos = nuevoX;
            yPos = nuevoY;
        }
    }

    // Incrementar el contador del contenedor correspondiente
    private void incrementarContenedor(int contenedorIndex) {
        contadorBolas[contenedorIndex]++;
        contenedoresLabels[contenedorIndex].setText(String.valueOf(contadorBolas[contenedorIndex]));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
