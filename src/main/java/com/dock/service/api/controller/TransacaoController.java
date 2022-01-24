package com.dock.service.api.controller;

import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dock.service.domain.exceptionhandler.NegocioException;
import com.dock.service.domain.model.Transacao;
import com.dock.service.domain.service.TransacaoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

	@Autowired
	private TransacaoService transacaoService;

	@PutMapping(value = "/deposito", consumes = "application/json")
	public ResponseEntity<Transacao> depositar(@Valid @RequestBody Transacao transacao) throws NegocioException {

		try {
			return ResponseEntity.ok(transacaoService.deposito(transacao));
		} catch (NegocioException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping(value = "/saque", consumes = "application/json")
	public ResponseEntity<Transacao> sacar(@Valid @RequestBody Transacao transacao) {
		try {
			return ResponseEntity.ok(transacaoService.sacar(transacao));
		} catch (NegocioException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping(value = "/extratoCompleto/{contaId}")
	public ResponseEntity<List<Transacao>> extrato(@Valid @PathVariable("contaId") Long contaId) {
		try {
			return ResponseEntity.ok(transacaoService.consultaExtrato(contaId));
		} catch (NegocioException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ResponseBody
	@GetMapping(value = "/extratoPeriodo/{contaId}/{dataInicio}/{dataFim}")
	public ResponseEntity<List<Transacao>> extratoPorPeriodo(@Valid @PathVariable("contaId") Long contaId,
			@PathVariable("dataInicio") @DateTimeFormat(pattern = "dd-MM-yyyy") Calendar dataInicio,
			@PathVariable("dataFim") @DateTimeFormat(pattern = "dd-MM-yyyy") Calendar dataFim) throws NegocioException {
		try {
			return ResponseEntity.ok(transacaoService.consultaExtratoPorPeriodo(contaId, dataInicio, dataFim));
		} catch (NegocioException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}