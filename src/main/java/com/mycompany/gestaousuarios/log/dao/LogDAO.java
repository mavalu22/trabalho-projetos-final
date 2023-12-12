package com.mycompany.gestaousuarios.log.dao;

public interface LogDAO {
    void saveLog(String operacao, String usuarioLogado, String usuarioManipulado);
    void saveLogErro(String operacao, String usuarioLogado, String usuarioManipulado, String mensagemErro);
}
