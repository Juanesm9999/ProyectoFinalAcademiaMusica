package ProyectoFinal.model;


import ProyectoFinal.model.gestion.*;

import java.util.List;

public class SistemaAcademia {
    private String nombre;
    private String nit;
    private List<Estudiante> listEstudiantes;
    private List<Profesor> theProfesores;
    private List<Administrador> theAdministradores;
    private List<Curso> theCursos;
    private List<Aula> theAulas;
    private List<Clase> theClases;
    private List<Inscripcion> theInscripciones;
    private List<Asistencia> theAsistencias;
    private List<EvaluacionProgreso> theEvaluaciones;

    public SistemaAcademia(String nombre, String nit, List<Estudiante> listEstudiantes) {
        this.nombre = nombre;
        this.nit = nit;
        this.listEstudiantes = new ArrayList<>();
        this.theProfesores = new ArrayList<>();
        this.theAdministradores = new ArrayList<>();
        this.theCursos = new ArrayList<>();
        this.theAulas = new ArrayList<>();
        this.theClases = new ArrayList<>();
        this.theInscripciones = new ArrayList<>();
        this.theAsistencias = new ArrayList<>();
        this.theEvaluaciones = new ArrayList<>();

    }

    public boolean registrarEstudiante(Estudiante estudiante) {

            boolean centinela = false;
            if (!verificarEstudiante(estudiante.getId())) {
                listEstudiantes.add(estudiante);
                centinela = true;
            }
            return centinela;
        }
    }
    public boolean actualizarEstudiante(String id, Estudiante actualizado) {
    return centinela;
}

    public boolean verificarEstudiante(String id) {
    boolean centinela = false;
    for (Estudiante estudiante : listEstudiantes) {
        if (estudiante.getId().equals(id)) {
            centinela = true;
        }
    }
    return centinela;
}



        public boolean eliminarEstudiante(String id) {
            boolean centinela = false;
            for (Estudiante estudiante: listEstudiantes) {
                if (estudiante.getId().equals(id)) {
                    // CORREGIDO: debe eliminar de listPropietarios, NO de listMascotas
                    listEstudiantes.remove(estudiante);
                    centinela = true;
                    break;
                }
            }
            return centinela;
        }
    }

    public void registrarProfesor(Profesor profesor) {}
    public void actualizarProfesor(Profesor profesor) {}
    public void eliminarProfesor(String id) {}

    public void crearCurso(Curso curso) {}
    public void modificarCurso(Curso curso) {}
    public void eliminarCurso(String id) {}

    public void inscribirEstudiante(Estudiante estudiante, Curso curso) {}
    public void cancelarInscripcion(String inscripcionId) {}

    public void crearClaseGrupal(ClaseGrupal clase) {}
    public void modificarClaseGrupal(ClaseGrupal clase) {}

    public void crearClaseIndividual(ClaseIndividual clase) {}
    public void modificarClaseIndividual(ClaseIndividual clase) {}

    public void registrarAsistencia(Asistencia asistencia) {}
    public void registrarEvaluacion(EvaluacionProgreso evaluacion) {}

    public boolean verificarConflictoHorario(Profesor profesor, String horario) {
        return false;
    }
    public boolean verificarConflictoAula(Aula aula, String horario) {
        return false;
    }
    public boolean verificarPrerrequisitos(Estudiante estudiante, Curso curso) {return false;}
    public boolean verificarCuposDisponibles(Curso curso) {
        return false;
    }

    public List<String> generarReporteAsistencia(Curso curso) {return null;}
    public List<String> generarReporteProgreso(TipoInstrumento instrumento, int nivel) {
        return null;
    }
    public List<String> generarReporteOcupacionAulas() {return null;}
    public List<String> generarReporteCargaDocente() {return null;}
    public List<String> generarReporteEstudiante(Estudiante estudiante) {return null;}
}