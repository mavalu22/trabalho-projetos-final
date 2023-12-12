package com.mycompany.gestaousuarios.principalpresenter.command;

import com.mycompany.gestaousuarios.presenter.LogConfigPresenter;
import com.mycompany.gestaousuarios.presenter.PrincipalPresenter;
import com.mycompany.gestaousuarios.view.PrincipalView;

public class LogConfigCommand extends PrincipalPresenterCommand{
    private LogConfigPresenter logConfigPresenter;
    
    public LogConfigCommand(
            PrincipalPresenter presenter, 
            PrincipalView principalView, 
            LogConfigPresenter logConfigPresenter) 
    {
        super(presenter, principalView);
        this.logConfigPresenter = logConfigPresenter;
    }
    
    @Override
    public void executar() {
        principalView.getDpMenu().remove(logConfigPresenter.getView());
        principalView.getDpMenu().add(logConfigPresenter.getView());
        logConfigPresenter.tipoLog();
        logConfigPresenter.getView().setVisible(true);
    }
    
}
