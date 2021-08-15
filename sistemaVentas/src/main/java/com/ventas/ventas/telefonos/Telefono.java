package com.ventas.ventas.telefonos;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Telefono {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String telcodigo;

    @Column
    private String modelo;
    @Column
    private float ram;
    @Column
    private float almacenamiento;
    @Column
    private String procesador;
    @Column
    private int cores;
    @Column
    private String descripcion;
    @Column
    private int fabricaid;
    @Column
    private String fabrica;
    @Column
    private float preciofabrica;
    @Column
    private float precioventa;
    @Column
    private int disponible;
    @Column
    private int inventario;
    @Column
    private String color;

    @Column
    private int fotoid;

    @Column
    private String foto;

    private String[] imagenes;
    
    public Telefono() {
       
    }

    public Telefono(String telcodigo, String foto) {
      this.telcodigo = telcodigo;
      this.foto = foto;
       
    }

    public Telefono(String telcodigo, float preciofabrica) {
      this.telcodigo = telcodigo;
      this.preciofabrica = preciofabrica;
      this.calcularPrecio();
    }

    public Telefono(String telcodigo, String modelo, float ram, float almacenamiento, String procesador, int cores,
        String descripcion, int fabricaid, String fabrica, float preciofabrica, float precioventa, int disponible,
        int inventario, String color, int fotoid, String foto) {
      this.telcodigo = telcodigo;
      this.modelo = modelo;
      this.ram = ram;
      this.almacenamiento = almacenamiento;
      this.procesador = procesador;
      this.cores = cores;
      this.descripcion = descripcion;
      this.fabricaid = fabricaid;
      this.fabrica = fabrica;
      this.preciofabrica = preciofabrica;
      this.precioventa = precioventa;
      this.disponible = disponible;
      this.inventario = inventario;
      this.color = color;
      this.fotoid = fotoid;
      this.foto = foto;
    }

    public String getTelcodigo() {
      return telcodigo;
    }

    public void setTelcodigo(String telcodigo) {
      this.telcodigo = telcodigo;
    }

    public String getModelo() {
      return modelo;
    }

    public void setModelo(String modelo) {
      this.modelo = modelo;
    }

    public float getRam() {
      return ram;
    }

    public void setRam(float ram) {
      this.ram = ram;
    }

    public float getAlmacenamiento() {
      return almacenamiento;
    }

    public void setAlmacenamiento(float almacenamiento) {
      this.almacenamiento = almacenamiento;
    }

    public String getProcesador() {
      return procesador;
    }

    public void setProcesador(String procesador) {
      this.procesador = procesador;
    }

    public int getCores() {
      return cores;
    }

    public void setCores(int cores) {
      this.cores = cores;
    }

    public String getDescripcion() {
      return descripcion;
    }

    public void setDescripcion(String descripcion) {
      this.descripcion = descripcion;
    }

    public int getFabricaid() {
      return fabricaid;
    }

    public void setFabricaid(int fabricaid) {
      this.fabricaid = fabricaid;
    }

    public String getFabrica() {
      return fabrica;
    }

    public void setFabrica(String fabrica) {
      this.fabrica = fabrica;
    }

    public float getPreciofabrica() {
      return preciofabrica;
    }

    public void setPreciofabrica(float preciofabrica) {
      this.preciofabrica = preciofabrica;
    }

    public float getPrecioventa() {
      return precioventa;
    }

    public void setPrecioventa(float precioventa) {
      this.precioventa = precioventa;
    }

    public int getDisponible() {
      return disponible;
    }

    public void setDisponible(int disponible) {
      this.disponible = disponible;
    }

    public int getInventario() {
      return inventario;
    }

    public void setInventario(int inventario) {
      this.inventario = inventario;
    }

    public String getColor() {
      return color;
    }

    public void setColor(String color) {
      this.color = color;
    }

    public int getFotoid() {
      return fotoid;
    }

    public void setFotoid(int fotoid) {
      this.fotoid = fotoid;
    }

    public String getFoto() {
      return foto;
    }

    public void setFoto(String foto) {
      this.foto = foto;
    }

    public String[] getImagenes(){
      return imagenes;
    }

    public void setImagenes (String[] imagenes){
      this.imagenes = imagenes;
    }

    public void calcularPrecio(){
      float resultado = (float) (this.preciofabrica * 1.9);
      this.precioventa = resultado;
    }

    
}
