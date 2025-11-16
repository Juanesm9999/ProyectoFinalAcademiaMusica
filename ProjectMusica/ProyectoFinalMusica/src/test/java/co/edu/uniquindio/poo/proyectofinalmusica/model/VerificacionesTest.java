/* package co.edu.uniquindio.poo.proyectofinalmusica.model;

import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Aula;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.ClaseGrupal;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Curso;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.NivelAprobado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class VerificacionesTest {

    private SistemaAcademia sistema;
    private Estudiante estudiante1;
    private Profesor profesor1;
    private Curso curso1;
    private Curso curso2;
    private Aula aula1;

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

        curso1 = new Curso(
                "C001",
                "PIANO-1",
                "Piano Nivel 1",
                TipoInstrumento.PIANO,
                1,
                "Introducción",
                20,
                0,
                EstadoCurso.ACTIVO,
                "2025-01-15",
                "2025-06-15",
                20
        );

        curso2 = new Curso(
                "C002",
                "PIANO-2",
                "Piano Nivel 2",
                TipoInstrumento.PIANO,
                2,
                "Intermedio",
                15,
                0,
                EstadoCurso.ACTIVO,
                "2025-01-15",
                "2025-06-15",
                20
        );

        aula1 = new Aula(
                "A001",
                "AULA-01",
                "Aula Principal",
                "Edificio A",
                30,
                true
        );
    }

    @Test
    public void testVerificarPrerrequisitosNivel1() {
        assertTrue(sistema.verificarPrerrequisitos(estudiante1, curso1));
    }

    @Test
    public void testVerificarPrerrequisitosNivel2SinAprobar() {
        assertFalse(sistema.verificarPrerrequisitos(estudiante1, curso2));
    }

    @Test
    public void testVerificarPrerrequisitosNivel2Aprobado() {
        NivelAprobado nivel = new NivelAprobado(
                "NA001",
                TipoInstrumento.PIANO,
                1,
                "2024-12-15",
                4.0,
                curso1
        );
        estudiante1.getTheNivelesAprobados().add(nivel);

        assertTrue(sistema.verificarPrerrequisitos(estudiante1, curso2));
    }

    @Test
    public void testVerificarCuposDisponibles() {
        assertTrue(sistema.verificarCuposDisponibles(curso1));
    }

    @Test
    public void testVerificarCuposNoDisponibles() {
        curso1.setCapacidadActual(curso1.getCapacidadMaxima());
        assertFalse(sistema.verificarCuposDisponibles(curso1));
    }

    @Test
    public void testVerificarConflictoHorarioSinConflicto() {
        assertFalse(sistema.verificarConflictoHorario(profesor1, "Lunes 10:00-12:00"));
    }

    @Test
    public void testVerificarConflictoHorarioConConflicto() {
        ClaseGrupal clase = new ClaseGrupal(
                20,
                0,
                20,
                "Desc",
                "CL001",
                "Lunes 10:00-12:00",
                "Lunes",
                "10:00",
                "12:00",
                TipoInstrumento.PIANO,
                1,
                true
        );
        clase.setTheProfesor(profesor1);
        sistema.crearClaseGrupal(clase);

        assertTrue(sistema.verificarConflictoHorario(profesor1, "Lunes 10:00-12:00"));
    }

    @Test
    public void testVerificarConflictoAulaSinConflicto() {
        assertFalse(sistema.verificarConflictoAula(aula1, "Martes 14:00-16:00"));
    }

    @Test
    public void testVerificarConflictoAulaConConflicto() {
        ClaseGrupal clase = new ClaseGrupal(
                20,
                0,
                20,
                "Desc",
                "CL001",
                "Martes 14:00-16:00",
                "Martes",
                "14:00",
                "16:00",
                TipoInstrumento.PIANO,
                1,
                true
        );
        clase.setTheAula(aula1);
        sistema.crearClaseGrupal(clase);

        assertTrue(sistema.verificarConflictoAula(aula1, "Martes 14:00-16:00"));
    }
} */ //