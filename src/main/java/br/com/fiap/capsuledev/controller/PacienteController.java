package br.com.fiap.capsuledev.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.capsuledev.domain.Monitoramento;
import br.com.fiap.capsuledev.domain.Paciente;
import br.com.fiap.capsuledev.domain.site.Usuario;

@Controller
public class PacienteController {
	
	public String formatadorData(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormat.format(date);
	}

	@PostMapping("/loginPaciente")
	public String indexPaciente(Usuario usuario, Model model) {

		RestTemplate api = new RestTemplate();
		
		String url = "https://capsuledevdigital01.herokuapp.com/paciente/" + usuario.getCodigo();
		
		List<Monitoramento> monitoramentosAtivos = new ArrayList<Monitoramento>();
		List<Monitoramento> monitoramentosNaoAtivos = new ArrayList<Monitoramento>();

		
		Paciente paciente = api.getForObject(url, Paciente.class);
		
		for (Monitoramento monitoramento : paciente.getMonitoramentos()) {
			monitoramento.setInicioFormatado(formatadorData(monitoramento.getInicio()));
			
			if (monitoramento.getAtivo() == true) {
				monitoramentosAtivos.add(monitoramento);
			}
			
			if (monitoramento.getAtivo() == false) {
				monitoramentosNaoAtivos.add(monitoramento);
			}
		}
		
		model.addAttribute("paciente", paciente);
		model.addAttribute("monitoramentosAtivos", monitoramentosAtivos);
		model.addAttribute("monitoramentosNaoAtivos", monitoramentosNaoAtivos);

		return "paciente/index";

	}
	
	@PostMapping("/loginMedico/paciente/{id}")
	public String paciente(@PathVariable("id") Long codigo, Model model) {
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/paciente/" + codigo;
		
		Paciente paciente = api.getForObject(url, Paciente.class);
		paciente.setNascimentoFormatado(formatadorData(paciente.getNascimento()));
		paciente.setTransplanteFormatado(formatadorData(paciente.getTransplante()));
		
		model.addAttribute("paciente", paciente);
		return "medico/paciente";
	}
	
	@PostMapping("/loginAdmin/cadastrarPaciente")
	public String salvarPaciente(Paciente paciente, RedirectAttributes redirectAttributes) {
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/paciente";
		Paciente pacienteResultado = api.postForObject(url, paciente, Paciente.class);
		
		redirectAttributes.addFlashAttribute("msg2", String.format("Paciente \"%s\" cadastrado com sucesso!", pacienteResultado.getNome()));
		return "redirect:/loginAdmin";
	}
	
	@PostMapping("/loginAdmin/editarPaciente")
	public String editarPaciente(long codigo, RedirectAttributes redirectAttributes) {
		
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/paciente/" + codigo;
		
		Paciente pacienteEditavel = api.getForObject(url, Paciente.class);
		
		redirectAttributes.addFlashAttribute("pacienteEditavel", pacienteEditavel);
		return "redirect:/loginAdmin";
	}
	
	@PostMapping("/loginAdmin/excluirPaciente")
	public String deletarPaciente(long codigo, RedirectAttributes redirectAttributes) {
		RestTemplate api = new RestTemplate();
		
		Map<String, String> params = new HashMap<>();
		params.put("id", String.valueOf(codigo));
		
		String url = "https://capsuledevdigital01.herokuapp.com/paciente/{id}";
		
		api.delete(url, params);
		
		return "redirect:/loginAdmin";
	}

}
