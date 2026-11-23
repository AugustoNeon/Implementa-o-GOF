# Uso de LLM

Declaração do uso de modelos de linguagem (LLM) no desenvolvimento do projeto **biblioteca-gof**.

## Ferramenta utilizada

- **Claude Code**.

## Extensão do uso

O LLM foi utilizado ao longo da construção do projeto, e não em uma etapa isolada.

## Partes implementadas pelo LLM

- **Rollback na fachada de circulação** (`FachadaDeCirculacao`): o desfazer das operações quando a gravação falha, com a restauração do estado do livro e das reservas e empréstimos.
- **Decorador de cache da consulta** (`ConsultaComCache`, padrão Decorator).
- **Testes de cenário** (`src/test/java/biblioteca/`, nomeados `R01_...` a `R10_...`).

## Partes com orientação do LLM e implementação própria

- **Executor de solicitações** (`ExecutorDeSolicitacoes`, padrão Command): o LLM orientou a lógica e respondeu duvidas. A implementação foi feita por nós com a ajuda dela.

## Uso geral ao longo do projeto

- Explicação dos padrões GoF e de como aplicá-los em cada requisito.
- Aplicação dos padrões no código.
- Arquitetura e organização das pastas e pacotes.
- Documentação (README e demais textos).
- Testes.
