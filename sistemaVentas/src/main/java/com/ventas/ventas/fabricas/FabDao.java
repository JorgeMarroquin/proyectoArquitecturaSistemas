package com.ventas.ventas.fabricas;

import java.util.List;

import com.ventas.ventas.Configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class FabDao {

    Configuracion configuracion = new Configuracion();

    private String dbuser = configuracion.getDb();
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public FabDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Fabricante> list(){
        String sql = "SELECT * FROM "+ dbuser +"FABRICANTES ORDER BY FABRICAID";
        List<Fabricante> listPrueba = jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Fabricante.class));
        return listPrueba;
    }

    public List<Fabricante> listDisponibles(){
        String sql = "SELECT * FROM "+ dbuser +"FABRICANTES WHERE PUERTO != 0";
        List<Fabricante> listPrueba = jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Fabricante.class));
        return listPrueba;
    }

    public void save(Fabricante nuevo) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withSchemaName(dbuser).withTableName("fabricantes").usingColumns("fabrica","puerto", "ip");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(nuevo);
         
        insertActor.execute(param);    
    }

    public Fabricante get(int id) {
		String sql = "SELECT * FROM "+ dbuser +"FABRICANTES WHERE FABRICAID = ?";
		Object[] args = {id};
		Fabricante fabrica = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Fabricante.class));
		return fabrica;
	}

    public Fabricante getByName(String fabrica) {
        try{
		String sql = "SELECT * FROM "+ dbuser +"FABRICANTES WHERE FABRICA = ? AND PUERTO != 0";
		Object[] args = {fabrica};
		Fabricante sale = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Fabricante.class));
		return sale;
        }
        catch (EmptyResultDataAccessException e) {
           return null;
        }
	}

    public void update(Fabricante modelo) {
		String sql = "UPDATE "+ dbuser +"FABRICANTES SET fabrica=:fabrica, puerto=:puerto, ip=:ip WHERE fabricaid=:fabricaid";
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(modelo);
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
		template.update(sql, param);		
	}

    public void delete(int id) {
		String sql = "DELETE FROM "+ dbuser +"FABRICANTES WHERE FABRICAID = ?";
		jdbcTemplate.update(sql, id);
	}

}
