package com.br.RegistroDeTarefas.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.RegistroDeTarefas.models.TaskModel;

public interface TaskRepository extends JpaRepository<TaskModel, UUID> {
}
