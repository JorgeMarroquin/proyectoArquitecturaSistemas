package com.ventas.ventas.users;

import java.sql.Date;
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
public class UsuarioDao {
    Configuracion configuracion = new Configuracion();

    private String dbuser = configuracion.getDb();
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public UsuarioDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //LOG----------------------------------------------------------------------------------------
    public void newLog(String accion, int user, String tabla){
        Date date = new Date(System.currentTimeMillis());
        String sql = "INSERT INTO "+ dbuser +"ACCIONES (ACCION, USUARIOID, TABLA, FECHA) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update( sql, accion, user, tabla, date);
    }

    public List<Acciones> listAccion(){
        String sql = "SELECT * FROM "+ dbuser +"ACCIONES JOIN "+ dbuser +"USERS USING(USUARIOID) ORDER BY FECHA DESC";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Acciones.class));
    }

    //LOGIN--------------------------------------------------------------------------------------
    public Usuario logIn(String nombre, String password) {
        try {
            String sql = "SELECT * FROM "+ dbuser +"USERS WHERE NOMBRE = ? AND PASSWORD = ? ";
		    Object[] args = {nombre, password};
		    Usuario user = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Usuario.class));
            return user;
       } 
       catch (EmptyResultDataAccessException e) {
          return null;
       }

	}

    //USUARIO CRUD-----------------------------------------------------------------------------------
    public List<Usuario> list(){
        String sql = "SELECT * FROM "+ dbuser +"USERS JOIN "+ dbuser +"ROL USING(ROLID) ORDER BY USUARIOID";
        List<Usuario> listPrueba = jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Usuario.class));
        return listPrueba;
    }

    public List<Usuario> listRol(){
        String sql = "SELECT * FROM "+ dbuser +"ROL";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Usuario.class));
    }

    public void save(Usuario nuevo) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withSchemaName(dbuser).withTableName("users").usingColumns("nombre","rolid", "password");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(nuevo);
         
        insertActor.execute(param);    
    }

    public Usuario get(int id) {
		String sql = "SELECT * FROM "+ dbuser +"USERS WHERE USUARIOID = ?";
		Object[] args = {id};
		Usuario fabrica = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Usuario.class));
		return fabrica;
	}

    public void update(Usuario modelo) {
		String sql = "UPDATE "+ dbuser +"USERS SET nombre=:nombre, rolid=:rolid, password=:password WHERE usuarioid=:usuarioid";
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(modelo);
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
		template.update(sql, param);		
	}

    public void delete(int id) {
		String sql = "DELETE FROM "+ dbuser +"USERS WHERE USUARIOID = ?";
		jdbcTemplate.update(sql, id);
	}
    
}
