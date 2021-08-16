package com.ventas.ventas.pedidos;

import java.sql.Date;
import java.text.DecimalFormat;

import com.ventas.ventas.Configuracion;
import com.ventas.ventas.telefonos.*;

public class Pedido {
    private DecimalFormat df = new DecimalFormat("#.00"); 
    private Telefono telefono;
    private Configuracion configuracion = new Configuracion();

    private int compraid;
    private int ordenid;
    private String telcodigo;
    private int cantidad;
    private float descuento;
    private float subtotal;
    private float total;
    private String estado;
    private Date fecha;
    private Date fechaCompra;
    private Date fechaEntrega;
    private String id; //campo _id de mongo
    private String tienda = configuracion.getTienda();
    private String fabrica;
    private float preciofabrica;

    public Pedido(){

    }

    public Pedido(int cantidad, Telefono telefono, String estado) {
        this.cantidad = cantidad;
        this.telefono = telefono;
        this.estado = estado;
    }

    public Pedido(int cantidad, Telefono telefono) {

        this.cantidad = cantidad;
        this.telefono = telefono;
    }

    public Pedido(String telcodigo, int cantidad, float total, String estado, Date fechaCompra, Date fechaEntrega,
            String tienda) {
        this.telcodigo = telcodigo;
        this.cantidad = cantidad;
        this.total = total;
        this.estado = estado;
        this.fechaCompra = fechaCompra;
        this.fechaEntrega = fechaEntrega;
        this.tienda = tienda;
    }

    public Telefono getTelefono() {
        return telefono;
    }

    public void setTelefono(Telefono telefono) {
        this.telefono = telefono;
    }

    public float subtotalPedido(){
        float resultado = this.cantidad * this.telefono.getPrecioventa(); 
        resultado = Float.valueOf(df.format(resultado));
        return resultado;
    }

    public float descuentoTotal(float descuento){
        float resultado = this.subtotalPedido() * descuento;
        resultado = Float.valueOf(df.format(resultado));
        return resultado;
    }

    public float totalPedido(float descuento){
        float resultado = this.subtotalPedido() * (1 - descuento);
        resultado = Float.valueOf(df.format(resultado));
        return resultado;
    }

    public void terminar(float descuento){
        this.descuento = this.descuentoTotal(descuento);
        this.total = this.totalPedido(descuento);
        this.subtotal = this.subtotalPedido();
        this.telcodigo = this.telefono.getTelcodigo();
        this.preciofabrica = this.telefono.getPreciofabrica();
    }

    public int getCompraid() {
        return compraid;
    }

    public void setCompraid(int compraid) {
        this.compraid = compraid;
    }

    public int getOrdenid() {
        return ordenid;
    }

    public void setOrdenid(int ordenid) {
        this.ordenid = ordenid;
    }

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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechacompra) {
        this.fechaCompra = fechacompra;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechacompra) {
        this.fechaEntrega = fechacompra;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getFabrica(){
        return fabrica;
    }

    public void setFabrica(String fabrica){
        this.fabrica = fabrica;
    }

    public float gerPreciofabrica(){
        return preciofabrica;
    }

    public void setPreciofabrica(float precioFabrica){
        this.preciofabrica = precioFabrica;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
