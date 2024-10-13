package org.example;

public class Productor extends Thread {
    private BufferCompartido buffer;
    private String componente;
    private GaltonBoard galtonBoard;

    public Productor(BufferCompartido buffer, String componente, GaltonBoard galtonBoard) {
        this.buffer = buffer;
        this.componente = componente;
        this.galtonBoard = galtonBoard;
    }

    @Override
    public void run() {
        try {
            while (true) {
                buffer.producir(componente);

                // Lanzamos la bola desde el centro del tablero
                javafx.application.Platform.runLater(() -> {
                    galtonBoard.lanzarBola(); // Llama a la función de lanzar bola
                });

                Thread.sleep(1000); // Simula el tiempo de producción
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
