package com.ventas.ventas.pedidos;

import java.util.List;

import com.ventas.ventas.Configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class PedidoDao {
    Configuracion configuracion = new Configuracion();

    private String dbuser = configuracion.getDb();
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public PedidoDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Orden> listOrden(){
        String sql = "SELECT * FROM "+ dbuser +"ORDENES ORDER BY FECHA DESC";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Orden.class));
    }

    public List<Pedido> listPedido(String ordenid){
        String sql = "SELECT * FROM "+ dbuser +"COMPRAS WHERE ORDENID = "+ ordenid +" ORDER BY COMPRAID";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Pedido.class));
    }

    public List<Pedido> listPedidosByFabrica(int fabrica){
        String sql = "SELECT TELCODIGO, CANTIDAD, TOTAL FROM ( SELECT * FROM "+ dbuser +"COMPRAS JOIN (SELECT ORDENID, FECHA FROM "+ dbuser +"ORDENES) USING(ORDENID) WHERE TRUNC(CURRENT_TIMESTAMP) = TRUNC(FECHA)) JOIN "+ dbuser +"TELEFONOS USING(TELCODIGO) WHERE FABRICAID = " + fabrica;
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Pedido.class));
    }

    public List<Compra> getListByClient(int nit){
        String sql = "SELECT * FROM "+ dbuser +"COMPRAS JOIN "+ dbuser +"ORDENES USING(ORDENID) WHERE NIT = " + nit + " AND EXTRACT(MONTH FROM FECHA) = EXTRACT(MONTH FROM CURRENT_TIMESTAMP) AND EXTRACT(YEAR FROM FECHA) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP) AND ESTADO = 'credito'";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Compra.class));
    }

    public List<Compra> getPendiente(int nit){
        String sql = "SELECT * FROM "+ dbuser +"COMPRAS JOIN "+ dbuser +"ORDENES USING(ORDENID) WHERE NIT = " + nit + " AND ESTADO = 'pendiente'";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Compra.class));
    }

    public void savePedido(Pedido nuevo) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withSchemaName(dbuser).withTableName("compras").usingColumns("ordenid","telcodigo","cantidad", "descuento", "subtotal", "total", "estado");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(nuevo);
        insertActor.execute(param);    
    }

    public Orden get(int id) {
		String sql = "SELECT * FROM "+ dbuser +"ORDENES WHERE ORDENID = ?";
		Object[] args = {id};
		Orden orden = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Orden.class));
		return orden;
	}

    public void generarOrden(Orden orden){
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withSchemaName(dbuser).withTableName("ordenes").usingColumns("nit","fecha","total", "descuento", "subtotal");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(orden);
        insertActor.execute(param); 
    }

    public Orden getLast(int nit){
        String sql = "SELECT * FROM "+ dbuser +"ORDENES WHERE NIT = ? ORDER BY ORDENID DESC FETCH FIRST 1 ROWS ONLY";
		Object[] args = {nit};
		Orden orden = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Orden.class));
		return orden;
    }

    public void cancelar(int id) {
        String updateQuery = "UPDATE "+ dbuser +"COMPRAS SET ESTADO = 'Cancelado' WHERE COMPRAID = ?";
        jdbcTemplate.update(updateQuery, id);
	}

    public void recibir(int id) {
        String updateQuery = "UPDATE "+ dbuser +"COMPRAS SET ESTADO = 'Recibido' WHERE COMPRAID = ?";
        jdbcTemplate.update(updateQuery, id);
	}

    public void entregar(int id) {
        String updateQuery = "UPDATE "+ dbuser +"COMPRAS SET ESTADO = 'entregado' WHERE COMPRAID = ?";
        jdbcTemplate.update(updateQuery, id);
	}

    public Compra comprobar(String telcode, int cantidad){
        String sql = "SELECT "+ dbuser +"COMPROBAREXISTENCIAS(?, " + cantidad + ") AS CANTIDAD FROM DUAL";
		Object[] args = {telcode};
		Compra orden = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Compra.class));
		return orden;
    }

    public void delete(int id) {
		String sql = "DELETE FROM "+ dbuser +"ORDENES WHERE ORDENID = ?";
		jdbcTemplate.update(sql, id);
	}
}
