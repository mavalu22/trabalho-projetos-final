package com.mycompany.gestaousuarios.principalpresenter.command;

import com.mycompany.gestaousuarios.presenter.LoginPresenter;
import com.mycompany.gestaousuarios.presenter.PrincipalPresenter;
import com.mycompany.gestaousuarios.view.PrincipalView;


public class LoginCommand extends PrincipalPresenterCommand{
    private LoginPresenter loginPresenter;

    public LoginCommand(
            LoginPresenter loginPresenter, 
            PrincipalPresenter presenter, 
            PrincipalView principalView) 
    {
        super(presenter, principalView);
        this.loginPresenter = loginPresenter;
    }
    

    @Override
    public void executar() {
        loginPresenter.limparCampos();
        principalView.getDpMenu().remove(loginPresenter.getView());
        principalView.getDpMenu().add(loginPresenter.getView());
        loginPresenter.getView().setVisible(true);
    }
    
    
}
