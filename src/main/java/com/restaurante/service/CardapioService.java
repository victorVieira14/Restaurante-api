package com.restaurante.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.restaurante.models.Cardapio;
import com.restaurante.repository.CardapioRepository;

@Service
public class CardapioService {
	
	@Autowired
	private CardapioRepository cardapioRepository;
	
	
	public Cardapio atualiza(Long codigo, Cardapio cardapio) {
	Cardapio cardapioSalva = cardapioRepository.findOne(codigo);
		
		if (cardapioSalva == null) 
			throw new EmptyResultDataAccessException(1);
		
		
		BeanUtils.copyProperties(cardapio, cardapioSalva, "codigo");
	
		
		return cardapioRepository.save(cardapioSalva);
	}

}
