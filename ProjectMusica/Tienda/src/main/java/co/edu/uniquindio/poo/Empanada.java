package co.edu.uniquindio.poo;

public class Empanada extends Producto {

    private boolean tipoPapa;

    public Empanada(int precio, int cantidadSalsas, String tamanio, boolean tipoPapa) {
        super(precio, cantidadSalsas, tamanio);
        this.tipoPapa = tipoPapa;
    }

    public boolean isTipoPapa() {
        return tipoPapa;
    }

    public void setTipoPapa(boolean tipoPapa) {
        this.tipoPapa = tipoPapa;
    }

    @Override
    public boolean aptoParaComer() {
        return false;
    }

    @Override
    public boolean ingredientesSuficientes() {
        return false;
    }
}
