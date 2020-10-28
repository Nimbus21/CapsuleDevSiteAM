package br.com.fiap.capsuledev.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.capsuledev.domain.Medico;
import br.com.fiap.capsuledev.domain.site.Usuario;

public class MedicoController {
	
	@PostMapping("/loginMedico")
	public String indexMedico(Usuario usuario, Model model) {
		
		montarIndexMedico(usuario, model);
		return "medico/index";
	}
	
	public void montarIndexMedico(Usuario usuario, Model model) {
		RestTemplate api = new RestTemplate();
		String url1 = "https://capsuledevdigital01.herokuapp.com/medico/" + usuario.getCodigo();
		Medico medico = api.getForObject(url1, Medico.class);
		
		String url2 = "https://capsuledevdigital01.herokuapp.com/paciente/pacientesByMedico?codigo=" + medico.getCodigo();
		List<?> pacientesMedico = api.getForObject(url2, List.class);
		
		model.addAttribute("medico", medico);
		model.addAttribute("pacientesMedico", pacientesMedico);
	};
	
	//esperando spring security
	@GetMapping("/loginMedico")
	public String indexMedicoGet(Usuario usuario, Model model) {
		
		montarIndexMedico(usuario, model);
		return "medico/index";
	}
	
	@PostMapping("/loginAdmin/cadastrarMedico")
	public String salvarMedico(Medico medico, RedirectAttributes redirectAttributes) {
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/medico";
		Medico medicoResultado = api.postForObject(url, medico, Medico.class);
		
		redirectAttributes.addFlashAttribute("msg3", String.format("Médico \"%s\" cadastrado com sucesso!", medicoResultado.getNome()));
		return "redirect:/loginAdmin";
	}
}
