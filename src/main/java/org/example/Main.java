package org.example;

public class Main {
    public static void main(String[] args) {
        BufferCompartido buffer = new BufferCompartido(10);
        Scheduler scheduler = new Scheduler(3); // Tenemos 3 productores.

        // Creación de productores para diferentes componentes
        Productor productor1 = new Productor(buffer, "Componente A", scheduler, 0);
        Productor productor2 = new Productor(buffer, "Componente B", scheduler, 1);
        Productor productor3 = new Productor(buffer, "Componente C", scheduler, 2);

        // Creación de consumidor (línea de ensamblaje)
        Consumidor ensamblador = new Consumidor(buffer);

        // Inicio de los hilos
        productor1.start();
        productor2.start();
        productor3.start();
        ensamblador.start();
    }
}
