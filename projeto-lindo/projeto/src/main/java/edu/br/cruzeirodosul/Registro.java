package edu.br.cruzeirodosul;

import java.util.*;
import lombok.*;

@Data
@AllArgsConstructor
public class Registro {
    private Map<String, Turma> turmas;
    private Map<String, Periodo> periodos;
    private Map<String, Aluno> alunos;
    private Map<String, Aluno> alunosPorCpf;
    private int proximoIdAluno;

    public Registro() {
        this.turmas = new HashMap<>();
        this.periodos = new HashMap<>();
        this.alunos = new HashMap<>();
        this.alunosPorCpf = new HashMap<>();
        this.proximoIdAluno = 1;
    }

    // Métodos para Turma
    // Atualizar o método cadastrarTurma para incluir capacidade máxima
    public void cadastrarTurma(String nome, String codigo, String curso, String periodo, int capacidadeMaxima) {
        if (turmas.containsKey(codigo)) {
            System.out.println("Turma com esse código já existe.");
        } else {
            turmas.put(codigo, new Turma(nome, codigo, curso, periodo, capacidadeMaxima));
            System.out.println("Turma cadastrada com sucesso.");
        }
    }

    // Atualizar o método simplificado para compatibilidade
    public void cadastrarTurma(String nome, String codigo) {
        cadastrarTurma(nome, codigo, "", "", 40); // Capacidade padrão de 40
    }

    // Atualizar o método cadastrarTurma existente
    public void cadastrarTurma(String nome, String codigo, String curso, String periodo) {
        cadastrarTurma(nome, codigo, curso, periodo, 40); // Capacidade padrão de 40
    }

    public void alterarTurma(String codigo, String novoNome, String novoCurso, String novoPeriodo) {
        alterarTurma(codigo, novoNome, novoCurso, novoPeriodo, turmas.get(codigo).getCapacidadeMaxima());
    }

    // Atualizar o método alterarTurma para incluir capacidade máxima
    public void alterarTurma(String codigo, String novoNome, String novoCurso, String novoPeriodo, int novaCapacidade) {
        Turma turma = turmas.get(codigo);
        if (turma != null) {
            turma.setNome(novoNome);
            turma.setCurso(novoCurso);
            turma.setPeriodo(novoPeriodo);
        
            // Verifica se a nova capacidade é menor que o número atual de alunos
            if (novaCapacidade < turma.getAlunos().size()) {
                System.out.println("Aviso: A nova capacidade é menor que o número atual de alunos matriculados.");
                System.out.println("A capacidade não foi alterada.");
            } else {
                turma.setCapacidadeMaxima(novaCapacidade);
            }
        
            System.out.println("Turma alterada com sucesso.");
        } else {
            System.out.println("Turma não encontrada.");
        }
    }

    // Atualizar o método simplificado para compatibilidade
    public void alterarTurma(String codigo, String novoNome) {
        Turma turma = turmas.get(codigo);
        if (turma != null) {
            alterarTurma(codigo, novoNome, turma.getCurso(), turma.getPeriodo(), turma.getCapacidadeMaxima());
        } else {
            System.out.println("Turma não encontrada.");
        }
    }

    public void excluirTurma(String codigo) {
        if (turmas.remove(codigo) != null) {
            System.out.println("Turma excluída com sucesso.");
        } else {
            System.out.println("Turma não encontrada.");
        }
    }

    public void listarTurmas() {
        if (turmas.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada.");
        } else {
            System.out.println("\n--- TURMAS CADASTRADAS ---");
            for (Turma t : turmas.values()) {
                System.out.println(t);
                System.out.println("------------------------------");
            }
        }
    }

    // Métodos para Aluno
    public void cadastrarAluno(String nome, String cpf, String genero, String email, String telefone) {
        // Verifica se já existe aluno com este CPF
        if (alunosPorCpf.containsKey(cpf)) {
            System.out.println("Aluno com este CPF já existe.");
            return;
        }
        
        // Gera matrícula baseada no ID
        String matricula = gerarMatricula();
        
        // Cria o aluno com ID único
        Aluno aluno = new Aluno(proximoIdAluno, nome, matricula, cpf, genero, email, telefone);
        
        // Adiciona aos mapas
        alunos.put(matricula, aluno);
        alunosPorCpf.put(cpf, aluno);
        
        // Incrementa o próximo ID
        proximoIdAluno++;
        
        System.out.println("Aluno cadastrado com sucesso. Matrícula: " + matricula);
    }

    // Método simplificado para compatibilidade
    public void cadastrarAluno(String nome, String matricula) {
        if (alunos.containsKey(matricula)) {
            System.out.println("Aluno com esta matrícula já existe.");
        } else {
            Aluno aluno = new Aluno(nome, matricula);
            aluno.setId(proximoIdAluno++);
            alunos.put(matricula, aluno);
            System.out.println("Aluno cadastrado com sucesso.");
        }
    }

    private String gerarMatricula() {
        // Gera uma matrícula baseada no ano atual e ID do aluno
        Calendar cal = Calendar.getInstance();
        int ano = cal.get(Calendar.YEAR);
        return String.format("%d%06d", ano, proximoIdAluno);
    }

    // Modificar o método matricularAluno para verificar vagas disponíveis
    public void matricularAluno(String codigoTurma, String matricula) {
        Turma turma = turmas.get(codigoTurma);
        Aluno aluno = alunos.get(matricula);
    
        if (turma == null) {
            System.out.println("Turma não encontrada.");
        } else if (aluno == null) {
            System.out.println("Aluno não encontrado.");
        } else {
            if (turma.alunoJaMatriculado(matricula)) {
                System.out.println("Aluno já está matriculado nesta turma.");
            } else if (turma.estaCheia()) {
                System.out.println("Não é possível matricular o aluno. Turma " + turma.getNome() + " está com todas as vagas preenchidas.");
            } else {
                boolean sucesso = turma.adicionarAluno(aluno);
                if (sucesso) {
                    System.out.println("Aluno matriculado com sucesso na turma " + turma.getNome() + 
                                      ". Vagas disponíveis: " + turma.vagasDisponiveis() + 
                                      " de " + turma.getCapacidadeMaxima() + ".");
                } else {
                    System.out.println("Não foi possível matricular o aluno.");
                }
            }
        }
    }

    public void alterarAluno(String matricula, String novoNome, String novoCpf, 
                             String novoGenero, String novoEmail, String novoTelefone) {
        Aluno aluno = alunos.get(matricula);
        if (aluno != null) {
            // Se o CPF for alterado, atualiza o mapa de CPFs
            if (!aluno.getCpf().isEmpty() && !aluno.getCpf().equals(novoCpf)) {
                alunosPorCpf.remove(aluno.getCpf());
            }
            
            aluno.setNome(novoNome);
            aluno.setCpf(novoCpf);
            aluno.setGenero(novoGenero);
            aluno.setEmail(novoEmail);
            aluno.setTelefone(novoTelefone);
            
            // Adiciona o novo CPF ao mapa
            if (!novoCpf.isEmpty()) {
                alunosPorCpf.put(novoCpf, aluno);
            }
            
            System.out.println("Aluno alterado com sucesso.");
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    // Método simplificado para compatibilidade
    public void alterarAluno(String matricula, String novoNome) {
        Aluno aluno = alunos.get(matricula);
        if (aluno != null) {
            alterarAluno(matricula, novoNome, aluno.getCpf(), 
                         aluno.getGenero(), aluno.getEmail(), aluno.getTelefone());
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    public void excluirAluno(String matricula) {
        Aluno aluno = alunos.get(matricula);
        if (aluno != null) {
            // Remove o aluno de todas as turmas primeiro
            for (Turma t : turmas.values()) {
                t.removerAluno(matricula);
            }
            
            // Remove do mapa de CPFs se tiver CPF
            if (!aluno.getCpf().isEmpty()) {
                alunosPorCpf.remove(aluno.getCpf());
            }
            
            // Remove do mapa principal
            alunos.remove(matricula);
            System.out.println("Aluno excluído com sucesso.");
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    public void listarAlunos() {
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            System.out.println("\n--- TODOS OS ALUNOS CADASTRADOS ---");
            for (Aluno a : alunos.values()) {
                System.out.println(a);
                System.out.println("------------------------------");
            }
        }
    }

    public void listarAlunos(String codigoTurma) {
        Turma turma = turmas.get(codigoTurma);
        if (turma != null) {
            if (turma.getAlunos().isEmpty()) {
                System.out.println("Não há alunos matriculados nesta turma.");
            } else {
                System.out.println("\n--- ALUNOS DA TURMA " + turma.getNome() + " ---");
                for (Aluno a : turma.getAlunos()) {
                    System.out.println(a);
                    System.out.println("------------------------------");
                }
            }
        } else {
            System.out.println("Turma não encontrada.");
        }
    }

    // Métodos para Período
    public void cadastrarPeriodo(String nome, String codigo, String descricao, String dataInicio, String dataFim) {
        if (periodos.containsKey(codigo)) {
            System.out.println("Período com esse código já existe.");
        } else {
            Periodo periodo = new Periodo(nome, codigo);
            periodo.setDescricao(descricao);
            periodo.setDataInicio(dataInicio);
            periodo.setDataFim(dataFim);
            periodos.put(codigo, periodo);
            System.out.println("Período cadastrado com sucesso.");
        }
    }

    // Método simplificado para compatibilidade
    public void cadastrarPeriodo(String nome, String codigo) {
        cadastrarPeriodo(nome, codigo, "", "", "");
    }

    public void listarPeriodos() {
        if (periodos.isEmpty()) {
            System.out.println("Nenhum período cadastrado.");
        } else {
            System.out.println("\n--- PERÍODOS CADASTRADOS ---");
            for (Periodo p : periodos.values()) {
                System.out.println(p);
                System.out.println("------------------------------");
            }
        }
    }

    // Atualizar o método gerarRelatorios para incluir informações de capacidade
    public void gerarRelatorios() {
        System.out.println("\n--- RELATÓRIO GERAL ---");
        System.out.println("Total de Turmas: " + turmas.size());
        System.out.println("Total de Períodos: " + periodos.size());
        System.out.println("Total de Alunos Cadastrados: " + alunos.size());
    
        // Distribuição por gênero
        Map<String, Integer> contagemGenero = new HashMap<>();
        for (Aluno a : alunos.values()) {
            String genero = a.getGenero().isEmpty() ? "Não informado" : a.getGenero();
            contagemGenero.put(genero, contagemGenero.getOrDefault(genero, 0) + 1);
        }
    
        System.out.println("\nDistribuição por Gênero:");
        for (Map.Entry<String, Integer> entry : contagemGenero.entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue() + " alunos");
        }
    
        // Alunos por turma e ocupação
        System.out.println("\nAlunos por Turma e Ocupação:");
        for (Turma t : turmas.values()) {
            int ocupacaoPercentual = (t.getAlunos().size() * 100) / t.getCapacidadeMaxima();
            System.out.println("- " + t.getNome() + " (" + t.getCurso() + ", " + t.getPeriodo() + "): " 
                             + t.getAlunos().size() + "/" + t.getCapacidadeMaxima() 
                             + " alunos (" + ocupacaoPercentual + "% ocupada)");
        }
    
        // Turmas por período
        Map<String, List<Turma>> turmasPorPeriodo = new HashMap<>();
        for (Turma t : turmas.values()) {
            String periodo = t.getPeriodo().isEmpty() ? "Não informado" : t.getPeriodo();
            if (!turmasPorPeriodo.containsKey(periodo)) {
                turmasPorPeriodo.put(periodo, new ArrayList<>());
            }
            turmasPorPeriodo.get(periodo).add(t);
        }
    
        System.out.println("\nTurmas por Período do Dia:");
        for (Map.Entry<String, List<Turma>> entry : turmasPorPeriodo.entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue().size() + " turmas");
        }
    
        // Turmas com vagas disponíveis
        System.out.println("\nTurmas com Vagas Disponíveis:");
        for (Turma t : turmas.values()) {
            if (t.vagasDisponiveis() > 0) {
                System.out.println("- " + t.getNome() + ": " + t.vagasDisponiveis() + " vagas disponíveis");
            }
        }
    
        // Turmas lotadas
        System.out.println("\nTurmas Lotadas:");
        boolean temTurmasLotadas = false;
        for (Turma t : turmas.values()) {
            if (t.estaCheia()) {
                System.out.println("- " + t.getNome());
                temTurmasLotadas = true;
            }
        }
        if (!temTurmasLotadas) {
            System.out.println("- Nenhuma turma está lotada.");
        }
    }

    // Métodos para Busca
    public void buscarAluno(String matricula) {
        Aluno aluno = alunos.get(matricula);
        if (aluno != null) {
            System.out.println("\n--- ALUNO ENCONTRADO ---");
            System.out.println(aluno);
            
            System.out.println("\nTurmas matriculadas:");
            boolean encontrado = false;
            for (Turma t : turmas.values()) {
                if (t.getAlunos().contains(aluno)) {
                    System.out.println("- " + t.getNome() + " (" + t.getCurso() + ", " + t.getPeriodo() + ")");
                    encontrado = true;
                }
            }
            if (!encontrado) {
                System.out.println("O aluno não está matriculado em nenhuma turma.");
            }
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    public void buscarAlunoPorCpf(String cpf) {
        Aluno aluno = alunosPorCpf.get(cpf);
        if (aluno != null) {
            System.out.println("\n--- ALUNO ENCONTRADO ---");
            System.out.println(aluno);
            
            System.out.println("\nTurmas matriculadas:");
            boolean encontrado = false;
            for (Turma t : turmas.values()) {
                if (t.getAlunos().contains(aluno)) {
                    System.out.println("- " + t.getNome() + " (" + t.getCurso() + ", " + t.getPeriodo() + ")");
                    encontrado = true;
                }
            }
            if (!encontrado) {
                System.out.println("O aluno não está matriculado em nenhuma turma.");
            }
        } else {
            System.out.println("Aluno não encontrado com este CPF.");
        }
    }

    public void buscarTurma(String codigo) {
        Turma turma = turmas.get(codigo);
        if (turma != null) {
            System.out.println("\n--- TURMA ENCONTRADA ---");
            System.out.println(turma);
            listarAlunos(codigo);
        } else {
            System.out.println("Turma não encontrada.");
        }
    }
    
    // Métodos para busca por nome (parcial)
    public void buscarAlunosPorNome(String parteNome) {
        List<Aluno> encontrados = new ArrayList<>();
        
        for (Aluno a : alunos.values()) {
            if (a.getNome().toLowerCase().contains(parteNome.toLowerCase())) {
                encontrados.add(a);
            }
        }
        
        if (encontrados.isEmpty()) {
            System.out.println("Nenhum aluno encontrado com este nome.");
        } else {
            System.out.println("\n--- ALUNOS ENCONTRADOS ---");
            for (Aluno a : encontrados) {
                System.out.println(a);
                System.out.println("------------------------------");
            }
            System.out.println("Total de alunos encontrados: " + encontrados.size());
        }
    }
    
    public void buscarTurmasPorNome(String parteNome) {
        List<Turma> encontradas = new ArrayList<>();
        
        for (Turma t : turmas.values()) {
            if (t.getNome().toLowerCase().contains(parteNome.toLowerCase()) || 
                t.getCurso().toLowerCase().contains(parteNome.toLowerCase())) {
                encontradas.add(t);
            }
        }
        
        if (encontradas.isEmpty()) {
            System.out.println("Nenhuma turma encontrada com este nome/curso.");
        } else {
            System.out.println("\n--- TURMAS ENCONTRADAS ---");
            for (Turma t : encontradas) {
                System.out.println(t);
                System.out.println("------------------------------");
            }
            System.out.println("Total de turmas encontradas: " + encontradas.size());
        }
    }
    
    // Método para validar CPF
    public boolean validarCpf(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");
        
        // Verifica se tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }
        
        // Verifica se todos os dígitos são iguais (caso inválido)
        boolean todosDigitosIguais = true;
        for (int i = 1; i < 11; i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                todosDigitosIguais = false;
                break;
            }
        }
        if (todosDigitosIguais) {
            return false;
        }
        
        // Calcula o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }
        int resto = soma % 11;
        int dv1 = (resto < 2) ? 0 : 11 - resto;
        
        // Verifica o primeiro dígito verificador
        if ((cpf.charAt(9) - '0') != dv1) {
            return false;
        }
        
        // Calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }
        resto = soma % 11;
        int dv2 = (resto < 2) ? 0 : 11 - resto;
        
        // Verifica o segundo dígito verificador
        return (cpf.charAt(10) - '0') == dv2;
    }
    
    // Método para validar email
    public boolean validarEmail(String email) {
        // Verificação básica de email
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
}
