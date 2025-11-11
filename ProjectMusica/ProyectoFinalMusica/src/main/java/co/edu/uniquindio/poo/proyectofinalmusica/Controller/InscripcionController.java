package co.edu.uniquindio.poo.proyectofinalmusica.controller;

import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class InscripcionController {
    private SistemaAcademia sistemaAcademia;

    public InscripcionController(SistemaAcademia sistemaAcademia) {
        this.sistemaAcademia = sistemaAcademia;
    }

    /**
     * Inscribe a un estudiante en un curso
     */
    public boolean inscribirEstudiante(Estudiante estudiante, Curso curso) {
        return sistemaAcademia.inscribirEstudiante(estudiante, curso);
    }

    /**
     * Cancela una inscripción
     */
    public boolean cancelarInscripcion(String inscripcionId) {
        return sistemaAcademia.cancelarInscripcion(inscripcionId);
    }

    /**
     * Busca una inscripción por ID
     */
    public Inscripcion buscarInscripcion(String id) {
        for (Inscripcion insc : sistemaAcademia.getListInscripciones()) {
            if (insc.getId().equals(id)) {
                return insc;
            }
        }
        return null;
    }

    /**
     * Obtiene todas las inscripciones
     */
    public List<Inscripcion> obtenerTodasInscripciones() {
        return sistemaAcademia.getListInscripciones();
    }

    /**
     * Obtiene las inscripciones activas
     */
    public List<Inscripcion> obtenerInscripcionesActivas() {
        return sistemaAcademia.getListInscripciones().stream()
                .filter(Inscripcion::isActiva)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene las inscripciones de un estudiante específico
     */
    public List<Inscripcion> obtenerInscripcionesPorEstudiante(String estudianteId) {
        return sistemaAcademia.getListInscripciones().stream()
                .filter(i -> i.getTheEstudiante() != null &&
                        i.getTheEstudiante().getId().equals(estudianteId))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene las inscripciones activas de un estudiante
     */
    public List<Inscripcion> obtenerInscripcionesActivasPorEstudiante(String estudianteId) {
        return sistemaAcademia.getListInscripciones().stream()
                .filter(i -> i.getTheEstudiante() != null &&
                        i.getTheEstudiante().getId().equals(estudianteId) &&
                        i.isActiva())
                .collect(Collectors.toList());
    }

    /**
     * Obtiene las inscripciones de un curso específico
     */
    public List<Inscripcion> obtenerInscripcionesPorCurso(String cursoId) {
        return sistemaAcademia.getListInscripciones().stream()
                .filter(i -> i.getTheCurso() != null &&
                        i.getTheCurso().getId().equals(cursoId))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene las inscripciones activas de un curso
     */
    public List<Inscripcion> obtenerInscripcionesActivasPorCurso(String cursoId) {
        return sistemaAcademia.getListInscripciones().stream()
                .filter(i -> i.getTheCurso() != null &&
                        i.getTheCurso().getId().equals(cursoId) &&
                        i.isActiva())
                .collect(Collectors.toList());
    }

    /**
     * Verifica si un estudiante está inscrito en un curso
     */
    public boolean verificarInscripcionExistente(String estudianteId, String cursoId) {
        return sistemaAcademia.getListInscripciones().stream()
                .anyMatch(i -> i.getTheEstudiante() != null &&
                        i.getTheEstudiante().getId().equals(estudianteId) &&
                        i.getTheCurso() != null &&
                        i.getTheCurso().getId().equals(cursoId) &&
                        i.isActiva());
    }

    /**
     * Aprueba una inscripción
     */
    public boolean aprobarInscripcion(String inscripcionId, double calificacionFinal) {
        Inscripcion inscripcion = buscarInscripcion(inscripcionId);
        if (inscripcion != null) {
            inscripcion.setAprobada(true);
            inscripcion.setEstado(EstadoInscripcion.APROBADA);
            inscripcion.setCalificacionFinal(calificacionFinal);

            // Crear nivel aprobado para el estudiante
            Estudiante estudiante = inscripcion.getTheEstudiante();
            Curso curso = inscripcion.getTheCurso();

            NivelAprobado nivelAprobado = new NivelAprobado(
                    "NIVEL-" + System.currentTimeMillis(),
                    curso.getInstrumento(),
                    curso.getNivel(),
                    LocalDate.now().toString(),
                    calificacionFinal,
                    curso
            );

            estudiante.getTheNivelesAprobados().add(nivelAprobado);
            return true;
        }
        return false;
    }

    /**
     * Reprueba una inscripción
     */
    public boolean reprobarInscripcion(String inscripcionId, double calificacionFinal) {
        Inscripcion inscripcion = buscarInscripcion(inscripcionId);
        if (inscripcion != null) {
            inscripcion.setAprobada(false);
            inscripcion.setEstado(EstadoInscripcion.REPROBADA);
            inscripcion.setCalificacionFinal(calificacionFinal);
            inscripcion.setActiva(false);
            return true;
        }
        return false;
    }

    /**
     * Actualiza la calificación final de una inscripción
     */
    public boolean actualizarCalificacion(String inscripcionId, double calificacion) {
        Inscripcion inscripcion = buscarInscripcion(inscripcionId);
        if (inscripcion != null) {
            inscripcion.setCalificacionFinal(calificacion);
            return true;
        }
        return false;
    }

    /**
     * Cuenta las inscripciones activas de un estudiante
     */
    public int contarInscripcionesActivasEstudiante(String estudianteId) {
        return (int) sistemaAcademia.getListInscripciones().stream()
                .filter(i -> i.getTheEstudiante() != null &&
                        i.getTheEstudiante().getId().equals(estudianteId) &&
                        i.isActiva())
                .count();
    }

    /**
     * Obtiene inscripciones por estado
     */
    public List<Inscripcion> obtenerInscripcionesPorEstado(EstadoInscripcion estado) {
        return sistemaAcademia.getListInscripciones().stream()
                .filter(i -> i.getEstado() == estado)
                .collect(Collectors.toList());
    }

    /**
     * Genera reporte de inscripciones de un curso
     */
    public String generarReporteInscripcionesCurso(String cursoId) {
        StringBuilder reporte = new StringBuilder();
        List<Inscripcion> inscripciones = obtenerInscripcionesPorCurso(cursoId);

        reporte.append("=== REPORTE DE INSCRIPCIONES ===\n\n");

        if (!inscripciones.isEmpty()) {
            Curso curso = inscripciones.get(0).getTheCurso();
            reporte.append("Curso: ").append(curso.getNombre()).append("\n");
            reporte.append("Total inscripciones: ").append(inscripciones.size()).append("\n\n");

            for (Inscripcion insc : inscripciones) {
                reporte.append("Estudiante: ").append(insc.getTheEstudiante().getNombre()).append("\n");
                reporte.append("  Estado: ").append(insc.getEstado()).append("\n");
                reporte.append("  Fecha: ").append(insc.getFechaInscripcion()).append("\n");
                if (insc.getCalificacionFinal() > 0) {
                    reporte.append("  Calificación: ").append(insc.getCalificacionFinal()).append("\n");
                }
                reporte.append("\n");
            }
        } else {
            reporte.append("No hay inscripciones para este curso\n");
        }

        return reporte.toString();
    }
}