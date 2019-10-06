CREATE TABLE cardapio (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	descricao VARCHAR(70) NOT NULL,
	preco DECIMAL(10,2) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO cardapio(nome, descricao, preco) values("Pastel", "Frango com catupiry", 4.50);
INSERT INTO cardapio(nome, descricao, preco) values("Pastel", "Frango com catupiry", 4.50);