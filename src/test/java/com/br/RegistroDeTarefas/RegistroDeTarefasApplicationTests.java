//Teste unitário
package com.br.RegistroDeTarefas;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Test
    public void testFindAllTasks() {
        List<TaskModel> tasks = taskservice.findALL();
        assertNotNull(tasks);
    }

    @Test
    public void testFindTaskById() {
        TaskModel task = new TaskModel("Task 2", "Descricao", "false");
        TaskModel savedTask = taskservice.save(task);
        Optional<TaskModel> foundTask = taskservice.findById(savedTask.getId());
        assertTrue(foundTask.isPresent());
        assertEquals(savedTask.getId(), foundTask.get().getId());
    }

    @Test
    public void testUpdateTask() {
        TaskModel task = new TaskModel("Task 3", "Descricao", "false");
        TaskModel savedTask = taskservice.save(task);

        TaskModel update = new TaskModel("Task 3 Atualizada", "Descricao Atualizada", "true");
        TaskModel updatedTask = taskservice.update(savedTask.getId(), update);

        assertEquals("Task 3 Atualizada", updatedTask.getNome());
        assertEquals("Descricao Atualizada", updatedTask.getDescricao());
        assertEquals("true", updatedTask.getStatus());
    }

    @Test
    public void testDeleteTask() {
        TaskModel task = new TaskModel("Task 4", "Descricao", "false");
        TaskModel savedTask = taskservice.save(task);
        UUID id = savedTask.getId();

        taskservice.delete(id);

        Optional<TaskModel> deletedTask = taskservice.findById(id);
        assertFalse(deletedTask.isPresent());
    }
}

