package com.mycompany.gestaousuarios.principalpresenter.state;

import com.mycompany.gestaousuarios.presenter.ManterUsuarioPresenter;
import com.mycompany.gestaousuarios.presenter.PrincipalPresenter;
import com.mycompany.gestaousuarios.principalpresenter.command.AlterarSenhaCommand;
import com.mycompany.gestaousuarios.principalpresenter.command.PrincipalPresenterCommand;
import com.mycompany.gestaousuarios.view.NaoAutorizadoView;


public class LoginNaoAutorizadoState extends PrincipalPresenterState{
    private NaoAutorizadoView naoAutorizadoView;
    private PrincipalPresenterCommand command;
    
    public LoginNaoAutorizadoState(PrincipalPresenter presenter) {
        super(presenter);
        manterUsuarioPresenter = new ManterUsuarioPresenter(
            presenter.getUsuario()
        );
        naoAutorizadoView = new NaoAutorizadoView();
        initComponents();
    }
    
    @Override
    public void initComponents(){
        decorarInfoUsuario();
        principalView.getDpMenu().add(manterUsuarioPresenter.getView());
        presenter.getPrincipalView().getDpMenu().add(naoAutorizadoView);
        principalView.getMiLogin().setEnabled(false);
        principalView.getMiAlterarSenha().setEnabled(true);
        principalView.getMiLog().setEnabled(false);
        principalView.getMiCadastrar().setEnabled(false);
        principalView.getBtnNotificacoes().setVisible(false);
        principalView.getLblInfoUsuario().setVisible(true);
        principalView.getMiBuscarUsuarios().setEnabled(false);
        principalView.getMiBuscarUsuarios().setVisible(false);
        naoAutorizadoView.setVisible(true);
    }
    
    @Override
    public void alterarSenha(){
        command = new AlterarSenhaCommand(
            manterUsuarioPresenter, 
            presenter, 
            principalView,
            presenter.getUsuario()
        );
        command.executar();
    }
    
    @Override
    public void sair(){
        presenter.fecharJanelasInternas();
        presenter.setEstado(new NaoLogadoState(presenter));
    }
}
