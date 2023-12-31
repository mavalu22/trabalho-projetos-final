package com.mycompany.gestaousuarios.persistencia.dao.usuario;

import com.mycompany.gestaousuarios.conexao.ConexaoSQLite;
import com.mycompany.gestaousuarios.log.adapter.LogAdapter;
import com.mycompany.gestaousuarios.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDAO implements IUsuarioDAO{
    private Connection conexao;
    
    public UsuarioDAO() {
    }

    @Override
    public void save(Usuario usuario) throws SQLException {
        PreparedStatement ps = null;
        String query = ""
                .concat("\n INSERT INTO usuario(")
                .concat("\n     nm_usuario")
                .concat("\n     , nm_username")
                .concat("\n     , ds_senha")
                .concat("\n     , ds_email")
                .concat("\n     , fl_admin")
                .concat("\n     , fl_autorizado")
                .concat("\n     , dt_cadastro")
                .concat("\n     , dt_modificacao)")
                .concat("\n VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
        
        try {
            conexao = ConexaoSQLite.getConnection();
            ps = conexao.prepareStatement(query);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getLogin());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getEmail());
            ps.setBoolean(5, usuario.isAdmin());
            ps.setBoolean(6, usuario.isAutorizado());
            ps.setTimestamp(7, Timestamp.valueOf(usuario.getDataCadastro()));
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();  
        } catch (SQLException ex) {
            LogAdapter logAdapter = new LogAdapter();
            logAdapter.saveLogErro("Erro ao registrar usuário", usuario.getNome(), "Admin", ex.getMessage());
            throw new SQLException("Erro ao registrar o usuário.\n" + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps);
            LogAdapter logAdapter = new LogAdapter();
            logAdapter.saveLog("Usuário criado", "Admin", usuario.getNome());
        }
    }
    
    @Override
    public void update(Usuario usuario) throws SQLException {
        PreparedStatement ps = null;
        String query = ""
                .concat("\n UPDATE usuario ")
                .concat("\n SET nm_usuario = ?")
                .concat("\n 	, nm_username = ?")
                .concat("\n 	, ds_email = ?")
                .concat("\n 	, fl_admin = ?")
                .concat("\n 	, fl_autorizado = ?")
                .concat("\n 	, dt_modificacao = ?")
                .concat("\n WHERE id_usuario = ? AND fl_ativo = 1;");

        try {
            conexao = ConexaoSQLite.getConnection();
            ps = conexao.prepareStatement(query);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getLogin());
            ps.setString(3, usuario.getEmail());
            ps.setBoolean(4, usuario.isAdmin());
            ps.setBoolean(5, usuario.isAutorizado());
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(7, usuario.getId());

            ps.executeUpdate();  
        } catch (SQLException ex) {
            LogAdapter logAdapter = new LogAdapter();
            logAdapter.saveLogErro("Erro ao editar o usuário", usuario.getNome(), "Admin", ex.getMessage());
            throw new SQLException("Erro ao editar o usuário.\n" + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps);
            LogAdapter logAdapter = new LogAdapter();
            logAdapter.saveLog("Usuário editado", "Admin", usuario.getNome());
        }
    }
    
    @Override
    public void updateSenha(Usuario usuario) throws SQLException {
        PreparedStatement ps = null;
        String query = ""
                .concat("\n UPDATE usuario")
                .concat("\n SET ds_senha = ?")
                .concat("\n , dt_modificacao = ?")
                .concat("\n WHERE id_usuario = ? AND fl_ativo = 1;");
        try {
            conexao = ConexaoSQLite.getConnection();
            ps = conexao.prepareStatement(query);
            ps.setString(1, usuario.getSenha());
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(3, usuario.getId());

            ps.executeUpdate();  
        } catch (SQLException ex) {
            LogAdapter logAdapter = new LogAdapter();
            logAdapter.saveLogErro("Erro ao atualizar senha", usuario.getNome(), "Admin", ex.getMessage());
            throw new SQLException("Erro ao atualizar senha.\n" + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps);
            LogAdapter logAdapter = new LogAdapter();
            logAdapter.saveLog("Senha do usuário alterada", "Admin", usuario.getNome());
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        PreparedStatement ps = null;
        
        try {
            String query = ""
                .concat("\n UPDATE usuario")
                .concat("\n SET fl_ativo = 0,")
                .concat("\n dt_modificacao = ?")
                .concat("\n WHERE id_usuario = ?;");
            
            conexao = ConexaoSQLite.getConnection();
            
            ps = conexao.prepareStatement(query);
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(2, id);
            
            LogAdapter logAdapter = new LogAdapter();
            logAdapter.saveLog("Usuário excluido", "Admin", this.getByID(id).getNome());
            ps.executeUpdate();
        } catch (SQLException ex) {
            LogAdapter logAdapter = new LogAdapter();
            logAdapter.saveLogErro("Erro ao excluir usuário", this.getByID(id).getNome(), "Admin", ex.getMessage());
            throw new SQLException("Erro ao excluir usuário.\n"
                    + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps);
        }
    }

    @Override
    public Usuario getByID(long id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String query = ""
                .concat("\n SELECT ")
                .concat("\n     u.nm_usuario")
                .concat("\n     , u.nm_username")
                .concat("\n     , u.ds_senha")
                .concat("\n     , u.ds_email")
                .concat("\n     , u.fl_admin")
                .concat("\n     , u.fl_autorizado")
                .concat("\n     , u.dt_cadastro")
                .concat("\n     , u.dt_modificacao")
                .concat("\n FROM usuario u ")
                .concat("\n WHERE u.id_usuario = ? AND u.fl_ativo = 1;");
            
            conexao = ConexaoSQLite.getConnection();
            
            ps = conexao.prepareStatement(query);
            ps.setLong(1, id);
            
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                throw new SQLException("Usuário com id "
                        + id + "não encontrado");
            }
            
            String nome = rs.getString(1);
            String login = rs.getString(2);
            String senha = rs.getString(3);
            String email = rs.getString(4);
            boolean isAdmin = rs.getBoolean(5);
            boolean isAutorizado = rs.getBoolean(6);
            LocalDateTime dataCadastro = rs.getTimestamp(7).toLocalDateTime();
            LocalDateTime dataModificacao = rs.getTimestamp(8).toLocalDateTime();
           
            return new Usuario(
                id, 
                nome, 
                login, 
                senha, 
                email, 
                isAdmin, 
                isAutorizado, 
                dataModificacao, 
                dataCadastro
            );
        } catch (SQLException ex) {
            throw new SQLException("Erro ao buscar usuário.\n"
                    + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps, rs);
        }
    }

    @Override
    public List<Usuario> getByNome(String nome) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<>();
        
        try {
            String query = ""
                .concat("\n SELECT ")
                .concat("\n     u.id_usuario")
                .concat("\n     , u.nm_usuario")
                .concat("\n     , u.nm_username")
                .concat("\n     , u.ds_email")
                .concat("\n     , u.fl_admin")
                .concat("\n     , u.fl_autorizado")
                .concat("\n     , u.dt_cadastro")
                .concat("\n     , u.dt_modificacao")
                .concat("\n FROM usuario u ")
                .concat("\n WHERE u.nm_usuario LIKE ? AND u.fl_ativo = 1;");
            
            conexao = ConexaoSQLite.getConnection();
            
            ps = conexao.prepareStatement(query);
            ps.setString(1, "%" + nome + "%");
            
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                throw new SQLException("Usuário com nome "
                        + nome + "não encontrado");
            }
            
            do{
                Long id = rs.getLong(1);
                String nomeResult = rs.getString(2);
                String login = rs.getString(3);
                String email = rs.getString(4);
                boolean isAdmin = rs.getBoolean(5);
                boolean isAutorizado = rs.getBoolean(6);
                LocalDateTime dataCadastro = rs.getTimestamp(7).toLocalDateTime();
                LocalDateTime dataModificacao = rs.getTimestamp(8).toLocalDateTime();
                usuarios.add(
                    new Usuario(
                        id, 
                        nomeResult, 
                        login, 
                        "", 
                        email, 
                        isAdmin, 
                        isAutorizado, 
                        dataModificacao, 
                        dataCadastro
                    )
                );
            }while(rs.next());
            
           
            return usuarios;
        } catch (SQLException ex) {
            throw new SQLException("Erro ao buscar usuário.\n"
                    + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps, rs);
        }
    }
    

    @Override
    public Usuario getByUsername(String username) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuario usuario;
        
        try {
            String query = ""
                .concat("\n SELECT ")
                .concat("\n     u.id_usuario")
                .concat("\n     , u.nm_usuario")
                .concat("\n     , u.nm_username")
                .concat("\n     , u.ds_email")
                .concat("\n     , u.fl_admin")
                .concat("\n     , u.fl_autorizado")
                .concat("\n     , u.dt_cadastro")
                .concat("\n     , u.dt_modificacao")
                .concat("\n FROM usuario u ")
                .concat("\n WHERE u.nm_username LIKE ? AND u.fl_ativo = 1;");
            
            conexao = ConexaoSQLite.getConnection();
            
            ps = conexao.prepareStatement(query);
            ps.setString(1, "%" + username + "%");
            
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                throw new SQLException("Usuário com username "
                        + username + "não encontrado");
            }
            

            Long id = rs.getLong(1);
            String nomeResult = rs.getString(2);
            String login = rs.getString(3);
            String email = rs.getString(4);
            boolean isAdmin = rs.getBoolean(5);
            boolean isAutorizado = rs.getBoolean(6);
            LocalDateTime dataCadastro = rs.getTimestamp(7).toLocalDateTime();
            LocalDateTime dataModificacao = rs.getTimestamp(8).toLocalDateTime();

            usuario = new Usuario(
                    id, 
                    nomeResult, 
                    login, 
                    "", 
                    email, 
                    isAdmin, 
                    isAutorizado, 
                    dataModificacao, 
                    dataCadastro
            );

           
            return usuario;
        } catch (SQLException ex) {
            throw new SQLException("Erro ao buscar usuário.\n"
                    + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps, rs);
        }
    }
    
    
    @Override
    public List<Usuario> getAll() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<Usuario> usuarios = new ArrayList<>();
        
        try {
            String query = ""
                .concat("\n SELECT ")
                .concat("\n     u.id_usuario")
                .concat("\n     , u.nm_usuario")
                .concat("\n     , u.nm_username")
                .concat("\n     , u.ds_senha")
                .concat("\n     , u.ds_email")
                .concat("\n     , u.fl_admin")
                .concat("\n     , u.fl_autorizado")
                .concat("\n     , u.dt_cadastro")
                .concat("\n     , u.dt_modificacao")
                .concat("\n FROM usuario u")
                .concat("\n WHERE u.fl_ativo = 1;");
            conexao = ConexaoSQLite.getConnection();
            
            ps = conexao.prepareStatement(query);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                do{
                    Long id = rs.getLong(1);
                    String nome = rs.getString(2);
                    String login = rs.getString(3);
                    String senha = rs.getString(4);
                    String email = rs.getString(5);
                    boolean isAdmin = rs.getBoolean(6);
                    boolean isAutorizado = rs.getBoolean(7);
                    LocalDateTime dataCadastro = rs.getTimestamp(8).toLocalDateTime();
                    LocalDateTime dataModificacao = rs.getTimestamp(9).toLocalDateTime();

                    usuarios.add(
                        new Usuario(
                            id, 
                            nome, 
                            login, 
                            senha, 
                            email, 
                            isAdmin, 
                            isAutorizado, 
                            dataModificacao, 
                            dataCadastro
                        )
                    );
                }while(rs.next());
            }
            return usuarios;
        } catch (SQLException ex) {
            throw new SQLException("Erro ao buscar usuários.\n"
                    + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps, rs);
        }
    }  

    @Override
    public void autorizeUsuario(long id) throws SQLException {
        PreparedStatement ps = null;
        String query = ""
                .concat("\n UPDATE usuario SET")
                .concat("\n fl_autorizado = ?")
                .concat("\n , dt_modificacao = ?")
                .concat("\n WHERE id_usuario = ?;");
        
        try {
            conexao = ConexaoSQLite.getConnection();
            ps = conexao.prepareStatement(query);
            ps.setBoolean(1, true);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(3, id);

            ps.executeUpdate();  
        } catch (SQLException ex) {
            LogAdapter logAdapter = new LogAdapter();
            logAdapter.saveLogErro("Erro ao autorizar usuário", this.getByID(id).getNome(), "Admin", ex.getMessage());
            throw new SQLException("Erro ao autorizar usuário.\n" + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps);
            LogAdapter logAdapter = new LogAdapter();
            logAdapter.saveLog("Usuário autorizado", "Admin", this.getByID(id).getNome());
        }
    }

    @Override
    public List<Usuario> getAllByFlagAutorizado(boolean isAutorizado) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<Usuario> usuarios = new ArrayList<>();
        
        try {
            String query = ""
                .concat("\n SELECT ")
                .concat("\n     u.id_usuario")
                .concat("\n     , u.nm_usuario")
                .concat("\n     , u.nm_username")
                .concat("\n     , u.ds_senha")
                .concat("\n     , u.ds_email")
                .concat("\n     , u.fl_admin")
                .concat("\n     , u.fl_autorizado")
                .concat("\n     , u.dt_cadastro")
                .concat("\n     , u.dt_modificacao")
                .concat("\n FROM usuario u ")
                .concat("\n WHERE fl_autorizado = ? AND u.fl_ativo = 1;");
            
            conexao = ConexaoSQLite.getConnection();
            
            ps = conexao.prepareStatement(query);
            ps.setBoolean(1, isAutorizado);

            rs = ps.executeQuery();
            
            if (!rs.next()) {
                throw new SQLException("Não há usuários cadastrados");
            }
            
            do{
                Long id = rs.getLong(1);
                String nome = rs.getString(2);
                String login = rs.getString(3);
                String senha = rs.getString(4);
                String email = rs.getString(5);
                boolean isAdmin = rs.getBoolean(6);
                boolean autorizado = rs.getBoolean(7);
                LocalDateTime dataCadastro = rs.getTimestamp(8).toLocalDateTime();
                LocalDateTime dataModificacao = rs.getTimestamp(9).toLocalDateTime();

                usuarios.add(
                    new Usuario(
                        id, 
                        nome, 
                        login, 
                        senha, 
                        email, 
                        isAdmin, 
                        autorizado, 
                        dataModificacao, 
                        dataCadastro
                    )
                );
            }while(rs.next());
   
            return usuarios;
        } catch (SQLException ex) {
            throw new SQLException("Erro ao buscar usuários.\n"
                    + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps, rs);
        }
    }

    @Override
    public List<Usuario> getAllByFlagAdmin(boolean isAdmin) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<Usuario> usuarios = new ArrayList<>();
        
        try {
            String query = ""
                .concat("\n SELECT ")
                .concat("\n     u.id_usuario")
                .concat("\n     , u.nm_usuario")
                .concat("\n     , u.nm_username")
                .concat("\n     , u.ds_senha")
                .concat("\n     , u.ds_email")
                .concat("\n     , u.fl_admin")
                .concat("\n     , u.fl_autorizado")
                .concat("\n     , u.dt_cadastro")
                .concat("\n     , u.dt_modificacao")
                .concat("\n FROM usuario u ")
                .concat("\n WHERE fl_admin = ? AND u.fl_ativo = 1;");
            
            conexao = ConexaoSQLite.getConnection();
            
            ps = conexao.prepareStatement(query);
            ps.setBoolean(1, isAdmin);

            rs = ps.executeQuery();
            
            if (!rs.next()) {
                throw new SQLException("Não há usuários cadastrados");
            }
            
            do{
                Long id = rs.getLong(1);
                String nome = rs.getString(2);
                String login = rs.getString(3);
                String senha = rs.getString(4);
                String email = rs.getString(5);
                boolean admin = rs.getBoolean(6);
                boolean autorizado = rs.getBoolean(7);
                LocalDateTime dataCadastro = rs.getTimestamp(8).toLocalDateTime();
                LocalDateTime dataModificacao = rs.getTimestamp(9).toLocalDateTime();

                usuarios.add(
                    new Usuario(
                        id, 
                        nome, 
                        login, 
                        senha, 
                        email, 
                        admin, 
                        autorizado, 
                        dataModificacao, 
                        dataCadastro
                    )
                );
            }while(rs.next());
   
            return usuarios;
        } catch (SQLException ex) {
            throw new SQLException("Erro ao buscar usuários.\n"
                    + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps, rs);
        }
    }

    @Override
    public boolean isAdmin(Usuario usuario) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String query = ""
                .concat("\n SELECT u.fl_admin")
                .concat("\n FROM usuario u ")
                .concat("\n WHERE u.nm_username = ? AND u.fl_ativo = 1;");
            
            conexao = ConexaoSQLite.getConnection();
            
            ps = conexao.prepareStatement(query);
            ps.setString(1, usuario.getLogin());
            
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                throw new SQLException("Usuário não encontrado");
            }

            return rs.getBoolean(1);
            
        } catch (SQLException ex) {
            throw new SQLException("Erro ao buscar usuário.\n"
                    + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps, rs);
        }
    }
    
    @Override
    public boolean isAutorizado(Usuario usuario) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String query = ""
                .concat("\n SELECT u.fl_autorizado")
                .concat("\n FROM usuario u ")
                .concat("\n WHERE u.nm_username = ? AND u.fl_ativo = 1;");
            
            conexao = ConexaoSQLite.getConnection();
            
            ps = conexao.prepareStatement(query);
            ps.setString(1, usuario.getLogin());
            
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                throw new SQLException("Usuário não encontrado");
            }
            
            boolean result = rs.getBoolean(1);

            return result;
            
        } catch (SQLException ex) {
            throw new SQLException("Erro ao buscar usuário.\n"
                    + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps, rs);
        }
    }

    @Override
    public Usuario login(String login, String senha) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String query = ""
                .concat("\n SELECT ")
                .concat("\n     u.id_usuario")
                .concat("\n     , u.nm_usuario")
                .concat("\n     , u.ds_email")
                .concat("\n     , u.fl_admin")
                .concat("\n     , u.fl_autorizado")
                .concat("\n     , u.dt_cadastro")
                .concat("\n     , u.dt_modificacao")
                .concat("\n FROM usuario u ")
                .concat("\n WHERE u.nm_username = ?")
                .concat("\n AND u.ds_senha = ? AND u.fl_ativo = 1;");
            
            conexao = ConexaoSQLite.getConnection();
            
            ps = conexao.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, senha);
            
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                throw new SQLException("Usuário não encontrado");
            }
            
            long id = rs.getLong(1);
            String nome = rs.getString(2);
            String email = rs.getString(3);
            boolean isAdmin = rs.getBoolean(4);
            boolean isAutorizado = rs.getBoolean(5);
            LocalDateTime dataCadastro = rs.getTimestamp(6).toLocalDateTime();
            LocalDateTime dataModificacao = rs.getTimestamp(7).toLocalDateTime();
           
            return new Usuario(
                id, 
                nome, 
                login, 
                senha, 
                email, 
                isAdmin, 
                isAutorizado, 
                dataModificacao, 
        LocalDateTime.now(),
                dataCadastro
            );
        } catch (SQLException ex) {
            throw new SQLException("Erro ao buscar usuário.\n"
                    + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps, rs);
        }
    } 

    @Override
    public int getTotalNotifications(Usuario usuario) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String query = ""
                .concat("\n SELECT ")
                .concat("\n 	COUNT(un.id_notificacao) qtd_notificacoes ")
                .concat("\n FROM usuario u")
                .concat("\n LEFT JOIN usuarios_notificados un ")
                .concat("\n ON u.id_usuario = un.id_destinatario")
                .concat("\n WHERE u.id_usuario = ? AND un.fl_lida = 0;");

            
            conexao = ConexaoSQLite.getConnection();
            
            ps = conexao.prepareStatement(query);
            ps.setLong(1, usuario.getId());
            
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                throw new SQLException("Usuário não encontrado");
            }
            
            int result = rs.getInt(1);

            return result;
            
        } catch (SQLException ex) {
            throw new SQLException("Erro ao buscar usuário.\n"
                    + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps, rs);
        }
    }
    
}
