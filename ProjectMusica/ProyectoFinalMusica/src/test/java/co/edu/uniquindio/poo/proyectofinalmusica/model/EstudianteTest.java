 package co.edu.uniquindio.poo.proyectofinalmusica.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

 public class EstudianteTest {
    private SistemaAcademia sistema;
    private Estudiante estudiante1;
    private Estudiante estudiante2;

    @BeforeEach
    public void setUp() {
        sistema = new SistemaAcademia("Academia UQ Música", "123456789");

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
    }

    @Test
    public void testRegistrarEstudiante() {
        boolean resultado = sistema.registrarEstudiante(estudiante1);
        assertTrue(resultado, "El estudiante debería registrarse correctamente");
        assertEquals(1, sistema.getListEstudiantes().size());
    }

    @Test
    public void testRegistrarEstudianteDuplicado() {
        sistema.registrarEstudiante(estudiante1);
        boolean resultado = sistema.registrarEstudiante(estudiante1);
        assertFalse(resultado, "No debería permitir estudiante duplicado");
    }

    @Test
    public void testActualizarEstudiante() {
        sistema.registrarEstudiante(estudiante1);

        Estudiante actualizado = new Estudiante(
                "EST001",
                LocalDate.now(),
                true,
                "E001",
                "Juan Carlos Pérez",
                "juancarlos@email.com",
                "3001234567",
                "Calle 1 Nueva",
                LocalDate.of(2000, 1, 15),
                "juan",
                "juan123"
        );

        boolean resultado = sistema.actualizarEstudiante("E001", actualizado);
        assertTrue(resultado);
        assertEquals("Juan Carlos Pérez", sistema.getListEstudiantes().get(0).getNombre());
    }

    @Test
    public void testEliminarEstudiante() {
        sistema.registrarEstudiante(estudiante1);
        boolean resultado = sistema.eliminarEstudiante("E001");
        assertTrue(resultado);
        assertEquals(0, sistema.getListEstudiantes().size());
    }

    @Test
    public void testVerificarEstudianteExiste() {
        sistema.registrarEstudiante(estudiante1);
        assertTrue(sistema.verificarEstudiante("E001"));
    }

    @Test
    public void testVerificarEstudianteNoExiste() {
        assertFalse(sistema.verificarEstudiante("E999"));
    }

    @Test
    public void testRegistrarMultiplesEstudiantes() {
        sistema.registrarEstudiante(estudiante1);
        sistema.registrarEstudiante(estudiante2);
        assertEquals(2, sistema.getListEstudiantes().size());
    }
}
