package co.edu.uniquindio.poo.proyectofinalmusica.model;

import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.BloqueDisponibilidad;


import java.util.List;

public class Profesor extends Persona {
    private String codigo;
    private String especialidad;
    private String fechaContratacion;
    private boolean activo;
    private List<TipoInstrumento> theInstrumentosImpartidos; // 1 a muchos
    private List<BloqueDisponibilidad> theDisponibilidad; // 1 a muchos
    private List<Clase> theClasesAsignadas; // 1 a muchos

    public Profesor(String codigo, String especialidad,String fechaContratacion,boolean activo, String  id, String nombre, String email, String telefono, String direccion,  String fechaNacimiento) {
        super(id, nombre,email,telefono,direccion, fechaNacimiento);
        this.codigo = codigo;
        this.especialidad = especialidad;
        this.fechaContratacion = fechaContratacion;
        this.activo = activo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(String fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<TipoInstrumento> getTheInstrumentosImpartidos() {
        return theInstrumentosImpartidos;
    }

    public void setTheInstrumentosImpartidos(List<TipoInstrumento> theInstrumentosImpartidos) {
        this.theInstrumentosImpartidos = theInstrumentosImpartidos;
    }

    public List<BloqueDisponibilidad> getTheDisponibilidad() {
        return theDisponibilidad;
    }

    public void setTheDisponibilidad(List<BloqueDisponibilidad> theDisponibilidad) {
        this.theDisponibilidad = theDisponibilidad;
    }

    public List<Clase> getTheClasesAsignadas() {
        return theClasesAsignadas;
    }

    public void setTheClasesAsignadas(List<Clase> theClasesAsignadas) {
        this.theClasesAsignadas = theClasesAsignadas;
    }


}