package com.ventas.ventas.fabricas;

public class Fabricante {
    private int fabricaid;
    private String fabrica;
    private int puerto;
    private String ip;

    public Fabricante(){
        
    }

    public Fabricante(int fabricaid, String fabrica, int puerto, String ip) {
        this.fabricaid = fabricaid;
        this.fabrica = fabrica;
        this.puerto = puerto;
        this.ip = ip;

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

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String generateUrl() {
        return "http://" + ip + ":" + puerto + "/";
    }
    
}
