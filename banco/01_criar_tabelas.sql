
CREATE TABLE IF NOT EXISTS livro (
    id      VARCHAR(20)  PRIMARY KEY,
    titulo  VARCHAR(200) NOT NULL,
    estado  VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS pessoa (
    cpf        VARCHAR(14) PRIMARY KEY,
    nome       VARCHAR(100) NOT NULL,
    sobrenome  VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS estudante (
    matricula  VARCHAR(20) PRIMARY KEY,
    cpf        VARCHAR(14) NOT NULL
);

CREATE TABLE IF NOT EXISTS atendente (
    matricula_funcional  VARCHAR(20) PRIMARY KEY,
    cpf                  VARCHAR(14) NOT NULL
);

CREATE TABLE IF NOT EXISTS reserva (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    id_livro      VARCHAR(20) NOT NULL,
    id_estudante  VARCHAR(20) NOT NULL,
    data_reserva  DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS emprestimo (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    id_livro         VARCHAR(20) NOT NULL,
    id_estudante     VARCHAR(20) NOT NULL,
    data_emprestimo  DATE NOT NULL,
    data_vencimento  DATE NOT NULL,
    num_renovacoes   INT  NOT NULL
);

CREATE TABLE IF NOT EXISTS devolucao (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    id_livro       VARCHAR(20) NOT NULL,
    id_estudante   VARCHAR(20) NOT NULL,
    data_devolucao DATE NOT NULL
);
