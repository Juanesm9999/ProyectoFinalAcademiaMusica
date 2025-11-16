package co.edu.uniquindio.poo.proyectofinalmusica.Controller;

import co.edu.uniquindio.poo.proyectofinalmusica.model.*;

/**
 * Controlador para la gestión de autenticación y login del sistema
 * Maneja inicio de sesión, registro y validación de usuarios
 *
 * @author Juan Esteban
 * @version 1.0
 */
public class LoginController {
    private SistemaAcademia sistemaAcademia;

    /**
     * Constructor del controlador de login
     * @param sistemaAcademia instancia del sistema académico
     */
    public LoginController(SistemaAcademia sistemaAcademia) {
        this.sistemaAcademia = sistemaAcademia;
    }

    /**
     * Intenta iniciar sesión con las credenciales proporcionadas
     * Busca el usuario en las tres listas según el tipo de rol
     *
     * @param usuario nombre de usuario
     * @param contrasena contraseña del usuario
     * @param tipoRol tipo de usuario (ESTUDIANTE, PROFESOR, ADMINISTRADOR)
     * @return Persona si las credenciales son válidas, null en caso contrario
     */
    public Persona iniciarSesion(String usuario, String contrasena, String tipoRol) {
        // Validar que los parámetros no sean nulos
        if (usuario == null || contrasena == null || tipoRol == null) {
            return null;
        }

        // Delegar la autenticación al sistema
        return sistemaAcademia.iniciarSesion(usuario.trim(), contrasena, tipoRol.toUpperCase());
    }

    /**
     * Verifica si un nombre de usuario ya existe en el sistema
     * Busca en las tres listas: estudiantes, profesores y administradores
     *
     * @param usuario nombre de usuario a verificar
     * @return true si el usuario existe, false en caso contrario
     */
    public boolean verificarUsuarioExiste(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) {
            return false;
        }
        return sistemaAcademia.verificarUsuarioExiste(usuario.trim());
    }

    /**
     * Valida el formato de un email
     * Verifica que contenga @ y punto
     *
     * @param email email a validar
     * @return true si el formato es válido, false en caso contrario
     */
    public boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        String emailTrim = email.trim();
        // Validación básica: debe contener @ y punto después del @
        int posicionArroba = emailTrim.indexOf('@');
        int posicionPunto = emailTrim.lastIndexOf('.');

        return posicionArroba > 0 &&
                posicionPunto > posicionArroba + 1 &&
                posicionPunto < emailTrim.length() - 1;
    }

    /**
     * Valida la fortaleza de una contraseña
     * Requisitos mínimos: al menos 6 caracteres
     *
     * @param contrasena contraseña a validar
     * @return true si la contraseña cumple los requisitos mínimos
     */
    public boolean validarContrasena(String contrasena) {
        if (contrasena == null || contrasena.length() < 6) {
            return false;
        }
        return true;
    }

    /**
     * Valida una contraseña con requisitos más estrictos
     * Requisitos: mínimo 8 caracteres, al menos una mayúscula, una minúscula y un número
     *
     * @param contrasena contraseña a validar
     * @return true si cumple los requisitos estrictos
     */
    public boolean validarContrasenaSegura(String contrasena) {
        if (contrasena == null || contrasena.length() < 8) {
            return false;
        }

        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneNumero = false;

        for (char c : contrasena.toCharArray()) {
            if (Character.isUpperCase(c)) tieneMayuscula = true;
            if (Character.isLowerCase(c)) tieneMinuscula = true;
            if (Character.isDigit(c)) tieneNumero = true;
        }

        return tieneMayuscula && tieneMinuscula && tieneNumero;
    }

    /**
     * Verifica las credenciales y retorna el tipo de usuario
     * Útil para determinar el rol sin crear una sesión completa
     *
     * @param usuario nombre de usuario
     * @param contrasena contraseña
     * @return tipo de usuario (ESTUDIANTE, PROFESOR, ADMINISTRADOR) o null si no existe
     */
    public String obtenerTipoUsuario(String usuario, String contrasena) {
        // Verificar en estudiantes
        for (Estudiante est : sistemaAcademia.getListEstudiantes()) {
            if (est.getUsuario() != null && est.getUsuario().equals(usuario) &&
                    est.getContrasenia() != null && est.getContrasenia().equals(contrasena)) {
                return "ESTUDIANTE";
            }
        }

        // Verificar en profesores
        for (Profesor prof : sistemaAcademia.getListProfesores()) {
            if (prof.getUsuario() != null && prof.getUsuario().equals(usuario) &&
                    prof.getContrasenia() != null && prof.getContrasenia().equals(contrasena)) {
                return "PROFESOR";
            }
        }

        // Verificar en administradores
        for (Administrador admin : sistemaAcademia.getListAdministradores()) {
            if (admin.getUsuario() != null && admin.getUsuario().equals(usuario) &&
                    admin.getContrasenia() != null && admin.getContrasenia().equals(contrasena)) {
                return "ADMINISTRADOR";
            }
        }

        return null;
    }

    /**
     * Cambia la contraseña de un usuario
     * Verifica la contraseña actual antes de cambiarla
     *
     * @param usuario nombre de usuario
     * @param contrasenaActual contraseña actual del usuario
     * @param nuevaContrasena nueva contraseña
     * @return true si el cambio fue exitoso, false en caso contrario
     */
    public boolean cambiarContrasena(String usuario, String contrasenaActual, String nuevaContrasena) {
        // Validar nueva contraseña
        if (!validarContrasena(nuevaContrasena)) {
            return false;
        }

        // Buscar en estudiantes
        for (Estudiante est : sistemaAcademia.getListEstudiantes()) {
            if (est.getUsuario() != null && est.getUsuario().equals(usuario) &&
                    est.getContrasenia() != null && est.getContrasenia().equals(contrasenaActual)) {
                est.setContrasenia(nuevaContrasena);
                return true;
            }
        }

        // Buscar en profesores
        for (Profesor prof : sistemaAcademia.getListProfesores()) {
            if (prof.getUsuario() != null && prof.getUsuario().equals(usuario) &&
                    prof.getContrasenia() != null && prof.getContrasenia().equals(contrasenaActual)) {
                prof.setContrasenia(nuevaContrasena);
                return true;
            }
        }

        // Buscar en administradores
        for (Administrador admin : sistemaAcademia.getListAdministradores()) {
            if (admin.getUsuario() != null && admin.getUsuario().equals(usuario) &&
                    admin.getContrasenia() != null && admin.getContrasenia().equals(contrasenaActual)) {
                admin.setContrasenia(nuevaContrasena);
                return true;
            }
        }

        return false;
    }

    /**
     * Recupera la contraseña enviando el email del usuario
     * (Simulación - en producción enviaría un email real)
     *
     * @param email email del usuario
     * @return nombre de usuario asociado al email, o null si no se encuentra
     */
    public String recuperarContrasena(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }

        // Buscar en estudiantes
        for (Estudiante est : sistemaAcademia.getListEstudiantes()) {
            if (est.getEmail() != null && est.getEmail().equalsIgnoreCase(email.trim())) {
                // En producción aquí se enviaría un email
                return est.getUsuario();
            }
        }

        // Buscar en profesores
        for (Profesor prof : sistemaAcademia.getListProfesores()) {
            if (prof.getEmail() != null && prof.getEmail().equalsIgnoreCase(email.trim())) {
                return prof.getUsuario();
            }
        }

        // Buscar en administradores
        for (Administrador admin : sistemaAcademia.getListAdministradores()) {
            if (admin.getEmail() != null && admin.getEmail().equalsIgnoreCase(email.trim())) {
                return admin.getUsuario();
            }
        }

        return null;
    }

    /**
     * Busca una persona por su nombre de usuario
     * Útil para obtener información completa del usuario
     *
     * @param usuario nombre de usuario
     * @return Persona encontrada o null
     */
    public Persona buscarPersonaPorUsuario(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) {
            return null;
        }

        // Buscar en estudiantes
        for (Estudiante est : sistemaAcademia.getListEstudiantes()) {
            if (est.getUsuario() != null && est.getUsuario().equals(usuario)) {
                return est;
            }
        }

        // Buscar en profesores
        for (Profesor prof : sistemaAcademia.getListProfesores()) {
            if (prof.getUsuario() != null && prof.getUsuario().equals(usuario)) {
                return prof;
            }
        }

        // Buscar en administradores
        for (Administrador admin : sistemaAcademia.getListAdministradores()) {
            if (admin.getUsuario() != null && admin.getUsuario().equals(usuario)) {
                return admin;
            }
        }

        return null;
    }

    /**
     * Verifica si un usuario está activo en el sistema
     *
     * @param usuario nombre de usuario
     * @return true si está activo, false en caso contrario
     */
    public boolean verificarUsuarioActivo(String usuario) {
        Persona persona = buscarPersonaPorUsuario(usuario);

        if (persona instanceof Estudiante) {
            return ((Estudiante) persona).getActivo();
        } else if (persona instanceof Profesor) {
            return ((Profesor) persona).isActivo();
        } else if (persona instanceof Administrador) {
            return true; // Los administradores siempre están activos
        }

        return false;
    }

    /**
     * Genera un nombre de usuario sugerido basado en el nombre completo
     * Formato: primera letra del nombre + apellido (en minúsculas)
     *
     * @param nombreCompleto nombre completo de la persona
     * @return nombre de usuario sugerido
     */
    public String generarUsuarioSugerido(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            return "";
        }

        String[] partes = nombreCompleto.trim().split("\\s+");
        if (partes.length < 2) {
            return nombreCompleto.toLowerCase().replaceAll("\\s+", "");
        }

        // Primera letra del nombre + apellido
        String usuarioBase = partes[0].substring(0, 1).toLowerCase() +
                partes[partes.length - 1].toLowerCase();

        // Si el usuario ya existe, agregar un número
        String usuarioFinal = usuarioBase;
        int contador = 1;
        while (verificarUsuarioExiste(usuarioFinal)) {
            usuarioFinal = usuarioBase + contador;
            contador++;
        }

        return usuarioFinal;
    }

    /**
     * Valida todos los campos necesarios para el registro
     *
     * @param usuario nombre de usuario
     * @param contrasena contraseña
     * @param email email
     * @param nombre nombre completo
     * @return mensaje de error o null si todo es válido
     */
    public String validarDatosRegistro(String usuario, String contrasena, String email, String nombre) {
        if (usuario == null || usuario.trim().isEmpty()) {
            return "El nombre de usuario es obligatorio";
        }

        if (usuario.trim().length() < 4) {
            return "El nombre de usuario debe tener al menos 4 caracteres";
        }

        if (verificarUsuarioExiste(usuario.trim())) {
            return "El nombre de usuario ya está en uso";
        }

        if (!validarContrasena(contrasena)) {
            return "La contraseña debe tener al menos 6 caracteres";
        }

        if (!validarEmail(email)) {
            return "El formato del email no es válido";
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre completo es obligatorio";
        }

        return null; // Todo válido
    }

    /**
     * Cierra la sesión del usuario actual
     * Método para mantener registro o realizar acciones de auditoría
     */
    public void cerrarSesion() {
        // Aquí se podría agregar lógica adicional como:
        // - Registro de auditoría
        // - Guardado de cambios pendientes
        // - Limpieza de datos temporales
        System.out.println("Sesión cerrada exitosamente");
    }

    /**
     * Obtiene el sistema académico
     *
     * @return instancia del sistema académico
     */
    public SistemaAcademia getSistemaAcademia() {
        return sistemaAcademia;
    }

    /**
     * Establece el sistema académico
     *
     * @param sistemaAcademia instancia del sistema académico
     */
    public void setSistemaAcademia(SistemaAcademia sistemaAcademia) {
        this.sistemaAcademia = sistemaAcademia;
    }
}