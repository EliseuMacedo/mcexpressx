package com.mcexpress.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mcexpress.domain.Cliente;

//anotação para classe e transformá-la em uma interface e herança de JPAREpository do Spring
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>  {
	//Essa interface CategoriaRepository herda todos os metodos JPA Spring Boot, por tanto não precisa ser implementado, assim
	//o categoria Repository será instanciada lá do service e acessará os métodos JPA Spring Boot
	
	//Vamos usar o padrão de nomes do Spring data
	@Transactional(readOnly = true) // Não necessita ser envolvida como transação no banco de dados, diminui o gerenciamento de loking
	Cliente findByEmail(String email); //No Spring Data se você usa a busca findBy + nome do campo, automaticamente o spring implementa esse método para nós
	
}
