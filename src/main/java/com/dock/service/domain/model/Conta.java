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
import javax.validation.Valid;
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
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PESSOA_ID")
	@Valid @NotNull
	private Pessoa pessoa;

	@Column(name = "SALDO", precision = 11, scale = 2)
	@NotNull
	private BigDecimal saldo;

	@Column(name = "LIMITE_SAQUE_DIARIO", precision = 11, scale = 2)
	@NotNull
	private BigDecimal limiteSaqueDiario;

	@Column(name = "FLAG_ATIVO")
	private boolean flagAtivo;

	@Enumerated
	@Column(name = "TIPO_CONTA")
	@NotNull
	private TipoConta tipoConta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_TRANSACAO")
	private Calendar dataCriacao;

	@JsonCreator
	public Conta(@JsonProperty("conta_id") Long id, 
			     @JsonProperty("pessoa") Pessoa pessoa,
			     @JsonProperty("tipoConta") TipoConta tipoConta) {
		
		if (id != null) {
			this.setId(id);
		}
		this.pessoa = pessoa;
		this.tipoConta = tipoConta;
		this.limiteSaqueDiario = BigDecimal.valueOf(1200.0);
		this.flagAtivo = true;
		this.dataCriacao = Calendar.getInstance();
	}
}
