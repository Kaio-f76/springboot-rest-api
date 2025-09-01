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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


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
        taskRepository.deleteAll(); // limpa o banco antes de cada teste
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
}
