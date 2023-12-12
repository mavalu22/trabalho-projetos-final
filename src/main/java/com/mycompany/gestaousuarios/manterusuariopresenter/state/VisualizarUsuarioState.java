package com.mycompany.gestaousuarios.manterusuariopresenter.state;

import com.mycompany.gestaousuarios.manterusuariopresenter.command.ExcluirUsuarioCommand;
import com.mycompany.gestaousuarios.manterusuariopresenter.command.ManterUsuarioCommand;
import com.mycompany.gestaousuarios.presenter.ManterUsuarioPresenter;
import java.sql.SQLException;


public class VisualizarUsuarioState extends ManterUsuarioPresenterState{
    
    private ManterUsuarioCommand command;
    
    public VisualizarUsuarioState(ManterUsuarioPresenter presenter) {
        super(presenter);
        initComponents();
    }
    
    @Override
    public void initComponents(){
        manterUsuarioView.setTitle("Visualizar usuário: " + presenter.getUsuario().getNome());
        manterUsuarioView.getTxtUserName().setEnabled(false);
        manterUsuarioView.getTxtEmail().setEnabled(false);
        manterUsuarioView.getTxtNome().setEnabled(false);
        manterUsuarioView.getBtnEditar().setEnabled(true);
        manterUsuarioView.getBtnSalvar().setEnabled(false);
        manterUsuarioView.getBtnExcluir().setEnabled(true);
        manterUsuarioView.getCbAdmin().setVisible(true);
        manterUsuarioView.getPsSenha().setVisible(false);
        manterUsuarioView.getLblSenha().setVisible(false);
        manterUsuarioView.getPsSenha().setEnabled(false);
        manterUsuarioView.getCbAutorizado().setVisible(true);
        manterUsuarioView.getCbAdmin().setEnabled(false);
        manterUsuarioView.getCbAutorizado().setEnabled(false);
        manterUsuarioView.getLblDataCriacao().setVisible(true);
        manterUsuarioView.getLblDataModificacao().setVisible(true);
        manterUsuarioView.getTxtId().setEditable(false);
        
    }
    
    @Override
    public void cancelar(){
        presenter.fechar();
    }
    
    @Override
    public void editar(){
         presenter.setEstado(new EditarUsuarioState(presenter));
    }
    
    public void excluir() throws SQLException{
        command = new ExcluirUsuarioCommand(presenter);
        command.executar();
        presenter.notificar();
        presenter.fechar();
    }
    
}
