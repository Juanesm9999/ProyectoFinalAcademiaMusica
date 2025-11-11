package co.edu.uniquindio.poo.proyectofinalmusica.controller;

import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;

import java.util.List;
import java.util.stream.Collectors;

public class CursoController {
    private SistemaAcademia sistemaAcademia;

    public CursoController(SistemaAcademia sistemaAcademia) {
        this.sistemaAcademia = sistemaAcademia;
    }

    // CRUD básico
    public boolean crearCurso(Curso curso) {
        return sistemaAcademia.crearCurso(curso);
    }

    public boolean modificarCurso(String id, Curso actualizado) {
        return sistemaAcademia.modificarCurso(id, actualizado);
    }

    public boolean eliminarCurso(String id) {
        return sistemaAcademia.eliminarCurso(id);
    }

    public Curso buscarCurso(String id) {
        for (Curso curso : sistemaAcademia.getListCursos()) {
            if (curso.getId().equals(id)) {
                return curso;
            }
        }
        return null;
    }

    public Curso buscarCursoPorCodigo(String codigo) {
        for (Curso curso : sistemaAcademia.getListCursos()) {
            if (curso.getCodigo().equals(codigo)) {
                return curso;
            }
        }
        return null;
    }

    public List<Curso> obtenerListaCursos() {
        return sistemaAcademia.getListCursos();
    }

    // Filtros y búsquedas
    public List<Curso> obtenerCursosActivos() {
        return sistemaAcademia.getListCursos().stream()
                .filter(c -> c.getEstado() == EstadoCurso.ACTIVO)
                .collect(Collectors.toList());
    }

    public List<Curso> obtenerCursosPorInstrumento(TipoInstrumento instrumento) {
        return sistemaAcademia.getListCursos().stream()
                .filter(c -> c.getInstrumento() == instrumento)
                .collect(Collectors.toList());
    }

    public List<Curso> obtenerCursosPorNivel(int nivel) {
        return sistemaAcademia.getListCursos().stream()
                .filter(c -> c.getNivel() == nivel)
                .collect(Collectors.toList());
    }

    public List<Curso> obtenerCursosConCuposDisponibles() {
        return sistemaAcademia.getListCursos().stream()
                .filter(c -> c.getCapacidadActual() < c.getCapacidadMaxima())
                .filter(c -> c.getEstado() == EstadoCurso.ACTIVO)
                .collect(Collectors.toList());
    }

    // Gestión de estudiantes
    public boolean agregarEstudianteACurso(String cursoId, Estudiante estudiante) {
        Curso curso = buscarCurso(cursoId);
        if (curso != null && sistemaAcademia.verificarCuposDisponibles(curso)) {
            return sistemaAcademia.inscribirEstudiante(estudiante, curso);
        }
        return false;
    }

    public List<Estudiante> obtenerEstudiantesDeCurso(String cursoId) {
        Curso curso = buscarCurso(cursoId);
        if (curso != null) {
            return curso.getTheEstudiantes();
        }
        return null;
    }

    public int contarEstudiantesInscritos(String cursoId) {
        Curso curso = buscarCurso(cursoId);
        return curso != null ? curso.getCapacidadActual() : 0;
    }

    // Gestión de clases
    public boolean agregarClaseACurso(String cursoId, ClaseGrupal clase) {
        Curso curso = buscarCurso(cursoId);
        if (curso != null) {
            curso.getTheClases().add(clase);
            return true;
        }
        return false;
    }

    public List<ClaseGrupal> obtenerClasesDeCurso(String cursoId) {
        Curso curso = buscarCurso(cursoId);
        if (curso != null) {
            return curso.getTheClases();
        }
        return null;
    }

    // Gestión de prerrequisitos
    public boolean agregarPrerrequisito(String cursoId, Curso prerrequisito) {
        Curso curso = buscarCurso(cursoId);
        if (curso != null && prerrequisito != null) {
            if (curso.getThePrerrequisitos() == null) {
                curso.setThePrerrequisitos(new java.util.ArrayList<>());
            }
            if (!curso.getThePrerrequisitos().contains(prerrequisito)) {
                curso.getThePrerrequisitos().add(prerrequisito);
                return true;
            }
        }
        return false;
    }

    public List<Curso> obtenerPrerrequisitos(String cursoId) {
        Curso curso = buscarCurso(cursoId);
        if (curso != null && curso.getThePrerrequisitos() != null) {
            return curso.getThePrerrequisitos();
        }
        return new java.util.ArrayList<>();
    }

    public boolean verificarPrerrequisitosEstudiante(String cursoId, Estudiante estudiante) {
        Curso curso = buscarCurso(cursoId);
        if (curso != null) {
            return sistemaAcademia.verificarPrerrequisitos(estudiante, curso);
        }
        return false;
    }

    // Estadísticas
    public double calcularPorcentajeOcupacion(String cursoId) {
        Curso curso = buscarCurso(cursoId);
        if (curso != null && curso.getCapacidadMaxima() > 0) {
            return (curso.getCapacidadActual() * 100.0) / curso.getCapacidadMaxima();
        }
        return 0.0;
    }

    public int obtenerCuposDisponibles(String cursoId) {
        Curso curso = buscarCurso(cursoId);
        if (curso != null) {
            return curso.getCapacidadMaxima() - curso.getCapacidadActual();
        }
        return 0;
    }

    // Cambio de estado
    public boolean activarCurso(String cursoId) {
        Curso curso = buscarCurso(cursoId);
        if (curso != null) {
            curso.setEstado(EstadoCurso.ACTIVO);
            return true;
        }
        return false;
    }

    public boolean completarCurso(String cursoId) {
        Curso curso = buscarCurso(cursoId);
        if (curso != null) {
            curso.setEstado(EstadoCurso.COMPLETADO);
            return true;
        }
        return false;
    }

    public boolean cancelarCurso(String cursoId) {
        Curso curso = buscarCurso(cursoId);
        if (curso != null) {
            curso.setEstado(EstadoCurso.CANCELADO);
            return true;
        }
        return false;
    }
}
