package co.edu.uniquindio.poo.proyectofinalmusica.model;


import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.*;

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

    }

    //---------------------------------------- CRUD ESTUDIANTE -----------------------------------------------

    public static boolean registrarEstudiante(Estudiante estudiante) {

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
                estudiante.setActivo(actualizado.isActivo());

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
                listEstudiantes.remove(profesor);
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


    public void inscribirEstudiante(Estudiante estudiante, Curso curso) {}
    public void cancelarInscripcion(String inscripcionId) {}

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
            if (claseGrupal.getId().equals(id)) {
                claseGrupal.setId(actualizado.getId());
                claseGrupal.setHorario(actualizado.getHorario());
                claseGrupal.setDiaSemana(actualizado.getDiaSemana());
                claseGrupal.setHoraInicio(actualizado.getHoraInicio());
                claseGrupal.setHoraFin(actualizado.getHoraFin());
                claseGrupal.setInstrumento(actualizado.getInstrumento());
                claseGrupal.setNivel(actualizado.getNivel());
                claseGrupal.setActiva(actualizado.isActiva());
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
            if (claseIndividual.getId().equals(id)) {
                claseIndividual.setId(actualizado.getId());
                claseIndividual.setHorario(actualizado.getHorario());
                claseIndividual.setDiaSemana(actualizado.getDiaSemana());
                claseIndividual.setHoraInicio(actualizado.getHoraInicio());
                claseIndividual.setHoraFin(actualizado.getHoraFin());
                claseIndividual.setInstrumento(actualizado.getInstrumento());
                claseIndividual.setNivel(actualizado.getNivel());
                claseIndividual.setActiva(actualizado.isActiva());
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
        return false;
    }
    public boolean verificarConflictoAula(Aula aula, String horario) {
        return false;
    }
    public boolean verificarPrerrequisitos(Estudiante estudiante, Curso curso) {return false;}
    public boolean verificarCuposDisponibles(Curso curso) {
        return false;
    }

    //-------------------------------------------- REPORTES -------------------------------------------------------


    public List<String> generarReporteAsistencia(Curso curso) {return null;}
    public List<String> generarReporteProgreso(TipoInstrumento instrumento, int nivel) {
        return null;
    }
    public List<String> generarReporteOcupacionAulas() {return null;}
    public List<String> generarReporteCargaDocente() {return null;}
    public List<String> generarReporteEstudiante(Estudiante estudiante) {return null;}


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
}


