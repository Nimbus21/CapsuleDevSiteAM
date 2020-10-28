package br.com.fiap.capsuledev.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.capsuledev.domain.CapsuleControl;
import br.com.fiap.capsuledev.domain.Monitoramento;
import br.com.fiap.capsuledev.domain.Paciente;
import br.com.fiap.capsuledev.domain.site.Usuario;

@Controller
public class HomeController {

	@GetMapping
	public String index(Model model) {
		return "index";
	}

	@PostMapping("/loginAdmin")
	public String indexAdmin(Usuario usuario, Model model) {

		montarIndexAdmin(model);
		return "admin/index";
	}
	
	//Esse método com get não deveria existir, mas deixa aí pra testar
	@GetMapping("/loginAdmin")
	public String indexAdmin(Model model) {
		
		montarIndexAdmin(model);
		return "admin/index";
	}
	
	@GetMapping("/loginAdministrador")
	public String loginAdministrador(Model model) {
		return "admin/login";
	}
	
	public String formatadorData(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormat.format(date);
	}
	
	public String formatadorHora(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormat.format(date);
	}
	
	public void montarIndexAdmin(Model model) {
		RestTemplate api = new RestTemplate();
		String url1 = "https://capsuledevdigital01.herokuapp.com/hospital";
		String url2 = "https://capsuledevdigital01.herokuapp.com/paciente";
		String url3 = "https://capsuledevdigital01.herokuapp.com/medico";
		String url4 = "https://capsuledevdigital01.herokuapp.com/monitoramento";
		
		List<?> hospitais = api.getForObject(url1, List.class);
		
		Paciente[] pacientesArray = api.getForObject(url2, Paciente[].class);
		List<Paciente> pacientes = Arrays.asList(pacientesArray);
		
		List<?> medicos = api.getForObject(url3, List.class);
		
		Monitoramento[] monitoramentosArray = api.getForObject(url4, Monitoramento[].class);
		List<Monitoramento> monitoramentos = Arrays.asList(monitoramentosArray);
		
		for (Paciente paciente : pacientes) {
			paciente.setNascimentoFormatado(formatadorData(paciente.getNascimento()));
			paciente.setTransplanteFormatado(formatadorData(paciente.getTransplante()));
		}
		
		for (Monitoramento monitoramento : monitoramentos) {
			monitoramento.setInicioFormatado(formatadorData(monitoramento.getInicio()));
			monitoramento.setFimFormatado(formatadorData(monitoramento.getFim()));
		}
						
		model.addAttribute("hospitais", hospitais);
		model.addAttribute("pacientes", pacientes);
		model.addAttribute("medicos", medicos);
		model.addAttribute("monitoramentos", monitoramentos);
	}
}
