package com.mycompany.gestaousuarios.validadorsenha;

import java.io.IOException;


public interface IValidadorSenhaAdapter {
    void validar(String senha) throws IOException;
}
