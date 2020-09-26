package br.com.fiap.capsuledev.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/admin")
public class HospitalController {

	@GetMapping
	public String index(Model model) {
		RestTemplate api = new RestTemplate();
		List<?> hospitais = api.getForObject("https://capsuledevdigital01.herokuapp.com/hospital", List.class);
		model.addAttribute("hospitais", hospitais);
		return "admin/hospital";
	}
}
