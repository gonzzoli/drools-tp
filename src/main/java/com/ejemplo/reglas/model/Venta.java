package com.ejemplo.reglas.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Venta {
    private double importe;
    private int cantidad;
    private Producto producto;

    public Venta() {
    } // Constructor vacío requerido por Jackson

    @Override
    public String toString() {
        return "Venta{" +
                "importe=" + importe +
                ", cantidad=" + cantidad +
                ", producto=" + producto.getNombre() +
                '}';
    }
}
