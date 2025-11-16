package co.edu.uniquindio.poo.proyectofinalmusica;

import co.edu.uniquindio.poo.proyectofinalmusica.controller.*;
import co.edu.uniquindio.poo.proyectofinalmusica.model.*;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;
import javafx.application.Application;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Aplicación Principal de la Academia de Música
 * Sistema completo de gestión con menú interactivo de consola
 */
public class App extends Application {

    private static SistemaAcademia sistema;
    private static Scanner scanner;

    // Controladores
    private static EstudianteController estudianteController;
    private static ProfesorController profesorController;
    private static AdministradorController administradorController;
    private static CursoController cursoController;
    private static ClaseController claseController;
    private static AulaController aulaController;
    private static InscripcionController inscripcionController;
    private static LoginController loginController;

    // Usuario actual logueado
    private static Persona usuarioActual = null;

    public static void main(String[] args) {
        inicializarSistema();
        inicializarControladores();
        cargarDatosIniciales();

        scanner = new Scanner(System.in);

        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   ACADEMIA DE MÚSICA - SISTEMA DE     ║");
        System.out.println("║          GESTIÓN ACADÉMICA             ║");
        System.out.println("╚════════════════════════════════════════╝");

        boolean continuar = true;
        while (continuar) {
            if (usuarioActual == null) {
                continuar = menuLogin();
            } else {
                if (usuarioActual instanceof Administrador) {
                    menuAdministrador();
                } else if (usuarioActual instanceof Profesor) {
                    menuProfesor();
                } else if (usuarioActual instanceof Estudiante) {
                    menuEstudiante();
                }
            }
        }

        scanner.close();
        System.out.println("\n¡Gracias por usar el sistema! Hasta pronto.");
    }

    // ==================== INICIALIZACIÓN ====================

    private static void inicializarSistema() {
        sistema = new SistemaAcademia("Academia de Música UQ", "NIT-123456789");
    }

    private static void inicializarControladores() {
        estudianteController = new EstudianteController(sistema);
        profesorController = new ProfesorController(sistema);
        administradorController = new AdministradorController(sistema);
        cursoController = new CursoController(sistema);
        claseController = new ClaseController(sistema);
        aulaController = new AulaController(sistema);
        inscripcionController = new InscripcionController(sistema);
        loginController = new LoginController(sistema);
    }

    private static void cargarDatosIniciales() {
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

        // Algunos profesores
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

        Profesor prof2 = new Profesor(
                "PROF002",
                "Guitarra",
                LocalDate.now().toString(),
                true,
                "P002",
                "Juan Martínez",
                "juan@academia.com",
                "3015555555",
                "Carrera 10 #20-30",
                LocalDate.of(1980, 8, 10),
                "juan",
                "juan123"
        );
        prof2.getTheInstrumentosImpartidos().add(TipoInstrumento.GUITARRA);
        sistema.agregarProfesor(prof2);

        // Algunos estudiantes
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

        // Algunas aulas
        Aula aula1 = new Aula("A001", "A-101", "Aula Principal", "Piso 1", 20, true);
        Aula aula2 = new Aula("A002", "A-102", "Aula Práctica 1", "Piso 1", 10, true);
        sistema.getListAulas().add(aula1);
        sistema.getListAulas().add(aula2);

        // Algunos cursos
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

        System.out.println("✓ Datos iniciales cargados correctamente");
        System.out.println("  Admin: admin/admin123");
        System.out.println("  Profesor: maria/maria123");
        System.out.println("  Estudiante: ana/ana123\n");
    }

    // ==================== MENÚ LOGIN ====================

    private static boolean menuLogin() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           INICIO DE SESIÓN             ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. Iniciar Sesión");
        System.out.println("2. Registrarse");
        System.out.println("3. Salir");
        System.out.print("\nSeleccione una opción: ");

        int opcion = leerEntero();

        switch (opcion) {
            case 1:
                iniciarSesion();
                break;
            case 2:
                registrarNuevoUsuario();
                break;
            case 3:
                return false;
            default:
                System.out.println("❌ Opción inválida");
        }
        return true;
    }

    private static void iniciarSesion() {
        System.out.print("\nUsuario: ");
        String usuario = scanner.nextLine();

        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        System.out.println("\nTipo de usuario:");
        System.out.println("1. Estudiante");
        System.out.println("2. Profesor");
        System.out.println("3. Administrador");
        System.out.print("Seleccione: ");

        int tipo = leerEntero();
        String tipoRol = "";

        switch (tipo) {
            case 1: tipoRol = "ESTUDIANTE"; break;
            case 2: tipoRol = "PROFESOR"; break;
            case 3: tipoRol = "ADMINISTRADOR"; break;
            default:
                System.out.println("❌ Tipo de usuario inválido");
                return;
        }

        usuarioActual = loginController.iniciarSesion(usuario, contrasena, tipoRol);

        if (usuarioActual != null) {
            System.out.println("\n✓ ¡Bienvenido " + usuarioActual.getNombre() + "!");
        } else {
            System.out.println("\n❌ Credenciales incorrectas");
        }
    }

    private static void registrarNuevoUsuario() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║             REGISTRO                   ║");
        System.out.println("╚════════════════════════════════════════╝");

        System.out.println("Tipo de usuario:");
        System.out.println("1. Estudiante");
        System.out.println("2. Profesor");
        System.out.print("Seleccione: ");

        int tipo = leerEntero();

        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();

        if (loginController.verificarUsuarioExiste(usuario)) {
            System.out.println("\n❌ El usuario ya existe");
            return;
        }

        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        String id = "U" + System.currentTimeMillis();

        boolean exito = false;

        if (tipo == 1) {
            Estudiante nuevo = new Estudiante(
                    "EST" + System.currentTimeMillis(),
                    LocalDate.now(),
                    true,
                    id,
                    nombre,
                    email,
                    telefono,
                    direccion,
                    LocalDate.of(2000, 1, 1),
                    usuario,
                    contrasena
            );
            exito = sistema.registrarEstudiante(nuevo);
        } else if (tipo == 2) {
            Profesor nuevo = new Profesor(
                    "PROF" + System.currentTimeMillis(),
                    "Por definir",
                    LocalDate.now().toString(),
                    true,
                    id,
                    nombre,
                    email,
                    telefono,
                    direccion,
                    LocalDate.of(1980, 1, 1),
                    usuario,
                    contrasena
            );
            exito = sistema.agregarProfesor(nuevo);
        }

        if (exito) {
            System.out.println("\n✓ Registro exitoso. Ya puede iniciar sesión.");
        } else {
            System.out.println("\n❌ Error en el registro");
        }
    }

    // ==================== MENÚ ADMINISTRADOR ====================

    private static void menuAdministrador() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║        MENÚ ADMINISTRADOR              ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1.  Gestión de Estudiantes");
        System.out.println("2.  Gestión de Profesores");
        System.out.println("3.  Gestión de Administradores");
        System.out.println("4.  Gestión de Cursos");
        System.out.println("5.  Gestión de Clases");
        System.out.println("6.  Gestión de Aulas");
        System.out.println("7.  Gestión de Inscripciones");
        System.out.println("8.  Reportes");
        System.out.println("9.  Ver Estadísticas");
        System.out.println("10. Cerrar Sesión");
        System.out.print("\nSeleccione una opción: ");

        int opcion = leerEntero();

        switch (opcion) {
            case 1: menuGestionEstudiantes(); break;
            case 2: menuGestionProfesores(); break;
            case 3: menuGestionAdministradores(); break;
            case 4: menuGestionCursos(); break;
            case 5: menuGestionClases(); break;
            case 6: menuGestionAulas(); break;
            case 7: menuGestionInscripciones(); break;
            case 8: menuReportes(); break;
            case 9: mostrarEstadisticas(); break;
            case 10:
                usuarioActual = null;
                System.out.println("\n✓ Sesión cerrada");
                break;
            default:
                System.out.println("❌ Opción inválida");
        }
    }

    // ==================== GESTIÓN ESTUDIANTES ====================

    private static void menuGestionEstudiantes() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      GESTIÓN DE ESTUDIANTES            ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. Listar todos los estudiantes");
        System.out.println("2. Buscar estudiante por ID");
        System.out.println("3. Agregar nuevo estudiante");
        System.out.println("4. Actualizar estudiante");
        System.out.println("5. Eliminar estudiante");
        System.out.println("6. Ver estudiantes activos");
        System.out.println("7. Volver");
        System.out.print("\nSeleccione una opción: ");

        int opcion = leerEntero();

        switch (opcion) {
            case 1: listarEstudiantes(); break;
            case 2: buscarEstudiante(); break;
            case 3: agregarEstudiante(); break;
            case 4: actualizarEstudiante(); break;
            case 5: eliminarEstudiante(); break;
            case 6: listarEstudiantesActivos(); break;
            case 7: break;
            default: System.out.println("❌ Opción inválida");
        }
    }

    private static void listarEstudiantes() {
        List<Estudiante> estudiantes = estudianteController.obtenerListaEstudiantes();

        System.out.println("\n=== LISTA DE ESTUDIANTES ===");
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados");
        } else {
            for (Estudiante est : estudiantes) {
                System.out.println("\nID: " + est.getId());
                System.out.println("Nombre: " + est.getNombre());
                System.out.println("Matrícula: " + est.getMatricula());
                System.out.println("Email: " + est.getEmail());
                System.out.println("Estado: " + (est.getActivo() ? "Activo" : "Inactivo"));
                System.out.println("Inscripciones: " + est.getTheInscripciones().size());
                System.out.println("---");
            }
        }
        presionEnterParaContinuar();
    }

    private static void buscarEstudiante() {
        System.out.print("\nIngrese ID del estudiante: ");
        String id = scanner.nextLine();

        Estudiante est = estudianteController.buscarEstudiante(id);

        if (est != null) {
            System.out.println("\n=== INFORMACIÓN DEL ESTUDIANTE ===");
            System.out.println("ID: " + est.getId());
            System.out.println("Nombre: " + est.getNombre());
            System.out.println("Matrícula: " + est.getMatricula());
            System.out.println("Email: " + est.getEmail());
            System.out.println("Teléfono: " + est.getTelefono());
            System.out.println("Dirección: " + est.getDireccion());
            System.out.println("Usuario: " + est.getUsuario());
            System.out.println("Estado: " + (est.getActivo() ? "Activo" : "Inactivo"));
            System.out.println("\nInscripciones activas: " +
                    inscripcionController.contarInscripcionesActivasEstudiante(id));
            System.out.println("Niveles aprobados: " + est.getTheNivelesAprobados().size());

            double promedio = estudianteController.calcularPromedioEstudiante(id);
            System.out.println("Promedio general: " + String.format("%.2f", promedio));
        } else {
            System.out.println("\n❌ Estudiante no encontrado");
        }
        presionEnterParaContinuar();
    }

    private static void agregarEstudiante() {
        System.out.println("\n=== AGREGAR ESTUDIANTE ===");

        System.out.print("ID: ");
        String id = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();

        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();

        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        Estudiante nuevo = new Estudiante(
                matricula,
                LocalDate.now(),
                true,
                id,
                nombre,
                email,
                telefono,
                direccion,
                LocalDate.of(2000, 1, 1),
                usuario,
                contrasena
        );

        if (estudianteController.registrarEstudiante(nuevo)) {
            System.out.println("\n✓ Estudiante agregado correctamente");
        } else {
            System.out.println("\n❌ Error: El estudiante ya existe");
        }
        presionEnterParaContinuar();
    }

    private static void actualizarEstudiante() {
        System.out.print("\nIngrese ID del estudiante a actualizar: ");
        String id = scanner.nextLine();

        Estudiante existente = estudianteController.buscarEstudiante(id);
        if (existente == null) {
            System.out.println("\n❌ Estudiante no encontrado");
            presionEnterParaContinuar();
            return;
        }

        System.out.println("\n=== ACTUALIZAR ESTUDIANTE ===");
        System.out.println("(Presione Enter para mantener el valor actual)");

        System.out.print("Nombre [" + existente.getNombre() + "]: ");
        String nombre = scanner.nextLine();
        if (nombre.isEmpty()) nombre = existente.getNombre();

        System.out.print("Email [" + existente.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (email.isEmpty()) email = existente.getEmail();

        System.out.print("Teléfono [" + existente.getTelefono() + "]: ");
        String telefono = scanner.nextLine();
        if (telefono.isEmpty()) telefono = existente.getTelefono();

        System.out.print("Activo (s/n) [" + (existente.getActivo() ? "s" : "n") + "]: ");
        String activoStr = scanner.nextLine();
        boolean activo = activoStr.isEmpty() ? existente.getActivo() : activoStr.equalsIgnoreCase("s");

        Estudiante actualizado = new Estudiante(
                existente.getMatricula(),
                existente.getFechaIngreso(),
                activo,
                id,
                nombre,
                email,
                telefono,
                existente.getDireccion(),
                existente.getFechaNacimiento(),
                existente.getUsuario(),
                existente.getContrasenia()
        );

        if (profesorController.actualizarProfesor(id, actualizado)) {
            System.out.println("\n✓ Profesor actualizado correctamente");
        } else {
            System.out.println("\n❌ Error al actualizar");
        }
        presionEnterParaContinuar();
    }

    private static void eliminarProfesor() {
        System.out.print("\nIngrese ID del profesor: ");
        String id = scanner.nextLine();

        Profesor prof = profesorController.buscarProfesor(id);
        if (prof != null) {
            System.out.print("\n¿Está seguro de eliminar a " + prof.getNombre() + "? (s/n): ");
            String confirmacion = scanner.nextLine();

            if (confirmacion.equalsIgnoreCase("s")) {
                if (profesorController.eliminarProfesor(id)) {
                    System.out.println("\n✓ Profesor eliminado");
                } else {
                    System.out.println("\n❌ Error al eliminar");
                }
            }
        } else {
            System.out.println("\n❌ Profesor no encontrado");
        }
        presionEnterParaContinuar();
    }

    private static void gestionarInstrumentosProfesor() {
        System.out.print("\nIngrese ID del profesor: ");
        String id = scanner.nextLine();

        Profesor prof = profesorController.buscarProfesor(id);
        if (prof == null) {
            System.out.println("\n❌ Profesor no encontrado");
            presionEnterParaContinuar();
            return;
        }

        System.out.println("\n=== INSTRUMENTOS DE " + prof.getNombre() + " ===");
        System.out.println("1. Ver instrumentos actuales");
        System.out.println("2. Agregar instrumento");
        System.out.println("3. Remover instrumento");
        System.out.print("\nSeleccione: ");

        int opcion = leerEntero();

        switch (opcion) {
            case 1:
                System.out.println("\nInstrumentos actuales:");
                for (TipoInstrumento inst : prof.getTheInstrumentosImpartidos()) {
                    System.out.println("  • " + inst);
                }
                break;
            case 2:
                System.out.println("\nInstrumentos disponibles:");
                TipoInstrumento[] todos = TipoInstrumento.values();
                for (int i = 0; i < todos.length; i++) {
                    System.out.println((i+1) + ". " + todos[i]);
                }
                System.out.print("\nSeleccione instrumento: ");
                int idx = leerEntero() - 1;
                if (idx >= 0 && idx < todos.length) {
                    if (profesorController.agregarInstrumentoAProfesor(id, todos[idx])) {
                        System.out.println("\n✓ Instrumento agregado");
                    } else {
                        System.out.println("\n❌ El profesor ya tiene ese instrumento");
                    }
                }
                break;
            case 3:
                System.out.println("\nInstrumentos del profesor:");
                List<TipoInstrumento> instrumentos = prof.getTheInstrumentosImpartidos();
                for (int i = 0; i < instrumentos.size(); i++) {
                    System.out.println((i+1) + ". " + instrumentos.get(i));
                }
                System.out.print("\nSeleccione instrumento a remover: ");
                int idxRem = leerEntero() - 1;
                if (idxRem >= 0 && idxRem < instrumentos.size()) {
                    if (profesorController.removerInstrumentoDeProfesor(id, instrumentos.get(idxRem))) {
                        System.out.println("\n✓ Instrumento removido");
                    }
                }
                break;
        }
        presionEnterParaContinuar();
    }

    private static void verCargaDocente() {
        System.out.println("\n=== CARGA DOCENTE ===");
        String reporte = administradorController.generarReporteCargaDocente();
        System.out.println(reporte);
        presionEnterParaContinuar();
    }

    // ==================== GESTIÓN ADMINISTRADORES ====================

    private static void menuGestionAdministradores() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║    GESTIÓN DE ADMINISTRADORES          ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. Listar administradores");
        System.out.println("2. Agregar administrador");
        System.out.println("3. Actualizar administrador");
        System.out.println("4. Eliminar administrador");
        System.out.println("5. Volver");
        System.out.print("\nSeleccione una opción: ");

        int opcion = leerEntero();

        switch (opcion) {
            case 1: listarAdministradores(); break;
            case 2: agregarAdministrador(); break;
            case 3: actualizarAdministrador(); break;
            case 4: eliminarAdministrador(); break;
            case 5: break;
            default: System.out.println("❌ Opción inválida");
        }
    }

    private static void listarAdministradores() {
        List<Administrador> admins = administradorController.obtenerListaAdministradores();

        System.out.println("\n=== LISTA DE ADMINISTRADORES ===");
        for (Administrador admin : admins) {
            System.out.println("\nNombre: " + admin.getNombre());
            System.out.println("Cargo: " + admin.getCargo());
            System.out.println("Departamento: " + admin.getDepartamento());
            System.out.println("Email: " + admin.getEmail());
            System.out.println("---");
        }
        presionEnterParaContinuar();
    }

    private static void agregarAdministrador() {
        System.out.println("\n=== AGREGAR ADMINISTRADOR ===");

        System.out.print("ID: ");
        String id = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Cargo: ");
        String cargo = scanner.nextLine();

        System.out.print("Departamento: ");
        String departamento = scanner.nextLine();

        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();

        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        Administrador nuevo = new Administrador(
                cargo,
                departamento,
                LocalDate.now().toString(),
                id,
                nombre,
                email,
                telefono,
                "Dirección",
                LocalDate.of(1975, 1, 1),
                usuario,
                contrasena
        );

        if (administradorController.agregarAdministrador(nuevo)) {
            System.out.println("\n✓ Administrador agregado correctamente");
        } else {
            System.out.println("\n❌ Error: El administrador ya existe");
        }
        presionEnterParaContinuar();
    }

    private static void actualizarAdministrador() {
        System.out.print("\nIngrese ID del administrador: ");
        String id = scanner.nextLine();

        Administrador existente = administradorController.buscarAdministrador(id);
        if (existente == null) {
            System.out.println("\n❌ Administrador no encontrado");
            presionEnterParaContinuar();
            return;
        }

        System.out.println("\n=== ACTUALIZAR ADMINISTRADOR ===");
        System.out.print("Nombre [" + existente.getNombre() + "]: ");
        String nombre = scanner.nextLine();
        if (nombre.isEmpty()) nombre = existente.getNombre();

        System.out.print("Cargo [" + existente.getCargo() + "]: ");
        String cargo = scanner.nextLine();
        if (cargo.isEmpty()) cargo = existente.getCargo();

        Administrador actualizado = new Administrador(
                cargo,
                existente.getDepartamento(),
                existente.getFechaIngreso(),
                id,
                nombre,
                existente.getEmail(),
                existente.getTelefono(),
                existente.getDireccion(),
                existente.getFechaNacimiento(),
                existente.getUsuario(),
                existente.getContrasenia()
        );

        if (administradorController.actualizarAdministrador(id, actualizado)) {
            System.out.println("\n✓ Administrador actualizado");
        }
        presionEnterParaContinuar();
    }

    private static void eliminarAdministrador() {
        System.out.print("\nIngrese ID del administrador: ");
        String id = scanner.nextLine();

        if (administradorController.eliminarAdministrador(id)) {
            System.out.println("\n✓ Administrador eliminado");
        } else {
            System.out.println("\n❌ Error al eliminar");
        }
        presionEnterParaContinuar();
    }

    // ==================== GESTIÓN CURSOS ====================

    private static void menuGestionCursos() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         GESTIÓN DE CURSOS              ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. Listar todos los cursos");
        System.out.println("2. Buscar curso");
        System.out.println("3. Agregar nuevo curso");
        System.out.println("4. Modificar curso");
        System.out.println("5. Eliminar curso");
        System.out.println("6. Ver cursos activos");
        System.out.println("7. Ver cursos por instrumento");
        System.out.println("8. Ver cursos con cupos");
        System.out.println("9. Volver");
        System.out.print("\nSeleccione una opción: ");

        int opcion = leerEntero();

        switch (opcion) {
            case 1: listarCursos(); break;
            case 2: buscarCurso(); break;
            case 3: agregarCurso(); break;
            case 4: modificarCurso(); break;
            case 5: eliminarCurso(); break;
            case 6: listarCursosActivos(); break;
            case 7: listarCursosPorInstrumento(); break;
            case 8: listarCursosConCupos(); break;
            case 9: break;
            default: System.out.println("❌ Opción inválida");
        }
    }

    private static void listarCursos() {
        List<Curso> cursos = cursoController.obtenerListaCursos();

        System.out.println("\n=== LISTA DE CURSOS ===");
        if (cursos.isEmpty()) {
            System.out.println("No hay cursos registrados");
        } else {
            for (Curso curso : cursos) {
                System.out.println("\nCódigo: " + curso.getCodigo());
                System.out.println("Nombre: " + curso.getNombre());
                System.out.println("Instrumento: " + curso.getInstrumento());
                System.out.println("Nivel: " + curso.getNivel());
                System.out.println("Estado: " + curso.getEstado());
                System.out.println("Capacidad: " + curso.getCapacidadActual() + "/" + curso.getCapacidadMaxima());
                System.out.println("Duración: " + curso.getDuracionSemanas() + " semanas");
                System.out.println("---");
            }
        }
        presionEnterParaContinuar();
    }

    private static void buscarCurso() {
        System.out.print("\nIngrese código del curso: ");
        String codigo = scanner.nextLine();

        Curso curso = cursoController.buscarCursoPorCodigo(codigo);

        if (curso != null) {
            System.out.println("\n=== INFORMACIÓN DEL CURSO ===");
            System.out.println("Código: " + curso.getCodigo());
            System.out.println("Nombre: " + curso.getNombre());
            System.out.println("Instrumento: " + curso.getInstrumento());
            System.out.println("Nivel: " + curso.getNivel());
            System.out.println("Descripción: " + curso.getDescripcion());
            System.out.println("Estado: " + curso.getEstado());
            System.out.println("Capacidad máxima: " + curso.getCapacidadMaxima());
            System.out.println("Inscritos: " + curso.getCapacidadActual());
            System.out.println("Cupos disponibles: " + cursoController.obtenerCuposDisponibles(curso.getId()));
            System.out.println("Duración: " + curso.getDuracionSemanas() + " semanas");
            System.out.println("Fecha inicio: " + curso.getFechaInicio());
            System.out.println("Fecha fin: " + curso.getFechaFin());
            System.out.println("Clases: " + curso.getTheClases().size());
        } else {
            System.out.println("\n❌ Curso no encontrado");
        }
        presionEnterParaContinuar();
    }

    private static void agregarCurso() {
        System.out.println("\n=== AGREGAR CURSO ===");

        System.out.print("ID: ");
        String id = scanner.nextLine();

        System.out.print("Código: ");
        String codigo = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.println("\nInstrumentos disponibles:");
        TipoInstrumento[] instrumentos = TipoInstrumento.values();
        for (int i = 0; i < instrumentos.length; i++) {
            System.out.println((i+1) + ". " + instrumentos[i]);
        }
        System.out.print("Seleccione instrumento: ");
        int idxInst = leerEntero() - 1;

        System.out.print("Nivel (1-10): ");
        int nivel = leerEntero();

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        System.out.print("Capacidad máxima: ");
        int capacidad = leerEntero();

        System.out.print("Duración en semanas: ");
        int duracion = leerEntero();

        if (idxInst >= 0 && idxInst < instrumentos.length) {
            Curso nuevo = new Curso(
                    id,
                    codigo,
                    nombre,
                    instrumentos[idxInst],
                    nivel,
                    descripcion,
                    capacidad,
                    0,
                    EstadoCurso.ACTIVO,
                    LocalDate.now().toString(),
                    LocalDate.now().plusWeeks(duracion).toString(),
                    duracion
            );

            if (cursoController.crearCurso(nuevo)) {
                System.out.println("\n✓ Curso creado correctamente");
            } else {
                System.out.println("\n❌ Error: El curso ya existe");
            }
        }
        presionEnterParaContinuar();
    }

    private static void modificarCurso() {
        System.out.print("\nIngrese ID del curso: ");
        String id = scanner.nextLine();

        Curso existente = cursoController.buscarCurso(id);
        if (existente == null) {
            System.out.println("\n❌ Curso no encontrado");
            presionEnterParaContinuar();
            return;
        }

        System.out.println("\n=== MODIFICAR CURSO ===");
        System.out.print("Nombre [" + existente.getNombre() + "]: ");
        String nombre = scanner.nextLine();
        if (nombre.isEmpty()) nombre = existente.getNombre();

        System.out.print("Descripción [" + existente.getDescripcion() + "]: ");
        String descripcion = scanner.nextLine();
        if (descripcion.isEmpty()) descripcion = existente.getDescripcion();

        Curso actualizado = new Curso(
                id,
                existente.getCodigo(),
                nombre,
                existente.getInstrumento(),
                existente.getNivel(),
                descripcion,
                existente.getCapacidadMaxima(),
                existente.getCapacidadActual(),
                existente.getEstado(),
                existente.getFechaInicio(),
                existente.getFechaFin(),
                existente.getDuracionSemanas()
        );

        if (cursoController.modificarCurso(id, actualizado)) {
            System.out.println("\n✓ Curso modificado correctamente");
        }
        presionEnterParaContinuar();
    }

    private static void eliminarCurso() {
        System.out.print("\nIngrese ID del curso: ");
        String id = scanner.nextLine();

        Curso curso = cursoController.buscarCurso(id);
        if (curso != null) {
            System.out.print("\n¿Está seguro de eliminar " + curso.getNombre() + "? (s/n): ");
            String confirmacion = scanner.nextLine();

            if (confirmacion.equalsIgnoreCase("s")) {
                if (cursoController.eliminarCurso(id)) {
                    System.out.println("\n✓ Curso eliminado");
                }
            }
        } else {
            System.out.println("\n❌ Curso no encontrado");
        }
        presionEnterParaContinuar();
    }

    private static void listarCursosActivos() {
        List<Curso> activos = cursoController.obtenerCursosActivos();

        System.out.println("\n=== CURSOS ACTIVOS ===");
        for (Curso curso : activos) {
            System.out.println("• " + curso.getNombre() + " - " + curso.getInstrumento() +
                    " Nivel " + curso.getNivel());
        }
        presionEnterParaContinuar();
    }

    private static void listarCursosPorInstrumento() {
        System.out.println("\nInstrumentos:");
        TipoInstrumento[] instrumentos = TipoInstrumento.values();
        for (int i = 0; i < instrumentos.length; i++) {
            System.out.println((i+1) + ". " + instrumentos[i]);
        }
        System.out.print("\nSeleccione: ");
        int idx = leerEntero() - 1;

        if (idx >= 0 && idx < instrumentos.length) {
            List<Curso> cursos = cursoController.obtenerCursosPorInstrumento(instrumentos[idx]);
            System.out.println("\n=== CURSOS DE " + instrumentos[idx] + " ===");
            for (Curso curso : cursos) {
                System.out.println("• " + curso.getNombre() + " (Nivel " + curso.getNivel() + ")");
            }
        }
        presionEnterParaContinuar();
    }

    private static void listarCursosConCupos() {
        List<Curso> conCupos = cursoController.obtenerCursosConCuposDisponibles();

        System.out.println("\n=== CURSOS CON CUPOS DISPONIBLES ===");
        for (Curso curso : conCupos) {
            int cupos = cursoController.obtenerCuposDisponibles(curso.getId());
            System.out.println("• " + curso.getNombre() + " - Cupos: " + cupos);
        }
        presionEnterParaContinuar();
    }

    // ==================== GESTIÓN CLASES ====================

    private static void menuGestionClases() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         GESTIÓN DE CLASES              ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. Listar todas las clases");
        System.out.println("2. Crear clase grupal");
        System.out.println("3. Crear clase individual");
        System.out.println("4. Asignar profesor a clase");
        System.out.println("5. Asignar aula a clase");
        System.out.println("6. Ver clases por instrumento");
        System.out.println("7. Ver clases con cupos");
        System.out.println("8. Volver");
        System.out.print("\nSeleccione una opción: ");

        int opcion = leerEntero();

        switch (opcion) {
            case 1: listarClases(); break;
            case 2: crearClaseGrupal(); break;
            case 3: crearClaseIndividual(); break;
            case 4: asignarProfesorAClase(); break;
            case 5: asignarAulaAClase(); break;
            case 6: listarClasesPorInstrumento(); break;
            case 7: listarClasesConCupos(); break;
            case 8: break;
            default: System.out.println("❌ Opción inválida");
        }
    }

    private static void listarClases() {
        List<Clase> clases = claseController.obtenerListaClases();

        System.out.println("\n=== LISTA DE CLASES ===");
        if (clases.isEmpty()) {
            System.out.println("No hay clases registradas");
        } else {
            for (Clase clase : clases) {
                String tipo = clase instanceof ClaseGrupal ? "GRUPAL" : "INDIVIDUAL";
                System.out.println("\n[" + tipo + "] " + clase.getId());
                System.out.println("Instrumento: " + clase.getInstrumento());
                System.out.println("Nivel: " + clase.getNivel());
                System.out.println("Horario: " + clase.getDiaSemana() + " " +
                        clase.getHoraInicio() + "-" + clase.getHoraFin());
                System.out.println("Profesor: " + (clase.getTheProfesor() != null ?
                        clase.getTheProfesor().getNombre() : "Sin asignar"));
                System.out.println("Aula: " + (clase.getTheAula() != null ?
                        clase.getTheAula().getNombre() : "Sin asignar"));
                System.out.println("Activa: " + (clase.isActiva() ? "Sí" : "No"));

                if (clase instanceof ClaseGrupal) {
                    ClaseGrupal grupal = (ClaseGrupal) clase;
                    System.out.println("Capacidad: " + grupal.getCapacidadActual() + "/" +
                            grupal.getCapacidadMaxima());
                }
                System.out.println("---");
            }
        }
        presionEnterParaContinuar();
    }

    private static void crearClaseGrupal() {
        System.out.println("\n=== CREAR CLASE GRUPAL ===");

        System.out.print("ID: ");
        String id = scanner.nextLine();

        System.out.println("\nInstrumentos:");
        TipoInstrumento[] instrumentos = TipoInstrumento.values();
        for (int i = 0; i < instrumentos.length; i++) {
            System.out.println((i+1) + ". " + instrumentos[i]);
        }
        System.out.print("Seleccione: ");
        int idxInst = leerEntero() - 1;

        System.out.print("Nivel (1-10): ");
        int nivel = leerEntero();

        System.out.print("Día de la semana: ");
        String dia = scanner.nextLine();

        System.out.print("Hora inicio (HH:MM): ");
        String horaInicio = scanner.nextLine();

        System.out.print("Hora fin (HH:MM): ");
        String horaFin = scanner.nextLine();

        System.out.print("Capacidad máxima: ");
        int capacidad = leerEntero();

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        if (idxInst >= 0 && idxInst < instrumentos.length) {
            ClaseGrupal nueva = new ClaseGrupal(
                    capacidad,
                    0,
                    capacidad,
                    descripcion,
                    id,
                    dia + " " + horaInicio + "-" + horaFin,
                    dia,
                    horaInicio,
                    horaFin,
                    instrumentos[idxInst],
                    nivel,
                    true
            );

            if (claseController.crearClaseGrupal(nueva)) {
                System.out.println("\n✓ Clase grupal creada correctamente");
            } else {
                System.out.println("\n❌ Error: La clase ya existe");
            }
        }
        presionEnterParaContinuar();
    }

    private static void crearClaseIndividual() {
        System.out.println("\n=== CREAR CLASE INDIVIDUAL ===");

        System.out.print("ID: ");
        String id = scanner.nextLine();

        System.out.println("\nInstrumentos:");
        TipoInstrumento[] instrumentos = TipoInstrumento.values();
        for (int i = 0; i < instrumentos.length; i++) {
            System.out.println((i+1) + ". " + instrumentos[i]);
        }
        System.out.print("Seleccione: ");
        int idxInst = leerEntero() - 1;

        System.out.print("Nivel (1-10): ");
        int nivel = leerEntero();

        System.out.print("Día de la semana: ");
        String dia = scanner.nextLine();

        System.out.print("Hora inicio (HH:MM): ");
        String horaInicio = scanner.nextLine();

        System.out.print("Hora fin (HH:MM): ");
        String horaFin = scanner.nextLine();

        System.out.print("Tema específico: ");
        String tema = scanner.nextLine();

        System.out.print("Objetivos: ");
        String objetivos = scanner.nextLine();

        if (idxInst >= 0 && idxInst < instrumentos.length) {
            ClaseIndividual nueva = new ClaseIndividual(
                    tema,
                    objetivos,
                    "",
                    id,
                    dia + " " + horaInicio + "-" + horaFin,
                    dia,
                    horaInicio,
                    horaFin,
                    instrumentos[idxInst],
                    nivel,
                    true
            );

            if (claseController.crearClaseIndividual(nueva)) {
                System.out.println("\n✓ Clase individual creada correctamente");
            } else {
                System.out.println("\n❌ Error: La clase ya existe");
            }
        }
        presionEnterParaContinuar();
    }

    private static void asignarProfesorAClase() {
        System.out.print("\nID de la clase: ");
        String claseId = scanner.nextLine();

        System.out.print("ID del profesor: ");
        String profId = scanner.nextLine();

        Profesor profesor = profesorController.buscarProfesor(profId);

        if (profesor != null) {
            if (claseController.asignarProfesorAClase(claseId, profesor)) {
                System.out.println("\n✓ Profesor asignado correctamente");
            } else {
                System.out.println("\n❌ Error: Conflicto de horario o clase no encontrada");
            }
        } else {
            System.out.println("\n❌ Profesor no encontrado");
        }
        presionEnterParaContinuar();
    }

    private static void asignarAulaAClase() {
        System.out.print("\nID de la clase: ");
        String claseId = scanner.nextLine();

        System.out.print("ID del aula: ");
        String aulaId = scanner.nextLine();

        Aula aula = aulaController.buscarAula(aulaId);

        if (aula != null) {
            if (claseController.asignarAulaAClase(claseId, aula)) {
                System.out.println("\n✓ Aula asignada correctamente");
            } else {
                System.out.println("\n❌ Error: Conflicto de aula o clase no encontrada");
            }
        } else {
            System.out.println("\n❌ Aula no encontrada");
        }
        presionEnterParaContinuar();
    }

    private static void listarClasesPorInstrumento() {
        System.out.println("\nInstrumentos:");
        TipoInstrumento[] instrumentos = TipoInstrumento.values();
        for (int i = 0; i < instrumentos.length; i++) {
            System.out.println((i+1) + ". " + instrumentos[i]);
        }
        System.out.print("\nSeleccione: ");
        int idx = leerEntero() - 1;

        if (idx >= 0 && idx < instrumentos.length) {
            List<Clase> clases = claseController.obtenerClasesPorInstrumento(instrumentos[idx]);
            System.out.println("\n=== CLASES DE " + instrumentos[idx] + " ===");
            for (Clase clase : clases) {
                String tipo = clase instanceof ClaseGrupal ? "Grupal" : "Individual";
                System.out.println("• [" + tipo + "] " + clase.getId() + " - Nivel " +
                        clase.getNivel() + " - " + clase.getHorario());
            }
        }
        presionEnterParaContinuar();
    }

    private static void listarClasesConCupos() {
        List<Clase> conCupos = claseController.obtenerClasesConCuposDisponibles();

        System.out.println("\n=== CLASES CON CUPOS DISPONIBLES ===");
        for (Clase clase : conCupos) {
            ClaseGrupal grupal = (ClaseGrupal) clase;
            System.out.println("• " + clase.getId() + " - " + clase.getInstrumento() +
                    " - Cupos: " + grupal.getCuposDisponibles());
        }
        presionEnterParaContinuar();
    }

    // ==================== GESTIÓN AULAS ====================

    private static void menuGestionAulas() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║          GESTIÓN DE AULAS              ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. Listar todas las aulas");
        System.out.println("2. Agregar aula");
        System.out.println("3. Modificar aula");
        System.out.println("4. Eliminar aula");
        System.out.println("5. Ver aulas disponibles");
        System.out.println("6. Ver ocupación de aulas");
        System.out.println("7. Volver");
        System.out.print("\nSeleccione una opción: ");

        int opcion = leerEntero();

        switch (opcion) {
            case 1: listarAulas(); break;
            case 2: agregarAula(); break;
            case 3: modificarAula(); break;
            case 4: eliminarAula(); break;
            case 5: listarAulasDisponibles(); break;
            case 6: verOcupacionAulas(); break;
            case 7: break;
            default: System.out.println("❌ Opción inválida");
        }
    }

    private static void listarAulas() {
        List<Aula> aulas = aulaController.obtenerListaAulas();

        System.out.println("\n=== LISTA DE AULAS ===");
        if (aulas.isEmpty()) {
            System.out.println("No hay aulas registradas");
        } else {
            for (Aula aula : aulas) {
                System.out.println("\nCódigo: " + aula.getCodigo());
                System.out.println("Nombre: " + aula.getNombre());
                System.out.println("Ubicación: " + aula.getUbicacion());
                System.out.println("Capacidad: " + aula.getCapacidad());
                System.out.println("Disponible: " + (aula.isDisponible() ? "Sí" : "No"));
                System.out.println("Clases asignadas: " + aulaController.contarClasesAsignadas(aula.getId()));
                System.out.println("---");
            }
        }
        presionEnterParaContinuar();
    }

    private static void agregarAula() {
        System.out.println("\n=== AGREGAR AULA ===");

        System.out.print("ID: ");
        String id = scanner.nextLine();

        System.out.print("Código: ");
        String codigo = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ubicación: ");
        String ubicacion = scanner.nextLine();

        System.out.print("Capacidad: ");
        int capacidad = leerEntero();

        Aula nueva = new Aula(id, codigo, nombre, ubicacion, capacidad, true);

        if (aulaController.agregarAula(nueva)) {
            System.out.println("\n✓ Aula agregada correctamente");
        } else {
            System.out.println("\n❌ Error: El aula ya existe");
        }
        presionEnterParaContinuar();
    }

    private static void modificarAula() {
        System.out.print("\nIngrese ID del aula: ");
        String id = scanner.nextLine();

        Aula existente = aulaController.buscarAula(id);
        if (existente == null) {
            System.out.println("\n❌ Aula no encontrada");
            presionEnterParaContinuar();
            return;
        }

        System.out.println("\n=== MODIFICAR AULA ===");
        System.out.print("Nombre [" + existente.getNombre() + "]: ");
        String nombre = scanner.nextLine();
        if (nombre.isEmpty()) nombre = existente.getNombre();

        System.out.print("Disponible (s/n) [" + (existente.isDisponible() ? "s" : "n") + "]: ");
        String dispStr = scanner.nextLine();
        boolean disponible = dispStr.isEmpty() ? existente.isDisponible() : dispStr.equalsIgnoreCase("s");

        Aula actualizada = new Aula(
                id,
                existente.getCodigo(),
                nombre,
                existente.getUbicacion(),
                existente.getCapacidad(),
                disponible
        );

        if (aulaController.actualizarAula(id, actualizada)) {
            System.out.println("\n✓ Aula modificada correctamente");
        }
        presionEnterParaContinuar();
    }

    private static void eliminarAula() {
        System.out.print("\nIngrese ID del aula: ");
        String id = scanner.nextLine();

        if (aulaController.eliminarAula(id)) {
            System.out.println("\n✓ Aula eliminada");
        } else {
            System.out.println("\n❌ Error al eliminar");
        }
        presionEnterParaContinuar();
    }

    private static void listarAulasDisponibles() {
        List<Aula> disponibles = aulaController.obtenerAulasDisponibles();

        System.out.println("\n=== AULAS DISPONIBLES ===");
        for (Aula aula : disponibles) {
            System.out.println("• " + aula.getNombre() + " (" + aula.getCodigo() +
                    ") - Capacidad: " + aula.getCapacidad());
        }
        presionEnterParaContinuar();
    }

    private static void verOcupacionAulas() {
        String reporte = administradorController.generarReporteOcupacionAulas();
        System.out.println("\n" + reporte);
        presionEnterParaContinuar();
    }

    // ==================== GESTIÓN INSCRIPCIONES ====================

    private static void menuGestionInscripciones() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      GESTIÓN DE INSCRIPCIONES          ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. Listar todas las inscripciones");
        System.out.println("2. Inscribir estudiante en curso");
        System.out.println("3. Cancelar inscripción");
        System.out.println("4. Aprobar inscripción");
        System.out.println("5. Ver inscripciones por estudiante");
        System.out.println("6. Ver inscripciones por curso");
        System.out.println("7. Volver");
        System.out.print("\nSeleccione una opción: ");

        int opcion = leerEntero();

        switch (opcion) {
            case 1: listarInscripciones(); break;
            case 2: inscribirEstudiante(); break;
            case 3: cancelarInscripcion(); break;
            case 4: aprobarInscripcion(); break;
            case 5: verInscripcionesPorEstudiante(); break;
            case 6: verInscripcionesPorCurso(); break;
            case 7: break;
            default: System.out.println("❌ Opción inválida");
        }
    }

    private static void listarInscripciones() {
        List<Inscripcion> inscripciones = inscripcionController.obtenerTodasInscripciones();

        System.out.println("\n=== LISTA DE INSCRIPCIONES ===");
        if (inscripciones.isEmpty()) {
            System.out.println("No hay inscripciones registradas");
        } else {
            for (Inscripcion insc : inscripciones) {
                System.out.println("\nID: " + insc.getId());
                System.out.println("Estudiante: " + insc.getTheEstudiante().getNombre());
                System.out.println("Curso: " + insc.getTheCurso().getNombre());
                System.out.println("Fecha: " + insc.getFechaInscripcion());
                System.out.println("Estado: " + insc.getEstado());
                System.out.println("Activa: " + (insc.isActiva() ? "Sí" : "No"));
                if (insc.getCalificacionFinal() > 0) {
                    System.out.println("Calificación: " + insc.getCalificacionFinal());
                }
                System.out.println("---");
            }
        }
        presionEnterParaContinuar();
    }

    private static void inscribirEstudiante() {
        System.out.println("\n=== INSCRIBIR ESTUDIANTE ===");

        System.out.print("ID del estudiante: ");
        String estudianteId = scanner.nextLine();

        System.out.print("ID del curso: ");
        String cursoId = scanner.nextLine();

        if (inscripcionController.inscribirEstudiante(
                estudianteController.buscarEstudiante(estudianteId),
                cursoController.buscarCurso(cursoId))) {
            System.out.println("\n✓ Estudiante inscrito correctamente");
        } else {
            System.out.println("\n❌ Error en la inscripción");
        }
        presionEnterParaContinuar();
    }

    private static void cancelarInscripcion() {
        System.out.print("\nID de la inscripción: ");
        String id = scanner.nextLine();

        if (inscripcionController.cancelarInscripcion(id)) {
            System.out.println("\n✓ Inscripción cancelada");
        } else {
            System.out.println("\n❌ Error al cancelar");
        }
        presionEnterParaContinuar();
    }

    private static void aprobarInscripcion() {
        System.out.print("\nID de la inscripción: ");
        String id = scanner.nextLine();

        System.out.print("Calificación final: ");
        double calificacion = leerDouble();

        if (inscripcionController.aprobarInscripcion(id, calificacion)) {
            System.out.println("\n✓ Inscripción aprobada");
        } else {
            System.out.println("\n❌ Error al aprobar");
        }
        presionEnterParaContinuar();
    }

    private static void verInscripcionesPorEstudiante() {
        System.out.print("\nID del estudiante: ");
        String id = scanner.nextLine();

        List<Inscripcion> inscripciones = inscripcionController.obtenerInscripcionesPorEstudiante(id);

        System.out.println("\n=== INSCRIPCIONES DEL ESTUDIANTE ===");
        for (Inscripcion insc : inscripciones) {
            System.out.println("• " + insc.getTheCurso().getNombre() + " - " + insc.getEstado());
        }
        presionEnterParaContinuar();
    }

    private static void verInscripcionesPorCurso() {
        System.out.print("\nID del curso: ");
        String id = scanner.nextLine();

        String reporte = inscripcionController.generarReporteInscripcionesCurso(id);
        System.out.println("\n" + reporte);
        presionEnterParaContinuar();
    }

    // ==================== REPORTES ====================

    private static void menuReportes() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║             REPORTES                   ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. Reporte de asistencia por curso");
        System.out.println("2. Reporte de progreso por instrumento");
        System.out.println("3. Reporte de ocupación de aulas");
        System.out.println("4. Reporte de carga docente");
        System.out.println("5. Reporte de estudiante");
        System.out.println("6. Volver");
        System.out.print("\nSeleccione una opción: ");

        int opcion = leerEntero();

        switch (opcion) {
            case 1: reporteAsistenciaCurso(); break;
            case 2: reporteProgresoPorInstrumento(); break;
            case 3: reporteOcupacionAulas(); break;
            case 4: reporteCargaDocente(); break;
            case 5: reporteEstudiante(); break;
            case 6: break;
            default: System.out.println("❌ Opción inválida");
        }
    }

    private static void reporteAsistenciaCurso() {
        System.out.print("\nID del curso: ");
        String cursoId = scanner.nextLine();

        String reporte = administradorController.generarReporteAsistenciaPorCurso(cursoId);
        System.out.println("\n" + reporte);
        presionEnterParaContinuar();
    }

    private static void reporteProgresoPorInstrumento() {
        System.out.println("\nInstrumentos:");
        TipoInstrumento[] instrumentos = TipoInstrumento.values();
        for (int i = 0; i < instrumentos.length; i++) {
            System.out.println((i+1) + ". " + instrumentos[i]);
        }
        System.out.print("\nSeleccione: ");
        int idx = leerEntero() - 1;

        System.out.print("Nivel: ");
        int nivel = leerEntero();

        if (idx >= 0 && idx < instrumentos.length) {
            String reporte = administradorController.generarReporteProgresoPorInstrumento(
                    instrumentos[idx], nivel);
            System.out.println("\n" + reporte);
        }
        presionEnterParaContinuar();
    }

    private static void reporteOcupacionAulas() {
        String reporte = administradorController.generarReporteOcupacionAulas();
        System.out.println("\n" + reporte);
        presionEnterParaContinuar();
    }

    private static void reporteCargaDocente() {
        String reporte = administradorController.generarReporteCargaDocente();
        System.out.println("\n" + reporte);
        presionEnterParaContinuar();
    }

    private static void reporteEstudiante() {
        System.out.print("\nID del estudiante: ");
        String id = scanner.nextLine();

        String reporte = administradorController.generarReporteEstudiante(id);
        System.out.println("\n" + reporte);
        presionEnterParaContinuar();
    }

    // ==================== ESTADÍSTICAS ====================

    private static void mostrarEstadisticas() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║          ESTADÍSTICAS GENERALES        ║");
        System.out.println("╚════════════════════════════════════════╝");

        int totalEstudiantes = sistema.getListEstudiantes().size();
        int estudiantesActivos = (int) sistema.getListEstudiantes().stream()
                .filter(Estudiante::getActivo).count();

        int totalProfesores = sistema.getListProfesores().size();
        int profesoresActivos = (int) sistema.getListProfesores().stream()
                .filter(Profesor::isActivo).count();

        int totalCursos = sistema.getListCursos().size();
        int cursosActivos = (int) sistema.getListCursos().stream()
                .filter(c -> c.getEstado() == EstadoCurso.ACTIVO).count();

        int totalClases = sistema.getListClases().size();
        int clasesActivas = (int) sistema.getListClases().stream()
                .filter(Clase::isActiva).count();

        int totalAulas = sistema.getListAulas().size();
        int aulasDisponibles = (int) sistema.getListAulas().stream()
                .filter(Aula::isDisponible).count();

        int totalInscripciones = sistema.getListInscripciones().size();
        int inscripcionesActivas = (int) sistema.getListInscripciones().stream()
                .filter(Inscripcion::isActiva).count();

        System.out.println("\nESTUDIANTES:");
        System.out.println("  Total: " + totalEstudiantes);
        System.out.println("  Activos: " + estudiantesActivos);

        System.out.println("\nPROFESORES:");
        System.out.println("  Total: " + totalProfesores);
        System.out.println("  Activos: " + profesoresActivos);

        System.out.println("\nCURSOS:");
        System.out.println("  Total: " + totalCursos);
        System.out.println("  Activos: " + cursosActivos);

        System.out.println("\nCLASES:");
        System.out.println("  Total: " + totalClases);
        System.out.println("  Activas: " + clasesActivas);

        System.out.println("\nAULAS:");
        System.out.println("  Total: " + totalAulas);
        System.out.println("  Disponibles: " + aulasDisponibles);

        System.out.println("\nINSCRIPCIONES:");
        System.out.println("  Total: " + totalInscripciones);
        System.out.println("  Activas: " + inscripcionesActivas);

        presionEnterParaContinuar();
    }

    // ==================== MENÚ PROFESOR ====================

    private static void menuProfesor() {
        Profesor profesor = (Profesor) usuarioActual;

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║          MENÚ PROFESOR                 ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("Bienvenido: " + profesor.getNombre());
        System.out.println("\n1. Ver mis clases");
        System.out.println("2. Ver mi horario");
        System.out.println("3. Ver estudiantes");
        System.out.println("4. Registrar asistencia");
        System.out.println("5. Evaluar estudiante");
        System.out.println("6. Ver mi perfil");
        System.out.println("7. Cerrar sesión");
        System.out.print("\nSeleccione una opción: ");

        int opcion = leerEntero();

        switch (opcion) {
            case 1: verMisClasesProfesor(profesor); break;
            case 2: verMiHorarioProfesor(profesor); break;
            case 3: verEstudiantesProfesor(profesor); break;
            case 4: registrarAsistenciaProfesor(profesor); break;
            case 5: evaluarEstudianteProfesor(profesor); break;
            case 6: verPerfilProfesor(profesor); break;
            case 7:
                usuarioActual = null;
                System.out.println("\n✓ Sesión cerrada");
                break;
            default: System.out.println("❌ Opción inválida");
        }
    }

    private static void verMisClasesProfesor(Profesor profesor) {
        System.out.println("\n=== MIS CLASES ===");
        List<Clase> clases = profesor.getTheClasesAsignadas();

        if (clases.isEmpty()) {
            System.out.println("No tiene clases asignadas");
        } else {
            for (Clase clase : clases) {
                String tipo = clase instanceof ClaseGrupal ? "GRUPAL" : "INDIVIDUAL";
                System.out.println("\n[" + tipo + "] " + clase.getId());
                System.out.println("Instrumento: " + clase.getInstrumento());
                System.out.println("Nivel: " + clase.getNivel());
                System.out.println("Horario: " + clase.getHorario());
                System.out.println("Aula: " + (clase.getTheAula() != null ?
                        clase.getTheAula().getNombre() : "Sin asignar"));

                if (clase instanceof ClaseGrupal) {
                    ClaseGrupal grupal = (ClaseGrupal) clase;
                    System.out.println("Estudiantes: " + grupal.getTheEstudiantesInscritos().size() +
                            "/" + grupal.getCapacidadMaxima());
                }
                System.out.println("---");
            }
        }
        presionEnterParaContinuar();
    }

    private static void verMiHorarioProfesor(Profesor profesor) {
        String horario = profesorController.generarHorarioProfesor(profesor.getId());
        System.out.println("\n" + horario);
        presionEnterParaContinuar();
    }

    private static void verEstudiantesProfesor(Profesor profesor) {
        System.out.println("\n=== MIS ESTUDIANTES ===");

        int totalEstudiantes = profesorController.contarEstudiantesTotalProfesor(profesor.getId());
        System.out.println("Total de estudiantes: " + totalEstudiantes + "\n");

        for (Clase clase : profesor.getTheClasesAsignadas()) {
            System.out.println("Clase: " + clase.getId() + " - " + clase.getInstrumento());

            if (clase instanceof ClaseGrupal) {
                ClaseGrupal grupal = (ClaseGrupal) clase;
                for (Estudiante est : grupal.getTheEstudiantesInscritos()) {
                    System.out.println("  • " + est.getNombre() + " (" + est.getMatricula() + ")");
                }
            } else if (clase instanceof ClaseIndividual) {
                ClaseIndividual individual = (ClaseIndividual) clase;
                if (individual.getTheEstudiante() != null) {
                    System.out.println("  • " + individual.getTheEstudiante().getNombre());
                }
            }
            System.out.println();
        }
        presionEnterParaContinuar();
    }

    private static void registrarAsistenciaProfesor(Profesor profesor) {
        System.out.println("\n=== REGISTRAR ASISTENCIA ===");

        System.out.print("ID de la clase: ");
        String claseId = scanner.nextLine();

        Clase clase = claseController.buscarClase(claseId);
        if (clase == null || !clase.getTheProfesor().getId().equals(profesor.getId())) {
            System.out.println("\n❌ Clase no encontrada o no es su clase");
            presionEnterParaContinuar();
            return;
        }

        System.out.print("ID del estudiante: ");
        String estudianteId = scanner.nextLine();

        Estudiante estudiante = estudianteController.buscarEstudiante(estudianteId);
        if (estudiante == null) {
            System.out.println("\n❌ Estudiante no encontrado");
            presionEnterParaContinuar();
            return;
        }

        System.out.print("¿Presente? (s/n): ");
        boolean presente = scanner.nextLine().equalsIgnoreCase("s");

        clase.registrarAsistencia(estudiante, presente);
        System.out.println("\n✓ Asistencia registrada");
        presionEnterParaContinuar();
    }

    private static void evaluarEstudianteProfesor(Profesor profesor) {
        System.out.println("\n=== EVALUAR ESTUDIANTE ===");

        System.out.print("ID de la clase: ");
        String claseId = scanner.nextLine();

        Clase clase = claseController.buscarClase(claseId);
        if (clase == null || !clase.getTheProfesor().getId().equals(profesor.getId())) {
            System.out.println("\n❌ Clase no encontrada o no es su clase");
            presionEnterParaContinuar();
            return;
        }

        System.out.print("ID del estudiante: ");
        String estudianteId = scanner.nextLine();

        Estudiante estudiante = estudianteController.buscarEstudiante(estudianteId);
        if (estudiante == null) {
            System.out.println("\n❌ Estudiante no encontrado");
            presionEnterParaContinuar();
            return;
        }

        System.out.print("Calificación (0-5): ");
        double calificacion = leerDouble();

        System.out.print("Comentarios: ");
        String comentarios = scanner.nextLine();

        clase.evaluarProgreso(estudiante, calificacion, comentarios);
        System.out.println("\n✓ Evaluación registrada");
        presionEnterParaContinuar();
    }

    private static void verPerfilProfesor(Profesor profesor) {
        System.out.println("\n=== MI PERFIL ===");
        System.out.println("Código: " + profesor.getCodigo());
        System.out.println("Nombre: " + profesor.getNombre());
        System.out.println("Email: " + profesor.getEmail());
        System.out.println("Teléfono: " + profesor.getTelefono());
        System.out.println("Especialidad: " + profesor.getEspecialidad());
        System.out.println("Fecha contratación: " + profesor.getFechaContratacion());

        System.out.println("\nInstrumentos:");
        for (TipoInstrumento inst : profesor.getTheInstrumentosImpartidos()) {
            System.out.println("  • " + inst);
        }

        System.out.println("\nEstadísticas:");
        System.out.println("  Clases asignadas: " + profesor.getTheClasesAsignadas().size());
        System.out.println("  Clases grupales: " + profesorController.contarClasesGrupalesProfesor(profesor.getId()));
        System.out.println("  Clases individuales: " + profesorController.contarClasesIndividualesProfesor(profesor.getId()));
        System.out.println("  Total estudiantes: " + profesorController.contarEstudiantesTotalProfesor(profesor.getId()));

        presionEnterParaContinuar();
    }

    // ==================== MENÚ ESTUDIANTE ====================

    private static void menuEstudiante() {
        Estudiante estudiante = (Estudiante) usuarioActual;

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         MENÚ ESTUDIANTE                ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("Bienvenido: " + estudiante.getNombre());
        System.out.println("\n1. Ver mis inscripciones");
        System.out.println("2. Ver cursos disponibles");
        System.out.println("3. Ver mi historial de asistencia");
        System.out.println("4. Ver mis evaluaciones");
        System.out.println("5. Ver niveles aprobados");
        System.out.println("6. Ver mi promedio");
        System.out.println("7. Ver mi perfil");
        System.out.println("8. Cerrar sesión");
        System.out.print("\nSeleccione una opción: ");

        int opcion = leerEntero();

        switch (opcion) {
            case 1: verMisInscripcionesEstudiante(estudiante); break;
            case 2: verCursosDisponiblesEstudiante(); break;
            case 3: verHistorialAsistenciaEstudiante(estudiante); break;
            case 4: verEvaluacionesEstudiante(estudiante); break;
            case 5: verNivelesAprobadosEstudiante(estudiante); break;
            case 6: verPromedioEstudiante(estudiante); break;
            case 7: verPerfilEstudiante(estudiante); break;
            case 8:
                usuarioActual = null;
                System.out.println("\n✓ Sesión cerrada");
                break;
            default: System.
                    existente.getUsuario(),
                    existente.getContrasenia()
        );

                if (estudianteController.actualizarEstudiante(id, actualizado)) {
                    System.out.println("\n✓ Estudiante actualizado correctamente");
                } else {
                    System.out.println("\n❌ Error al actualizar");
                }
                presionEnterParaContinuar();
        }

        private static void eliminarEstudiante() {
            System.out.print("\nIngrese ID del estudiante a eliminar: ");
            String id = scanner.nextLine();

            Estudiante est = estudianteController.buscarEstudiante(id);
            if (est != null) {
                System.out.println("\n¿Está seguro de eliminar a " + est.getNombre() + "? (s/n): ");
                String confirmacion = scanner.nextLine();

                if (confirmacion.equalsIgnoreCase("s")) {
                    if (estudianteController.eliminarEstudiante(id)) {
                        System.out.println("\n✓ Estudiante eliminado");
                    } else {
                        System.out.println("\n❌ Error al eliminar");
                    }
                }
            } else {
                System.out.println("\n❌ Estudiante no encontrado");
            }
            presionEnterParaContinuar();
        }

        private static void listarEstudiantesActivos() {
            List<Estudiante> activos = estudianteController.obtenerEstudiantesActivos();

            System.out.println("\n=== ESTUDIANTES ACTIVOS ===");
            if (activos.isEmpty()) {
                System.out.println("No hay estudiantes activos");
            } else {
                for (Estudiante est : activos) {
                    System.out.println("• " + est.getNombre() + " (" + est.getMatricula() + ")");
                }
            }
            presionEnterParaContinuar();
        }

        // ==================== GESTIÓN PROFESORES ====================

        private static void menuGestionProfesores() {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║       GESTIÓN DE PROFESORES            ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("1. Listar todos los profesores");
            System.out.println("2. Buscar profesor");
            System.out.println("3. Agregar nuevo profesor");
            System.out.println("4. Actualizar profesor");
            System.out.println("5. Eliminar profesor");
            System.out.println("6. Gestionar instrumentos de profesor");
            System.out.println("7. Ver carga docente");
            System.out.println("8. Volver");
            System.out.print("\nSeleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1: listarProfesores(); break;
                case 2: buscarProfesor(); break;
                case 3: agregarProfesor(); break;
                case 4: actualizarProfesor(); break;
                case 5: eliminarProfesor(); break;
                case 6: gestionarInstrumentosProfesor(); break;
                case 7: verCargaDocente(); break;
                case 8: break;
                default: System.out.println("❌ Opción inválida");
            }
        }

        private static void listarProfesores() {
            List<Profesor> profesores = profesorController.obtenerListaProfesores();

            System.out.println("\n=== LISTA DE PROFESORES ===");
            if (profesores.isEmpty()) {
                System.out.println("No hay profesores registrados");
            } else {
                for (Profesor prof : profesores) {
                    System.out.println("\nCódigo: " + prof.getCodigo());
                    System.out.println("Nombre: " + prof.getNombre());
                    System.out.println("Especialidad: " + prof.getEspecialidad());
                    System.out.println("Email: " + prof.getEmail());
                    System.out.println("Clases asignadas: " + prof.getTheClasesAsignadas().size());
                    System.out.println("Instrumentos: " + prof.getTheInstrumentosImpartidos().size());
                    System.out.println("---");
                }
            }
            presionEnterParaContinuar();
        }

        private static void buscarProfesor() {
            System.out.print("\nIngrese código del profesor: ");
            String codigo = scanner.nextLine();

            Profesor prof = profesorController.buscarProfesorPorCodigo(codigo);

            if (prof != null) {
                System.out.println("\n=== INFORMACIÓN DEL PROFESOR ===");
                System.out.println("Código: " + prof.getCodigo());
                System.out.println("Nombre: " + prof.getNombre());
                System.out.println("Especialidad: " + prof.getEspecialidad());
                System.out.println("Email: " + prof.getEmail());
                System.out.println("Teléfono: " + prof.getTelefono());
                System.out.println("Estado: " + (prof.isActivo() ? "Activo" : "Inactivo"));

                System.out.println("\nInstrumentos que imparte:");
                for (TipoInstrumento inst : prof.getTheInstrumentosImpartidos()) {
                    System.out.println("  • " + inst);
                }

                System.out.println("\nClases asignadas: " + prof.getTheClasesAsignadas().size());
                System.out.println("Clases grupales: " + profesorController.contarClasesGrupalesProfesor(prof.getId()));
                System.out.println("Clases individuales: " + profesorController.contarClasesIndividualesProfesor(prof.getId()));
                System.out.println("Total estudiantes: " + profesorController.contarEstudiantesTotalProfesor(prof.getId()));
            } else {
                System.out.println("\n❌ Profesor no encontrado");
            }
            presionEnterParaContinuar();
        }

        private static void agregarProfesor() {
            System.out.println("\n=== AGREGAR PROFESOR ===");

            System.out.print("ID: ");
            String id = scanner.nextLine();

            System.out.print("Código: ");
            String codigo = scanner.nextLine();

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine();

            System.out.print("Dirección: ");
            String direccion = scanner.nextLine();

            System.out.print("Especialidad: ");
            String especialidad = scanner.nextLine();

            System.out.print("Usuario: ");
            String usuario = scanner.nextLine();

            System.out.print("Contraseña: ");
            String contrasena = scanner.nextLine();

            Profesor nuevo = new Profesor(
                    codigo,
                    especialidad,
                    LocalDate.now().toString(),
                    true,
                    id,
                    nombre,
                    email,
                    telefono,
                    direccion,
                    LocalDate.of(1980, 1, 1),
                    usuario,
                    contrasena
            );

            if (profesorController.agregarProfesor(nuevo)) {
                System.out.println("\n✓ Profesor agregado correctamente");
            } else {
                System.out.println("\n❌ Error: El profesor ya existe");
            }
            presionEnterParaContinuar();
        }

        private static void actualizarProfesor() {
            System.out.print("\nIngrese ID del profesor: ");
            String id = scanner.nextLine();

            Profesor existente = profesorController.buscarProfesor(id);
            if (existente == null) {
                System.out.println("\n❌ Profesor no encontrado");
                presionEnterParaContinuar();
                return;
            }

            System.out.println("\n=== ACTUALIZAR PROFESOR ===");
            System.out.println("(Presione Enter para mantener el valor actual)");

            System.out.print("Nombre [" + existente.getNombre() + "]: ");
            String nombre = scanner.nextLine();
            if (nombre.isEmpty()) nombre = existente.getNombre();

            System.out.print("Especialidad [" + existente.getEspecialidad() + "]: ");
            String especialidad = scanner.nextLine();
            if (especialidad.isEmpty()) especialidad = existente.getEspecialidad();

            System.out.print("Activo (s/n) [" + (existente.isActivo() ? "s" : "n") + "]: ");
            String activoStr = scanner.nextLine();
            boolean activo = activoStr.isEmpty() ? existente.isActivo() : activoStr.equalsIgnoreCase("s");

            Profesor actualizado = new Profesor(
                    existente.getCodigo(),
                    especialidad,
                    existente.getFechaContratacion(),
                    activo,
                    id,
                    nombre,
                    existente.getEmail(),
                    existente.getTelefono(),
                    existente.getDireccion(),
                    existente.getFechaNacimiento()

    public <__TMP__> __TMP__ openAdministradorDashboard() {
    }