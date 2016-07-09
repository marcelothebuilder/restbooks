DROP TABLE IF EXISTS comentario;
DROP TABLE IF EXISTS livro;
DROP TABLE IF EXISTS autor;

CREATE TABLE autor (
	codigo BIGINT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	nascimento DATE,
	nacionalidade VARCHAR(100),
	PRIMARY KEY (codigo)
);

CREATE TABLE livro (
	codigo BIGINT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	publicacao DATE NOT NULL,
	editora VARCHAR(60) NOT NULL,
	resumo VARCHAR(1000),
	codigo_autor BIGINT NOT NULL,
    PRIMARY KEY(codigo),
    CONSTRAINT fk_livro_autor FOREIGN KEY (codigo_autor) REFERENCES autor(codigo)
);

CREATE TABLE comentario (
	codigo BIGINT NOT NULL AUTO_INCREMENT,
	codigo_livro BIGINT NOT NULL,
	autor VARCHAR(100) NOT NULL,
	conteudo VARCHAR(1000) NOT NULL,
	data TIMESTAMP NOT NULL DEFAULT NOW(),
	PRIMARY KEY (codigo),
	CONSTRAINT fk_comentario_livro FOREIGN KEY (codigo_livro) REFERENCES livro (codigo)
);
