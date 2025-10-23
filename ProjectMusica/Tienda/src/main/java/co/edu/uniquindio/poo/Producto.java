package co.edu.uniquindio.poo;

import java.io.Serializable;

public abstract class Producto implements IConsumible{

    protected int precio, cantidadSalsas;
    protected String tamanio;

    public Producto(int precio, int cantidadSalsas, String tamanio) {
        this.precio = precio;
        this.cantidadSalsas = cantidadSalsas;
        this.tamanio = tamanio;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidadSalsas() {
        return cantidadSalsas;
    }

    public void setCantidadSalsas(int cantidadSalsas) {
        this.cantidadSalsas = cantidadSalsas;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }
}
