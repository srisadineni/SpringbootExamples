package com.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.QServiceRequest;
import com.demo.domain.SearchDTO;
import com.demo.domain.ServiceRequest;
import com.demo.repositories.ServiceRequestRepository;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.expr.NumberExpression;
import com.mysema.query.types.template.NumberTemplate;

@Service
public class ServiceRequestServiceImpl implements
		ServiceRequestService {

	@Autowired
	private ServiceRequestRepository serviceRequestRepository;	

	
	
	@Override
	public Iterable<ServiceRequest> searchRequests(SearchDTO searchDTO) {
		BooleanBuilder b = new BooleanBuilder();
		QServiceRequest serviceRequest = QServiceRequest.serviceRequest;		

		
		if (searchDTO.getLat() != null&& searchDTO.getLng()!=null) {
			  
			   NumberExpression<Double> expression = NumberTemplate.create( Double.class,"FUNCTION ('is_near_by',{0},{1},{2},{3})", serviceRequest.lat, serviceRequest.lng, searchDTO.getLat(),
						searchDTO.getLng());			
			b = b.and(expression.loe(searchDTO.getRadius()));
		}

		return serviceRequestRepository.findAll(b);
	}



	@Override
	public ServiceRequest getServiceRequest(Long id) {
		
		return serviceRequestRepository.findOne(id);
	}



	@Override
	public ServiceRequest save(ServiceRequest serviceRequest) {		
		return serviceRequestRepository.save(serviceRequest);
	}

	

}
