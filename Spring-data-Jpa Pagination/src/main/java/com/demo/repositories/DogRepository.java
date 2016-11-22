package com.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.demo.domain.Dog;

public interface DogRepository extends JpaRepository<Dog, Long>,QueryDslPredicateExecutor<Dog>{


	
}
