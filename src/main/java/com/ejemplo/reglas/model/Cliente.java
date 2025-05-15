package com.ejemplo.reglas.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Cliente {
    private String nombre;
    private int puntos;
    private String zona;

    public Cliente() {} // Constructor vacío requerido por Jackson

    @Override
    public String toString() {
        return nombre + " (" + puntos + " pts)";
    }
}
