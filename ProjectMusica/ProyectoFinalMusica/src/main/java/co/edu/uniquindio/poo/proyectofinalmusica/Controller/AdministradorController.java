package co.edu.uniquindio.poo.proyectofinalmusica.controller;

import java.util.List;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Administrador;
import co.edu.uniquindio.poo.proyectofinalmusica.model.SistemaAcademia;


public class AdministradorController {
    SistemaAcademia sistemaAcademia;


    public AdministradorController(SistemaAcademia sistemaAcademia) {
        this.sistemaAcademia = sistemaAcademia;
    }


    public boolean crearAdministrador(Administrador administrador) {
        return sistemaAcademia.agregarAdministrador(administrador);
    }


    public List<Administrador> obtenerListaAdministradores() {
        return sistemaAcademia.getListAdministradores();
    }


    public boolean eliminarPropietario(String id) {
        return sistemaAcademia.eliminarPropietario(id);
    }


    public boolean actualizarPropietario(String id, Propietario propietario) {
        return sistemaAcademia.actualizarPropietario(id, propietario);
    }


    public void setVeterinaria(SistemaAcademia sistemaAcademia) {
    }
}



















