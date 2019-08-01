package com.mcexpress;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mcexpress.domain.Categoria;
import com.mcexpress.domain.Produto;
import com.mcexpress.repositories.CategoriaRepository;
import com.mcexpress.repositories.ProdutoRepository;



@SpringBootApplication                        //Essa implementação executa a instanciação no momento em que a aplicação iniciar
public class McexpressxApplication implements CommandLineRunner {
	
	//dependencias de instancias de repositorios automática
	@Autowired
	private CategoriaRepository catRepository;
	@Autowired
	private ProdutoRepository prodRepository;

	public static void main(String[] args) {
		SpringApplication.run(McexpressxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Aqui vou instanciar os meus objetos
		

		//Instanciar categorias
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		
		//Instanciar Produtos
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		//Adicionar a lista de produtos as categorias/ addAll é uma operação da lista em que eu passo uma coleção de elementos.
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		//Adicionar as categorias aos produtos
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		//Salvar produtos e categorias no banco
		this.catRepository.saveAll(Arrays.asList(cat1,cat2));
		this.prodRepository.saveAll(Arrays.asList(p1,p2,p3));
		
	}
}
	