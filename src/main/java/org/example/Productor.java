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
                galtonBoard.lanzarBola();  // Llamada para lanzar bola en el tablero
                Thread.sleep(1000); // Simula el tiempo de producción
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
