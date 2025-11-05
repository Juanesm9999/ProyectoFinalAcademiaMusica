package co.edu.uniquindio.poo.proyectofinalmusica.model.gestion;

import co.edu.uniquindio.poo.proyectofinalmusica.model.EstadoCurso;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Estudiante;
import co.edu.uniquindio.poo.proyectofinalmusica.model.SistemaAcademia;
import co.edu.uniquindio.poo.proyectofinalmusica.model.TipoInstrumento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InscripcionTest {
    private SistemaAcademia sistema;
    private Estudiante estudiante1;
    private Curso curso1;
    private Curso curso2;

    @BeforeEach
    public void setUp() {
        sistema = new SistemaAcademia("Academia UQ Música", "123456789");

        estudiante1 = new Estudiante(
                "EST001", LocalDate.now(), true, "E001",
                "Juan Pérez", "juan@email.com", "3001234567",
                "Calle 1", LocalDate.of(2000, 1, 15)
        );

        curso1 = new Curso(
                "C001", "PIANO-1", "Piano Nivel 1",
                TipoInstrumento.PIANO, 1, "Introducción al piano",
                20, 0, EstadoCurso.ACTIVO,
                "2025-01-15", "2025-06-15", 20
        );

        curso2 = new Curso(
                "C002", "PIANO-2", "Piano Nivel 2",
                TipoInstrumento.PIANO, 2, "Piano intermedio",
                15, 0, EstadoCurso.ACTIVO,
                "2025-01-15", "2025-06-15", 20
        );
    }

    @Test
    public void testInscribirEstudianteExitoso() {
        sistema.registrarEstudiante(estudiante1);
        sistema.crearCurso(curso1);

        boolean resultado = sistema.inscribirEstudiante(estudiante1, curso1);
        assertTrue(resultado);
        assertEquals(1, sistema.getListInscripciones().size());
    }

    @Test
    public void testInscribirEstudianteInactivo() {
        estudiante1.setActivo(false);
        sistema.registrarEstudiante(estudiante1);
        sistema.crearCurso(curso1);

        boolean resultado = sistema.inscribirEstudiante(estudiante1, curso1);
        assertFalse(resultado);
    }

    @Test
    public void testInscribirCursoInactivo() {
        sistema.registrarEstudiante(estudiante1);
        curso1.setEstado(EstadoCurso.CANCELADO);
        sistema.crearCurso(curso1);

        boolean resultado = sistema.inscribirEstudiante(estudiante1, curso1);
        assertFalse(resultado);
    }

    @Test
    public void testInscribirSinPrerrequisitos() {
        sistema.registrarEstudiante(estudiante1);
        sistema.crearCurso(curso2); // Nivel 2 requiere nivel 1

        boolean resultado = sistema.inscribirEstudiante(estudiante1, curso2);
        assertFalse(resultado);
    }

    @Test
    public void testInscribirConPrerrequisitos() {
        sistema.registrarEstudiante(estudiante1);
        sistema.crearCurso(curso2);

        NivelAprobado nivelAprobado = new NivelAprobado(
                "NA001", TipoInstrumento.PIANO, 1,
                "2024-12-15", 4.5, curso1
        );
        estudiante1.getTheNivelesAprobados().add(nivelAprobado);

        boolean resultado = sistema.inscribirEstudiante(estudiante1, curso2);
        assertTrue(resultado);
    }

    @Test
    public void testInscribirCursoLleno() {
        sistema.registrarEstudiante(estudiante1);
        sistema.crearCurso(curso1);
        curso1.setCapacidadActual(curso1.getCapacidadMaxima());

        boolean resultado = sistema.inscribirEstudiante(estudiante1, curso1);
        assertFalse(resultado);
    }

    @Test
    public void testInscripcionDuplicada() {
        sistema.registrarEstudiante(estudiante1);
        sistema.crearCurso(curso1);

        sistema.inscribirEstudiante(estudiante1, curso1);
        boolean resultado = sistema.inscribirEstudiante(estudiante1, curso1);

        assertFalse(resultado);
        assertEquals(1, sistema.getListInscripciones().size());
    }

    @Test
    public void testCancelarInscripcion() {
        sistema.registrarEstudiante(estudiante1);
        sistema.crearCurso(curso1);
        sistema.inscribirEstudiante(estudiante1, curso1);

        String idInscripcion = sistema.getListInscripciones().get(0).getId();
        boolean resultado = sistema.cancelarInscripcion(idInscripcion);

        assertTrue(resultado);
        assertFalse(sistema.getListInscripciones().get(0).isActiva());
    }
}