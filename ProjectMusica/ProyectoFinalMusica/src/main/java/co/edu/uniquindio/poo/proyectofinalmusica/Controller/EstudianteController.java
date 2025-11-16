package co.edu.uniquindio.poo.proyectofinalmusica.Controller;

import co.edu.uniquindio.poo.proyectofinalmusica.model.Estudiante;
import co.edu.uniquindio.poo.proyectofinalmusica.model.SistemaAcademia;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;

import java.util.List;
import java.util.stream.Collectors;

public class EstudianteController {
    private SistemaAcademia sistemaAcademia;

    public EstudianteController(SistemaAcademia sistemaAcademia) {
        this.sistemaAcademia = sistemaAcademia;
    }

    public boolean registrarEstudiante(Estudiante estudiante) {
        return sistemaAcademia.registrarEstudiante(estudiante);
    }

    public boolean actualizarEstudiante(String id, Estudiante actualizado) {
        return sistemaAcademia.actualizarEstudiante(id, actualizado);
    }

    public boolean eliminarEstudiante(String id) {
        return sistemaAcademia.eliminarEstudiante(id);
    }

    public Estudiante buscarEstudiante(String id) {
        for (Estudiante estudiante : sistemaAcademia.getListEstudiantes()) {
            if (estudiante.getId().equals(id)) {
                return estudiante;
            }
        }
        return null;
    }

    public List<Estudiante> obtenerListaEstudiantes() {
        return sistemaAcademia.getListEstudiantes();
    }

    public List<Estudiante> obtenerEstudiantesActivos() {
        return sistemaAcademia.getListEstudiantes().stream()
                .filter(Estudiante::getActivo)
                .collect(Collectors.toList());
    }

    public List<Inscripcion> obtenerInscripcionesEstudiante(String estudianteId) {
        Estudiante estudiante = buscarEstudiante(estudianteId);
        if (estudiante != null) {
            return estudiante.getTheInscripciones();
        }
        return null;
    }

    public List<Asistencia> obtenerAsistenciasEstudiante(String estudianteId) {
        Estudiante estudiante = buscarEstudiante(estudianteId);
        if (estudiante != null) {
            return estudiante.getTheHistorialAsistencia();
        }
        return null;
    }

    public List<EvaluacionProgreso> obtenerEvaluacionesEstudiante(String estudianteId) {
        Estudiante estudiante = buscarEstudiante(estudianteId);
        if (estudiante != null) {
            return estudiante.getTheEvaluaciones();
        }
        return null;
    }

    public boolean inscribirACurso(String estudianteId, String cursoId) {
        Estudiante estudiante = buscarEstudiante(estudianteId);
        Curso curso = null;

        for (Curso c : sistemaAcademia.getListCursos()) {
            if (c.getId().equals(cursoId)) {
                curso = c;
                break;
            }
        }

        if (estudiante != null && curso != null) {
            return sistemaAcademia.inscribirEstudiante(estudiante, curso);
        }
        return false;
    }

    public double calcularPromedioEstudiante(String estudianteId) {
        Estudiante estudiante = buscarEstudiante(estudianteId);
        if (estudiante != null && !estudiante.getTheEvaluaciones().isEmpty()) {
            double suma = estudiante.getTheEvaluaciones().stream()
                    .mapToDouble(EvaluacionProgreso::getCalificacion)
                    .sum();
            return suma / estudiante.getTheEvaluaciones().size();
        }
        return 0.0;
    }
}