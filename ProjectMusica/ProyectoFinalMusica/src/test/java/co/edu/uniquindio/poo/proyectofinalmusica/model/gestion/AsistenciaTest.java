package co.edu.uniquindio.poo.proyectofinalmusica.model.gestion;


import co.edu.uniquindio.poo.proyectofinalmusica.model.Estudiante;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Profesor;
import co.edu.uniquindio.poo.proyectofinalmusica.model.SistemaAcademia;
import co.edu.uniquindio.poo.proyectofinalmusica.model.TipoInstrumento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AsistenciaTest {

        private SistemaAcademia sistema;
        private Estudiante estudiante1;
        private Estudiante estudiante2;
        private Profesor profesor1;
        private ClaseGrupal claseGrupal;
        private ClaseIndividual claseIndividual;
        private Asistencia asistencia1;
        private Asistencia asistencia2;

        @BeforeEach
        public void setUp() {
            sistema = new SistemaAcademia("Academia UQ Música", "123456789");

            // Crear estudiantes
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

            // Crear profesor
            profesor1 = new Profesor(
                    "PROF001", "Música Clásica", "2020-01-01", true,
                    "P001", "Carlos López", "carlos@email.com",
                    "3009876543", "Calle 3",
                    LocalDate.of(1985, 3, 10)
            );

            // Crear clase grupal
            claseGrupal = new ClaseGrupal(
                    20, 0, 20, "Clase de piano grupal",
                    "CL001", "Lunes 10:00-12:00", "Lunes", "10:00", "12:00",
                    TipoInstrumento.PIANO, 1, true
            );
            claseGrupal.setTheProfesor(profesor1);

            // Crear clase individual
            claseIndividual = new ClaseIndividual(
                    "Técnica avanzada", "Mejorar digitación", "Clase personalizada",
                    "CL002", "Martes 14:00-15:00", "Martes", "14:00", "15:00",
                    TipoInstrumento.VIOLIN, 2, true
            );
            claseIndividual.setTheEstudiante(estudiante1);
            claseIndividual.setTheProfesor(profesor1);

            // Crear asistencias
            asistencia1 = new Asistencia(
                    "AST001",
                    estudiante1,
                    claseGrupal,
                    LocalDate.now(),
                    true,
                    "Asistencia completa"
            );

            asistencia2 = new Asistencia(
                    "AST002",
                    estudiante2,
                    claseGrupal,
                    LocalDate.now(),
                    false,
                    "Falta justificada por enfermedad"
            );
        }

        @Test
        public void testRegistrarAsistenciaPresente() {
            sistema.registrarEstudiante(estudiante1);
            sistema.crearClaseGrupal(claseGrupal);

            boolean resultado = sistema.registrarAsistencia(asistencia1);

            assertTrue(resultado, "La asistencia debería registrarse correctamente");
            assertEquals(1, sistema.getListAsistencias().size());
            assertTrue(asistencia1.isPresente(), "El estudiante debería estar presente");
        }

        @Test
        public void testRegistrarAsistenciaAusente() {
            sistema.registrarEstudiante(estudiante2);
            sistema.crearClaseGrupal(claseGrupal);

            boolean resultado = sistema.registrarAsistencia(asistencia2);

            assertTrue(resultado, "La asistencia debería registrarse correctamente");
            assertFalse(asistencia2.isPresente(), "El estudiante debería estar ausente");
            assertEquals("Falta justificada por enfermedad", asistencia2.getObservaciones());
        }

        @Test
        public void testRegistrarMultiplesAsistencias() {
            sistema.registrarEstudiante(estudiante1);
            sistema.registrarEstudiante(estudiante2);
            sistema.crearClaseGrupal(claseGrupal);

            sistema.registrarAsistencia(asistencia1);
            sistema.registrarAsistencia(asistencia2);

            assertEquals(2, sistema.getListAsistencias().size());
        }

        @Test
        public void testRegistrarAsistenciaDuplicada() {
            sistema.registrarEstudiante(estudiante1);
            sistema.crearClaseGrupal(claseGrupal);

            sistema.registrarAsistencia(asistencia1);
            boolean resultado = sistema.registrarAsistencia(asistencia1);

            assertFalse(resultado, "No debería permitir asistencia duplicada");
            assertEquals(1, sistema.getListAsistencias().size());
        }

        @Test
        public void testVerificarAsistenciaExiste() {
            sistema.registrarEstudiante(estudiante1);
            sistema.crearClaseGrupal(claseGrupal);
            sistema.registrarAsistencia(asistencia1);

            assertTrue(sistema.verificarAsistencia("AST001"));
        }

        @Test
        public void testVerificarAsistenciaNoExiste() {
            assertFalse(sistema.verificarAsistencia("AST999"));
        }

        @Test
        public void testAsistenciaClaseIndividual() {
            sistema.registrarEstudiante(estudiante1);
            sistema.crearClaseIndividual(claseIndividual);

            Asistencia asistenciaIndividual = new Asistencia(
                    "AST003",
                    estudiante1,
                    claseIndividual,
                    LocalDate.now(),
                    true,
                    "Clase individual completada"
            );

            boolean resultado = sistema.registrarAsistencia(asistenciaIndividual);
            assertTrue(resultado);
            assertEquals(estudiante1, asistenciaIndividual.getTheEstudiante());
        }

        @Test
        public void testAsistenciaConObservaciones() {
            sistema.registrarEstudiante(estudiante1);
            sistema.crearClaseGrupal(claseGrupal);

            Asistencia asistenciaConObs = new Asistencia(
                    "AST004",
                    estudiante1,
                    claseGrupal,
                    LocalDate.now(),
                    true,
                    "Llegó 10 minutos tarde"
            );

            sistema.registrarAsistencia(asistenciaConObs);

            assertEquals("Llegó 10 minutos tarde", asistenciaConObs.getObservaciones());
        }

        @Test
        public void testAsistenciaFechaCorrecta() {
            sistema.registrarEstudiante(estudiante1);
            sistema.crearClaseGrupal(claseGrupal);

            LocalDate fechaEsperada = LocalDate.now();
            sistema.registrarAsistencia(asistencia1);

            assertEquals(fechaEsperada, asistencia1.getFecha());
        }

        @Test
        public void testAsistenciaRelacionEstudianteClase() {
            sistema.registrarEstudiante(estudiante1);
            sistema.crearClaseGrupal(claseGrupal);
            sistema.registrarAsistencia(asistencia1);

            assertEquals(estudiante1, asistencia1.getTheEstudiante());
            assertEquals(claseGrupal, asistencia1.getTheClase());
        }

}

