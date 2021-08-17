package com.ventas.ventas;

import static org.junit.jupiter.api.Assertions.*;

import com.ventas.ventas.clientes.ModeloCliente;
import com.ventas.ventas.telefonos.*;

import org.junit.jupiter.api.Test;

public class CarritoTest {

    private Carrito carrito = new Carrito();
    private Telefono telefono = new Telefono();
    private Telefono telefono2 = new Telefono();
    private ModeloCliente cliente = new ModeloCliente();

    private void setAll(){
        telefono.setTelcodigo("1");
        telefono.setPrecioventa(100);
        telefono2.setTelcodigo("2");
        telefono2.setPrecioventa(200);
        carrito.addPedido(1, telefono, "contado");
        carrito.addPedido(1, telefono2, "credito");
        cliente.setDescuento((float) 0.1);
        cliente.setVencimiento("2022-01-01");
    }
    
    @Test
    void testAddPedido() {
        Telefono telefono = new Telefono();
        telefono.setTelcodigo("1");
        carrito.addPedido(1, telefono, "contado");
        assertEquals(1, carrito.getCarro().size());
        carrito.addPedido(1, telefono, "credito");
        assertEquals(2, carrito.getCarro().size());
    }

    @Test
    void testFind() {
        Telefono telefono = new Telefono();
        telefono.setTelcodigo("2");
        assertNull(carrito.find("2", "contado"));
        carrito.addPedido(1, telefono, "contado");
        assertNotNull(carrito.find("2", "contado"));

    }

    @Test
    void testDeletePedido() {
        Telefono telefono = new Telefono();
        telefono.setTelcodigo("1");
        carrito.addPedido(1, telefono, "contado");
        carrito.addPedido(1, telefono, "credito");
        carrito.deletePedido("1", "contado");
        assertEquals(1, carrito.getCarro().size());
        assertEquals("credito", carrito.getCarro().get(0).getEstado());
    }

    @Test
    void testSubtotal() {
        setAll();
        assertEquals(300, carrito.subtotal());
    }

    @Test
    void testDescuentoTotal() {
        setAll();
        ModeloCliente cliente = new ModeloCliente();
        cliente.setDescuento((float) 0.1);
        cliente.setVencimiento("2022-01-01");
        carrito.setClient(cliente);
        assertEquals(30, carrito.descuentoTotal());
    }

    @Test
    void testTotales() {
        setAll();
        carrito.setClient(cliente);
        assertEquals(270, carrito.totales());
    }

    @Test
    void testTerminar() {
        setAll();
        carrito.setClient(cliente);
        carrito.terminar(1);
        assertEquals(1, carrito.getCarro().get(0).getOrdenid());
        assertEquals(1, carrito.getCarro().get(1).getOrdenid());
        assertEquals(10, carrito.getCarro().get(0).getDescuento());
        assertEquals(20, carrito.getCarro().get(1).getDescuento());
        assertEquals(100, carrito.getCarro().get(0).getSubtotal());
        assertEquals(200, carrito.getCarro().get(1).getSubtotal());
        assertEquals(90, carrito.getCarro().get(0).getTotal());
        assertEquals(180, carrito.getCarro().get(1).getTotal());
        assertEquals(180, carrito.getCarro().get(1).getTotal());
    }
}
