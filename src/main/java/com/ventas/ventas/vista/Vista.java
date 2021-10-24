package com.ventas.ventas.vista;

public class Vista {
    private String telcodigo;
    private int cantidad;
    private float total;
    private float ganancia;

    /*public Vista(){

    }
    
    public Vista(String telcodigo, int cantidad, float total, float ganancia) {
        this.telcodigo = telcodigo;
        this.cantidad = cantidad;
        this.total = total;
        this.ganancia = ganancia;
    }*/

    public String getTelcodigo() {
        return telcodigo;
    }
    public void setTelcodigo(String telcodigo) {
        this.telcodigo = telcodigo;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public float getTotal() {
        return total;
    }
    public void setTotal(float total) {
        this.total = total;
    }
    public float getGanancia() {
        return ganancia;
    }
    public void setGanancia(float ganancia) {
        this.ganancia = ganancia;
    }

    
}
