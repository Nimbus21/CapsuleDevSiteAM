package br.com.fiap.capsuledev.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.capsuledev.domain.Hospital;

@Controller
public class HospitalController {

	@GetMapping("/admin")
	public String index(Model model) {
		RestTemplate api = new RestTemplate();
		List<?> hospitais = api.getForObject("https://capsuledevdigital01.herokuapp.com/hospital", List.class);
		model.addAttribute("hospitais", hospitais);
		return "admin/index";
	}
	
	@PostMapping("/loginAdmin/cadastrarHospital")
	public String salvarHospital(Hospital hospital, RedirectAttributes redirectAttributes) {
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/hospital";
		Hospital hospitalResultado = api.postForObject(url, hospital, Hospital.class);
		
		redirectAttributes.addFlashAttribute("msg", String.format("Hospital \"%s\" cadastrado com sucesso!", hospitalResultado.getNome()));
		return "redirect:/loginAdmin";
	}
	
	@PostMapping("/loginAdmin/editarHospital")
	public String editarHospital(long codigo, RedirectAttributes redirectAttributes) {
		
		RestTemplate api = new RestTemplate();
		String url = "https://capsuledevdigital01.herokuapp.com/hospital/" + codigo;
		
		Hospital hospitalEditavel = api.getForObject(url, Hospital.class);
		
		redirectAttributes.addFlashAttribute("hospitalEditavel", hospitalEditavel);
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
}
