package com.mycompany.gestaousuarios.persistencia.repository.usuario;

import com.mycompany.gestaousuarios.persistencia.dao.usuario.IUsuarioDAO;
import com.mycompany.gestaousuarios.persistencia.dao.usuario.UsuarioDAO;
import com.mycompany.gestaousuarios.model.Usuario;
import java.sql.SQLException;
import java.util.List;


public class UsuarioRepository implements IUsuarioRepository{
    private IUsuarioDAO usuarioDAO;
    
    public UsuarioRepository() {
        this.usuarioDAO = new UsuarioDAO();
    }

    @Override
    public void salvar(Usuario usuario) throws SQLException {
        this.usuarioDAO.save(usuario);
    }

    @Override
    public void atualizarSenha(Usuario usuario) throws SQLException {
        this.usuarioDAO.updateSenha(usuario);
    }

    @Override
    public void deletar(long id) throws SQLException {
       this.usuarioDAO.delete(id);
    }

    @Override
    public Usuario buscarPorID(long id) throws SQLException {
        return this.usuarioDAO.getByID(id);
    }

    @Override
    public List<Usuario> buscarTodos() throws SQLException {
        return this.usuarioDAO.getAll();
    }

    @Override
    public void autorizarUsuario(long id) throws SQLException {
        this.usuarioDAO.autorizeUsuario(id);
    }

    @Override
    public List<Usuario> buscarTodosPorFlagAutorizado(boolean isAutorizado) throws SQLException {
        return this.usuarioDAO.getAllByFlagAutorizado(isAutorizado);
    }

    @Override
    public List<Usuario> buscarTodosPorFlagAdmin(boolean isAdmin) throws SQLException {
        return this.usuarioDAO.getAllByFlagAdmin(isAdmin);
    }

    @Override
    public boolean isAdmin(Usuario usuario) throws SQLException {
        return this.usuarioDAO.isAdmin(usuario);
    }

    @Override
    public boolean isAutorizado(Usuario usuario) throws SQLException {
        return this.usuarioDAO.isAutorizado(usuario);
    }

    @Override
    public Usuario login(String login, String senha) throws SQLException {
        return this.usuarioDAO.login(login, senha);
    }

    @Override
    public int buscarTotalNotificacoes(Usuario usuario) throws SQLException {
        return this.usuarioDAO.getTotalNotifications(usuario);
    }

    @Override
    public void atualizarUsuario(Usuario usuario) throws SQLException {
        this.usuarioDAO.update(usuario);
    }

    @Override
    public List<Usuario> buscarPorNome(String nome) throws SQLException {
        return this.usuarioDAO.getByNome(nome);
    }
    
}
