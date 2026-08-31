# Sistema de Gestão de Manutenção | S.G.M

Sistema para controle de ordens de manutenção de equipamentos industriais, permitindo o cadastro de equipamentos, técnicos responsáveis e o acompanhamento do ciclo de vida de cada ordem de manutenção (aberta, em andamento e concluída).

## 📋 Sobre o Projeto

O objetivo do sistema é resolver um problema comum em ambientes industriais: a falta de controle centralizado sobre o estado dos equipamentos e o histórico de manutenções realizadas. Com essa aplicação, é possível:

- Cadastrar e consultar equipamentos
- Cadastrar técnicos responsáveis pelas manutenções
- Abrir, acompanhar e encerrar ordens de manutenção
- Consultar o histórico de status de cada ordem
- Garantir que um equipamento não tenha mais de uma ordem de manutenção aberta simultaneamente

## 🚀 Tecnologias Utilizadas

**Back-end**
- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security + JWT (autenticação)
- Bean Validation

**Banco de Dados**
- PostgreSQL

**Documentação de API**
- Swagger / springdoc-openapi

**Front-end**
- HTML5
- CSS3
- JavaScript (Fetch API)

**Testes**
- JUnit 5
- Mockito

**Outras Ferramentas**
- Git / GitHub (versionamento)
- DBeaver (administração do banco)
- Postman (testes de API)
- Trello (gestão ágil das tarefas)

## 🗂️ Modelagem do Banco de Dados

O sistema é composto pelas seguintes entidades principais:

- **Equipamento**: dados do equipamento e seu status atual
- **Técnico**: responsável pela execução das manutenções
- **Ordem de Manutenção**: registro de uma solicitação de manutenção, vinculada a um equipamento e a um técnico
- **Histórico de Status**: registro de cada mudança de status ocorrida em uma ordem de manutenção

> O diagrama entidade-relacionamento (DER) completo está disponível em `/docs/der.png`.

## 🔧 Funcionalidades

- [x] CRUD de Equipamentos
- [x] CRUD de Técnicos
- [x] Abertura de Ordens de Manutenção
- [x] Transição de status (Aberta → Em andamento → Concluída)
- [x] Bloqueio de múltiplas ordens abertas para o mesmo equipamento
- [x] Histórico de mudanças de status
- [x] Autenticação via JWT
- [x] Documentação automática dos endpoints via Swagger

## ▶️ Como Executar o Projeto

### Pré-requisitos
- JDK 17 ou superior
- PostgreSQL instalado e em execução
- Maven

### Passos

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/nome-do-repositorio.git

# Acesse a pasta do projeto
cd nome-do-repositorio

# Configure as variáveis de ambiente do banco de dados
# (veja o arquivo application.properties.example)

# Execute a aplicação
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

A documentação interativa da API pode ser acessada em:
```
http://localhost:8080/swagger-ui.html
```

### Executando os testes

```bash
./mvnw test
```

## 🖥️ Front-end

O front-end é uma interface simples em HTML, CSS e JavaScript, localizada na pasta `/frontend`, que consome a API para listar equipamentos e gerenciar ordens de manutenção. Para utilizá-lo, basta abrir o arquivo `index.html` em um navegador com o back-end em execução.

## 📌 Estrutura do Projeto

```
├── src/
│   ├── main/
│   │   ├── java/          # Código-fonte da aplicação
│   │   └── resources/     # Configurações e application.properties
│   └── test/               # Testes unitários
├── frontend/                # Interface web (HTML, CSS, JS)
├── docs/                     # Diagramas e documentação técnica
└── README.md
```

## 📄 Licença

Este projeto está sob a licença MIT. Sinta-se à vontade para utilizá-lo como referência de estudo.

## 👤 Autor

Desenvolvido por Victor Manoel Soares Silva Alves.
