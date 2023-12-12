package com.mycompany.gestaousuarios.principalpresenter.state;

import com.mycompany.gestaousuarios.model.Usuario;
import com.mycompany.gestaousuarios.presenter.BuscarUsuarioPresenter;
import com.mycompany.gestaousuarios.presenter.LogConfigPresenter;
import com.mycompany.gestaousuarios.presenter.ManterUsuarioPresenter;
import com.mycompany.gestaousuarios.presenter.PrincipalPresenter;
import com.mycompany.gestaousuarios.presenter.VisualizarNotificacoesPresenter;
import com.mycompany.gestaousuarios.presenter.VisualizarUsuarioObserver;
import com.mycompany.gestaousuarios.principalpresenter.command.AlterarSenhaCommand;
import com.mycompany.gestaousuarios.principalpresenter.command.BuscarUsuarioCommand;
import com.mycompany.gestaousuarios.principalpresenter.command.CadastrarUsuarioAdminCommand;
import com.mycompany.gestaousuarios.principalpresenter.command.LogConfigCommand;
import com.mycompany.gestaousuarios.principalpresenter.command.PrincipalPresenterCommand;
import com.mycompany.gestaousuarios.principalpresenter.command.VisualizarNotificacoesCommand;
import com.mycompany.gestaousuarios.principalpresenter.command.VisualizarUsuarioCommand;


public class LoginAdminState extends PrincipalPresenterState implements VisualizarUsuarioObserver{
    private BuscarUsuarioPresenter buscarUsuarioPresenter;
    private PrincipalPresenterCommand command;
    
    public LoginAdminState(PrincipalPresenter presenter) {
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
        buscarUsuarioPresenter = new BuscarUsuarioPresenter();
        buscarUsuarioPresenter.subscribe(this);
        logConfigPresenter = new LogConfigPresenter();
        
        initComponents();
    }
    
    @Override
    public void initComponents(){
        decorarBotaoNotificacoes();
        decorarInfoUsuario();
        principalView.getDpMenu().add(manterUsuarioPresenter.getView());
        principalView.getDpMenu().add(visualizarNotificacoesPresenter.getView());
        principalView.getDpMenu().add(buscarUsuarioPresenter.getView());
        principalView.getMiLogin().setEnabled(false);
        principalView.getMiCadastrar().setEnabled(true);
        principalView.getMiLog().setEnabled(true);
        principalView.getBtnNotificacoes().setVisible(true);
        principalView.getLblInfoUsuario().setVisible(true);
        principalView.getMiAlterarSenha().setEnabled(true);
        principalView.getMiBuscarUsuarios().setEnabled(true);
        principalView.getMiBuscarUsuarios().setVisible(true);
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
    public void cadastrar(){
        command = new CadastrarUsuarioAdminCommand(
            buscarUsuarioPresenter, 
            presenter, 
            principalView
        );
        command.executar();
    }
    
    @Override
    public void buscarUsuarios(){
        command = new BuscarUsuarioCommand(
            buscarUsuarioPresenter, 
            presenter,
            principalView
        );
        command.executar();
        
    }

    @Override
    public void visualizarUsuario(Usuario usuario) {
        command = new VisualizarUsuarioCommand(
            buscarUsuarioPresenter, 
            usuario, 
            presenter, 
            principalView
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
