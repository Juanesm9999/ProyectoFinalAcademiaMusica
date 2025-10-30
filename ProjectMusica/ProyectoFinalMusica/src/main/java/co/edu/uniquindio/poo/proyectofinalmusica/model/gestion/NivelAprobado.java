package co.edu.uniquindio.poo.proyectofinalmusica.model.gestion;

import co.edu.uniquindio.poo.proyectofinalmusica.model.TipoInstrumento;

public class NivelAprobado {
    private String id;
    private TipoInstrumento instrumento;
    private int nivel;
    private String fechaAprobacion;
    private double calificacion;
    private Curso theCurso;

    public NivelAprobado(String id, TipoInstrumento instrumento, int nivel,String fechaAprobacion, double calificacion, Curso theCurso) {
        this.id = id;
        this.instrumento = instrumento;
        this.nivel = nivel;
        this.fechaAprobacion = fechaAprobacion;
        this.calificacion = calificacion;
        this.theCurso = theCurso;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(String fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public Curso getTheCurso() {
        return theCurso;
    }

    public void setTheCurso(Curso theCurso) {
        this.theCurso = theCurso;
    }
}
