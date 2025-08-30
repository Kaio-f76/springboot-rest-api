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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	@Autowired
	private TaskService taskservice;
	
	@PostMapping
	public ResponseEntity<TaskModel> create(@Valid @RequestBody TaskModel taskmodel) {
		TaskModel savedTask = taskservice.save(taskmodel);
        return ResponseEntity.ok(savedTask);
	}
	
	@GetMapping
	public ResponseEntity<List<TaskModel>> list() {
		return ResponseEntity.ok(taskservice.findALL());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TaskModel> find(@PathVariable UUID id) {
	        return taskservice.findById(id)
	            .map(ResponseEntity::ok)
	            .orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TaskModel> update(@PathVariable UUID id, @Valid @RequestBody TaskModel taskupdate) {
		try {
            TaskModel updatedTask = taskservice.update(id, taskupdate);
            return ResponseEntity.ok(updatedTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }	
	}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		taskservice.delete(id);
        return ResponseEntity.noContent().build();
	}
	
	
	
}









