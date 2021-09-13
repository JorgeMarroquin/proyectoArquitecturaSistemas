package com.ventas.ventas;

import java.net.ConnectException;
import java.time.*;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.ventas.ventas.telefonos.*;
import com.ventas.ventas.clientes.*;
import com.ventas.ventas.fabricas.*;
import com.ventas.ventas.tutorial.*;
import com.ventas.ventas.users.*;
import com.ventas.ventas.pedidos.*;
import com.ventas.ventas.service.MailService;
import com.ventas.ventas.vista.*;

import org.hibernate.hql.spi.id.IdTableHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Controlador {

    private Configuracion configuracion = new Configuracion();

    private Carrito carrito = new Carrito();
    private boolean login = false;
    private Usuario user = new Usuario();
    private String msg = "";
    private String msgCarrito = "";

    private boolean existeCliente = false;
    private ModeloCliente cliente = new ModeloCliente();

    private String tiendaActual = configuracion.getTienda();
    private String contr = configuracion.getPasswordTienda();

    @Autowired
    private MailService mailService;

    @Autowired
    private Dao dao;

    @Autowired
    private TelDao teldao;

    @Autowired
    private FabDao fabDao;
    
    @Autowired
    private ClienteDao daoc;

    @Autowired
    private UsuarioDao userDao;

    @Autowired
    private PedidoDao pedidoDao;

    @Autowired
    private VistaDao vistaDao;

    //TELEFONO------------------------------------------------------------------------------------------------
    @RequestMapping("/telefonos")
    public String telefonosPage(final Model telefono) {
        final List<Telefono> listTel = teldao.list();
        telefono.addAttribute("listTel", listTel);
        telefono.addAttribute("usuario", this.user);
        return "telefonos/telefonos.html";
    }
    
    @RequestMapping("/newTel")
    public String telefonoNew(Model model) {
        Telefono nuevoTel = new Telefono();
        model.addAttribute("nuevoTel", nuevoTel);
        final List<Telefono> listMar = teldao.listMarcas();
        model.addAttribute("usuario", this.user);
        model.addAttribute("listMar", listMar);
        return "telefonos/telefonosNew.html";
    }

    @RequestMapping(value = "/saveTel", method = RequestMethod.POST)
    public String telefonoSave(@ModelAttribute("newtel") Telefono newtel) {
    teldao.save(newtel);
    userDao.newLog("Nuevo telefono Codigo:  " + newtel.getTelcodigo(), this.user.getUsuarioid(), "TELEFONOS");
    return "redirect:/telefonos";
    }

    @RequestMapping("/editTel/{id}")
    public ModelAndView telefonoEdit(@PathVariable(name = "id") String id) {
        ModelAndView mav = new ModelAndView("telefonos/telefonosUpdate.html");
        Telefono telefono = teldao.get(id);
        List<Telefono> listMar = teldao.listMarcas();
        List<Telefono> listFotos = teldao.listFotos(id);
        Telefono fotoVacia = new Telefono();
        mav.addObject("listMar", listMar);
        mav.addObject("telefono", telefono);
        mav.addObject("listFotos", listFotos);
        mav.addObject("fotoVacia", fotoVacia);
        mav.addObject("usuario", this.user);
        mav.addObject("msg", this.msg);
        this.msg = "";
        return mav;
    }

    @RequestMapping("/verTel/{id}")
    public ModelAndView telefonoVer(@PathVariable(name = "id") String id) {
        ModelAndView mav = new ModelAndView("telefonos/telefonosVer.html");
        Telefono telefono = teldao.get(id);
        List<Telefono> listMar = teldao.listMarcas();
        List<Telefono> listFotos = teldao.listFotos(id);
        Telefono fotoVacia = new Telefono();
        mav.addObject("listMar", listMar);
        mav.addObject("telefono", telefono);
        mav.addObject("listFotos", listFotos);
        mav.addObject("fotoVacia", fotoVacia);
        mav.addObject("usuario", this.user);
        mav.addObject("msg", this.msg);
        this.msg = "";
        return mav;
    }

    @RequestMapping(value = "/saveFoto", method = RequestMethod.POST)
    public String fotoSave(@RequestParam String idtel, @ModelAttribute("fotoVacia") Telefono fotoVacia) {
        if(!fotoVacia.getFoto().equals("")){
            fotoVacia.setTelcodigo(idtel);
            teldao.saveFoto(fotoVacia);
        }
        userDao.newLog("Nueva foto para telefono Codigo:  " + fotoVacia.getTelcodigo(), this.user.getUsuarioid(), "FOTOS");
        return "redirect:/editTel/"+idtel;
    }

    @RequestMapping("/deleteFoto/{id}/{idtel}")
    public String fotoDelete(@PathVariable(name = "id") String id, @PathVariable(name = "idtel") String idtel) {
        teldao.deleteFoto(id);
        userDao.newLog("Foto eliminada para telefono Codigo:  " + id, this.user.getUsuarioid(), "FOTOS");
        return "redirect:/editTel/"+idtel;
    }
        
    @RequestMapping(value = "/updateTel", method = RequestMethod.POST)
    public String telefonoUpdate(@ModelAttribute("telefono") Telefono telefono) {
        teldao.update(telefono);
        System.out.println(telefono.getPreciofabrica());
        userDao.newLog("Telefono editado Codigo:  " + telefono.getTelcodigo(), this.user.getUsuarioid(), "TELEFONOS");
        return "redirect:/telefonos";
    }

    @RequestMapping("/deleteTel/{id}")
    public String telefonoDelete(@PathVariable(name = "id") String id) {
        teldao.delete(id);
        userDao.newLog("Telefono eliminado Codigo:  " + id, this.user.getUsuarioid(), "TELEFONOS");
        return "redirect:/telefonos";       
    }

    @RequestMapping(value = "/addCarrito/{id}/{pagina}", method = RequestMethod.POST)
    public String addCarrito(@PathVariable(name = "id") String id, @PathVariable(name = "pagina") int pagina, @RequestParam String cantidadTel, @RequestParam String estado) {
        Telefono telefono = teldao.get(id);
        int cant = Integer.parseInt(cantidadTel);
        this.carrito.addPedido(cant, telefono, estado);
        switch(pagina){
            case 0:
            return "redirect:/telefonos";  

            case 1:
            return "redirect:/editTel" + id;  

            case 2:
            return "redirect:/verTel/" + id;  

            default:
            return "redirect:/telefonos";  
        }     
    }

    //FABRICA--------------------------------------------------------------------------------------------
    @RequestMapping("/fabricas")
    public String fabricasPage(final Model model) {
        final List<Fabricante> listFab = fabDao.list();
        model.addAttribute("listFab", listFab);
        model.addAttribute("usuario", this.user);
        return "fabricas/fabricas.html";
    }

    @RequestMapping("/newFabrica")
    public String fabricaNew(Model model) {
        Fabricante nuevo = new Fabricante();
        model.addAttribute("nuevo", nuevo);
        model.addAttribute("usuario", this.user);
        return "fabricas/fabricasNew.html";
    }
    
    @RequestMapping(value = "/saveFabrica", method = RequestMethod.POST)
    public String fabricaSave(@ModelAttribute("modelo") Fabricante modelo) {
    fabDao.save(modelo);
    userDao.newLog("Nueva fabrica " + modelo.getFabrica(), this.user.getUsuarioid(), "FABRICANTES");
    return "redirect:/fabricas";
    }

    @RequestMapping("/editFabrica/{id}")
    public ModelAndView fabricaEdit(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("fabricas/fabricasUpdate.html");
        Fabricante modelo = fabDao.get(id);
        mav.addObject("modelo", modelo);
        mav.addObject("usuario", this.user);
        return mav;
    }
        
    @RequestMapping(value = "/updateFabrica", method = RequestMethod.POST)
    public String fabricaUpdate(@ModelAttribute("modelo") Fabricante modelo) {
        fabDao.update(modelo);
        userDao.newLog("Fabrica editada " + modelo.getFabrica(), this.user.getUsuarioid(), "FABRICANTES");
        return "redirect:/fabricas";
    }

    @RequestMapping("/deleteFabrica/{id}")
    public String fabricaDelete(@PathVariable(name = "id") int id) {
        fabDao.delete(id);
        userDao.newLog("Fabrica eliminada ID:  " + id, this.user.getUsuarioid(), "FABRICANTES");
        return "redirect:/fabricas";       
    }

    //CARRITO------------------------------------------------------------------------------------------
    @RequestMapping("/carrito")
    public String carritoPage(final Model carrito) {
        carrito.addAttribute("usuario", this.user);
        carrito.addAttribute("carrito", this.carrito);
        carrito.addAttribute("cliente", this.cliente);
        carrito.addAttribute("msg", this.msg);
        carrito.addAttribute("msgCarrito", this.msgCarrito);
        if(this.carrito.getClient().getNit() != 0 ){
            List<Compra> pendiente = pedidoDao.getPendiente(this.carrito.getClient().getNit());
            carrito.addAttribute("pendientes", pendiente);
        }else{
            List<Compra> pendiente = new ArrayList<Compra>();
            carrito.addAttribute("pendientes", pendiente);
        }
        this.msg = "";
        this.msgCarrito = "";
        return "pedidos/carrito.html";
    }

    @RequestMapping("/entregar/{id}")
    public String entregarPendiente(@PathVariable(name = "id") int id) {
        pedidoDao.entregar(id);
        return "redirect:/carrito";
    }

    @RequestMapping("/deleteCarrito/{id}/{estado}")
    public String carritoDel(@PathVariable(name = "id") String id, @PathVariable(name = "estado") String estado) {
        carrito.deletePedido(id, estado);
        return "redirect:/carrito";
    }

    @RequestMapping(value = "/carritoCliente", method = RequestMethod.POST)
    public String carritoClientePage(@RequestParam String nitCliente) {
        if(!nitCliente.equals("")){
            ModeloCliente clienteOrden = daoc.get(Integer.parseInt(nitCliente));
            this.cliente = new ModeloCliente();
            this.existeCliente = false;
            if(clienteOrden != null){
                this.cliente = clienteOrden;
                this.carrito.setClient(clienteOrden);
                this.existeCliente = true;
            }else{
                this.cliente = new ModeloCliente();
                this.carrito.setClient(cliente);
                this.msg = "No existe el cliente";
            }
        }
        return "redirect:/carrito";
    }

    @RequestMapping("/ordenar")
    public String ordenarCarrito() {
        if(this.cliente.getTipoclienteid() != 1){
            for(Pedido element : this.carrito.getCarro()){
                Compra verificar = pedidoDao.comprobar(element.getTelefono().getTelcodigo(), element.getCantidad());
                System.out.println(element.getCantidad());
                if(verificar.getCantidad() == 0){
                    this.msgCarrito = "El teléfono Codigo " + element.getTelefono().getTelcodigo() + " no cuenta con unidades suficientes";
                    return "redirect:/carrito";
                }
            }
        }
        Orden orden = new Orden(this.cliente.getNit(), this.carrito);
        pedidoDao.generarOrden(orden);
        Orden lastOrden = pedidoDao.getLast(this.cliente.getNit());
        this.carrito.terminar(lastOrden.getOrdenid());
        userDao.newLog("Nueva orden No:  " + lastOrden.getOrdenid(), this.user.getUsuarioid(), "ORDENES");
        for(Pedido element : this.carrito.getCarro()){
            if(element.getEstado().equals("credito")){
                if(this.cliente.getTipoclienteid() == 1){
                    pedidoDao.savePedido(element);
                    userDao.newLog("Nueva compra para orden No:  " + element.getOrdenid(), this.user.getUsuarioid(), "COMPRAS");
                }
            }else{
                pedidoDao.savePedido(element);
                userDao.newLog("Nueva compra para orden No:  " + element.getOrdenid(), this.user.getUsuarioid(), "COMPRAS");
            }
            
        }
        this.carrito = new Carrito();
        this.cliente = new ModeloCliente();
        this.existeCliente = false;
        return "redirect:/carrito";
    }


    //PEDIDOS-----------------------------------------------------------------------------
    
    @RequestMapping("/pedidos")
    public String pedidosPage(final Model model) {
        final List<Orden> listOrden = pedidoDao.listOrden();
        model.addAttribute("listOrden", listOrden);
        model.addAttribute("usuario", this.user);
        return "pedidos/pedidos.html";
    }

    @RequestMapping("/verOrden/{id}")
    public String detallesOrden(Model model, @PathVariable(name = "id") String id) {
        List<Pedido> listPedidos = pedidoDao.listPedido(id);
        Orden orden = pedidoDao.get(Integer.parseInt(id));
        ModeloCliente cli = daoc.get(orden.getNit());
        model.addAttribute("listPedidos", listPedidos);
        model.addAttribute("orden", orden);
        model.addAttribute("cliente", cli);
        model.addAttribute("usuario", this.user);
        return "pedidos/pedidosDetalle.html";
    }

    //CLIENTES-----------------------------------------------------------------------------------------
    @RequestMapping("/sendMail")
    public String sendMail(){

        Month mes = LocalDate.now().getMonth();
        String nombre = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));

        List<ModeloCliente> clientes = daoc.list();

        for(ModeloCliente clienteInd : clientes){
            String header = "Hola " + clienteInd.getNombre() + "\nEstas son tus compras del mes de " + nombre.toUpperCase() + "\n\n";
            String message = header;
            List<Compra> compras = pedidoDao.getListByClient(clienteInd.getNit());
            for(Compra compra : compras){
                message = message + compra.toString();
            }

            message = message + "\n\nSaludos desde la tienda de " + this.tiendaActual;

            mailService.sendMail(clienteInd.getEmail(), nombre.toUpperCase(), message);
        }
        

        return "redirect:/clientes";
    }

    @RequestMapping("/clientes")
    public String clientesPage(final Model model) {
        final List<ModeloCliente> listClient = daoc.list();
        model.addAttribute("listClient", listClient);
        model.addAttribute("usuario", this.user);
        return "clientes/ClienteRead.html";
    }

    @RequestMapping("/newCliente")
    public String clienteNew(Model model) {
        ModeloCliente nuevoClient = new ModeloCliente();
        model.addAttribute("nuevoClient", nuevoClient);
        List<ModeloCliente> listTipos = daoc.listTipo();
        model.addAttribute("listTipos", listTipos);
        model.addAttribute("usuario", this.user);
        return "clientes/clientecreate.html";
    }

    @RequestMapping("/deleteCliente/{nit}")
    public String deleteCliente(@PathVariable(name = "nit") int nit) {
        daoc.delete(nit);
        userDao.newLog("Cliente eliminado nit:  " + nit, this.user.getUsuarioid(), "CLIENTES");
        return "redirect:/clientes";       
    }

    @RequestMapping(value = "/savec", method = RequestMethod.POST)
        public String save(@ModelAttribute("nuevoClient") ModeloCliente nuevoClient) {
        daoc.save(nuevoClient);
        userDao.newLog("Nuevo cliente Nit:  " + nuevoClient.getNit(), this.user.getUsuarioid(), "CLIENTES");
        return "redirect:/clientes";
    }

    @RequestMapping("/editCliente/{nit}")
    public ModelAndView clienteEdit(@PathVariable(name = "nit") int nit) {
        ModelAndView mav = new ModelAndView("clientes/clienteupdate.html");
        ModeloCliente modelo = daoc.get(nit);
        List<ModeloCliente> listTipos = daoc.listTipo();
        mav.addObject("modelo", modelo);
        mav.addObject("listTipos", listTipos);
        mav.addObject("usuario", this.user);
        return mav;
    }
    
    @RequestMapping(value = "/updatec", method = RequestMethod.POST)
    public String clienteUpdate(@ModelAttribute("modelo") ModeloCliente modelo, @RequestParam String fechaVen) {
        modelo.setVencimiento(fechaVen);
        daoc.update(modelo);
        userDao.newLog("Cliente modificado Nit:  " + modelo.getNit(), this.user.getUsuarioid(), "CLIENTES");
        return "redirect:/clientes";
    }

    //USUARIOS---------------------------------------------------------------------------------------------------------------------
    @RequestMapping("/")
    public String viewLogIn(final Model model) {
        if(this.login){
            List<Acciones> acciones = userDao.listAccion();
            List<Vista> vistaTel = vistaDao.listVentatel();
            List<Vista> vistaMes = vistaDao.listVentames();
            List<Vista> ganancias = vistaDao.listGananciames();
            model.addAttribute("ganancias", ganancias);
            model.addAttribute("ventaTel", vistaTel);
            model.addAttribute("ventaMes", vistaMes);
            model.addAttribute("usuario", this.user);
            model.addAttribute("acciones", acciones);
            return "home.html";
        }else{
            model.addAttribute("msg", this.msg);
            return "index.html";
        }
    }

    @RequestMapping(value = "/comprobar", method = RequestMethod.POST)
    public String comprobarUsuario(@RequestParam String name, @RequestParam String pass) {
        Usuario usuario = userDao.logIn(name, pass);
        if(usuario != null){
            this.msg ="";
            this.user = usuario;
            this.login = true;
            userDao.newLog("Inicio de sesion", usuario.getUsuarioid(), "USERS");
        }else{
            this.msg ="Nombre o password incorrecto";
        }
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logOutUser() {
        this.login = false;
        userDao.newLog("Cierre de sesion", this.user.getUsuarioid(), "USERS");
        return "redirect:/";
    }

    @RequestMapping("/usuarios")
    public String usuariosPage(final Model model) {
        final List<Usuario> listUsuario = userDao.list();
        model.addAttribute("listUsuario", listUsuario);
        model.addAttribute("usuario", this.user);
        return "usuarios/usuarios.html";
    }

    @RequestMapping("/newUsuario")
    public String usuarioNew(Model model) {
        Usuario nuevo = new Usuario();
        List<Usuario> roles = userDao.listRol();
        model.addAttribute("nuevo", nuevo);
        model.addAttribute("roles", roles);
        model.addAttribute("usuario", this.user);
        return "usuarios/usuariosNew.html";
    }
    
    @RequestMapping(value = "/saveUsuario", method = RequestMethod.POST)
    public String usuariosave(@ModelAttribute("modelo") Usuario modelo) {
    userDao.save(modelo);
    userDao.newLog("Nuevo usuario: " + modelo.getNombre(), this.user.getUsuarioid(), "USERS");
    return "redirect:/usuarios";
    }

    @RequestMapping("/editUsuario/{id}")
    public ModelAndView usuarioEdit(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("usuarios/usuariosUpdate.html");
        Usuario modelo = userDao.get(id);
        List<Usuario> roles = userDao.listRol();
        mav.addObject("modelo", modelo);
        mav.addObject("roles", roles);
        mav.addObject("usuario", this.user);
        return mav;
    }
        
    @RequestMapping(value = "/updateUsuario", method = RequestMethod.POST)
    public String usuarioUpdate(@ModelAttribute("modelo") Usuario modelo) {
        userDao.update(modelo);
        userDao.newLog("Usuario modificado: " + modelo.getNombre(), this.user.getUsuarioid(), "USERS");
        return "redirect:/usuarios";
    }

    @RequestMapping("/deleteUsuario/{id}")
    public String usuarioDelete(@PathVariable(name = "id") int id) {
        userDao.delete(id);
        userDao.newLog("Usuario borado ID: " + id, this.user.getUsuarioid(), "USERS");
        return "redirect:/usuarios";       
    }

    //REST--------------------------------------------------------
    @RequestMapping(value = "/test")
	public String getDispo(final Model model){
        /*String uri = "http://localhost:3001/test";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Fabricante[]> response = restTemplate.getForEntity(uri,Fabricante[].class);
        Fabricante[] employees = response.getBody();	  
        model.addAttribute("listTest", employees);  
            return "test.html";*/
            String url = "http://localhost:3001/post";

// create an instance of RestTemplate
RestTemplate restTemplate = new RestTemplate();

// create a post object
Fabricante post = new Fabricante(101, "testfinal", 8080,
                "A powerful tool for building web apps.");

// send POST request
restTemplate.postForEntity(url, post, Void.class);


return "redirect:/";
	}

    @RequestMapping(value = "/restTel",method = RequestMethod.GET)
    public String restTelPage(final Model model) {
        List<Telefono> telefonos = new ArrayList<Telefono>();
        List<Fabricante> fabricas = fabDao.listDisponibles();
        for(Fabricante fab : fabricas){
            try {
                String uri = fab.generateUrl() + "restTelefonos/" + tiendaActual + "/"+ contr;
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<Telefono[]> response = restTemplate.getForEntity(uri,Telefono[].class);
                Telefono[] tel = response.getBody();
                telefonos.addAll(Arrays.asList(tel));
              } catch (Exception e) {
                this.msg = fab.getFabrica() + ", ";
              }
        }
        if(!this.msg.equals("")){
            this.msg = "Fabricas no conectadas: " + this.msg;
        }
        model.addAttribute("msg", this.msg);
        model.addAttribute("listTel", telefonos);  
        model.addAttribute("usuario", this.user);
        this.msg = "";
        return "telefonos/telefonosFabrica.html";
    }

    @RequestMapping("/saveRestTel/{codigo}/{fabrica}")
    public String saveRestTel(@PathVariable(name = "codigo") String codigo, @PathVariable(name = "fabrica") String fabrica) {
        Fabricante fabricante = fabDao.getByName(fabrica);
        String uri = fabricante.generateUrl() + "oneTel/" + codigo + "/" + tiendaActual + "/"+ contr;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Telefono[]> response = restTemplate.getForEntity(uri,Telefono[].class);
            Telefono[] tel = response.getBody();
            Telefono guardar = tel[0];
            guardar.setFabricaid(fabricante.getFabricaid());
            teldao.save(guardar);
            for(String foto : guardar.getImagenes()){
                Telefono te = new Telefono(guardar.getTelcodigo(), foto);
                teldao.saveFoto(te);
            }
        return "redirect:/telefonos";       
    }

    @RequestMapping("/restPedidos")
    public String restPedidosPage(final Model model) {
        List<Pedido> orden = new ArrayList<Pedido>();
        List<Fabricante> fabricas = fabDao.listDisponibles();
        for(Fabricante fab : fabricas){
            try{
            String uri = fab.generateUrl() + "restOrdenes/" + tiendaActual + "/"+ contr;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Pedido[]> response = restTemplate.getForEntity(uri,Pedido[].class);
            Pedido[] ord = response.getBody();
            orden.addAll(Arrays.asList(ord));
            } catch (Exception e) {
            this.msg = fab.getFabrica() + ", ";
            }
            
        }
        if(!this.msg.equals("")){
            this.msg = "Fabricas no conectadas: " + this.msg;
        }
        model.addAttribute("listOrden", orden);
        model.addAttribute("usuario", this.user);
        model.addAttribute("msg", this.msg);
        this.msg = "";
        return "pedidos/pedidosFabrica.html";
    }

    @RequestMapping(value = "/restNewPedido/{idTel}/{pagina}", method = RequestMethod.POST)
    public String restAddPedido(@PathVariable(name = "idTel") String idTel, 
                                        @PathVariable(name = "pagina") int pagina,
                                        @RequestParam int cantidadTel) {
        Telefono telefono = teldao.get(idTel);
        Fabricante fabric = fabDao.get(telefono.getFabricaid());
        Pedido element = new Pedido(cantidadTel, telefono);
        RestTemplate restTemplate = new RestTemplate();
        try{
            ResponseEntity<Void> response = restTemplate.postForEntity(fabric.generateUrl() + "restAddPedido/" + tiendaActual + "/"+ contr, element, Void.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                this.msg = "Pedido aceptado";
            } else {
                System.out.println("Request Failed");
            }

        } catch (Exception e) {
            this.msg = "Fabrica no conectada";
            if(pagina == 1){
                
                return "redirect:/editTel/" + idTel;
            }else{
                return "redirect:/verTel/" + idTel;
            }
        }
        
        return "redirect:/restPedidos";
    }

    @RequestMapping("/restAccion/{id}/{fabrica}/{accion}/{cantidad}/{tel}")
    public String restPedidoAceptarPage(@PathVariable(name = "id") String id, 
                                        @PathVariable(name = "fabrica") String fabrica, 
                                        @PathVariable(name = "accion") int accion, 
                                        @PathVariable(name = "cantidad") int cant,
                                        @PathVariable(name = "tel") String tel) {
        Fabricante fabric = fabDao.getByName(fabrica);
        RestTemplate restTemplate = new RestTemplate();
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		if(accion == 1){
		    map.put("estado", "Recibido");
        }else{
            map.put("estado", "Cancelado");
        }
		ResponseEntity<Void> response = restTemplate.postForEntity(fabric.generateUrl() + "restActualizarPedido/" + tiendaActual + "/"+ contr, map, Void.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            if(accion == 1){
                teldao.actualizarInventario(cant, tel);
            }
		} else {
			System.out.println("Request Failed");
		}

        return "redirect:/restPedidos";
    }

}
