package co.edu.uniquindio.poo.proyectofinalmusica.model.gestion;

import co.edu.uniquindio.poo.proyectofinalmusica.model.*;

import java.util.ArrayList;
import java.util.List;

public class Curso implements IReportable {
    private String id;
    private String codigo;
    private String nombre;
    private TipoInstrumento instrumento;
    private int nivel;
    private String descripcion;
    private int capacidadMaxima;
    private int capacidadActual;
    private List<Curso> thePrerrequisitos; // muchos a muchos (relación reflexiva)
    private EstadoCurso estado;
    private String fechaInicio;
    private String fechaFin;
    private int duracionSemanas;
    private List<Clase> theClases; // 1 a muchos
    private List<Estudiante> theEstudiantes;// 1 a muchos
    private List<Inscripcion> listInscripciones;

    public Curso(String id, String codigo, String nombre, TipoInstrumento instrumento, int nivel, String descripcion, int capacidadMaxima, int capacidadActual, EstadoCurso estado, String fechaInicio, String fechaFin, int duracionSemanas) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.instrumento = instrumento;
        this.nivel = nivel;
        this.descripcion = descripcion;
        this.capacidadMaxima = capacidadMaxima;
        this.capacidadActual = capacidadActual;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.duracionSemanas = duracionSemanas;
        this.theClases = new ArrayList<>();
        this.theEstudiantes = new ArrayList<>();
        this.listInscripciones = new ArrayList<>();
        this.estado = estado;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoInstrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(TipoInstrumento instrumento) {
        this.instrumento = instrumento;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getCapacidadActual() {
        return capacidadActual;
    }

    public void setCapacidadActual(int capacidadActual) {
        this.capacidadActual = capacidadActual;
    }

    public EstadoCurso getEstado() {
        return estado;
    }

    public void setEstado(EstadoCurso estado) {
        this.estado = estado;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Curso> getThePrerrequisitos() {
        return thePrerrequisitos;
    }

    public void setThePrerrequisitos(List<Curso> thePrerrequisitos) {
        this.thePrerrequisitos = thePrerrequisitos;
    }

    public List<Clase> getTheClases() {
        return theClases;
    }

    public void setTheClases(List<Clase> theClases) {
        this.theClases = theClases;
    }

    public int getDuracionSemanas() {
        return duracionSemanas;
    }

    public void setDuracionSemanas() {
        this.duracionSemanas = duracionSemanas;
    }

    public List<Estudiante> getTheEstudiantes() {
        return theEstudiantes;
    }

    public void setTheEstudiantes(List<Estudiante> theEstudiantes) {
        this.theEstudiantes = theEstudiantes;
    }

    public void setDuracionSemanas(int duracionSemanas) {
        this.duracionSemanas = duracionSemanas;
    }

    public List<Inscripcion> getListInscripciones() {
        return listInscripciones;
    }

    public void setListInscripciones(List<Inscripcion> listInscripciones) {
        this.listInscripciones = listInscripciones;
    }

    @Override
    public String generarReporte() {
        return "";
    }


}