package com.demo.service;

import java.util.List;
import java.util.Map;

import com.demo.domain.Dog;
import com.demo.domain.FavRequest;

public interface DogService {
	
	List<String> findAllDogPictureUrlsByBreed(String breed); 
	
	List<Dog> findAllDogs();
	
	Map<String,List<String>> findAllDogPictureUrls(); 
	
	Dog create(Dog dog);
	
	void addFavOrUnFav(FavRequest request) throws Exception;
	
	Dog getDog(Long id);

}
