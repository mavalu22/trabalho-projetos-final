package com.mycompany.gestaousuarios.presenter;

import com.mycompany.gestaousuarios.model.NotificacaoDTO;


public interface VisualizarNotificacoesObserver {
    void visualizarNotificacao(NotificacaoDTO dto);
}
