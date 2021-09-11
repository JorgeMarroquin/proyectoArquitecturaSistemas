package com.ventas.ventas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ventas.ventas.telefonos.*;

import org.junit.jupiter.api.*;

class TelefonoTest {

    private Telefono telefono = new Telefono();

    @Test
    void testModelo(){
        telefono.setModelo("M");
        assertEquals("M", telefono.getModelo());
        telefono.setModelo("M2");
        assertEquals("M2", telefono.getModelo());
    }

    @Test
    void testInventario(){
        telefono.setInventario(10);
        assertEquals(10, telefono.getInventario());
        telefono.setInventario(20);
        assertEquals(20, telefono.getInventario());
    }

    @Test
    void testPrecioFabrica(){
        telefono.setPreciofabrica(100);
        assertEquals(100, telefono.getPreciofabrica());
        telefono.setPreciofabrica(200);
        assertEquals(200, telefono.getPreciofabrica());
    }

    @Test
    void testCalcularPrecio(){
        telefono.setPreciofabrica(100);
        telefono.calcularPrecio();
        assertEquals(190, telefono.getPrecioventa());
    }
    
}