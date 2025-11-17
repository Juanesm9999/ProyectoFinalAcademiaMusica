package co.edu.uniquindio.poo.proyectofinalmusica;

import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

/**
 * Aplicación Principal de la Academia de Música
 * Interfaz Gráfica con JavaFX
 */
public class App extends Application {

    // Sistema principal
    public SistemaAcademia sistema;

    // Stage principal
    private Stage primaryStage;

    // Usuario actual logueado
    private Persona usuarioActual;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;

        // Inicializar el sistema
        inicializarSistema();
        cargarDatosIniciales();

        // Abrir vista de login
        openLoginView();

        // Configurar stage principal
        primaryStage.setTitle("Academia de Música UQ - Sistema de Gestión");
        primaryStage.show();
    }

    // ==================== INICIALIZACIÓN ====================

    private void inicializarSistema() {
        sistema = new SistemaAcademia("Academia de Música UQ", "NIT-123456789");
        System.out.println("✓ Sistema inicializado correctamente");
    }

    private void cargarDatosIniciales() {
        // Administrador por defecto
        Administrador admin = new Administrador(
                "Director",
                "Administración",
                LocalDate.now().toString(),
                "ADM001",
                "Carlos Rodríguez",
                "admin@academia.com",
                "3001234567",
                "Calle Principal #123",
                LocalDate.of(1975, 5, 15),
                "admin",
                "admin123"
        );
        sistema.agregarAdministrador(admin);

        // Profesor de ejemplo
        Profesor prof1 = new Profesor(
                "PROF001",
                "Piano",
                LocalDate.now().toString(),
                true,
                "P001",
                "María García",
                "maria@academia.com",
                "3009876543",
                "Avenida Sur #45",
                LocalDate.of(1985, 3, 20),
                "maria",
                "maria123"
        );
        prof1.getTheInstrumentosImpartidos().add(TipoInstrumento.PIANO);
        sistema.agregarProfesor(prof1);

        // Estudiante de ejemplo
        Estudiante est1 = new Estudiante(
                "EST2024001",
                LocalDate.now(),
                true,
                "E001",
                "Ana López",
                "ana@email.com",
                "3101111111",
                "Calle 5 #10-15",
                LocalDate.of(2005, 6, 25),
                "ana",
                "ana123"
        );
        sistema.registrarEstudiante(est1);

        // Aulas de ejemplo
        Aula aula1 = new Aula("A001", "A-101", "Aula Principal", "Piso 1", 20, true);
        Aula aula2 = new Aula("A002", "A-102", "Aula Práctica 1", "Piso 1", 10, true);
        sistema.getListAulas().add(aula1);
        sistema.getListAulas().add(aula2);

        // Curso de ejemplo
        Curso curso1 = new Curso(
                "C001",
                "PIANO-1",
                "Piano Básico",
                TipoInstrumento.PIANO,
                1,
                "Introducción al piano",
                15,
                0,
                EstadoCurso.ACTIVO,
                LocalDate.now().toString(),
                LocalDate.now().plusMonths(6).toString(),
                24
        );
        sistema.crearCurso(curso1);

        System.out.println("✓ Datos iniciales cargados");
        System.out.println("  Usuarios de prueba:");
        System.out.println("  - Admin: admin/admin123");
        System.out.println("  - Profesor: maria/maria123");
        System.out.println("  - Estudiante: ana/ana123");

        cargarDatosDemo();
    }

    // ==================== NAVEGACIÓN ENTRE VISTAS ====================

    // Login
    public void openLoginView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/loginView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.LoginViewController) controller).setApp(this));
    }

    // Dashboards
    public void openAdministradorDashboard(Administrador admin) {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/administradorDashboardView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.AdministradorDashboardViewController) controller)
                        .setApp(this, admin != null ? admin : (Administrador) usuarioActual));
    }

    public void openProfesorDashboard(Profesor profesor) {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/profesorDashboardView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.ProfesorDashboardViewController) controller)
                        .setApp(this, profesor != null ? profesor : (Profesor) usuarioActual));
    }

    public void openEstudianteDashboard(Estudiante estudiante) {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/estudianteDashboardView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.EstudianteDashboardViewController) controller)
                        .setApp(this, estudiante != null ? estudiante : (Estudiante) usuarioActual));
    }

    // Gestión - Administrador
    public void openEstudianteView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/estudianteView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.EstudianteViewController) controller).setApp(this));
    }

    public void openProfesorView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/profesorView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.ProfesorViewController) controller).setApp(this));
    }

    public void openCursoView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/cursoView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.CursoViewController) controller).setApp(this));
    }

    public void openClaseView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/claseView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.ClaseViewController) controller).setApp(this));
    }

    public void openAulaView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/aulaView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.AulaViewController) controller).setApp(this));
    }

    public void openReportesView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/reportesView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.ReportesViewController) controller).setApp(this));
    }

    public void openAdministradorView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/administradorView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.AdministradorViewController) controller).setApp(this));
    }

    // Método auxiliar genérico para cargar vistas
    private void loadView(String fxmlPath, java.util.function.Consumer<Object> controllerSetup) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller != null && controllerSetup != null) {
                controllerSetup.accept(controller);
            }

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al cargar vista " + fxmlPath + ": " + e.getMessage());
        }
    }

    // Getter para sistema (usado en algunos ViewControllers)
    public SistemaAcademia getSistemaAcademia() {
        return sistema;
    }

    // ==================== GESTIÓN DE USUARIO ACTUAL ====================

    public Persona getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Persona usuario) {
        this.usuarioActual = usuario;
    }

    public void cerrarSesion() {
        usuarioActual = null;
        openLoginView();
    }

    //                         MAIN

    public static void main(String[] args) {
        launch(args);
    }

    private void cargarDatosDemo() {
        List<Profesor> listProfesores = sistema.getListProfesores();
        List<Estudiante> listEstudiantes = sistema.getListEstudiantes();
        List<Aula> listAulas = sistema.getListAulas();
        List<Horario> listHorarios = sistema.getListHorarios();
        List<Curso> listCursos = sistema.getListCursos();
        List<Clase> listClases = sistema.getListClases();
        List<Inscripcion> listInscripciones = sistema.getListInscripciones();
        List<EvaluacionProgreso> listEvaluaciones = sistema.getListEvaluaciones();
        List<Asistencia> listAsistencias = sistema.getListAsistencias();

        // PROFESORES
        Profesor profesor1 = new Profesor("P001", "Guitarra", "2020-02-15", true, "1010", "Carlos Pérez",
                "carlos@academia.com", "3105551111", "Calle 10 #15-30", LocalDate.of(1985, 6, 20),
                "cperez", "1234");
        Profesor profesor2 = new Profesor("P002", "Piano", "2021-01-10", true, "1011", "Laura Gómez",
                "laura@academia.com", "3144442222", "Carrera 5 #20-45", LocalDate.of(1990, 9, 12),
                "lgomez", "abcd");
        listProfesores.add(profesor1);
        listProfesores.add(profesor2);

        // ESTUDIANTES
        Estudiante est1 = new Estudiante("E001", LocalDate.of(2023, 1, 10), true, "2001", "Juan Martínez",
                "juan@gmail.com", "3201112233", "Calle 8 #12-21", LocalDate.of(2005, 4, 10),
                "jmartinez", "pass1");
        Estudiante est2 = new Estudiante("E002", LocalDate.of(2022, 9, 5), true, "2002", "Sofía Ramírez",
                "sofia@gmail.com", "3009997777", "Barrio El Prado", LocalDate.of(2004, 11, 5),
                "sramirez", "pass2");
        listEstudiantes.add(est1);
        listEstudiantes.add(est2);

        // AULAS
        Aula aula1 = new Aula("A1", "AU-101", "Aula de Piano", "Bloque A - Piso 1", 12, true);
        Aula aula2 = new Aula("A2", "AU-202", "Aula de Guitarra", "Bloque B - Piso 2", 18, true);
        Aula aula3 = new Aula("A3", "AU-303", "Sala de Canto", "Bloque C - Piso 3", 15, false);
        listAulas.add(aula1);
        listAulas.add(aula2);
        listAulas.add(aula3);

        // HORARIOS
        Horario h1 = new Horario("H01", "Lunes", "14:00", "16:00", aula1);
        Horario h2 = new Horario("H02", "Martes", "10:00", "12:00", aula2);
        Horario h3 = new Horario("H03", "Miércoles", "08:00", "10:00", aula1);
        listHorarios.add(h1);
        listHorarios.add(h2);
        listHorarios.add(h3);

        // CURSOS
        Curso cursoGuitarra1 = new Curso("C01", "GTR-101", "Guitarra Básica", TipoInstrumento.GUITARRA, 1,
                "Curso de iniciación a la guitarra", 12, 0, EstadoCurso.ACTIVO, "2025-02-01", "2025-05-01", 12);
        Curso cursoPiano1 = new Curso("C02", "PNO-101", "Piano Básico", TipoInstrumento.PIANO, 1,
                "Curso básico de piano", 10, 0, EstadoCurso.ACTIVO, "2025-02-05", "2025-04-30", 12);
        listCursos.add(cursoGuitarra1);
        listCursos.add(cursoPiano1);

        // CLASES
        ClaseGrupal claseGrupalGuitarra = new ClaseGrupal(12, 0, 12, "Clase grupal de guitarra", "CL01",
                "Horario tarde", "Lunes", "14:00", "16:00", TipoInstrumento.GUITARRA, 1, true);
        claseGrupalGuitarra.setTheAula(aula1);
        claseGrupalGuitarra.setTheProfesor(profesor1);

        ClaseGrupal claseGrupalPiano = new ClaseGrupal(10, 0, 10, "Clase grupal de piano", "CL02",
                "Horario mañana", "Martes", "10:00", "12:00", TipoInstrumento.PIANO, 1, true);
        claseGrupalPiano.setTheAula(aula2);
        claseGrupalPiano.setTheProfesor(profesor2);

        ClaseIndividual claseInd1 = new ClaseIndividual("Postura", "Entender técnica correcta",
                "Traer guitarra propia", "CI01", "Horario especial", "Miércoles", "08:00", "10:00",
                TipoInstrumento.GUITARRA, 1, true);
        claseInd1.setTheAula(aula2);
        claseInd1.setTheProfesor(profesor1);

        listClases.add(claseGrupalGuitarra);
        listClases.add(claseGrupalPiano);
        listClases.add(claseInd1);

        cursoGuitarra1.getTheClases().add(claseGrupalGuitarra);
        cursoPiano1.getTheClases().add(claseGrupalPiano);

        // Horarios detallados
        h1.setClase(claseGrupalGuitarra);
        h1.setCurso(cursoGuitarra1);
        h1.setProfesor(profesor1);
        h1.setTipoInstrumento(TipoInstrumento.GUITARRA);
        claseGrupalGuitarra.setTheHorario(h1);

        h2.setClase(claseGrupalPiano);
        h2.setCurso(cursoPiano1);
        h2.setProfesor(profesor2);
        h2.setTipoInstrumento(TipoInstrumento.PIANO);
        claseGrupalPiano.setTheHorario(h2);

        h3.setClase(claseInd1);
        h3.setProfesor(profesor1);
        h3.setTipoInstrumento(TipoInstrumento.GUITARRA);
        claseInd1.setTheHorario(h3);

        // INSCRIPCIONES
        Inscripcion ins1 = new Inscripcion("I001", "2025-02-01", EstadoInscripcion.ACTIVA, true, false, 0);
        ins1.setTheEstudiante(est1);
        ins1.setTheCurso(cursoGuitarra1);
        est1.getTheInscripciones().add(ins1);
        cursoGuitarra1.getListInscripciones().add(ins1);
        listInscripciones.add(ins1);

        Inscripcion ins2 = new Inscripcion("I002", "2025-02-03", EstadoInscripcion.ACTIVA, true, false, 0);
        ins2.setTheEstudiante(est2);
        ins2.setTheCurso(cursoPiano1);
        est2.getTheInscripciones().add(ins2);
        cursoPiano1.getListInscripciones().add(ins2);
        listInscripciones.add(ins2);

        // NIVELES APROBADOS
        NivelAprobado nivelAprobadoEst1 = new NivelAprobado("NA001", TipoInstrumento.GUITARRA, 0,
                "2024-12-10", 4.5, cursoGuitarra1);
        est1.getTheNivelesAprobados().add(nivelAprobadoEst1);

        // EVALUACIONES
        EvaluacionProgreso ev1 = new EvaluacionProgreso("EV1", 4.5, "Buen avance, domina técnica básica",
                "Trabajar postura y control de ritmo", "2024-10-20");
        ev1.setTheEstudiante(est1);
        ev1.setTheCurso(cursoGuitarra1);
        ev1.setTheClase(claseGrupalGuitarra);
        ev1.setTheEvaluador(profesor1);

        EvaluacionProgreso ev2 = new EvaluacionProgreso("EV2", 3.8, "Proceso estable",
                "Mejorar lectura musical", "2024-10-25");
        ev2.setTheEstudiante(est2);
        ev2.setTheCurso(cursoPiano1);
        ev2.setTheClase(claseGrupalPiano);
        ev2.setTheEvaluador(profesor2);

        listEvaluaciones.add(ev1);
        listEvaluaciones.add(ev2);

        // ASISTENCIAS
        Asistencia a1 = new Asistencia("AS1", est1, claseGrupalGuitarra, LocalDate.of(2024, 10, 12),
                true, "Participó activamente");
        Asistencia a2 = new Asistencia("AS2", est1, claseGrupalPiano, LocalDate.of(2024, 10, 19),
                false, "No asistió");
        Asistencia a3 = new Asistencia("AS3", est2, claseGrupalPiano, LocalDate.of(2024, 10, 15),
                true, "Llegó temprano");

        listAsistencias.add(a1);
        listAsistencias.add(a2);
        listAsistencias.add(a3);

        // DISPONIBILIDAD PROFESORES
        BloqueDisponibilidad b1 = new BloqueDisponibilidad("BD1", "LUNES", "08:00", "10:00", true, profesor1);
        BloqueDisponibilidad b2 = new BloqueDisponibilidad("BD2", "MIERCOLES", "14:00", "16:00", true, profesor1);
        BloqueDisponibilidad b3 = new BloqueDisponibilidad("BD3", "VIERNES", "09:00", "11:00", true, profesor2);
        profesor1.getTheDisponibilidad().add(b1);
        profesor1.getTheDisponibilidad().add(b2);
        profesor2.getTheDisponibilidad().add(b3);

        // ASIGNAR CLASES A PROFESORES
        profesor1.getTheClasesAsignadas().add(claseGrupalGuitarra);
        profesor1.getTheClasesAsignadas().add(claseInd1);
        profesor2.getTheClasesAsignadas().add(claseGrupalPiano);

        System.out.println("✓ Datos demo adicionales cargados para pruebas");
    }

}