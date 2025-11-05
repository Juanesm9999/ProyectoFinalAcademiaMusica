package co.edu.uniquindio.poo.proyectofinalmusica.model.gestion;

import co.edu.uniquindio.poo.proyectofinalmusica.model.EstadoCurso;
import co.edu.uniquindio.poo.proyectofinalmusica.model.SistemaAcademia;
import co.edu.uniquindio.poo.proyectofinalmusica.model.TipoInstrumento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CursoTest {

    private SistemaAcademia sistema;
    private Curso curso1;
    private Curso curso2;

    @BeforeEach
    public void setUp() {
        sistema = new SistemaAcademia("Academia UQ Música", "123456789");

        curso1 = new Curso(
                "C001", "PIANO-1", "Piano Nivel 1",
                TipoInstrumento.PIANO, 1, "Introducción al piano",
                20, 0, EstadoCurso.ACTIVO,
                "2025-01-15", "2025-06-15", 20
        );

        curso2 = new Curso(
                "C002", "GUITARRA-1", "Guitarra Nivel 1",
                TipoInstrumento.GUITARRA, 1, "Introducción a la guitarra",
                15, 0, EstadoCurso.ACTIVO,
                "2025-01-15", "2025-06-15", 20
        );
    }

    @Test
    public void testCrearCurso() {
        boolean resultado = sistema.crearCurso(curso1);
        assertTrue(resultado);
        assertEquals(1, sistema.getListCursos().size());
    }

    @Test
    public void testCrearCursoDuplicado() {
        sistema.crearCurso(curso1);
        boolean resultado = sistema.crearCurso(curso1);
        assertFalse(resultado);
    }

    @Test
    public void testModificarCurso() {
        sistema.crearCurso(curso1);

        Curso actualizado = new Curso(
                "C001", "PIANO-1-MOD", "Piano Nivel 1 Modificado",
                TipoInstrumento.PIANO, 1, "Descripción modificada",
                25, 0, EstadoCurso.ACTIVO,
                "2025-02-01", "2025-07-01", 20
        );

        boolean resultado = sistema.modificarCurso("C001", actualizado);
        assertTrue(resultado);
        assertEquals("Piano Nivel 1 Modificado", sistema.getListCursos().get(0).getNombre());
    }

    @Test
    public void testEliminarCurso() {
        sistema.crearCurso(curso1);
        boolean resultado = sistema.eliminarCurso("C001");
        assertTrue(resultado);
        assertEquals(0, sistema.getListCursos().size());
    }

    @Test
    public void testVerificarCursoExiste() {
        sistema.crearCurso(curso1);
        assertTrue(sistema.verificarCurso("C001"));
    }

    @Test
    public void testCrearMultiplesCursos() {
        sistema.crearCurso(curso1);
        sistema.crearCurso(curso2);
        assertEquals(2, sistema.getListCursos().size());
    }

}