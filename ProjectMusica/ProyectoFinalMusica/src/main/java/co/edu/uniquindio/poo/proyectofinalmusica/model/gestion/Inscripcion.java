package main.java.co.edu.uniquindio.poo.proyectofinalmusica.model.gestion;

import ProyectoFinal.model.EstadoInscripcion;
import ProyectoFinal.model.Estudiante;

public class Inscripcion {
    private String id;
    private Estudiante theEstudiante; // muchos a 1
    private Curso theCurso; // muchos a 1
    private String fechaInscripcion;
    private EstadoInscripcion estado;
    private boolean activa;
    private boolean aprobada;
    private double calificacionFinal;

    public Inscripcion(String id, String fechaInscripcion, EstadoInscripcion estado, boolean activa, boolean aprobada, double calificacionFinal) {
        this.id = id;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
        this.activa = activa;
        this.aprobada = aprobada;
        this.calificacionFinal = calificacionFinal;


    }
}
