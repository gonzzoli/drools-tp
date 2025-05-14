package com.ejemplo.reglas.service;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.runtime.rule.Match;

public class ReglaTracker extends DefaultAgendaEventListener {

    private final List<Match> activadas = new ArrayList<>();

    @Override
    public void matchCreated(MatchCreatedEvent event) {
        System.out.println("📋 Regla activada en agenda: " + event.getMatch().getRule().getName());
        activadas.add(event.getMatch());
    }

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        System.out.println("🎯 Regla ejecutada: " + event.getMatch().getRule().getName());
    }

    public List<Match> getReglasPendientes() {
        return new ArrayList<>(activadas);
    }

    public void limpiar() {
        activadas.clear();
    }
}