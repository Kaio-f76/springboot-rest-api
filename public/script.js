const API = 'http://localhost:8080';

// Utilitários de token
function salvarToken(token) {
    localStorage.setItem('token', token);
}

function obterToken() {
    return localStorage.getItem('token');
}

function logout() {
    localStorage.removeItem('token');
    window.location.href = 'index.html';
}

document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;

            try {
                const res = await fetch(`${API}/auth/login`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ username, password })
                });

                if (!res.ok) throw new Error('Login inválido');

                const data = await res.json();
                salvarToken(data.token);
                window.location.href = 'tasks.html';
            } catch (err) {
                document.getElementById('errorMsg').textContent = err.message;
            }
        });
    }

    const taskList = document.getElementById('taskList');
    const taskForm = document.getElementById('taskForm');
    const formTitle = document.getElementById('formTitle');
    const taskIdInput = document.getElementById('taskId');

    if (taskList) {
        if (!obterToken()) {
            alert("Você precisa estar logado.");
            window.location.href = 'index.html';
            return;
        }

        function carregarTarefas() {
            fetch(`${API}/tasks`, {
                headers: { 'Authorization': `Bearer ${obterToken()}` }
            })
                .then(res => res.json())
                .then(tasks => {
                    taskList.innerHTML = '';
                    tasks.forEach(task => renderCard(task));
                })
                .catch(() => alert('Erro ao carregar tarefas'));
        }

        function renderCard(task) {
            const card = document.createElement('div');
            card.className = 'col-md-4';
            card.innerHTML = `
                <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title">${task.nome}</h5>
                    <p class="card-text">${task.descricao}</p>
                    <span class="badge bg-${task.status === 'CONCLUIDA' ? 'success' : 'warning'}">${task.status}</span>
                </div>
                <div class="card-footer d-flex justify-content-between">
                    <button class="btn btn-sm btn-primary" onclick="editarTarefa('${task.id}')">Editar</button>
                    <button class="btn btn-sm btn-danger" onclick="excluirTarefa('${task.id}')">Excluir</button>
                </div>
                </div>
            `;
            taskList.appendChild(card);
        }


        taskForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const id = taskIdInput.value;
            const nome = document.getElementById('nome').value;
            const descricao = document.getElementById('descricao').value;
            const status = document.getElementById('status').value;

            const payload = { nome, descricao, status };
            const method = id ? 'PUT' : 'POST';
            const url = id ? `${API}/tasks/${id}` : `${API}/tasks`;

            try {
                const res = await fetch(url, {
                    method,
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${obterToken()}`
                    },
                    body: JSON.stringify(payload)
                });

                if (!res.ok) throw new Error('Erro ao salvar tarefa');
                taskForm.reset();
                taskIdInput.value = '';
                formTitle.textContent = 'Criar Nova Tarefa';
                carregarTarefas();
            } catch (err) {
                alert(err.message);
            }
        });

        // Funções globais
        window.excluirTarefa = function (id) {
            fetch(`${API}/tasks/${id}`, {
                method: 'DELETE',
                headers: { 'Authorization': `Bearer ${obterToken()}` }
            })
                .then(res => {
                    if (!res.ok) throw new Error('Erro ao excluir');
                    carregarTarefas();
                })
                .catch(err => alert(err.message));
        };

        window.editarTarefa = function (id) {
            fetch(`${API}/tasks/${id}`, {
                headers: { 'Authorization': `Bearer ${obterToken()}` }
            })
                .then(res => {
                    if (!res.ok) throw new Error('Erro ao buscar tarefa');
                    return res.json();
                })
                .then(task => {
                    taskIdInput.value = task.id;
                    document.getElementById('nome').value = task.nome;
                    document.getElementById('descricao').value = task.descricao;
                    document.getElementById('status').value = task.status;
                    formTitle.textContent = 'Editar Tarefa';
                })
                .catch(err => alert(err.message));
        };

        window.cancelarEdicao = function () {
            taskForm.reset();
            taskIdInput.value = '';
            formTitle.textContent = 'Criar Nova Tarefa';
        };

        window.filtrarPorStatus = function () {
            const status = document.getElementById('filtroStatus').value;
            if (!status) return carregarTarefas(); // Se nenhum status for selecionado, carrega todas

            fetch(`${API}/tasks/status/${status}`, {
                headers: { 'Authorization': `Bearer ${obterToken()}` }
            })
                .then(res => {
                    if (!res.ok) throw new Error('Erro ao filtrar tarefas');
                    return res.json();
                })
                .then(tasks => {
                    taskList.innerHTML = '';
                    tasks.forEach(renderCard);
                })
                .catch(err => alert(err.message));
        };


        carregarTarefas();
    }
});
