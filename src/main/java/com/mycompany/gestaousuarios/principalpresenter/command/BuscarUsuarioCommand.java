package com.mycompany.gestaousuarios.principalpresenter.command;

import com.mycompany.gestaousuarios.presenter.BuscarUsuarioPresenter;
import com.mycompany.gestaousuarios.presenter.PrincipalPresenter;
import com.mycompany.gestaousuarios.view.PrincipalView;


public class BuscarUsuarioCommand extends PrincipalPresenterCommand{
    private BuscarUsuarioPresenter buscarUsuarioPresenter;

    public BuscarUsuarioCommand(
            BuscarUsuarioPresenter buscarUsuarioPresenter, 
            PrincipalPresenter presenter, 
            PrincipalView principalView
    ) {
        super(presenter, principalView);
        this.buscarUsuarioPresenter = buscarUsuarioPresenter;
    }

    @Override
    public void executar() {
        buscarUsuarioPresenter.fechar();
        principalView.getDpMenu().remove(buscarUsuarioPresenter.getView());
        buscarUsuarioPresenter.atualizarTabela();
        principalView.getDpMenu().add(buscarUsuarioPresenter.getView());
        buscarUsuarioPresenter.getView().setVisible(true);
    }
    
    
}
