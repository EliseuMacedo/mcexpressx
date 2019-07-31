package com.mcexpress.resources; // o nome Resources é utilizado para criar a classe de controlador rest

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mcexpress.domain.Categoria;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@RequestMapping(method = RequestMethod.GET)
	public List<Categoria> listar() {
		Categoria cat1 = new Categoria(1, "Informática");
		Categoria cat2 = new Categoria(2, "Escritório");
		Categoria cat3 = new Categoria(3, "Departamento Pessoal");
		Categoria cat4 = new Categoria(4, "Recursos Humanos");
		Categoria cat5 = new Categoria(5, "Pcp");
		Categoria cat6 = new Categoria(6, "Financeiro");
		Categoria cat7 = new Categoria(7, "Contabilidade");
		Categoria cat8 = new Categoria(8, "Call Center");
		
		List<Categoria> lista = new ArrayList<>();
		lista.add(cat1);
		lista.add(cat2);
		lista.add(cat3);
		lista.add(cat4);
		lista.add(cat5);
		lista.add(cat6);
		lista.add(cat7);
		lista.add(cat8);
		return lista;
	}
}
