package co.edu.uniquindio.poo;

import java.awt.geom.Area;

public class ArepaRellena extends Producto {

    private boolean tieneQueso, isHarina;

    public ArepaRellena(int precio, int cantidadSalsas, String tamanio, boolean tieneQueso, boolean harina) {
        super(precio, cantidadSalsas, tamanio);
        this.tieneQueso = tieneQueso;
        this.isHarina = harina;
    }

    public boolean isTieneQueso() {
        return tieneQueso;
    }

    public void setTieneQueso(boolean tieneQueso) {
        this.tieneQueso = tieneQueso;
    }

    public boolean isHarina() {
        return isHarina;
    }

    public void setHarina(boolean harina) {
        isHarina = harina;
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
