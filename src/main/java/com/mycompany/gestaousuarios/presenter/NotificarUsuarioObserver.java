package com.mycompany.gestaousuarios.presenter;

import com.mycompany.gestaousuarios.model.Usuario;
import java.sql.SQLException;


public interface NotificarUsuarioObserver {
    void notificarNovoUsuario(Usuario remetente) throws SQLException;
}
