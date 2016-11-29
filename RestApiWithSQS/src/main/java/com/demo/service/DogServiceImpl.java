package com.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.demo.domain.Dog;
import com.demo.domain.FavRequest;
import com.demo.repositories.DogRepo;

@Service
public class DogServiceImpl implements DogService{
	
	@Autowired
	private DogRepo dogRepo;
	
	@Autowired
	private AmazonSQSClient amazonSQSClient;
	
	

	@Override
	public List<String> findAllDogPictureUrlsByBreed(String breed) {
		return dogRepo.findAllDogPictureUrlsByBreed(breed);
	}

	@Override
	public Dog create(Dog dog) {
		return dogRepo.save(dog);
	}
	
	@JmsListener(destination = "fav_requests")
    public void processFavRequests(String requestJSON) throws JMSException {
        
        try {
            FavRequest request=FavRequest.fromJSON(requestJSON);
            saveFavOrUnFav(request.getDogId(), request.isFavOrUnfav());
        } catch (IOException ex) {            
            throw new JMSException("Encountered error while parsing message.");
        }
    }
	
	@Override
	public void addFavOrUnFav(FavRequest request) throws Exception {		
		SendMessageRequest sendMessageRequest = new SendMessageRequest();
		sendMessageRequest.setMessageBody(FavRequest.toJSON(request));
		amazonSQSClient.sendMessage(sendMessageRequest);
	}
	
	
	public void  saveFavOrUnFav(Long id, boolean isFav) {
		
		Dog dog = dogRepo.findOne(id);
		
		if(isFav) {
			Integer favCount = dog.getFavCount();
			dog.setFavCount(favCount!=null?favCount+1:1);
			
		}
		else {
			Integer unfavCount = dog.getUnFavCount();
			dog.setUnFavCount(unfavCount!=null?unfavCount+1:1);
		}
		
		 dogRepo.save(dog);
	}

	@Override
	public Dog getDog(Long id) {
		
		return dogRepo.findOne(id);
	}

	@Override
	public Map<String, List<String>> findAllDogPictureUrls() {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
	    dogRepo.findAll().forEach(d->{
		if(map.get( d.getBreed())!=null){
		   map.get(  d.getBreed()).add(d.getDogPictureUrl());
		}else{
			List<String> l = new ArrayList<String>();
			l.add(d.getDogPictureUrl());
			map.put( d.getBreed(), l);
		}
		});
		return map;
	}

	@Override
	public List<Dog> findAllDogs() {
		return (List<Dog>) dogRepo.findAll();
	}
	
	
	
	

}
