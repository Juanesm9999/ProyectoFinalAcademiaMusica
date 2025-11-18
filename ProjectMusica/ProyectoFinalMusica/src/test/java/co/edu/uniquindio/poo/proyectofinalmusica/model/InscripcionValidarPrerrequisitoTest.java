package co.edu.uniquindio.poo.proyectofinalmusica.model;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.InscripcionController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Curso;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.NivelAprobado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class InscripcionValidarPrerrequisitoTest {

    private SistemaAcademia sistema;
    private InscripcionController controller;
    private Estudiante estudiante;
    private Curso cursoNivel2;
    private Curso cursoNivel1;

    @BeforeEach
    void setUp() {
        sistema = new SistemaAcademia("Academia UQ", "123456789");
        controller = new InscripcionController(sistema);

        // Crear estudiante
        estudiante = new Estudiante(
                "EST001",
                LocalDate.now(),
                true,
                "E001",
                "Camila Suárez",
                "camila@test.com",
                "3006666666",
                "Transversal Test 300",
                LocalDate.of(2001, 7, 12),
                "camila",
                "camila123"
        );

        // Crear cursos
        cursoNivel1 = new Curso(
                "C001",
                "SAX-1",
                "Saxofón Nivel 1",
                TipoInstrumento.SAXOFON,
                1,
                "Curso básico",
                10,
                0,
                EstadoCurso.ACTIVO,
                "2025-01-15",
                "2025-06-30",
                24
        );

        cursoNivel2 = new Curso(
                "C002",
                "SAX-2",
                "Saxofón Nivel 2",
                TipoInstrumento.SAXOFON,
                2,
                "Curso intermedio",
                10,
                0,
                EstadoCurso.ACTIVO,
                "2025-07-15",
                "2025-12-31",
                24
        );

        sistema.registrarEstudiante(estudiante);
        sistema.crearCurso(cursoNivel1);
        sistema.crearCurso(cursoNivel2);
    }

    /**
     * ESCENARIO 10: Estudiante con nivel previo aprobado
     * Resultado esperado: Validación exitosa
     */
    @Test
    void testValidarPrerrequisitos_EstudianteConNivelPrevioAprobado() {
        // Arrange: Agregar nivel aprobado
        NivelAprobado nivelAprobado = new NivelAprobado(
                "NA001",
                TipoInstrumento.SAXOFON,
                1,
                "2025-06-25",
                4.2,
                cursoNivel1
        );
        estudiante.getTheNivelesAprobados().add(nivelAprobado);

        // Act: Intentar inscribir usando InscripcionController
        boolean resultado = controller.inscribirEstudiante(estudiante, cursoNivel2);

        // Assert
        assertTrue(resultado, "El estudiante debería poder inscribirse cumpliendo prerrequisitos");
    }

    /**
     * ESCENARIO 10b: Estudiante SIN nivel previo aprobado
     * Resultado esperado: Validación fallida
     */
    @Test
    void testValidarPrerrequisitos_EstudianteSinNivelPrevio() {
        // Act: Intentar inscribir sin nivel previo
        boolean resultado = controller.inscribirEstudiante(estudiante, cursoNivel2);

        // Assert
        assertFalse(resultado, "El estudiante NO debería poder inscribirse sin prerrequisitos");
    }

}

