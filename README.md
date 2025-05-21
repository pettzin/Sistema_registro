# 🎓 EducaClass

**EducaClass** é um sistema de gerenciamento de matrículas escolares desenvolvido em Java utilizando o NetBeans. A aplicação segue o padrão de arquitetura MVC e utiliza `Swing` para a construção da interface gráfica.

## 📌 Descrição

O sistema foi criado para facilitar o gerenciamento de **alunos**, **professores**, **cursos** e **turmas** em uma instituição de ensino. Com funcionalidades completas de cadastro, matrícula e relatórios, o sistema também oferece validações, tratamento de erros e uma interface interativa.

## 🚀 Tecnologias Utilizadas

- Java (JDK)
- NetBeans IDE
- Swing (`javax.swing.*`)
- AWT (`java.awt.*`)
- JDBC (MySQL)
- Arquitetura MVC
- MySQL Workbench

## 🧩 Funcionalidades Principais

- **CRUD Completo** para Alunos, Turmas, Cursos e Professores
- **Autenticação** com validação de informações matriculadas e tratamento de erros
- **Relatórios**:
  - Alunos por turma
  - Quantidade de vagas
  - Professor responsável pelo curso
- **Busca avançadas**
- **Associação de professor à curso**
- **Associação de curso à turma**
- **Interface gráfica (Swing)**
- **Validações de campos e dados**

## 🧑‍💻 Contribuidores

- [Matheus Domeneghetti](https://github.com/Sarito333)
- [Natan Gleison](https://github.com/Natan-gleison)
- [Petterson Machado](https://github.com/pettzin)
- [Renan Alves](https://github.com/Renan01032) 
- [Veronica Neves](https://github.com/VeehNB)

## 📂 Estrutura de Dados

Algumas entidades do sistema:

- **Aluno**: matrícula, nome, CPF, gênero, e-mail, telefone, endereço  
- **Professor**: ID, nome, CPF, e-mail, telefone  
- **Curso**: ID, nome, descrição  
- **Turma**: código, nome, período, capacidade, data de início e fim  
- **Matrícula**: ID, data da matrícula

## ⚙️ Configuração do Banco de Dados

Para conectar-se corretamente:

Coloque o arquivo DBConnection.java na pasta connection do projeto.

Esse arquivo gerencia a conexão com o banco de dados.

Abra o arquivo env_texto.txt (fornecido no projeto).

Ele contém os dados necessários para configurar a conexão (host, nome do banco, usuário e senha) no MySQL Workbench.


⚠️ O arquivo DBConnection.java não está incluso no repositório por motivos de segurança.
⚠️ O banco de dados já está criado e operacional — basta configurar a conexão.

▶️ Como Executar
Copie o arquivo DBConnection.java para a pasta connection.

Insira os dados de acesso do banco conforme o env_texto.txt.

Abra o projeto no NetBeans OU VSCode.

Execute a classe principal (Main) para iniciar a aplicação

---

> Este projeto foi desenvolvido como parte de um trabalho acadêmico e visa demonstrar o uso de boas práticas em Java, incluindo o uso de Swing, organização em camadas (MVC), e interação com banco de dados.
