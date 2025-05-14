package com.ejemplo.reglas.model;

public class Cliente {
    private String nombre;
    private int puntos;

    public Cliente() {} // Constructor vacío requerido por Jackson

    public Cliente(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }

    @Override
    public String toString() {
        return nombre + " (" + puntos + " pts)";
    }
}
