DROP TABLE IF EXISTS comentario;
DROP TABLE IF EXISTS livro;

CREATE TABLE livro (
	codigo BIGINT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	publicacao DATE NOT NULL,
	editora VARCHAR(60) NOT NULL,
	resumo VARCHAR(1000),
	autor VARCHAR(100) NOT NULL,
    PRIMARY KEY(codigo)
);

CREATE TABLE comentario (
	codigo BIGINT NOT NULL AUTO_INCREMENT,
	codigo_livro BIGINT NOT NULL,
	autor VARCHAR(100) NOT NULL,
	conteudo VARCHAR(1000) NOT NULL,
	data TIMESTAMP NOT NULL DEFAULT NOW(),
	PRIMARY KEY (codigo),
	FOREIGN KEY (codigo_livro) REFERENCES livro (codigo)
);