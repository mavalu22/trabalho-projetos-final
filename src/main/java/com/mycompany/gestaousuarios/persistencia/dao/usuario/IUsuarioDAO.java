package com.mycompany.gestaousuarios.persistencia.dao.usuario;

import com.mycompany.gestaousuarios.model.Usuario;
import java.sql.SQLException;
import java.util.List;


public interface IUsuarioDAO {
    void save(Usuario usuario) throws SQLException;
    void updateSenha(Usuario usuario) throws SQLException;
    void delete(long id) throws SQLException;
    Usuario getByID(long id) throws SQLException;
    List<Usuario> getAll() throws SQLException;  
    void autorizeUsuario(long id) throws SQLException;
    List<Usuario> getAllByFlagAutorizado(boolean isAutorizado) throws SQLException;
    List<Usuario> getAllByFlagAdmin(boolean isAdmin) throws SQLException;  
    boolean isAdmin(Usuario usuario) throws SQLException;
    boolean isAutorizado(Usuario usuario) throws SQLException;
    Usuario login(String login, String senha) throws SQLException;
    int getTotalNotifications(Usuario usuario) throws SQLException;
    void update(Usuario usuario) throws SQLException;
    List<Usuario> getByNome(String nome) throws SQLException;
    Usuario getByUsername(String username) throws SQLException;
}
