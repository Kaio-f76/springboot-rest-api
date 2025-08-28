//Teste unitário
package com.br.RegistroDeTarefas;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.br.RegistroDeTarefas.models.TaskModel;
import com.br.RegistroDeTarefas.services.TaskService;

@SpringBootTest
class RegistroDeTarefasApplicationTests {

	@Autowired
	private TaskService taskservice;
	
	@Test
	public void testCreateTask() {
	    TaskModel task = new TaskModel("Task 1", "Descricao", "true");
	    TaskModel savedTask = taskservice.save(task);
	    assertNotNull(savedTask.getId());
	  }

}
