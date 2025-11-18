package Controller;

import co.edu.uniquindio.poo.proyectofinalmusica.Controller.AdministradorController;
import co.edu.uniquindio.poo.proyectofinalmusica.Controller.ClaseController;
import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AdminControllerTest {


        private SistemaAcademia sistema;
        private AdministradorController controller;
        private ClaseController claseController;
        private Aula aula1;
        private Aula aula2;
        private Profesor profesor;
        private ClaseGrupal clase1;
        private ClaseGrupal clase2;

        @BeforeEach
        void setUp() {
            sistema = new SistemaAcademia("Academia UQ", "123456789");
            controller = new AdministradorController(sistema);
            claseController = new ClaseController(sistema);

            // Crear aulas
            aula1 = new Aula("A001", "AU-301", "Aula Test 1", "Piso 3", 25, true);
            aula2 = new Aula("A002", "AU-302", "Aula Test 2", "Piso 3", 20, true);

            // Crear profesor
            profesor = new Profesor(
                    "PROF001",
                    "Piano",
                    "2019-06-01",
                    true,
                    "P001",
                    "Daniela Rojas",
                    "daniela@test.com",
                    "3004444444",
                    "Carrera Test 100",
                    LocalDate.of(1987, 11, 5),
                    "daniela",
                    "daniela123"
            );

            // Crear clases
            clase1 = new ClaseGrupal(
                    20,
                    0,
                    20,
                    "Clase Piano A",
                    "CL001",
                    "Martes 09:00-11:00",
                    "Martes",
                    "09:00",
                    "11:00",
                    TipoInstrumento.PIANO,
                    1,
                    true
            );
            clase1.setTheProfesor(profesor);
            clase1.setTheAula(aula1);

            clase2 = new ClaseGrupal(
                    15,
                    0,
                    15,
                    "Clase Piano B",
                    "CL002",
                    "Martes 09:00-11:00",
                    "Martes",
                    "09:00",
                    "11:00",
                    TipoInstrumento.PIANO,
                    1,
                    true
            );
            clase2.setTheProfesor(profesor);
            clase2.setTheAula(aula1);

            sistema.getListAulas().add(aula1);
            sistema.getListAulas().add(aula2);
            sistema.agregarProfesor(profesor);
        }

        /**
         * ESCENARIO 8: Dos clases en misma aula/hora
         * Resultado esperado: Conflicto detectado
         */
        @Test
        void testValidarConflictosHorario_DosClasesEnMismaAulaYHora() {
            // Arrange: Agregar primera clase
            sistema.crearClaseGrupal(clase1);

            // Act: Verificar conflicto usando ClaseController
            boolean hayConflicto = claseController.verificarConflictoAulaHorario(
                    aula1,
                    "Martes",
                    "09:00",
                    "11:00"
            );

            // Assert
            assertTrue(hayConflicto, "Debería detectar conflicto de horario en el aula");
        }

        /**
         * ESCENARIO 8b: Dos clases sin conflicto (diferente aula/hora)
         * Resultado esperado: Sin conflicto
         */
        @Test
        void testValidarConflictosHorario_SinConflicto() {
            // Arrange: Modificar clase2 para que no tenga conflicto
            clase2.setDiaSemana("Jueves");
            clase2.setHorario("Jueves 15:00-17:00");
            clase2.setTheAula(aula2);
            sistema.crearClaseGrupal(clase1);

            // Act
            boolean hayConflicto = claseController.verificarConflictoAulaHorario(
                    aula2,
                    "Jueves",
                    "15:00",
                    "17:00"
            );

            // Assert
            assertFalse(hayConflicto, "No debería detectar conflicto");
        }
}

