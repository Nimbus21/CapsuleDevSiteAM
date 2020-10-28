package br.com.fiap.capsuledev.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class Monitoramento {

	private Long codigo;
	private String descricao;
	private Date inicio;
	private String inicioFormatado;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fim;
	private String fimFormatado;
	private Boolean ativo;
	private Long frequenciaSegundos;
	private Hospital hospital;
	private Long codigoHospital;
	private List<CapsuleWatch> listaCapsuleWatch = new ArrayList<CapsuleWatch>();
	private Medico medico;
	private Long codigoMedico;
	private List<CapsuleControl> listaCapsuleControl = new ArrayList<CapsuleControl>();
	private Paciente paciente;
	private Long codigoPaciente;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	
	public String getInicioFormatado() {
		return inicioFormatado;
	}
	public void setInicioFormatado(String inicioFormatado) {
		this.inicioFormatado = inicioFormatado;
	}
	public Date getFim() {
		return fim;
	}
	public void setFim(Date fim) {
		this.fim = fim;
	}
	
	public String getFimFormatado() {
		return fimFormatado;
	}
	public void setFimFormatado(String fimFormatado) {
		this.fimFormatado = fimFormatado;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public Long getFrequenciaSegundos() {
		return frequenciaSegundos;
	}
	public void setFrequenciaSegundos(Long frequenciaSegundos) {
		this.frequenciaSegundos = frequenciaSegundos;
	}
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	public Long getCodigoPaciente() {
		return codigoPaciente;
	}
	public void setCodigoPaciente(Long codigoPaciente) {
		this.codigoPaciente = codigoPaciente;
	}
	public Hospital getHospital() {
		return hospital;
	}
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public Long getCodigoHospital() {
		return codigoHospital;
	}
	public void setCodigoHospital(Long codigoHospital) {
		this.codigoHospital = codigoHospital;
	}
	public List<CapsuleWatch> getListaCapsuleWatch() {
		return listaCapsuleWatch;
	}
	public void setListaCapsuleWatch(List<CapsuleWatch> listaCapsuleWatch) {
		this.listaCapsuleWatch = listaCapsuleWatch;
	}
	public Medico getMedico() {
		return medico;
	}
	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	public Long getCodigoMedico() {
		return codigoMedico;
	}
	public void setCodigoMedico(Long codigoMedico) {
		this.codigoMedico = codigoMedico;
	}
	public List<CapsuleControl> getListaCapsuleControl() {
		return listaCapsuleControl;
	}
	public void setListaCapsuleControl(List<CapsuleControl> listaCapsuleControl) {
		this.listaCapsuleControl = listaCapsuleControl;
	}
	
}
