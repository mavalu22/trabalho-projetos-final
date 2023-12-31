package com.mycompany.gestaousuarios.persistencia.dao.notificacao;

import com.mycompany.gestaousuarios.conexao.ConexaoSQLite;
import com.mycompany.gestaousuarios.log.adapter.LogAdapter;
import com.mycompany.gestaousuarios.model.Notificacao;
import com.mycompany.gestaousuarios.model.NotificacaoDTO;
import com.mycompany.gestaousuarios.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class NotificacaoDAO implements INotificacaoDAO{
    private Connection conexao;
    
    public NotificacaoDAO() {
    }
    
    @Override
    public void notifyNewUser(Usuario remetente, List<Usuario> destinatarios, Notificacao notificacao) throws SQLException {
        PreparedStatement ps = null;
        try {
            conexao = ConexaoSQLite.getConnection();
            
            for(Usuario destinatario : destinatarios){
                String query = ""
                    .concat("\n insert into usuarios_notificados (")
                    .concat("\n     id_notificacao")
                    .concat("\n     , id_destinatario")
                    .concat("\n     , id_remetente")
                    .concat("\n     , fl_lida")
                    .concat("\n     , dt_envio")
                    .concat("\n     , dt_visualizacao)")
                    .concat("\n values(?,?,?,?,?,?);");

                ps = conexao.prepareStatement(query);
                ps.setTimestamp(1, Timestamp.valueOf(notificacao.getIdNotificacao()));
                ps.setLong(2, destinatario.getId());
                ps.setLong(3, remetente.getId());
                ps.setBoolean(4, false);
                ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                ps.setTimestamp(6, null);
                ps.executeUpdate();
                LogAdapter logAdapter = new LogAdapter();
                logAdapter.saveLog("Usuário notificado", remetente.getNome(), destinatario.getNome());
            }
        } catch (SQLException ex) {
            LogAdapter logAdapter = new LogAdapter();
            logAdapter.saveLogErro("Erro ao notificar usuário", remetente.getNome(), "", ex.getMessage());
            throw new SQLException("Erro ao registrar o usuário.\n" + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps);
            
        }

    }

    @Override
    public void save(Notificacao notificacao) throws SQLException {
        PreparedStatement ps = null;
        String query = ""
            .concat("\n insert into notificacao(")
            .concat("\n     ds_mensagem")
            .concat("\n     , fl_tipo")
            .concat("\n     , id_notificacao) ")
            .concat("\n values (?, ?, ?);");
        try {
            conexao = ConexaoSQLite.getConnection();
            ps = conexao.prepareStatement(query);
            ps.setString(1, notificacao.getMensagem());
            ps.setInt(2, notificacao.getTipo());
            ps.setTimestamp(3, Timestamp.valueOf(notificacao.getIdNotificacao()));
            ps.executeUpdate();  
        } catch (SQLException ex) {
            throw new SQLException("Erro ao registrar o usuário.\n" + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps);
        }
    }

    @Override
    public List<NotificacaoDTO> getAllNotifies() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<NotificacaoDTO> notificacoes = new ArrayList<>();
        
        try {
            String query = ""
                .concat("\n SELECT")
                .concat("\n 	un.id_notificacao")
                .concat("\n 	, un.id_remetente")
                .concat("\n 	, r.nm_username remetente")
                .concat("\n 	, un.id_destinatario")
                .concat("\n 	, d.nm_username destinatario")
                .concat("\n 	, un.fl_lida")
                .concat("\n 	, un.dt_visualizacao")
                .concat("\n 	, un.dt_envio")
                .concat("\n , n.ds_mensagem")
                .concat("\n , n.fl_tipo")
                .concat("\n FROM usuarios_notificados un")
                .concat("\n LEFT join usuario d ON un.id_destinatario = d.id_usuario")
                .concat("\n LEFT join usuario r ON un.id_remetente = r.id_usuario")
                .concat("\n LEFT join notificacao n ON un.id_notificacao = n.id_notificacao")
                .concat("\n WHERE d.fl_ativo = 1 AND r.fl_ativo = 1");
            conexao = ConexaoSQLite.getConnection();
            
            ps = conexao.prepareStatement(query);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                do{
                    LocalDateTime idNotificacao = rs.getTimestamp(1).toLocalDateTime();
                    Long idRemetente = rs.getLong(2);
                    String remetenteUsername = rs.getString(3);
                    Long idDestinatario = rs.getLong(4);
                    String destinatarioUsername = rs.getString(5);
                    boolean flLida = rs.getBoolean(6);
                    LocalDateTime dataVisualizacao;
                    try{
                        dataVisualizacao = rs.getTimestamp(7).toLocalDateTime();
                    }catch(Exception e){
                        dataVisualizacao = null;
                    }
                    LocalDateTime dataEnvio = rs.getTimestamp(8).toLocalDateTime();
                    String mensagem = rs.getString(9);
                    int flTipo = rs.getInt(10);

                    notificacoes.add(
                        new NotificacaoDTO(
                            idNotificacao, 
                            idRemetente, 
                            remetenteUsername, 
                            idDestinatario, 
                            destinatarioUsername, 
                            flLida, 
                            dataVisualizacao,
                            dataEnvio,
                            mensagem,
                            flTipo
                        )
                    );
                }while(rs.next());
            }
            return notificacoes;
        } catch (SQLException ex) {
            throw new SQLException("Erro ao buscar notificacões.\n"
                    + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps, rs);
        }
    }

    @Override
    public List<NotificacaoDTO> getNotifiesByUsername(String username) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<NotificacaoDTO> notificacoes = new ArrayList<>();
        
        try {
            String query = ""
                .concat("\n SELECT")
                .concat("\n 	un.id_notificacao")
                .concat("\n 	, un.id_remetente")
                .concat("\n 	, r.nm_username remetente")
                .concat("\n 	, un.id_destinatario")
                .concat("\n 	, d.nm_username destinatario")
                .concat("\n 	, un.fl_lida")
                .concat("\n 	, un.dt_visualizacao")
                .concat("\n 	, un.dt_envio")
                .concat("\n , n.ds_mensagem")
                .concat("\n , n.fl_tipo")
                .concat("\n FROM usuarios_notificados un")
                .concat("\n LEFT join usuario d ON un.id_destinatario = d.id_usuario")
                .concat("\n LEFT join usuario r ON un.id_remetente = r.id_usuario")
                .concat("\n LEFT join notificacao n ON un.id_notificacao = n.id_notificacao")
                .concat("\n WHERE d.fl_ativo = 1")
                .concat("\n AND r.fl_ativo = 1")
                .concat("\n AND d.nm_username = ?");
            
            conexao = ConexaoSQLite.getConnection();
            
            ps = conexao.prepareStatement(query);
            
            ps.setString(1, username);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                do{
                    LocalDateTime idNotificacao = rs.getTimestamp(1).toLocalDateTime();
                    Long idRemetente = rs.getLong(2);
                    String remetenteUsername = rs.getString(3);
                    Long idDestinatario = rs.getLong(4);
                    String destinatarioUsername = rs.getString(5);
                    boolean flLida = rs.getBoolean(6);
                    LocalDateTime dataVisualizacao;
                    try{
                        dataVisualizacao = rs.getTimestamp(7).toLocalDateTime();
                    }catch(Exception e){
                        dataVisualizacao = null;
                    }
                    
                    LocalDateTime dataEnvio = rs.getTimestamp(8).toLocalDateTime();
                    
                    String mensagem = rs.getString(9);
                    int flTipo = rs.getInt(10);
                    
                    notificacoes.add(
                        new NotificacaoDTO(
                            idNotificacao, 
                            idRemetente, 
                            remetenteUsername, 
                            idDestinatario, 
                            destinatarioUsername, 
                            flLida, 
                            dataVisualizacao,
                            dataEnvio,
                            mensagem,
                            flTipo
                        )
                    );
                }while(rs.next());
            }
            return notificacoes;
        } catch (SQLException ex) {
            throw new SQLException("Erro ao buscar notificacões.\n"
                    + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps, rs);
        }
    }

    @Override
    public int getTotalNotifies(String username) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String query = ""
                .concat("\n SELECT ")
                .concat("\n     COUNT(un.id_notificacao) qtd_notificacoes ")
                .concat("\n FROM usuario d")
                .concat("\n LEFT JOIN usuarios_notificados un ")
                .concat("\n     ON d.id_usuario = un.id_destinatario")
                .concat("\n LEFT JOiN usuario r ")
                .concat("\n     ON r.id_usuario = un.id_remetente ")
                .concat("\n WHERE d.nm_username = ?")
                .concat("\n     AND un.fl_lida = 0 ")
                .concat("\n     AND r.fl_ativo = 1");

            conexao = ConexaoSQLite.getConnection();
            
            ps = conexao.prepareStatement(query);
            ps.setString(1, username);
            
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                throw new SQLException("Notificações não encontradas");
            }
            
            int result = rs.getInt(1);

            return result;
            
        } catch (SQLException ex) {
            throw new SQLException("Erro ao buscar notificações.\n"
                    + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps, rs);
        }
    }

    @Override
    public NotificacaoDTO getNotficacaoByID(String idNotificacao) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        NotificacaoDTO notificacao;
        
        try {
            String query = ""
                .concat("\n SELECT")
                .concat("\n 	un.id_notificacao")
                .concat("\n 	, un.id_remetente")
                .concat("\n 	, r.nm_username remetente")
                .concat("\n 	, un.id_destinatario")
                .concat("\n 	, d.nm_username destinatario")
                .concat("\n 	, un.fl_lida")
                .concat("\n 	, un.dt_visualizacao")
                .concat("\n 	, un.dt_envio")
                .concat("\n , n.ds_mensagem")
                .concat("\n , n.fl_tipo")
                .concat("\n FROM usuarios_notificados un")
                .concat("\n LEFT join usuario d ON un.id_destinatario = d.id_usuario")
                .concat("\n LEFT join usuario r ON un.id_remetente = r.id_usuario")
                .concat("\n LEFT join notificacao n ON un.id_notificacao = n.id_notificacao")
                .concat("\n WHERE un.id_notificacao = ?");
            
            conexao = ConexaoSQLite.getConnection();
            
            ps = conexao.prepareStatement(query);
            
            ps.setString(1, idNotificacao);
            
            rs = ps.executeQuery();
            if (!rs.next())
                throw new SQLException("Usuário possui notificações");

            LocalDateTime Notificacao = rs.getTimestamp(1).toLocalDateTime();
            Long idRemetente = rs.getLong(2);
            String remetenteUsername = rs.getString(3);
            Long idDestinatario = rs.getLong(4);
            String destinatarioUsername = rs.getString(5);
            boolean flLida = rs.getBoolean(6);
            LocalDateTime dataVisualizacao;
            try{
                dataVisualizacao = rs.getTimestamp(7).toLocalDateTime();
            }catch(Exception e){
                dataVisualizacao = null;
            }

            LocalDateTime dataEnvio = rs.getTimestamp(8).toLocalDateTime();

            String mensagem = rs.getString(9);
            int flTipo = rs.getInt(10);

            notificacao = new NotificacaoDTO(
                Notificacao, 
                idRemetente, 
                remetenteUsername, 
                idDestinatario, 
                destinatarioUsername, 
                flLida, 
                dataVisualizacao,
                dataEnvio,
                mensagem,
                flTipo
            );
            
            return notificacao;
        } catch (SQLException ex) {
            throw new SQLException("Erro ao buscar notificacões.\n"
                    + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps, rs);
        }
    }

    @Override
    public void readNotificacao(String idNotificacao) throws SQLException {
        PreparedStatement ps = null;
        String query = ""
                .concat("\n UPDATE usuarios_notificados ")
                .concat("\n SET fl_lida = 1")
                .concat("\n , dt_visualizacao = ?")
                .concat("\n WHERE id_notificacao = ?");
        try {
            conexao = ConexaoSQLite.getConnection();
            ps = conexao.prepareStatement(query);
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(2, idNotificacao);

            ps.executeUpdate();  
        } catch (SQLException ex) {
            LogAdapter logAdapter = new LogAdapter();
            logAdapter.saveLogErro("Erro ao ler notificação", getNotficacaoByID(idNotificacao).getRemetenteUsername(), getNotficacaoByID(idNotificacao).getDestinatarioUsername(), ex.getMessage());
            throw new SQLException("Erro ao usuário senha.\n" + ex.getMessage());
        } finally {
            ConexaoSQLite.closeConnection(conexao, ps);
            LogAdapter logAdapter = new LogAdapter();
            logAdapter.saveLog("Usuário leu a notificação", getNotficacaoByID(idNotificacao).getRemetenteUsername(), getNotficacaoByID(idNotificacao).getDestinatarioUsername());            
        }
    }
}
