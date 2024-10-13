package org.example;

public class Scheduler {
    private int currentProducer = 0; //Inicializamos el primer productor como 0.
    private int numProducers;

    public Scheduler(int numProducers) {
        this.numProducers = numProducers;
    }

    // Método para verificar si es el turno del productor actual.
    public synchronized boolean esMiTurno(int producerId) {
        while (producerId != currentProducer) {
            try {
                wait(); //Espera si no es su turno.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true; //Es su turno, puede producir.
    }

    // Método para pasar el turno al siguiente productor.
    public synchronized void siguienteTurno() {
        currentProducer = (currentProducer + 1) % numProducers; //Pasar turno al siguiente productor.
        notifyAll(); //Notifica a los demás productores para verificar su turno.
    }
}
