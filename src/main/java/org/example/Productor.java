package org.example;

public class Productor extends Thread {
    private BufferCompartido buffer;
    private String componente;
    private Scheduler scheduler;
    private int producerId;

    public Productor(BufferCompartido buffer, String componente, Scheduler scheduler, int producerId) {
        this.buffer = buffer;
        this.componente = componente;
        this.scheduler = scheduler;
        this.producerId = producerId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                //Verificar si es su turno de producir.
                if (scheduler.esMiTurno(producerId)) {
                    buffer.producir(componente); //Produce el componente.
                    Thread.sleep(1000); //Simula el tiempo de producción.
                    scheduler.siguienteTurno(); //Pasa el turno al siguiente productor.
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
