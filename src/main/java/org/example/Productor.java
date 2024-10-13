package org.example;

public class Productor extends Thread {
    private BufferCompartido buffer;
    private String componente;

    public Productor(BufferCompartido buffer, String componente) {
        this.buffer = buffer;
        this.componente = componente;
    }

    @Override
    public void run() {
        try {
            while (true) {
                buffer.producir(componente);
                Thread.sleep(1000); //Simula el tiempo de producción
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
