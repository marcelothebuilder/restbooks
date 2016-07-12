INSERT INTO autor (nome, nascimento, nacionalidade) VALUES ('Geraldo', NOW(), 'Russia');
INSERT INTO livro (nome, publicacao, editora, codigo_autor) VALUES ('Harry Potter', NOW(), 'Abril', 1);
INSERT INTO comentario (codigo_livro, autor, conteudo) VALUES (1, 'Marcelo', 'Que livro ruim!');
INSERT INTO comentario (codigo_livro, autor, conteudo) VALUES (1, 'Roberta', 'Que livro ruim/2!');