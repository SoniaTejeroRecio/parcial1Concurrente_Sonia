package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class BufferCompartido {
    private Queue<String> buffer = new LinkedList<>();
    private int capacidad;

    public BufferCompartido(int capacidad) {
        this.capacidad = capacidad;
    }

    public synchronized void producir(String componente) throws InterruptedException {
        while (buffer.size() == capacidad) {
            wait(); // Espera si el búfer está lleno
        }
        buffer.add(componente);
        System.out.println("Producido: " + componente);
        notifyAll(); // Notifica a los consumidores que pueden consumir
    }

    public synchronized String consumir() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait(); // Espera si no hay componentes
        }
        String componente = buffer.poll();
        System.out.println("Consumido: " + componente);
        notifyAll(); // Notifica a los productores que pueden producir
        return componente;
    }
}
