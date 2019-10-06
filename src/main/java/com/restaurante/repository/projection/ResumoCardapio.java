package com.restaurante.repository.projection;

import java.math.BigDecimal;

public class ResumoCardapio {


	private Long codigo;
	private String nome;
	private String descricao;
	private BigDecimal preco;
	
	
	public ResumoCardapio(Long codigo, String nome, String descricao, BigDecimal preco) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
	}


	public Long getCodigo() {
		return codigo;
	}


	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public BigDecimal getPreco() {
		return preco;
	}


	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
}
