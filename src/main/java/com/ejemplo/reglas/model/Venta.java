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
    private Cliente cliente;
    private double descuentoAplicado;
    private int diaSemana; // Del 0 al 6, lunes a domingo
    private int costoEnvio;
    private boolean participaPorPremio;

    public Venta() {
    } // Constructor vacío requerido por Jackson

    public void aplicarDescuento(double descuento) {
        importe -= importe * descuento;
        descuentoAplicado = descuento;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "importe=" + importe +
                ", cantidad=" + cantidad +
                ", producto=" + producto.getNombre() +
                ", cliente=" + cliente.getNombre() +
                ", descuentoAplicado=" + descuentoAplicado +
                ", diaSemana=" + diaSemana +
                ", costoEnvio=" + costoEnvio +
                ", participaPorPremio=" + participaPorPremio +
                '}';
    }
}
