package com.mycompany.gestaousuarios.manterusuariopresenter.command;

import com.mycompany.gestaousuarios.presenter.ManterUsuarioPresenter;
import java.sql.SQLException;


public class AlterarSenhaCommand extends ManterUsuarioCommand{

    public AlterarSenhaCommand(ManterUsuarioPresenter presenter) {
        super(presenter);
    }

    @Override
    public void executar() throws SQLException {
        presenter.getUsuarioService().atualizarSenha(presenter.getUsuario());
    }
    
    
}
