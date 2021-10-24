package com.ventas.ventas.users;

import java.sql.Date;

public class Acciones {
    private int accionid;
    private String accion;
    private int usuarioid;
    private String nombre;
    private String tabla;
    private Date fecha;
    
    public Acciones(){

    }

    public Acciones(int accionid, String accion, int usuarioid, String nombre, String tabla, Date fecha) {
        this.accionid = accionid;
        this.accion = accion;
        this.usuarioid = usuarioid;
        this.nombre = nombre;
        this.tabla = tabla;
        this.fecha = fecha;
    }

    public int getAccionid() {
        return accionid;
    }

    public void setAccionid(int accionid) {
        this.accionid = accionid;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public int getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(int usuarioid) {
        this.usuarioid = usuarioid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    
}
