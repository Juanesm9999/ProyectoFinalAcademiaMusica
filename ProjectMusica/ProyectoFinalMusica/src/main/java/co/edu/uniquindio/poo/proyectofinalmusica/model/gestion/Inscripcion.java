package co.edu.uniquindio.poo.proyectofinalmusica.model.gestion;

import co.edu.uniquindio.poo.proyectofinalmusica.model.EstadoInscripcion;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Estudiante;

public class Inscripcion {
    private String id;
    private Estudiante theEstudiante;
    private Curso theCurso;
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

    public Curso getTheCurso() {
        return theCurso;
    }

    public void setTheCurso(Curso theCurso) {
        this.theCurso = theCurso;
    }

    public String getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(String fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public EstadoInscripcion getEstado() {
        return estado;
    }

    public void setEstado(EstadoInscripcion estado) {
        this.estado = estado;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public boolean isAprobada() {
        return aprobada;
    }

    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }

    public double getCalificacionFinal() {
        return calificacionFinal;
    }

    public void setCalificacionFinal(double calificacionFinal) {
        this.calificacionFinal = calificacionFinal;
    }
}
