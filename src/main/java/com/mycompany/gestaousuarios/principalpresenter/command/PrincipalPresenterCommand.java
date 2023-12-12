package com.mycompany.gestaousuarios.principalpresenter.command;

import com.mycompany.gestaousuarios.presenter.PrincipalPresenter;
import com.mycompany.gestaousuarios.view.PrincipalView;


public abstract class PrincipalPresenterCommand {
    protected PrincipalPresenter presenter;
    protected PrincipalView principalView;

    public PrincipalPresenterCommand(PrincipalPresenter presenter, PrincipalView principalView) {
        this.presenter = presenter;
        this.principalView = principalView;
    }
    
    public abstract void executar();
}
