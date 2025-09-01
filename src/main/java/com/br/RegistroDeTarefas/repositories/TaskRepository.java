package com.br.RegistroDeTarefas.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.RegistroDeTarefas.models.TaskModel;

public interface TaskRepository extends JpaRepository<TaskModel, UUID> {
	List<TaskModel> findByStatus(String status);
}
