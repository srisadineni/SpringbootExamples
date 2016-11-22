package com.demo.controllers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.domain.Dog;
import com.demo.domain.HelperUtils;
import com.demo.domain.Pager;
import com.demo.domain.SearchDTO;
import com.demo.services.DogService;

@Controller
public class DogController {
	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 4;
	private static final String DEFAULT_SORT_STRING = "id";

	@Autowired
	DogService dogService;

	@Value("${breeds}")
	public String breeds;

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("searchDTO", new SearchDTO());
		model.addAttribute("breeds", Arrays.asList(breeds.split(",")));
		return "searchDogs";
	}

	@RequestMapping("/save/")
	public String save(Dog dog, Model model, HttpSession session) {
		dogService.save(dog);
		return search(model, session, null, null, null, null, new SearchDTO());
	}

	@RequestMapping("/create/")
	public String view(Model model) {
		model.addAttribute("dog", new Dog());
		model.addAttribute("breeds", Arrays.asList(breeds.split(",")));
		return "createDog";
	}

	@RequestMapping(value = "/search")
	public String search(
			Model model,
			HttpSession session,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "sortString", required = false) String sortString,
			@RequestParam(value = "oldSortString", required = false) String oldSortString,
			@RequestParam(value = "oldDirection", required = false) Direction oldDirection,
			@ModelAttribute("searchDTO") SearchDTO searchDTO) {
		SearchDTO sessionSearchDTO = (SearchDTO) session.getAttribute("searchDTO");
		if (sessionSearchDTO != null && page != null) {
			searchDTO = sessionSearchDTO;
		}
		PageRequest pageRequest = HelperUtils.createPageRequest(model, page,
				sortString, oldSortString, oldDirection, INITIAL_PAGE,
				INITIAL_PAGE_SIZE, DEFAULT_SORT_STRING);
		return searchDogs(model, session, searchDTO, pageRequest);
	}

	public String searchDogs(Model model, HttpSession session, SearchDTO searchDTO,
			PageRequest pageRequest) {

		Page<Dog> dogs = dogService.searchRequests(searchDTO,
				pageRequest);
		Pager pager = new Pager(dogs.getTotalPages(),
				dogs.getNumber(), BUTTONS_TO_SHOW);
		model.addAttribute("dogs", dogs);
		model.addAttribute("pager", pager);
		model.addAttribute("searchDTO", searchDTO != null ? searchDTO
				: new Dog());
		session.setAttribute("searchDTO", searchDTO);
		model.addAttribute("breeds", Arrays.asList(breeds.split(",")));
		return "searchDogs";
	}

}
