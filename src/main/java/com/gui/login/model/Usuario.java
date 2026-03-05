package com.gui.login.model;

public class Usuario {




    protected String nombre;
    private int edad;
    private String correo;
    private String password;
    protected final String tipo;  // Cambié a protected para poder acceder desde las clases hijas
    protected final String nombreComunidad;

    public Usuario(String nombre, int edad, String correo, String password, String tipo, String nombreComunidad) {
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
        this.password = password;
        this.tipo = tipo;  // Aquí se recibe el valor de tipo
        this.nombreComunidad = nombreComunidad;
    }


    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public int getEdad() {
        return edad;
    }

    public String getPassword() {
        return password;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNombreComunidad(){ return nombreComunidad;}


}
