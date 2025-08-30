package com.br.RegistroDeTarefas;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.br.RegistroDeTarefas.config.SecurityConfig;
import com.br.RegistroDeTarefas.controllers.TaskController;
import com.br.RegistroDeTarefas.services.TaskService;

@WebMvcTest(TaskController.class)
@Import(SecurityConfig.class) 
@AutoConfigureMockMvc
public class TaskSecurityTests {

    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean    
    private TaskService taskService;


    // Usuário autenticado com role USER tenta acessar endpoint protegido
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void whenUserWithRoleUserAccessTasks_thenOk() throws Exception {
        mockMvc.perform(get("/tasks"))
            .andExpect(status().isOk());
    }

    // Usuário anônimo (não autenticado) tenta acessar endpoint protegido
    @Test
    void whenAnonymousAccessTasks_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/tasks"))
            .andExpect(status().isUnauthorized());
    }

    // Usuário autenticado sem role necessária (exemplo ROLE_ADMIN) tenta acessar
    @Test
    @WithMockUser(username = "otheruser", roles = {"GUEST"})
    void whenUserWithInsufficientRoleAccessTasks_thenForbidden() throws Exception {
        mockMvc.perform(get("/tasks"))
            .andExpect(status().isForbidden());
    }
}
