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

		if (pessoa.isEmpty()) {
			throw new PessoaInexistenteOuInativaException();
		}
		return lancamentoRepository.save(lancamento);
	}

	public Lancamento atualizar(Long id, Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarLancamentoExistente(id);
		if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			validarPessoa(lancamento);
		}

		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "id");

		return lancamentoRepository.save(lancamentoSalvo);
	}

	public void validarPessoa(Lancamento lancamento) {
		Pessoa pessoa = lancamento.getPessoa();
		if (pessoa == null || pessoa.getId() == null)
			throw new PessoaInexistenteOuInativaException();

		Optional<Pessoa> pessoaOptional = pessoaRepository.findById(pessoa.getId());

		if (pessoaOptional.isEmpty())
			throw new PessoaInexistenteOuInativaException();
	}

	public Lancamento buscarLancamentoExistente(Long id) {
		Optional<Lancamento> lancamentoSalvo = lancamentoRepository.findById(id);
		if (lancamentoSalvo.isEmpty()) {
			throw new IllegalArgumentException();
		}

		return lancamentoSalvo.get();
	}
}
