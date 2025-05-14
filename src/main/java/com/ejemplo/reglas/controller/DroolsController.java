package com.ejemplo.reglas.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.reglas.dto.ResultadoEjecucionDTO;
import com.ejemplo.reglas.model.Cliente;
import com.ejemplo.reglas.model.Venta;
import com.ejemplo.reglas.service.MotorDeReglasService;

@RestController
@RequestMapping("/api/reglas")
public class DroolsController {

    private final MotorDeReglasService reglasService;

    public DroolsController(MotorDeReglasService reglasService) {
        this.reglasService = reglasService;
    }
    @PostMapping("/caso-test")
    public String ejecutarTest() throws Exception {
        reglasService.ejecutarTest();
        return "✅ Caso Test (ver consola)";
    }

    @PostMapping("/clientes/insertar")
    public String insertarCliente(@RequestBody Cliente cliente) throws Exception {
        reglasService.insertarHechoYMostrarAgenda(cliente);
        return "🧠 Hecho insertado y reglas preparadas (ver consola)";
    }
    @PostMapping("/ventas/insertar")
    public String insertarVenta(@RequestBody Venta venta) throws Exception {
        reglasService.ejecutarReglasVenta(venta);
        return "🧠 Hecho insertado y reglas preparadas (ver consola)";
    }

    @PostMapping("/testrest")
    public ResultadoEjecucionDTO ejecutarReglasTest() throws Exception {
        return reglasService.ejecutarTestComoJson();
    }
    
}
