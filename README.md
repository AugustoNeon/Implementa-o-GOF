# biblioteca-gof

Sistema de empréstimo de livros com padrões GoF.


## O que precisa ter instalado

- Java 17 ou superior.
- MySQL 8 ligado (só para o perfil de produção e para os testes da família relacional).
- Maven não precisa: o projeto usa o Maven Wrapper (`mvnw`), que baixa o Maven na primeira execução.

## Preparar o MySQL (uma vez)

Rodar como `root` os dois scripts da pasta `banco/`:

```
source banco/00_criar_banco_e_usuario.sql
use biblioteca;
source banco/01_criar_tabelas.sql
```

O usuário e a senha usados pelo sistema estão em `Configuracao.java`.

## Compilar e rodar os testes

Windows:

```
.\mvnw.cmd test
```

Linux, macOS ou Git Bash:

```
./mvnw test
```

Os testes de `RelacionalTest` apagam o conteúdo das tabelas do banco `biblioteca`.

## Executar

```
./mvnw -q compile exec:java "-Dexec.args=MODO PERFIL BANCO"
```

| Argumento | Valores | Padrão |
|---|---|---|
| MODO | `menu` (menu interativo) ou `demo` (fluxo completo automático) | `menu` |
| PERFIL | `teste` (memória) ou `producao` (MySQL) | `teste` |
| BANCO | `A` ou `B` (banco que emite o boleto da multa) | `A` |

Exemplos:

```
./mvnw -q compile exec:java "-Dexec.args=demo teste A"
./mvnw -q compile exec:java "-Dexec.args=demo producao B"
./mvnw -q compile exec:java "-Dexec.args=menu teste A"
```

O modo `demo` faz: cadastros, reserva, empréstimo, renovação (duas vezes e a terceira recusada),
devolução atrasada, multa, boleto, pagamento e inspeção.

## Pastas

```
banco/                          scripts SQL do MySQL
modelos/                        modelo UML no Astah (Trabalho_GoF.asta) e o PDF com os diagramas
src/main/java/biblioteca/
  dominio/       Livro, Pessoa, Estudante, Atendente, Reserva, Emprestimo, Devolucao, interfaces iLivro...
  estado/        State (requisito 1)
  multa/         Strategy (2)
  cobranca/      Adapter (3)
  circulacao/    Facade (4)
  devolucao/     Observer (5)
  solicitacao/   Command (6)
  cadastro/      Template Method (7)
  persistencia/  Abstract Factory (8), com as famílias memoria/ e relacional/
  consulta/      Decorator (9)
  hierarquia/    Composite (10)
  tratador/      Tratadores (a interface só chama eles)
  Sistema.java, Demonstracao.java, Main.java
src/test/java/biblioteca/        testes JUnit 5, um por cenário (R01_..., R02_...)
```

A tabela de rastreabilidade (requisito, padrão, classes e teste) está em [RASTREABILIDADE.md](RASTREABILIDADE.md).

## Observações

- As multas e a fila de inspeção ficam só em memória, porque a fábrica do requisito 8 cria apenas os repositórios de Livro, Movimentacao e Relacionamento.
- Os bancos A e B são classes simuladas, com APIs diferentes. O adaptador faz a conversão.
- O Oracle é réplica do MySQL e a aplicação não escreve nele.

## Uso de LLM

- **Ferramenta:** Claude Code.
- **Modelo:** Claude Sonnet 5.5.

Onde foi usado:

- **Código:** rollback da fachada de circulação (`FachadaDeCirculacao`) e decorador de cache da consulta (`ConsultaComCache`).
- **Orientação:** a lógica do `ExecutorDeSolicitacoes` (Command) foi orientada pelo modelo e implementada pela equipe.
- **Interface:** montagem da interface de linha de comando (`Main`).
- **Testes:** testes de cenário (`R01_...` a `R10_...`).
- **Documentação:** README, tabela de rastreabilidade e demais textos.
- **Uso geral:** explicação e aplicação dos padrões GoF e organização das pastas e pacotes.

Os principais pedidos feitos ao modelo e o que a equipe aceitou, corrigiu ou descartou estão em [REGISTRO_LLM.md](REGISTRO_LLM.md).

## Equipe

- Augusto Neon
- Rhyan Henrique
- Leticia Rosa
- Lorenzo Bot
