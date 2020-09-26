package br.com.fiap.capsuledev.domain;

import java.util.Date;

public class Monitoramento {

	private Long codigo;
	private Date inicio;
	private Date fim;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public Date getFim() {
		return fim;
	}
	public void setFim(Date fim) {
		this.fim = fim;
	}
	
	
}
