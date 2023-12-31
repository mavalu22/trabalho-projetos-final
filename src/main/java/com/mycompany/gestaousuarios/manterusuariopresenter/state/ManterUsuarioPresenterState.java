package com.mycompany.gestaousuarios.manterusuariopresenter.state;

import com.mycompany.gestaousuarios.manterusuariopresenter.command.ManterUsuarioCommand;
import com.mycompany.gestaousuarios.presenter.ManterUsuarioPresenter;
import com.mycompany.gestaousuarios.view.ManterUsuarioView;
import java.io.IOException;
import java.sql.SQLException;

public abstract class ManterUsuarioPresenterState {
    
    protected ManterUsuarioPresenter presenter;
    protected ManterUsuarioView manterUsuarioView;
    protected ManterUsuarioCommand command;

    public ManterUsuarioPresenterState(ManterUsuarioPresenter presenter) {
        this.presenter = presenter;
        this.manterUsuarioView = this.presenter.getView();
    }
    
    public void salvar() throws SQLException, IOException{
        throw new RuntimeException("Operação inválida para o estado atual");
    }
    
    public void cancelar(){
        throw new RuntimeException("Operação inválida para o estado atual");
    }
    
    public void editar(){
        throw new RuntimeException("Operação inválida para o estado atual");
    }
    
    public void excluir() throws SQLException{
        throw new RuntimeException("Operação inválida para o estado atual");
    }
    
    public void initComponents(){
        throw new RuntimeException("Operação inválida para o estado atual");
    }
}
