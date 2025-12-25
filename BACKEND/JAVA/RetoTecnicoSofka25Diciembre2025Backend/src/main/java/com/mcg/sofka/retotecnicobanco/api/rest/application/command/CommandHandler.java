package com.mcg.sofka.retotecnicobanco.api.rest.application.command;

public interface CommandHandler<C> {
    void handle(C command);
}
