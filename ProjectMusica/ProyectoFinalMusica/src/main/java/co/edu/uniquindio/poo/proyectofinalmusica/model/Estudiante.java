package co.edu.uniquindio.poo.proyectofinalmusica.model;

import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Asistencia;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.EvaluacionProgreso;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Inscripcion;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.NivelAprobado;

import java.util.List;

public class Estudiante extends Persona implements IReportable {
    private String matricula;
    private String fechaIngreso;
    private boolean activo;
    private List<Inscripcion> theInscripciones; // 1 a muchos
    private List<NivelAprobado> theNivelesAprobados; // 1 a muchos
    private List<Asistencia> theHistorialAsistencia; // 1 a muchos
    private List<EvaluacionProgreso> theEvaluaciones; // 1 a muchos

    public Estudiante(String matricula, String fechaIngreso , boolean activo, String  id, String nombre, String apellido, String email, String telefono, String direccion,  String fechaNacimiento) {
        super(id, nombre,email,telefono,direccion, fechaNacimiento);
        this.matricula = matricula;
        this.fechaIngreso = fechaIngreso;
        this.activo = activo;

    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Inscripcion> getTheInscripciones() {
        return theInscripciones;
    }

    public void setTheInscripciones(List<Inscripcion> theInscripciones) {
        this.theInscripciones = theInscripciones;
    }

    public List<NivelAprobado> getTheNivelesAprobados() {
        return theNivelesAprobados;
    }

    public void setTheNivelesAprobados(List<NivelAprobado> theNivelesAprobados) {
        this.theNivelesAprobados = theNivelesAprobados;
    }

    public List<Asistencia> getTheHistorialAsistencia() {
        return theHistorialAsistencia;
    }

    public void setTheHistorialAsistencia(List<Asistencia> theHistorialAsistencia) {
        this.theHistorialAsistencia = theHistorialAsistencia;
    }

    public List<EvaluacionProgreso> getTheEvaluaciones() {
        return theEvaluaciones;
    }

    public void setTheEvaluaciones(List<EvaluacionProgreso> theEvaluaciones) {
        this.theEvaluaciones = theEvaluaciones;
    }

    @Override
    public String generarReporte() {
        return "";
    }




}