package com.mcexpress;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mcexpress.domain.Categoria;
import com.mcexpress.domain.Cidade;
import com.mcexpress.domain.Cliente;
import com.mcexpress.domain.Endereco;
import com.mcexpress.domain.Estado;
import com.mcexpress.domain.Produto;
import com.mcexpress.domain.enums.TipoCliente;
import com.mcexpress.repositories.CategoriaRepository;
import com.mcexpress.repositories.CidadeRepository;
import com.mcexpress.repositories.ClienteRepository;
import com.mcexpress.repositories.EnderecoRepository;
import com.mcexpress.repositories.EstadoRepository;
import com.mcexpress.repositories.ProdutoRepository;



@SpringBootApplication                        //Essa implementação executa a instanciação no momento em que a aplicação iniciar
public class McexpressxApplication implements CommandLineRunner {
	
	//dependencias de instancias de repositorios automática
	@Autowired
	private CategoriaRepository catRepository;
	@Autowired
	private ProdutoRepository prodRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	

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
		
		//Instanciar estados e cidades
		
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		//Instanciar Cliente
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834",cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getEndereços().addAll(Arrays.asList(e1,e2));
		
		//salver
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		

		
		
	}
}
	