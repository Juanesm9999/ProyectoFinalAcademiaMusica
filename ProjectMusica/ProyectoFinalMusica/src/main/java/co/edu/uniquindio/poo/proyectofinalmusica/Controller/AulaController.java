package co.edu.uniquindio.poo.proyectofinalmusica.controller;

import co.edu.uniquindio.poo.proyectofinalmusica.model.Clase;
import co.edu.uniquindio.poo.proyectofinalmusica.model.SistemaAcademia;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Aula;

import java.util.List;
import java.util.stream.Collectors;

public class AulaController {
    private SistemaAcademia sistemaAcademia;

    public AulaController(SistemaAcademia sistemaAcademia) {
        this.sistemaAcademia = sistemaAcademia;
    }

    public boolean agregarAula(Aula aula) {
        if (!verificarAula(aula.getId())) {
            sistemaAcademia.getListAulas().add(aula);
            return true;
        }
        return false;
    }

    public boolean actualizarAula(String id, Aula actualizada) {
        for (Aula aula : sistemaAcademia.getListAulas()) {
            if (aula.getId().equals(id)) {
                aula.setCodigo(actualizada.getCodigo());
                aula.setNombre(actualizada.getNombre());
                aula.setUbicacion(actualizada.getUbicacion());
                aula.setCapacidad(actualizada.getCapacidad());
                aula.setDisponible(actualizada.isDisponible());
                return true;
            }
        }
        return false;
    }

    public boolean eliminarAula(String id) {
        for (Aula aula : sistemaAcademia.getListAulas()) {
            if (aula.getId().equals(id)) {
                sistemaAcademia.getListAulas().remove(aula);
                return true;
            }
        }
        return false;
    }

    public Aula buscarAula(String id) {
        for (Aula aula : sistemaAcademia.getListAulas()) {
            if (aula.getId().equals(id)) {
                return aula;
            }
        }
        return null;
    }

    public List<Aula> obtenerListaAulas() {
        return sistemaAcademia.getListAulas();
    }

    public List<Aula> obtenerAulasDisponibles() {
        return sistemaAcademia.getListAulas().stream()
                .filter(Aula::isDisponible)
                .collect(Collectors.toList());
    }

    public List<Aula> obtenerAulasPorCapacidadMinima(int capacidadMinima) {
        return sistemaAcademia.getListAulas().stream()
                .filter(a -> a.getCapacidad() >= capacidadMinima)
                .collect(Collectors.toList());
    }

    public boolean verificarDisponibilidad(String aulaId, String horario) {
        return !sistemaAcademia.verificarConflictoAula(buscarAula(aulaId), horario);
    }

    public int contarClasesAsignadas(String aulaId) {
        int contador = 0;
        for (Clase clase : sistemaAcademia.getListClases()) {
            if (clase.getTheAula() != null && clase.getTheAula().getId().equals(aulaId)) {
                contador++;
            }
        }
        return contador;
    }

    private boolean verificarAula(String id) {
        for (Aula aula : sistemaAcademia.getListAulas()) {
            if (aula.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public List<Clase> obtenerClasesPorAula(String aulaId) {
        return sistemaAcademia.getListClases().stream()
                .filter(c -> c.getTheAula() != null && c.getTheAula().getId().equals(aulaId))
                .collect(Collectors.toList());
    }
}