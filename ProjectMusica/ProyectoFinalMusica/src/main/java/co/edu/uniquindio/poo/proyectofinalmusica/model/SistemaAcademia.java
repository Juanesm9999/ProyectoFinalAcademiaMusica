package co.edu.uniquindio.poo.proyectofinalmusica.model;


import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SistemaAcademia {
    private String nombre;
    private String nit;
    private List<Estudiante> listEstudiantes;
    private List<Profesor> listProfesores;
    private List<Administrador> listAdministradores;
    private List<Curso> listCursos;
    private List<Aula> listAulas;
    private List<Clase> listClases;
    private List<Inscripcion> listInscripciones;
    private List<Asistencia> listAsistencias;
    private List<EvaluacionProgreso> listEvaluaciones;
    private List<Horario> listHorarios;

    public SistemaAcademia(String nombre, String nit){
        this.nombre = nombre;
        this.nit = nit;
        this.listEstudiantes = new ArrayList<>();
        this.listProfesores = new ArrayList<>();
        this.listAdministradores = new ArrayList<>();
        this.listCursos = new ArrayList<>();
        this.listAulas = new ArrayList<>();
        this.listClases = new ArrayList<>();
        this.listInscripciones = new ArrayList<>();
        this.listAsistencias = new ArrayList<>();
        this.listEvaluaciones = new ArrayList<>();
        this.listHorarios = new ArrayList<>();

    }

    //---------------------------------------- CRUD ESTUDIANTE -----------------------------------------------

    public boolean registrarEstudiante(Estudiante estudiante) {

        boolean centinela = false;
        if (!verificarEstudiante(estudiante.getId())) {
            listEstudiantes.add(estudiante);
            centinela = true;
        }
        return centinela;
    }

    public boolean actualizarEstudiante(String id, Estudiante actualizado) {
        boolean centinela = false;
        for (Estudiante estudiante : listEstudiantes) {
            //id, nombre,email,telefono,direccion, fechaNacimiento,String matricula,String fechaIngreso , boolean activo,
            if (estudiante.getId().equals(id)) {
                estudiante.setId(actualizado.getId());
                estudiante.setNombre(actualizado.getNombre());
                estudiante.setEmail(actualizado.getEmail());
                estudiante.setTelefono(actualizado.getTelefono());
                estudiante.setDireccion(actualizado.getDireccion());
                estudiante.setFechaNacimiento(actualizado.getFechaNacimiento());
                estudiante.setMatricula(actualizado.getMatricula());
                estudiante.setFechaIngreso(actualizado.getFechaIngreso());
                estudiante.setActivo(actualizado.getActivo());

                centinela = true;
                break;
            }
        }
        return centinela;
    }

    public boolean verificarEstudiante(String id) {
        boolean centinela = false;
        for (Estudiante estudiante : listEstudiantes) {
            if (estudiante.getId().equals(id)) {
                centinela = true;
            }
        }
        return centinela;
    }



    public boolean eliminarEstudiante(String id) {
        boolean centinela = false;
        for (Estudiante estudiante: listEstudiantes) {
            if (estudiante.getId().equals(id)) {
                // CORREGIDO: debe eliminar de listPropietarios, NO de listMascotas
                listEstudiantes.remove(estudiante);
                centinela = true;
                break;
            }
        }
        return centinela;
    }

 //------------------------------------------- CRUD PROFESOR -----------------------------------------------------


    public boolean agregarProfesor(Profesor profesor) {

            boolean centinela = false;
            if (!verificarProfesor(profesor.getId())) {
                listProfesores.add(profesor);
                centinela = true;
            }
            return centinela;
        }

    public boolean actualizaProfesor(String id, Profesor actualizado) {
        boolean centinela = false;
        for (Profesor profesor : listProfesores) {
            //String codigo, String especialidad,String fechaContratacion,boolean activo, String  id, String nombre, String email, String telefono, String direccion,  String fechaNacimiento
            if (profesor.getId().equals(id)) {
                profesor.setCodigo(actualizado.getCodigo());
                profesor.setEspecialidad(actualizado.getEspecialidad());
                profesor.setFechaContratacion(actualizado.getFechaContratacion());
                profesor.setActivo(actualizado.isActivo());
                profesor.setId(actualizado.getId());
                profesor.setNombre(actualizado.getNombre());
                profesor.setEmail(actualizado.getEmail());
                profesor.setTelefono(actualizado.getTelefono());
                profesor.setDireccion(actualizado.getDireccion());
                profesor.setFechaNacimiento(actualizado.getFechaNacimiento());

                centinela = true;
                break;
            }
        }
        return centinela;
    }

    public boolean eliminarProfesor(String id) {
        boolean centinela = false;
        for (Profesor profesor: listProfesores) {
            if (profesor.getId().equals(id)) {
                // CORREGIDO: debe eliminar de listPropietarios, NO de listMascotas
                listProfesores.remove(profesor);
                centinela = true;
                break;
            }
        }
        return centinela;
    }

    public boolean verificarProfesor(String id) {
            boolean centinela = false;
            for (Profesor profesor : listProfesores) {
                if (profesor.getId().equals(id)) {
                    centinela = true;
                }
            }
            return centinela;
        }



    //-------------------------------------------- CRUD CURSO -------------------------------------------------------


    public boolean crearCurso(Curso curso) {
        boolean centinela = false;
        if (!verificarCurso(curso.getId())) {
            listCursos.add(curso);
            centinela = true;
        }
        return centinela;
    }
        
    //      id,codigo,nombre,instrumento,nivel,descripcion,capacidadMaxima,capacidadActual, estado,fechaInicio,fechaFin,duracionSemanas
    public boolean modificarCurso(String id, Curso actualizado) {
        boolean centinela = false;
        for (Curso curso : listCursos) {
            if (curso.getId().equals(id)) {
                curso.setId(actualizado.getId());
                curso.setNombre(actualizado.getNombre());
                curso.setCodigo(actualizado.getCodigo());
                curso.setInstrumento(actualizado.getInstrumento());
                curso.setNivel(actualizado.getNivel());
                curso.setDescripcion(actualizado.getDescripcion());
                curso.setCapacidadMaxima(actualizado.getCapacidadMaxima());
                curso.setCapacidadActual(actualizado.getCapacidadActual());
                curso.setEstado(actualizado.getEstado());
                curso.setFechaInicio(actualizado.getFechaInicio());
                curso.setFechaFin(actualizado.getFechaFin());
                curso.setDuracionSemanas(actualizado.getDuracionSemanas());

                centinela = true;
                break;
            }
        }
        return centinela;
    }


    public boolean eliminarCurso(String id) {
        boolean centinela = false;
        for (Curso curso: listCursos) {
            if (curso.getId().equals(id)) {
                listCursos.remove(curso);
                centinela = true;
                break;
            }
        }
        return centinela;
    }


    public boolean verificarCurso(String id) {
        boolean centinela = false;
        for (Curso curso : listCursos) {
            if (curso.getId().equals(id)) {
                centinela = true;
            }
        }
        return centinela;
    }

    //-------------------------------------------- INSCRIPCIÓN -------------------------------------------------------


    public boolean inscribirEstudiante(Estudiante estudiante, Curso curso) {

        if (!estudiante.getActivo()) {
            System.out.println("Error: El estudiante no está activo");
            return false;
        }

        // 2. Verificar que el curso esté activo
        if (curso.getEstado() != EstadoCurso.ACTIVO) {
            System.out.println("Error: El curso no está activo");
            return false;
        }

        // 3. Verificar prerrequisitos
        if (!verificarPrerrequisitos(estudiante, curso)) {
            System.out.println("Error: El estudiante no cumple con los prerrequisitos");
            return false;
        }

        // 4. Verificar cupos disponibles
        if (!verificarCuposDisponibles(curso)) {
            System.out.println("Error: No hay cupos disponibles en el curso");
            return false;
        }

        // 5. Verificar que no esté ya inscrito
        for (Inscripcion insc : listInscripciones) {
            if (insc.getTheEstudiante().getId().equals(estudiante.getId()) &&
                    insc.getTheCurso().getId().equals(curso.getId()) &&
                    insc.isActiva()) {
                System.out.println("Error: El estudiante ya está inscrito en este curso");
                return false;
            }
        }

        // 6. Crear la inscripción
        String idInscripcion = "INSCRIPCION-" + System.currentTimeMillis();
        Inscripcion nuevaInscripcion = new Inscripcion(
                idInscripcion,
                java.time.LocalDate.now().toString(),
                EstadoInscripcion.ACTIVA,
                true,
                false,
                0.0
        );
        nuevaInscripcion.setTheEstudiante(estudiante);
        nuevaInscripcion.setTheCurso(curso);

        // 7. Agregar inscripción al sistema
        listInscripciones.add(nuevaInscripcion);

        // 8. Agregar estudiante al curso
        curso.getTheEstudiantes().add(estudiante);
        curso.setCapacidadActual(curso.getCapacidadActual() + 1);

        // 9. Agregar inscripción al estudiante
        estudiante.getTheInscripciones().add(nuevaInscripcion);

        System.out.println("Inscripción exitosa: " + estudiante.getNombre() + " en " + curso.getNombre());
        return true;

    }

    /*public boolean inscribirEstudiante(Estudiante estudiante) throws InscripcionException {
        // Verificar que el estudiante no sea nulo
        if (estudiante == null) {
            throw new InscripcionException("El estudiante no puede ser nulo");
        }

        // Verificar que no esté ya inscrito
        if (estudiantesInscritos.contains(estudiante)) {
            throw new InscripcionException("El estudiante ya está inscrito en este curso");
        }

        // Verificar capacidad del grupo (Regla de negocio #1)
        if (estudiantesInscritos.size() >= capacidadMaxima) {
            throw new InscripcionException("El curso ha alcanzado su capacidad máxima");
        }

        // Verificar prerrequisitos (Regla de negocio #3)
        if (!cumplePrerequisitos(estudiante)) {
            throw new InscripcionException(
                "El estudiante no cumple con los prerrequisitos. " +
                "Debe aprobar el nivel " + nivelPrerequisito + " primero"
            );
        }

        // Verificar conflicto de horarios para el estudiante
        if (tieneConflictoHorario(estudiante)) {
            throw new InscripcionException(
                "El estudiante tiene un conflicto de horario con otro curso"
            );
        }

        // Inscribir al estudiante
        estudiantesInscritos.add(estudiante);
        estudiante.agregarCurso(estudiante); // Mantener la relación bidireccional

        return true;
    }
     */
    public boolean cancelarInscripcion(String inscripcionId) {
        for (Inscripcion inscripcion : listInscripciones) {
            if (inscripcion.getId().equals(inscripcionId)) {
                // Cambiar estado
                inscripcion.setActiva(false);
                inscripcion.setEstado(EstadoInscripcion.CANCELADA);

                // Liberar cupo en el curso
                Curso curso = inscripcion.getTheCurso();
                curso.setCapacidadActual(curso.getCapacidadActual() - 1);

                System.out.println("Inscripción cancelada exitosamente");
                return true;
            }
        }
        System.out.println("Error: Inscripción no encontrada");
        return false;

    }


    //-------------------------------------------- CLASE GRUPAL -------------------------------------------------------


    public boolean crearClaseGrupal(ClaseGrupal claseGrupal) {
        boolean centinela = false;
        if (!verificarClaseGrupal(claseGrupal.getId())) {
            listClases.add(claseGrupal);
            centinela = true;
        }
        return centinela;
    }

    //     id,horario,diaSemana,horaInicio,HoraFin,instrumento,nivel,activa,capacidadMaxima, capacidadActual, cuposDisponibles
    public boolean modificarClaseGrupal(String id, ClaseGrupal actualizado) {
        boolean centinela = false;
        for (Clase claseGrupal : listClases) {
            if (claseGrupal.getId().equals(id) && claseGrupal instanceof ClaseGrupal) {
                ClaseGrupal grupal = (ClaseGrupal) claseGrupal;
                claseGrupal.setId(actualizado.getId());
                claseGrupal.setHorario(actualizado.getHorario());
                claseGrupal.setDiaSemana(actualizado.getDiaSemana());
                claseGrupal.setHoraInicio(actualizado.getHoraInicio());
                claseGrupal.setHoraFin(actualizado.getHoraFin());
                claseGrupal.setInstrumento(actualizado.getInstrumento());
                claseGrupal.setNivel(actualizado.getNivel());
                claseGrupal.setActiva(actualizado.isActiva());
                claseGrupal.setTheAula(actualizado.getTheAula());
                claseGrupal.setTheProfesor(actualizado.getTheProfesor());
                // Actualizar campos específicos de ClaseGrupal
                grupal.setCapacidadMaxima(actualizado.getCapacidadMaxima());
                grupal.setDescripcion(actualizado.getDescripcion());
                centinela = true;
                break;
            }
        }
        return centinela;
    }




    public boolean verificarClaseGrupal(String id) {
        boolean centinela = false;
        for (Clase claseGrupal : listClases) {
            if (claseGrupal.getId().equals(id)) {
                centinela = true;
            }
        }
        return centinela;
    }

    //-------------------------------------------- CLASE INDIVIDUAL -------------------------------------------------------


    public boolean crearClaseIndividual(ClaseIndividual claseIndividual) {
        boolean centinela = false;
        if (!verificarClaseIndividual(claseIndividual.getId())) {
            listClases.add(claseIndividual);
            centinela = true;
        }
        return centinela;
    }

    //     id,horario,diaSemana,horaInicio,HoraFin,instrumento,nivel,activa,capacidadMaxima, capacidadActual, cuposDisponibles
    public boolean modificarClaseIndividual(String id, ClaseIndividual actualizado) {
        boolean centinela = false;
        for (Clase claseIndividual : listClases) {
            if (claseIndividual.getId().equals(id) && claseIndividual instanceof ClaseIndividual) {
                ClaseIndividual individual = (ClaseIndividual) claseIndividual;
                claseIndividual.setId(actualizado.getId());
                claseIndividual.setHorario(actualizado.getHorario());
                claseIndividual.setDiaSemana(actualizado.getDiaSemana());
                claseIndividual.setHoraInicio(actualizado.getHoraInicio());
                claseIndividual.setHoraFin(actualizado.getHoraFin());
                claseIndividual.setInstrumento(actualizado.getInstrumento());
                claseIndividual.setNivel(actualizado.getNivel());
                claseIndividual.setActiva(actualizado.isActiva());
                claseIndividual.setTheAula(actualizado.getTheAula());
                claseIndividual.setTheProfesor(actualizado.getTheProfesor());
                // Actualizar campos específicos de ClaseIndividual
                individual.setTemaEspecifico(actualizado.getTemaEspecifico());
                individual.setObjetivos(actualizado.getObjetivos());
                individual.setObservaciones(actualizado.getObservaciones());
                centinela = true;
                break;
            }
        }
        return centinela;
    }




    public boolean verificarClaseIndividual(String id) {
        boolean centinela = false;
        for (Clase claseIndividual : listClases) {
            if (claseIndividual.getId().equals(id)) {
                centinela = true;
            }
        }
        return centinela;
    }

    //-------------------------------------------- ASISTENCIA -------------------------------------------------------


    public boolean registrarAsistencia(Asistencia asistencia) {
        boolean centinela = false;
        if (!verificarAsistencia(asistencia.getId())) {
            listAsistencias.add(asistencia);
            centinela = true;
        }
        return centinela;
    }

    public boolean verificarAsistencia(String id) {
        boolean centinela = false;
        for (Asistencia asistencia : listAsistencias) {
            if (asistencia.getId().equals(id)) {
                centinela = true;
            }
        }
        return centinela;
    }


    public boolean registrarEvaluacion(EvaluacionProgreso evaluacionProgreso) {
        boolean centinela = false;
        if (!verificarEvaluacionProgreso(evaluacionProgreso.getId())) {
            listEvaluaciones.add(evaluacionProgreso);
            centinela = true;
        }
        return centinela;
    }

    public boolean verificarEvaluacionProgreso(String id) {
        boolean centinela = false;
        for (EvaluacionProgreso evaluacionProgreso : listEvaluaciones) {
            if (evaluacionProgreso.getId().equals(id)) {
                centinela = true;
            }
        }
        return centinela;
    }

    //-------------------------------------------- VERIFICACIONES -------------------------------------------------------


    public boolean verificarConflictoHorario(Profesor profesor, String horario) {
        for (Clase clase : listClases) {
                if (clase.getTheProfesor() != null &&
                        clase.getTheProfesor().getId().equals(profesor.getId()) &&
                        clase.getHorario().equals(horario) &&
                        clase.isActiva()) {
                    return true;
                }
            }
            return false;
        }
        




    public boolean verificarConflictoAula(Aula aula, String horario) {
        // Verificar si el aula está ocupada en ese horario
        for (Clase clase : listClases) {
            if (clase.getTheAula() != null &&
                    clase.getTheAula().getId().equals(aula.getId()) &&
                    clase.getHorario().equals(horario) &&
                    clase.isActiva()) {
                return true; // HAY conflicto
            }
        }
        return false; // NO hay conflicto
    }

    public boolean verificarPrerrequisitos(Estudiante estudiante, Curso curso) {

        if (curso.getNivel() == 1) {
            return true;
        }

// Verificar que tenga aprobado el nivel anterior del mismo instrumento
        for (NivelAprobado nivelAprobado : estudiante.getTheNivelesAprobados()) {
            if (nivelAprobado.getInstrumento() == curso.getInstrumento() &&
                    nivelAprobado.getNivel() >= (curso.getNivel() - 1)) {
                return true;
            }
        }

        return false;
    }

    public boolean verificarCuposDisponibles(Curso curso) {
        return curso.getCapacidadActual() < curso.getCapacidadMaxima();
    }

    //-------------------------------------------- REPORTES -------------------------------------------------------



    public List<String> generarReporteAsistencia(Curso curso) {
        List<String> reporte = new ArrayList<>();
        reporte.add("=== REPORTE DE ASISTENCIA ===");
        reporte.add("Curso: " + curso.getNombre());
        reporte.add("Código: " + curso.getCodigo());
        reporte.add("");

        for (Inscripcion inscripcion : listInscripciones) {
            if (inscripcion.getTheCurso().getId().equals(curso.getId())) {
                Estudiante estudiante = inscripcion.getTheEstudiante();
                reporte.add("Estudiante: " + estudiante.getNombre());

                int presentes = 0;
                int ausentes = 0;

                for (Asistencia asistencia : estudiante.getTheHistorialAsistencia()) {
                    if (asistencia.isPresente()) {
                        presentes++;
                    } else {
                        ausentes++;
                    }
                }

                reporte.add("  Asistencias: " + presentes);
                reporte.add("  Ausencias: " + ausentes);
                reporte.add("");
            }
        }

        return reporte;
    }

    public List<String> generarReporteProgreso(TipoInstrumento instrumento, int nivel) {
        List<String> reporte = new ArrayList<>();
        reporte.add("=== REPORTE DE PROGRESO ===");
        reporte.add("Instrumento: " + instrumento);
        reporte.add("Nivel: " + nivel);
        reporte.add("");

        for (Curso curso : listCursos) {
            if (curso.getInstrumento() == instrumento && curso.getNivel() == nivel) {
                reporte.add("Curso: " + curso.getNombre());

                for (EvaluacionProgreso eval : listEvaluaciones) {
                    if (eval.getTheCurso() != null && eval.getTheCurso().getId().equals(curso.getId())) {
                        reporte.add("  Estudiante: " + eval.getTheEstudiante().getNombre());
                        reporte.add("  Calificación: " + eval.getCalificacion());
                        reporte.add("  Comentarios: " + eval.getComentarios());
                        reporte.add("");
                    }
                }
            }
        }

        return reporte;
    }

    public List<String> generarReporteOcupacionAulas() {
        List<String> reporte = new ArrayList<>();
        reporte.add("=== REPORTE DE OCUPACIÓN DE AULAS ===");
        reporte.add("");

        for (Aula aula : listAulas) {
            reporte.add("Aula: " + aula.getNombre());
            reporte.add("Código: " + aula.getCodigo());
            reporte.add("Capacidad: " + aula.getCapacidad());

            int clasesAsignadas = 0;
            for (Clase clase : listClases) {
                if (clase.getTheAula() != null && clase.getTheAula().getId().equals(aula.getId())) {
                    clasesAsignadas++;
                    reporte.add("  - " + clase.getHorario() + " - " + clase.getInstrumento());
                }
            }

            reporte.add("Total clases: " + clasesAsignadas);
            reporte.add("");
        }

        return reporte;
    }

    public List<String> generarReporteCargaDocente() {
        List<String> reporte = new ArrayList<>();
        reporte.add("=== REPORTE DE CARGA DOCENTE ===");
        reporte.add("");

        for (Profesor profesor : listProfesores) {
            reporte.add("Profesor: " + profesor.getNombre());
            reporte.add("Código: " + profesor.getCodigo());
            reporte.add("Especialidad: " + profesor.getEspecialidad());

            int clasesAsignadas = 0;
            for (Clase clase : listClases) {
                if (clase.getTheProfesor() != null && clase.getTheProfesor().getId().equals(profesor.getId())) {
                    clasesAsignadas++;
                    reporte.add("  - " + clase.getHorario() + " - " + clase.getInstrumento());
                }
            }

            reporte.add("Total clases: " + clasesAsignadas);
            reporte.add("");
        }

        return reporte;
    }

    public List<String> generarReporteEstudiante(Estudiante estudiante) {
        List<String> reporte = new ArrayList<>();
        reporte.add("=== REPORTE DEL ESTUDIANTE ===");
        reporte.add("");
        reporte.add("Nombre: " + estudiante.getNombre());
        reporte.add("Matrícula: " + estudiante.getMatricula());
        reporte.add("Email: " + estudiante.getEmail());
        reporte.add("");

        reporte.add("CURSOS INSCRITOS:");
        for (Inscripcion inscripcion : estudiante.getTheInscripciones()) {
            if (inscripcion.isActiva()) {
                reporte.add("  - " + inscripcion.getTheCurso().getNombre());
            }
        }
        reporte.add("");

        reporte.add("NIVELES APROBADOS:");
        for (NivelAprobado nivel : estudiante.getTheNivelesAprobados()) {
            reporte.add("  - " + nivel.getInstrumento() + " Nivel " + nivel.getNivel() +
                    " - Calificación: " + nivel.getCalificacion());
        }
        reporte.add("");

        reporte.add("EVALUACIONES:");
        for (EvaluacionProgreso eval : estudiante.getTheEvaluaciones()) {
            reporte.add("  - Calificación: " + eval.getCalificacion());
            reporte.add("    Comentarios: " + eval.getComentarios());
        }

        return reporte;
    }

    //--------------------------------------- GETTERS Y SETTERS -----------------------------------------------
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public List<Estudiante> getListEstudiantes() {
        return listEstudiantes;
    }

    public void setListEstudiantes(List<Estudiante> listEstudiantes) {
        this.listEstudiantes = listEstudiantes;
    }

    public List<Profesor> getListProfesores() {
        return listProfesores;
    }

    public void setListProfesores(List<Profesor> listProfesores) {
        this.listProfesores = listProfesores;
    }

    public List<Administrador> getListAdministradores() {
        return listAdministradores;
    }

    public void setListAdministradores(List<Administrador> listAdministradores) {
        this.listAdministradores = listAdministradores;
    }

    public List<Curso> getListCursos() {
        return listCursos;
    }

    public void setListCursos(List<Curso> listCursos) {
        this.listCursos = listCursos;
    }

    public List<Aula> getListAulas() {
        return listAulas;
    }

    public void setListAulas(List<Aula> listAulas) {
        this.listAulas = listAulas;
    }

    public List<Clase> getListClases() {
        return listClases;
    }

    public void setListClases(List<Clase> listClases) {
        this.listClases = listClases;
    }

    public List<Inscripcion> getListInscripciones() {
        return listInscripciones;
    }

    public void setListInscripciones(List<Inscripcion> listInscripciones) {
        this.listInscripciones = listInscripciones;
    }

    public List<Asistencia> getListAsistencias() {
        return listAsistencias;
    }

    public void setListAsistencias(List<Asistencia> listAsistencias) {
        this.listAsistencias = listAsistencias;
    }

    public List<EvaluacionProgreso> getListEvaluaciones() {
        return listEvaluaciones;
    }

    public void setListEvaluaciones(List<EvaluacionProgreso> listEvaluaciones) {
        this.listEvaluaciones = listEvaluaciones;
    }

    public List<Horario> getListHorarios() {
        return listHorarios;
    }

    public void setListHorarios(List<Horario> listHorarios) {
        this.listHorarios = listHorarios;
    }
    //------------------------------------------- CRUD ADMINISTRADOR -----------------------------------------------------

    public boolean agregarAdministrador(Administrador administrador) {
        boolean centinela = false;
        if (!verificarAdministrador(administrador.getId())) {
            listAdministradores.add(administrador);
            centinela = true;
        }
        return centinela;
    }

    public boolean actualizarAdministrador(String id, Administrador actualizado) {
        boolean centinela = false;
        for (Administrador administrador : listAdministradores) {
            if (administrador.getId().equals(id)) {
                administrador.setId(actualizado.getId());
                administrador.setNombre(actualizado.getNombre());
                administrador.setEmail(actualizado.getEmail());
                administrador.setTelefono(actualizado.getTelefono());
                administrador.setDireccion(actualizado.getDireccion());
                administrador.setFechaNacimiento(actualizado.getFechaNacimiento());
                administrador.setCargo(actualizado.getCargo());
                administrador.setDepartamento(actualizado.getDepartamento());
                administrador.setFechaIngreso(actualizado.getFechaIngreso());
                administrador.setUsuario(actualizado.getUsuario());
                administrador.setContrasenia(actualizado.getContrasenia());
                centinela = true;
                break;
            }
        }
        return centinela;
    }

    public boolean eliminarAdministrador(String id) {
        boolean centinela = false;
        for (Administrador administrador : listAdministradores) {
            if (administrador.getId().equals(id)) {
                listAdministradores.remove(administrador);
                centinela = true;
                break;
            }
        }
        return centinela;
    }

    public boolean verificarAdministrador(String id) {
        boolean centinela = false;
        for (Administrador administrador : listAdministradores) {
            if (administrador.getId().equals(id)) {
                centinela = true;
            }
        }
        return centinela;
    }

    //-------------------------------------------- LOGIN Y AUTENTICACIÓN -------------------------------------------------------

    public Persona iniciarSesion(String usuario, String contrasena, String tipoRol) {
        switch (tipoRol) {
            case "ESTUDIANTE":
                for (Estudiante est : listEstudiantes) {
                    if (est.getUsuario() != null && est.getUsuario().equals(usuario) &&
                            est.getContrasenia() != null && est.getContrasenia().equals(contrasena)) {
                        return est;
                    }
                }
                break;

            case "PROFESOR":
                for (Profesor prof : listProfesores) {
                    if (prof.getUsuario() != null && prof.getUsuario().equals(usuario) &&
                            prof.getContrasenia() != null && prof.getContrasenia().equals(contrasena)) {
                        return prof;
                    }
                }
                break;

            case "ADMINISTRADOR":
                for (Administrador admin : listAdministradores) {
                    if (admin.getUsuario() != null && admin.getUsuario().equals(usuario) &&
                            admin.getContrasenia() != null && admin.getContrasenia().equals(contrasena)) {
                        return admin;
                    }
                }
                break;
        }
        return null;
    }

    public boolean verificarUsuarioExiste(String usuario) {
        for (Estudiante est : listEstudiantes) {
            if (est.getUsuario() != null && est.getUsuario().equals(usuario)) {
                return true;
            }
        }

        for (Profesor prof : listProfesores) {
            if (prof.getUsuario() != null && prof.getUsuario().equals(usuario)) {
                return true;
            }
        }

        for (Administrador admin : listAdministradores) {
            if (admin.getUsuario() != null && admin.getUsuario().equals(usuario)) {
                return true;
            }
        }

        return false;
    }
    // Agregar este método en SistemaAcademia.java para inicializar datos de prueba completos
// CORREGIDO según los constructores reales de las clases

    public void inicializarDatosDePrueba() {
        // ============================================
        // 1. CREAR AULAS
        // ============================================
        // Constructor: (String id, String codigo, String nombre, String ubicacion, int capacidad, boolean disponible)
        Aula aula101 = new Aula("AU001", "A101", "Aula Principal", "Edificio A - Primer Piso", 20, true);
        Aula aula102 = new Aula("AU002", "A102", "Sala de Piano", "Edificio A - Primer Piso", 15, true);
        Aula aula103 = new Aula("AU003", "A103", "Sala de Guitarra", "Edificio A - Segundo Piso", 12, true);
        listAulas.add(aula101);
        listAulas.add(aula102);
        listAulas.add(aula103);

        // ============================================
        // 2. CREAR PROFESORES
        // ============================================
        // Constructor: (String codigo, String especialidad, String fechaContratacion, boolean activo,
        //               String id, String nombre, String email, String telefono, String direccion,
        //               LocalDate fechaNacimiento, String usuario, String contrasenia)
        Profesor profCarlos = new Profesor(
                "P001",                              // codigo
                "Guitarra",                          // especialidad
                "2020-01-15",                        // fechaContratacion
                true,                                // activo
                "PROF001",                           // id
                "Carlos Pérez",                      // nombre
                "carlos@academia.com",               // email
                "3001234567",                        // telefono
                "Calle 10 #20-30",                   // direccion
                LocalDate.of(1985, 5, 20),          // fechaNacimiento
                "carlos@academia.com",               // usuario
                "1234"                               // contrasenia
        );

        Profesor profMaria = new Profesor(
                "P002",
                "Piano",
                "2019-08-20",
                true,
                "PROF002",
                "María López",
                "maria@academia.com",
                "3009876543",
                "Carrera 15 #30-40",
                LocalDate.of(1988, 3, 15),
                "maria@academia.com",
                "1234"
        );

        listProfesores.add(profCarlos);
        listProfesores.add(profMaria);

        // ============================================
        // 3. CREAR ESTUDIANTES
        // ============================================
        // Constructor: (String matricula, LocalDate fechaIngreso, boolean activo,
        //               String id, String nombre, String email, String telefono, String direccion,
        //               LocalDate fechaNacimiento, String usuario, String contrasenia)
        Estudiante est1 = new Estudiante(
                "EST2024001",                        // matricula
                LocalDate.of(2024, 1, 15),          // fechaIngreso
                true,                                // activo
                "E001",                              // id
                "Ana López",                         // nombre
                "ana@correo.com",                    // email
                "3001234567",                        // telefono
                "Calle 5 #10-20",                    // direccion
                LocalDate.of(2000, 5, 15),          // fechaNacimiento
                "ana@correo.com",                    // usuario
                "1234"                               // contrasenia
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
                "laura@correo.com",
                "3009876543",
                "Avenida 30 #20-30",
                LocalDate.of(2001, 3, 10),
                "laura@correo.com",
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

        // ============================================
        // 4. CREAR CURSOS CON CLASES
        // ============================================

        // Constructor Curso: (String id, String codigo, String nombre, TipoInstrumento instrumento, int nivel,
        //                     String descripcion, int capacidadMaxima, int capacidadActual, EstadoCurso estado,
        //                     String fechaInicio, String fechaFin, int duracionSemanas)
        Curso cursoGuitarra1 = new Curso(
                "C001",                              // id
                "GIT-NV1-2024",                      // codigo
                "Guitarra Básica",                   // nombre
                TipoInstrumento.GUITARRA,            // instrumento
                1,                                   // nivel
                "Curso introductorio de guitarra",   // descripcion
                15,                                  // capacidadMaxima
                4,                                   // capacidadActual
                EstadoCurso.ACTIVO,                  // estado
                "2024-01-15",                        // fechaInicio
                "2024-06-30",                        // fechaFin
                24                                   // duracionSemanas
        );

        // Constructor ClaseGrupal: (int capacidadMaxima, int capacidadActual, int cuposDisponibles, String descripcion,
        //                          String id, String horario, String diaSemana, String horaInicio, String horaFin,
        //                          TipoInstrumento instrumento, int nivel, boolean activa)
        ClaseGrupal claseGuitarraGrupal = new ClaseGrupal(
                12,                                  // capacidadMaxima
                3,                                   // capacidadActual
                9,                                   // cuposDisponibles
                "Clase grupal de guitarra básica",  // descripcion
                "CL001",                             // id
                "Lunes 14:00-16:00",                 // horario
                "Lunes",                             // diaSemana
                "14:00",                             // horaInicio
                "16:00",                             // horaFin
                TipoInstrumento.GUITARRA,            // instrumento
                1,                                   // nivel
                true                                 // activa
        );
        claseGuitarraGrupal.setTheProfesor(profCarlos);
        claseGuitarraGrupal.setTheAula(aula103);

        // IMPORTANTE: Agregar estudiantes a la clase grupal
        claseGuitarraGrupal.getTheEstudiantesInscritos().add(est1);
        claseGuitarraGrupal.getTheEstudiantesInscritos().add(est2);
        claseGuitarraGrupal.getTheEstudiantesInscritos().add(est3);

        // Constructor ClaseIndividual: (String temaEspecifico, String objetivos, String observaciones,
        //                               String id, String horario, String diaSemana, String horaInicio, String horaFin,
        //                               TipoInstrumento instrumento, int nivel, boolean activa)
        ClaseIndividual claseGuitarraIndiv = new ClaseIndividual(
                "Técnicas avanzadas de guitarra",   // temaEspecifico
                "Mejorar técnica individual",        // objetivos
                "Clase personalizada",               // observaciones
                "CL002",                             // id
                "Miércoles 08:00-09:00",             // horario
                "Miércoles",                         // diaSemana
                "08:00",                             // horaInicio
                "09:00",                             // horaFin
                TipoInstrumento.GUITARRA,            // instrumento
                1,                                   // nivel
                true                                 // activa
        );
        claseGuitarraIndiv.setTheProfesor(profCarlos);
        claseGuitarraIndiv.setTheAula(aula103);
        claseGuitarraIndiv.setTheEstudiante(est4);

        // Agregar clases al curso
        cursoGuitarra1.getTheClases().add(claseGuitarraGrupal);
        cursoGuitarra1.getTheClases().add(claseGuitarraIndiv);

        // Agregar clases al profesor
        profCarlos.getTheClasesAsignadas().add(claseGuitarraGrupal);
        profCarlos.getTheClasesAsignadas().add(claseGuitarraIndiv);

        // Agregar instrumentos al profesor
        profCarlos.getTheInstrumentosImpartidos().add(TipoInstrumento.GUITARRA);

        listCursos.add(cursoGuitarra1);

        // CURSO 2: Piano Nivel 1
        Curso cursoPiano1 = new Curso(
                "C002",
                "PIA-NV1-2024",
                "Piano Básico",
                TipoInstrumento.PIANO,
                1,
                "Curso introductorio de piano",
                10,
                0,
                EstadoCurso.ACTIVO,
                "2024-01-20",
                "2024-07-10",
                24
        );

        ClaseGrupal clasePianoGrupal = new ClaseGrupal(
                10,
                0,
                10,
                "Clase grupal de piano básico",
                "CL003",
                "Martes 10:00-12:00",
                "Martes",
                "10:00",
                "12:00",
                TipoInstrumento.PIANO,
                1,
                true
        );
        clasePianoGrupal.setTheProfesor(profMaria);
        clasePianoGrupal.setTheAula(aula102);

        cursoPiano1.getTheClases().add(clasePianoGrupal);
        profMaria.getTheClasesAsignadas().add(clasePianoGrupal);
        profMaria.getTheInstrumentosImpartidos().add(TipoInstrumento.PIANO);

        listCursos.add(cursoPiano1);

        // CURSO 3: Guitarra Nivel 2 (con prerrequisito)
        Curso cursoGuitarra2 = new Curso(
                "C003",
                "GIT-NV2-2024",
                "Guitarra Intermedia",
                TipoInstrumento.GUITARRA,
                2,
                "Curso intermedio de guitarra",
                12,
                0,
                EstadoCurso.ACTIVO,
                "2024-07-15",
                "2024-12-20",
                24
        );
        listCursos.add(cursoGuitarra2);

        // ============================================
        // 5. CREAR INSCRIPCIONES
        // ============================================

        // Constructor: (String id, String fechaInscripcion, EstadoInscripcion estado,
        //               boolean activa, boolean aprobada, double calificacionFinal)

        // Ana López inscrita en Guitarra 1
        Inscripcion insc1 = new Inscripcion(
                "I001",                              // id
                "2024-01-15",                        // fechaInscripcion
                EstadoInscripcion.ACTIVA,            // estado
                true,                                // activa
                false,                               // aprobada
                0.0                                  // calificacionFinal
        );
        insc1.setTheEstudiante(est1);
        insc1.setTheCurso(cursoGuitarra1);
        est1.getTheInscripciones().add(insc1);
        cursoGuitarra1.getListInscripciones().add(insc1);
        cursoGuitarra1.getTheEstudiantes().add(est1);
        listInscripciones.add(insc1);

        // Juan Martínez inscrito en Guitarra 1
        Inscripcion insc2 = new Inscripcion(
                "I002",
                "2024-01-15",
                EstadoInscripcion.ACTIVA,
                true,
                false,
                0.0
        );
        insc2.setTheEstudiante(est2);
        insc2.setTheCurso(cursoGuitarra1);
        est2.getTheInscripciones().add(insc2);
        cursoGuitarra1.getListInscripciones().add(insc2);
        cursoGuitarra1.getTheEstudiantes().add(est2);
        listInscripciones.add(insc2);

        // Laura García inscrita en Guitarra 1
        Inscripcion insc3 = new Inscripcion(
                "I003",
                "2024-01-15",
                EstadoInscripcion.ACTIVA,
                true,
                false,
                0.0
        );
        insc3.setTheEstudiante(est3);
        insc3.setTheCurso(cursoGuitarra1);
        est3.getTheInscripciones().add(insc3);
        cursoGuitarra1.getListInscripciones().add(insc3);
        cursoGuitarra1.getTheEstudiantes().add(est3);
        listInscripciones.add(insc3);

        // Pedro Rodríguez inscrito en Guitarra 1 (clase individual)
        Inscripcion insc4 = new Inscripcion(
                "I004",
                "2024-01-16",
                EstadoInscripcion.ACTIVA,
                true,
                false,
                0.0
        );
        insc4.setTheEstudiante(est4);
        insc4.setTheCurso(cursoGuitarra1);
        est4.getTheInscripciones().add(insc4);
        cursoGuitarra1.getListInscripciones().add(insc4);
        cursoGuitarra1.getTheEstudiantes().add(est4);
        listInscripciones.add(insc4);

        // ============================================
        // 6. CREAR ALGUNAS ASISTENCIAS DE EJEMPLO
        // ============================================

        // Constructor: (String id, Estudiante theEstudiante, Clase theClase,
        //               LocalDate fecha, boolean presente, String observaciones)

        // Asistencias para Ana (3 presentes, 1 ausente)
        Asistencia asist1 = new Asistencia(
                "AS001",
                est1,
                claseGuitarraGrupal,
                LocalDate.of(2024, 10, 7),
                true,
                "Excelente participación"
        );
        est1.getTheHistorialAsistencia().add(asist1);
        listAsistencias.add(asist1);

        Asistencia asist2 = new Asistencia(
                "AS002",
                est1,
                claseGuitarraGrupal,
                LocalDate.of(2024, 10, 14),
                true,
                "Muy atenta en clase"
        );
        est1.getTheHistorialAsistencia().add(asist2);
        listAsistencias.add(asist2);

        Asistencia asist3 = new Asistencia(
                "AS003",
                est1,
                claseGuitarraGrupal,
                LocalDate.of(2024, 10, 21),
                false,
                "Justificación médica"
        );
        est1.getTheHistorialAsistencia().add(asist3);
        listAsistencias.add(asist3);

        Asistencia asist4 = new Asistencia(
                "AS004",
                est1,
                claseGuitarraGrupal,
                LocalDate.of(2024, 10, 28),
                true,
                "Presente"
        );
        est1.getTheHistorialAsistencia().add(asist4);
        listAsistencias.add(asist4);

        // Asistencias para Juan
        Asistencia asist5 = new Asistencia(
                "AS005",
                est2,
                claseGuitarraGrupal,
                LocalDate.of(2024, 10, 7),
                true,
                "Presente"
        );
        est2.getTheHistorialAsistencia().add(asist5);
        listAsistencias.add(asist5);

        Asistencia asist6 = new Asistencia(
                "AS006",
                est2,
                claseGuitarraGrupal,
                LocalDate.of(2024, 10, 14),
                true,
                "Presente"
        );
        est2.getTheHistorialAsistencia().add(asist6);
        listAsistencias.add(asist6);

        // ============================================
        // 7. CREAR EVALUACIONES DE PROGRESO
        // ============================================

        // Constructor: (String id, double calificacion, String comentarios,
        //               String areasAMejorar, String fecha)

        // Evaluación 1 para Ana
        EvaluacionProgreso eval1 = new EvaluacionProgreso(
                "EV001",
                4.5,
                "Excelente progreso en acordes básicos. Muestra dedicación y práctica constante.",
                "Trabajar en cambios rápidos entre acordes",
                "2024-10-20"
        );
        eval1.setTheEstudiante(est1);
        eval1.setTheClase(claseGuitarraGrupal);
        eval1.setTheCurso(cursoGuitarra1);
        eval1.setTheEvaluador(profCarlos);
        est1.getTheEvaluaciones().add(eval1);
        listEvaluaciones.add(eval1);

        // Evaluación 2 para Ana
        EvaluacionProgreso eval2 = new EvaluacionProgreso(
                "EV002",
                4.8,
                "Mejora notable en técnica. Excelente dominio de los acordes básicos.",
                "Perfeccionar ritmo en rasgueados",
                "2024-11-05"
        );
        eval2.setTheEstudiante(est1);
        eval2.setTheClase(claseGuitarraGrupal);
        eval2.setTheCurso(cursoGuitarra1);
        eval2.setTheEvaluador(profCarlos);
        est1.getTheEvaluaciones().add(eval2);
        listEvaluaciones.add(eval2);

        // Evaluación para Juan
        EvaluacionProgreso eval3 = new EvaluacionProgreso(
                "EV003",
                3.8,
                "Buen avance, necesita practicar más en casa.",
                "Mejorar postura de la mano izquierda y practicar escalas",
                "2024-10-20"
        );
        eval3.setTheEstudiante(est2);
        eval3.setTheClase(claseGuitarraGrupal);
        eval3.setTheCurso(cursoGuitarra1);
        eval3.setTheEvaluador(profCarlos);
        est2.getTheEvaluaciones().add(eval3);
        listEvaluaciones.add(eval3);

        // Evaluación para Laura
        EvaluacionProgreso eval4 = new EvaluacionProgreso(
                "EV004",
                4.2,
                "Muy buena técnica y ritmo. Sigue practicando así.",
                "Trabajar en coordinación mano derecha-izquierda",
                "2024-10-20"
        );
        eval4.setTheEstudiante(est3);
        eval4.setTheClase(claseGuitarraGrupal);
        eval4.setTheCurso(cursoGuitarra1);
        eval4.setTheEvaluador(profCarlos);
        est3.getTheEvaluaciones().add(eval4);
        listEvaluaciones.add(eval4);

        // Evaluación para Pedro (clase individual)
        EvaluacionProgreso eval5 = new EvaluacionProgreso(
                "EV005",
                4.0,
                "Progreso constante en clases individuales. Buen manejo de técnicas.",
                "Practicar escalas pentatónicas y arpegios",
                "2024-10-25"
        );
        eval5.setTheEstudiante(est4);
        eval5.setTheClase(claseGuitarraIndiv);
        eval5.setTheCurso(cursoGuitarra1);
        eval5.setTheEvaluador(profCarlos);
        est4.getTheEvaluaciones().add(eval5);
        listEvaluaciones.add(eval5);

        // ============================================
        // 8. CREAR NIVEL APROBADO PARA EST1
        // ============================================
        // Constructor: (String id, TipoInstrumento instrumento, int nivel,
        //               String fechaAprobacion, double calificacion, Curso theCurso)
        NivelAprobado nivelAna = new NivelAprobado(
                "NA001",
                TipoInstrumento.GUITARRA,
                1,
                "2024-11-10",
                4.65,
                cursoGuitarra1
        );
        est1.getTheNivelesAprobados().add(nivelAna);

        // ============================================
        // 9. CREAR ADMINISTRADOR
        // ============================================
        // Constructor: (String cargo, String departamento, String fechaIngreso,
        //               String id, String nombre, String email, String telefono, String direccion,
        //               LocalDate fechaNacimiento, String usuario, String contrasenia)
        Administrador admin = new Administrador(
                "Director Académico",                // cargo
                "Administración",                    // departamento
                "2018-05-15",                        // fechaIngreso
                "A001",                              // id
                "Admin Principal",                   // nombre
                "admin@academia.com",                // email
                "3001112233",                        // telefono
                "Dirección Academia",                // direccion
                LocalDate.of(1980, 7, 10),          // fechaNacimiento
                "admin@academia.com",                // usuario
                "admin123"                           // contrasenia
        );
        listAdministradores.add(admin);

        System.out.println("✅ Datos de prueba inicializados correctamente");
        System.out.println("📊 Resumen:");
        System.out.println("   - Estudiantes: " + listEstudiantes.size());
        System.out.println("   - Profesores: " + listProfesores.size());
        System.out.println("   - Cursos: " + listCursos.size());
        System.out.println("   - Clases totales: " + (cursoGuitarra1.getTheClases().size() + cursoPiano1.getTheClases().size()));
        System.out.println("   - Inscripciones: " + listInscripciones.size());
        System.out.println("   - Asistencias: " + listAsistencias.size());
        System.out.println("   - Evaluaciones: " + listEvaluaciones.size());
        System.out.println("   - Aulas: " + listAulas.size());
        System.out.println("   - Estudiantes en clase grupal de guitarra: " + claseGuitarraGrupal.getTheEstudiantesInscritos().size());
    }
}