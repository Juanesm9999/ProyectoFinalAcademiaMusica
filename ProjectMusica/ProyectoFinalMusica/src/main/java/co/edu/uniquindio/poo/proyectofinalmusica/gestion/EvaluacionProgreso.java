package ProyectoFinal.model.gestion;

import ProyectoFinal.model.Clase;
import ProyectoFinal.model.Estudiante;
import ProyectoFinal.model.Profesor;

public class EvaluacionProgreso {
    private String id;
    private Estudiante theEstudiante; // muchos a 1
    private Curso theCurso; // muchos a 1
    private Clase theClase; // muchos a 1
    private double calificacion;
    private String comentarios;

    private String areasAMejorar;
    private String fecha;
    private Profesor theEvaluador;

    public EvaluacionProgreso(String id, double calificacion, String comentarios,String AreasAMejorar,String fecha) {
        this.id = id;
        this.calificacion = calificacion;
        this.comentarios = comentarios;

        this.areasAMejorar = AreasAMejorar;
        this.fecha = fecha;

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

    public Curso getTheCurso() {
        return theCurso;
    }

    public void setTheCurso(Curso theCurso) {
        this.theCurso = theCurso;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }


    public String getAreasAMejorar() {
        return areasAMejorar;
    }

    public void setAreasAMejorar(String areasAMejorar) {
        this.areasAMejorar = areasAMejorar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

