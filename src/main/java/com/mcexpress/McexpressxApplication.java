package com.mcexpress;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mcexpress.domain.Categoria;
import com.mcexpress.repositories.CategoriaRepository;



@SpringBootApplication                        //Essa implementação executa a instanciação no momento em que a aplicação iniciar
public class McexpressxApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository catRepository;

	public static void main(String[] args) {
		SpringApplication.run(McexpressxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Aqui vou instanciar os meus objetos
		

		//Instanciar categorias
		Categoria cat1 = new Categoria(null, "Departamento Pessoal");
		Categoria cat2 = new Categoria(null, "Recursos Humanos");
		Categoria cat3 = new Categoria(null, "Financeiro");
		Categoria cat4 = new Categoria(null, "Call Center");
		Categoria cat5 = new Categoria(null, "PCP");
		Categoria cat6 = new Categoria(null, "Diretoria");
		Categoria cat7 = new Categoria(null, "Contabilidade");
		
		//Salvar as categorias no banco
		this.catRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7));
		
		
	}
}
	