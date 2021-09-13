package com.ventas.ventas.telefonos;

import java.util.List;

import com.ventas.ventas.Configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class TelDao {

    Configuracion configuracion = new Configuracion();

    private String dbuser = configuracion.getDb();
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TelDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Telefono> list(){
        String sql = "SELECT * FROM "+ dbuser +"TELEFONOS JOIN "+ dbuser +"FABRICANTES USING(FABRICAID)";
        List<Telefono> listTel = jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Telefono.class));
        return listTel;
    }

    public List<Telefono> listMarcas(){
        String sql = "SELECT * FROM "+ dbuser +"FABRICANTES";
        List<Telefono> listTel = jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Telefono.class));
        return listTel;
    }

    public List<Telefono> listFotos(String id){
        String sql = "SELECT * FROM "+ dbuser +"FOTOS WHERE TELCODIGO = '"+ id +"'";
        List<Telefono> listTel = jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Telefono.class));
        return listTel;
    }

    public void save(Telefono nuevo) {
        nuevo.calcularPrecio();
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withSchemaName(dbuser).withTableName("telefonos").usingColumns("telcodigo", "modelo", "ram", "almacenamiento", "procesador", "cores", "descripcion", "fabricaid", "preciofabrica", "precioventa", "disponible", "inventario", "color");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(nuevo);
         
        insertActor.execute(param);    
    }

    public void saveFoto(Telefono nuevo) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withSchemaName(dbuser).withTableName("fotos").usingColumns("telcodigo", "foto");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(nuevo);
         
        insertActor.execute(param);    
    }

    public void deleteFoto(String id) {
		String sql = "DELETE FROM "+ dbuser +"FOTOS WHERE FOTOID = ?";
		jdbcTemplate.update(sql, id);
	}

    public Telefono get(String id) {
		String sql = "SELECT * FROM "+ dbuser +"TELEFONOS JOIN "+ dbuser +"FABRICANTES USING(FABRICAID) WHERE TELCODIGO = ?";
		Object[] args = {id};
		Telefono tel = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Telefono.class));
		return tel;
	}

    public void update(Telefono modelo) {
        modelo.calcularPrecio();
		String sql = "UPDATE "+ dbuser +"TELEFONOS SET modelo=:modelo, ram=:ram, almacenamiento=:almacenamiento, procesador=:procesador, cores=:cores, descripcion=:descripcion, fabricaid=:fabricaid, preciofabrica=:preciofabrica, precioventa=:precioventa, disponible=:disponible, inventario=:inventario, color=:color WHERE telcodigo=:telcodigo";
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(modelo);
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
		template.update(sql, param);		
	}

    public void actualizarInventario(int cantidad, String tel) {
        jdbcTemplate.update("CALL "+ dbuser +"SUMAINVENTARIO(?, ?)", cantidad, tel);
	}

    public void delete(String id) {
		String sql = "DELETE FROM "+ dbuser +"TELEFONOS WHERE TELCODIGO = ?";
		jdbcTemplate.update(sql, id);
	}
//test
}
