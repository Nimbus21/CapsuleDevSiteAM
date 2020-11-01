package br.com.fiap.capsuledev.domain;

import java.util.Date;

public class CapsuleWatch {
	private Long codigo;
	private Integer batimentos;
	private Float temperatura;
	private Float pressao;
	private Date dataHora;
	private String dataFormatada;
	private String horaFormatada;
	private Monitoramento monitoramento;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Integer getBatimentos() {
		return batimentos;
	}
	public void setBatimentos(Integer batimentos) {
		this.batimentos = batimentos;
	}
	public Float getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(Float temperatura) {
		this.temperatura = temperatura;
	}
	public Float getPressao() {
		return pressao;
	}
	public void setPressao(Float pressao) {
		this.pressao = pressao;
	}
	public Date getDataHora() {
		return dataHora;
	}
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	public String getDataFormatada() {
		return dataFormatada;
	}
	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}
	public String getHoraFormatada() {
		return horaFormatada;
	}
	public void setHoraFormatada(String horaFormatada) {
		this.horaFormatada = horaFormatada;
	}
	public Monitoramento getMonitoramento() {
		return monitoramento;
	}
	public void setMonitoramento(Monitoramento monitoramento) {
		this.monitoramento = monitoramento;
	}
	
}
