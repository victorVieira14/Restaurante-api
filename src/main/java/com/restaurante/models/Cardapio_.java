package com.restaurante.models;

import java.math.BigDecimal;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Cardapio.class)
public class Cardapio_ {
	

		public static volatile SingularAttribute<Cardapio, Long> codigo;
		public static volatile SingularAttribute<Cardapio, String> nome;
		public static volatile SingularAttribute<Cardapio, String> descricao;
		public static volatile SingularAttribute<Cardapio, BigDecimal> preco;

		
}
