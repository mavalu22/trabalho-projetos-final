package com.mycompany.gestaousuarios.principalpresenter.command;

import com.mycompany.gestaousuarios.manterusuariopresenter.state.AlterarSenhaState;
import com.mycompany.gestaousuarios.model.Usuario;
import com.mycompany.gestaousuarios.presenter.ManterUsuarioPresenter;
import com.mycompany.gestaousuarios.presenter.PrincipalPresenter;
import com.mycompany.gestaousuarios.view.PrincipalView;


public class AlterarSenhaCommand extends PrincipalPresenterCommand{
    private ManterUsuarioPresenter manterUsuarioPresenter;

    public AlterarSenhaCommand(
            ManterUsuarioPresenter manterUsuarioPresenter, 
            PrincipalPresenter presenter,
            PrincipalView principalView,
            Usuario usuario
    ) 
    {
        super(presenter, principalView);
        this.manterUsuarioPresenter = manterUsuarioPresenter;
        this.manterUsuarioPresenter.setUsuario(usuario);
    }
    
    @Override
    public void executar() {
        principalView.getDpMenu().remove(manterUsuarioPresenter.getView());
        manterUsuarioPresenter.carregarCampos();
        manterUsuarioPresenter.setEstado(new AlterarSenhaState(manterUsuarioPresenter));
        principalView.getDpMenu().add(manterUsuarioPresenter.getView());
        manterUsuarioPresenter.getView().setVisible(true);
    }
    
    
}
