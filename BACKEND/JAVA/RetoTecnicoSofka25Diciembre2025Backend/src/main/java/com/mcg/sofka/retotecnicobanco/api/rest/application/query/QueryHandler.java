package com.mcg.sofka.retotecnicobanco.api.rest.application.query;

public interface QueryHandler<Q, R> {
    R handle(Q query);
}
