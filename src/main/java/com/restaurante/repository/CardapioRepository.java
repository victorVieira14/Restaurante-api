package com.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.models.Cardapio;
import com.restaurante.repository.cardapio.CardapioRepositoryQuery;

public interface CardapioRepository extends JpaRepository<Cardapio, Long>, CardapioRepositoryQuery {

}
