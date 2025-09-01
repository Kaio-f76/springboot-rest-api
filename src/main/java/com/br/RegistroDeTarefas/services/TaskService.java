package com.br.RegistroDeTarefas.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.RegistroDeTarefas.models.TaskModel;
import com.br.RegistroDeTarefas.repositories.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskrepository;
	
	@Transactional
	public TaskModel save(TaskModel taskmodel) {
		return taskrepository.save(taskmodel);
	}
	
	@Transactional(readOnly = true)
	public List<TaskModel> findALL() {
		return taskrepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<TaskModel> findById(UUID id) {
		return taskrepository.findById(id);
	}
	
	@Transactional
	public List<TaskModel> findByStatus(String status) {
	    return taskrepository.findByStatus(status);
	}

	
	@Transactional
	public TaskModel update(UUID id, TaskModel taskupdate) {
		TaskModel taskmodel = taskrepository.findById(id).orElseThrow();
		taskmodel.setNome(taskupdate.getNome());
		taskmodel.setDescricao(taskupdate.getDescricao());
		taskmodel.setStatus(taskupdate.getStatus());
		return taskrepository.save(taskmodel);
	}
	
	@Transactional
	public void delete(UUID id) {
		taskrepository.deleteById(id);
	}
	
	
	
	
	
}
