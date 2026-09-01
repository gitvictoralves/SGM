# Estrutura do Front-end — SGM

Este documento descreve as páginas previstas para a interface web do sistema, suas funções e o fluxo de navegação entre elas. O front-end é simples (HTML, CSS e JavaScript puro), com foco em consumir a API e permitir a operação básica do sistema — sem necessidade de frameworks.

---

## 🗺️ Visão Geral da Navegação

```
Login
  └── Dashboard (Home)
        ├── Equipamentos
        │     ├── Listagem de Equipamentos
        │     ├── Cadastro/Edição de Equipamento
        │     └── Detalhes do Equipamento (com histórico)
        ├── Técnicos
        │     ├── Listagem de Técnicos
        │     └── Cadastro/Edição de Técnico
        └── Ordens de Manutenção
              ├── Listagem de Ordens
              ├── Abertura de Nova Ordem
              └── Detalhes da Ordem (com histórico de status)
```

---

## 📄 Páginas Previstas

### 1. `login.html` — Login

**Função:** autenticar o usuário no sistema antes de liberar o acesso às demais páginas.

- Campos: usuário/e-mail e senha
- Botão "Entrar"
- Ao autenticar com sucesso, a API retorna um token JWT, que é armazenado (em memória/variável de sessão) e enviado nas próximas requisições
- Exibição de mensagem de erro em caso de credenciais inválidas

---

### 2. `dashboard.html` — Painel Inicial

**Função:** servir como tela inicial após o login, com uma visão geral do sistema.

- Cards com indicadores simples: total de equipamentos, ordens abertas, ordens em andamento, ordens concluídas
- Atalhos rápidos para "Nova Ordem de Manutenção" e "Novo Equipamento"
- Menu de navegação lateral ou superior, presente em todas as páginas internas

---

### 3. `equipamentos.html` — Listagem de Equipamentos

**Função:** exibir todos os equipamentos cadastrados, com opção de busca e acesso rápido às ações.

- Tabela com colunas: nome, código/identificação, status atual (Ativo, Em manutenção, Inativo), última manutenção
- Campo de busca/filtro por nome ou status
- Botão "Novo Equipamento"
- Ação por linha: "Ver detalhes", "Editar", "Excluir"

---

### 4. `equipamento-form.html` — Cadastro/Edição de Equipamento

**Função:** criar um novo equipamento ou editar um já existente (mesma página reaproveitada para os dois casos).

- Campos: nome, código, descrição, status inicial
- Botões "Salvar" e "Cancelar"
- Validação de campos obrigatórios antes do envio à API

---

### 5. `equipamento-detalhes.html` — Detalhes do Equipamento

**Função:** exibir as informações completas de um equipamento e seu histórico de manutenções.

- Dados cadastrais do equipamento
- Lista das ordens de manutenção associadas (passadas e atuais), com status e datas
- Botão "Abrir Nova Ordem de Manutenção" (caso não haja ordem aberta para o equipamento)

---

### 6. `tecnicos.html` — Listagem de Técnicos

**Função:** exibir os técnicos cadastrados no sistema.

- Tabela com colunas: nome, especialidade, quantidade de ordens em andamento
- Botão "Novo Técnico"
- Ação por linha: "Editar", "Excluir"

---

### 7. `tecnico-form.html` — Cadastro/Edição de Técnico

**Função:** criar ou editar os dados de um técnico responsável pelas manutenções.

- Campos: nome, especialidade/área, contato
- Botões "Salvar" e "Cancelar"

---

### 8. `ordens.html` — Listagem de Ordens de Manutenção

**Função:** exibir todas as ordens de manutenção cadastradas, com filtro por status.

- Tabela com colunas: equipamento, técnico responsável, status, data de abertura
- Filtros: status (Aberta, Em andamento, Concluída) e período
- Botão "Nova Ordem de Manutenção"
- Ação por linha: "Ver detalhes", "Atualizar status"

---

### 9. `ordem-form.html` — Abertura de Nova Ordem de Manutenção

**Função:** registrar uma nova solicitação de manutenção para um equipamento.

- Campos: seleção do equipamento, seleção do técnico responsável, descrição do problema
- Validação: impedir abertura se já existir uma ordem aberta para o equipamento selecionado (regra de negócio vinda da API)
- Botão "Abrir Ordem"

---

### 10. `ordem-detalhes.html` — Detalhes da Ordem de Manutenção

**Função:** exibir todas as informações de uma ordem específica e permitir a atualização do seu status.

- Dados da ordem: equipamento, técnico, descrição, status atual, datas
- Linha do tempo com o histórico de mudanças de status
- Botões de ação para avançar o status (ex: "Iniciar Atendimento", "Concluir Ordem")

---

## 🧩 Componentes Reutilizáveis

Para evitar repetição de código entre as páginas, alguns elementos devem ser compartilhados:

| Componente | Onde é usado | Função |
|---|---|---|
| Menu de navegação | Todas as páginas internas | Acesso rápido entre Equipamentos, Técnicos e Ordens |
| Tabela genérica | Listagens | Estrutura reaproveitável para exibir dados com ordenação/filtro |
| Modal de confirmação | Exclusões | Confirmar ação antes de excluir um registro |
| Badge de status | Ordens e Equipamentos | Exibição colorida do status (ex: verde = concluída, amarelo = em andamento, vermelho = aberta) |
| Formulário genérico | Cadastros/Edições | Estrutura de campos e validação reaproveitada |

---

## 🔗 Integração com a API

Cada página consome os endpoints REST do back-end via `fetch`, seguindo o padrão:

| Página | Endpoint(s) consumidos |
|---|---|
| Login | `POST /auth/login` |
| Equipamentos (listagem) | `GET /equipamentos` |
| Equipamento (form) | `POST /equipamentos`, `PUT /equipamentos/{id}` |
| Equipamento (detalhes) | `GET /equipamentos/{id}`, `GET /equipamentos/{id}/historico` |
| Técnicos (listagem) | `GET /tecnicos` |
| Técnico (form) | `POST /tecnicos`, `PUT /tecnicos/{id}` |
| Ordens (listagem) | `GET /ordens` |
| Ordem (form) | `POST /ordens` |
| Ordem (detalhes) | `GET /ordens/{id}`, `PATCH /ordens/{id}/status` |

---

## 📌 Observações

- Todas as páginas internas (exceto o login) devem verificar a presença do token JWT antes de carregar os dados, redirecionando para `login.html` caso o usuário não esteja autenticado.
- O design pode ser bem simples nesta primeira versão — o foco do projeto é a lógica de back-end e a integração via API, não a estética da interface.
- Essa estrutura pode ser reduzida caso o prazo aperte: nesse caso, priorize `login`, `equipamentos`, `ordens` e `ordem-form`, que cobrem o fluxo principal do sistema.