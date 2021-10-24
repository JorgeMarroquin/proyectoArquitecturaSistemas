package com.ventas.ventas;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.List;

import com.ventas.ventas.fabricas.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

public class FabDaoTest{

    @Autowired(required = true)
    private FabDao dao;

    @BeforeEach
    void setUp() throws Exception{
        dao = mock(FabDao.class);
    }
    
    @Test
	public void testList() {
        List<Fabricante> lista = dao.list();
        assertTrue(lista.isEmpty());
	}

    @Test
    public void testSave() throws IOException{
        Fabricante fabrica = new Fabricante(1, "test", 177013, "177.01.3");
        dao.save(fabrica);
    }

    @Test
    void testUpdate() {
        Fabricante fabrica = new Fabricante(1, "test2", 177013, "177.01.3");
        dao.update(fabrica);
    }

    @Test
    void testDelete() {
        dao.delete(64);
    }
}