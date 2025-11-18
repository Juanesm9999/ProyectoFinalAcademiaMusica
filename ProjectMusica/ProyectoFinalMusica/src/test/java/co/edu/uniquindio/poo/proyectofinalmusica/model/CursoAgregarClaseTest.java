package co.edu.uniquindio.poo.proyectofinalmusica.model;


import co.edu.uniquindio.poo.proyectofinalmusica.Controller.ClaseController;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.CursoController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CursoAgregarClaseTest {private SistemaAcademia sistema;
    private CursoController cursoController;
    private ClaseController claseController;
    private Curso curso;
    private Aula aula;
    private Profesor profesor;

    @BeforeEach
    void setUp() {
        sistema = new SistemaAcademia("Academia UQ", "123456789");
        cursoController = new CursoController(sistema);
        claseController = new ClaseController(sistema);

        // Crear curso
        curso = new Curso(
                "C001",
                "VIO-1",
                "Violín Nivel 1",
                TipoInstrumento.VIOLIN,
                1,
                "Curso básico de violín",
                12,
                0,
                EstadoCurso.ACTIVO,
                "2025-02-01",
                "2025-07-31",
                24
        );

        // Crear aula
        aula = new Aula("A001", "AU-401", "Aula Violín", "Piso 4", 15, true);

        // Crear profesor
        profesor = new Profesor(
                "PROF001",
                "Violín",
                "2021-03-01",
                true,
                "P001",
                "Felipe Vargas",
                "felipe@test.com",
                "3005555555",
                "Diagonal Test 200",
                LocalDate.of(1992, 2, 28),
                "felipe",
                "felipe123"
        );

        cursoController.crearCurso(curso);
        sistema.getListAulas().add(aula);
        sistema.agregarProfesor(profesor);
    }

    /**
     * ESCENARIO 9: Agregar clase válida
     * Resultado esperado: Clase agregada
     */
    @Test
    void testAgregarClase_ClaseValida() {
        // Arrange
        ClaseGrupal claseValida = new ClaseGrupal(
                12,
                0,
                12,
                "Clase de violín",
                "CL001",
                "Viernes 10:00-12:00",
                "Viernes",
                "10:00",
                "12:00",
                TipoInstrumento.VIOLIN,
                1,
                true
        );
        claseValida.setTheProfesor(profesor);
        claseValida.setTheAula(aula);

        // Act: Agregar clase al curso usando CursoController
        boolean agregada = cursoController.agregarClaseACurso(curso.getId(), claseValida);
        claseController.crearClaseGrupal(claseValida);

        // Assert
        assertTrue(agregada, "La clase debería agregarse al curso");
        assertFalse(curso.getTheClases().isEmpty(), "El curso debe tener clases");
        assertEquals(1, curso.getTheClases().size(), "Debe haber 1 clase en el curso");
    }

}

