package com.demo;

  
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.demo.Application;
import com.demo.domain.Dog;
import com.demo.repositories.DogRepo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class DogControllerTest {
	
	@Autowired
	DogRepo dogRepo;
	
	Dog bruno;
	Dog jimmy;
	
	@Before
	public void setUpForTest() {
		bruno = new Dog();
		jimmy = new Dog();
	}
	
	@Test
	public void createDog(){
		//TODO when()...then().. return
	}

}
