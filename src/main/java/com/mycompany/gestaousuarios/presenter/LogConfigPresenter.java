package com.mycompany.gestaousuarios.presenter;

import com.mycompany.gestaousuarios.view.LogConfigView;
import io.github.cdimascio.dotenv.Dotenv;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class LogConfigPresenter {
    private LogConfigView view;

    public LogConfigPresenter() {
        this.view = new LogConfigView();
        initListeners();
    }
    
    public LogConfigView getView(){
        return this.view;
    }
    
    public void initListeners(){
        
        this.view.getFecharButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                fechar();
            }
        });

        this.view.getButtonLogSalvar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                logSalvar();
            }
        });
           
    }
    
    public void tipoLog(){
        
        Dotenv env = Dotenv.configure()
            .directory("./resources")
            .filename(".env")
            .load();        
        String logType = env.get("LOG_TYPE");
        
        if ("JSON".equals(logType)) {
            view.getComboLog().setSelectedIndex(1);
        } else if ("CSV".equals(logType)) {
            view.getComboLog().setSelectedIndex(0);
        } else {
            throw new IllegalArgumentException("Tipo de log desconhecido: " + logType);
        }                
    }
    
    public void fechar(){
        view.dispose();
    }
    
    public void logSalvar(){        
        String logType = "LOG_TYPE=" + view.getComboLog().getSelectedItem().toString();
        try {
            substituirLogType("./resources/.env", "LOG_TYPE", logType);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        
        JOptionPane.showMessageDialog(
            view, 
            view.getComboLog().getSelectedItem().toString() + " definido como formato de persistencia dos logs!"
        );
        
        view.dispose();
        
    }

    private static void substituirLogType(String caminhoDoArquivo, String prefixoParaExcluir, String novoConteudo) throws IOException {
        BufferedReader leitor = new BufferedReader(new FileReader(caminhoDoArquivo));
        StringBuilder conteudo = new StringBuilder();
        String linhaAtual;

        while ((linhaAtual = leitor.readLine()) != null) {
           if (linhaAtual.startsWith(prefixoParaExcluir)) {
                conteudo.append(novoConteudo).append("\n");
            } else {
                conteudo.append(linhaAtual).append("\n");
            }
        }

        leitor.close();

        BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoDoArquivo));
        escritor.write(conteudo.toString());
        escritor.close();
    }

}
