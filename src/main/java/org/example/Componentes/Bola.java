package org.example.Componentes;

//Clase para respresentar uno de los componentes específicos producidos en la fábrica
public class Bola extends Componente {
    public Bola() {
        super("Bola");
    }

    @Override
    public void ensamblar() {
        System.out.println("Ensamblando una bola");
    }
}
