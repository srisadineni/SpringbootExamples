package com.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.demo.domain.ServiceRequest;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long>,QueryDslPredicateExecutor<ServiceRequest>{


	
}
