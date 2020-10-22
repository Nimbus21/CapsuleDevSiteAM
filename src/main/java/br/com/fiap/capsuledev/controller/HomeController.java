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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.capsuledev.domain.CapsuleControl;
import br.com.fiap.capsuledev.domain.CapsuleWatch;
import br.com.fiap.capsuledev.domain.Hospital;
import br.com.fiap.capsuledev.domain.Medico;
import br.com.fiap.capsuledev.domain.Monitoramento;
import br.com.fiap.capsuledev.domain.Paciente;
import br.com.fiap.capsuledev.domain.site.Usuario;

@Controller
public class HomeController {

	@GetMapping
	public String index(Model model) {
		return "index";
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

	@PostMapping("/loginPaciente/monitoramento/{id}")
	public String monitoramentoPaciente(@PathVariable("id") Long codigo, Model model) {
		
		montarMonitoramento(codigo, model);
		
		
		return "paciente/monitoramento";
	}
	
	@PostMapping("/loginMedico")
	public String indexMedico(Usuario usuario, Model model) {
		
		montarIndexMedico(usuario, model);
		return "medico/index";
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
	
	//esperando spring security
	@GetMapping("/loginMedico")
	public String indexMedicoGet(Usuario usuario, Model model) {
		
		montarIndexMedico(usuario, model);
		return "medico/index";
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
	
	@PostMapping("/loginMedico/paciente/monitoramento/{id}")
	public String monitoramento(@PathVariable("id") Long codigo, Model model) {

		montarMonitoramento(codigo, model);
		return "medico/monitoramento";
	}
	
	//@PostMapping("/loginMedico/paciente/monitoramento/{id}/cadastrarCapsuleControl")
	@PostMapping("/loginMedico/monitoramento/cadastrarCapsuleControl")
	public String paginaCadastroCapsuleControl(Long codigo, Model model) {
		
		/*
		RestTemplate api = new RestTemplate();
		
		String url = "https://capsuledevdigital01.herokuapp.com/monitoramento";
		
		List<?> listaMonitoramentos = api.getForObject(url, List.class);
		
		model.addAttribute("listaMonitoramentos", listaMonitoramentos);
		
		*/
		model.addAttribute("codigo", codigo);
		return "medico/cadastrarCapsuleControl";
	}
	
	@PostMapping("/loginMedico/monitoramento/cadastrarCapsuleControl/confirm")
	public String cadastrarCapsuleControl(CapsuleControl capsuleControl) {
		RestTemplate api = new RestTemplate();
		String url1 = "https://capsuledevdigital01.herokuapp.com/monitoramento/" + capsuleControl.getCodigoMonitoramento();
		String url2 = "https://capsuledevdigital01.herokuapp.com/capsuleControl/";
		
		Monitoramento monitoramento = api.getForObject(url1, Monitoramento.class);
		
		capsuleControl.setMonitoramento(monitoramento);
		capsuleControl.setData(new Date(System.currentTimeMillis()));

//		CapsuleControl capsuleControlResultado = api.postForObject(url2, capsuleControl, CapsuleControl.class);
		api.postForObject(url2, capsuleControl, CapsuleControl.class);
		return "redirect:/";
	}
	
	@PostMapping("/loginAdmin/cadastrarHospital")
	public String salvarHospital(Hospital hospital, RedirectAttributes redirectAttributes) {
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/hospital";
		Hospital hospitalResultado = api.postForObject(url, hospital, Hospital.class);
		
		redirectAttributes.addFlashAttribute("msg", String.format("Hospital \"%s\" cadastrado com sucesso!", hospitalResultado.getNome()));
		return "redirect:/loginAdmin";
	}
	
	@PostMapping("/loginAdmin/cadastrarPaciente")
	public String salvarPaciente(Paciente paciente, RedirectAttributes redirectAttributes) {
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/paciente";
		Paciente pacienteResultado = api.postForObject(url, paciente, Paciente.class);
		
		redirectAttributes.addFlashAttribute("msg2", String.format("Paciente \"%s\" cadastrado com sucesso!", pacienteResultado.getNome()));
		return "redirect:/loginAdmin";
	}
	
	@PostMapping("/loginAdmin/cadastrarMonitoramento")
	public String salvarMonitoramento(Monitoramento monitoramento, RedirectAttributes redirectAttributes) {
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/paciente";
		Monitoramento monitoramentoResultado = api.postForObject(url, monitoramento, Monitoramento.class);
		
		redirectAttributes.addFlashAttribute("msg3", String.format("Monitoramento \"%s\" cadastrado com sucesso!", monitoramentoResultado.getCodigo()));
		return "redirect:/loginAdmin";
	}
	
	@PostMapping("/loginAdmin/excluirHospital")
	public String deletarHospital(long codigo, RedirectAttributes redirectAttributes) {
		RestTemplate api = new RestTemplate();
		
		Map<String, String> params = new HashMap<>();
		params.put("id", String.valueOf(codigo));
		
		String url = "https://capsuledevdigital01.herokuapp.com/hospital/{id}";
		
		api.delete(url, params);
		
		redirectAttributes.addFlashAttribute("msg", "Hospital excluído com sucesso!");
		return "redirect:/loginAdmin";
	}
	
	@PostMapping("/loginMedico/excluirCapsuleControl")
	public String deletarCapsuleControl(long codigo, RedirectAttributes redirectAttributes) {
		RestTemplate api = new RestTemplate();
		
		Map<String, String> params = new HashMap<>();
		params.put("id", String.valueOf(codigo));
		
		String url = "https://capsuledevdigital01.herokuapp.com/capsuleControl/{id}";
		
		api.delete(url, params);
		
		redirectAttributes.addFlashAttribute("msg", "CapsuleControl excluído com sucesso!");
		return "redirect:/";
	}
	
	@PostMapping("/loginAdmin/editarHospital")
	public String editarHospital(long codigo, RedirectAttributes redirectAttributes) {
		
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/hospital/" + codigo;
		
		Hospital hospitalEditavel = api.getForObject(url, Hospital.class);
		
		redirectAttributes.addFlashAttribute("hospitalEditavel", hospitalEditavel);
		return "redirect:/loginAdmin";
	}
	
	@PostMapping("/loginMedico/editarCapsuleControl")
	public String editarCapsuleControl(long codigo, Model model) {
		/*
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/capsuleControl/" + codigo;
		
		CapsuleControl capsuleControlEditavel = api.getForObject(url, CapsuleControl.class);
		
		redirectAttributes.addFlashAttribute("capsuleControlEditavel", capsuleControlEditavel);
		return "redirect:/loginMedico";
		*/
		
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/capsuleControl/" + codigo;
		
		CapsuleControl capsuleControl = api.getForObject(url, CapsuleControl.class);
		
		model.addAttribute("capsuleControl", capsuleControl);
		
		return "medico/editarCapsuleControl";
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
	
	public void montarMonitoramento(Long codigo, Model model) {
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/monitoramento/" + codigo;
		
		Monitoramento monitoramento = api.getForObject(url, Monitoramento.class);
		monitoramento.setInicioFormatado(formatadorData(monitoramento.getInicio()));
		monitoramento.setFimFormatado(formatadorData(monitoramento.getFim()));
		
		for (CapsuleWatch capsuleWatch : monitoramento.getListaCapsuleWatch()) {
			capsuleWatch.setDataFormatada(formatadorData(capsuleWatch.getDataHora()));
			capsuleWatch.setHoraFormatada(formatadorHora(capsuleWatch.getDataHora()));
		}
		
		for (CapsuleControl capsuleControl : monitoramento.getListaCapsuleControl()) {
			capsuleControl.setDataFormatada(formatadorData(capsuleControl.getData()));
		}
		
		String status = new String();
		
		if (monitoramento.getAtivo() == true) {
			status = "Ativo";
		} else {
			status = "Concluído";
		}
		
		model.addAttribute("status", status);		
		model.addAttribute("monitoramento", monitoramento);
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
	
	public void montarIndexAdmin(Model model) {
		RestTemplate api = new RestTemplate();
		String url1 = "https://capsuledevdigital01.herokuapp.com/hospital";
		String url2 = "https://capsuledevdigital01.herokuapp.com/paciente";
		
		List<?> hospitais = api.getForObject(url1, List.class);
		List<?> pacientes = api.getForObject(url2, List.class);
		
		model.addAttribute("hospitais", hospitais);
		model.addAttribute("pacientes", pacientes);
	}
}
