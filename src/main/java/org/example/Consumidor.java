package org.example;

public class Consumidor extends Thread {
    private BufferCompartido buffer;

    public Consumidor(BufferCompartido buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String componente = buffer.consumir();
                ensamblar(componente);
                Thread.sleep(1500); //Simula el tiempo de ensamblaje
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void ensamblar(String componente) {
        System.out.println("Ensamblando componente: " + componente);
    }
}
