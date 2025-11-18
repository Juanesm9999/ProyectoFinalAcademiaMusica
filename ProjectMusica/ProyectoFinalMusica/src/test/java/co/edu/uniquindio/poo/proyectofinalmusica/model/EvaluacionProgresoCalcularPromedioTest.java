package co.edu.uniquindio.poo.proyectofinalmusica.model;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.EstudianteController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.ClaseGrupal;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Curso;

import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.EvaluacionProgreso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
public class EvaluacionProgresoCalcularPromedioTest {

        private SistemaAcademia sistema;
        private EstudianteController estudianteController;
        private Estudiante estudiante;
        private Profesor profesor;
        private Curso curso;
        private ClaseGrupal clase;

        @BeforeEach
        void setUp() {
            sistema = new SistemaAcademia("Academia UQ", "123456789");
            estudianteController = new EstudianteController(sistema);

            // Crear estudiante
            estudiante = new Estudiante(
                    "EST001",
                    LocalDate.now(),
                    true,
                    "E001",
                    "Diego Mendoza",
                    "diego@test.com",
                    "3007777777",
                    "Avenida Test 400",
                    LocalDate.of(2004, 4, 18),
                    "diego",
                    "diego123"
            );

            // Crear profesor
            profesor = new Profesor(
                    "PROF001",
                    "Batería",
                    "2018-09-10",
                    true,
                    "P001",
                    "Sofía Ramírez",
                    "sofia@test.com",
                    "3008888888",
                    "Calle Test 500",
                    LocalDate.of(1985, 12, 3),
                    "sofia",
                    "sofia123"
            );

            // Crear curso
            curso = new Curso(
                    "C001",
                    "BAT-1",
                    "Batería Nivel 1",
                    TipoInstrumento.BATERIA,
                    1,
                    "Curso básico",
                    8,
                    0,
                    EstadoCurso.ACTIVO,
                    "2025-03-01",
                    "2025-08-31",
                    24
            );

            // Crear clase
            clase = new ClaseGrupal(
                    8,
                    0,
                    8,
                    "Clase batería",
                    "CL001",
                    "Sábado 14:00-16:00",
                    "Sábado",
                    "14:00",
                    "16:00",
                    TipoInstrumento.BATERIA,
                    1,
                    true
            );
            clase.setTheProfesor(profesor);

            estudianteController.registrarEstudiante(estudiante);
            sistema.agregarProfesor(profesor);
            sistema.crearCurso(curso);
            sistema.crearClaseGrupal(clase);
        }

        /**
         * ESCENARIO 11: Estudiante con 3 evaluaciones
         * Resultado esperado: Promedio correcto
         */
        @Test
        void testCalcularPromedio_EstudianteCon3Evaluaciones() {
            // Arrange: Crear 3 evaluaciones
            EvaluacionProgreso eval1 = new EvaluacionProgreso(
                    "EV001",
                    4.0,
                    "Buen ritmo",
                    "Mejorar coordinación",
                    "2025-04-15"
            );
            eval1.setTheEstudiante(estudiante);
            eval1.setTheCurso(curso);
            eval1.setTheClase(clase);
            eval1.setTheEvaluador(profesor);

            EvaluacionProgreso eval2 = new EvaluacionProgreso(
                    "EV002",
                    4.5,
                    "Excelente progreso",
                    "Continuar practicando",
                    "2025-05-20"
            );
            eval2.setTheEstudiante(estudiante);
            eval2.setTheCurso(curso);
            eval2.setTheClase(clase);
            eval2.setTheEvaluador(profesor);

            EvaluacionProgreso eval3 = new EvaluacionProgreso(
                    "EV003",
                    5.0,
                    "Sobresaliente",
                    "Ninguna",
                    "2025-06-25"
            );
            eval3.setTheEstudiante(estudiante);
            eval3.setTheCurso(curso);
            eval3.setTheClase(clase);
            eval3.setTheEvaluador(profesor);

            estudiante.getTheEvaluaciones().add(eval1);
            estudiante.getTheEvaluaciones().add(eval2);
            estudiante.getTheEvaluaciones().add(eval3);

            // Act: Calcular promedio usando EstudianteController
            double promedio = estudianteController.calcularPromedioEstudiante(estudiante.getId());

            // Assert
            assertEquals(4.5, promedio, 0.01, "El promedio debe ser 4.5");
            assertEquals(3, estudiante.getTheEvaluaciones().size(), "Debe haber 3 evaluaciones");
        }

        /**
         * ESCENARIO 11b: Estudiante sin evaluaciones
         * Resultado esperado: Promedio 0.0
         */
        @Test
        void testCalcularPromedio_EstudianteSinEvaluaciones() {
            // Act: Calcular promedio sin evaluaciones usando EstudianteController
            double promedio = estudianteController.calcularPromedioEstudiante(estudiante.getId());

            // Assert
            assertEquals(0.0, promedio, 0.01, "El promedio debe ser 0.0");
            assertTrue(estudiante.getTheEvaluaciones().isEmpty(), "No debe haber evaluaciones");
        }
    }

