package co.edu.uniquindio.poo.proyectofinalmusica.controller;

import java.util.List;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Estudiante;
import co.edu.uniquindio.poo.proyectofinalmusica.model.SistemaAcademia;


public class EstudianteController {
    SistemaAcademia sistemaAcademia;


    public EstudianteController(SistemaAcademia sistemaAcademia) {
        this.sistemaAcademia = sistemaAcademia;
    }


    public boolean crearEstudiante(Estudiante estudiante) {
        return sistemaAcademia.registrarEstudiante(estudiante);
    }


    public List<Estudiante> obtenerListaEstudiantes() {
        return sistemaAcademia.getListEstudiantes();
    }


    public boolean eliminarEstudiante(String id) {
        return sistemaAcademia.eliminarEstudiante(id);
    }


    public boolean actualizarEstudiante(String id, Estudiante estudiante) {
        return sistemaAcademia.actualizarEstudiante(id, estudiante);
    }


    public void setSistemaAcademia(SistemaAcademia sistemaAcademia) {
    }

    public String getId() {
    }
}




















