package com.br.RegistroDeTarefas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RegistroDeTarefasApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistroDeTarefasApplication.class, args);
		System.out.println("Rodando na porta 8080 !");
	}

}
