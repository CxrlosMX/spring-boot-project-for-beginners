package com.bolsadeideas.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.service.ClienteServiceImpl;

@Controller // Marmamos la clase
@RequestMapping("/factura") // Mapeamos una ruta especifica
@SessionAttributes("factura")
public class FacturaController {
	@Autowired
	private ClienteServiceImpl clienteService;

	@GetMapping("/form/{clienteId}") // Recibe un valor, que es el ID del cliente
	public String crear(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> model,
			RedirectAttributes flash) {
		Cliente cliente = clienteService.findOne(clienteId); // Obtenemos el cliente

		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar"; // Si no encuentra el Cliente, rediregimos a la vista- listar.html
		}
		Factura factura = new Factura();
		factura.setCliente(cliente); // Relacionamos la factura con el cliente

		model.put("factura", factura); // Pasamos la factura a la vista
		model.put("titulo", "Crear Factura");
		return "factura/form";
	}
}
