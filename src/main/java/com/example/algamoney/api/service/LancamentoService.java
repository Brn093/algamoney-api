package com.example.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	public Lancamento salvar(Lancamento lancamento) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getId());

        if(pessoa.isEmpty()) {
            throw new PessoaInexistenteOuInativaException();
        }
        return lancamentoRepository.save(lancamento);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public Lancamento atualizar(Long id, Lancamento lancamento) {
		Optional<Lancamento> lancamentoSalvo = buscarLancamentoExistente(id);
		if(!lancamento.getPessoa().equals(lancamentoSalvo)) {
			validarPessoa(lancamento);
		}
		
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "id");
		
		return lancamentoRepository.save(lancamentoSalvo);
	}
	
	private void validarPessoa(Lancamento lancamento) {
		Optional<Pessoa> pessoa = null;  
		if(lancamento.getPessoa().getId() != null) {
			pessoa = pessoaRepository.findById(lancamento.getPessoa().getId());
		}
		
		if(pessoa == null || pessoa.isPresent()) {
			throw new PessoaInexistenteOuInativaException();
		}	
	}
	
	
	private Optional<Lancamento> buscarLancamentoExistente(Long id) {
		Optional<Lancamento> lancamentoSalvo = lancamentoRepository.findById(id);
		if(lancamentoSalvo == null) {
			throw new IllegalArgumentException();
		}
		
		return lancamentoSalvo;
	}
}
