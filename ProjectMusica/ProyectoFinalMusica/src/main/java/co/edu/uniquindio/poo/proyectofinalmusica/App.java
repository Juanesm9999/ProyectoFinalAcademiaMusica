package co.edu.uniquindio.poo.proyectofinalmusica;

import co.edu.uniquindio.poo.proyectofinalmusica.ViewController.CursoViewController;
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

    public SistemaAcademia sistema;


    private Stage primaryStage;


    private Persona usuarioActual;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;


        inicializarSistema();
        cargarDatosIniciales();


        openLoginView();

        primaryStage.setTitle("Academia de Música UQ - Sistema de Gestión");
        primaryStage.show();
    }


    private void inicializarSistema() {
        sistema = new SistemaAcademia("Academia de Música UQ", "NIT-123456789");
        System.out.println("✓ Sistema inicializado correctamente");
    }

    private void cargarDatosIniciales() {
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

        Aula aula1 = new Aula("A001", "A-101", "Aula Principal", "Piso 1", 20, true);
        Aula aula2 = new Aula("A002", "A-102", "Aula Práctica 1", "Piso 1", 10, true);
        sistema.getListAulas().add(aula1);
        sistema.getListAulas().add(aula2);

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



    public void openLoginView() {
        loadView("/co/edu/uniquindio/poo/proyectofinalmusica/loginView.fxml",
                controller -> ((co.edu.uniquindio.poo.proyectofinalmusica.ViewController.LoginViewController) controller).setApp(this));
    }


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


    public SistemaAcademia getSistemaAcademia() {
        return sistema;
    }



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




        Profesor profesor1 = new Profesor(
                "P001",
                "Guitarra",
                "2020-02-15",
                true,
                "1010",
                "Carlos Pérez",
                "carlos@academia.com",
                "3105551111",
                "Calle 10 #15-30",
                LocalDate.of(1985, 6, 20),
                "carlos@academia.com",  
                "1234"
        );
        profesor1.getTheInstrumentosImpartidos().add(TipoInstrumento.GUITARRA);
        profesor1.getTheInstrumentosImpartidos().add(TipoInstrumento.VIOLIN);

        Profesor profesor2 = new Profesor(
                "P002",
                "Piano",
                "2021-01-10",
                true,
                "1011",
                "Laura Gómez",
                "laura@academia.com",
                "3144442222",
                "Carrera 5 #20-45",
                LocalDate.of(1990, 9, 12),
                "laura@academia.com",
                "1234"
        );
        profesor2.getTheInstrumentosImpartidos().add(TipoInstrumento.PIANO);
        profesor2.getTheInstrumentosImpartidos().add(TipoInstrumento.SAXOFON);

        listProfesores.add(profesor1);
        listProfesores.add(profesor2);




        Estudiante est1 = new Estudiante(
                "EST2024001",
                LocalDate.of(2024, 1, 15),
                true,
                "E001",
                "Ana López",
                "ana@correo.com",
                "3001234567",
                "Calle 5 #10-20",
                LocalDate.of(2000, 5, 15),
                "ana@correo.com",
                "1234"
        );

        Estudiante est2 = new Estudiante(
                "EST2024002",
                LocalDate.of(2024, 1, 15),
                true,
                "E002",
                "Juan Martínez",
                "juan@correo.com",
                "3007654321",
                "Carrera 20 #15-25",
                LocalDate.of(1999, 8, 20),
                "juan@correo.com",
                "1234"
        );

        Estudiante est3 = new Estudiante(
                "EST2024003",
                LocalDate.of(2024, 1, 15),
                true,
                "E003",
                "Laura García",
                "laura2@correo.com",
                "3009876543",
                "Avenida 30 #20-30",
                LocalDate.of(2001, 3, 10),
                "laura2@correo.com",
                "1234"
        );

        Estudiante est4 = new Estudiante(
                "EST2024004",
                LocalDate.of(2024, 1, 16),
                true,
                "E004",
                "Pedro Rodríguez",
                "pedro@correo.com",
                "3005551234",
                "Calle 40 #25-35",
                LocalDate.of(2000, 11, 25),
                "pedro@correo.com",
                "1234"
        );

        listEstudiantes.add(est1);
        listEstudiantes.add(est2);
        listEstudiantes.add(est3);
        listEstudiantes.add(est4);




        Aula aula1 = new Aula("A1", "AU-101", "Aula de Piano", "Bloque A - Piso 1", 12, true);
        Aula aula2 = new Aula("A2", "AU-202", "Aula de Guitarra", "Bloque B - Piso 2", 18, true);
        Aula aula3 = new Aula("A3", "AU-303", "Sala de Canto", "Bloque C - Piso 3", 15, false);
        listAulas.add(aula1);
        listAulas.add(aula2);
        listAulas.add(aula3);




        Curso cursoGuitarra1 = new Curso(
                "C01",
                "GTR-101",
                "Guitarra Básica",
                TipoInstrumento.GUITARRA,
                1,
                "Curso de iniciación a la guitarra",
                12,
                4,
                EstadoCurso.ACTIVO,
                "2024-01-15",
                "2024-06-30",
                24
        );

        Curso cursoPiano1 = new Curso(
                "C02",
                "PNO-101",
                "Piano Básico",
                TipoInstrumento.PIANO,
                1,
                "Curso básico de piano",
                10,
                0,
                EstadoCurso.ACTIVO,
                "2024-01-20",
                "2024-07-10",
                24
        );

        listCursos.add(cursoGuitarra1);
        listCursos.add(cursoPiano1);




        ClaseGrupal claseGrupalGuitarra = new ClaseGrupal(
                12,
                3,
                9,
                "Clase grupal de guitarra básica",
                "CL01",
                "Lunes 14:00-16:00",
                "Lunes",
                "14:00",
                "16:00",
                TipoInstrumento.GUITARRA,
                1,
                true
        );
        claseGrupalGuitarra.setTheAula(aula2);
        claseGrupalGuitarra.setTheProfesor(profesor1);


        claseGrupalGuitarra.getTheEstudiantesInscritos().add(est1);
        claseGrupalGuitarra.getTheEstudiantesInscritos().add(est2);
        claseGrupalGuitarra.getTheEstudiantesInscritos().add(est3);

        ClaseGrupal claseGrupalPiano = new ClaseGrupal(
                10,
                0,
                10,
                "Clase grupal de piano",
                "CL02",
                "Martes 10:00-12:00",
                "Martes",
                "10:00",
                "12:00",
                TipoInstrumento.PIANO,
                1,
                true
        );
        claseGrupalPiano.setTheAula(aula1);
        claseGrupalPiano.setTheProfesor(profesor2);

        ClaseIndividual claseInd1 = new ClaseIndividual(
                "Técnicas avanzadas de guitarra",
                "Mejorar técnica individual",
                "Clase personalizada",
                "CI01",
                "Miércoles 08:00-09:00",
                "Miércoles",
                "08:00",
                "09:00",
                TipoInstrumento.GUITARRA,
                1,
                true
        );
        claseInd1.setTheAula(aula2);
        claseInd1.setTheProfesor(profesor1);
        claseInd1.setTheEstudiante(est4);

        listClases.add(claseGrupalGuitarra);
        listClases.add(claseGrupalPiano);
        listClases.add(claseInd1);


        cursoGuitarra1.getTheClases().add(claseGrupalGuitarra);
        cursoPiano1.getTheClases().add(claseGrupalPiano);


        profesor1.getTheClasesAsignadas().add(claseGrupalGuitarra);
        profesor1.getTheClasesAsignadas().add(claseInd1);
        profesor2.getTheClasesAsignadas().add(claseGrupalPiano);




        Horario h1 = new Horario("H01", "Lunes", "14:00", "16:00", aula2);
        h1.setClase(claseGrupalGuitarra);
        h1.setCurso(cursoGuitarra1);
        h1.setProfesor(profesor1);
        h1.setTipoInstrumento(TipoInstrumento.GUITARRA);
        claseGrupalGuitarra.setTheHorario(h1);

        Horario h2 = new Horario("H02", "Martes", "10:00", "12:00", aula1);
        h2.setClase(claseGrupalPiano);
        h2.setCurso(cursoPiano1);
        h2.setProfesor(profesor2);
        h2.setTipoInstrumento(TipoInstrumento.PIANO);
        claseGrupalPiano.setTheHorario(h2);

        Horario h3 = new Horario("H03", "Miércoles", "08:00", "09:00", aula2);
        h3.setClase(claseInd1);
        h3.setProfesor(profesor1);
        h3.setTipoInstrumento(TipoInstrumento.GUITARRA);
        claseInd1.setTheHorario(h3);

        listHorarios.add(h1);
        listHorarios.add(h2);
        listHorarios.add(h3);




        Inscripcion ins1 = new Inscripcion("I001", "2024-01-15", EstadoInscripcion.ACTIVA, true, false, 0);
        ins1.setTheEstudiante(est1);
        ins1.setTheCurso(cursoGuitarra1);
        est1.getTheInscripciones().add(ins1);
        cursoGuitarra1.getListInscripciones().add(ins1);
        cursoGuitarra1.getTheEstudiantes().add(est1);
        listInscripciones.add(ins1);

        Inscripcion ins2 = new Inscripcion("I002", "2024-01-15", EstadoInscripcion.ACTIVA, true, false, 0);
        ins2.setTheEstudiante(est2);
        ins2.setTheCurso(cursoGuitarra1);
        est2.getTheInscripciones().add(ins2);
        cursoGuitarra1.getListInscripciones().add(ins2);
        cursoGuitarra1.getTheEstudiantes().add(est2);
        listInscripciones.add(ins2);

        Inscripcion ins3 = new Inscripcion("I003", "2024-01-15", EstadoInscripcion.ACTIVA, true, false, 0);
        ins3.setTheEstudiante(est3);
        ins3.setTheCurso(cursoGuitarra1);
        est3.getTheInscripciones().add(ins3);
        cursoGuitarra1.getListInscripciones().add(ins3);
        cursoGuitarra1.getTheEstudiantes().add(est3);
        listInscripciones.add(ins3);

        Inscripcion ins4 = new Inscripcion("I004", "2024-01-16", EstadoInscripcion.ACTIVA, true, false, 0);
        ins4.setTheEstudiante(est4);
        ins4.setTheCurso(cursoGuitarra1);
        est4.getTheInscripciones().add(ins4);
        cursoGuitarra1.getListInscripciones().add(ins4);
        cursoGuitarra1.getTheEstudiantes().add(est4);
        listInscripciones.add(ins4);




        NivelAprobado nivelAprobadoEst1 = new NivelAprobado(
                "NA001",
                TipoInstrumento.GUITARRA,
                1,
                "2024-11-10",
                4.65,
                cursoGuitarra1
        );
        est1.getTheNivelesAprobados().add(nivelAprobadoEst1);




        EvaluacionProgreso ev1 = new EvaluacionProgreso(
                "EV001",
                4.5,
                "Excelente progreso en acordes básicos. Muestra dedicación y práctica constante.",
                "Trabajar en cambios rápidos entre acordes",
                "2024-10-20"
        );
        ev1.setTheEstudiante(est1);
        ev1.setTheCurso(cursoGuitarra1);
        ev1.setTheClase(claseGrupalGuitarra);
        ev1.setTheEvaluador(profesor1);
        est1.getTheEvaluaciones().add(ev1);

        EvaluacionProgreso ev2 = new EvaluacionProgreso(
                "EV002",
                4.8,
                "Mejora notable en técnica. Excelente dominio de los acordes básicos.",
                "Perfeccionar ritmo en rasgueados",
                "2024-11-05"
        );
        ev2.setTheEstudiante(est1);
        ev2.setTheCurso(cursoGuitarra1);
        ev2.setTheClase(claseGrupalGuitarra);
        ev2.setTheEvaluador(profesor1);
        est1.getTheEvaluaciones().add(ev2);

        EvaluacionProgreso ev3 = new EvaluacionProgreso(
                "EV003",
                3.8,
                "Buen avance, necesita practicar más en casa.",
                "Mejorar postura de la mano izquierda y practicar escalas",
                "2024-10-20"
        );
        ev3.setTheEstudiante(est2);
        ev3.setTheCurso(cursoGuitarra1);
        ev3.setTheClase(claseGrupalGuitarra);
        ev3.setTheEvaluador(profesor1);
        est2.getTheEvaluaciones().add(ev3);

        EvaluacionProgreso ev4 = new EvaluacionProgreso(
                "EV004",
                4.2,
                "Muy buena técnica y ritmo. Sigue practicando así.",
                "Trabajar en coordinación mano derecha-izquierda",
                "2024-10-20"
        );
        ev4.setTheEstudiante(est3);
        ev4.setTheCurso(cursoGuitarra1);
        ev4.setTheClase(claseGrupalGuitarra);
        ev4.setTheEvaluador(profesor1);
        est3.getTheEvaluaciones().add(ev4);

        EvaluacionProgreso ev5 = new EvaluacionProgreso(
                "EV005",
                4.0,
                "Progreso constante en clases individuales. Buen manejo de técnicas.",
                "Practicar escalas pentatónicas y arpegios",
                "2024-10-25"
        );
        ev5.setTheEstudiante(est4);
        ev5.setTheCurso(cursoGuitarra1);
        ev5.setTheClase(claseInd1);
        ev5.setTheEvaluador(profesor1);
        est4.getTheEvaluaciones().add(ev5);

        listEvaluaciones.add(ev1);
        listEvaluaciones.add(ev2);
        listEvaluaciones.add(ev3);
        listEvaluaciones.add(ev4);
        listEvaluaciones.add(ev5);




        Asistencia a1 = new Asistencia(
                "AS001",
                est1,
                claseGrupalGuitarra,
                LocalDate.of(2024, 10, 7),
                true,
                "Excelente participación"
        );
        est1.getTheHistorialAsistencia().add(a1);

        Asistencia a2 = new Asistencia(
                "AS002",
                est1,
                claseGrupalGuitarra,
                LocalDate.of(2024, 10, 14),
                true,
                "Muy atenta en clase"
        );
        est1.getTheHistorialAsistencia().add(a2);

        Asistencia a3 = new Asistencia(
                "AS003",
                est1,
                claseGrupalGuitarra,
                LocalDate.of(2024, 10, 21),
                false,
                "Justificación médica"
        );
        est1.getTheHistorialAsistencia().add(a3);

        Asistencia a4 = new Asistencia(
                "AS004",
                est1,
                claseGrupalGuitarra,
                LocalDate.of(2024, 10, 28),
                true,
                "Presente"
        );
        est1.getTheHistorialAsistencia().add(a4);

        Asistencia a5 = new Asistencia(
                "AS005",
                est2,
                claseGrupalGuitarra,
                LocalDate.of(2024, 10, 7),
                true,
                "Participó activamente"
        );
        est2.getTheHistorialAsistencia().add(a5);

        Asistencia a6 = new Asistencia(
                "AS006",
                est2,
                claseGrupalGuitarra,
                LocalDate.of(2024, 10, 14),
                true,
                "Presente"
        );
        est2.getTheHistorialAsistencia().add(a6);

        listAsistencias.add(a1);
        listAsistencias.add(a2);
        listAsistencias.add(a3);
        listAsistencias.add(a4);
        listAsistencias.add(a5);
        listAsistencias.add(a6);




        BloqueDisponibilidad b1 = new BloqueDisponibilidad("BD1", "LUNES", "08:00", "10:00", true, profesor1);
        BloqueDisponibilidad b2 = new BloqueDisponibilidad("BD2", "MIERCOLES", "14:00", "16:00", true, profesor1);
        BloqueDisponibilidad b3 = new BloqueDisponibilidad("BD3", "VIERNES", "09:00", "11:00", true, profesor2);
        profesor1.getTheDisponibilidad().add(b1);
        profesor1.getTheDisponibilidad().add(b2);
        profesor2.getTheDisponibilidad().add(b3);

        System.out.println("✅ Datos demo completos cargados");
        System.out.println("📊 Resumen de datos:");
        System.out.println("   - Profesores: " + listProfesores.size());
        System.out.println("   - Estudiantes: " + listEstudiantes.size());
        System.out.println("   - Aulas: " + listAulas.size());
        System.out.println("   - Cursos: " + listCursos.size());
        System.out.println("   - Clases: " + listClases.size());
        System.out.println("   - Inscripciones: " + listInscripciones.size());
        System.out.println("   - Evaluaciones: " + listEvaluaciones.size());
        System.out.println("   - Asistencias: " + listAsistencias.size());
        System.out.println("   - Estudiantes en clase grupal de guitarra: " + claseGrupalGuitarra.getTheEstudiantesInscritos().size());
        System.out.println("\n🔐 Credenciales de prueba:");
        System.out.println("   - Profesor Carlos: carlos@academia.com / 1234");
        System.out.println("   - Profesor Laura: laura@academia.com / 1234");
        System.out.println("   - Estudiante Ana: ana@correo.com / 1234");
        System.out.println("   - Estudiante Juan: juan@correo.com / 1234");
        System.out.println("   - Estudiante Laura G: laura2@correo.com / 1234");
        System.out.println("   - Estudiante Pedro: pedro@correo.com / 1234");
    }
}