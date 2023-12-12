package com.mycompany.gestaousuarios.principalpresenter.command;

import com.mycompany.gestaousuarios.manterusuariopresenter.state.VisualizarUsuarioState;
import com.mycompany.gestaousuarios.model.Usuario;
import com.mycompany.gestaousuarios.presenter.BuscarUsuarioPresenter;
import com.mycompany.gestaousuarios.presenter.ManterUsuarioPresenter;
import com.mycompany.gestaousuarios.presenter.PrincipalPresenter;
import com.mycompany.gestaousuarios.view.PrincipalView;


public class VisualizarUsuarioCommand extends PrincipalPresenterCommand{
    private BuscarUsuarioPresenter buscarUsuarioPresenter;
    private Usuario usuario;

    public VisualizarUsuarioCommand(
            BuscarUsuarioPresenter buscarUsuarioPresenter, 
            Usuario usuario, 
            PrincipalPresenter presenter, 
            PrincipalView principalView) 
    {
        super(presenter, principalView);
        this.buscarUsuarioPresenter = buscarUsuarioPresenter;
        this.usuario = usuario;
    }
    
    
    @Override
    public void executar() {
        ManterUsuarioPresenter manterUsuarioPresenter;
        manterUsuarioPresenter = new ManterUsuarioPresenter(usuario);
        buscarUsuarioPresenter.subscribe(manterUsuarioPresenter);
        manterUsuarioPresenter.subscribeManterUsuarioObserver(buscarUsuarioPresenter);
        manterUsuarioPresenter.setEstado(new VisualizarUsuarioState(manterUsuarioPresenter));
        principalView.getDpMenu().add(manterUsuarioPresenter.getView());
        manterUsuarioPresenter.getView().setVisible(true);
    }
     
     
    
}
