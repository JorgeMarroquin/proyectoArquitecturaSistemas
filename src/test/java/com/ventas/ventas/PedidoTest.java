package com.ventas.ventas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ventas.ventas.pedidos.Pedido;
import com.ventas.ventas.telefonos.Telefono;

import org.junit.jupiter.api.Test;

class PedidoTest {

    private Pedido pedido = new Pedido(1, new Telefono("1", 100), "contado");

    @Test
    void testSubTotalPedido(){
        assertEquals(190, pedido.subtotalPedido());
    }

    @Test
    void testDescuentoTotal(){
        assertEquals(19, pedido.descuentoTotal((float) 0.1));
    }

    @Test
    void testTotalPedido(){
        assertEquals(171, pedido.totalPedido((float) 0.1));
    }

    @Test
    void testTerminar(){
        pedido.terminar((float) 0.1);
        assertEquals(190, pedido.getSubtotal());
        assertEquals(19, pedido.getDescuento());
        assertEquals(171, pedido.getTotal());
        assertEquals("1", pedido.getTelcodigo());
    }
    
}