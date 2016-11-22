package com.demo.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.domain.SearchDTO;
import com.demo.domain.ServiceRequest;
import com.demo.services.ServiceRequestService;

@Controller
public class ServiceRequestController {

	@Autowired
	ServiceRequestService serviceRequestService;
	
	@Value("${serviceTypes}")
	public String services;
	
	
	@RequestMapping("/")
	public String index(Model model) {			
		return search(new SearchDTO(), model);
	}
	
	@RequestMapping("/search/")
	public String search(SearchDTO s, Model model) {		
		 model.addAttribute("serviceRequests", (List<ServiceRequest>) serviceRequestService.searchRequests(s));
		 model.addAttribute("searchDTO", new SearchDTO());
		return "searchServiceRequests";
	}

	
	@RequestMapping("/save/")
	public String save(ServiceRequest serviceRequest, Model model) {	
		serviceRequestService.save(serviceRequest);
		model.addAttribute("serviceRequests", (List<ServiceRequest>) serviceRequestService.searchRequests(new SearchDTO()));
		 model.addAttribute("searchDTO", new SearchDTO());
		return "searchServiceRequests";
	}
	
	@RequestMapping("/create/")
	public String view(Model model) {	
		model.addAttribute("serviceRequest", new ServiceRequest());
		model.addAttribute("serviceTypes",
				Arrays.asList(services.split(",")));
		return "createServiceRequest";
	}
}
