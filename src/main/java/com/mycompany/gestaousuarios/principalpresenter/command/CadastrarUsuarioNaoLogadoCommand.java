package com.mycompany.gestaousuarios.principalpresenter.command;

import com.mycompany.gestaousuarios.presenter.ManterUsuarioPresenter;
import com.mycompany.gestaousuarios.presenter.PrincipalPresenter;
import com.mycompany.gestaousuarios.view.PrincipalView;


public class CadastrarUsuarioNaoLogadoCommand extends PrincipalPresenterCommand{
    private ManterUsuarioPresenter manterUsuarioPresenter;
            
    public CadastrarUsuarioNaoLogadoCommand(
            PrincipalPresenter presenter, 
            PrincipalView principalView, 
            ManterUsuarioPresenter manterUsuarioPresenter) 
    {
        super(presenter, principalView);
        this.manterUsuarioPresenter = manterUsuarioPresenter;
    }

    @Override
    public void executar() {
        manterUsuarioPresenter.limparCampos();
        principalView.getDpMenu().remove(manterUsuarioPresenter.getView());
        principalView.getDpMenu().add(manterUsuarioPresenter.getView());
        manterUsuarioPresenter.getView().setVisible(true);
    }
    
    
}
