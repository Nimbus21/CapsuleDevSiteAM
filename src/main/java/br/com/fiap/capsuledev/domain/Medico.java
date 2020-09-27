package br.com.fiap.capsuledev.domain;

import java.util.ArrayList;
import java.util.List;

public class Medico {
	private Long codigo;
	private String nome;
	private String crm;
	private List<Monitoramento> monitoramentos = new ArrayList<Monitoramento>();
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCrm() {
		return crm;
	}
	public void setCrm(String crm) {
		this.crm = crm;
	}
	public List<Monitoramento> getMonitoramentos() {
		return monitoramentos;
	}
	public void setMonitoramentos(List<Monitoramento> monitoramentos) {
		this.monitoramentos = monitoramentos;
	}
	
	
}
