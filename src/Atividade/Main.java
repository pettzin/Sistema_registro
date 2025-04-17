package Atividade;


import java.util.Scanner;

public class Main {
 public static void main(String[] args) {
     Registro sistema = new Registro();
     Scanner sc = new Scanner(System.in);
     int opcao;

     do {
         System.out.println("\n--- Sistema de Registro ---");
         System.out.println("1. Cadastrar Turma");
         System.out.println("2. Alterar Turma");
         System.out.println("3. Excluir Turma");
         System.out.println("4. Listar Turmas");
         System.out.println("5. Cadastrar Aluno");
         System.out.println("6. Alterar Aluno");
         System.out.println("7. Excluir Aluno");
         System.out.println("8. Listar Alunos de uma Turma");
         System.out.println("0. Sair");
         System.out.print("Escolha: ");
         opcao = sc.nextInt(); sc.nextLine();

         switch (opcao) {
             case 1 -> {
                 System.out.print("Nome da turma: ");
                 String nome = sc.nextLine();
                 System.out.print("Código da turma: ");
                 String codigo = sc.nextLine();
                 sistema.cadastrarTurma(nome, codigo);
             }
             case 2 -> {
                 System.out.print("Código da turma: ");
                 String codigo = sc.nextLine();
                 System.out.print("Novo nome: ");
                 String novoNome = sc.nextLine();
                 sistema.alterarTurma(codigo, novoNome);
             }
             case 3 -> {
                 System.out.print("Código da turma: ");
                 String codigo = sc.nextLine();
                 sistema.excluirTurma(codigo);
             }
             case 4 -> sistema.listarTurmas();
             case 5 -> {
                 System.out.print("Código da turma: ");
                 String cod = sc.nextLine();
                 System.out.print("Nome do aluno: ");
                 String nome = sc.nextLine();
                 System.out.print("Matrícula do aluno: ");
                 String matricula = sc.nextLine();
                 sistema.cadastrarAluno(cod, nome, matricula);
             }
             case 6 -> {
                 System.out.print("Código da turma: ");
                 String cod = sc.nextLine();
                 System.out.print("Matrícula do aluno: ");
                 String matricula = sc.nextLine();
                 System.out.print("Novo nome do aluno: ");
                 String novoNome = sc.nextLine();
                 sistema.alterarAluno(cod, matricula, novoNome);
             }
             case 7 -> {
                 System.out.print("Código da turma: ");
                 String cod = sc.nextLine();
                 System.out.print("Matrícula do aluno: ");
                 String matricula = sc.nextLine();
                 sistema.excluirAluno(cod, matricula);
             }
             case 8 -> {
                 System.out.print("Código da turma: ");
                 String cod = sc.nextLine();
                 sistema.listarAlunos(cod);
             }
             case 0 -> System.out.println("Encerrando...");
             default -> System.out.println("Opção inválida.");
         }

     } while (opcao != 0);

     sc.close();
 }
}
