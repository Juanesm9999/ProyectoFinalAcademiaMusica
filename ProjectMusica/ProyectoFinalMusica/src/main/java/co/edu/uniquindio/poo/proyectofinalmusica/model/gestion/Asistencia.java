package co.edu.uniquindio.poo.proyectofinalmusica.model.gestion;

import co.edu.uniquindio.poo.proyectofinalmusica.model.Clase;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Estudiante;

import java.time.LocalDate;

public class Asistencia {
    private String id;
    private Estudiante theEstudiante;
    private Clase theClase;
    private LocalDate fecha;
    private boolean presente;

    private String observaciones;

    public Asistencia(String id, Estudiante theestudiante, Clase theclase,LocalDate fecha,boolean presente, String observaciones) {
        this.id = id;
        this.theEstudiante = theestudiante;
        this.theClase = theclase;
        this.fecha = fecha;
        this.presente = presente;

        this.observaciones = observaciones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Estudiante getTheEstudiante() {
        return theEstudiante;
    }

    public void setTheEstudiante(Estudiante theEstudiante) {
        this.theEstudiante = theEstudiante;
    }

    public Clase getTheClase() {
        return theClase;
    }

    public void setTheClase(Clase theClase) {
        this.theClase = theClase;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }


}