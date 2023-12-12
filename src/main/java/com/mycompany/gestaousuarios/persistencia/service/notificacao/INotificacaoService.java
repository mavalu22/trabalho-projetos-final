package com.mycompany.gestaousuarios.persistencia.service.notificacao;

import com.mycompany.gestaousuarios.model.Notificacao;
import com.mycompany.gestaousuarios.model.NotificacaoDTO;
import com.mycompany.gestaousuarios.model.Usuario;
import java.sql.SQLException;
import java.util.List;


public interface INotificacaoService {
    void notificarNovoUsuario(
            Usuario remetenteSemId
    ) throws SQLException;
    void salvar(Notificacao notificacao) throws SQLException;
    List<Usuario> buscarAdmins() throws SQLException;
    Usuario buscarPorUsername(String username) throws SQLException;
    List<NotificacaoDTO> buscarTodasNotificacoes() throws SQLException;
    List<NotificacaoDTO> buscarNotificacoesPorUsername(String username) throws SQLException;
    int totalNotificacoes(String username) throws SQLException;
    NotificacaoDTO buscarNotificacaoPorID(String idNotificacao) throws SQLException;
    void visualizarNotificacao(String idNotificacao) throws SQLException;
}
