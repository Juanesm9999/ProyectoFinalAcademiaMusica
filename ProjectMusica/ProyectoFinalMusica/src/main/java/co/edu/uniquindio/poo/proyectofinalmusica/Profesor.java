package ProyectoFinal.model;

import ProyectoFinal.model.gestion.BloqueDisponibilidad;

import java.util.List;

public class Profesor extends Persona {
    private String codigo;
    private String especialidad;
    private String fechaContratacion;
    private boolean activo;
    private List<TipoInstrumento> theInstrumentosImpartidos; // 1 a muchos
    private List<BloqueDisponibilidad> theDisponibilidad; // 1 a muchos
    private List<Clase> theClasesAsignadas; // 1 a muchos

    public Profesor(String codigo, String especialidad,String fechaContratacion,boolean activo, String  id, String nombre, String apellido, String email, String telefono, String direccion,  String fechaNacimiento) {
        super(id, nombre,apellido,email,telefono,direccion, fechaNacimiento);
        this.codigo = codigo;
        this.especialidad = especialidad;
        this.fechaContratacion = fechaContratacion;
        this.activo = activo;
    }

}