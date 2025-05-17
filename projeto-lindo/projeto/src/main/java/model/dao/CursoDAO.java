package model.dao;

import model.connection.DBConnection;
import model.Curso;
import model.Professor;
import model.Turma;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {
    
    public void salvar(Curso curso) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            if (curso.getId() == 0) {
                // Inserir novo curso
                String sql = "INSERT INTO Curso (nome, descricao, id_professor) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                
                stmt.setString(1, curso.getNome());
                stmt.setString(2, curso.getDescricao());
                
                if (curso.getProfessor() != null) {
                    stmt.setInt(3, curso.getProfessor().getId());
                } else {
                    stmt.setNull(3, java.sql.Types.INTEGER);
                }
                
                stmt.executeUpdate();
                
                // Obter o ID gerado
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    curso.setId(rs.getInt(1));
                }
                rs.close();
            } else {
                // Atualizar curso existente
                String sql = "UPDATE Curso SET nome = ?, descricao = ?, id_professor = ? WHERE id = ?";
                stmt = conn.prepareStatement(sql);
                
                stmt.setString(1, curso.getNome());
                stmt.setString(2, curso.getDescricao());
                
                if (curso.getProfessor() != null) {
                    stmt.setInt(3, curso.getProfessor().getId());
                } else {
                    stmt.setNull(3, java.sql.Types.INTEGER);
                }
                
                stmt.setInt(4, curso.getId());
                
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar curso: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar curso", e);
        } finally {
            fecharRecursos(stmt);
        }
    }
    
    public void excluir(Curso curso) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Verificar se o curso tem turmas associadas
            String verificarTurmasSql = "SELECT COUNT(*) FROM Turma WHERE id_curso = ?";
            stmt = conn.prepareStatement(verificarTurmasSql);
            stmt.setInt(1, curso.getId());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new RuntimeException("Não é possível excluir o curso pois existem turmas associadas a ele.");
            }
            rs.close();
            stmt.close();
            
            // Excluir o curso
            String sql = "DELETE FROM Curso WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, curso.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir curso: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir curso", e);
        } finally {
            fecharRecursos(stmt);
        }
    }
    
    public Curso buscarPorId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Curso curso = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Curso WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                curso = criarCursoDoResultSet(rs);
                carregarTurmas(curso);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar curso por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar curso por ID", e);
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return curso;
    }
    
    public List<Curso> buscarTodos() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Curso> cursos = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Curso ORDER BY nome";
            stmt = conn.prepareStatement(sql);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Curso curso = criarCursoDoResultSet(rs);
                carregarTurmas(curso);
                cursos.add(curso);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os cursos: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar todos os cursos", e);
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return cursos;
    }
    
    public List<Curso> buscarPorNome(String nome) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Curso> cursos = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Curso WHERE nome LIKE ? ORDER BY nome";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Curso curso = criarCursoDoResultSet(rs);
                carregarTurmas(curso);
                cursos.add(curso);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cursos por nome: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar cursos por nome", e);
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return cursos;
    }
    
    private Curso criarCursoDoResultSet(ResultSet rs) throws SQLException {
        Curso curso = new Curso();
        curso.setId(rs.getInt("id"));
        curso.setNome(rs.getString("nome"));
        curso.setDescricao(rs.getString("descricao"));
        
        // Carregar o professor do curso
        carregarProfessor(curso);
        
        return curso;
    }
    
    private void carregarTurmas(Curso curso) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Turma WHERE id_curso = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, curso.getId());
            
            rs = stmt.executeQuery();
            
            List<Turma> turmas = new ArrayList<>();
            while (rs.next()) {
                Turma turma = new Turma();
                turma.setCodigo(rs.getString("codigo"));
                turma.setNome(rs.getString("nome"));
                turma.setPeriodo(rs.getString("periodo"));
                turma.setCapacidade(rs.getInt("capacidade_maxima"));
                turma.setDataInicio(rs.getString("data_inicio"));
                turma.setDataTermino(rs.getString("data_fim"));
                turma.setCurso(curso);
                
                turmas.add(turma);
            }
            
            curso.setTurmas(turmas);
            
        } catch (SQLException e) {
            System.err.println("Erro ao carregar turmas do curso: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
    }
    
    private void carregarProfessor(Curso curso) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT p.* FROM Professor p " +
                         "INNER JOIN Curso c ON p.id = c.id_professor " +
                         "WHERE c.id = ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, curso.getId());
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Professor professor = new Professor();
                professor.setId(rs.getInt("id"));
                professor.setNome(rs.getString("nome"));
                professor.setCpf(rs.getString("cpf"));
                professor.setEmail(rs.getString("email"));
                professor.setTelefone(rs.getString("telefone"));
                professor.setEndereco(rs.getString("endereco"));
                
                curso.setProfessor(professor);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao carregar professor do curso: " + e.getMessage());
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