// Teste unitário
package com.br.RegistroDeTarefas;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.br.RegistroDeTarefas.config.SecurityConfig;
import com.br.RegistroDeTarefas.controllers.TaskController;
import com.br.RegistroDeTarefas.models.TaskModel;
import com.br.RegistroDeTarefas.services.TaskService;

@WebMvcTest(TaskController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
public class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean    
    private TaskService taskService;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetAllTasks() throws Exception {
        TaskModel task = new TaskModel();
        task.setId(UUID.randomUUID());
        task.setNome("Tarefa 1");
        task.setDescricao("desc");
        task.setStatus("true");

        List<TaskModel> tasks = List.of(task);
        Mockito.when(taskService.findALL()).thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nome").value("Tarefa 1"));
    }
    
    @Test
    @WithMockUser(roles = {"USER"})
    void testCreateTaskValidationError() throws Exception {
        String json = "{\"nome\":\"\",\"descricao\":\"desc\",\"status\":\"true\"}"; // nome vazio para disparar validação

        mockMvc.perform(post("/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .with(csrf())  // adiciona token CSRF na requisição
            .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.nome").value("Nome é obrigatório"));
    }
}
