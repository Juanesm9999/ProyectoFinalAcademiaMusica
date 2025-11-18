package Controller;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.EstudianteController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para EstudianteController basados en tabla de escenarios
 */
public class EstudianteControllerTest {

    private SistemaAcademia sistema;
    private EstudianteController controller;
    private Estudiante estudiante;
    private Curso cursoNivel1;
    private Curso cursoNivel2;
    private Aula aula;

    @BeforeEach
    void setUp() {
        sistema = new SistemaAcademia("Academia UQ", "123456789");
        controller = new EstudianteController(sistema);

        // Crear estudiante
        estudiante = new Estudiante(
                "EST2025001",
                LocalDate.now(),
                true,
                "E001",
                "Pedro Martínez",
                "pedro@test.com",
                "3001111111",
                "Calle Test 123",
                LocalDate.of(2002, 3, 15),
                "pedro",
                "pedro123"
        );

        // Crear aula
        aula = new Aula("A001", "AU-101", "Aula Test", "Piso 1", 15, true);
        sistema.getListAulas().add(aula);

        // Crear cursos
        cursoNivel1 = new Curso(
                "C001",
                "GIT-1",
                "Guitarra Nivel 1",
                TipoInstrumento.GUITARRA,
                1,
                "Curso básico",
                10,
                5,
                EstadoCurso.ACTIVO,
                "2025-01-01",
                "2025-06-30",
                24
        );

        cursoNivel2 = new Curso(
                "C002",
                "GIT-2",
                "Guitarra Nivel 2",
                TipoInstrumento.GUITARRA,
                2,
                "Curso intermedio",
                10,
                0,
                EstadoCurso.ACTIVO,
                "2025-07-01",
                "2025-12-31",
                24
        );

        controller.registrarEstudiante(estudiante);
        sistema.crearCurso(cursoNivel1);
        sistema.crearCurso(cursoNivel2);
    }

    /**
     * ESCENARIO 1: Estudiante cumple prerrequisitos y hay cupo
     * Resultado esperado: Inscripción exitosa
     */
    @Test
    void testInscribirCurso_EstudianteCumplePrerrequisitosYHayCupo() {
        // Arrange: Estudiante tiene nivel previo aprobado
        NivelAprobado nivelPrevio = new NivelAprobado(
                "NA001",
                TipoInstrumento.GUITARRA,
                1,
                "2024-12-15",
                4.0,
                cursoNivel1
        );
        estudiante.getTheNivelesAprobados().add(nivelPrevio);

        // Act: Usar método inscribirACurso del controller
        boolean resultado = controller.inscribirACurso(estudiante.getId(), cursoNivel2.getId());

        // Assert
        assertTrue(resultado, "La inscripción debería ser exitosa");
        assertFalse(estudiante.getTheInscripciones().isEmpty(), "Debe haber al menos una inscripción");
    }

    /**
     * ESCENARIO 2: Estudiante NO cumple prerrequisitos
     * Resultado esperado: Inscripción rechazada
     */
    @Test
    void testInscribirCurso_EstudianteNoCumplePrerrequisitos() {
        // Act: Estudiante NO tiene nivel 1 aprobado para inscribirse en nivel 2
        boolean resultado = controller.inscribirACurso(estudiante.getId(), cursoNivel2.getId());

        // Assert
        assertFalse(resultado, "La inscripción debería ser rechazada por falta de prerrequisitos");
    }

    /**
     * ESCENARIO 3: Curso lleno (sin cupos)
     * Resultado esperado: Inscripción rechazada
     */
    @Test
    void testInscribirCurso_CursoLlenoSinCupos() {
        // Arrange: Curso nivel 1 lleno
        cursoNivel1.setCapacidadActual(cursoNivel1.getCapacidadMaxima());

        // Act
        boolean resultado = controller.inscribirACurso(estudiante.getId(), cursoNivel1.getId());

        // Assert
        assertFalse(resultado, "La inscripción debería ser rechazada porque el curso está lleno");
    }
}
