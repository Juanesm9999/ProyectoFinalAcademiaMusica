package co.edu.uniquindio.poo.proyectofinalmusica.Controller;

import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;

import java.util.List;
import java.util.stream.Collectors;

public class ClaseController {
    private SistemaAcademia sistemaAcademia;

    public ClaseController(SistemaAcademia sistemaAcademia) {
        this.sistemaAcademia = sistemaAcademia;
    }

    // CRUD Clase Grupal
    public boolean crearClaseGrupal(ClaseGrupal claseGrupal) {
        return sistemaAcademia.crearClaseGrupal(claseGrupal);
    }

    public boolean modificarClaseGrupal(String id, ClaseGrupal actualizada) {
        return sistemaAcademia.modificarClaseGrupal(id, actualizada);
    }

    // CRUD Clase Individual
    public boolean crearClaseIndividual(ClaseIndividual claseIndividual) {
        return sistemaAcademia.crearClaseIndividual(claseIndividual);
    }

    public boolean modificarClaseIndividual(String id, ClaseIndividual actualizada) {
        return sistemaAcademia.modificarClaseIndividual(id, actualizada);
    }

    // Operaciones generales
    public Clase buscarClase(String id) {
        for (Clase clase : sistemaAcademia.getListClases()) {
            if (clase.getId().equals(id)) {
                return clase;
            }
        }
        return null;
    }

    public List<Clase> obtenerListaClases() {
        return sistemaAcademia.getListClases();
    }

    public List<ClaseGrupal> obtenerClasesGrupales() {
        return sistemaAcademia.getListClases().stream()
                .filter(c -> c instanceof ClaseGrupal)
                .map(c -> (ClaseGrupal) c)
                .collect(Collectors.toList());
    }

    public List<ClaseIndividual> obtenerClasesIndividuales() {
        return sistemaAcademia.getListClases().stream()
                .filter(c -> c instanceof ClaseIndividual)
                .map(c -> (ClaseIndividual) c)
                .collect(Collectors.toList());
    }

    public List<Clase> obtenerClasesActivas() {
        return sistemaAcademia.getListClases().stream()
                .filter(Clase::isActiva)
                .collect(Collectors.toList());
    }

    public List<Clase> obtenerClasesPorInstrumento(TipoInstrumento instrumento) {
        return sistemaAcademia.getListClases().stream()
                .filter(c -> c.getInstrumento() == instrumento)
                .collect(Collectors.toList());
    }

    public List<Clase> obtenerClasesPorProfesor(String profesorId) {
        return sistemaAcademia.getListClases().stream()
                .filter(c -> c.getTheProfesor() != null &&
                        c.getTheProfesor().getId().equals(profesorId))
                .collect(Collectors.toList());
    }

    public boolean asignarProfesorAClase(String claseId, Profesor profesor) {
        Clase clase = buscarClase(claseId);
        if (clase != null) {
            // Verificar conflictos de horario
            if (!sistemaAcademia.verificarConflictoHorario(profesor, clase.getHorario())) {
                clase.setTheProfesor(profesor);
                profesor.getTheClasesAsignadas().add(clase);
                return true;
            }
        }
        return false;
    }

    public boolean asignarAulaAClase(String claseId, Aula aula) {
        Clase clase = buscarClase(claseId);
        if (clase != null) {
            // Verificar conflictos de aula
            if (!sistemaAcademia.verificarConflictoAula(aula, clase.getHorario())) {
                clase.setTheAula(aula);
                return true;
            }
        }
        return false;
    }

    public boolean inscribirEstudianteEnClaseGrupal(String claseId, Estudiante estudiante) {
        Clase clase = buscarClase(claseId);
        if (clase instanceof ClaseGrupal) {
            ClaseGrupal grupal = (ClaseGrupal) clase;
            if (grupal.getCuposDisponibles() > 0 &&
                    !grupal.getTheEstudiantesInscritos().contains(estudiante)) {
                grupal.getTheEstudiantesInscritos().add(estudiante);
                grupal.setCapacidadActual(grupal.getCapacidadActual() + 1);
                grupal.setCuposDisponibles(grupal.getCuposDisponibles() - 1);
                return true;
            }
        }
        return false;
    }

    public boolean asignarEstudianteAClaseIndividual(String claseId, Estudiante estudiante) {
        Clase clase = buscarClase(claseId);
        if (clase instanceof ClaseIndividual) {
            ClaseIndividual individual = (ClaseIndividual) clase;
            if (individual.getTheEstudiante() == null) {
                individual.setTheEstudiante(estudiante);
                return true;
            }
        }
        return false;
    }

    public List<Clase> obtenerClasesConCuposDisponibles() {
        return sistemaAcademia.getListClases().stream()
                .filter(c -> c instanceof ClaseGrupal)
                .map(c -> (ClaseGrupal) c)
                .filter(c -> c.getCuposDisponibles() > 0)
                .collect(Collectors.toList());
    }

    public int contarEstudiantesEnClase(String claseId) {
        Clase clase = buscarClase(claseId);
        if (clase instanceof ClaseGrupal) {
            return ((ClaseGrupal) clase).getTheEstudiantesInscritos().size();
        } else if (clase instanceof ClaseIndividual) {
            return ((ClaseIndividual) clase).getTheEstudiante() != null ? 1 : 0;
        }
        return 0;
    }

    public boolean eliminarClase(String id) {
        for (Clase clase : sistemaAcademia.getListClases()) {
            if (clase.getId().equals(id)) {
                sistemaAcademia.getListClases().remove(clase);
                return true;
            }
        }
        return false;
    }

    public boolean verificarConflictoAulaHorario(Aula aula, String diaSemana, String horaInicio, String horaFin) {
        return verificarConflictoAulaHorario(aula, diaSemana, horaInicio, horaFin, null);
    }

    public boolean verificarConflictoAulaHorario(Aula aula, String diaSemana, String horaInicio, String horaFin, String excluirClaseId) {
        if (aula == null) {
            return false;
        }

        for (Clase clase : sistemaAcademia.getListClases()) {
            if (excluirClaseId != null && clase.getId().equals(excluirClaseId)) {
                continue;
            }

            if (clase.isActiva() && 
                clase.getTheAula() != null && 
                clase.getTheAula().getId().equals(aula.getId()) &&
                clase.getDiaSemana() != null &&
                clase.getDiaSemana().equals(diaSemana) &&
                clase.getHoraInicio() != null &&
                clase.getHoraFin() != null) {
                
                if (haySolapamientoHorario(clase.getHoraInicio(), clase.getHoraFin(), horaInicio, horaFin)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean haySolapamientoHorario(String inicio1, String fin1, String inicio2, String fin2) {
        try {
            int minutosInicio1 = convertirHoraAMinutos(inicio1);
            int minutosFin1 = convertirHoraAMinutos(fin1);
            int minutosInicio2 = convertirHoraAMinutos(inicio2);
            int minutosFin2 = convertirHoraAMinutos(fin2);

            return (minutosInicio1 < minutosFin2 && minutosInicio2 < minutosFin1);
        } catch (Exception e) {
            return inicio1.equals(inicio2) && fin1.equals(fin2);
        }
    }

    private int convertirHoraAMinutos(String hora) {
        String[] partes = hora.split(":");
        int horas = Integer.parseInt(partes[0]);
        int minutos = partes.length > 1 ? Integer.parseInt(partes[1]) : 0;
        return horas * 60 + minutos;
    }
}