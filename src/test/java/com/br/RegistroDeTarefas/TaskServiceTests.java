//Teste de integração
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
class TaskServiceTests {

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
    public void testFindTasksByStatus() {
        TaskModel task1 = new TaskModel("Task A", "Descrição A", "PENDENTE");
        TaskModel task2 = new TaskModel("Task B", "Descrição B", "PENDENTE");
        TaskModel task3 = new TaskModel("Task C", "Descrição C", "CONCLUIDA");

        taskservice.save(task1);
        taskservice.save(task2);
        taskservice.save(task3);

        List<TaskModel> pendentes = taskservice.findByStatus("PENDENTE");

        assertEquals(2, pendentes.size());
        assertTrue(pendentes.stream().allMatch(t -> t.getStatus().equals("PENDENTE")));
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

