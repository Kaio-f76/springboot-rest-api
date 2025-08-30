package com.br.RegistroDeTarefas.models;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "TB_Task")
public class TaskModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	
	@NotBlank(message = "Nome é obrigatório")
	@Size(max = 30, message = "Nome deve ter no máximo 30 caracteres")
    @Column(nullable = false, unique = true, length = 30)
	private String nome;
    
	@NotBlank(message = "descrição é obrigatório")
	@Size(max = 30, message = "descrição deve ter no máximo 60 caracteres")
    @Column(nullable = false, unique = false, length = 60)
	private String descricao;
    
	@NotBlank(message = "Status é obrigatório")
	@Size(max = 30, message = "Status deve ter no máximo 15 caracteres")
    @Column(nullable = false, unique = false, length = 15)
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
