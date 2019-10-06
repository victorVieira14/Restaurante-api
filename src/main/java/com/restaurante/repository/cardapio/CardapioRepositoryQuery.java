package com.restaurante.repository.cardapio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.restaurante.models.Cardapio;
import com.restaurante.repository.filter.CardapioFilter;
import com.restaurante.repository.projection.ResumoCardapio;


public interface CardapioRepositoryQuery {

	public Page<Cardapio> filtrar(CardapioFilter filter, Pageable pageable);
	public Page<ResumoCardapio> resumir(CardapioFilter filter, Pageable pageable);
	
}
