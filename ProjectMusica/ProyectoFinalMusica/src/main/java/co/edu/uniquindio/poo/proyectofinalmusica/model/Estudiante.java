package ProyectoFinal.model;

import main.java.co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Asistencia;
import main.java.co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.EvaluacionProgreso;
import main.java.co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Inscripcion;
import main.java.co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.NivelAprobado;

import java.util.List;

public class Estudiante extends Persona implements IReportable {
    private String matricula;
    private String fechaIngreso;
    private boolean activo;
    private List<Inscripcion> theInscripciones; // 1 a muchos
    private List<NivelAprobado> theNivelesAprobados; // 1 a muchos
    private List<Asistencia> theHistorialAsistencia; // 1 a muchos
    private List<EvaluacionProgreso> theEvaluaciones; // 1 a muchos

    public Estudiante(String matricula,String fechaIngreso , boolean activo, String  id, String nombre, String apellido, String email, String telefono, String direccion,  String fechaNacimiento) {
        super(id, nombre,apellido,email,telefono,direccion, fechaNacimiento);
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

    @Override
    public String generarReporte() {
        return "";
    }


}