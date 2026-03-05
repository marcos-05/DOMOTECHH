package com.gui.login.model;
import java.util.ArrayList;
import java.util.List;

public class Puerta {
    private String tipo; // Pública o Privada
    private boolean abierta;
    private ArrayList<String> registros;

    public Puerta(String tipo) {
        this.tipo = tipo;
        this.abierta = false; // Por defecto cerrada
        this.registros = new ArrayList<>();
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void abrir(String usuario) {
        if (!abierta) {
            abierta = true;
            //registros.add(new Registro(usuario));
            System.out.println("Puerta " + tipo + " abierta por " + usuario);
        } else {
            System.out.println("La puerta ya está abierta.");
        }
    }

    public void cerrar(String usuario) {
        if (abierta) {
            abierta = false;
            //registros.add(new Registro(usuario));
            System.out.println("Puerta " + tipo + " cerrada por " + usuario);
        } else {
            System.out.println("La puerta ya está cerrada.");
        }
    }

}









/*
public class Puerta {

    private int numeroPuerta;
    private String tipoDePuerta;
    private ArrayList<Residente> listaDeResidentes = new ArrayList<Residente>();

    public Puerta(int numeroPuerta, String tipoDePuerta){
        this.numeroPuerta=numeroPuerta;
        this.tipoDePuerta=tipoDePuerta;
    }

    public int getNumeroPuerta(){
        return numeroPuerta;
    }
    public String getTipoDePuerta(){
        return tipoDePuerta;
    }

    public ArrayList<Registro> listaRegistros=new ArrayList<Registro>();

    public ArrayList<Registro> guardarRegistroSalidasPuertaPrinciapl(){
        Registro registroSalida = new Registro();

        return null;
    }

    public ArrayList<String> guardarRegistrosEntradasPuertaPrincipal(){
        int registro=0;
        registro++;
        String entrada= "Entrada de la puerta principal "+registro;
        ArrayList<String> registroEntradasTotal = new ArrayList<String>();
        registroEntradasTotal.add(entrada);
        return registroEntradasTotal;
    }


    public ArrayList<String> guardarRegistrosEntradasPuertaSecundaria(int numeroPuerta){
        int registro=0;
        registro++;
        String entrada= "Entrada puerta secundaria "+numeroPuerta+" "+registro;
        ArrayList<String> registroEntradasTotalPuertaSecundaria = new ArrayList<String>();
        registroEntradasTotalPuertaSecundaria.add(entrada);
        return registroEntradasTotalPuertaSecundaria;
    }

    public ArrayList<String> guardarRegistrosSalidasPuertaSecundaria(int numeroPuerta){
        int registro=0;
        registro++;
        String entrada= "Salida puerta secundaria "+registro;
        ArrayList<String> registroSalidasTotalPuertaSecundaria = new ArrayList<String>();
        registroSalidasTotalPuertaSecundaria.add(entrada);
        return registroSalidasTotalPuertaSecundaria;
    }

} */