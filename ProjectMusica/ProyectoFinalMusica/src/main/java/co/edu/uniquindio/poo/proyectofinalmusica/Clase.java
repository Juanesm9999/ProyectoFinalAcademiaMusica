package ProyectoFinal.model;

import ProyectoFinal.model.gestion.Aula;

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
    public void registrarAsistenciaClaseIndividual();

    public void evaluarProgresoClaseIndividual(){
    }

}