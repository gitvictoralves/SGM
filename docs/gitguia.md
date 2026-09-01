# Guia de Comandos Git — SGM

Guia rápido com comandos prontos para copiar, colar e adaptar durante o desenvolvimento do projeto.

---

## 🔧 Configuração Inicial (fazer uma única vez)

```bash
git config --global user.name "Seu Nome"
git config --global user.email "seu-email@exemplo.com"
```

## 📥 Clonar e Iniciar o Projeto

```bash
# Clonar o repositório
git clone https://github.com/seu-usuario/sgm.git
cd sgm

# Ver o status atual
git status

# Ver o histórico de commits
git log --oneline
```

---

## 🌿 Trabalhando com Branches

### Convenção de nomes de branch

| Tipo | Prefixo | Exemplo |
|---|---|---|
| Nova funcionalidade | `feature/` | `feature/cadastro-equipamento` |
| Correção de bug | `fix/` | `fix/erro-validacao-ordem` |
| Ajuste/melhoria | `chore/` | `chore/atualizar-dependencias` |
| Documentação | `docs/` | `docs/atualizar-readme` |
| Testes | `test/` | `test/servico-ordem-manutencao` |

### Criar uma branch nova

```bash
# Sempre parta da main atualizada
git checkout main
git pull origin main

# Criar e mudar para a nova branch
git checkout -b feature/nome-da-funcionalidade
```

### Trocar de branch

```bash
git checkout nome-da-branch
```

### Listar branches

```bash
# Locais
git branch

# Locais e remotas
git branch -a
```

### Apagar uma branch (depois que já foi mergeada)

```bash
# Local
git branch -d feature/nome-da-funcionalidade

# Remota
git push origin --delete feature/nome-da-funcionalidade
```

---

## 💾 Commits

### Fluxo básico

```bash
# Ver o que foi alterado
git status

# Adicionar arquivos específicos
git add src/main/java/com/sgm/EquipamentoController.java

# Ou adicionar tudo
git add .

# Fazer o commit
git commit -m "feat: adiciona endpoint de cadastro de equipamento"

# Enviar para o repositório remoto
git push origin feature/nome-da-funcionalidade
```

### Convenção de mensagens de commit (Conventional Commits)

| Prefixo | Quando usar | Exemplo |
|---|---|---|
| `feat:` | Nova funcionalidade | `feat: adiciona CRUD de técnico` |
| `fix:` | Correção de bug | `fix: corrige validação de status da ordem` |
| `docs:` | Alteração em documentação | `docs: atualiza instruções de instalação no README` |
| `style:` | Formatação, sem mudança de lógica | `style: ajusta indentação do controller` |
| `refactor:` | Refatoração sem mudar comportamento | `refactor: extrai lógica de validação para service` |
| `test:` | Adição ou ajuste de testes | `test: adiciona teste unitário para OrdemService` |
| `chore:` | Tarefas de manutenção (deps, configs) | `chore: atualiza versão do Spring Boot` |

### Exemplos prontos de commits comuns no projeto

```bash
git commit -m "feat: cria entidade Equipamento com atributos básicos"
git commit -m "feat: implementa endpoint POST /equipamentos"
git commit -m "feat: adiciona regra de bloqueio de ordem duplicada"
git commit -m "fix: corrige status que não atualizava no histórico"
git commit -m "fix: trata erro 404 quando equipamento não existe"
git commit -m "test: adiciona teste para regra de ordem duplicada"
git commit -m "docs: adiciona instruções de execução no README"
git commit -m "chore: adiciona springdoc-openapi para documentação da API"
```

---

## 🔄 Mantendo a Branch Atualizada

```bash
# Estando na sua branch de feature
git checkout feature/nome-da-funcionalidade

# Buscar atualizações da main
git fetch origin

# Trazer as mudanças da main para dentro da sua branch
git merge origin/main
```

> Se preferir manter o histórico mais limpo, pode usar `git rebase origin/main` no lugar do `merge` — mas cuidado ao usar rebase em branches já compartilhadas com outras pessoas.

---

## 🔀 Enviando para Revisão (Pull Request)

```bash
# Garantir que está tudo commitado
git status

# Enviar a branch para o GitHub
git push origin feature/nome-da-funcionalidade
```

Depois disso, abra o Pull Request pelo GitHub, comparando `feature/nome-da-funcionalidade` → `main`.

---

## ⏪ Desfazendo Coisas (com cuidado)

```bash
# Desfazer alterações não commitadas em um arquivo
git checkout -- nome-do-arquivo

# Remover um arquivo da área de staging (antes do commit)
git reset nome-do-arquivo

# Desfazer o último commit, mantendo as alterações nos arquivos
git reset --soft HEAD~1

# Desfazer o último commit e as alterações (cuidado, é destrutivo)
git reset --hard HEAD~1

# Reverter um commit específico já enviado (cria um novo commit de reversão)
git revert <hash-do-commit>
```

---

## 🏷️ Tags de Versão (opcional, útil ao fazer deploy)

```bash
# Criar uma tag
git tag -a v1.0.0 -m "Primeira versão funcional do SGM"

# Enviar a tag para o repositório remoto
git push origin v1.0.0
```

---

## 📝 Fluxo Resumido do Dia a Dia

```bash
git checkout main
git pull origin main
git checkout -b feature/nova-funcionalidade

# ... codar ...

git add .
git commit -m "feat: descreve o que foi feito"
git push origin feature/nova-funcionalidade

# Abrir Pull Request no GitHub
# Após aprovação e merge:

git checkout main
git pull origin main
git branch -d feature/nova-funcionalidade
```