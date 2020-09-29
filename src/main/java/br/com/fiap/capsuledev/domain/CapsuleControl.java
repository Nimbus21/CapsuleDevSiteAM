package br.com.fiap.capsuledev.domain;

import java.util.Date;

public class CapsuleControl {
	private Long codigo;
	private String remedio;
	private String dose;
	private Date data;
	private String dataFormatada;
	private Monitoramento monitoramento;
	private Long codigoMonitoramento;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getRemedio() {
		return remedio;
	}
	public void setRemedio(String remedio) {
		this.remedio = remedio;
	}
	public String getDose() {
		return dose;
	}
	public void setDose(String dose) {
		this.dose = dose;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDataFormatada() {
		return dataFormatada;
	}
	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}
	public Monitoramento getMonitoramento() {
		return monitoramento;
	}
	public void setMonitoramento(Monitoramento monitoramento) {
		this.monitoramento = monitoramento;
	}
	public Long getCodigoMonitoramento() {
		return codigoMonitoramento;
	}
	public void setCodigoMonitoramento(Long codigoMonitoramento) {
		this.codigoMonitoramento = codigoMonitoramento;
	}
	
}
