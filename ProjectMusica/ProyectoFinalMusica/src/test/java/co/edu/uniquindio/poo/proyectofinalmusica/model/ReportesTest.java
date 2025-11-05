package co.edu.uniquindio.poo.proyectofinalmusica.model;

import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportesTest {

    private SistemaAcademia sistema;
    private Estudiante estudiante1;
    private Estudiante estudiante2;
    private Profesor profesor1;
    private Curso curso1;
    private ClaseGrupal claseGrupal;
    private Aula aula1;

    @BeforeEach
    public void setUp() {
        sistema = new SistemaAcademia("Academia UQ Música", "123456789");

        estudiante1 = new Estudiante(
                "EST001", LocalDate.now(), true, "E001",
                "Juan Pérez", "juan@email.com", "3001234567",
                "Calle 1", LocalDate.of(2000, 1, 15)
        );

        estudiante2 = new Estudiante(
                "EST002", LocalDate.now(), true, "E002",
                "María García", "maria@email.com", "3007654321",
                "Calle 2", LocalDate.of(1999, 5, 20)
        );

        profesor1 = new Profesor(
                "PROF001", "Música Clásica", "2020-01-01", true,
                "P001", "Carlos López", "carlos@email.com",
                "3009876543", "Calle 3",
                LocalDate.of(1985, 3, 10)
        );

        curso1 = new Curso(
                "C001", "PIANO-1", "Piano Nivel 1",
                TipoInstrumento.PIANO, 1, "Introducción al piano",
                20, 2, EstadoCurso.ACTIVO,
                "2025-01-15", "2025-06-15", 20
        );

        aula1 = new Aula(
                "A001", "AULA-01", "Aula Principal",
                "Edificio A", 30, true
        );

        claseGrupal = new ClaseGrupal(
                20, 2, 18, "Clase de piano",
                "CL001", "Lunes 10:00-12:00", "Lunes", "10:00", "12:00",
                TipoInstrumento.PIANO, 1, true
        );
        claseGrupal.setTheProfesor(profesor1);
        claseGrupal.setTheAula(aula1);
    }

    @Test
    public void testGenerarReporteAsistenciaNoNulo() {
        sistema.crearCurso(curso1);

        List<String> reporte = sistema.generarReporteAsistencia(curso1);
        assertNotNull(reporte, "El reporte no debería ser nulo");
    }

    @Test
    public void testGenerarReporteAsistenciaContenido() {
        sistema.registrarEstudiante(estudiante1);
        sistema.crearCurso(curso1);
        sistema.inscribirEstudiante(estudiante1, curso1);

        List<String> reporte = sistema.generarReporteAsistencia(curso1);
        assertTrue(reporte.size() > 0, "El reporte debería tener contenido");
        assertTrue(reporte.get(0).contains("REPORTE DE ASISTENCIA"));
    }

    @Test
    public void testGenerarReporteAsistenciaConDatos() {
        sistema.registrarEstudiante(estudiante1);
        sistema.crearCurso(curso1);
        sistema.inscribirEstudiante(estudiante1, curso1);

        // Registrar asistencias
        Asistencia asist1 = new Asistencia(
                "AST001", estudiante1, claseGrupal,
                LocalDate.now(), true, "Presente"
        );
        sistema.registrarAsistencia(asist1);

        List<String> reporte = sistema.generarReporteAsistencia(curso1);
        assertTrue(reporte.stream().anyMatch(linea -> linea.contains("Juan Pérez")));
    }

    @Test
    public void testGenerarReporteProgresoNoNulo() {
        List<String> reporte = sistema.generarReporteProgreso(TipoInstrumento.PIANO, 1);
        assertNotNull(reporte, "El reporte no debería ser nulo");
    }

    @Test
    public void testGenerarReporteProgresoContenido() {
        sistema.crearCurso(curso1);

        List<String> reporte = sistema.generarReporteProgreso(TipoInstrumento.PIANO, 1);
        assertTrue(reporte.size() > 0, "El reporte debería tener contenido");
        assertTrue(reporte.get(0).contains("REPORTE DE PROGRESO"));
    }

    @Test
    public void testGenerarReporteProgresoInstrumentoCorrecto() {
        sistema.crearCurso(curso1);

        List<String> reporte = sistema.generarReporteProgreso(TipoInstrumento.PIANO, 1);
        assertTrue(reporte.stream().anyMatch(linea -> linea.contains("PIANO")));
    }

    @Test
    public void testGenerarReporteProgresoConEvaluaciones() {
        sistema.registrarEstudiante(estudiante1);
        sistema.agregarProfesor(profesor1);
        sistema.crearCurso(curso1);
        sistema.inscribirEstudiante(estudiante1, curso1);

        EvaluacionProgreso eval = new EvaluacionProgreso(
                "EVAL001", 4.5, "Excelente",
                "Ninguna", "2025-06-15"
        );
        eval.setTheEstudiante(estudiante1);
        eval.setTheCurso(curso1);
        eval.setTheEvaluador(profesor1);
        sistema.registrarEvaluacion(eval);

        List<String> reporte = sistema.generarReporteProgreso(TipoInstrumento.PIANO, 1);
        assertTrue(reporte.stream().anyMatch(linea -> linea.contains("4.5")));
    }

    @Test
    public void testGenerarReporteOcupacionAulasNoNulo() {
        List<String> reporte = sistema.generarReporteOcupacionAulas();
        assertNotNull(reporte, "El reporte no debería ser nulo");
    }

    @Test
    public void testGenerarReporteOcupacionAulasContenido() {
        sistema.getListAulas().add(aula1);

        List<String> reporte = sistema.generarReporteOcupacionAulas();
        assertTrue(reporte.size() > 0, "El reporte debería tener contenido");
        assertTrue(reporte.get(0).contains("OCUPACIÓN DE AULAS"));
    }

    @Test
    public void testGenerarReporteOcupacionAulasConClases() {
        sistema.getListAulas().add(aula1);
        sistema.crearClaseGrupal(claseGrupal);

        List<String> reporte = sistema.generarReporteOcupacionAulas();
        assertTrue(reporte.stream().anyMatch(linea -> linea.contains("Aula Principal")));
    }

    @Test
    public void testGenerarReporteCargaDocenteNoNulo() {
        List<String> reporte = sistema.generarReporteCargaDocente();
        assertNotNull(reporte, "El reporte no debería ser nulo");
    }

    @Test
    public void testGenerarReporteCargaDocenteContenido() {
        sistema.agregarProfesor(profesor1);

        List<String> reporte = sistema.generarReporteCargaDocente();
        assertTrue(reporte.size() > 0, "El reporte debería tener contenido");
        assertTrue(reporte.get(0).contains("CARGA DOCENTE"));
    }

    @Test
    public void testGenerarReporteCargaDocenteConClases() {
        sistema.agregarProfesor(profesor1);
        sistema.crearClaseGrupal(claseGrupal);

        List<String> reporte = sistema.generarReporteCargaDocente();
        assertTrue(reporte.stream().anyMatch(linea -> linea.contains("Carlos López")));
    }

    @Test
    public void testGenerarReporteEstudianteNoNulo() {
        sistema.registrarEstudiante(estudiante1);

        List<String> reporte = sistema.generarReporteEstudiante(estudiante1);
        assertNotNull(reporte, "El reporte no debería ser nulo");
    }

    @Test
    public void testGenerarReporteEstudianteContenido() {
        sistema.registrarEstudiante(estudiante1);

        List<String> reporte = sistema.generarReporteEstudiante(estudiante1);
        assertTrue(reporte.size() > 0, "El reporte debería tener contenido");
        assertTrue(reporte.get(0).contains("REPORTE DEL ESTUDIANTE"));
    }

    @Test
    public void testGenerarReporteEstudianteDatosCorrectos() {
        sistema.registrarEstudiante(estudiante1);

        List<String> reporte = sistema.generarReporteEstudiante(estudiante1);
        assertTrue(reporte.stream().anyMatch(linea -> linea.contains("Juan Pérez")));
        assertTrue(reporte.stream().anyMatch(linea -> linea.contains("EST001")));
        assertTrue(reporte.stream().anyMatch(linea -> linea.contains("juan@email.com")));
    }

    @Test
    public void testGenerarReporteEstudianteConInscripciones() {
        sistema.registrarEstudiante(estudiante1);
        sistema.crearCurso(curso1);
        sistema.inscribirEstudiante(estudiante1, curso1);

        List<String> reporte = sistema.generarReporteEstudiante(estudiante1);
        assertTrue(reporte.stream().anyMatch(linea -> linea.contains("CURSOS INSCRITOS")));
        assertTrue(reporte.stream().anyMatch(linea -> linea.contains("Piano Nivel 1")));
    }

    @Test
    public void testGenerarReporteEstudianteConNivelesAprobados() {
        sistema.registrarEstudiante(estudiante1);

        NivelAprobado nivel = new NivelAprobado(
                "NA001", TipoInstrumento.PIANO, 1,
                "2024-12-15", 4.5, curso1
        );
        estudiante1.getTheNivelesAprobados().add(nivel);

        List<String> reporte = sistema.generarReporteEstudiante(estudiante1);
        assertTrue(reporte.stream().anyMatch(linea -> linea.contains("NIVELES APROBADOS")));
        assertTrue(reporte.stream().anyMatch(linea -> linea.contains("PIANO")));
    }
}