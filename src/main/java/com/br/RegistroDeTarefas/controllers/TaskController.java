package com.br.RegistroDeTarefas.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.RegistroDeTarefas.models.TaskModel;
import com.br.RegistroDeTarefas.services.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {
	@Autowired
	private TaskService taskservice;
	
	@PostMapping
	public TaskModel create(@RequestBody TaskModel taskmodel) {
		return taskservice.save(taskmodel);
	}
	
	@GetMapping
	public List<TaskModel> list() {
		return taskservice.findALL();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TaskModel> find(@PathVariable UUID id) {
		return taskservice.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public TaskModel update(@PathVariable UUID id, @RequestBody TaskModel taskmodel) {
		return taskservice.update(id, taskmodel);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		taskservice.delete(id);
	}
	
	
	
}









