package com.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.domain.Dog;
import com.demo.domain.FavRequest;
import com.demo.service.DogService;

@RestController
public class DogController {
	
	@Autowired
	private DogService dogService;
	
	
	@RequestMapping(value = "/showalldogpictures/{breed}")
	public @ResponseBody List<String> getAllDogPictureUrlsByBreed(@PathVariable String breed) {		
		return dogService.findAllDogPictureUrlsByBreed(breed);		
	}
	
	@RequestMapping(value = "/showalldogpictures")
	public @ResponseBody Map<String,List<String>> getAllDogPictureUrls() {
		return dogService.findAllDogPictureUrls();
		
	}
	
	@RequestMapping(value = "/createdog", method = RequestMethod.POST)
	public @ResponseBody Dog createNewDog(Dog dog) {		
		return dogService.create(dog);
		
	}
	
	//Vote up a dog picture
	@RequestMapping(value = "/addfav/{id}", method = RequestMethod.PUT)
	public @ResponseBody String fav(@PathVariable Long id) {		
		 try {
			dogService.addFavOrUnFav(new FavRequest(id, true));
		} catch (Exception e) {
			e.printStackTrace();
			return "Operation Failed";
		}	
		 return "Operation Successfully";
	}
	
	//Vote down a dog picture
	@RequestMapping(value = "/addunfav/{id}", method = RequestMethod.PUT)
	public @ResponseBody String unfav(@PathVariable Long id) {
		
		 try {
			dogService.addFavOrUnFav(new FavRequest(id, false));
		} catch (Exception e) {
			e.printStackTrace();
			return "Operation Failed";
		}
		 return "Operation Successfully";
	}
	
	
	@RequestMapping(value = "/getDogDetails/{id}", method = RequestMethod.GET)
	public @ResponseBody Dog getDogDetails(@PathVariable Long id) {		
		return dogService.getDog(id);		
	}
	
	@RequestMapping(value = "/getDogDetails", method = RequestMethod.GET)
	public @ResponseBody List<Dog> getDogDetails() {		
		return dogService.findAllDogs();		
	}
	
	

}
