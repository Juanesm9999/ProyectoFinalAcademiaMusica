package co.edu.uniquindio.poo.proyectofinalmusica.Controller;

import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;

import java.util.List;

public class AdministradorController {
    private SistemaAcademia sistemaAcademia;

    public AdministradorController(SistemaAcademia sistemaAcademia) {
        this.sistemaAcademia = sistemaAcademia;
    }

    public boolean agregarAdministrador(Administrador administrador) {
        return sistemaAcademia.agregarAdministrador(administrador);
    }

    public boolean actualizarAdministrador(String id, Administrador actualizado) {
        return sistemaAcademia.actualizarAdministrador(id, actualizado);
    }

    public boolean eliminarAdministrador(String id) {
        return sistemaAcademia.eliminarAdministrador(id);
    }

    public Administrador buscarAdministrador(String id) {
        for (Administrador admin : sistemaAcademia.getListAdministradores()) {
            if (admin.getId().equals(id)) {
                return admin;
            }
        }
        return null;
    }

    public List<Administrador> obtenerListaAdministradores() {
        return sistemaAcademia.getListAdministradores();
    }

    // Reportes
    public String generarReporteAsistenciaPorCurso(String cursoId) {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE ASISTENCIA POR CURSO ===\n\n");

        Curso curso = buscarCurso(cursoId);
        if (curso == null) {
            return "Curso no encontrado";
        }

        reporte.append("Curso: ").append(curso.getNombre()).append("\n");
        reporte.append("Instrumento: ").append(curso.getInstrumento()).append("\n");
        reporte.append("Nivel: ").append(curso.getNivel()).append("\n\n");

        for (Estudiante estudiante : curso.getTheEstudiantes()) {
            reporte.append("Estudiante: ").append(estudiante.getNombre()).append("\n");

            long presentes = estudiante.getTheHistorialAsistencia().stream()
                    .filter(a -> a.isPresente())
                    .count();
            long total = estudiante.getTheHistorialAsistencia().size();

            double porcentaje = total > 0 ? (presentes * 100.0 / total) : 0;
            reporte.append("  Asistencia: ").append(presentes).append("/").append(total)
                    .append(" (").append(String.format("%.1f", porcentaje)).append("%)\n");
        }

        return reporte.toString();
    }

    public String generarReporteProgresoPorInstrumento(TipoInstrumento instrumento, int nivel) {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE PROGRESO ===\n\n");
        reporte.append("Instrumento: ").append(instrumento).append("\n");
        reporte.append("Nivel: ").append(nivel).append("\n\n");

        for (Curso curso : sistemaAcademia.getListCursos()) {
            if (curso.getInstrumento() == instrumento && curso.getNivel() == nivel) {
                reporte.append("Curso: ").append(curso.getNombre()).append("\n");

                for (Estudiante est : curso.getTheEstudiantes()) {
                    double promedio = calcularPromedioEstudiante(est);
                    reporte.append("  - ").append(est.getNombre())
                            .append(": ").append(String.format("%.2f", promedio)).append("\n");
                }
                reporte.append("\n");
            }
        }

        return reporte.toString();
    }

    public String generarReporteOcupacionAulas() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE OCUPACIÓN DE AULAS ===\n\n");

        for (Aula aula : sistemaAcademia.getListAulas()) {
            reporte.append("Aula: ").append(aula.getNombre()).append(" (").append(aula.getCodigo()).append(")\n");
            reporte.append("Capacidad: ").append(aula.getCapacidad()).append("\n");
            reporte.append("Ubicación: ").append(aula.getUbicacion()).append("\n");

            int clasesAsignadas = 0;
            for (Clase clase : sistemaAcademia.getListClases()) {
                if (clase.getTheAula() != null && clase.getTheAula().getId().equals(aula.getId())) {
                    clasesAsignadas++;
                }
            }

            reporte.append("Clases asignadas: ").append(clasesAsignadas).append("\n");
            reporte.append("Estado: ").append(aula.isDisponible() ? "Disponible" : "No disponible").append("\n\n");
        }

        return reporte.toString();
    }

    public String generarReporteCargaDocente() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE CARGA DOCENTE ===\n\n");

        for (Profesor profesor : sistemaAcademia.getListProfesores()) {
            reporte.append("Profesor: ").append(profesor.getNombre()).append("\n");
            reporte.append("Especialidad: ").append(profesor.getEspecialidad()).append("\n");
            reporte.append("Clases asignadas: ").append(profesor.getTheClasesAsignadas().size()).append("\n");

            reporte.append("Instrumentos: ");
            for (TipoInstrumento inst : profesor.getTheInstrumentosImpartidos()) {
                reporte.append(inst).append(" ");
            }
            reporte.append("\n\n");
        }

        return reporte.toString();
    }

    public String generarReporteEstudiante(String estudianteId) {
        StringBuilder reporte = new StringBuilder();
        Estudiante estudiante = buscarEstudiante(estudianteId);

        if (estudiante == null) {
            return "Estudiante no encontrado";
        }

        reporte.append("=== REPORTE DE ESTUDIANTE ===\n\n");
        reporte.append("Nombre: ").append(estudiante.getNombre()).append("\n");
        reporte.append("Matrícula: ").append(estudiante.getMatricula()).append("\n");
        reporte.append("Email: ").append(estudiante.getEmail()).append("\n\n");

        reporte.append("INSCRIPCIONES ACTIVAS:\n");
        for (Inscripcion insc : estudiante.getTheInscripciones()) {
            if (insc.isActiva()) {
                reporte.append("  - ").append(insc.getTheCurso().getNombre()).append("\n");
            }
        }

        reporte.append("\nNIVELES APROBADOS:\n");
        for (NivelAprobado nivel : estudiante.getTheNivelesAprobados()) {
            reporte.append("  - ").append(nivel.getInstrumento())
                    .append(" Nivel ").append(nivel.getNivel())
                    .append(": ").append(String.format("%.2f", nivel.getCalificacion())).append("\n");
        }

        double promedioGeneral = calcularPromedioEstudiante(estudiante);
        reporte.append("\nPROMEDIO GENERAL: ").append(String.format("%.2f", promedioGeneral)).append("\n");

        return reporte.toString();
    }

    // Métodos auxiliares
    private Curso buscarCurso(String id) {
        for (Curso curso : sistemaAcademia.getListCursos()) {
            if (curso.getId().equals(id)) {
                return curso;
            }
        }
        return null;
    }

    private Estudiante buscarEstudiante(String id) {
        for (Estudiante est : sistemaAcademia.getListEstudiantes()) {
            if (est.getId().equals(id)) {
                return est;
            }
        }
        return null;
    }

    private double calcularPromedioEstudiante(Estudiante estudiante) {
        if (estudiante.getTheEvaluaciones().isEmpty()) {
            return 0.0;
        }

        double suma = estudiante.getTheEvaluaciones().stream()
                .mapToDouble(EvaluacionProgreso::getCalificacion)
                .sum();

        return suma / estudiante.getTheEvaluaciones().size();
    }
}