package com.mycompany.gestaousuarios.principalpresenter.command;

import com.mycompany.gestaousuarios.presenter.BuscarUsuarioPresenter;
import com.mycompany.gestaousuarios.presenter.ManterUsuarioPresenter;
import com.mycompany.gestaousuarios.presenter.PrincipalPresenter;
import com.mycompany.gestaousuarios.view.PrincipalView;


public class CadastrarUsuarioAdminCommand extends PrincipalPresenterCommand{
     private BuscarUsuarioPresenter buscarUsuarioPresenter;

    public CadastrarUsuarioAdminCommand(
            BuscarUsuarioPresenter buscarUsuarioPresenter, 
            PrincipalPresenter presenter, 
            PrincipalView principalView
    ) {
        super(presenter, principalView);
        this.buscarUsuarioPresenter = buscarUsuarioPresenter;
    }

    @Override
    public void executar() {
        ManterUsuarioPresenter manterUsuarioPresenter;
        manterUsuarioPresenter = new ManterUsuarioPresenter();
        manterUsuarioPresenter.subscribeNotificarUsuarioObserver(presenter);
        manterUsuarioPresenter.subscribeManterUsuarioObserver(buscarUsuarioPresenter);
        principalView.getDpMenu().add(manterUsuarioPresenter.getView());
        manterUsuarioPresenter.getView().setVisible(true);
    }
     
    
}
