package com.dock.service.domain.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dock.service.domain.model.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>{
	
	public List<Transacao> findByContaId(Long idConta);
	
	public List<Transacao> findAllByConta_IdAndDataTransacaoGreaterThanEqualAndDataTransacaoLessThanEqual(Long id, Calendar dataInicio, Calendar dataFim);
}
