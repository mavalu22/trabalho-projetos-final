package com.mycompany.gestaousuarios.principalpresenter.command;

import com.mycompany.gestaousuarios.presenter.PrincipalPresenter;
import com.mycompany.gestaousuarios.presenter.VisualizarNotificacoesPresenter;
import com.mycompany.gestaousuarios.view.PrincipalView;


public class VisualizarNotificacoesCommand extends PrincipalPresenterCommand{
    private VisualizarNotificacoesPresenter notificacoesPresenter;
    
    public VisualizarNotificacoesCommand(
            PrincipalPresenter presenter, 
            PrincipalView principalView, 
            VisualizarNotificacoesPresenter notificacoesPresenter
    ) {
        super(presenter, principalView);
        this.notificacoesPresenter = notificacoesPresenter;
    }

    @Override
    public void executar() {
        principalView.getDpMenu().remove(notificacoesPresenter.getView());
        notificacoesPresenter.atualizarTabela();
        principalView.getDpMenu().add(notificacoesPresenter.getView());
        notificacoesPresenter.getView().setVisible(true);
    }
    
    
}
