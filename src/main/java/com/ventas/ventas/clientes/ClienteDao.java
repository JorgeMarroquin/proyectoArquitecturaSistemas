package com.ventas.ventas.clientes;

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
public class ClienteDao {

    Configuracion configuracion = new Configuracion();

    private String dbuser = configuracion.getDb();
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public ClienteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<ModeloCliente> list(){
        String sql = "SELECT * FROM "+ dbuser +"CLIENTES JOIN "+ dbuser +"TIPOCLIENTES USING(TIPOCLIENTEID) WHERE NIT != 0";
        List<ModeloCliente> listPrueba = jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(ModeloCliente.class));
        return listPrueba;
    }

    public List<ModeloCliente> listTipo(){
        String sql = "SELECT * FROM "+ dbuser +"TIPOCLIENTES";
        List<ModeloCliente> listPrueba = jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(ModeloCliente.class));
        return listPrueba;
    }

    public void save(ModeloCliente nuevo) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withSchemaName(dbuser).withTableName("clientes").usingColumns("nombre","nit","email","telefono","patente","tipoclienteid", "vencimiento");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(nuevo);
         
        insertActor.execute(param);    
    }

    public ModeloCliente get(int nit) {
        try{
		String sql = "SELECT * FROM "+ dbuser +"CLIENTES JOIN "+ dbuser +"TIPOCLIENTES USING(TIPOCLIENTEID) WHERE NIT = ?";
		Object[] args = {nit};
		ModeloCliente sale = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(ModeloCliente.class));
		return sale;
        }
        catch (EmptyResultDataAccessException e) {
           return null;
        }
	}

    public void update(ModeloCliente modelo) {
		String sql = "UPDATE "+ dbuser +"CLIENTES SET nombre=:nombre, email=:email, telefono=:telefono, patente=:patente, tipoclienteid=:tipoclienteid, vencimiento=:vencimiento WHERE nit=:nit";
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(modelo);
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
		template.update(sql, param);		
	}

    public void delete(int nit) {
		String sql = "DELETE FROM "+ dbuser +"CLIENTES WHERE NIT = ?";
		jdbcTemplate.update(sql, nit);
	}

}
