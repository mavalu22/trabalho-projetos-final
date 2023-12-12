package com.mycompany.gestaousuarios.principalpresenter.state;

import com.mycompany.gestaousuarios.presenter.LogConfigPresenter;
import com.mycompany.gestaousuarios.presenter.ManterUsuarioPresenter;
import com.mycompany.gestaousuarios.presenter.PrincipalPresenter;
import com.mycompany.gestaousuarios.presenter.VisualizarNotificacoesPresenter;
import com.mycompany.gestaousuarios.principalpresenter.command.AlterarSenhaCommand;
import com.mycompany.gestaousuarios.principalpresenter.command.LogConfigCommand;
import com.mycompany.gestaousuarios.principalpresenter.command.PrincipalPresenterCommand;
import com.mycompany.gestaousuarios.principalpresenter.command.VisualizarNotificacoesCommand;

public class LoginUsuarioState extends PrincipalPresenterState{
    
    private PrincipalPresenterCommand command;
    public LoginUsuarioState(PrincipalPresenter presenter) {
        super(presenter);
        manterUsuarioPresenter = new ManterUsuarioPresenter(
            presenter.getUsuario()
        );
        visualizarNotificacoesPresenter = new VisualizarNotificacoesPresenter(
                presenter.getUsuario()
        );
        visualizarNotificacoesPresenter.subscribeNotificacaoObserver(
                presenter
        );
        logConfigPresenter = new LogConfigPresenter();
        initComponents();
    }
    
    @Override
    public void initComponents() {
        decorarInfoUsuario();
        decorarBotaoNotificacoes();
        principalView.getDpMenu().add(manterUsuarioPresenter.getView());
        principalView.getDpMenu().add(visualizarNotificacoesPresenter.getView());
        principalView.getMiLogin().setEnabled(false);
        principalView.getMiCadastrar().setEnabled(false);
        principalView.getMiLog().setEnabled(true);
        principalView.getMiLog().setVisible(true);
        principalView.getLblInfoUsuario().setVisible(true);
        principalView.getBtnNotificacoes().setVisible(true);
        principalView.getMiAlterarSenha().setEnabled(true);
        principalView.getMiBuscarUsuarios().setEnabled(false);
        principalView.getMiBuscarUsuarios().setVisible(false);
    }

    @Override
    public void sair(){
        presenter.fecharJanelasInternas();
        presenter.setEstado(new NaoLogadoState(presenter));
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
    public void logConfig(){
        command = new LogConfigCommand(presenter, principalView, logConfigPresenter);
        command.executar();
    }
    
    @Override
    public void visualizarNotificacoes(){
        command = new VisualizarNotificacoesCommand(
            presenter, 
            principalView, 
            visualizarNotificacoesPresenter
        );
        command.executar();
    }
}
