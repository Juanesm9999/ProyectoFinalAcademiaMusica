package ProyectoFinal.model.gestion;

import ProyectoFinal.model.Clase;
import ProyectoFinal.model.Estudiante;
import ProyectoFinal.model.TipoInstrumento;

public class ClaseIndividual extends Clase {
    private Estudiante theEstudiante; // 1 a 1
    private String temaEspecifico;
    private String objetivos;
    private String observaciones;

    public ClaseIndividual(String temaEspecifico, String objetivos, String observaciones,String id, String horario,String diaSemana, String horaInicio,String HoraFin,TipoInstrumento instrumento, int nivel, boolean activa){
        super(id,horario,diaSemana,horaInicio,HoraFin,instrumento,nivel,activa);
        this.temaEspecifico = temaEspecifico;
        this.objetivos = objetivos;
        this.observaciones = observaciones;
    }
    @Override
    public void registrarAsistenciaClaseIndividual(Estudiante estudiante, boolean presente) {   

    }

    @Override
    public void evaluarProgresoClaseIndividual(Estudiante estudiante, double calificacion, String comentarios) {

    }
}
