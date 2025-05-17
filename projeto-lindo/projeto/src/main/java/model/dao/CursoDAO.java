package model.dao;

import model.Curso;
import model.Professor;
import model.Turma;
import model.connection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {
    
    /**
     * Salva um curso no banco de dados (insere ou atualiza)
     * @param curso - objeto Curso a ser salvo
     */
    public void salvar(Curso curso) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Verifica se o curso já existe pelo nome
            Curso cursoExistente = buscarPorNome(curso.getNome()).stream()
                                   .filter(c -> c.getNome().equalsIgnoreCase(curso.getNome()))
                                   .findFirst()
                                   .orElse(null);
            
            if (cursoExistente == null) {
                // Inserir novo curso
                String sql = "INSERT INTO Curso (nome, descricao, id_professor) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                
                stmt.setString(1, curso.getNome());
                stmt.setString(2, curso.getDescricao());
                stmt.setInt(3, curso.getProfessor().getId());
                
                stmt.executeUpdate();
                
                // Obtém o ID gerado
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    curso.setId(rs.getInt(1));
                }
                
                System.out.println("Curso inserido com sucesso!");
            } else {
                // Atualizar curso existente
                String sql = "UPDATE Curso SET descricao = ?, id_professor = ? WHERE nome = ?";
                stmt = conn.prepareStatement(sql);
                
                stmt.setString(1, curso.getDescricao());
                stmt.setInt(2, curso.getProfessor().getId());
                stmt.setString(3, curso.getNome());
                
                stmt.executeUpdate();
                
                // Atualiza o ID do curso
                curso.setId(cursoExistente.getId());
                
                System.out.println("Curso atualizado com sucesso!");
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao salvar curso: " + e.getMessage());
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
     * Exclui um curso do banco de dados
     * @param curso - objeto Curso a ser excluído
     */
    public void excluir(Curso curso) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Primeiro atualiza as turmas para remover a referência ao curso
            String sqlTurma = "UPDATE Turma SET id_curso = NULL WHERE id_curso = ?";
            stmt = conn.prepareStatement(sqlTurma);
            stmt.setInt(1, curso.getId());
            stmt.executeUpdate();
            
            // Depois remove o curso
            String sqlCurso = "DELETE FROM Curso WHERE id = ?";
            stmt = conn.prepareStatement(sqlCurso);
            stmt.setInt(1, curso.getId());
            stmt.executeUpdate();
            
            System.out.println("Curso excluído com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("Erro ao excluir curso: " + e.getMessage());
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
     * Busca um curso pelo ID
     * @param id - ID do curso
     * @return Curso - objeto Curso encontrado ou null
     */
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
                curso = mapearCurso(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar curso por ID: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return curso;
    }
    
    /**
     * Busca todos os cursos cadastrados
     * @return List<Curso> - lista de cursos
     */
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
                Curso curso = mapearCurso(rs);
                cursos.add(curso);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os cursos: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return cursos;
    }
    
    /**
     * Busca cursos pelo nome (busca parcial)
     * @param nome - nome ou parte do nome a ser buscado
     * @return List<Curso> - lista de cursos encontrados
     */
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
                Curso curso = mapearCurso(rs);
                cursos.add(curso);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cursos por nome: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return cursos;
    }
    
    /**
     * Busca cursos por professor
     * @param professor - objeto Professor
     * @return List<Curso> - lista de cursos do professor
     */
    public List<Curso> buscarPorProfessor(Professor professor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Curso> cursos = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Curso WHERE id_professor = ? ORDER BY nome";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, professor.getId());
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Curso curso = mapearCurso(rs);
                curso.setProfessor(professor);
                cursos.add(curso);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cursos por professor: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return cursos;
    }
    
    /**
     * Mapeia um ResultSet para um objeto Curso
     * @param rs - ResultSet com dados do curso
     * @return Curso - objeto Curso mapeado
     * @throws SQLException - caso ocorra algum erro no acesso aos dados
     */
    private Curso mapearCurso(ResultSet rs) throws SQLException {
        Curso curso = new Curso();
        
        curso.setId(rs.getInt("id"));
        curso.setNome(rs.getString("nome"));
        curso.setDescricao(rs.getString("descricao"));
        
        // Busca o professor do curso
        int idProfessor = rs.getInt("id_professor");
        if (idProfessor > 0) {
            ProfessorDAO professorDAO = new ProfessorDAO();
            Professor professor = professorDAO.buscarPorId(idProfessor);
            curso.setProfessor(professor);
        }
        
        // Busca as turmas do curso
        buscarTurmasDoCurso(curso);
        
        return curso;
    }
    
    /**
     * Busca as turmas de um curso
     * @param curso - objeto Curso
     */
    private void buscarTurmasDoCurso(Curso curso) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Turma WHERE id_curso = ? ORDER BY codigo";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, curso.getId());
            
            rs = stmt.executeQuery();
            
            List<Turma> turmas = new ArrayList<>();
            
            while (rs.next()) {
                Turma turma = new Turma();
                turma.setId(rs.getInt("id"));
                turma.setCodigo(rs.getString("codigo"));
                turma.setPeriodo(rs.getString("periodo"));
                turma.setCapacidade(rs.getInt("capacidade_maxima"));
                turma.setDataInicio(rs.getDate("data_inicio"));
                turma.setDataTermino(rs.getDate("data_termino"));
                turma.setCurso(curso);
                
                turmas.add(turma);
            }
            
            curso.setTurmas(turmas);
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turmas do curso: " + e.getMessage());
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