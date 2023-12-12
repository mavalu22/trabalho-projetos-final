package com.mycompany.gestaousuarios.log.adapter;

import com.mycompany.gestaousuarios.log.dao.CSVLogDAO;
import com.mycompany.gestaousuarios.log.dao.JSONLogDAO;
import com.mycompany.gestaousuarios.log.dao.LogDAO;
import io.github.cdimascio.dotenv.Dotenv;

public class LogAdapter implements LogDAO{    
    private LogDAO logDAO;
    
    public LogAdapter() {
        Dotenv env = Dotenv.configure()
            .directory("./resources")
            .filename(".env")
            .load();        
        String logType = env.get("LOG_TYPE");
        
        if ("JSON".equals(logType)) {
            this.logDAO = new JSONLogDAO();
        } else if ("CSV".equals(logType)) {
            this.logDAO = new CSVLogDAO();
        } else {
            throw new IllegalArgumentException("Tipo de log desconhecido: " + logType);
        }
    }
    
    @Override
    public void saveLog(String operacao, String usuarioLogado, String usuarioManipulado){
        logDAO.saveLog(operacao, usuarioLogado, usuarioManipulado);
    }
    
    @Override
    public void saveLogErro(String operacao, String usuarioLogado, String usuarioManipulado, String mensagemErro){
        logDAO.saveLogErro(operacao, usuarioLogado, usuarioManipulado, mensagemErro);
    }
    
}
