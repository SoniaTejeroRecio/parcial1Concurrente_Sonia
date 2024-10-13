package org.example.Componentes;

//Clase abstracta que representa los componentes genéricos que la fábrica produce
public abstract class Componente {
    protected String nombre;

    public Componente(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract void ensamblar(); //Método abstracto para ensamblar cada componente
}
