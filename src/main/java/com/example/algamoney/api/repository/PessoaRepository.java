package com.example.algamoney.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	public Page<Pessoa> findByNomeContaining(String nome, Pageable pageable);
	
	Pessoa save(Optional<Pessoa> pessoaSalva);
}