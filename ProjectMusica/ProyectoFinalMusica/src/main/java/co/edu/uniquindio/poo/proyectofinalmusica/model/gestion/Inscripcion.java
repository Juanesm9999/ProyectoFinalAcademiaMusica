package co.edu.uniquindio.poo.proyectofinalmusica.model.gestion;

import co.edu.uniquindio.poo.proyectofinalmusica.model.EstadoInscripcion;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Estudiante;

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
