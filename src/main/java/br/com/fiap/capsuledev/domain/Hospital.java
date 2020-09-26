package br.com.fiap.capsuledev.domain;

import java.util.ArrayList;
import java.util.List;

public class Hospital {

	private Long codigo;
	private String nome;
	private String cnpj;
	private String inscricaoEstadual;
	private String endereco;
	private String telefone;
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
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}
	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public List<Monitoramento> getMonitoramentos() {
		return monitoramentos;
	}
	public void setMonitoramentos(List<Monitoramento> monitoramentos) {
		this.monitoramentos = monitoramentos;
	}
	
}
