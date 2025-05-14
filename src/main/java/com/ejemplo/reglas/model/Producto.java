package com.ejemplo.reglas.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Producto {
    private String nombre;
    private String rubro;
    private String proveedor;

    public Producto() {
    } // Constructor vacío requerido por Jackson
}
