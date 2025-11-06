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

    public boolean eliminarAdministrador(String id) {
        return sistemaAcademia.eliminarAdministrador(id);
    }

    public boolean actualizarAdministrador(String id, Administrador administrador) {
        return sistemaAcademia.actualizarAdministrador(id, administrador);
    }

    public boolean verificarAdministrador(String id) {
        return sistemaAcademia.verificarAdministrador(id);
    }
}



















