package com.dock.service.domain.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dock.service.domain.exceptionhandler.NegocioException;
import com.dock.service.domain.model.Conta;
import com.dock.service.domain.model.TipoTransacao;
import com.dock.service.domain.model.Transacao;
import com.dock.service.domain.repository.ContaRepository;
import com.dock.service.domain.repository.TransacaoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TransacaoService {

	private TransacaoRepository transacaoRepository;

	private ContaRepository contaRepository;

	@Transactional
	public Transacao deposito(Transacao transacao) {
		Conta conta = contaRepository.findById(transacao.getConta().getId())
				.orElseThrow(() -> new NegocioException("Conta não encontrado."));
		if (!conta.isFlagAtivo())
			throw new NegocioException("Conta não está ativada.");
		if (transacao.getValor().doubleValue() <= 0.0)
			throw new NegocioException("Valor invalido.");
		transacao.setTipoTransacao(TipoTransacao.DEPOSITO);
		conta.setSaldo(conta.getSaldo().add(transacao.getValor()));
		contaRepository.save(conta);
		return transacaoRepository.save(transacao);
	}

	@Transactional
	public Transacao sacar(Transacao transacao) {
		Conta conta = contaRepository.findById(transacao.getConta().getId())
				.orElseThrow(() -> new NegocioException("Conta não encontrado."));
		if (!conta.isFlagAtivo())
			throw new NegocioException("Conta não está ativada.");
		if (transacao.getValor().doubleValue() <= 0.0)
			throw new NegocioException("Valor invalido.");
		if (transacao.getValor().doubleValue() > conta.getSaldo().doubleValue())
			throw new NegocioException("Valor invalido.");
		transacao.setTipoTransacao(TipoTransacao.SAQUE);
		conta.setSaldo(conta.getSaldo().subtract(transacao.getValor()));
		contaRepository.save(conta);
		return transacaoRepository.save(transacao);
	}

	public List<Transacao> consultaExtrato(Long id) {
		List<Transacao> transacoes = transacaoRepository.findByContaId(id);
		if (transacoes.isEmpty())
			new NegocioException("Conta não encontrado");
		return transacoes;
	}

	public List<Transacao> consultaExtratoPorPeriodo(Long id, Calendar dataInicial, Calendar dataFinal) {
		contaRepository.findById(id).orElseThrow(() -> new NegocioException("Conta não encontrado"));
		return transacaoRepository.findAllByConta_IdAndDataTransacaoGreaterThanEqualAndDataTransacaoLessThanEqual(id,
				dataInicial, dataFinal);
	}
}
