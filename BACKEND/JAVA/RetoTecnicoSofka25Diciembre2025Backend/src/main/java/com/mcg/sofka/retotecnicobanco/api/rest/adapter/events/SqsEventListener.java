package com.mcg.sofka.retotecnicobanco.api.rest.adapter.events;

import org.springframework.stereotype.Component;

@Component
public class SqsEventListener {
    public void onMessage(String payload) {
        // deserialize and handle message
    }
}
