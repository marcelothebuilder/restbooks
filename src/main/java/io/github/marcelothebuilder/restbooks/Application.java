package io.github.marcelothebuilder.restbooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Esta classe é o ponto de início da aplicação. Inicia com Spring Boot.
 * 
 * @author Marcelo Paixao Resende
 *
 */
@SpringBootApplication
public class Application {

	/**
	 * Entrypoint da aplicação.
	 * 
	 * @param args
	 *            argumentos passados via linha de comando
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
