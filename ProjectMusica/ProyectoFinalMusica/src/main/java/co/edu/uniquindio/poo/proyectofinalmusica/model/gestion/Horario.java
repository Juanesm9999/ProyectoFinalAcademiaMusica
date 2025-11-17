package co.edu.uniquindio.poo.proyectofinalmusica.model.gestion;

import co.edu.uniquindio.poo.proyectofinalmusica.model.Clase;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Profesor;
import co.edu.uniquindio.poo.proyectofinalmusica.model.TipoInstrumento;

public class Horario {
    private String id;
    private Clase theClase; // 1 a 1
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private Aula theAula;
    private Profesor profesor;
    private Clase clase;
    private Curso curso;
    private TipoInstrumento tipoInstrumento;
    public Horario(String id, String diaSemana, String horaInicio, String horaFin, Aula aula) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.theAula = aula;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Clase getTheClase() {
        return theClase;
    }

    public void setTheClase(Clase theClase) {
        this.theClase = theClase;
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

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public TipoInstrumento getTipoInstrumento() {
        return tipoInstrumento;
    }

    public void setTipoInstrumento(TipoInstrumento tipoInstrumento) {
        this.tipoInstrumento = tipoInstrumento;
    }
}

