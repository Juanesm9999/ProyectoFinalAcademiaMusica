/* package co.edu.uniquindio.poo.proyectofinalmusica.model;

import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.ClaseGrupal;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Curso;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.EvaluacionProgreso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EvaluacionTest {
    private SistemaAcademia sistema;
    private Estudiante estudiante1;
    private Estudiante estudiante2;
    private Profesor profesor1;
    private Curso curso1;
    private ClaseGrupal claseGrupal;
    private EvaluacionProgreso evaluacion1;
    private EvaluacionProgreso evaluacion2;

    @BeforeEach
    public void setUp() {
        sistema = new SistemaAcademia("Academia UQ Música", "123456789");

        // Crear estudiantes
        estudiante1 = new Estudiante(
                "EST001",
                LocalDate.now(),
                true,
                "E001",
                "Juan Pérez",
                "juan@email.com",
                "3001234567",
                "Calle 1",
                LocalDate.of(2000, 1, 15),
                "juan",
                "juan123"
        );

        estudiante2 = new Estudiante(
                "EST002",
                LocalDate.now(),
                true,
                "E002",
                "María García",
                "maria@email.com",
                "3007654321",
                "Calle 2",
                LocalDate.of(1999, 5, 20),
                "maria",
                "maria123"
        );

        // Crear profesor
        profesor1 = new Profesor(
                "PROF001",
                "Música Clásica",
                "2020-01-01",
                true,
                "P001",
                "Carlos López",
                "carlos@email.com",
                "3009876543",
                "Calle 3",
                LocalDate.of(1985, 3, 10),
                "carlos",
                "carlos123"
        );

        // Crear curso
        curso1 = new Curso(
                "C001",
                "PIANO-1",
                "Piano Nivel 1",
                TipoInstrumento.PIANO,
                1,
                "Introducción al piano",
                20,
                0,
                EstadoCurso.ACTIVO,
                "2025-01-15",
                "2025-06-15",
                20
        );

        // Crear clase
        claseGrupal = new ClaseGrupal(
                20,
                0,
                20,
                "Clase de piano",
                "CL001",
                "Lunes 10:00-12:00",
                "Lunes",
                "10:00",
                "12:00",
                TipoInstrumento.PIANO,
                1,
                true
        );

        // Crear evaluaciones
        evaluacion1 = new EvaluacionProgreso(
                "EVAL001",
                4.5,
                "Excelente progreso en la técnica",
                "Mejorar el uso del pedal",
                "2025-06-15"
        );
        evaluacion1.setTheEstudiante(estudiante1);
        evaluacion1.setTheCurso(curso1);
        evaluacion1.setTheClase(claseGrupal);
        evaluacion1.setTheEvaluador(profesor1);

        evaluacion2 = new EvaluacionProgreso(
                "EVAL002",
                3.8,
                "Buen desempeño general",
                "Practicar más ejercicios de digitación",
                "2025-06-15"
        );
        evaluacion2.setTheEstudiante(estudiante2);
        evaluacion2.setTheCurso(curso1);
        evaluacion2.setTheClase(claseGrupal);
        evaluacion2.setTheEvaluador(profesor1);
    }

    @Test
    public void testRegistrarEvaluacion() {
        sistema.registrarEstudiante(estudiante1);
        sistema.agregarProfesor(profesor1);
        sistema.crearCurso(curso1);

        boolean resultado = sistema.registrarEvaluacion(evaluacion1);

        assertTrue(resultado, "La evaluación debería registrarse correctamente");
        assertEquals(1, sistema.getListEvaluaciones().size());
    }

    @Test
    public void testRegistrarEvaluacionConCalificacion() {
        sistema.registrarEstudiante(estudiante1);
        sistema.agregarProfesor(profesor1);
        sistema.crearCurso(curso1);

        sistema.registrarEvaluacion(evaluacion1);

        assertEquals(4.5, evaluacion1.getCalificacion());
    }

    @Test
    public void testRegistrarEvaluacionConComentarios() {
        sistema.registrarEstudiante(estudiante1);
        sistema.agregarProfesor(profesor1);
        sistema.crearCurso(curso1);

        sistema.registrarEvaluacion(evaluacion1);

        assertEquals("Excelente progreso en la técnica", evaluacion1.getComentarios());
        assertEquals("Mejorar el uso del pedal", evaluacion1.getAreasAMejorar());
    }

    @Test
    public void testRegistrarMultiplesEvaluaciones() {
        sistema.registrarEstudiante(estudiante1);
        sistema.registrarEstudiante(estudiante2);
        sistema.agregarProfesor(profesor1);
        sistema.crearCurso(curso1);

        sistema.registrarEvaluacion(evaluacion1);
        sistema.registrarEvaluacion(evaluacion2);

        assertEquals(2, sistema.getListEvaluaciones().size());
    }

    @Test
    public void testRegistrarEvaluacionDuplicada() {
        sistema.registrarEstudiante(estudiante1);
        sistema.agregarProfesor(profesor1);
        sistema.crearCurso(curso1);

        sistema.registrarEvaluacion(evaluacion1);
        boolean resultado = sistema.registrarEvaluacion(evaluacion1);

        assertFalse(resultado, "No debería permitir evaluación duplicada");
        assertEquals(1, sistema.getListEvaluaciones().size());
    }

    @Test
    public void testVerificarEvaluacionExiste() {
        sistema.registrarEstudiante(estudiante1);
        sistema.agregarProfesor(profesor1);
        sistema.crearCurso(curso1);
        sistema.registrarEvaluacion(evaluacion1);

        assertTrue(sistema.verificarEvaluacionProgreso("EVAL001"));
    }

    @Test
    public void testVerificarEvaluacionNoExiste() {
        assertFalse(sistema.verificarEvaluacionProgreso("EVAL999"));
    }

    @Test
    public void testEvaluacionRelacionEstudianteProfesor() {
        sistema.registrarEstudiante(estudiante1);
        sistema.agregarProfesor(profesor1);
        sistema.crearCurso(curso1);
        sistema.registrarEvaluacion(evaluacion1);

        assertEquals(estudiante1, evaluacion1.getTheEstudiante());
        assertEquals(profesor1, evaluacion1.getTheEvaluador());
    }

    @Test
    public void testEvaluacionRelacionCursoClase() {
        sistema.registrarEstudiante(estudiante1);
        sistema.agregarProfesor(profesor1);
        sistema.crearCurso(curso1);
        sistema.crearClaseGrupal(claseGrupal);
        sistema.registrarEvaluacion(evaluacion1);

        assertEquals(curso1, evaluacion1.getTheCurso());
        assertEquals(claseGrupal, evaluacion1.getTheClase());
    }

    @Test
    public void testEvaluacionCalificacionAprobatoria() {
        sistema.registrarEstudiante(estudiante1);
        sistema.agregarProfesor(profesor1);
        sistema.crearCurso(curso1);

        evaluacion1.setCalificacion(4.5);
        sistema.registrarEvaluacion(evaluacion1);

        assertTrue(evaluacion1.getCalificacion() >= 3.0, "Calificación aprobatoria");
    }

    @Test
    public void testEvaluacionCalificacionReprobatoria() {
        sistema.registrarEstudiante(estudiante2);
        sistema.agregarProfesor(profesor1);
        sistema.crearCurso(curso1);

        EvaluacionProgreso evaluacionBaja = new EvaluacionProgreso(
                "EVAL003",
                2.5,
                "Necesita mejorar",
                "Practicar más",
                "2025-06-15"
        );
        evaluacionBaja.setTheEstudiante(estudiante2);
        evaluacionBaja.setTheCurso(curso1);
        evaluacionBaja.setTheEvaluador(profesor1);

        sistema.registrarEvaluacion(evaluacionBaja);

        assertTrue(evaluacionBaja.getCalificacion() < 3.0, "Calificación reprobatoria");
    }

    @Test
    public void testEvaluacionFecha() {
        sistema.registrarEstudiante(estudiante1);
        sistema.agregarProfesor(profesor1);
        sistema.crearCurso(curso1);

        sistema.registrarEvaluacion(evaluacion1);

        assertEquals("2025-06-15", evaluacion1.getFecha());
    }
} */ //