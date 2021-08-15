package com.ventas.ventas.tutorial;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class Dao {

    private String dbuser = "";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Modelo> list(){
        String sql = "SELECT * FROM "+ dbuser +"CLIENTES";
        List<Modelo> listPrueba = jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Modelo.class));
        return listPrueba;
    }

    public void save(Modelo nuevo) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("clientes").usingColumns("nombre");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(nuevo);
         
        insertActor.execute(param);    
    }

    public Modelo get(int id) {
		String sql = "SELECT * FROM "+ dbuser +"CLIENTES WHERE clientid = ?";
		Object[] args = {id};
		Modelo sale = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Modelo.class));
		return sale;
	}

    public void update(Modelo modelo) {
		String sql = "UPDATE "+ dbuser +"CLIENTES SET nombre=:nombre WHERE clientid=:clientid";
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(modelo);
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
		template.update(sql, param);		
	}

    public void delete(int id) {
		String sql = "DELETE FROM "+ dbuser +"CLIENTES WHERE clientid = ?";
		jdbcTemplate.update(sql, id);
	}

}
