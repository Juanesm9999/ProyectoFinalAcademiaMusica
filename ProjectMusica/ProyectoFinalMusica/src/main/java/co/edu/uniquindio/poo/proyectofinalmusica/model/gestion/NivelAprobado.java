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
}
