package com.restaurante.repository.cardapio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.restaurante.models.Cardapio;
import com.restaurante.models.Cardapio_;
import com.restaurante.repository.filter.CardapioFilter;
import com.restaurante.repository.projection.ResumoCardapio;


public class CardapioRepositoryImpl implements CardapioRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;		
	

	@Override
	public Page<Cardapio> filtrar(CardapioFilter CardapioFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cardapio> criteria = builder.createQuery(Cardapio.class);
		Root<Cardapio> root = criteria.from(Cardapio.class);
		
		Predicate[] predicates = criarRestricoes(CardapioFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Cardapio> query = manager.createQuery(criteria);
		adicionarNumeroPorPaginacao(query, pageable);	
		
		
		
		
		return new PageImpl<>(query.getResultList(), pageable, total(CardapioFilter));
	}

	

	@Override
	public Page<ResumoCardapio> resumir(CardapioFilter CardapioFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoCardapio> criteria = builder.createQuery(ResumoCardapio.class);
		Root<Cardapio> root = criteria.from(Cardapio.class);
		
		
		criteria.select(builder.construct(ResumoCardapio.class, 
				root.get(Cardapio_.codigo), 
				root.get(Cardapio_.nome),
				root.get(Cardapio_.descricao)));
			
		
		Predicate[] predicates = criarRestricoes(CardapioFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ResumoCardapio> query = manager.createQuery(criteria);
		adicionarNumeroPorPaginacao(query, pageable);
		
		
		return new PageImpl<>(query.getResultList(), pageable, total(CardapioFilter));
	}

	
	private Predicate[] criarRestricoes(CardapioFilter CardapioFilter, CriteriaBuilder builder,
			Root<Cardapio> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(CardapioFilter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root.get(Cardapio_.descricao)), "%" + CardapioFilter.getDescricao().toLowerCase() + "%"));
		}
		
		if (!StringUtils.isEmpty(CardapioFilter.getNome())) {
			predicates.add(builder.like(
					builder.lower(root.get(Cardapio_.nome)), "%" + CardapioFilter.getNome().toLowerCase() + "%"));
		}

	
		
		return predicates.toArray(new Predicate[predicates.size()]);

	}
	
	private void adicionarNumeroPorPaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int numeroTotalPorPagina = pageable.getPageSize();
		int numeroInicial = paginaAtual * numeroTotalPorPagina;
		
		query.setFirstResult(numeroInicial);
		query.setMaxResults(numeroTotalPorPagina);
		
	}
	
	
	private Long total(CardapioFilter CardapioFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Cardapio> root = criteria.from(Cardapio.class);
		
		Predicate[] predicates = criarRestricoes(CardapioFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}


}
