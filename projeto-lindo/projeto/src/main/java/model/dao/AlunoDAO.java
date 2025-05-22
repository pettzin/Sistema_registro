package model.dao;

import model.connection.DBConnection;
import model.Aluno;
import model.Turma;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    
    public void salvar(Aluno aluno) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Verificar se o aluno já existe
            String verificarSql = "SELECT COUNT(*) FROM Aluno WHERE matricula = ?";
            stmt = conn.prepareStatement(verificarSql);
            stmt.setString(1, aluno.getMatricula());
            
            ResultSet rs = stmt.executeQuery();
            boolean alunoExiste = rs.next() && rs.getInt(1) > 0;
            rs.close();
            stmt.close();
            
            if (!alunoExiste) {
                // Inserir novo aluno
                String sql = "INSERT INTO Aluno (matricula, nome, cpf, genero, email, telefone, endereco, data_criacao) VALUES (?, ?, ?, ?, ?, ?, ?, CURDATE())";
                stmt = conn.prepareStatement(sql);
                
                stmt.setString(1, aluno.getMatricula());
                stmt.setString(2, aluno.getNome());
                stmt.setString(3, aluno.getCpf());
                stmt.setString(4, aluno.getGenero());
                stmt.setString(5, aluno.getEmail());
                stmt.setString(6, aluno.getTelefone());
                stmt.setString(7, aluno.getEndereco());
                
                stmt.executeUpdate();
            } else {
                // Atualizar aluno existente
                String sql = "UPDATE Aluno SET nome = ?, cpf = ?, genero = ?, email = ?, telefone = ?, endereco = ? WHERE matricula = ?";
                stmt = conn.prepareStatement(sql);
                
                stmt.setString(1, aluno.getNome());
                stmt.setString(2, aluno.getCpf());
                stmt.setString(3, aluno.getGenero());
                stmt.setString(4, aluno.getEmail());
                stmt.setString(5, aluno.getTelefone());
                stmt.setString(6, aluno.getEndereco());
                stmt.setString(7, aluno.getMatricula());
                
                stmt.executeUpdate();
            }
            
            // Se o aluno tiver uma turma, verificar a capacidade e inserir na tabela Matricula
            if (aluno.getTurma() != null) {
                // Verificar a capacidade da turma
                String verificarCapacidadeSql = "SELECT t.capacidade_maxima, COUNT(m.matricula_aluno) as alunos_matriculados " +
                                               "FROM Turma t " +
                                               "LEFT JOIN Matricula m ON t.codigo = m.codigo_turma " +
                                               "WHERE t.codigo = ? " +
                                               "GROUP BY t.codigo, t.capacidade_maxima";
                
                stmt = conn.prepareStatement(verificarCapacidadeSql);
                stmt.setString(1, aluno.getTurma().getCodigo());
                
                rs = stmt.executeQuery();
                
                if (rs.next()) {
                    int capacidadeMaxima = rs.getInt("capacidade_maxima");
                    int alunosMatriculados = rs.getInt("alunos_matriculados");
                    
                    // Verificar se o aluno já está matriculado nesta turma
                    String verificarMatriculaSql = "SELECT COUNT(*) FROM Matricula WHERE matricula_aluno = ? AND codigo_turma = ?";
                    stmt.close();
                    stmt = conn.prepareStatement(verificarMatriculaSql);
                    stmt.setString(1, aluno.getMatricula());
                    stmt.setString(2, aluno.getTurma().getCodigo());
                    
                    ResultSet rsMatricula = stmt.executeQuery();
                    boolean alunoJaMatriculado = rsMatricula.next() && rsMatricula.getInt(1) > 0;
                    rsMatricula.close();
                    stmt.close();
                    
                    // Se o aluno não estiver matriculado e a turma estiver cheia, lançar exceção
                    if (!alunoJaMatriculado && alunosMatriculados >= capacidadeMaxima) {
                        throw new RuntimeException("Não é possível matricular o aluno. A turma já atingiu sua capacidade máxima de " + capacidadeMaxima + " alunos.");
                    }
                    
                    // Se o aluno não estiver matriculado, inserir na tabela Matricula
                    if (!alunoJaMatriculado) {
                        String inserirMatriculaSql = "INSERT INTO Matricula (matricula_aluno, codigo_turma, id_curso, data_matricula) VALUES (?, ?, ?, CURDATE())";
                        stmt = conn.prepareStatement(inserirMatriculaSql);
                        
                        stmt.setString(1, aluno.getMatricula());
                        stmt.setString(2, aluno.getTurma().getCodigo());
                        stmt.setInt(3, aluno.getTurma().getCurso().getId());
                        
                        stmt.executeUpdate();
                    }
                }
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar aluno: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar aluno", e);
        } finally {
            fecharRecursos(stmt);
        }
    }
    
    public void excluir(Aluno aluno) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Excluir as matrículas do aluno
            String excluirMatriculasSql = "DELETE FROM Matricula WHERE matricula_aluno = ?";
            stmt = conn.prepareStatement(excluirMatriculasSql);
            stmt.setString(1, aluno.getMatricula());
            stmt.executeUpdate();
            stmt.close();
            
            // Excluir o aluno
            String sql = "DELETE FROM Aluno WHERE matricula = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, aluno.getMatricula());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir aluno: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir aluno", e);
        } finally {
            fecharRecursos(stmt);
        }
    }
    
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
                aluno = criarAlunoDoResultSet(rs);
                buscarTurmaDoAluno(aluno);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar aluno por ID", e);
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return aluno;
    }
    
    public Aluno buscarPorMatricula(String matricula) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Aluno aluno = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Aluno WHERE matricula = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, matricula);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                aluno = criarAlunoDoResultSet(rs);
                buscarTurmaDoAluno(aluno);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno por matrícula: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar aluno por matrícula", e);
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return aluno;
    }
    
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
                Aluno aluno = criarAlunoDoResultSet(rs);
                buscarTurmaDoAluno(aluno);
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os alunos: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar todos os alunos", e);
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return alunos;
    }
    
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
                Aluno aluno = criarAlunoDoResultSet(rs);
                buscarTurmaDoAluno(aluno);
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar alunos por nome: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar alunos por nome", e);
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return alunos;
    }
    
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
                aluno = criarAlunoDoResultSet(rs);
                buscarTurmaDoAluno(aluno);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno por CPF: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar aluno por CPF", e);
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return aluno;
    }
    
    public List<Aluno> buscarPorTurma(Turma turma) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Aluno> alunos = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT a.* FROM Aluno a " +
                         "INNER JOIN Matricula m ON a.matricula = m.matricula_aluno " +
                         "WHERE m.codigo_turma = ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, turma.getCodigo());
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Aluno aluno = criarAlunoDoResultSet(rs);
                aluno.setTurma(turma); // Já sabemos a turma
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar alunos por turma: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar alunos por turma", e);
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return alunos;
    }
    
    private Aluno criarAlunoDoResultSet(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setMatricula(rs.getString("matricula"));
        aluno.setNome(rs.getString("nome"));
        aluno.setCpf(rs.getString("cpf"));
        aluno.setGenero(rs.getString("genero"));
        aluno.setEmail(rs.getString("email"));
        aluno.setTelefone(rs.getString("telefone"));
        aluno.setEndereco(rs.getString("endereco"));

        // Recuperar a data de criação
        java.sql.Date dataCriacao = rs.getDate("data_criacao");
        if (dataCriacao != null) {
            // Converter para o formato desejado (dd/MM/yyyy)
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            aluno.setDataCriacao(sdf.format(dataCriacao));
        }
        
        return aluno;
    }
    
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
                turma.setNome(rs.getString("nome"));
                turma.setPeriodo(rs.getString("periodo"));
                turma.setCapacidade(rs.getInt("capacidade_maxima"));
                turma.setDataInicio(rs.getString("data_inicio"));
                turma.setDataTermino(rs.getString("data_fim"));
                
                // Buscar o curso da turma
                int idCurso = rs.getInt("id_curso");
                CursoDAO cursoDAO = new CursoDAO();
                turma.setCurso(cursoDAO.buscarPorId(idCurso));
                
                aluno.setTurma(turma);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turma do aluno: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
    }
    
    private void fecharRecursos(Statement stmt) {
        fecharRecursos(stmt, null);
    }
    
    private void fecharRecursos(Statement stmt, ResultSet rs) {
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
