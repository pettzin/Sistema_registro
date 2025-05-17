package model.dao;

import model.Professor;
import model.connection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {
    
    public void salvar(Professor professor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            if (professor.getId() == 0) {
                // Inserir novo professor
                String sql = "INSERT INTO Professor (nome, cpf, email, telefone, endereco) VALUES (?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                
                stmt.setString(1, professor.getNome());
                stmt.setString(2, professor.getCpf());
                stmt.setString(3, professor.getEmail());
                stmt.setString(4, professor.getTelefone());
                stmt.setString(5, professor.getEndereco());
                
                stmt.executeUpdate();
                
                // Obter o ID gerado
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    professor.setId(rs.getInt(1));
                }
                rs.close();
            } else {
                // Atualizar professor existente
                String sql = "UPDATE Professor SET nome = ?, cpf = ?, email = ?, telefone = ?, endereco = ? WHERE id = ?";
                stmt = conn.prepareStatement(sql);
                
                stmt.setString(1, professor.getNome());
                stmt.setString(2, professor.getCpf());
                stmt.setString(3, professor.getEmail());
                stmt.setString(4, professor.getTelefone());
                stmt.setString(5, professor.getEndereco());
                stmt.setInt(6, professor.getId());
                
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar professor: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar professor", e);
        } finally {
            fecharRecursos(stmt);
        }
    }
    
    public void excluir(Professor professor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Verificar se o professor está associado a algum curso
            String verificarCursoSql = "SELECT COUNT(*) FROM Curso WHERE id_professor = ?";
            stmt = conn.prepareStatement(verificarCursoSql);
            stmt.setInt(1, professor.getId());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new RuntimeException("Não é possível excluir o professor pois está associado a um ou mais cursos.");
            }
            rs.close();
            stmt.close();
            
            // Excluir o professor
            String sql = "DELETE FROM Professor WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, professor.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir professor: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir professor", e);
        } finally {
            fecharRecursos(stmt);
        }
    }
    
    public Professor buscarPorId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Professor professor = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Professor WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                professor = criarProfessorDoResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar professor por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar professor por ID", e);
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return professor;
    }
    
    public List<Professor> buscarTodos() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Professor> professores = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Professor ORDER BY nome";
            stmt = conn.prepareStatement(sql);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Professor professor = criarProfessorDoResultSet(rs);
                professores.add(professor);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os professores: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar todos os professores", e);
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return professores;
    }
    
    public List<Professor> buscarPorNome(String nome) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Professor> professores = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Professor WHERE nome LIKE ? ORDER BY nome";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Professor professor = criarProfessorDoResultSet(rs);
                professores.add(professor);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar professores por nome: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar professores por nome", e);
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return professores;
    }
    
    public Professor buscarPorCpf(String cpf) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Professor professor = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Professor WHERE cpf = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                professor = criarProfessorDoResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar professor por CPF: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar professor por CPF", e);
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return professor;
    }
    
    private Professor criarProfessorDoResultSet(ResultSet rs) throws SQLException {
        Professor professor = new Professor();
        professor.setId(rs.getInt("id"));
        professor.setNome(rs.getString("nome"));
        professor.setCpf(rs.getString("cpf"));
        professor.setEmail(rs.getString("email"));
        professor.setTelefone(rs.getString("telefone"));
        professor.setEndereco(rs.getString("endereco"));
        
        return professor;
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
