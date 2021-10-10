package com.ventas.ventas.users;

public class Usuario {
    private int usuarioid;
    private String nombre;
    private int rolid;
    private String rol;
    private String password;

	public Usuario() {

	}
    
	public Usuario(int usuarioid, String nombre, int rolid, String rol, String password) {
		this.usuarioid = usuarioid;
		this.nombre = nombre;
		this.rolid = rolid;
		this.rol = rol;
		this.password = password;
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
	public int getRolid() {
		return rolid;
	}
	public void setRolid(int rolid) {
		this.rolid = rolid;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
		
	}
    
    
}
