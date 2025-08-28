package com.br.RegistroDeTarefas.models;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_Task")
public class TaskModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
    @Column(nullable = false, unique = true, length = 10)
	private String nome;
    
    @Column(nullable = false, unique = false, length = 30)
	private String descricao;
    
    @Column(nullable = false, unique = false, length = 5)
	private String status;

      
	public TaskModel() {
	}
	
	public TaskModel(String nome, String descricao, String status) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.status = status;
	}



	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
    
    
	
}
