package co.edu.uniquindio.poo.proyectofinalmusica.model.gestion;

import co.edu.uniquindio.poo.proyectofinalmusica.model.Profesor;

public class BloqueDisponibilidad {
    private String id;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private boolean disponible;
    private Profesor theProfesor;

    public BloqueDisponibilidad(String id, String diaSemana, String horaInicio, String horaFin, boolean disponible,  Profesor theProfesor) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.disponible = disponible;
        this.theProfesor = theProfesor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Profesor getTheProfesor() {
        return theProfesor;
    }

    public void setTheProfesor(Profesor theProfesor) {
        this.theProfesor = theProfesor;
    }
}