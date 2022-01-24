package com.dock.service.domain.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dock.service.domain.exceptionhandler.NegocioException;
import com.dock.service.domain.model.Conta;
import com.dock.service.domain.repository.ContaRepository;
import com.dock.service.domain.repository.PessoaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ContaService{

	private PessoaRepository pessoaRepository;
	
	private ContaRepository contaRepository;
	
	@Transactional
	public Conta salvar(Conta conta){
		boolean cpf = pessoaRepository.findByCpf(conta.getPessoa().getCpf())
				.stream()
				.anyMatch(clienteExistente -> !clienteExistente.equals(conta));
		if (!cpf) {
			throw new NegocioException("Cliente não cadastrado.");
		}
		return contaRepository.save(conta);
	}
	
	public BigDecimal consultaSaldo(Long id) {
		Conta conta = contaRepository.findById(id)
				.orElseThrow(() -> new NegocioException("Conta não encontrado"));
		return conta.getSaldo();	
	}

	public Conta blockedConta(Long id) {
		Conta conta = contaRepository.findById(id)
				.orElseThrow(() -> new NegocioException("Conta não encontrado"));
		conta.setFlagAtivo(conta.isFlagAtivo() ? false : true);
		return contaRepository.save(conta);	
	}
}