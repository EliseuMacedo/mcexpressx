package com.mcexpress.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mcexpress.domain.Cidade;

//anotação para classe e transformá-la em uma interface e herança de JPAREpository do Spring
@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>  {
	//Essa interface CategoriaRepository herda todos os metodos JPA Spring Boot, por tanto não precisa ser implementado, assim
	//o categoria Repository será instanciada lá do service e acessará os métodos JPA Spring Boot
}
