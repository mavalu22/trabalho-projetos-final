package com.mycompany.gestaousuarios.log.dao;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;

public class JSONLogDAO implements LogDAO{
    private String filepath;
    
    public JSONLogDAO() {
        Dotenv env = Dotenv.configure()
            .directory("./resources")
            .filename(".env")
            .load();
        filepath = env.get("LOG_JSON_PATH");
    }
    
    @Override
    public void saveLog(String operacao, String usuarioLogado, String usuarioManipulado){
        
        Date dataHoraAtual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataHoraFormatada = formato.format(dataHoraAtual);
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Operação", operacao);
        jsonObject.put("Nome", usuarioManipulado);
        jsonObject.put("Usuario", usuarioLogado);
        jsonObject.put("Data/Hora", dataHoraFormatada);
        
        try (FileWriter file = new FileWriter(filepath, true)) { 
            file.write(jsonObject.toString() + System.lineSeparator());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void saveLogErro(String operacao, String usuarioLogado, String usuarioManipulado, String mensagemErro){
        Date dataHoraAtual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataHoraFormatada = formato.format(dataHoraAtual);
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Operação", operacao);
        jsonObject.put("Nome", usuarioManipulado);
        jsonObject.put("Usuario", usuarioLogado);
        jsonObject.put("Mensagem de erro", mensagemErro);
        jsonObject.put("Data/Hora", dataHoraFormatada);
        
        try (FileWriter file = new FileWriter(filepath, true)) { 
            file.write(jsonObject.toString() + System.lineSeparator());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
