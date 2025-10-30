package co.edu.uniquindio.poo.proyectofinalmusica.model;

import co.edu.uniquindio.poo.proyectofinalmusica.model.gestion.Aula;

public abstract class Clase implements IEvaluable {
    protected String id;
    protected String horario;
    protected String diaSemana;
    protected String horaInicio;
    protected String horaFin;
    protected Aula theAula; // 1 a 1
    protected Profesor theProfesor; // 1 a 1
    protected TipoInstrumento instrumento;
    protected int nivel;
    protected boolean activa;

    public Clase(String id, String horario,String diaSemana, String horaInicio,String HoraFin,TipoInstrumento instrumento, int nivel, boolean activa) {
        this.id = id;
        this.horario = horario;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.instrumento = instrumento;
        this.nivel = nivel;
        this.activa = activa;



    }

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

    public Aula getTheAula() {
        return theAula;
    }

    public void setTheAula(Aula theAula) {
        this.theAula = theAula;
    }

    public Profesor getTheProfesor() {
        return theProfesor;
    }

    public void setTheProfesor(Profesor theProfesor) {
        this.theProfesor = theProfesor;
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


}