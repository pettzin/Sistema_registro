package model.dao;

import model.connection.DBConnection;
import model.Aluno;
import model.Curso;
import model.Turma;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaDAO {
    
    public void salvar(Turma turma) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Verificar se a turma já existe
            String verificarSql = "SELECT COUNT(*) FROM Turma WHERE codigo = ?";
            stmt = conn.prepareStatement(verificarSql);
            stmt.setString(1, turma.getCodigo());
            
            ResultSet rs = stmt.executeQuery();
            boolean turmaExiste = rs.next() && rs.getInt(1) > 0;
            rs.close();
            stmt.close();
            
            if (!turmaExiste) {
                // Inserir nova turma
                String sql = "INSERT INTO Turma (codigo, nome, id_curso, matricula_aluno, periodo, capacidade_maxima, data_inicio, data_fim) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                
                stmt.setString(1, turma.getCodigo());
                stmt.setString(2, turma.getNome());
                
                if (turma.getCurso() != null) {
                    stmt.setInt(3, turma.getCurso().getId());
                } else {
                    throw new RuntimeException("A turma deve estar associada a um curso.");
                }
                
                // Verificar se há pelo menos um aluno na turma
                if (turma.getAlunos() != null && !turma.getAlunos().isEmpty()) {
                    stmt.setString(4, turma.getAlunos().get(0).getMatricula());
                } else {
                    throw new RuntimeException("A turma deve ter pelo menos um aluno matriculado.");
                }
                
                stmt.setString(5, turma.getPeriodo());
                stmt.setInt(6, turma.getCapacidade());
                stmt.setString(7, turma.getDataInicio());
                stmt.setString(8, turma.getDataTermino());
                
                stmt.executeUpdate();
                
                // Inserir os alunos na tabela Matricula
                for (Aluno aluno : turma.getAlunos()) {
                    String inserirMatriculaSql = "INSERT INTO Matricula (matricula_aluno, codigo_turma, id_curso, data_matricula) VALUES (?, ?, ?, CURDATE())";
                    PreparedStatement stmtMatricula = conn.prepareStatement(inserirMatriculaSql);
                    stmtMatricula.setString(1, aluno.getMatricula());
                    stmtMatricula.setString(2, turma.getCodigo());
                    stmtMatricula.setInt(3, turma.getCurso().getId());
                    stmtMatricula.executeUpdate();
                    stmtMatricula.close();
                }
                
            } else {
                // Atualizar turma existente
                String sql = "UPDATE Turma SET nome = ?, id_curso = ?, periodo = ?, capacidade_maxima = ?, data_inicio = ?, data_fim = ? WHERE codigo = ?";
                stmt = conn.prepareStatement(sql);
                
                stmt.setString(1, turma.getNome());
                
                if (turma.getCurso() != null) {
                    stmt.setInt(2, turma.getCurso().getId());
                } else {
                    throw new RuntimeException("A turma deve estar associada a um curso.");
                }
                
                stmt.setString(3, turma.getPeriodo());
                stmt.setInt(4, turma.getCapacidade());
                stmt.setString(5, turma.getDataInicio());
                stmt.setString(6, turma.getDataTermino());
                stmt.setString(7, turma.getCodigo());
                
                stmt.executeUpdate();
                
                // Atualizar alunos da turma
                // Primeiro, remover todas as matrículas existentes
                String excluirMatriculasSql = "DELETE FROM Matricula WHERE codigo_turma = ?";
                PreparedStatement stmtExcluir = conn.prepareStatement(excluirMatriculasSql);
                stmtExcluir.setString(1, turma.getCodigo());
                stmtExcluir.executeUpdate();
                stmtExcluir.close();
                
                // Depois, inserir as novas matrículas
                for (Aluno aluno : turma.getAlunos()) {
                    String inserirMatriculaSql = "INSERT INTO Matricula (matricula_aluno, codigo_turma, id_curso, data_matricula) VALUES (?, ?, ?, CURDATE())";
                    PreparedStatement stmtMatricula = conn.prepareStatement(inserirMatriculaSql);
                    stmtMatricula.setString(1, aluno.getMatricula());
                    stmtMatricula.setString(2, turma.getCodigo());
                    stmtMatricula.setInt(3, turma.getCurso().getId());
                    stmtMatricula.executeUpdate();
                    stmtMatricula.close();
                }
                
                // Atualizar o campo matricula_aluno na tabela Turma
                if (turma.getAlunos() != null && !turma.getAlunos().isEmpty()) {
                    String atualizarMatriculaAlunoSql = "UPDATE Turma SET matricula_aluno = ? WHERE codigo = ?";
                    PreparedStatement stmtAtualizarMatricula = conn.prepareStatement(atualizarMatriculaAlunoSql);
                    stmtAtualizarMatricula.setString(1, turma.getAlunos().get(0).getMatricula());
                    stmtAtualizarMatricula.setString(2, turma.getCodigo());
                    stmtAtualizarMatricula.executeUpdate();
                    stmtAtualizarMatricula.close();
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar turma: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar turma", e);
        } finally {
            fecharRecursos(stmt);
        }
    }
    
    public void excluir(Turma turma) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Excluir as matrículas da turma
            String excluirMatriculasSql = "DELETE FROM Matricula WHERE codigo_turma = ?";
            stmt = conn.prepareStatement(excluirMatriculasSql);
            stmt.setString(1, turma.getCodigo());
            stmt.executeUpdate();
            stmt.close();
            
            // Excluir a turma
            String sql = "DELETE FROM Turma WHERE codigo = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, turma.getCodigo());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir turma: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir turma", e);
        } finally {
            fecharRecursos(stmt);
        }
    }
    
    public Turma buscarPorCodigo(String codigo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Turma turma = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Turma WHERE codigo = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, codigo);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                turma = criarTurmaDoResultSet(rs);
                carregarCurso(turma);
                carregarAlunos(turma);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turma por código: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar turma por código", e);
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return turma;
    }
    
    public List<Turma> buscarTodos() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Turma> turmas = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Turma ORDER BY codigo";
            stmt = conn.prepareStatement(sql);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Turma turma = criarTurmaDoResultSet(rs);
                carregarCurso(turma);
                carregarAlunos(turma);
                turmas.add(turma);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as turmas: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar todas as turmas", e);
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return turmas;
    }
    
    public List<Turma> buscarTurmasPorCodigo(String codigo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Turma> turmas = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Turma WHERE codigo LIKE ? ORDER BY codigo";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + codigo + "%");
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Turma turma = criarTurmaDoResultSet(rs);
                carregarCurso(turma);
                carregarAlunos(turma);
                turmas.add(turma);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turmas por código: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar turmas por código", e);
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return turmas;
    }
    
    private Turma criarTurmaDoResultSet(ResultSet rs) throws SQLException {
        Turma turma = new Turma();
        turma.setCodigo(rs.getString("codigo"));
        turma.setNome(rs.getString("nome"));
        turma.setPeriodo(rs.getString("periodo"));
        turma.setCapacidade(rs.getInt("capacidade_maxima"));
        turma.setDataInicio(rs.getString("data_inicio"));
        turma.setDataTermino(rs.getString("data_fim"));
        
        return turma;
    }
    
    private void carregarCurso(Turma turma) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT c.* FROM Curso c " +
                         "INNER JOIN Turma t ON c.id = t.id_curso " +
                         "WHERE t.codigo = ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, turma.getCodigo());
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Curso curso = new Curso();
                curso.setId(rs.getInt("id"));
                curso.setNome(rs.getString("nome"));
                curso.setDescricao(rs.getString("descricao"));
                
                // Carregar o professor do curso
                int idProfessor = rs.getInt("id_professor");
                if (idProfessor > 0) {
                    ProfessorDAO professorDAO = new ProfessorDAO();
                    curso.setProfessor(professorDAO.buscarPorId(idProfessor));
                }
                
                turma.setCurso(curso);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao carregar curso da turma: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
    }
    
    private void carregarAlunos(Turma turma) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT a.* FROM Aluno a " +
                         "INNER JOIN Matricula m ON a.matricula = m.matricula_aluno " +
                         "WHERE m.codigo_turma = ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, turma.getCodigo());
            
            rs = stmt.executeQuery();
            
            List<Aluno> alunos = new ArrayList<>();
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setMatricula(rs.getString("matricula"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setGenero(rs.getString("genero"));
                aluno.setEmail(rs.getString("email"));
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setEndereco(rs.getString("endereco"));
                
                alunos.add(aluno);
            }
            
            turma.setAlunos(alunos);
            
        } catch (SQLException e) {
            System.err.println("Erro ao carregar alunos da turma: " + e.getMessage());
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
