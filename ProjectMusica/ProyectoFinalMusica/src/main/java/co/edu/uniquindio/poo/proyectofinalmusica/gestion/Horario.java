package ProyectoFinal.model.gestion;

import ProyectoFinal.model.Clase;

public class Horario {
    private String id;
    private Clase theClase; // 1 a 1
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private Aula theAula; // muchos a 1

    public Horario(String id, String diaSemana, String horaInicio, String horaFin, Aula aula) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.theAula = aula;
    }
}

