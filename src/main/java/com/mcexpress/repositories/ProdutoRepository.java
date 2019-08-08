package com.mcexpress.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mcexpress.domain.Categoria;
import com.mcexpress.domain.Produto;

//anotação para classe e transformá-la em uma interface e herança de JPAREpository do Spring
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>  {
	//Essa interface CategoriaRepository herda todos os metodos JPA Spring Boot, por tanto não precisa ser implementado, assim
	//o categoria Repository será instanciada lá do service e acessará os métodos JPA Spring Boot
	
	//Quando o método possui estrutura padrão o proprio SpringData implementa para mim, 
	//porém quando é específico eu preciso inserir a anotação 
	
	//A listagem de produtos que contém o trecho de nome dado e que pertencem a pelo menos uma das categorias dadas JPQL
	/*@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
			Page<Produto> search(
			@Param("nome") String nome,
			@Param("categorias") List<Categoria> categorias,
			Pageable pageRequest);*/
	
	
	//É possível usar essa mesma consulta que fizemos usando o padrão de nomes, ao usar as palavras e as classes relacionadas
	//ele já monta toda a consulta. O exemplo do select acima seria 
	
	// Page<Produto> findDistinctByNomeContainingAndCategoriasIn
	//O "Nome" é o primeiro argumento da minha pesquisa
	//O "Containing" aplica o like no meu argumento
	//O nome findDistinctByNomeContainingAndCategoriasIn dispensará a consulta e os parametros de anotações @Param("nome")@Param("categorias"), veja o exemplo abaixo
	
	//se colocar a Query em cima do metodo ela dá prioridade para a Query
	
	@Transactional(readOnly = true) // Não necessita ser envolvida como transação no banco de dados, diminui o gerenciamento de loking
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
	
	
}
