package com.gestion.productos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gestion.productos.entidades.Producto;
import com.gestion.productos.servicio.ProductoService;

@Controller
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	
	@RequestMapping("/")
	public String verPaginaInicio(Model model,@Param("palabraClave") String palabraClave) {		
		List<Producto> listaProductos = productoService.listAll(palabraClave);
		model.addAttribute("palabraClave", palabraClave);
		model.addAttribute("listaProductos", listaProductos);
		return "index";		
	}
	
	@RequestMapping("/nuevo")
	public String mostrarRegistrarProducto(Model model) {
		Producto producto = new Producto();
		model.addAttribute("producto", producto);
		return "nuevo_producto";		
	}
	
	@RequestMapping( value = "/guardar", method = RequestMethod.POST)
	public String guradarProducto(@ModelAttribute("producto") Producto producto) {
		productoService.save(producto);
		return "redirect:/";		
	}
	
	@RequestMapping("/editar/{id}")
	public ModelAndView mostrarEditarProducto(@PathVariable(name = "id") Long id) {
		ModelAndView modelAndView = new ModelAndView("editar_producto");
		Producto producto = productoService.get(id);
		modelAndView.addObject("producto", producto);
		return modelAndView;		
	}
	
	@RequestMapping("/eliminar/{id}")
	public String eliminarProducto(@PathVariable(name = "id") Long id) {
		productoService.delete(id);
		return "redirect:/";		
	}

}
