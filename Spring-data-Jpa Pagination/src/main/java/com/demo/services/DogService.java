package com.demo.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.demo.domain.Dog;
import com.demo.domain.SearchDTO;

public interface DogService {
      
    Dog getServiceRequest(Long id);
    
    Dog save(Dog dog);

	Page<Dog> searchRequests(SearchDTO searchDTO, Pageable p);
    
}
