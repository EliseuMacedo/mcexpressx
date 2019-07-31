package com.mcexpress.resources; // o nome Resources é utilizado para criar a classe de controlador rest

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@RequestMapping(method = RequestMethod.GET) //verbo http para dados
	public String listar() {
		return "Rest funcionando";
	}
}
