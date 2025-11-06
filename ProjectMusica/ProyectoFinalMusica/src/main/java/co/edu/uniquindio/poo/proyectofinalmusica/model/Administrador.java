package co.edu.uniquindio.poo.proyectofinalmusica.model;

import java.time.LocalDate;

public class Administrador extends Persona {
    private String cargo;
    private String departamento;
    private String fechaIngreso;

    public Administrador(String cargo, String departamento, String fechaIngreso,String  id, String nombre, String email, String telefono, String direccion,  LocalDate fechaNacimiento,String usuario,String contrasenia) {
        super(id, nombre,email,telefono,direccion, fechaNacimiento,usuario,contrasenia);
        this.cargo = cargo;
        this.departamento = departamento;
        this.fechaIngreso = fechaIngreso;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }


}