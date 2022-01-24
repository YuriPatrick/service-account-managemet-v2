package com.dock.service.api.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dock.service.domain.exceptionhandler.NegocioException;
import com.dock.service.domain.model.Conta;
import com.dock.service.domain.repository.ContaRepository;
import com.dock.service.domain.service.ContaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/contas")
public class ContaController {

	private ContaService contaService;
	private ContaRepository contaRepository;
	
	@ResponseBody
	@PostMapping(value = "/cria", consumes = "application/json")
	public ResponseEntity<Conta> cadastrar(@Valid @RequestBody Conta conta) {
		try {			
			return ResponseEntity.ok(contaService.salvar(conta));
		}catch(NegocioException e ) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@ResponseBody
	@GetMapping("/consultarSaldo/{contaId}")
	public ResponseEntity<BigDecimal> consulta(@Valid @PathVariable("contaId") Long contaId){
		if (!contaRepository.existsById(contaId)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(contaService.consultaSaldo(contaId));
	}
	
	@PutMapping(value = "/blocked/{contaId}")
	public ResponseEntity<Conta> blockedConta(@Valid @PathVariable("contaId") Long contaId){
		if (!contaRepository.existsById(contaId)) {
			return ResponseEntity.notFound().build();
		}
		contaService.blockedConta(contaId);
		return ResponseEntity.noContent().build();
	}
}
