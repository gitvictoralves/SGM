# Roadmap — SGM (Sistema de Gestão de Manutenção de Equipamentos)

> Duração estimada: 6 semanas, com ritmo de 1-2h de dedicação por dia.
> Acompanhamento das tarefas via quadro Kanban (Trello / GitHub Projects).

---

## Semana 1 — Fundamentos e Modelagem

**Objetivo:** preparar o ambiente e definir a estrutura de dados do sistema.

- [ ] Instalar IntelliJ IDEA, JDK 17+, PostgreSQL, DBeaver e Git
- [ ] Criar repositório no GitHub com README inicial
- [ ] Modelar o banco de dados (DER): Equipamento, Técnico, Ordem de Manutenção, Histórico de Status
- [ ] Criar o projeto Spring Boot via [start.spring.io](https://start.spring.io) com as dependências: Web, JPA, PostgreSQL Driver, Validation
- [ ] Criar quadro Kanban com colunas: Backlog / Fazendo / Testando / Concluído
- [ ] Quebrar o projeto em tarefas pequenas dentro do quadro

---

## Semana 2 — CRUD Básico

**Objetivo:** implementar as operações fundamentais de cadastro.

- [ ] Criar as entidades JPA: Equipamento, Técnico, Ordem de Manutenção
- [ ] Criar Repositories, Services e Controllers para CRUD de Equipamento
- [ ] Criar Repositories, Services e Controllers para CRUD de Técnico
- [ ] Testar todos os endpoints no Postman
- [ ] Realizar commits pequenos e frequentes, com mensagens claras

---

## Semana 3 — Regras de Negócio

**Objetivo:** implementar a lógica central do sistema.

- [ ] Implementar o fluxo de status da Ordem de Manutenção (Aberta → Em andamento → Concluída)
- [ ] Bloquear a criação de uma nova ordem se já existir uma aberta para o mesmo equipamento
- [ ] Criar o registro de histórico a cada mudança de status
- [ ] Implementar tratamento de erros com `@ExceptionHandler` e retornos HTTP adequados (400, 404, 500)

---

## Semana 4 — Segurança e Documentação Técnica

**Objetivo:** proteger a API e documentar a arquitetura.

- [ ] Implementar autenticação com Spring Security + JWT
- [ ] Integrar Swagger (springdoc-openapi) para documentação automática dos endpoints
- [ ] Escrever a documentação técnica do projeto (arquitetura, decisões de design, diagrama do banco)

---

## Semana 5 — Front-end e Testes

**Objetivo:** criar a interface e garantir a qualidade do código.

- [ ] Criar tela de listagem de equipamentos (HTML/CSS/JS)
- [ ] Criar tela de abertura e encerramento de ordens de manutenção
- [ ] Integrar o front-end com a API via `fetch`
- [ ] Escrever de 4 a 6 testes unitários com JUnit e Mockito nas principais regras de negócio
- [ ] Atualizar o README com instruções de uso, prints de tela e link do Swagger

---

## Semana 6 — Deploy e Preparação Final

**Objetivo:** disponibilizar o projeto publicamente e revisar o material de apresentação.

- [ ] Subir o banco de dados em um serviço gratuito (Neon ou Supabase)
- [ ] Realizar o deploy do back-end (Render ou similar)
- [ ] Testar o funcionamento completo em produção
- [ ] Revisar o quadro Kanban para documentar a evolução do desenvolvimento
- [ ] Preparar um roteiro de apresentação do projeto (problema → solução → tecnologias → desafios enfrentados)

---

## Marcos do Projeto

| Marco | Semana | Entregável |
|---|---|---|
| Ambiente configurado | 1 | Repositório criado, banco modelado |
| CRUD funcional | 2 | Endpoints de Equipamento e Técnico testados |
| Regras de negócio implementadas | 3 | Fluxo de status funcionando |
| API segura e documentada | 4 | JWT + Swagger ativos |
| Sistema completo | 5 | Front-end integrado e testes passando |
| Projeto publicado | 6 | Aplicação em produção e pronta para apresentação |