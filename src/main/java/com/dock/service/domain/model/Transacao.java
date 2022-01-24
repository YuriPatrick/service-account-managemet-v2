package com.dock.service.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Transacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "CONTA_ID")
	@NotNull
	private Conta conta;
	
	@Column(name = "VALOR", precision = 11, scale = 2)
	@NotNull
	private BigDecimal valor;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_TRANSACAO")
	private Calendar dataTransacao;
	
	@Enumerated
	@Column(name = "TIPO_TRANSACAO", nullable = false)
	@NotNull
	private TipoTransacao tipoTransacao;
		
	@JsonCreator
	public Transacao(@JsonProperty("conta") Conta conta,
					 @JsonProperty("valor") BigDecimal valor) {
		this.conta = conta;
		this.valor = valor;
		this.dataTransacao = Calendar.getInstance();
	}
}