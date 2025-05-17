package model.dao;

import model.Aluno;
import model.Curso;
import model.Turma;
import model.connection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaDAO {
    
    /**
     * Salva uma turma no banco de dados (insere ou atualiza)
     * @param turma - objeto Turma a ser salvo
     */
    public void salvar(Turma turma) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Verifica se a turma já existe pelo código
            Turma turmaExistente = buscarPorCodigo(turma.getCodigo()).stream()
                                    .findFirst()
                                    .orElse(null);
            
            if (turmaExistente == null) {
                // Inserir nova turma
                String sql = "INSERT INTO Turma (codigo, nome, periodo, capacidade_maxima, data_inicio, data_termino, id_curso) VALUES (?, ?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                
                stmt.setString(1, turma.getCodigo());
                stmt.setString(2, turma.getCodigo()); // Usando o código como nome também
                stmt.setString(3, turma.getPeriodo());
                stmt.setInt(4, turma.getCapacidade());
                stmt.setDate(5, new java.sql.Date(turma.getDataInicio().getTime()));
                stmt.setDate(6, new java.sql.Date(turma.getDataTermino().getTime()));
                stmt.setInt(7, turma.getCurso().getId());
                
                stmt.executeUpdate();
                
                // Obtém o ID gerado
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    turma.setId(rs.getInt(1));
                }
                
                System.out.println("Turma inserida com sucesso!");
            } else {
                // Atualizar turma existente
                String sql = "UPDATE Turma SET nome = ?, periodo = ?, capacidade_maxima = ?, data_inicio = ?, data_termino = ?, id_curso = ? WHERE codigo = ?";
                stmt = conn.prepareStatement(sql);
                
                stmt.setString(1, turma.getCodigo()); // Usando o código como nome também
                stmt.setString(2, turma.getPeriodo());
                stmt.setInt(3, turma.getCapacidade());
                stmt.setDate(4, new java.sql.Date(turma.getDataInicio().getTime()));
                stmt.setDate(5, new java.sql.Date(turma.getDataTermino().getTime()));
                stmt.setInt(6, turma.getCurso().getId());
                stmt.setString(7, turma.getCodigo());
                
                stmt.executeUpdate();
                
                // Atualiza o ID da turma
                turma.setId(turmaExistente.getId());
                
                System.out.println("Turma atualizada com sucesso!");
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao salvar turma: " + e.getMessage());
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
     * Exclui uma turma do banco de dados
     * @param turma - objeto Turma a ser excluído
     */
    public void excluir(Turma turma) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Primeiro remove as matrículas da turma (devido à chave estrangeira)
            String sqlMatricula = "DELETE FROM Matricula WHERE codigo_turma = ?";
            stmt = conn.prepareStatement(sqlMatricula);
            stmt.setString(1, turma.getCodigo());
            stmt.executeUpdate();
            
            // Depois remove a turma
            String sqlTurma = "DELETE FROM Turma WHERE codigo = ?";
            stmt = conn.prepareStatement(sqlTurma);
            stmt.setString(1, turma.getCodigo());
            stmt.executeUpdate();
            
            System.out.println("Turma excluída com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("Erro ao excluir turma: " + e.getMessage());
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
     * Busca uma turma pelo ID
     * @param id - ID da turma
     * @return Turma - objeto Turma encontrado ou null
     */
    public Turma buscarPorId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Turma turma = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Turma WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                turma = mapearTurma(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turma por ID: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return turma;
    }
    
    /**
     * Busca todas as turmas cadastradas
     * @return List<Turma> - lista de turmas
     */
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
                Turma turma = mapearTurma(rs);
                turmas.add(turma);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todas as turmas: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return turmas;
    }
    
    /**
     * Busca turmas pelo código (busca parcial)
     * @param codigo - código ou parte do código a ser buscado
     * @return List<Turma> - lista de turmas encontradas
     */
    public List<Turma> buscarPorCodigo(String codigo) {
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
                Turma turma = mapearTurma(rs);
                turmas.add(turma);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turmas por código: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return turmas;
    }
    
    /**
     * Busca turmas por curso
     * @param curso - objeto Curso
     * @return List<Turma> - lista de turmas do curso
     */
    public List<Turma> buscarPorCurso(Curso curso) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Turma> turmas = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Turma WHERE id_curso = ? ORDER BY codigo";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, curso.getId());
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Turma turma = mapearTurma(rs);
                turma.setCurso(curso);
                turmas.add(turma);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turmas por curso: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return turmas;
    }
    
    /**
     * Busca turmas por período
     * @param periodo - período a ser buscado
     * @return List<Turma> - lista de turmas do período
     */
    public List<Turma> buscarPorPeriodo(String periodo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Turma> turmas = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT * FROM Turma WHERE periodo = ? ORDER BY codigo";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, periodo);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Turma turma = mapearTurma(rs);
                turmas.add(turma);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turmas por período: " + e.getMessage());
        } finally {
            fecharRecursos(stmt, rs);
        }
        
        return turmas;
    }
    
    /**
     * Mapeia um ResultSet para um objeto Turma
     * @param rs - ResultSet com dados da turma
     * @return Turma - objeto Turma mapeado
     * @throws SQLException - caso ocorra algum erro no acesso aos dados
     */
    private Turma mapearTurma(ResultSet rs) throws SQLException {
        Turma turma = new Turma();
        
        turma.setId(rs.getInt("id"));
        turma.setCodigo(rs.getString("codigo"));
        turma.setPeriodo(rs.getString("periodo"));
        turma.setCapacidade(rs.getInt("capacidade_maxima"));
        turma.setDataInicio(rs.getDate("data_inicio"));
        turma.setDataTermino(rs.getDate("data_termino"));
        
        // Busca o curso da turma
        int idCurso = rs.getInt("id_curso");
        if (idCurso > 0) {
            CursoDAO cursoDAO = new CursoDAO();
            Curso curso = cursoDAO.buscarPorId(idCurso);
            turma.setCurso(curso);
        }
        
        // Busca os alunos da turma
        buscarAlunosDaTurma(turma);
        
        return turma;
    }
    
    /**
     * Busca os alunos de uma turma
     * @param turma - objeto Turma
     */
    private void buscarAlunosDaTurma(Turma turma) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            String sql = "SELECT a.* FROM Aluno a " +
                         "INNER JOIN Matricula m ON a.matricula = m.matricula_aluno " +
                         "WHERE m.codigo_turma = ? " +
                         "ORDER BY a.nome";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, turma.getCodigo());
            
            rs = stmt.executeQuery();
            
            List<Aluno> alunos = new ArrayList<>();
            AlunoDAO alunoDAO = new AlunoDAO();
            
            while (rs.next()) {
                String matricula = rs.getString("matricula");
                Aluno aluno = alunoDAO.buscarPorId(Integer.parseInt(matricula));
                if (aluno != null) {
                    aluno.setTurma(turma);
                    alunos.add(aluno);
                }
            }
            
            turma.setAlunos(alunos);
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar alunos da turma: " + e.getMessage());
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