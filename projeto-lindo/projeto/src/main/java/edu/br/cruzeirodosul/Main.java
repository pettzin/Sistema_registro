package edu.br.cruzeirodosul;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    // Códigos ANSI para cores
    private static final String RESET = "\033[0m";
    private static final String BLUE = "\033[0;34m";
    private static final String CYAN = "\033[0;36m";
    private static final String YELLOW = "\033[0;33m";
    private static final String GREEN = "\033[0;32m";
    private static final String PURPLE = "\033[0;35m";
    private static final String WHITE = "\033[0;37m";
    private static final String RED = "\033[0;31m";

    public static void mostrarLogo() {
        System.out.println(YELLOW + "\n   *    .  *       .       *       .   *      ." + RESET);
        System.out.println(CYAN + "  .         ____     .      .    `      .    *" + RESET);
        System.out.println(BLUE + "    *    . |    |    *   _________    .      ." + RESET);
        System.out.println(BLUE + "   .      -|    |-    . /         \\     .    *" + RESET);
        System.out.println(CYAN + "      *   .|____| .    |  " + PURPLE + "A C A D" + CYAN + "  |      ." + RESET);
        System.out.println(CYAN + "   .    *  /    \\    * |   " + PURPLE + "E M I" + CYAN + "   |  .     *" + RESET);
        System.out.println(BLUE + "      .   /      \\   .  \\         /      ." + RESET);
        System.out.println(BLUE + "   *    ./        \\.    \\_______/   *      ." + RESET);
        System.out.println(CYAN + "  .   *  `        `  *     .      .     *" + RESET);
        System.out.println(YELLOW + "      .     *    .      *      .     .      *" + RESET);
        System.out.println(GREEN + "\n  S I S T E M A   D E   G E S T Ã O   A C A D Ê M I C A" + RESET);
        System.out.println(WHITE + "  ==============================================" + RESET);
        System.out.println(YELLOW + "  Desenvolvido por CodeAstra" + RESET);
        System.out.println("\n" + CYAN + "  Pressione " + GREEN + "ENTER" + CYAN + " para continuar..." + RESET);
    }

    public static void limparConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            // Se ocorrer algum erro, simplesmente continua
            System.out.println("\n".repeat(50)); // Fallback para 50 linhas em branco
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Registro sistema = new Registro();
        
        // Mostra a logo inicial
        limparConsole();
        mostrarLogo();
        sc.nextLine(); // Aguarda ENTER
        
        int opcao;
        do {
            limparConsole();
            System.out.println("\n--- Sistema de Registro ---");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Cadastrar Turma");
            System.out.println("3. Cadastrar Período");
            System.out.println("4. Realizar Matrícula");
            System.out.println("5. Listar Alunos de uma Turma");
            System.out.println("6. Listar Turmas");
            System.out.println("7. Relatórios");
            System.out.println("8. Buscar Aluno");
            System.out.println("9. Buscar Turma");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            
            try {
                opcao = sc.nextInt();
                sc.nextLine(); // Limpa o buffer
            } catch (Exception e) {
                opcao = -1; // Opção inválida
                sc.nextLine(); // Limpa o buffer
            }

            limparConsole();
            switch (opcao) {
                case 1 -> { // Cadastrar Aluno
                    System.out.println(PURPLE + "\n--- CADASTRO DE ALUNO ---" + RESET);
                    System.out.print("Nome do aluno: ");
                    String nomeAluno = sc.nextLine();
                    System.out.print("Matrícula do aluno: ");
                    String matricula = sc.nextLine();
                    sistema.cadastrarAluno(nomeAluno, matricula);
                }
                case 2 -> { // Cadastrar Turma
                    System.out.println(BLUE + "\n--- CADASTRO DE TURMA ---" + RESET);
                    System.out.print("Nome da turma: ");
                    String nome = sc.nextLine();
                    System.out.print("Código da turma: ");
                    String codigo = sc.nextLine();
                    sistema.cadastrarTurma(nome, codigo);
                }
                case 3 -> { // Cadastrar Período
                    System.out.println(CYAN + "\n--- CADASTRO DE PERÍODO ---" + RESET);
                    System.out.print("Nome do período: ");
                    String nomePeriodo = sc.nextLine();
                    System.out.print("Código do período: ");
                    String codigoPeriodo = sc.nextLine();
                    sistema.cadastrarPeriodo(nomePeriodo, codigoPeriodo);
                }
                case 4 -> { // Realizar Matrícula
                    System.out.println(YELLOW + "\n--- MATRÍCULA DE ALUNO ---" + RESET);
                    System.out.print("Código da turma: ");
                    String codTurma = sc.nextLine();
                    System.out.print("Matrícula do aluno: ");
                    String mat = sc.nextLine();
                    sistema.matricularAluno(codTurma, mat);
                }
                case 5 -> { // Listar Alunos de uma Turma
                    System.out.println(WHITE + "\n--- ALUNOS DA TURMA ---" + RESET);
                    System.out.print("Código da turma: ");
                    String cod = sc.nextLine();
                    sistema.listarAlunos(cod);
                }
                case 6 -> { // Listar Turmas
                    System.out.println(GREEN + "\n--- LISTA DE TURMAS ---" + RESET);
                    sistema.listarTurmas();
                }
                case 7 -> { // Relatórios
                    System.out.println(BLUE + "\n--- RELATÓRIOS ---" + RESET);
                    sistema.gerarRelatorios();
                }
                case 8 -> { // Buscar Aluno
                    System.out.println(CYAN + "\n--- BUSCA DE ALUNO ---" + RESET);
                    System.out.print("Matrícula do aluno: ");
                    String mat = sc.nextLine();
                    sistema.buscarAluno(mat);
                }
                case 9 -> { // Buscar Turma
                    System.out.println(YELLOW + "\n--- BUSCA DE TURMA ---" + RESET);
                    System.out.print("Código da turma: ");
                    String cod = sc.nextLine();
                    sistema.buscarTurma(cod);
                }
                case 0 -> System.out.println(RED + "Encerrando sistema..." + RESET);
                default -> System.out.println(RED + "Opção inválida!" + RESET);
            }
            
            if (opcao != 0) {
                System.out.println("\n" + CYAN + "Pressione " + GREEN + "ENTER" + CYAN + " para continuar..." + RESET);
                sc.nextLine();
            }
        } while (opcao != 0);

        sc.close();
    }
}
}