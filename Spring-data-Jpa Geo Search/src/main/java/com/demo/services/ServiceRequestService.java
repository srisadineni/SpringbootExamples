package com.demo.services;


import com.demo.domain.SearchDTO;
import com.demo.domain.ServiceRequest;

public interface ServiceRequestService {
      
    ServiceRequest getServiceRequest(Long id);
    
    Iterable<ServiceRequest> searchRequests(SearchDTO searchDTO);

    ServiceRequest save(ServiceRequest serviceRequest);
    
}
