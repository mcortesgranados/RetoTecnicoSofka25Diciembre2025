package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.command;

import com.mcg.sofka.retotecnicobanco.api.rest.application.command.dto.CreatePersonCommand;
import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.CommandUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person/command")
public class PersonCommandController {

    private final CommandUseCase<CreatePersonCommand> createUseCase;

    public PersonCommandController(CommandUseCase<CreatePersonCommand> createUseCase) {
        this.createUseCase = createUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreatePersonCommand command) {
        createUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
