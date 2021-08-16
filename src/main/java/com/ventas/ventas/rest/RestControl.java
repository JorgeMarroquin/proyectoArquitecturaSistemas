package com.ventas.ventas.rest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.ventas.ventas.fabricas.*;
import com.ventas.ventas.pedidos.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RestControl {
    @Autowired(required = true)
	private PedidoDao dao;

    @Autowired(required = true)
	private FabDao fabDao;

    @GetMapping("/reportes")
	public List<Pedido> greeting(@RequestParam(value = "name", defaultValue = "") String name) {
        Fabricante fabrica = fabDao.getByName(name);
        List<Pedido> pedidos = dao.listPedidosByFabrica(fabrica.getFabricaid());
		return pedidos;
	}
}

