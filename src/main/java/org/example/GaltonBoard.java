package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import java.util.Random;

public class GaltonBoard extends Application {

    private static final int NUM_CONTENEDORES = 10; //Número de contenedores (bins)
    private static final int ANCHO_CONTENEDOR = 50; //Ancho de cada contenedor
    private static final int ALTO_TABLERO = 400; //Altura desde la que caen las bolas

    @Override
    public void start(Stage stage) {
        //Crear los contenedores inferiores
        HBox contenedores = new HBox();
        for (int i = 0; i < NUM_CONTENEDORES; i++) {
            Rectangle contenedor = new Rectangle(ANCHO_CONTENEDOR, 50, Color.LIGHTGRAY);
            contenedores.getChildren().add(contenedor);
        }

        //LO que sale por pantalla
        VBox root = new VBox();
        root.getChildren().addAll(contenedores);

        Scene scene = new Scene(root, NUM_CONTENEDORES * ANCHO_CONTENEDOR, ALTO_TABLERO + 100);
        stage.setScene(scene);
        stage.setTitle("Simulación Tablero de Galton");
        stage.show();

        //Simulaion del lanzamiento de bolas
        lanzarBolasConcurrentemente(root);
    }

    // Método para lanzar las bolas desde la parte superior
    private void lanzarBolasConcurrentemente(VBox root) {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                Circle bola = new Circle(10, Color.BLUE);
                javafx.application.Platform.runLater(() -> root.getChildren().add(0, bola));

                int destino = random.nextInt(NUM_CONTENEDORES) * ANCHO_CONTENEDOR;

                TranslateTransition animacion = new TranslateTransition(Duration.seconds(2), bola);
                animacion.setToX(destino);
                animacion.setToY(ALTO_TABLERO);
                javafx.application.Platform.runLater(animacion::play);

                try {
                    Thread.sleep(1000); // Simula el tiempo entre bolas
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
