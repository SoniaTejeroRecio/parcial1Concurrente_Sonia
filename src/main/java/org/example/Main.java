package org.example;

public class Main {
    public static void main(String[] args) {
        BufferCompartido buffer = new BufferCompartido(10);
        GaltonBoard galtonBoard = new GaltonBoard();

        // Lanzar la simulación de JavaFX en un hilo separado
        new Thread(() -> GaltonBoard.launch(GaltonBoard.class)).start();

        // Esperar a que la UI se inicie antes de lanzar los hilos productores
        try {
            Thread.sleep(2000); // Espera para asegurarse de que la UI de JavaFX se haya cargado
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Creación de productores
        Productor productor1 = new Productor(buffer, "Componente A", galtonBoard);
        Productor productor2 = new Productor(buffer, "Componente B", galtonBoard);
        Productor productor3 = new Productor(buffer, "Componente C", galtonBoard);

        // Creación de consumidor
        Consumidor ensamblador = new Consumidor(buffer);

        // Iniciar los hilos
        productor1.start();
        productor2.start();
        productor3.start();
        ensamblador.start();
    }
}
