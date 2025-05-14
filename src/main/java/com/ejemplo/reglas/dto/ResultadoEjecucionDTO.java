package com.ejemplo.reglas.dto;

import java.util.List;

public class ResultadoEjecucionDTO {
    public String resumen;
    public List<String> hechosInsertados;
    public List<String> reglasActivadas;
    public List<String> reglasEjecutadas;
    public int totalEjecutadas;
}