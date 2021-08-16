package com.ventas.ventas.clientes;

import javax.persistence.Entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ModeloCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nit = 0;

    @Column
    private String nombre;
    @Column
    private String email;
    @Column 
    private int telefono;
    @Column
    private String patente;
    @Column
    private int tipoclienteid;
    @Column
    private String tipocliente;
    @Column
    private Date vencimiento;
    @Column 
    private float descuento = 0;

    public ModeloCliente(){
        
    }

    public ModeloCliente(int nit, String nombre, String email, int telefono, String patente, int tipoclienteid,
            String tipocliente, Date vencimiento, float descuento) {
        this.nit = nit;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.patente = patente;
        this.tipoclienteid = tipoclienteid;
        this.tipocliente = tipocliente;
        this.vencimiento = vencimiento;
        this.descuento = descuento;
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public int getTipoclienteid() {
        return tipoclienteid;
    }

    public void setTipoclienteid(int tipoclienteid) {
        this.tipoclienteid = tipoclienteid;
    }

    public String getTipocliente() {
        return tipocliente;
    }

    public void setTipocliente(String tipocliente) {
        this.tipocliente = tipocliente;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = Date.valueOf(vencimiento);
    }

    public float getDescuento(){
        return descuento;
    }
    
    public void setDescuento(float descuento){
            this.descuento = descuento;
    }

    public float realdescuento(){
        Date date = new Date(System.currentTimeMillis());
        Date venc = this.vencimiento;
        float respuesta = 0;
        if(venc != null){
            if(venc.compareTo(date) < 0){
                respuesta = 0;
            }else{
                respuesta = this.descuento;
            }
        }
        return respuesta;
    }

    public String estadoCuenta(){
        Date date = new Date(System.currentTimeMillis());
        Date venc = this.vencimiento;
        String respuesta = "";
        if(venc != null){
            if(venc.compareTo(date) < 0){
                respuesta = "Vencido";
            }else{
                respuesta = "Vigente";
            }
        }
        return respuesta;
    }
}