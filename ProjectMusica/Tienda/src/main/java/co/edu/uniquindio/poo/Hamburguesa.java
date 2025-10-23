package co.edu.uniquindio.poo;

public class Hamburguesa extends Producto {

    private boolean tienePepinillos;
    private String tipoCarne;

    public Hamburguesa(int precio, int cantidadSalsas, String tamanio, boolean tienePepinillos, String tipoCarne) {
        super(precio, cantidadSalsas, tamanio);
        this.tienePepinillos = tienePepinillos;
        this.tipoCarne = tipoCarne;
    }

    public boolean isTienePepinillos() {
        return tienePepinillos;
    }

    public void setTienePepinillos(boolean tienePepinillos) {
        this.tienePepinillos = tienePepinillos;
    }

    public String getTipoCarne() {
        return tipoCarne;
    }

    public void setTipoCarne(String tipoCarne) {
        this.tipoCarne = tipoCarne;
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
