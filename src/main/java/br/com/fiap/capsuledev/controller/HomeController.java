package br.com.fiap.capsuledev.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import br.com.fiap.capsuledev.domain.Paciente;
import br.com.fiap.capsuledev.domain.PacienteUsuario;

@Controller
public class HomeController {
	
	

	@GetMapping
	public String index(Model model) {
		return "index";
	}
	
	@PostMapping("/loginPaciente")
	public String index(PacienteUsuario pacienteUsuario, Model model) {
		//System.out.println(pacienteUsuario.getCodigo());
		System.out.println(pacienteUsuario.getEmail());
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/paciente/" + pacienteUsuario.getCodigo();
		System.out.println(url);
		Paciente paciente = api.getForObject(url, Paciente.class);
		//System.out.println(paciente.getNome());
		model.addAttribute("paciente", paciente); 
		//Volta pro localhost
		//return "redirect:/";
		return "paciente/index";
	}
}
