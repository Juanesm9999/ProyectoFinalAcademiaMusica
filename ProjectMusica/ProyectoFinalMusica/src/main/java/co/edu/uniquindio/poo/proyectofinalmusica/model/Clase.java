package co.edu.uniquindio.poo.proyectofinalmusica.model;

import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Aula;
import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Horario;

public abstract class Clase {
    protected String id;
    protected String horario;
    protected String diaSemana;
    protected String horaInicio;
    protected String horaFin;
    protected TipoInstrumento instrumento;
    protected int nivel;
    protected boolean activa;
    protected Profesor theProfesor; // muchos a 1
    protected Aula theAula; // muchos a 1
    protected Horario theHorario; // 1 a 1

    public Clase(String id, String horario, String diaSemana, String horaInicio,
                 String horaFin, TipoInstrumento instrumento, int nivel, boolean activa) {
        this.id = id;
        this.horario = horario;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.instrumento = instrumento;
        this.nivel = nivel;
        this.activa = activa;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
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

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public Profesor getTheProfesor() {
        return theProfesor;
    }

    public void setTheProfesor(Profesor theProfesor) {
        this.theProfesor = theProfesor;
    }

    public Aula getTheAula() {
        return theAula;
    }

    public void setTheAula(Aula theAula) {
        this.theAula = theAula;
    }

    public Horario getTheHorario() {
        return theHorario;
    }

    public void setTheHorario(Horario theHorario) {
        this.theHorario = theHorario;
    }

    // Métodos abstractos que deben implementar las clases hijas
    public abstract void registrarAsistencia(Estudiante estudiante, boolean presente){
}