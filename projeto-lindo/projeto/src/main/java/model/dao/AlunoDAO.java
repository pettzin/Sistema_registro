package model.dao;

import model.Aluno;
import model.Turma;
import model.connection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    
    /**
     * Salva um aluno no banco de dados (insere ou atualiza)
     * @param aluno - objeto Aluno a ser salvo
     */
    public void salvar(Aluno aluno) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Verifica se o aluno já existe pelo CPF
            Aluno alunoExistente = buscarPorCpf(aluno.getCpf());
            
            if (alunoExistente == null) {
                // Inserir novo aluno
                String sql = "INSERT INTO Aluno (matricula, nome, cpf, genero, email, endereco, telefone) VALUES (?, ?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                
                // Gera uma matrícula se não existir
                String matricula = aluno.getMatricula() != null && !aluno.getMatricula().isEmpty() 
                                  ? aluno.getMatricula() 
                                  : gerarMatricula();
                
                stmt.setString(1, matricula);
                stmt.setString(2, aluno.getNome());
                stmt.setString(3, aluno.getCpf());
                stmt.setString(4, aluno.getGenero());
                stmt.setString(5, aluno.getEmail());
                stmt.setString(6, aluno.getEndereco());
                stmt.setString(7, aluno.getTelefone());
                
                stmt.executeUpdate();
                
                // Atualiza a matrícula do aluno
                aluno.setMatricula(matricula);
                aluno.setId(Integer.parseInt(matricula));
                
                System.out.println("Aluno inserido com sucesso!");
            } else {
                // Atualizar aluno existente
                String sql = "UPDATE Aluno SET nome = ?, genero = ?, email = ?, endereco = ?, telefone = ? WHERE cpf = ?";
                stmt = conn.prepareStatement(sql);
                
                stmt.setString(1, aluno.getNome());
                stmt.setString(2, aluno.getGenero());
                stmt.setString(3, aluno.getEmail());
                stmt.setString(4, aluno.getEndereco());
                stmt.setString(5, aluno.getTelefone());
                stmt.setString(6, aluno.getCpf());
                
                stmt.executeUpdate();
                
                // Atualiza a matrícula do aluno
                aluno.setMatricula(alunoExistente.getMatricula());
                aluno.setId(Integer.parseInt(alunoExistente.getMatricula()));
                
                System.out.println("Aluno atualizado com sucesso!");
            }
            
            // Se o aluno tem uma turma associada, verifica se precisa criar uma matrícula
            if (aluno.getTurma() != null) {
                vincularAlunoTurma(aluno, aluno.getTurma());
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao salvar aluno: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar statement: " + e.getMessage());
            }
        }
    }
    
    /**
     * Exclui um aluno do banco de dados
     * @param aluno - objeto Aluno a ser excluído
     */
    public void excluir(Aluno aluno) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Primeiro remove as matrículas do aluno (devido à chave estrangeira)
            String sqlMatricula = "DELETE FROM Matricula WHERE matricula_aluno = ?";
            stmt = conn.prepareStatement(sqlMatricula);
            stmt.setString(1, aluno.getMatricula());
            stmt.executeUpdate();
            
            // Depois remove o aluno
            String sqlAluno = "DELETE FROM Aluno WHERE cpf = ?";
            stmt = conn.prepareStatement(sqlAluno);
            stmt.setString(1, aluno.getCpf());
            stmt.executeUpdate();
            
            System.out.println("Aluno excluído com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("Erro ao excluir aluno: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar statement: " + e.getMessage());
            }
        }
    }
    
    /**
     * Busca um aluno pelo ID (matrícula)
     * @param id - ID do aluno
     * @return Aluno - objeto Aluno encontrado ou null
     */
    public Aluno buscarPorId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Aluno aluno = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Aluno WHERE matricula = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.valueOf(id));
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                aluno = mapearAluno(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno por ID: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return aluno;
    }
    
    /**
     * Busca todos os alunos cadastrados
     * @return List<Aluno> - lista de alunos
     */
    public List<Aluno> buscarTodos() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Aluno> alunos = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Aluno ORDER BY nome";
            stmt = conn.prepareStatement(sql);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Aluno aluno = mapearAluno(rs);
                alunos.add(aluno);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os alunos: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return alunos;
    }
    
    /**
     * Busca alunos pelo nome (busca parcial)
     * @param nome - nome ou parte do nome a ser buscado
     * @return List<Aluno> - lista de alunos encontrados
     */
    public List<Aluno> buscarPorNome(String nome) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Aluno> alunos = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Aluno WHERE nome LIKE ? ORDER BY nome";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Aluno aluno = mapearAluno(rs);
                alunos.add(aluno);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar alunos por nome: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return alunos;
    }
    
    /**
     * Busca um aluno pelo CPF
     * @param cpf - CPF do aluno
     * @return Aluno - objeto Aluno encontrado ou null
     */
    public Aluno buscarPorCpf(String cpf) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Aluno aluno = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Aluno WHERE cpf = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                aluno = mapearAluno(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno por CPF: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return aluno;
    }
    
    /**
     * Busca alunos por turma
     * @param turma - objeto Turma
     * @return List<Aluno> - lista de alunos da turma
     */
    public List<Aluno> buscarPorTurma(Turma turma) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Aluno> alunos = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT a.* FROM Aluno a " +
                         "INNER JOIN Matricula m ON a.matricula = m.matricula_aluno " +
                         "WHERE m.codigo_turma = ? " +
                         "ORDER BY a.nome";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, turma.getCodigo());
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Aluno aluno = mapearAluno(rs);
                aluno.setTurma(turma);
                alunos.add(aluno);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar alunos por turma: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return alunos;
    }
    
    /**
     * Vincula um aluno a uma turma (cria uma matrícula)
     * @param aluno - objeto Aluno
     * @param turma - objeto Turma
     * @return boolean - true se a operação foi bem-sucedida
     */
    private boolean vincularAlunoTurma(Aluno aluno, Turma turma) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Verifica se já existe uma matrícula para este aluno nesta turma
            String sqlVerifica = "SELECT COUNT(*) FROM Matricula WHERE matricula_aluno = ? AND codigo_turma = ?";
            stmt = conn.prepareStatement(sqlVerifica);
            stmt.setString(1, aluno.getMatricula());
            stmt.setString(2, turma.getCodigo());
            
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            
            if (count == 0) {
                // Cria uma nova matrícula
                String sqlInsert = "INSERT INTO Matricula (matricula_aluno, codigo_turma, id_curso, data_matricula) VALUES (?, ?, ?, CURDATE())";
                stmt = conn.prepareStatement(sqlInsert);
                stmt.setString(1, aluno.getMatricula());
                stmt.setString(2, turma.getCodigo());
                stmt.setInt(3, turma.getCurso().getId());
                
                stmt.executeUpdate();
                System.out.println("Aluno matriculado na turma com sucesso!");
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            System.err.println("Erro ao vincular aluno à turma: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar statement: " + e.getMessage());
            }
        }
    }
    
    /**
     * Mapeia um ResultSet para um objeto Aluno
     * @param rs - ResultSet com dados do aluno
     * @return Aluno - objeto Aluno mapeado
     * @throws SQLException - caso ocorra algum erro no acesso aos dados
     */
    private Aluno mapearAluno(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno();
        
        String matricula = rs.getString("matricula");
        aluno.setMatricula(matricula);
        aluno.setId(Integer.parseInt(matricula));
        aluno.setNome(rs.getString("nome"));
        aluno.setCpf(rs.getString("cpf"));
        aluno.setGenero(rs.getString("genero"));
        aluno.setEmail(rs.getString("email"));
        aluno.setEndereco(rs.getString("endereco"));
        aluno.setTelefone(rs.getString("telefone"));
        
        // Busca a turma do aluno (se houver)
        buscarTurmaDoAluno(aluno);
        
        return aluno;
    }
    
    /**
     * Busca a turma de um aluno
     * @param aluno - objeto Aluno
     */
    private void buscarTurmaDoAluno(Aluno aluno) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT t.* FROM Turma t " +
                         "INNER JOIN Matricula m ON t.codigo = m.codigo_turma " +
                         "WHERE m.matricula_aluno = ? " +
                         "LIMIT 1";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, aluno.getMatricula());
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Turma turma = new Turma();
                turma.setCodigo(rs.getString("codigo"));
                turma.setCapacidade(rs.getInt("capacidade_maxima"));
                
                // Aqui precisaríamos buscar o curso da turma, mas isso seria feito pelo TurmaDAO
                // Por enquanto, deixamos apenas o código básico
                
                aluno.setTurma(turma);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turma do aluno: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
    }
    
    /**
     * Gera uma matrícula única para o aluno
     * @return String - matrícula gerada
     */
    private String gerarMatricula() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Busca a maior matrícula existente
            String sql = "SELECT MAX(CAST(matricula AS UNSIGNED)) AS max_matricula FROM Aluno";
            stmt = conn.prepareStatement(sql);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                int maxMatricula = rs.getInt("max_matricula");
                return String.valueOf(maxMatricula + 1);
            } else {
                return "1000"; // Matrícula inicial
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao gerar matrícula: " + e.getMessage());
            return String.valueOf(System.currentTimeMillis()); // Fallback usando timestamp
        } finally {
            fecharRecursos(stmt, rs);
        }
    }
    
    /**
     * Fecha recursos JDBC (Statement e ResultSet)
     * @param stmt - PreparedStatement a ser fechado
     * @param rs - ResultSet a ser fechado
     */
    private void fecharRecursos(PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar recursos: " + e.getMessage());
        }
    }
}