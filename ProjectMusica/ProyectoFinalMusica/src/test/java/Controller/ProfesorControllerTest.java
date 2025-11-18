package Controller;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.ClaseController;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.ProfesorController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class ProfesorControllerTest {

    private SistemaAcademia sistema;
    private ProfesorController controller;
    private ClaseController claseController;
    private Profesor profesor;
    private Aula aula1;
    private Aula aula2;
    private ClaseGrupal claseExistente;

    @BeforeEach
    void setUp() {
        sistema = new SistemaAcademia("Academia UQ", "123456789");
        controller = new ProfesorController(sistema);
        claseController = new ClaseController(sistema);

        // Crear profesor
        profesor = new Profesor(
                "PROF001",
                "Guitarra",
                "2020-01-15",
                true,
                "P001",
                "Roberto Sánchez",
                "roberto@test.com",
                "3002222222",
                "Avenida Test 456",
                LocalDate.of(1980, 5, 10),
                "roberto",
                "roberto123"
        );
        profesor.getTheInstrumentosImpartidos().add(TipoInstrumento.GUITARRA);

        // Crear aulas
        aula1 = new Aula("A001", "AU-201", "Aula Guitarra 1", "Piso 2", 20, true);
        aula2 = new Aula("A002", "AU-202", "Aula Guitarra 2", "Piso 2", 15, true);

        // Crear clase existente (para pruebas de conflicto)
        claseExistente = new ClaseGrupal(
                15,
                0,
                15,
                "Clase existente",
                "CL001",
                "Lunes 10:00-12:00",
                "Lunes",
                "10:00",
                "12:00",
                TipoInstrumento.GUITARRA,
                1,
                true
        );
        claseExistente.setTheProfesor(profesor);
        claseExistente.setTheAula(aula1);

        controller.agregarProfesor(profesor);
        sistema.getListAulas().add(aula1);
        sistema.getListAulas().add(aula2);
        claseController.crearClaseGrupal(claseExistente);
    }

    /**
     * ESCENARIO 4: Aula y profesor disponibles
     * Resultado esperado: Clase creada
     */
    @Test
    void testCrearClase_AulaYProfesorDisponibles() {
        // Arrange: Nueva clase en horario diferente y aula diferente
        ClaseGrupal nuevaClase = new ClaseGrupal(
                12,
                0,
                12,
                "Clase nueva",
                "CL002",
                "Miércoles 14:00-16:00",
                "Miércoles",
                "14:00",
                "16:00",
                TipoInstrumento.GUITARRA,
                1,
                true
        );
        nuevaClase.setTheProfesor(profesor);
        nuevaClase.setTheAula(aula2);

        // Act
        boolean resultado = claseController.crearClaseGrupal(nuevaClase);

        // Assert
        assertTrue(resultado, "La clase debería crearse exitosamente");
        assertEquals(2, sistema.getListClases().size(), "Debe haber 2 clases en el sistema");
    }

    /**
     * ESCENARIO 5: Conflicto de horario (aula ocupada)
     * Resultado esperado: Clase rechazada
     */
    @Test
    void testCrearClase_ConflictoHorarioAulaOcupada() {
        // Arrange: Intentar crear clase en mismo horario y aula
        ClaseGrupal claseConflicto = new ClaseGrupal(
                10,
                0,
                10,
                "Clase con conflicto",
                "CL003",
                "Lunes 10:00-12:00",
                "Lunes",
                "10:00",
                "12:00",
                TipoInstrumento.GUITARRA,
                1,
                true
        );
        claseConflicto.setTheProfesor(profesor);
        claseConflicto.setTheAula(aula1);

        // Act: Verificar conflicto de aula y horario
        boolean hayConflicto = claseController.verificarConflictoAulaHorario(
                aula1,
                "Lunes",
                "10:00",
                "12:00"
        );

        // Assert
        assertTrue(hayConflicto, "Debería detectar conflicto de aula");
    }

    /**
     * ESCENARIO 6: Conflicto de horario (profesor ocupado)
     * Resultado esperado: Clase rechazada
     */
    @Test
    void testCrearClase_ConflictoHorarioProfesorOcupado() {
        // Arrange: Intentar crear clase con mismo profesor en mismo horario
        ClaseGrupal claseConflicto = new ClaseGrupal(
                10,
                0,
                10,
                "Clase con conflicto profesor",
                "CL004",
                "Lunes 10:00-12:00",
                "Lunes",
                "10:00",
                "12:00",
                TipoInstrumento.GUITARRA,
                1,
                true
        );
        claseConflicto.setTheProfesor(profesor);
        claseConflicto.setTheAula(aula2);

        // Act: Verificar conflicto de horario del profesor
        boolean hayConflicto = sistema.verificarConflictoHorario(profesor, "Lunes 10:00-12:00");

        // Assert
        assertTrue(hayConflicto, "Debería detectar que el profesor está ocupado");
    }

    /**
     * ESCENARIO 7: Estudiante inscrito en la clase
     * Resultado esperado: Asistencia registrada
     */
    @Test
    void testRegistrarAsistencia_EstudianteInscritoEnClase() {
        // Arrange: Crear estudiante e inscribirlo en la clase
        Estudiante estudiante = new Estudiante(
                "EST001",
                LocalDate.now(),
                true,
                "E001",
                "Ana Torres",
                "ana@test.com",
                "3003333333",
                "Calle Estudiante 789",
                LocalDate.of(2003, 8, 20),
                "ana",
                "ana123"
        );
        claseExistente.getTheEstudiantesInscritos().add(estudiante);
        sistema.registrarEstudiante(estudiante);

        // Act: Usar método de clase para registrar asistencia
        claseExistente.registrarAsistencia(estudiante, true);

        // Assert
        assertFalse(estudiante.getTheHistorialAsistencia().isEmpty(), "Debe haber al menos 1 asistencia");
    }
}