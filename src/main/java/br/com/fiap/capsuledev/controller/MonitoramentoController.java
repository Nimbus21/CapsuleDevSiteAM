package br.com.fiap.capsuledev.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

@Controller
public class MonitoramentoController {
	
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
	
	@PostMapping("/loginMedico/paciente/monitoramento/{id}")
	public String monitoramento(@PathVariable("id") Long codigo, Model model) {

		montarMonitoramento(codigo, model);
		return "medico/monitoramento";
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

	@PostMapping("/loginPaciente/monitoramento/{id}")
	public String monitoramentoPaciente(@PathVariable("id") Long codigo, Model model) {
		
		montarMonitoramento(codigo, model);
		
		
		return "paciente/monitoramento";
	}
	
	@PostMapping("/loginAdmin/cadastrarMonitoramento")
	public String salvarMonitoramento(Monitoramento monitoramento, RedirectAttributes redirectAttributes) {
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/monitoramento";
		String url2 = "https://capsuledevdigital01.herokuapp.com/hospital/" + monitoramento.getCodigoHospital();
		String url3 = "https://capsuledevdigital01.herokuapp.com/medico/" + monitoramento.getCodigoMedico();
		String url4 = "https://capsuledevdigital01.herokuapp.com/paciente/" + monitoramento.getCodigoPaciente();
		
		System.out.println("url 2: " + url2);
		System.out.println("fim> " + monitoramento.getFim());
		
		Hospital hospital = api.getForObject(url2, Hospital.class);
		Medico medico = api.getForObject(url3, Medico.class);
		Paciente paciente = api.getForObject(url4, Paciente.class);
		
		Date agora = new Date(System.currentTimeMillis());
		
		monitoramento.setInicio(agora);
		monitoramento.setAtivo(true);
		monitoramento.setHospital(hospital);
		monitoramento.setMedico(medico);
		monitoramento.setPaciente(paciente);
		
		Monitoramento monitoramentoResultado = api.postForObject(url, monitoramento, Monitoramento.class);
		
		redirectAttributes.addFlashAttribute("msg4", String.format("Monitoramento \"%s\" cadastrado com sucesso!", monitoramentoResultado.getCodigo()));
		return "redirect:/loginAdmin";
	}
	
	@PostMapping("/loginAdmin/excluirMonitoramento")
	public String deletarMonitoramento(long codigo, RedirectAttributes redirectAttributes) {
		RestTemplate api = new RestTemplate();
		
		Map<String, String> params = new HashMap<>();
		params.put("id", String.valueOf(codigo));
		
		String url = "https://capsuledevdigital01.herokuapp.com/monitoramento/{id}";
		
		System.out.println("MonitoramentoDelete: " + url);
		api.delete(url, params);
		
		return "redirect:/loginAdmin";
	}

	@PostMapping("/loginAdmin/editarMonitoramento")
	public String editarMonitoramento(long codigo, RedirectAttributes redirectAttributes) {
		
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/monitoramento/" + codigo;
		
		Monitoramento monitoramentoEditavel = api.getForObject(url, Monitoramento.class);
		
		redirectAttributes.addFlashAttribute("monitoramentoEditavel", monitoramentoEditavel);
		return "redirect:/loginAdmin";
	}
}
