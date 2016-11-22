package com.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo.domain.Dog;
import com.demo.domain.QDog;
import com.demo.domain.SearchDTO;
import com.demo.repositories.DogRepository;
import com.mysema.query.BooleanBuilder;

@Service
public class DogServiceImpl implements DogService {

	@Autowired
	private DogRepository dogRepository;

	@Override
	public Page<Dog> searchRequests(SearchDTO searchDTO, Pageable p) {
		BooleanBuilder b = new BooleanBuilder();
		QDog qDog = QDog.dog;

		if (searchDTO != null) {
			if (!StringUtils.isEmpty(searchDTO.getSearchString())) {
				b = b.and(qDog.id.like(searchDTO.getSearchString())
						.or(qDog.name.like(searchDTO.getSearchString()))
						.or(qDog.breed.like(searchDTO.getSearchString()))
						.or(qDog.age.like(searchDTO.getSearchString())));
			}
		}

		return dogRepository.findAll(b, p);
	}

	@Override
	public Dog getServiceRequest(Long id) {

		return dogRepository.findOne(id);
	}

	@Override
	public Dog save(Dog dog) {
		return dogRepository.save(dog);
	}

}
