package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
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

    private static final int NUM_CONTENEDORES = 10;
    private static final int ANCHO_CONTENEDOR = 50;
    private static final int ALTO_TABLERO = 400;
    private static final int RADIO_BOLA = 5;
    private static final int NUM_CLAOS = 8;  // Número de filas de clavos
    private VBox root;
    private int[] contenedoresContador = new int[NUM_CONTENEDORES]; // Contador para bolas en cada contenedor
    private Label[] contenedoresLabels = new Label[NUM_CONTENEDORES]; // Etiquetas de texto para mostrar conteo
    private Pane tablero; // Panel para añadir las bolas
    private Circle[][] clavos; // Matriz para almacenar los clavos
    private Random random = new Random();

    @Override
    public void start(Stage stage) {
        root = new VBox();
        tablero = new Pane();  // Aseguramos que `tablero` esté inicializado aquí

        clavos = new Circle[NUM_CLAOS][]; // Crear matriz para almacenar clavos

        // Crear los contenedores inferiores y las etiquetas de conteo
        HBox contenedores = new HBox();
        for (int i = 0; i < NUM_CONTENEDORES; i++) {
            Rectangle contenedor = new Rectangle(ANCHO_CONTENEDOR, 50, Color.LIGHTGRAY);
            contenedores.getChildren().add(contenedor);

            // Crear etiquetas para mostrar el conteo de bolas
            Label label = new Label("0");
            contenedoresLabels[i] = label;  // Guardar la referencia para actualizarla después
            contenedores.getChildren().add(label); // Añadir la etiqueta debajo del contenedor
        }

        agregarClavos(tablero);  // Añadir clavos al tablero

        root.getChildren().addAll(tablero, contenedores);

        Scene scene = new Scene(root, NUM_CONTENEDORES * ANCHO_CONTENEDOR, ALTO_TABLERO + 100);
        stage.setScene(scene);
        stage.setTitle("Simulación Tablero de Galton");
        stage.show();
    }

    // Método para agregar clavos al tablero
    private void agregarClavos(Pane tablero) {
        double spacingX = ANCHO_CONTENEDOR;
        double spacingY = 50; // Distancia vertical entre filas de clavos

        // Crear filas de clavos en forma triangular
        for (int fila = 0; fila < NUM_CLAOS; fila++) {
            clavos[fila] = new Circle[fila + 1];
            for (int i = 0; i <= fila; i++) {
                Circle clavo = new Circle(5, Color.BLACK);
                double xPos = (NUM_CLAOS - fila) * (spacingX / 2) + i * spacingX;
                double yPos = fila * spacingY;
                clavo.setCenterX(xPos);
                clavo.setCenterY(yPos);
                tablero.getChildren().add(clavo);
                clavos[fila][i] = clavo; // Guardar el clavo en la matriz
            }
        }
    }

    // Método para lanzar bolas desde la parte superior con rebotes en los clavos
    public void lanzarBola() {
        Platform.runLater(() -> {
            if (tablero != null) {  // Asegurarnos de que `tablero` no sea null
                Circle bola = new Circle(RADIO_BOLA, Color.BLUE);
                bola.setCenterX(ANCHO_CONTENEDOR * NUM_CONTENEDORES / 2);
                bola.setCenterY(RADIO_BOLA);
                tablero.getChildren().add(bola);  // Asegurarnos de que este acceso se haga en el hilo de JavaFX

                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), event -> {
                    // Simular rebote por los clavos
                    for (int fila = 0; fila < NUM_CLAOS; fila++) {
                        double xPos = bola.getCenterX();
                        for (Circle clavo : clavos[fila]) {
                            // Verificar si la bola está cerca del clavo
                            if (Math.abs(clavo.getCenterX() - xPos) < ANCHO_CONTENEDOR / 2) {
                                // Simular el rebote hacia la izquierda o derecha
                                if (random.nextBoolean()) {
                                    bola.setCenterX(bola.getCenterX() - ANCHO_CONTENEDOR / 2); // Mover hacia la izquierda
                                } else {
                                    bola.setCenterX(bola.getCenterX() + ANCHO_CONTENEDOR / 2); // Mover hacia la derecha
                                }
                                break;
                            }
                        }
                        bola.setCenterY(bola.getCenterY() + 50); // Mover la bola hacia abajo
                    }

                    // Cuando llega a la parte inferior, determinar el contenedor
                    if (bola.getCenterY() >= ALTO_TABLERO - RADIO_BOLA) {
                        int contenedorDestino = (int) (bola.getCenterX() / ANCHO_CONTENEDOR);
                        if (contenedorDestino >= 0 && contenedorDestino < NUM_CONTENEDORES) {
                            contenedoresContador[contenedorDestino]++;
                            contenedoresLabels[contenedorDestino].setText(String.valueOf(contenedoresContador[contenedorDestino]));
                        }
                        tablero.getChildren().remove(bola); // Eliminar la bola del tablero
                    }
                }));

                timeline.setCycleCount(NUM_CLAOS + 1); // Número de rebotes más el desplazamiento final
                timeline.play();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
