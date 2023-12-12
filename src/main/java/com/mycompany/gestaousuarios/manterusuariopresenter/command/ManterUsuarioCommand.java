package com.mycompany.gestaousuarios.manterusuariopresenter.command;

import com.mycompany.gestaousuarios.presenter.ManterUsuarioPresenter;
import java.sql.SQLException;


public abstract class ManterUsuarioCommand {
    protected ManterUsuarioPresenter presenter;

    public ManterUsuarioCommand(ManterUsuarioPresenter presenter) {
        this.presenter = presenter;
    }
    
    public abstract void executar() throws SQLException;

    
}
