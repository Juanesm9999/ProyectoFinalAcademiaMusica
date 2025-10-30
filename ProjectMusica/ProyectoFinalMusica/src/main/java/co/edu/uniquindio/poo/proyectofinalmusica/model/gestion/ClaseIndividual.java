package co.edu.uniquindio.poo.proyectofinalmusica.model.gestion;

import co.edu.uniquindio.poo.proyectofinalmusica.model.Clase;
import co.edu.uniquindio.poo.proyectofinalmusica.model.Estudiante;
import co.edu.uniquindio.poo.proyectofinalmusica.model.TipoInstrumento;

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

    public Estudiante getTheEstudiante() {
        return theEstudiante;
    }

    public void setTheEstudiante(Estudiante theEstudiante) {
        this.theEstudiante = theEstudiante;
    }

    public String getTemaEspecifico() {
        return temaEspecifico;
    }

    public void setTemaEspecifico(String temaEspecifico) {
        this.temaEspecifico = temaEspecifico;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public void registrarAsistencia(Estudiante estudiante, boolean presente) {

    }

    @Override
    public void evaluarProgreso(Estudiante estudiante, double calificacion, String comentarios) {

    }
}
