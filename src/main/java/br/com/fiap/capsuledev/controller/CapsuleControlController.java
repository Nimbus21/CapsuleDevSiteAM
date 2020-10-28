package br.com.fiap.capsuledev.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.capsuledev.domain.CapsuleControl;
import br.com.fiap.capsuledev.domain.Monitoramento;

@Controller
public class CapsuleControlController {
	
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

}
