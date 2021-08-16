package com.ventas.ventas.pedidos;

import java.sql.Date;
import java.text.DecimalFormat;

import com.ventas.ventas.Carrito;

public class Orden {
    private DecimalFormat df = new DecimalFormat("#.00"); 
    private int ordenid;
    private int nit;
    private Date fecha;
    private float total;
    private float descuento;
    private float subtotal;

    public Orden(){

    }

    public Orden(int nit, float total, float descuento, float subtotal){
        this.nit = nit;
        this.fecha = new Date(System.currentTimeMillis());
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = total;
    }
    
    public Orden(int nit , Carrito carrito) {
        this.nit = nit;
        this.fecha = new Date(System.currentTimeMillis());
        this.total = Float.valueOf(df.format(carrito.totales()));
        this.descuento = Float.valueOf(df.format(carrito.descuentoTotal()));
        this.subtotal = Float.valueOf(df.format(carrito.subtotal()));
    }

    public int getOrdenid() {
        return ordenid;
    }

    public void setOrdenid(int ordenid) {
        this.ordenid = ordenid;
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    
}
