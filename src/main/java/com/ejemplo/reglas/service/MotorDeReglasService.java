package com.ejemplo.reglas.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Agenda;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Service;

import com.ejemplo.reglas.dto.ResultadoEjecucionDTO;
import com.ejemplo.reglas.model.Cliente;
import com.ejemplo.reglas.model.Venta;

@Service
public class MotorDeReglasService {

    private KieSession crearSesion(String drl) throws Exception {
        KieHelper helper = new KieHelper();
        helper.addContent(drl, ResourceType.DRL);
        return helper.build().newKieSession();
    }

    public void ejecutarReglasVenta(Venta venta) throws Exception {
        System.out.println("\n=== INSERTAR VENTA Y MOSTRAR AGENDA ===");

        String drl = Files.readString(Path.of("src/main/resources/rules/reglas_ventas.drl"));
        KieSession kieSession = crearSesion(drl);
        ReglaTracker tracker = new ReglaTracker();
        kieSession.addEventListener(tracker);

        System.out.println("Hecho recibido: " + venta);
        kieSession.insert(venta);

        Agenda agenda = kieSession.getAgenda();
        agenda.getAgendaGroup("beneficios-por-importe-final").setFocus();
        agenda.getAgendaGroup("aplicacion-descuentos").setFocus();
        System.out.println("\n📋 Reglas activadas en agenda:");
        tracker.getReglasPendientes().forEach(m -> System.out.println("  🔔 " + m.getRule().getName()));

        kieSession.fireAllRules();
        System.out.println("Venta resultante: " + venta);
        kieSession.dispose();
    }

    public void ejecutarTest() throws Exception {
        System.out.println("\n🧪 === TEST 0: Reglas básicas ===");

        // 📄 1. Cargar reglas desde archivo simple
        String drl = Files.readString(Path.of("src/main/resources/rules/0_test_reglas.drl"));

        // 🧠 2. Crear sesión y registrar listener para ver agenda y ejecuciones
        KieSession kieSession = crearSesion(drl);
        ReglaTracker tracker = new ReglaTracker();
        kieSession.addEventListener(tracker);

        // 👥 3. Insertar hechos: clientes de prueba
        Cliente[] clientes = {
                new Cliente("Juan", 120, "Zona 2") // VIP + cualquier cliente
        };

        System.out.println("\n📥 Insertando hechos en memoria de trabajo:");
        for (Cliente c : clientes) {
            kieSession.insert(c);
            System.out.println("  [+] " + c);
        }

        // 🚀 5. Ejecutar todas las reglas
        System.out.println("\n⚙️ Ejecutando reglas...");
        int total = kieSession.fireAllRules();
        System.out.println("✅ Total de reglas ejecutadas: " + total);

        // 🧹 6. Finalizar sesión
        kieSession.dispose();
        tracker.limpiar();

        System.out.println("\n🏁 Fin del test de reglas educativas.\n");
    }

    public void insertarHechoYMostrarAgenda(Cliente cliente) throws Exception {
        System.out.println("\n=== INSERTAR CLIENTE Y MOSTRAR AGENDA ===");

        String drl = Files.readString(Path.of("src/main/resources/rules/0_test_reglas.drl"));
        KieSession kieSession = crearSesion(drl);
        ReglaTracker tracker = new ReglaTracker();
        kieSession.addEventListener(tracker);

        System.out.println("Hecho recibido: " + cliente);
        kieSession.insert(cliente);

        System.out.println("\n📋 Reglas activadas en agenda:");
        tracker.getReglasPendientes().forEach(m -> System.out.println("  🔔 " + m.getRule().getName()));

        kieSession.fireAllRules();
        kieSession.dispose();
    }

    public ResultadoEjecucionDTO ejecutarTestComoJson() throws Exception {
        ResultadoEjecucionDTO resultado = new ResultadoEjecucionDTO();
        resultado.resumen = "Ejecutando reglas desde 0_test_reglas.drl";
        resultado.hechosInsertados = new ArrayList<>();
        resultado.reglasActivadas = new ArrayList<>();
        resultado.reglasEjecutadas = new ArrayList<>();

        String drl = Files.readString(Path.of("src/main/resources/rules/0_test_reglas.drl"));
        KieSession kieSession = crearSesion(drl);
        // ReglaTracker tracker = new ReglaTracker();

        kieSession.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void matchCreated(MatchCreatedEvent event) {
                resultado.reglasActivadas.add(event.getMatch().getRule().getName());
            }

            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                resultado.reglasEjecutadas.add(event.getMatch().getRule().getName());
            }
        });

        Cliente[] clientes = {
                new Cliente("Juan", 120, "Zona 3")
        };

        for (Cliente c : clientes) {
            kieSession.insert(c);
            resultado.hechosInsertados.add(c.toString());
        }

        kieSession.fireAllRules(0); // Activar reglas para capturar agenda
        kieSession.fireAllRules(); // Ejecutar
        resultado.totalEjecutadas = resultado.reglasEjecutadas.size();

        kieSession.dispose();
        return resultado;
    }

}