package br.com.fiap.capsuledev.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import br.com.fiap.capsuledev.domain.Medico;
import br.com.fiap.capsuledev.domain.Paciente;
import br.com.fiap.capsuledev.domain.Usuario;

@Controller
public class HomeController {
	private List<Long> codigosAutorizados = new ArrayList<Long>();

	@GetMapping
	public String index(Model model) {
		return "index";
	}

	@PostMapping("/loginPaciente")
	public String indexPaciente(Usuario usuario, Model model) {
		codigosAutorizados.add((long) 1);

		if (codigosAutorizados.contains(usuario.getCodigo())) {
			System.out.println(usuario.getEmail());
			RestTemplate api = new RestTemplate();
			String url = "https://capsuledevdigital01.herokuapp.com/paciente/" + usuario.getCodigo();
			System.out.println(url);
			Paciente paciente = api.getForObject(url, Paciente.class);
			model.addAttribute("paciente", paciente);

			return "paciente/index";
		} else {
			System.out.println("Código não autorizado");
			return "redirect:/";
		}

	}

	@PostMapping("/loginMedico")
	public String indexMedico(Usuario usuario, Model model) {
//		System.out.println(usuario.getEmail());
		RestTemplate api = new RestTemplate();
		String url1 = "https://capsuledevdigital01.herokuapp.com/medico/" + usuario.getCodigo();
//		System.out.println(url1);
		Medico medico = api.getForObject(url1, Medico.class);
		
		String url2 = "https://capsuledevdigital01.herokuapp.com/paciente/pacientesByMedico?codigo=" + medico.getCodigo();
//		System.out.println(url2);
		List<?> pacientesMedico = api.getForObject(url2, List.class);
		
		model.addAttribute("medico", medico);
		model.addAttribute("pacientesMedico", pacientesMedico);
		return "medico/index";
	}

	@PostMapping("/loginAdmin")
	public String indexAdmin(Usuario usuario, Model model) {
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/hospital";
		List<?> hospitais = api.getForObject(url, List.class);
		model.addAttribute("hospitais", hospitais);
		return "admin/index";
	}
	
	@PostMapping("/loginMedico/paciente/{id}")
	public String paciente(@PathVariable("id") Long codigo, Model model) {
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/paciente/" + codigo;
//		System.out.println(url);
		Paciente paciente = api.getForObject(url, Paciente.class);
		model.addAttribute("paciente", paciente);
		return "medico/paciente";
	}
}
