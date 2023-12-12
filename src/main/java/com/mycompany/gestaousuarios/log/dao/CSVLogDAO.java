package com.mycompany.gestaousuarios.log.dao;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSVLogDAO implements LogDAO{
    private String filepath;
    
    public CSVLogDAO() {
        Dotenv env = Dotenv.configure()
            .directory("./resources")
            .filename(".env")
            .load();
        filepath = env.get("LOG_CSV_PATH");
    }
    
    @Override
    public void saveLog(String operacao, String usuarioLogado, String usuarioManipulado){
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath, true))) {
            
        Date dataHoraAtual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataHoraFormatada = formato.format(dataHoraAtual);
        
        StringBuilder sb = new StringBuilder();
            sb.append(operacao);
            sb.append(";");
            sb.append(usuarioManipulado);
            sb.append(";");
            sb.append(dataHoraFormatada);
            sb.append(";");
            sb.append(usuarioLogado);
            sb.append(";");
            sb.append("\n");

            writer.write(sb.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void saveLogErro(String operacao, String usuarioLogado, String usuarioManipulado, String mensagemErro){
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath, true))) {
            
        Date dataHoraAtual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataHoraFormatada = formato.format(dataHoraAtual);
        
        StringBuilder sb = new StringBuilder();
            sb.append(operacao);
            sb.append(";");
            sb.append(usuarioManipulado);
            sb.append(";");
            sb.append(usuarioLogado);
            sb.append(";");
            sb.append(mensagemErro);
            sb.append(";");
            sb.append(dataHoraFormatada);
            sb.append(";");
            sb.append("\n");

            writer.write(sb.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
