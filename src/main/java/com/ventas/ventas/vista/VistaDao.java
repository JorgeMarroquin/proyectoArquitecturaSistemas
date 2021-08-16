package com.ventas.ventas.vista;

import java.util.List;

import com.ventas.ventas.Configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VistaDao {
    Configuracion configuracion = new Configuracion();

    private String dbuser = configuracion.getDb();
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public VistaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Vista> listVentatel(){
        String sql = "SELECT * FROM "+ dbuser +"VENTATELEFONOS ORDER BY CANTIDAD";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Vista.class));
    }

    public List<Vista> listVentames(){
        String sql = "SELECT * FROM "+ dbuser +"VENTAMES ORDER BY CANTIDAD";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Vista.class));
    }

    public List<Vista> listGananciames(){
        String sql = "SELECT * FROM "+ dbuser +"GANANCIAMES ORDER BY GANANCIA";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Vista.class));
    }
}
