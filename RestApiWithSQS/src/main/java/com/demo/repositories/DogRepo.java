package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.domain.Dog;

@Repository
public interface DogRepo extends CrudRepository<Dog, Long>{
	
	@Query("Select d.dogPictureUrl FROM Dog d where d.breed = :breed")
	List<String> findAllDogPictureUrlsByBreed(@Param("breed") String breed);

}
