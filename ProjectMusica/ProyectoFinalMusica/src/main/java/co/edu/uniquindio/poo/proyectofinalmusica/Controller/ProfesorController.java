package co.edu.uniquindio.poo.proyectofinalmusica.Controller;

import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;

import java.util.List;
import java.util.stream.Collectors;

public class ProfesorController {
    private SistemaAcademia sistemaAcademia;

    public ProfesorController(SistemaAcademia sistemaAcademia) {
        this.sistemaAcademia = sistemaAcademia;
    }

    // CRUD básico
    public boolean agregarProfesor(Profesor profesor) {
        return sistemaAcademia.agregarProfesor(profesor);
    }

    public boolean actualizarProfesor(String id, Profesor actualizado) {
        return sistemaAcademia.actualizaProfesor(id, actualizado);
    }

    public boolean eliminarProfesor(String id) {
        return sistemaAcademia.eliminarProfesor(id);
    }

    public Profesor buscarProfesor(String id) {
        for (Profesor profesor : sistemaAcademia.getListProfesores()) {
            if (profesor.getId().equals(id)) {
                return profesor;
            }
        }
        return null;
    }

    public Profesor buscarProfesorPorCodigo(String codigo) {
        for (Profesor profesor : sistemaAcademia.getListProfesores()) {
            if (profesor.getCodigo().equals(codigo)) {
                return profesor;
            }
        }
        return null;
    }

    public List<Profesor> obtenerListaProfesores() {
        return sistemaAcademia.getListProfesores();
    }

    // Filtros
    public List<Profesor> obtenerProfesoresActivos() {
        return sistemaAcademia.getListProfesores().stream()
                .filter(Profesor::isActivo)
                .collect(Collectors.toList());
    }

    public List<Profesor> obtenerProfesoresPorEspecialidad(String especialidad) {
        return sistemaAcademia.getListProfesores().stream()
                .filter(p -> p.getEspecialidad().equalsIgnoreCase(especialidad))
                .collect(Collectors.toList());
    }

    public List<Profesor> obtenerProfesoresPorInstrumento(TipoInstrumento instrumento) {
        return sistemaAcademia.getListProfesores().stream()
                .filter(p -> p.getTheInstrumentosImpartidos().contains(instrumento))
                .collect(Collectors.toList());
    }

    // Gestión de instrumentos
    public boolean agregarInstrumentoAProfesor(String profesorId, TipoInstrumento instrumento) {
        Profesor profesor = buscarProfesor(profesorId);
        if (profesor != null && !profesor.getTheInstrumentosImpartidos().contains(instrumento)) {
            profesor.getTheInstrumentosImpartidos().add(instrumento);
            return true;
        }
        return false;
    }

    public boolean removerInstrumentoDeProfesor(String profesorId, TipoInstrumento instrumento) {
        Profesor profesor = buscarProfesor(profesorId);
        if (profesor != null) {
            return profesor.getTheInstrumentosImpartidos().remove(instrumento);
        }
        return false;
    }

    public List<TipoInstrumento> obtenerInstrumentosProfesor(String profesorId) {
        Profesor profesor = buscarProfesor(profesorId);
        if (profesor != null) {
            return profesor.getTheInstrumentosImpartidos();
        }
        return null;
    }

    // Gestión de disponibilidad
    public boolean agregarBloqueDisponibilidad(String profesorId, BloqueDisponibilidad bloque) {
        Profesor profesor = buscarProfesor(profesorId);
        if (profesor != null) {
            bloque.setTheProfesor(profesor);
            profesor.getTheDisponibilidad().add(bloque);
            return true;
        }
        return false;
    }

    public boolean eliminarBloqueDisponibilidad(String profesorId, String bloqueId) {
        Profesor profesor = buscarProfesor(profesorId);
        if (profesor != null) {
            return profesor.getTheDisponibilidad().removeIf(b -> b.getId().equals(bloqueId));
        }
        return false;
    }

    public List<BloqueDisponibilidad> obtenerDisponibilidadProfesor(String profesorId) {
        Profesor profesor = buscarProfesor(profesorId);
        if (profesor != null) {
            return profesor.getTheDisponibilidad();
        }
        return null;
    }

    public List<BloqueDisponibilidad> obtenerBloquesDisponibles(String profesorId) {
        Profesor profesor = buscarProfesor(profesorId);
        if (profesor != null) {
            return profesor.getTheDisponibilidad().stream()
                    .filter(BloqueDisponibilidad::isDisponible)
                    .collect(Collectors.toList());
        }
        return null;
    }

    public boolean verificarDisponibilidadEnHorario(String profesorId, String diaSemana, String horaInicio) {
        Profesor profesor = buscarProfesor(profesorId);
        if (profesor != null) {
            return profesor.getTheDisponibilidad().stream()
                    .anyMatch(b -> b.getDiaSemana().equals(diaSemana) &&
                            b.getHoraInicio().equals(horaInicio) &&
                            b.isDisponible());
        }
        return false;
    }

    // Gestión de clases asignadas
    public boolean asignarClaseAProfesor(String profesorId, Clase clase) {
        Profesor profesor = buscarProfesor(profesorId);
        if (profesor != null && !sistemaAcademia.verificarConflictoHorario(profesor, clase.getHorario())) {
            profesor.getTheClasesAsignadas().add(clase);
            clase.setTheProfesor(profesor);
            return true;
        }
        return false;
    }

    public boolean removerClaseDeProfesor(String profesorId, String claseId) {
        Profesor profesor = buscarProfesor(profesorId);
        if (profesor != null) {
            return profesor.getTheClasesAsignadas().removeIf(c -> c.getId().equals(claseId));
        }
        return false;
    }

    public List<Clase> obtenerClasesAsignadas(String profesorId) {
        Profesor profesor = buscarProfesor(profesorId);
        if (profesor != null) {
            return profesor.getTheClasesAsignadas();
        }
        return null;
    }

    public List<Clase> obtenerClasesActivasProfesor(String profesorId) {
        Profesor profesor = buscarProfesor(profesorId);
        if (profesor != null) {
            return profesor.getTheClasesAsignadas().stream()
                    .filter(Clase::isActiva)
                    .collect(Collectors.toList());
        }
        return null;
    }

    // Estadísticas
    public int contarClasesProfesor(String profesorId) {
        Profesor profesor = buscarProfesor(profesorId);
        return profesor != null ? profesor.getTheClasesAsignadas().size() : 0;
    }

    public int contarClasesGrupalesProfesor(String profesorId) {
        Profesor profesor = buscarProfesor(profesorId);
        if (profesor != null) {
            return (int) profesor.getTheClasesAsignadas().stream()
                    .filter(c -> c instanceof ClaseGrupal)
                    .count();
        }
        return 0;
    }

    public int contarClasesIndividualesProfesor(String profesorId) {
        Profesor profesor = buscarProfesor(profesorId);
        if (profesor != null) {
            return (int) profesor.getTheClasesAsignadas().stream()
                    .filter(c -> c instanceof ClaseIndividual)
                    .count();
        }
        return 0;
    }

    public int contarEstudiantesTotalProfesor(String profesorId) {
        Profesor profesor = buscarProfesor(profesorId);
        if (profesor != null) {
            int total = 0;
            for (Clase clase : profesor.getTheClasesAsignadas()) {
                if (clase instanceof ClaseGrupal) {
                    total += ((ClaseGrupal) clase).getTheEstudiantesInscritos().size();
                } else if (clase instanceof ClaseIndividual) {
                    total += ((ClaseIndividual) clase).getTheEstudiante() != null ? 1 : 0;
                }
            }
            return total;
        }
        return 0;
    }

    // Reportes específicos
    public String generarHorarioProfesor(String profesorId) {
        Profesor profesor = buscarProfesor(profesorId);
        if (profesor == null) return "Profesor no encontrado";

        StringBuilder horario = new StringBuilder();
        horario.append("=== HORARIO DEL PROFESOR ===\n\n");
        horario.append("Nombre: ").append(profesor.getNombre()).append("\n");
        horario.append("Código: ").append(profesor.getCodigo()).append("\n\n");

        for (Clase clase : profesor.getTheClasesAsignadas()) {
            horario.append(clase.getDiaSemana()).append(" ")
                    .append(clase.getHoraInicio()).append(" - ")
                    .append(clase.getHoraFin()).append(" | ")
                    .append(clase.getInstrumento()).append(" Nivel ")
                    .append(clase.getNivel());

            if (clase instanceof ClaseGrupal) {
                horario.append(" (Grupal)");
            } else {
                horario.append(" (Individual)");
            }
            horario.append("\n");
        }

        return horario.toString();
    }
}
