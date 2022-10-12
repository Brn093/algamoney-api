package com.example.algamoney.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.lancamento.LancamentoRepositoryQuery;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

	Lancamento save(Optional<Lancamento> lancamentoSalvo);

	//Lancamento save(Optional<?> lancamentoSalvo);
	//public Optional<Lancamento> findByDescricao(String descricao);
	
	
}