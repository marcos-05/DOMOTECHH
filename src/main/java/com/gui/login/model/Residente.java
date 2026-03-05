package com.gui.login.model;
import java.util.ArrayList;

public class Residente extends Usuario {

    private ArrayList<Puerta> puertasPrivadas;

    public Residente(String nombre, int edad, String correo, String password, String nombreComunidad) {
        super(nombre, edad, correo, password,"residente",nombreComunidad);
        this.puertasPrivadas = new ArrayList<>();
        Puerta PuertaGarage = new Puerta("Privada");
        this.puertasPrivadas.add(PuertaGarage);
    }
    public void agregarPuertaPrivada(Puerta puerta) {
        puertasPrivadas.add(puerta);
    }

    public void interactuarConPuerta(Puerta puerta, boolean abrir) {
        if (puerta.getTipo().equals("Publica") || puertasPrivadas.contains(puerta)) {
            if (abrir) {
                puerta.abrir(nombre);
            } else {
                puerta.cerrar(nombre);
            }
        } else {
            System.out.println(nombre + " no tiene acceso a esta puerta.");
        }
    }
}


