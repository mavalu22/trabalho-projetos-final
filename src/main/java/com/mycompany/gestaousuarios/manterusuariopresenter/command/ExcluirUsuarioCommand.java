package com.mycompany.gestaousuarios.manterusuariopresenter.command;

import com.mycompany.gestaousuarios.presenter.ManterUsuarioPresenter;
import java.sql.SQLException;


public class ExcluirUsuarioCommand extends ManterUsuarioCommand{

    public ExcluirUsuarioCommand(ManterUsuarioPresenter presenter) {
        super(presenter);
    }

    @Override
    public void executar() throws SQLException {
        presenter.getUsuarioService().deletar(presenter.getUsuario().getId());
    }
    
    
    
}
