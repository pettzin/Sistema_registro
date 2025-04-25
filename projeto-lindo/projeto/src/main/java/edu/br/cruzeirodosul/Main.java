package edu.br.cruzeirodosul;

import java.util.Scanner;

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
            System.out.println("6. Listar Todos os Alunos");
            System.out.println("7. Listar Turmas");
            System.out.println("8. Listar Períodos");
            System.out.println("9. Relatórios");
            System.out.println("10. Buscar Aluno por Matrícula");
            System.out.println("11. Buscar Aluno por CPF");
            System.out.println("12. Buscar Aluno por Nome");
            System.out.println("13. Buscar Turma por Código");
            System.out.println("14. Buscar Turma por Nome/Curso");
            System.out.println("15. Alterar Aluno");
            System.out.println("16. Alterar Turma");
            System.out.println("17. Excluir Aluno");
            System.out.println("18. Excluir Turma");
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
                    
                    System.out.print("CPF (apenas números): ");
                    String cpf = sc.nextLine();
                    
                    // Validação básica de CPF
                    if (!sistema.validarCpf(cpf)) {
                        System.out.println(RED + "CPF inválido! Por favor, verifique e tente novamente." + RESET);
                        break;
                    }
                    
                    System.out.print("Gênero (M/F/Outro): ");
                    String genero = sc.nextLine();
                    
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    
                    // Validação básica de email
                    if (!email.isEmpty() && !sistema.validarEmail(email)) {
                        System.out.println(RED + "Email inválido! Por favor, verifique e tente novamente." + RESET);
                        break;
                    }
                    
                    System.out.print("Telefone: ");
                    String telefone = sc.nextLine();
                    
                    sistema.cadastrarAluno(nomeAluno, cpf, genero, email, telefone);
                }
                case 2 -> { // Cadastrar Turma
                    System.out.println(BLUE + "\n--- CADASTRO DE TURMA ---" + RESET);
                    System.out.print("Nome da turma: ");
                    String nome = sc.nextLine();
                    System.out.print("Código da turma: ");
                    String codigo = sc.nextLine();
                    System.out.print("Curso: ");
                    String curso = sc.nextLine();
                    System.out.print("Período (Manhã/Tarde/Noite): ");
                    String periodo = sc.nextLine();
                    System.out.print("Capacidade máxima de alunos: ");
                    int capacidade = 40; // Valor padrão
                    try {
                        capacidade = Integer.parseInt(sc.nextLine());
                        if (capacidade <= 0) {
                            System.out.println(YELLOW + "Capacidade inválida! Usando valor padrão de 40." + RESET);
                            capacidade = 40;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(YELLOW + "Capacidade inválida! Usando valor padrão de 40." + RESET);
                    }
                    sistema.cadastrarTurma(nome, codigo, curso, periodo, capacidade);
                }
                case 3 -> { // Cadastrar Período
                    System.out.println(CYAN + "\n--- CADASTRO DE PERÍODO ---" + RESET);
                    System.out.print("Nome do período: ");
                    String nomePeriodo = sc.nextLine();
                    System.out.print("Código do período: ");
                    String codigoPeriodo = sc.nextLine();
                    System.out.print("Descrição: ");
                    String descricao = sc.nextLine();
                    System.out.print("Data de início (DD/MM/AAAA): ");
                    String dataInicio = sc.nextLine();
                    System.out.print("Data de fim (DD/MM/AAAA): ");
                    String dataFim = sc.nextLine();
                    sistema.cadastrarPeriodo(nomePeriodo, codigoPeriodo, descricao, dataInicio, dataFim);
                }
                case 4 -> { // Realizar Matrícula
                    System.out.println(YELLOW + "\n--- MATRÍCULA DE ALUNO ---" + RESET);
                    System.out.print("Código da turma: ");
                    String codTurma = sc.nextLine();
                    
                    // Verificar se a turma existe e mostrar vagas disponíveis
                    Turma turma = sistema.getTurmas().get(codTurma);
                    if (turma == null) {
                        System.out.println(RED + "Turma não encontrada." + RESET);
                        break;
                    }
                    
                    System.out.println(GREEN + "Turma: " + turma.getNome() + RESET);
                    System.out.println("Curso: " + turma.getCurso());
                    System.out.println("Período: " + turma.getPeriodo());
                    System.out.println("Vagas: " + turma.getAlunos().size() + "/" + turma.getCapacidadeMaxima());
                    System.out.println("Vagas disponíveis: " + turma.vagasDisponiveis());
                    
                    if (turma.estaCheia()) {
                        System.out.println(RED + "Esta turma está com todas as vagas preenchidas!" + RESET);
                        break;
                    }
                    
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
                case 6 -> { // Listar Todos os Alunos
                    System.out.println(PURPLE + "\n--- LISTA DE TODOS OS ALUNOS ---" + RESET);
                    sistema.listarAlunos();
                }
                case 7 -> { // Listar Turmas
                    System.out.println(GREEN + "\n--- LISTA DE TURMAS ---" + RESET);
                    sistema.listarTurmas();
                }
                case 8 -> { // Listar Períodos
                    System.out.println(CYAN + "\n--- LISTA DE PERÍODOS ---" + RESET);
                    sistema.listarPeriodos();
                }
                case 9 -> { // Relatórios
                    System.out.println(BLUE + "\n--- RELATÓRIOS ---" + RESET);
                    sistema.gerarRelatorios();
                }
                case 10 -> { // Buscar Aluno por Matrícula
                    System.out.println(CYAN + "\n--- BUSCA DE ALUNO POR MATRÍCULA ---" + RESET);
                    System.out.print("Matrícula do aluno: ");
                    String mat = sc.nextLine();
                    sistema.buscarAluno(mat);
                }
                case 11 -> { // Buscar Aluno por CPF
                    System.out.println(CYAN + "\n--- BUSCA DE ALUNO POR CPF ---" + RESET);
                    System.out.print("CPF do aluno: ");
                    String cpf = sc.nextLine();
                    sistema.buscarAlunoPorCpf(cpf);
                }
                case 12 -> { // Buscar Aluno por Nome
                    System.out.println(CYAN + "\n--- BUSCA DE ALUNO POR NOME ---" + RESET);
                    System.out.print("Nome (ou parte do nome) do aluno: ");
                    String nome = sc.nextLine();
                    sistema.buscarAlunosPorNome(nome);
                }
                case 13 -> { // Buscar Turma por Código
                    System.out.println(YELLOW + "\n--- BUSCA DE TURMA POR CÓDIGO ---" + RESET);
                    System.out.print("Código da turma: ");
                    String cod = sc.nextLine();
                    sistema.buscarTurma(cod);
                }
                case 14 -> { // Buscar Turma por Nome/Curso
                    System.out.println(YELLOW + "\n--- BUSCA DE TURMA POR NOME/CURSO ---" + RESET);
                    System.out.print("Nome ou curso (ou parte): ");
                    String termo = sc.nextLine();
                    sistema.buscarTurmasPorNome(termo);
                }
                case 15 -> { // Alterar Aluno
                    System.out.println(PURPLE + "\n--- ALTERAÇÃO DE ALUNO ---" + RESET);
                    System.out.print("Matrícula do aluno: ");
                    String mat = sc.nextLine();
                    
                    Aluno aluno = sistema.getAlunos().get(mat);
                    if (aluno == null) {
                        System.out.println("Aluno não encontrado.");
                        break;
                    }
                    
                    System.out.println("Dados atuais: " + aluno);
                    
                    System.out.print("Novo nome (deixe em branco para manter): ");
                    String novoNome = sc.nextLine();
                    if (novoNome.isEmpty()) novoNome = aluno.getNome();
                    
                    System.out.print("Novo CPF (deixe em branco para manter): ");
                    String novoCpf = sc.nextLine();
                    if (novoCpf.isEmpty()) novoCpf = aluno.getCpf();
                    else if (!sistema.validarCpf(novoCpf)) {
                        System.out.println(RED + "CPF inválido! Alteração cancelada." + RESET);
                        break;
                    }
                    
                    System.out.print("Novo gênero (deixe em branco para manter): ");
                    String novoGenero = sc.nextLine();
                    if (novoGenero.isEmpty()) novoGenero = aluno.getGenero();
                    
                    System.out.print("Novo email (deixe em branco para manter): ");
                    String novoEmail = sc.nextLine();
                    if (novoEmail.isEmpty()) novoEmail = aluno.getEmail();
                    else if (!sistema.validarEmail(novoEmail)) {
                        System.out.println(RED + "Email inválido! Alteração cancelada." + RESET);
                        break;
                    }
                    
                    System.out.print("Novo telefone (deixe em branco para manter): ");
                    String novoTelefone = sc.nextLine();
                    if (novoTelefone.isEmpty()) novoTelefone = aluno.getTelefone();
                    
                    sistema.alterarAluno(mat, novoNome, novoCpf, novoGenero, novoEmail, novoTelefone);
                }
                case 16 -> { // Alterar Turma
                    System.out.println(BLUE + "\n--- ALTERAÇÃO DE TURMA ---" + RESET);
                    System.out.print("Código da turma: ");
                    String cod = sc.nextLine();
                    
                    Turma turma = sistema.getTurmas().get(cod);
                    if (turma == null) {
                        System.out.println("Turma não encontrada.");
                        break;
                    }
                    
                    System.out.println("Dados atuais: " + turma);
                    
                    System.out.print("Novo nome (deixe em branco para manter): ");
                    String novoNome = sc.nextLine();
                    if (novoNome.isEmpty()) novoNome = turma.getNome();
                    
                    System.out.print("Novo curso (deixe em branco para manter): ");
                    String novoCurso = sc.nextLine();
                    if (novoCurso.isEmpty()) novoCurso = turma.getCurso();
                    
                    System.out.print("Novo período (deixe em branco para manter): ");
                    String novoPeriodo = sc.nextLine();
                    if (novoPeriodo.isEmpty()) novoPeriodo = turma.getPeriodo();
                    
                    System.out.print("Nova capacidade máxima (deixe em branco para manter): ");
                    String capacidadeStr = sc.nextLine();
                    int novaCapacidade = turma.getCapacidadeMaxima();
                    
                    if (!capacidadeStr.isEmpty()) {
                        try {
                            novaCapacidade = Integer.parseInt(capacidadeStr);
                            if (novaCapacidade <= 0) {
                                System.out.println(YELLOW + "Capacidade inválida! Mantendo o valor atual." + RESET);
                                novaCapacidade = turma.getCapacidadeMaxima();
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(YELLOW + "Capacidade inválida! Mantendo o valor atual." + RESET);
                        }
                    }
                    
                    sistema.alterarTurma(cod, novoNome, novoCurso, novoPeriodo, novaCapacidade);
                }
                case 17 -> { // Excluir Aluno
                    System.out.println(RED + "\n--- EXCLUSÃO DE ALUNO ---" + RESET);
                    System.out.print("Matrícula do aluno: ");
                    String mat = sc.nextLine();
                    
                    System.out.print("Tem certeza que deseja excluir este aluno? (S/N): ");
                    String confirmacao = sc.nextLine();
                    
                    if (confirmacao.equalsIgnoreCase("S")) {
                        sistema.excluirAluno(mat);
                    } else {
                        System.out.println("Operação cancelada.");
                    }
                }
                case 18 -> { // Excluir Turma
                    System.out.println(RED + "\n--- EXCLUSÃO DE TURMA ---" + RESET);
                    System.out.print("Código da turma: ");
                    String cod = sc.nextLine();
                    
                    System.out.print("Tem certeza que deseja excluir esta turma? (S/N): ");
                    String confirmacao = sc.nextLine();
                    
                    if (confirmacao.equalsIgnoreCase("S")) {
                        sistema.excluirTurma(cod);
                    } else {
                        System.out.println("Operação cancelada.");
                    }
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
