# 🎓 EducaClass

**EducaClass** é um sistema de gerenciamento de matrículas escolares desenvolvido em Java utilizando o NetBeans. A aplicação segue o padrão de arquitetura MVC e utiliza `Swing` para a construção da interface gráfica.

## 📌 Descrição

O sistema foi criado para facilitar o gerenciamento de **alunos**, **professores**, **cursos** e **turmas** em uma instituição de ensino. Com funcionalidades completas de cadastro, matrícula e relatórios, o sistema também oferece validações, tratamento de erros e uma interface interativa.

## 🚀 Tecnologias Utilizadas

- Java (JDK)
- NetBeans IDE
- Swing (`javax.swing.*`)
- AWT (`java.awt.*`)
- JDBC (com padrão Singleton para conexão com banco)
- Arquitetura MVC

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

## ▶️ Como Executar

Para iniciar o projeto, basta abrir o projeto no **NetBeans** e executar a classe principal (`Main`). O sistema já está pronto para uso, com interface gráfica e menus interativos.

---

> Este projeto foi desenvolvido como parte de um trabalho acadêmico e visa demonstrar o uso de boas práticas em Java, incluindo o uso de Swing, organização em camadas (MVC), e interação com banco de dados.
