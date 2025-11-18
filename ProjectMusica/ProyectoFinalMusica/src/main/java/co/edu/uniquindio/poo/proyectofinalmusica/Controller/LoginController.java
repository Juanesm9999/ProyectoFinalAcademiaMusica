package co.edu.uniquindio.poo.proyectofinalmusica.Controller;

import co.edu.uniquindio.poo.proyectofinalmusica.model.*;


public class LoginController {
    private SistemaAcademia sistemaAcademia;


    public LoginController(SistemaAcademia sistemaAcademia) {
        this.sistemaAcademia = sistemaAcademia;
    }


    public Persona iniciarSesion(String usuario, String contrasena, String tipoRol) {
        // Validar que los parámetros no sean nulos
        if (usuario == null || contrasena == null || tipoRol == null) {
            return null;
        }

        // Delegar la autenticación al sistema
        return sistemaAcademia.iniciarSesion(usuario.trim(), contrasena, tipoRol.toUpperCase());
    }


    public boolean verificarUsuarioExiste(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) {
            return false;
        }
        return sistemaAcademia.verificarUsuarioExiste(usuario.trim());
    }


    public boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        String emailTrim = email.trim();

        int posicionArroba = emailTrim.indexOf('@');
        int posicionPunto = emailTrim.lastIndexOf('.');

        return posicionArroba > 0 &&
                posicionPunto > posicionArroba + 1 &&
                posicionPunto < emailTrim.length() - 1;
    }


    public boolean validarContrasena(String contrasena) {
        if (contrasena == null || contrasena.length() < 6) {
            return false;
        }
        return true;
    }


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


    public boolean cambiarContrasena(String usuario, String contrasenaActual, String nuevaContrasena) {

        if (!validarContrasena(nuevaContrasena)) {
            return false;
        }


        for (Estudiante est : sistemaAcademia.getListEstudiantes()) {
            if (est.getUsuario() != null && est.getUsuario().equals(usuario) &&
                    est.getContrasenia() != null && est.getContrasenia().equals(contrasenaActual)) {
                est.setContrasenia(nuevaContrasena);
                return true;
            }
        }


        for (Profesor prof : sistemaAcademia.getListProfesores()) {
            if (prof.getUsuario() != null && prof.getUsuario().equals(usuario) &&
                    prof.getContrasenia() != null && prof.getContrasenia().equals(contrasenaActual)) {
                prof.setContrasenia(nuevaContrasena);
                return true;
            }
        }


        for (Administrador admin : sistemaAcademia.getListAdministradores()) {
            if (admin.getUsuario() != null && admin.getUsuario().equals(usuario) &&
                    admin.getContrasenia() != null && admin.getContrasenia().equals(contrasenaActual)) {
                admin.setContrasenia(nuevaContrasena);
                return true;
            }
        }

        return false;
    }


    public String recuperarContrasena(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }


        for (Estudiante est : sistemaAcademia.getListEstudiantes()) {
            if (est.getEmail() != null && est.getEmail().equalsIgnoreCase(email.trim())) {
                // En producción aquí se enviaría un email
                return est.getUsuario();
            }
        }


        for (Profesor prof : sistemaAcademia.getListProfesores()) {
            if (prof.getEmail() != null && prof.getEmail().equalsIgnoreCase(email.trim())) {
                return prof.getUsuario();
            }
        }


        for (Administrador admin : sistemaAcademia.getListAdministradores()) {
            if (admin.getEmail() != null && admin.getEmail().equalsIgnoreCase(email.trim())) {
                return admin.getUsuario();
            }
        }

        return null;
    }


    public Persona buscarPersonaPorUsuario(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) {
            return null;
        }


        for (Estudiante est : sistemaAcademia.getListEstudiantes()) {
            if (est.getUsuario() != null && est.getUsuario().equals(usuario)) {
                return est;
            }
        }


        for (Profesor prof : sistemaAcademia.getListProfesores()) {
            if (prof.getUsuario() != null && prof.getUsuario().equals(usuario)) {
                return prof;
            }
        }


        for (Administrador admin : sistemaAcademia.getListAdministradores()) {
            if (admin.getUsuario() != null && admin.getUsuario().equals(usuario)) {
                return admin;
            }
        }

        return null;
    }


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


    public String generarUsuarioSugerido(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            return "";
        }

        String[] partes = nombreCompleto.trim().split("\\s+");
        if (partes.length < 2) {
            return nombreCompleto.toLowerCase().replaceAll("\\s+", "");
        }


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


    public void cerrarSesion() {
        System.out.println("Sesión cerrada exitosamente");
    }


    public SistemaAcademia getSistemaAcademia() {
        return sistemaAcademia;
    }


    public void setSistemaAcademia(SistemaAcademia sistemaAcademia) {
        this.sistemaAcademia = sistemaAcademia;
    }
}