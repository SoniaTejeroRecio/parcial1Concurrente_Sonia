package org.example.Componentes;

//Clase para respresentar uno de los componentes específicos producidos en la fábrica
public class Clavo extends Componente {
    public Clavo() {
        super("Clavo");
    }

    @Override
    public void ensamblar() {
        System.out.println("Ensamblando un clavo");
    }
}
