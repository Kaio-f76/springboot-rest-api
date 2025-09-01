package com.br.RegistroDeTarefas;

import com.br.RegistroDeTarefas.models.TaskModel;
import com.br.RegistroDeTarefas.repositories.TaskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setup() {
        taskRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void testCreateTaskValidationError() throws Exception {
        String json = "{\"nome\":\"\",\"descricao\":\"desc\",\"status\":\"true\"}";

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nome").value("Nome é obrigatório"));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void testGetAllTasks() throws Exception {
        TaskModel task = new TaskModel("Tarefa 1", "desc", "true");
        taskRepository.save(task);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Tarefa 1"))
                .andExpect(jsonPath("$[0].status").value("true"));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void testGetTaskById() throws Exception {
        TaskModel task = new TaskModel("Tarefa única", "detalhe", "true");
        task = taskRepository.save(task);

        mockMvc.perform(get("/tasks/" + task.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Tarefa única"));
    }
    
    @Test
    @WithMockUser(roles = {"USER"})
    void testGetTasksByStatus() throws Exception {
        TaskModel task1 = new TaskModel("Tarefa 1", "detalhe 1", "PENDENTE");
        TaskModel task2 = new TaskModel("Tarefa 2", "detalhe 2", "CONCLUIDA");
        taskRepository.save(task1);
        taskRepository.save(task2);

        mockMvc.perform(get("/tasks/status/PENDENTE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Tarefa 1"))
                .andExpect(jsonPath("$[0].status").value("PENDENTE"));
    }


    @Test
    @WithMockUser(roles = {"USER"})
    void testUpdateTask() throws Exception {
        TaskModel task = new TaskModel("Original", "desc", "false");
        task = taskRepository.save(task);

        String updatedJson = "{\"nome\":\"Atualizada\",\"descricao\":\"nova desc\",\"status\":\"true\"}";

        mockMvc.perform(put("/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Atualizada"))
                .andExpect(jsonPath("$.status").value("true"));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void testDeleteTask() throws Exception {
        TaskModel task = new TaskModel("Para deletar", "desc", "false");
        task = taskRepository.save(task);

        mockMvc.perform(delete("/tasks/" + task.getId())
                .with(csrf()))
                .andExpect(status().isNoContent());
    }
}
