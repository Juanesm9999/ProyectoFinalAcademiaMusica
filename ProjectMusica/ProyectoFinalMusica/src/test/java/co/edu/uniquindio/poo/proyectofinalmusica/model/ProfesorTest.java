package co.edu.uniquindio.poo.proyectofinalmusica.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProfesorTest {
    private SistemaAcademia sistema;
    private Profesor profesor1;
    private Profesor profesor2;

    @BeforeEach
    public void setUp() {
        sistema = new SistemaAcademia("Academia UQ Música", "123456789");

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

        profesor2 = new Profesor(
                "PROF002",
                "Jazz",
                "2021-03-15",
                true,
                "P002",
                "Ana Martínez",
                "ana@email.com",
                "3001112233",
                "Calle 4",
                LocalDate.of(1990, 7, 25),
                "ana",
                "ana123"
        );
    }

    @Test
    public void testAgregarProfesor() {
        boolean resultado = sistema.agregarProfesor(profesor1);
        assertTrue(resultado);
        assertEquals(1, sistema.getListProfesores().size());
    }

    @Test
    public void testAgregarProfesorDuplicado() {
        sistema.agregarProfesor(profesor1);
        boolean resultado = sistema.agregarProfesor(profesor1);
        assertFalse(resultado);
    }

    @Test
    public void testActualizarProfesor() {
        sistema.agregarProfesor(profesor1);

        Profesor actualizado = new Profesor(
                "PROF001",
                "Música Moderna",
                "2020-01-01",
                true,
                "P001",
                "Carlos Alberto López",
                "carlosalberto@email.com",
                "3009876543",
                "Calle 3 Nueva",
                LocalDate.of(1985, 3, 10),
                "carlos",
                "carlos123"
        );

        boolean resultado = sistema.actualizaProfesor("P001", actualizado);
        assertTrue(resultado);
        assertEquals("Música Moderna", sistema.getListProfesores().get(0).getEspecialidad());
    }

    @Test
    public void testEliminarProfesor() {
        sistema.agregarProfesor(profesor1);
        boolean resultado = sistema.eliminarProfesor("P001");
        assertTrue(resultado);
        assertEquals(0, sistema.getListProfesores().size());
    }

    @Test
    public void testVerificarProfesorExiste() {
        sistema.agregarProfesor(profesor1);
        assertTrue(sistema.verificarProfesor("P001"));
    }

    @Test
    public void testVerificarProfesorNoExiste() {
        assertFalse(sistema.verificarProfesor("P999"));
    }
}