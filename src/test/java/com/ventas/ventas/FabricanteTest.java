package com.ventas.ventas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ventas.ventas.fabricas.*;

import org.junit.jupiter.api.*;

class FabricanteTest {

    private Fabricante fabrica = new Fabricante();
    
    @Test
    void testFabricaId(){
        fabrica.setFabricaid(1);
        assertEquals(1, fabrica.getFabricaid());
        fabrica.setFabricaid(2);
        assertEquals(2, fabrica.getFabricaid());
    }
    
    @Test
    void testFabrica(){
        fabrica.setFabrica("Test");
        assertEquals("Test", fabrica.getFabrica());
        fabrica.setFabrica("Test2");
        assertEquals("Test2", fabrica.getFabrica());
    }

    @Test
    void testPuerto(){
        fabrica.setPuerto(8080);
        assertEquals(8080, fabrica.getPuerto());
        fabrica.setPuerto(8090);
        assertEquals(8090, fabrica.getPuerto());
    }

    @Test
    void testIp(){
        fabrica.setIp("localhost");
        assertEquals("localhost", fabrica.getIp());
        fabrica.setIp("google");
        assertEquals("google", fabrica.getIp());
    }
}