package com.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.models.Cardapio;

public interface CardapioRepository extends JpaRepository<Cardapio, Long> {

}
