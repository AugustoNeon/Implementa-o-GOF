# Registro de uso de LLM

Resumo dos principais pedidos feitos ao modelo e do que a equipe aceitou, corrigiu ou descartou.

- **Ferramenta:** Claude Code.
- **Modelo:** Claude Sonnet 5.5.

## Principais pedidos

1. Auditoria do código.
2. Validação dos testes.
3. Documentação completa (README, tabela de rastreabilidade e demais textos).
4. Explicação dos padrões GoF e de como aplicá-los em cada requisito.
5. Guia de plano de estruturação para a montagem do projeto.
6. Montagem da interface de linha de comando.

## O que a equipe aceitou

- A sugestão de organização das pastas e pacotes.
- As correções sugeridas para o código.
- O guia de plano de estruturação para a montagem.
- A implementação, pelo modelo, de partes específicas: o rollback da fachada de circulação (`FachadaDeCirculacao`), o decorador de cache da consulta (`ConsultaComCache`) e os testes de cenário (`R01_...` a `R10_...`).

## O que a equipe descartou

- Métodos mais avançados: a equipe se manteve em um nível básico a intermediário para facilitar a leitura de partes mais complexas.
- A ideia de interface web sugerida pelo modelo: a interface ficou em linha de comando.
- A intervenção do modelo em arquivos mais simples, como as classes que são apenas dados assim como classes com variações(tipo a multa).

## O que a equipe fez com orientação do modelo

- `ExecutorDeSolicitacoes` (padrão Command): o modelo orientou a lógica e respondeu dúvidas. A implementação foi feita pela equipe.
- `FachadaDeCirculacao` (padrão Facade): o modelo orientou a lógica e respondeu dúvidas. A implementação foi feita pela equipe.
- `GrupoDoAcervo` (padrão Composite): o modelo orientou a lógica e respondeu dúvidas. A implementação foi feita pela equipe.
- `RepositorioMovimentacaoRelacional` (família relacional do Abstract Factory): o modelo orientou a lógica e respondeu dúvidas. A implementação foi feita pela equipe.
