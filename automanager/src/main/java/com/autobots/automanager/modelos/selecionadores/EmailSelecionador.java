package com.autobots.automanager.modelos.selecionadores;

import com.autobots.automanager.entitades.Email;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailSelecionador {
    public Email selecionadorEmail(List<Email> emails, long id) {
        Email emailSelecionado = null;
        for (Email email: emails) {
            if (email.getId() == id) {
                emailSelecionado = email;
            }
        }
        return emailSelecionado;
    }
}
