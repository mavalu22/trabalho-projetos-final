package com.mycompany.gestaousuarios.manterusuariopresenter.command;

import com.mycompany.gestaousuarios.model.Usuario;
import com.mycompany.gestaousuarios.presenter.ManterUsuarioPresenter;
import java.sql.SQLException;
import java.util.List;


public class SalvarUsuarioCommand extends ManterUsuarioCommand{

    public SalvarUsuarioCommand(ManterUsuarioPresenter presenter) {
        super(presenter);
    }

    @Override
    public void executar() throws SQLException{
        List<Usuario> usuarios = presenter.getUsuarioService().buscarTodos();
        Usuario usuario = presenter.getUsuario();
        if(usuarios.isEmpty()){
            usuario.setIsAdmin(true);
            usuario.setIsAutorizado(true);
        }
            
        presenter.getUsuarioService().salvar(usuario);
    }
    
}
